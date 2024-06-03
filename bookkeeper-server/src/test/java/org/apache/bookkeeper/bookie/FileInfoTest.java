package org.apache.bookkeeper.bookie;

import org.apache.bookkeeper.bookie.FileInfo;
import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.junit.Assert.*;

public class FileInfoTest {
    private static final File logFile = new File("bklogs/test-bklog.bin");
    private static final byte[] masterKey = "bkIntegrityKey".getBytes();
    private static final int headerVersion = FileInfo.V1;

    @After
    public void postTest() {
        assertTrue(logFile.delete());
        assertTrue(logFile.getParentFile().delete());
    }

    @Test
    public void testCheckOpen() throws IOException {
        FileInfo fileInfo = createFileInfo();

        assertThrows(IOException.class, fileInfo::readHeader);

        fileInfo.checkOpen(false);
        assertFalse(logFile.exists());
        assertThrows(IOException.class, fileInfo::readHeader);

        fileInfo.checkOpen(true);
        assertTrue(logFile.exists());
        checkEntireLogFileHeader(fileInfo);

        fileInfo.close(true);
    }

    @Test
    public void testNullWrite() throws IOException {
        FileInfo fileInfo = createFileInfo();

        assertThrows(NullPointerException.class, () -> fileInfo.write(null, 0));

        fileInfo.close(true);
    }

    @Test
    public void testFirstByteBufferInvalidWrite() throws IOException {
        FileInfo fileInfo = createFileInfo();

        byte[] data = "Hello world".getBytes();
        ByteBuffer dataBuf = ByteBuffer.allocate(data.length);

        assertThrows(NullPointerException.class,
                () -> fileInfo.write(new ByteBuffer[] { null, dataBuf }, 0));

        fileInfo.close(true);
    }

    @Test
    public void testSecondByteBufferInvalidWrite() throws IOException {
        FileInfo fileInfo = createFileInfo();

        byte[] data = "Hello world".getBytes();
        ByteBuffer dataBuf = ByteBuffer.allocate(data.length);

        assertThrows(NullPointerException.class,
                () -> fileInfo.write(new ByteBuffer[] { dataBuf, null }, 0));

        fileInfo.close(true);
    }

    @Test
    public void testCorrectSingleElementByteBufferWrite() throws IOException {
        FileInfo fileInfo = createFileInfo();

        byte[] data = "Hello world".getBytes();

        ByteBuffer dataBuf = ByteBuffer.allocate(data.length);
        dataBuf.put(data);
        dataBuf.flip();
        fileInfo.write(new ByteBuffer[] { dataBuf }, 0);

        ByteBuffer dstBuf = ByteBuffer.allocate(data.length);
        int effNReadBytes = fileInfo.read(dstBuf, 0, false);

        assertEquals(data.length, effNReadBytes);

        byte[] dataBack = new byte[data.length];
        dstBuf.flip();
        dstBuf.get(dataBack, 0, data.length);

        assertEquals(new String(data), new String(dataBack));

        fileInfo.close(true);
    }

    /*
    @Test
    public void testReadEntryAfterWrite() throws IOException {
        byte[] payloadData = "Hello world".getBytes();
        FileInfo fileInfo = createFileInfo();

        fileInfo.write(createLogEntry(0, 0, payloadData), 0);

        ByteBuffer logEntry = ByteBuffer.allocate(payloadData.length);
        fileInfo.read(logEntry, BK_ENTRY_META_LEN, false);

        byte[] matchingPayload = new byte[payloadData.length];
        logEntry.flip();
        logEntry.get(matchingPayload, 0, payloadData.length);

        fileInfo.close(true);

        assertEquals(new String(payloadData), new String(matchingPayload));
    }
     */

    private static void checkEntireLogFileHeader(FileInfo fileInfo) throws IOException {
        try(FileInputStream fis = new FileInputStream(logFile)) {
            byte[] briefHeader = new byte[LOGFILE_BRIEFHEADER_V1_LENGTH];
            int nReadBytes = fis.read(briefHeader);

            assertEquals(nReadBytes, LOGFILE_BRIEFHEADER_V1_LENGTH);

            assertEquals('B', briefHeader[0]);
            assertEquals('K', briefHeader[1]);
            assertEquals('L', briefHeader[2]);
            assertEquals('E', briefHeader[3]);

            int leHdrVer = bytesToInt(briefHeader[4], briefHeader[5],
                    briefHeader[6], briefHeader[7]);
            assertEquals(headerVersion, leHdrVer);

            int leMkLen = bytesToInt(briefHeader[8], briefHeader[9],
                    briefHeader[10], briefHeader[11]);
            assertEquals(masterKey.length, leMkLen);

            assertEquals(new String(masterKey),
                    new String(extractBytes(12, 12 + masterKey.length, briefHeader)));

            byte leState0 = briefHeader[12 + masterKey.length];
            byte leState1 = briefHeader[12 + masterKey.length + 1];
            byte leState2 = briefHeader[12 + masterKey.length + 2];
            byte leState3 = briefHeader[12 + masterKey.length + 3];

            int stateBits = bytesToInt(leState0, leState1, leState2, leState3);
            assertEquals(0, stateBits & FileInfo.STATE_FENCED_BIT);
        }

        assertTrue(fileInfo.setFenced());
        fileInfo.flushHeader();

        try(FileInputStream fis = new FileInputStream(logFile)) {
            byte[] briefHeader = new byte[LOGFILE_BRIEFHEADER_V1_LENGTH];
            int nReadBytes = fis.read(briefHeader);

            assertEquals(nReadBytes, LOGFILE_BRIEFHEADER_V1_LENGTH);

            byte leState0 = briefHeader[12 + masterKey.length];
            byte leState1 = briefHeader[12 + masterKey.length + 1];
            byte leState2 = briefHeader[12 + masterKey.length + 2];
            byte leState3 = briefHeader[12 + masterKey.length + 3];

            int stateBits = bytesToInt(leState0, leState1, leState2, leState3);
            assertEquals(1, stateBits & FileInfo.STATE_FENCED_BIT);
        }

        fileInfo.close(true);
    }

    // test utils

    // 0: Signature [Integer]
    // 4: HeaderVersion [Integer]
    // 8: MasterKeyLengthInBytes [Integer]
    // 12: MasterKey [VariadicSize]
    // 12+MKLen: FencingStateBits [Integer]
    private static final int LOGFILE_BRIEFHEADER_V1_LENGTH =
            Integer.BYTES * 4 + masterKey.length;

    private static int bytesToInt(byte b0, byte b1, byte b2, byte b3) {
        return b0 << 24 | b1 << 16 | b2 << 8 | b3;
    }

    private static byte[] extractBytes(int fromIdxIncl, int toExcl, byte[] largerBuf) {
        byte[] buf = new byte[toExcl - fromIdxIncl];
        for(int i = fromIdxIncl; i < toExcl; ++i) {
            buf[i - fromIdxIncl] = largerBuf[i];
        }

        return buf;
    }

    private static FileInfo createFileInfo() throws IOException {
        return new FileInfo(logFile, masterKey, headerVersion);
    }

    private static final long BK_ENTRY_META_LEN = Long.BYTES * 2 + Integer.BYTES;

    /*
    private static ByteBuffer[] createLogEntry(int ledgerId, int entryId, byte[] payload) {
        ByteBuffer bkMeta = ByteBuffer.allocate((int) BK_ENTRY_META_LEN);
        bkMeta.putLong(ledgerId);
        bkMeta.putLong(entryId);
        bkMeta.putInt(payload.length);
        bkMeta.flip();

        ByteBuffer bkPayload = ByteBuffer.allocate(payload.length);
        bkPayload.put(payload);
        bkPayload.flip();

        return new ByteBuffer[] { bkMeta, bkPayload };
    }
     */
}