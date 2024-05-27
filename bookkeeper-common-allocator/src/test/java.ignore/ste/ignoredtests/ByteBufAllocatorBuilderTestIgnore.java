package ste.ignoredtests;

import static org.junit.Assert.assertEquals;

import java.util.function.Consumer;

import org.apache.bookkeeper.common.allocator.ByteBufAllocatorBuilder;
import org.apache.bookkeeper.common.allocator.ByteBufAllocatorWithOomHandler;
import org.apache.bookkeeper.common.allocator.LeakDetectionPolicy;
import org.apache.bookkeeper.common.allocator.OutOfMemoryPolicy;
import org.apache.bookkeeper.common.allocator.PoolingPolicy;
import org.junit.Test;

public class ByteBufAllocatorBuilderTestIgnore {
    @Test
    public void testMinimalBuilder() {
        ByteBufAllocatorBuilder builder = ByteBufAllocatorBuilder.create();
        builder
            .leakDetectionPolicy(LeakDetectionPolicy.Disabled)
            .outOfMemoryPolicy(OutOfMemoryPolicy.ThrowException)
            .poolingPolicy(PoolingPolicy.PooledDirect);
        
        ByteBufAllocatorWithOomHandler allocator = builder.build();
        assertEquals(true, allocator.isDirectBufferPooled());
    }    
    
    @Test
    public void testUnpooledBuilder() {
        ByteBufAllocatorBuilder builder = ByteBufAllocatorBuilder.create();
        builder
            .leakDetectionPolicy(LeakDetectionPolicy.Disabled)
            .outOfMemoryPolicy(OutOfMemoryPolicy.ThrowException)
            .poolingPolicy(PoolingPolicy.UnpooledHeap);
        
        ByteBufAllocatorWithOomHandler allocator = builder.build();
        assertEquals(false, allocator.isDirectBufferPooled());
    }

    @Test
    public void testMostStrictLeakDetectionPolicyBuilder() {
        ByteBufAllocatorBuilder builder = ByteBufAllocatorBuilder.create();
        builder
            .leakDetectionPolicy(LeakDetectionPolicy.Paranoid)
            .outOfMemoryPolicy(OutOfMemoryPolicy.ThrowException)
            .poolingPolicy(PoolingPolicy.UnpooledHeap);
        
        ByteBufAllocatorWithOomHandler allocator = builder.build();
        assertEquals(false, allocator.isDirectBufferPooled());
    }
    
    @Test
    public void testOomHeapFallbackPolicyBuilder() {
        ByteBufAllocatorBuilder builder = ByteBufAllocatorBuilder.create();
        builder
            .leakDetectionPolicy(LeakDetectionPolicy.Paranoid)
            .outOfMemoryPolicy(OutOfMemoryPolicy.FallbackToHeap)
            .poolingPolicy(PoolingPolicy.UnpooledHeap);
        
        ByteBufAllocatorWithOomHandler allocator = builder.build();
        assertEquals(false, allocator.isDirectBufferPooled());
    }
    
    @Test
    public void testOomListenerBuilder() {
        ByteBufAllocatorBuilder builder = ByteBufAllocatorBuilder.create();
        builder
            .leakDetectionPolicy(LeakDetectionPolicy.Paranoid)
            .outOfMemoryPolicy(OutOfMemoryPolicy.FallbackToHeap)
            .poolingPolicy(PoolingPolicy.UnpooledHeap)
            .outOfMemoryListener(new Consumer<OutOfMemoryError>() {
                @Override
                public void accept(OutOfMemoryError t) {

                }
            });
        
        ByteBufAllocatorWithOomHandler allocator = builder.build();
        assertEquals(false, allocator.isDirectBufferPooled());
    }
}
