package org.apache.bookkeeper.bookie;

import io.netty.buffer.ByteBuf;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public final class BufferedChannelWriteTest extends BufferedChannelWithMockedFileChannel {
    public BufferedChannelWriteTest() throws IOException {}

    @After
    public void clrBuf() {
        sut.clear();
    }

    @Test
    public void testNullWriteBuffer() {
        assertThrows(NullPointerException.class, () -> sut.write(null));
    }

    @Test
    public void testEmptyWriteBuffer() throws IOException {
        sut.write(allocBuf(""));

        assertEquals(0, sut.position());
        assertEquals(0, sut.getNumOfBytesInWriteBuffer());
    }

    @Test
    public void testNonEmptyWriteBuffer() throws IOException {
        String data = "random data";
        ByteBuf buf = allocBuf(data);

        sut.write(buf);

        assertEquals(data.length(), sut.position());
        assertEquals(data.length(), sut.getNumOfBytesInWriteBuffer());

        ByteBuf destBuf = allocBuf();

        sut.read(destBuf, 0, data.length());
        assertEquals(data, bufContentToString(data.length(), destBuf));
    }

    @Test
    public void testTwoConsecutiveWrites() throws IOException {
        String[] data = { "random data", "more random data" };

        consecutiveWrites(data);
    }

    @Test
    public void testThreeConsecutiveWrites() throws IOException {
        String[] data = { "random data", "more random data", "again random data" };

        consecutiveWrites(data);
    }

    private void consecutiveWrites(String[] datas) throws IOException {
        int prevWritePos = 0;

        for(final String data : datas) {
            ByteBuf buf = allocBuf(data);

            sut.write(buf);

            assertEquals(prevWritePos + data.length(), sut.position());
            assertEquals(prevWritePos + data.length(), sut.getNumOfBytesInWriteBuffer());

            ByteBuf destBuf = allocBuf();

            sut.read(destBuf, prevWritePos, data.length());
            assertEquals(data, bufContentToString(data.length(), destBuf));

            prevWritePos += data.length();
        }

        ByteBuf wholeBuf = allocBuf();
        sut.read(wholeBuf, 0, prevWritePos);

        assertEquals(concatArray(datas), bufContentToString(prevWritePos, wholeBuf));
    }

    private static String concatArray(String[] strs) {
        StringBuilder builder = new StringBuilder();

        for(final String str : strs) {
            builder.append(str);
        }

        return builder.toString();
    }
}
