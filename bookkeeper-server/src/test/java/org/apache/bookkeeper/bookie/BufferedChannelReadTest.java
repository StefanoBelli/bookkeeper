package org.apache.bookkeeper.bookie;

import io.netty.buffer.ByteBuf;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;
import org.mockito.verification.VerificationMode;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@RunWith(Parameterized.class)
public final class BufferedChannelReadTest extends BufferedChannelWithMockedFileChannel {
    private final String randomData;

    private final ByteBuf destBuf;
    private final int pos;
    private final int len;
    private final Class<Throwable> expectedException;

    private final VerificationMode expectedWriteInvokeVerificationMode;
    private final VerificationMode expectedReadInvokeVerificationMode;
    private final VerificationMode expectedPositionInvokeVerificationMode;

    public BufferedChannelReadTest(ByteBuf destBuf, int pos, int len, Class<Throwable> expectedException, int nRandData) throws IOException {
        super();

        FileChannelMocker fileChannelMocker = new FileChannelMocker();
        fileChannelMocker.mockOnceAndForAll();

        this.pos = pos;
        this.len = len;
        this.expectedException = expectedException;
        this.destBuf = destBuf;
        this.randomData = generateString(nRandData);

        int expFlushes = (int) Math.floor((double) nRandData / capacity);

        expectedWriteInvokeVerificationMode = Mockito.times(expFlushes);
        expectedPositionInvokeVerificationMode = Mockito.times(1 + expFlushes);
        expectedReadInvokeVerificationMode = pos + len >= 100 ? Mockito.atLeastOnce() : Mockito.never();

        sut.write(allocBuf(randomData));
    }

    @Parameterized.Parameters
    public static Iterable<Object[]> params() {
        return Arrays.asList(new Object[][] {
                //{null, 0, 0, NullPointerException.class, 50},
                //{allocBuf(), -1, -1, IllegalArgumentException.class, 50},
                //{allocBuf(), -1, 0, IllegalArgumentException.class, 50},
                //{allocBuf(), 0, -1, IllegalArgumentException.class, 50},
                {allocBuf(), 0, 0, null, 50},
                {allocBuf(), 0, 1, null, 50},
                {allocBuf(), 0, 12, null, 50},
                {allocBuf(), 1, 12, null, 50},
                {allocBuf(), 10, 2, null, 50},
                {allocBuf(), 0, 50, null, 50},
                {allocBuf(), 40, 10, null, 50},
                {allocBuf(), 0, 51, IOException.class, 50},
                {allocBuf(), 40, 11, IOException.class, 50},
                {allocBuf(), 0, 100, null, 300},
                {allocBuf(), 0, 140, null, 300},
                {allocBuf(), 105, 10, null, 300},
                {allocBuf(), 230, 70, null, 300},
                {allocBuf(), 70, 230, null, 300},
                //{allocBuf(), 0, 300, null, 300},
                //{allocBuf(), 0, 301, IOException.class, 300},
                {allocBuf(), 300, 1, IOException.class, 300},
        });
    }

    @Test
    public void testRead() throws Throwable {
        ThrowingRunnable readRunnable = () -> sut.read(destBuf, pos, len);

        if(expectedException != null) {
            assertThrows(expectedException, readRunnable);
        } else {
            readRunnable.run();

            assertEquals(
                    randomData.substring(pos, pos + len),
                    bufContentToString(len, destBuf));

            Mockito.verify(fileChannel, expectedReadInvokeVerificationMode).read(Mockito.any(ByteBuffer.class), Mockito.anyLong());
        }

        Mockito.verify(fileChannel, expectedWriteInvokeVerificationMode).write(Mockito.any(ByteBuffer.class));
        Mockito.verify(fileChannel, expectedPositionInvokeVerificationMode).position();
    }

    private final class FileChannelMocker {
        private final RefWrapper<Long> position = new RefWrapper<>();
        private final RefWrapper<byte[]> storedBytes = new RefWrapper<>();

        private FileChannelMocker() {
            position.setRef(0L);
            storedBytes.setRef(null);
        }

        // use this
        public void mockOnceAndForAll() throws IOException {
            mockPosition();
            mockRead();
            mockWrite();
        }

        private void mockPosition() throws IOException {
            Mockito
                    .when(fileChannel.position())
                    .thenAnswer(m -> position.getRef());
        }

        private void mockRead() throws IOException {
            Mockito
                    .when(fileChannel.read(Mockito.any(ByteBuffer.class), Mockito.anyLong()))
                    .thenAnswer(m -> {
                        ByteBuffer destBuf = m.getArgument(0, ByteBuffer.class);
                        Long arg1 = m.getArgument(1, Long.class);

                        int startingFromPos = arg1.intValue();
                        byte[] storage = storedBytes.getRef();

                        if(startingFromPos < 0) {
                            throw new IllegalArgumentException();
                        }

                        if(startingFromPos >= storage.length) {
                            return -1;
                        }

                        int nReadBytes = destBuf.capacity() + startingFromPos > storage.length ?
                                storage.length - startingFromPos : destBuf.capacity();

                        destBuf.put(storage, startingFromPos, nReadBytes);
                        destBuf.flip();

                        return nReadBytes;
                    });
        }

        private void mockWrite() throws IOException {
            Mockito
                    .when(fileChannel.write(Mockito.any(ByteBuffer.class)))
                    .thenAnswer(m -> {
                        ByteBuffer srcBuf = m.getArgument(0, ByteBuffer.class);

                        int nBytesWrote = srcBuf.remaining();
                        byte[] nextBytesToBeStored = new byte[nBytesWrote];
                        srcBuf.get(nextBytesToBeStored);

                        Long curPos = position.getRef();
                        position.setRef(curPos + nBytesWrote);

                        byte[] alreadyStoredBytes = storedBytes.getRef();
                        if(alreadyStoredBytes == null) {
                            storedBytes.setRef(nextBytesToBeStored);
                        } else {
                            storedBytes.setRef(concatArray(alreadyStoredBytes, nextBytesToBeStored));
                        }

                        return nBytesWrote;
                    });
        }

        private byte[] concatArray(byte[] arr1, byte[] arr2) {
            final byte[] res = new byte[arr1.length + arr2.length];

            System.arraycopy(arr1, 0, res, 0, arr1.length);
            System.arraycopy(arr2, 0, res, arr1.length, arr2.length);

            return res;
        }
    }
}
