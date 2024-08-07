package org.apache.bookkeeper.bookie;

import io.netty.buffer.ByteBuf;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.internal.matchers.Null;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@RunWith(Parameterized.class)
public final class BufferedChannelReadTest extends BufferedChannelWithMockedFileChannel {
    private final String randomData = "randomdatarandomdatarandomdatarandomdatarandomdata";

    private final ByteBuf destBuf;
    private final int pos;
    private final int len;
    private final Class<Throwable> expectedException;

    public BufferedChannelReadTest(ByteBuf destBuf, int pos, int len, Class<Throwable> expectedException) throws IOException {
        super();

        this.pos = pos;
        this.len = len;
        this.expectedException = expectedException;
        this.destBuf = destBuf;

        sut.write(allocBuf(randomData));
    }

    @Parameterized.Parameters
    public static Iterable<Object[]> params() {
        return Arrays.asList(new Object[][] {
                //{null, 0, 0, NullPointerException.class},
                //{allocBuf(), -1, -1, IllegalArgumentException.class},
                //{allocBuf(), -1, 0, IllegalArgumentException.class},
                //{allocBuf(), 0, -1, IllegalArgumentException.class},
                {allocBuf(), 0, 0, null},
                {allocBuf(), 0, 1, null},
                {allocBuf(), 0, 12, null},
                {allocBuf(), 1, 12, null},
                {allocBuf(), 10, 2, null},
                {allocBuf(), 0, 50, null},
                {allocBuf(), 40, 10, null},
                {allocBuf(), 0, 51, IOException.class},
                {allocBuf(), 40, 11, IOException.class}
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
        }
    }
}
