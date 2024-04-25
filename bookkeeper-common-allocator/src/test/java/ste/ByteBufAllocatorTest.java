package ste;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.Collection;

import org.apache.bookkeeper.common.allocator.ByteBufAllocatorBuilder;
import org.apache.bookkeeper.common.allocator.LeakDetectionPolicy;
import org.apache.bookkeeper.common.allocator.OutOfMemoryPolicy;
import org.apache.bookkeeper.common.allocator.PoolingPolicy;
import org.assertj.core.util.Arrays;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

@RunWith(Parameterized.class)
public class ByteBufAllocatorTest {
    private interface GetByteBuf {
        void alloc(ByteBufAllocator allocator);
        ByteBuf buf();
        ByteBuf buf(int minCap);
        ByteBuf buf(int minCap, int maxCap);
    }

    private GetByteBuf getByteBuf;

    private static class GetBuffer implements GetByteBuf {
        private ByteBufAllocator allocator;
        
        public void alloc(ByteBufAllocator allocator) {
            this.allocator = allocator;
        }

        @Override
        public ByteBuf buf() {
            return allocator.buffer();
        }

        @Override
        public ByteBuf buf(int minCap) {
            return allocator.buffer(minCap);
        }

        @Override
        public ByteBuf buf(int minCap, int maxCap) {
            return allocator.buffer(minCap, maxCap);
        }
        
    }
    
    private static class GetIoBuffer implements GetByteBuf {
        private ByteBufAllocator allocator;

        @Override
        public ByteBuf buf() {
            return allocator.ioBuffer();
        }

        @Override
        public ByteBuf buf(int minCap) {
            return allocator.ioBuffer(minCap);
        }

        @Override
        public ByteBuf buf(int minCap, int maxCap) {
            return allocator.ioBuffer(minCap, maxCap);
        }

        @Override
        public void alloc(ByteBufAllocator allocator) {
            this.allocator = allocator;
        }
        
    }   
 
    private static class GetHeapBuffer implements GetByteBuf {

        private ByteBufAllocator allocator;

        @Override
        public ByteBuf buf() {
            return allocator.heapBuffer();
        }

        @Override
        public ByteBuf buf(int minCap) {
            return allocator.heapBuffer(minCap);
        }

        @Override
        public ByteBuf buf(int minCap, int maxCap) {
            return allocator.heapBuffer(minCap, maxCap);
        }

        @Override
        public void alloc(ByteBufAllocator allocator) {
            this.allocator = allocator;
        }
        
    }
  
    private static class GetDirectBuffer implements GetByteBuf {
        private ByteBufAllocator allocator;

        @Override
        public ByteBuf buf() {
            return allocator.directBuffer();
        }

        @Override
        public ByteBuf buf(int minCap) {
            return allocator.directBuffer(minCap);
        }

        @Override
        public ByteBuf buf(int minCap, int maxCap) {
            return allocator.directBuffer(minCap, maxCap);
        }
        
        @Override
        public void alloc(ByteBufAllocator allocator) {
            this.allocator = allocator;
        }
    }  

    public ByteBufAllocatorTest(LeakDetectionPolicy l, OutOfMemoryPolicy o, PoolingPolicy p, GetByteBuf getByteBuf) {
        ByteBufAllocatorBuilder builder = ByteBufAllocatorBuilder.create();
        builder
            .leakDetectionPolicy(l)
            .outOfMemoryPolicy(o)
            .poolingPolicy(p);
        this.getByteBuf = getByteBuf;
        this.getByteBuf.alloc(builder.build());
        this.basicBuf = getByteBuf.buf();
        this.basicMinSizedBuf = getByteBuf.buf(100);
        this.basicMinSizedMaxCapBuf = getByteBuf.buf(100, 300);
    }

    @Parameters
    public static Collection params() {
        return Arrays.asList(new Object[][] {
            { LeakDetectionPolicy.Disabled, OutOfMemoryPolicy.ThrowException, PoolingPolicy.PooledDirect, new GetBuffer() },
            { LeakDetectionPolicy.Simple, OutOfMemoryPolicy.ThrowException, PoolingPolicy.PooledDirect, new GetBuffer() },
            { LeakDetectionPolicy.Advanced, OutOfMemoryPolicy.ThrowException, PoolingPolicy.PooledDirect, new GetBuffer() },
            { LeakDetectionPolicy.Paranoid, OutOfMemoryPolicy.ThrowException, PoolingPolicy.PooledDirect, new GetBuffer() },
            { LeakDetectionPolicy.Disabled, OutOfMemoryPolicy.FallbackToHeap, PoolingPolicy.PooledDirect, new GetBuffer() },
            { LeakDetectionPolicy.Simple, OutOfMemoryPolicy.FallbackToHeap, PoolingPolicy.PooledDirect, new GetBuffer() },
            { LeakDetectionPolicy.Advanced, OutOfMemoryPolicy.FallbackToHeap, PoolingPolicy.PooledDirect, new GetBuffer() },
            { LeakDetectionPolicy.Paranoid, OutOfMemoryPolicy.FallbackToHeap, PoolingPolicy.PooledDirect, new GetBuffer() },
            { LeakDetectionPolicy.Disabled, OutOfMemoryPolicy.ThrowException, PoolingPolicy.UnpooledHeap, new GetBuffer() },
            { LeakDetectionPolicy.Simple, OutOfMemoryPolicy.ThrowException, PoolingPolicy.UnpooledHeap, new GetBuffer() },
            { LeakDetectionPolicy.Advanced, OutOfMemoryPolicy.ThrowException, PoolingPolicy.UnpooledHeap, new GetBuffer() },
            { LeakDetectionPolicy.Paranoid, OutOfMemoryPolicy.ThrowException, PoolingPolicy.UnpooledHeap, new GetBuffer() },
            { LeakDetectionPolicy.Disabled, OutOfMemoryPolicy.FallbackToHeap, PoolingPolicy.UnpooledHeap, new GetBuffer() },
            { LeakDetectionPolicy.Simple, OutOfMemoryPolicy.FallbackToHeap, PoolingPolicy.UnpooledHeap, new GetBuffer() },
            { LeakDetectionPolicy.Advanced, OutOfMemoryPolicy.FallbackToHeap, PoolingPolicy.UnpooledHeap, new GetBuffer() },
            { LeakDetectionPolicy.Paranoid, OutOfMemoryPolicy.FallbackToHeap, PoolingPolicy.UnpooledHeap, new GetBuffer() },
            { LeakDetectionPolicy.Disabled, OutOfMemoryPolicy.ThrowException, PoolingPolicy.PooledDirect, new GetIoBuffer() },
            { LeakDetectionPolicy.Simple, OutOfMemoryPolicy.ThrowException, PoolingPolicy.PooledDirect, new GetIoBuffer() },
            { LeakDetectionPolicy.Advanced, OutOfMemoryPolicy.ThrowException, PoolingPolicy.PooledDirect, new GetIoBuffer() },
            { LeakDetectionPolicy.Paranoid, OutOfMemoryPolicy.ThrowException, PoolingPolicy.PooledDirect, new GetIoBuffer() },
            { LeakDetectionPolicy.Disabled, OutOfMemoryPolicy.FallbackToHeap, PoolingPolicy.PooledDirect, new GetIoBuffer() },
            { LeakDetectionPolicy.Simple, OutOfMemoryPolicy.FallbackToHeap, PoolingPolicy.PooledDirect, new GetIoBuffer() },
            { LeakDetectionPolicy.Advanced, OutOfMemoryPolicy.FallbackToHeap, PoolingPolicy.PooledDirect, new GetIoBuffer() },
            { LeakDetectionPolicy.Paranoid, OutOfMemoryPolicy.FallbackToHeap, PoolingPolicy.PooledDirect, new GetIoBuffer() },
            { LeakDetectionPolicy.Disabled, OutOfMemoryPolicy.ThrowException, PoolingPolicy.UnpooledHeap, new GetIoBuffer() },
            { LeakDetectionPolicy.Simple, OutOfMemoryPolicy.ThrowException, PoolingPolicy.UnpooledHeap, new GetIoBuffer() },
            { LeakDetectionPolicy.Advanced, OutOfMemoryPolicy.ThrowException, PoolingPolicy.UnpooledHeap, new GetIoBuffer() },
            { LeakDetectionPolicy.Paranoid, OutOfMemoryPolicy.ThrowException, PoolingPolicy.UnpooledHeap, new GetIoBuffer() },
            { LeakDetectionPolicy.Disabled, OutOfMemoryPolicy.FallbackToHeap, PoolingPolicy.UnpooledHeap, new GetIoBuffer() },
            { LeakDetectionPolicy.Simple, OutOfMemoryPolicy.FallbackToHeap, PoolingPolicy.UnpooledHeap, new GetIoBuffer() },
            { LeakDetectionPolicy.Advanced, OutOfMemoryPolicy.FallbackToHeap, PoolingPolicy.UnpooledHeap, new GetIoBuffer() },
            { LeakDetectionPolicy.Paranoid, OutOfMemoryPolicy.FallbackToHeap, PoolingPolicy.UnpooledHeap, new GetIoBuffer() },
            { LeakDetectionPolicy.Disabled, OutOfMemoryPolicy.ThrowException, PoolingPolicy.PooledDirect, new GetDirectBuffer() },
            { LeakDetectionPolicy.Simple, OutOfMemoryPolicy.ThrowException, PoolingPolicy.PooledDirect, new GetDirectBuffer() },
            { LeakDetectionPolicy.Advanced, OutOfMemoryPolicy.ThrowException, PoolingPolicy.PooledDirect, new GetDirectBuffer() },
            { LeakDetectionPolicy.Paranoid, OutOfMemoryPolicy.ThrowException, PoolingPolicy.PooledDirect, new GetDirectBuffer() },
            { LeakDetectionPolicy.Disabled, OutOfMemoryPolicy.FallbackToHeap, PoolingPolicy.PooledDirect, new GetDirectBuffer() },
            { LeakDetectionPolicy.Simple, OutOfMemoryPolicy.FallbackToHeap, PoolingPolicy.PooledDirect, new GetDirectBuffer() },
            { LeakDetectionPolicy.Advanced, OutOfMemoryPolicy.FallbackToHeap, PoolingPolicy.PooledDirect, new GetDirectBuffer() },
            { LeakDetectionPolicy.Paranoid, OutOfMemoryPolicy.FallbackToHeap, PoolingPolicy.PooledDirect, new GetDirectBuffer() },
            { LeakDetectionPolicy.Disabled, OutOfMemoryPolicy.ThrowException, PoolingPolicy.UnpooledHeap, new GetDirectBuffer() },
            { LeakDetectionPolicy.Simple, OutOfMemoryPolicy.ThrowException, PoolingPolicy.UnpooledHeap, new GetDirectBuffer() },
            { LeakDetectionPolicy.Advanced, OutOfMemoryPolicy.ThrowException, PoolingPolicy.UnpooledHeap, new GetDirectBuffer() },
            { LeakDetectionPolicy.Paranoid, OutOfMemoryPolicy.ThrowException, PoolingPolicy.UnpooledHeap, new GetDirectBuffer() },
            { LeakDetectionPolicy.Disabled, OutOfMemoryPolicy.FallbackToHeap, PoolingPolicy.UnpooledHeap, new GetDirectBuffer() },
            { LeakDetectionPolicy.Simple, OutOfMemoryPolicy.FallbackToHeap, PoolingPolicy.UnpooledHeap, new GetDirectBuffer() },
            { LeakDetectionPolicy.Advanced, OutOfMemoryPolicy.FallbackToHeap, PoolingPolicy.UnpooledHeap, new GetDirectBuffer() },
            { LeakDetectionPolicy.Paranoid, OutOfMemoryPolicy.FallbackToHeap, PoolingPolicy.UnpooledHeap, new GetDirectBuffer() },
            { LeakDetectionPolicy.Disabled, OutOfMemoryPolicy.ThrowException, PoolingPolicy.PooledDirect, new GetHeapBuffer() },
            { LeakDetectionPolicy.Simple, OutOfMemoryPolicy.ThrowException, PoolingPolicy.PooledDirect, new GetHeapBuffer() },
            { LeakDetectionPolicy.Advanced, OutOfMemoryPolicy.ThrowException, PoolingPolicy.PooledDirect, new GetHeapBuffer() },
            { LeakDetectionPolicy.Paranoid, OutOfMemoryPolicy.ThrowException, PoolingPolicy.PooledDirect, new GetHeapBuffer() },
            { LeakDetectionPolicy.Disabled, OutOfMemoryPolicy.FallbackToHeap, PoolingPolicy.PooledDirect, new GetHeapBuffer() },
            { LeakDetectionPolicy.Simple, OutOfMemoryPolicy.FallbackToHeap, PoolingPolicy.PooledDirect, new GetHeapBuffer() },
            { LeakDetectionPolicy.Advanced, OutOfMemoryPolicy.FallbackToHeap, PoolingPolicy.PooledDirect, new GetHeapBuffer() },
            { LeakDetectionPolicy.Paranoid, OutOfMemoryPolicy.FallbackToHeap, PoolingPolicy.PooledDirect, new GetHeapBuffer() },
            { LeakDetectionPolicy.Disabled, OutOfMemoryPolicy.ThrowException, PoolingPolicy.UnpooledHeap, new GetHeapBuffer() },
            { LeakDetectionPolicy.Simple, OutOfMemoryPolicy.ThrowException, PoolingPolicy.UnpooledHeap, new GetHeapBuffer() },
            { LeakDetectionPolicy.Advanced, OutOfMemoryPolicy.ThrowException, PoolingPolicy.UnpooledHeap, new GetHeapBuffer() },
            { LeakDetectionPolicy.Paranoid, OutOfMemoryPolicy.ThrowException, PoolingPolicy.UnpooledHeap, new GetHeapBuffer() },
            { LeakDetectionPolicy.Disabled, OutOfMemoryPolicy.FallbackToHeap, PoolingPolicy.UnpooledHeap, new GetHeapBuffer() },
            { LeakDetectionPolicy.Simple, OutOfMemoryPolicy.FallbackToHeap, PoolingPolicy.UnpooledHeap, new GetHeapBuffer() },
            { LeakDetectionPolicy.Advanced, OutOfMemoryPolicy.FallbackToHeap, PoolingPolicy.UnpooledHeap, new GetHeapBuffer() },
            { LeakDetectionPolicy.Paranoid, OutOfMemoryPolicy.FallbackToHeap, PoolingPolicy.UnpooledHeap, new GetHeapBuffer() },
        });
    }

    private ByteBuf basicBuf;
    private ByteBuf basicMinSizedBuf;
    private ByteBuf basicMinSizedMaxCapBuf;

    @Test
    public void testBufsProps() {
        assertEquals(100, basicMinSizedBuf.capacity());
        assertEquals(100, basicMinSizedMaxCapBuf.capacity());

        assertEquals(Integer.MAX_VALUE, basicMinSizedBuf.maxCapacity());
        assertEquals(Integer.MAX_VALUE, basicBuf.maxCapacity());
        assertEquals(300, basicMinSizedMaxCapBuf.maxCapacity());
        
        assertEquals(false, basicBuf.isReadable());
        assertEquals(false, basicMinSizedBuf.isReadable());
        assertEquals(false, basicMinSizedMaxCapBuf.isReadable());

        assertEquals(false, basicBuf.isReadOnly());
        assertEquals(false, basicMinSizedBuf.isReadOnly());
        assertEquals(false, basicMinSizedMaxCapBuf.isReadOnly());

    }

    @Test
    public void testBufsWrites() {
        assertEquals(true, checkWrites(basicBuf, basicBuf.capacity() + 10));
        assertEquals(true, checkWrites(basicMinSizedBuf, basicMinSizedBuf.capacity() + 10));
        assertEquals(true, checkWrites(basicMinSizedMaxCapBuf, basicMinSizedMaxCapBuf.capacity()));
        assertEquals(true, checkWrites(basicMinSizedMaxCapBuf, 100));
        assertThrows(Throwable.class, () -> checkWrites(basicMinSizedMaxCapBuf, 101));

        assertEquals(basicMinSizedMaxCapBuf.capacity(), basicMinSizedMaxCapBuf.maxCapacity());

        basicBuf.clear();
        basicMinSizedBuf.clear();
        basicMinSizedMaxCapBuf.clear();
        
        assertEquals(false, basicBuf.isReadable());
        assertEquals(false, basicMinSizedBuf.isReadable());
        assertEquals(false, basicMinSizedMaxCapBuf.isReadable());
    }

    @Test
    public void testReadOnlyBufs() {
        ByteBuf roBuf = basicBuf.asReadOnly();
        ByteBuf roMinSizedBuf = basicMinSizedBuf.asReadOnly();
        ByteBuf roMinSizedMaxCapBuf = basicMinSizedMaxCapBuf.asReadOnly();

        roBuf.clear();
        roMinSizedBuf.clear();
        roMinSizedMaxCapBuf.clear();

        assertEquals(false, roBuf.isReadable());
        assertEquals(false, roMinSizedBuf.isReadable());
        assertEquals(false, roMinSizedMaxCapBuf.isReadable());
        
        assertEquals(true, roBuf.isReadOnly());
        assertEquals(true, roMinSizedBuf.isReadOnly());
        assertEquals(true, roMinSizedMaxCapBuf.isReadOnly());

        assertThrows(Throwable.class, () -> checkWrites(roBuf, roBuf.capacity() + 10));
        assertThrows(Throwable.class, () -> checkWrites(roMinSizedBuf, roMinSizedBuf.capacity() + 10));
        assertThrows(Throwable.class, () -> checkWrites(roMinSizedMaxCapBuf, roMinSizedMaxCapBuf.capacity()));
        assertThrows(Throwable.class, () -> checkWrites(roMinSizedMaxCapBuf, 100));
        assertThrows(Throwable.class, () -> checkWrites(roMinSizedMaxCapBuf, 101));
    }
    
    @Test
    public void testRoBufsReads() {
        basicBuf.clear();
        basicMinSizedBuf.clear();
        
        assertEquals(false, basicBuf.isReadable());
        assertEquals(false, basicMinSizedBuf.isReadable());

        assertEquals(true, checkWrites(basicBuf, basicBuf.capacity() + 10));
        assertEquals(true, checkWrites(basicMinSizedBuf, basicMinSizedBuf.capacity() + 10));

        ByteBuf roBuf = basicBuf.asReadOnly();
        ByteBuf roMinSizedBuf = basicMinSizedBuf.asReadOnly();

        roBuf.setIndex(0, roBuf.writerIndex());
        roMinSizedBuf.setIndex(0, roMinSizedBuf.writerIndex());

        assertEquals(true, roBuf.isReadable());
        assertEquals(true, roMinSizedBuf.isReadable());

        assertEquals(true, checkReadsOnly(roBuf, roBuf.readableBytes()));
        assertEquals(true, checkReadsOnly(roMinSizedBuf, roMinSizedBuf.readableBytes()));

        basicBuf.clear();
        basicMinSizedBuf.clear();
        
        assertEquals(false, basicBuf.isReadable());
        assertEquals(false, basicMinSizedBuf.isReadable());
    }

    private boolean checkWrites(ByteBuf buf, int maxWrites) {
        int i = 1;
        while(i <= maxWrites) {
            buf.writeByte((byte) i);
            ++i;
        }
        
        i = 1;
        while(i <= maxWrites) {
            if((byte) i != buf.readByte()) {
                return false;
            }
            ++i;
        }

        return true;
    }

    private boolean checkReadsOnly(ByteBuf roBuf, int maxReads) {
        int i = 1;
        while(i <= maxReads) {
            if((byte) i != roBuf.readByte()) {
                return false;
            }
            ++i;
        }

        return true;
    }
}
