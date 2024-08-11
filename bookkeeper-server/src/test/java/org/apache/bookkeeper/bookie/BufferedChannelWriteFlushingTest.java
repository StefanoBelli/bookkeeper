package org.apache.bookkeeper.bookie;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public final class BufferedChannelWriteFlushingTest extends BufferedChannelWithMockedFileChannel {

    private final String[] preWriteData;
    private final String writeData;

    private final FileChannelMocker fileChannelMocker = new FileChannelMocker();

    public BufferedChannelWriteFlushingTest(String[] preWriteData, String writeData) throws IOException {
        this.preWriteData = preWriteData;
        this.writeData = writeData;
        fileChannelMocker.mockOnceAndForAll();
    }

    @Parameterized.Parameters
    public static Iterable<Object[]> params() {
        return Arrays.asList(new Object[][]{
                { null, "" },
                { null, "random data" },
                { new String[] { generateString(50) }, "more random data" },
                { new String[] { generateString(50), generateString(10) } , "datadatadata" },
                { new String[] { generateString(90) }, "again random data" },
                { new String[] { generateString(80), generateString(10) }, "even more random data" },
                { null, generateString(capacity + 10) }
        });
    }

    @Before
    public void preWrite() throws IOException {
        if(preWriteData != null) {
            for (final String data : preWriteData) {
                sut.write(allocBuf(data));
            }
        }
    }

    @Test
    public void testWrite() throws IOException {
        sut.write(allocBuf(writeData));

        String fullyWroteData = getFullyWroteData();
        int remainingBytes = getRemainingBytesOverall(fullyWroteData);

        Mockito.verify(fileChannel, Mockito.times(remainingBytes >= 0 ? 1 : 0)).write(Mockito.any(ByteBuffer.class));
        Mockito.verify(fileChannel, Mockito.times(remainingBytes >= 0 ? 2 : 1)).position();
        assertEquals(flip(remainingBytes), sut.getNumOfBytesInWriteBuffer());
        assertEquals(fullyWroteData.length(), sut.position());
        assertEquals(fileChannelMocker.numOfBytesWrote.getRef().longValue(), sut.getFileChannelPosition());
        assertEquals(getExpectedWroteData(fullyWroteData, remainingBytes), fileChannelMocker.wroteData.getRef());
    }

    private String getFullyWroteData() {
        final String prew = preWriteData != null ? concatArray(preWriteData) : "";
        return concatArray(new String[] { prew, writeData });
    }

    private int getRemainingBytesOverall(String full) {
        return full.length() - capacity;
    }

    private static int flip(int remb) {
        return remb < 0 ? remb + capacity : remb;
    }

    private static String getExpectedWroteData(String fullyWroteData, int remainingBytes) {
        if(remainingBytes < 0) {
            return null;
        }

        return fullyWroteData.substring(0,fullyWroteData.length() - remainingBytes);
    }

    private final class FileChannelMocker {
        private final RefWrapper<Long> numOfBytesWrote = new RefWrapper<>();
        private final RefWrapper<String> wroteData = new RefWrapper<>();

        public FileChannelMocker() {
            numOfBytesWrote.setRef((long) 0);
            wroteData.setRef(null);
        }

        public void mockOnceAndForAll() throws IOException {
            mockPosition();
            mockWrite();
        }

        private void mockWrite() throws IOException {
            Mockito.when(fileChannel.write(Mockito.any(ByteBuffer.class))).thenAnswer(
                    invocationOnMock -> {
                        ByteBuffer arg0 = invocationOnMock.getArgument(0, ByteBuffer.class);
                        if (arg0.hasRemaining()) {
                            int lnumOfBytesWrote = arg0.remaining();
                            byte[] wroteToFile = new byte[lnumOfBytesWrote];
                            arg0.get(wroteToFile);

                            wroteData.setRef(new String(wroteToFile));
                            numOfBytesWrote.setRef((long) lnumOfBytesWrote);

                            return lnumOfBytesWrote;
                        }

                        return 0;
                    });
        }

        private void mockPosition() throws IOException {
            Mockito.when(fileChannel.position()).thenAnswer(invocationOnMock -> numOfBytesWrote.getRef());
        }

    }
}
