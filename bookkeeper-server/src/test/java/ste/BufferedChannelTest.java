package ste;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.apache.bookkeeper.bookie.BufferedChannel;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@RunWith(MockitoJUnitRunner.class)
public class BufferedChannelTest {
    public BufferedChannel bufferedChannel;

    public FileChannel fileChannel;

    @Before
    public void setupNewBufferedChannel() throws IOException {
        fileChannel = Mockito.mock(FileChannel.class);
        bufferedChannel = new BufferedChannel(ByteBufAllocator.DEFAULT, fileChannel, 100);
    }

    @Test
    public void testWriteReadNoFlush() throws IOException {
        byte[] myBytes = "somebytes".getBytes();
        ByteBuf contentWriteBuf = getBuffer();

        contentWriteBuf.writeBytes(myBytes);
        bufferedChannel.write(contentWriteBuf);

        assertEquals(myBytes.length, bufferedChannel.getNumOfBytesInWriteBuffer());

        assertEquals(myBytes.length, bufferedChannel.position());

        assertEquals(0, bufferedChannel.getFileChannelPosition());

        ByteBuf contentReadBuf = getBuffer();
        contentReadBuf.capacity(myBytes.length);
        int nReadBytes = bufferedChannel.read(contentReadBuf, 0, myBytes.length);

        byte[] stillMyBytes = new byte[nReadBytes];
        contentReadBuf.readBytes(stillMyBytes);

        assertEquals(new String(myBytes), new String(stillMyBytes));
    }

    @Test
    public void testMultipleWritesReadsNoFlush() throws IOException {
        byte[][] myBytes = {
                "k".getBytes(),
                "somebytes".getBytes(),
                "morebytes".getBytes()
        };

        int totalNumBytes = 0;

        for(byte[] bytesToWrite : myBytes) {
            ByteBuf contentWriteBuf = getBuffer();
            contentWriteBuf.writeBytes(bytesToWrite);
            bufferedChannel.write(contentWriteBuf);
            totalNumBytes += bytesToWrite.length;
        }

        assertEquals(totalNumBytes, bufferedChannel.getNumOfBytesInWriteBuffer());

        assertEquals(totalNumBytes, bufferedChannel.position());

        assertEquals(0, bufferedChannel.getFileChannelPosition());

        int curReadPos = 0;
        for(byte[] matchingBytesToRead : myBytes) {
            ByteBuf contentReadBuf = getBuffer();
            contentReadBuf.capacity(matchingBytesToRead.length);
            int nReadBytes = bufferedChannel.read(contentReadBuf, curReadPos, matchingBytesToRead.length);

            byte[] stillMyBytes = new byte[nReadBytes];
            contentReadBuf.readBytes(stillMyBytes);

            assertEquals(new String(matchingBytesToRead), new String(stillMyBytes));

            curReadPos += nReadBytes;
        }
    }

    @Test
    public void testWriteReadForceFlush() throws IOException {
        byte[] myBytes = "somebytes".getBytes();

        ByteBuf buf = getBuffer();
        buf.writeBytes(myBytes);

        bufferedChannel.write(buf);

        RefWrapper<byte[]> wroteRef = new RefWrapper<>();

        Mockito
                .when(fileChannel.write(Mockito.any(ByteBuffer.class)))
                .thenAnswer(
                        invoc -> {
                            ByteBuffer localBuf = invoc.getArgument(0, ByteBuffer.class);
                            int rem = localBuf.remaining();
                            byte[] dstLocalBuf = new byte[rem];
                            localBuf.get(dstLocalBuf, localBuf.position(), rem);
                            wroteRef.set(dstLocalBuf);
                            return rem;
                        });

        Mockito
                .when(fileChannel.position())
                        .thenAnswer(unused -> (long) wroteRef.get().length);

        bufferedChannel.flushAndForceWrite(true);

        byte[] flushedBytes = wroteRef.get();

        assertEquals(0, bufferedChannel.getNumOfBytesInWriteBuffer());

        assertEquals(flushedBytes.length, bufferedChannel.position());

        assertEquals(flushedBytes.length, bufferedChannel.getFileChannelPosition());
    }

    @Test
    public void testWriteReadExceedBufferCapacity() {

    }

    @Test
    public void testClearBuf() {

    }

    @Test
    public void testReadEmptyBuffer() {
        ByteBuf buf = getBuffer();
        assertThrows(IOException.class, () -> bufferedChannel.read(buf, 0, 1));
    }

    private static ByteBuf getBuffer() {
        return ByteBufAllocator.DEFAULT.buffer();
    }
}

class RefWrapper<T> {
    private T t;

    public void set(T t) {
        this.t = t;
    }

    public T get() {
        return t;
    }
}