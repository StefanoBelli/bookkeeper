package org.apache.bookkeeper.bookie;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class BufferedChannelWithMockedFileChannel {
    private static final ByteBufAllocator bufAllocator = ByteBufAllocator.DEFAULT;
    protected final FileChannel fileChannel = Mockito.mock(FileChannel.class);
    protected final BufferedChannel sut;
    protected static final int capacity = 100;

    protected BufferedChannelWithMockedFileChannel() throws IOException {
        Mockito.when(fileChannel.position()).thenReturn(Long.valueOf(0));
        sut = new BufferedChannel(bufAllocator, fileChannel, capacity);
    }

    protected static ByteBuf allocBuf(String data) {
        byte[] b = data.getBytes(StandardCharsets.UTF_8);
        ByteBuf buf = bufAllocator.buffer();
        buf.writeBytes(b);

        return buf;
    }

    protected static ByteBuf allocBuf() {
        return bufAllocator.buffer();
    }

    protected static String bufContentToString(int size, ByteBuf buf) {
        byte[] b = new byte[size];
        buf.getBytes(0, b);

        return new String(b);
    }

    protected static String concatArray(String[] strs) {
        StringBuilder builder = new StringBuilder();

        for(final String str : strs) {
            builder.append(str);
        }

        return builder.toString();
    }

    protected static String generateString(int len) {
        StringBuilder s = new StringBuilder();

        for(int i = 1; i <= len; ++i) {
            s.append(randomChar());
        }

        return s.toString();
    }

    protected static char randomChar() {
        final Random random = new Random();
        final String alphabet = "qwertyuiopasdfghjklzxcvbnm1234567890";

        return alphabet.charAt(random.nextInt(alphabet.length()));
    }

}