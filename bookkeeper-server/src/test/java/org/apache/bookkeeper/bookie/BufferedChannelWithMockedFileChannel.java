package org.apache.bookkeeper.bookie;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

public class BufferedChannelWithMockedFileChannel {
    private final ByteBufAllocator bufAllocator = ByteBufAllocator.DEFAULT;
    protected final FileChannel fileChannel = Mockito.mock(FileChannel.class);
    protected final BufferedChannel sut;
    protected final int capacity;

    protected BufferedChannelWithMockedFileChannel(int capacity) throws IOException {
        this.capacity = capacity;
        sut = new BufferedChannel(bufAllocator, fileChannel, capacity);
    }

    protected BufferedChannelWithMockedFileChannel() throws IOException {
        this(100);
    }

    protected ByteBuf allocBuf(String data) {
        byte[] b = data.getBytes(StandardCharsets.UTF_8);
        ByteBuf buf = bufAllocator.buffer();
        buf.writeBytes(b);

        return buf;
    }

    protected ByteBuf allocBuf() {
        return bufAllocator.buffer();
    }

    protected String bufContentToString(int size, ByteBuf buf) {
        byte[] b = new byte[size];
        buf.getBytes(0, b);

        return new String(b);
    }
}