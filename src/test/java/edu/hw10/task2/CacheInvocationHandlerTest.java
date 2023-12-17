package edu.hw10.task2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CacheInvocationHandlerTest {
    @Test
    @DisplayName("Testing CacheProxy with Disk Cache")
    public void cacheProxyTestWithDiskCache() {

        TestInterface proxy = new TestImpl();
        proxy = CacheProxy.create(proxy, TestInterface.class);
        Integer value = proxy.diskTest(10, true);
        for (int i = 0; i < 3; i++) {
            Integer newVal = proxy.diskTest(10, true);
            assertEquals(value, newVal);
        }
        assertEquals(1, proxy.getCounter());
    }

    @Test
    @DisplayName("Testing CacheProxy with In-Memory Cache")
    public void cacheProxyTestWithInMemoryCache() {
        TestInterface proxy = new TestImpl();
        proxy = CacheProxy.create(proxy, TestInterface.class);
        long value = proxy.mapTest(10);
        for (int i = 0; i < 3; i++) {
            long newVal = proxy.mapTest(10);
            assertEquals(value, newVal);
        }
        assertEquals(1, proxy.getCounter());
    }
}
