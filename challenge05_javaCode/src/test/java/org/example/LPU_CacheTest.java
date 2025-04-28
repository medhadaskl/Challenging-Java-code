package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class LPU_CacheTest {

    private LPU_Cache<Integer, String> cache;

    @BeforeEach
    public void setUp() {
        cache = new LPU_Cache<>(3);
    }

    @Test
    public void testPutAndGet() {
        cache.put(1, "One");
        cache.put(2, "Two");
        cache.put(3, "Three");

        assertEquals("One", cache.get(1), "Value for key 1 should be 'One'");
        assertEquals("Two", cache.get(2), "Value for key 2 should be 'Two'");
        assertEquals("Three", cache.get(3), "Value for key 3 should be 'Three'");
    }

    @Test
    public void testEvictionPolicy() {
        cache.put(1, "One");
        cache.put(2, "Two");
        cache.put(3, "Three");

        // Adding a new entry should evict the least recently used (LRU) entry
        cache.put(4, "Four");

        // Key 1 should be evicted because it was the least recently used
        assertNull(cache.get(1), "Key 1 should be evicted from the cache");
        assertEquals("Two", cache.get(2), "Key 2 should still exist in the cache");
        assertEquals("Three", cache.get(3), "Key 3 should still exist in the cache");
        assertEquals("Four", cache.get(4), "Key 4 should be in the cache");
    }

    @Test
    public void testCacheCapacity() {
        cache.put(1, "One");
        cache.put(2, "Two");
        cache.put(3, "Three");

        cache.put(4, "Four");

        assertNull(cache.get(1), "Key 1 should be evicted as it was the oldest");
        assertNotNull(cache.get(2), "Key 2 should be in the cache");
        assertNotNull(cache.get(3), "Key 3 should be in the cache");
        assertNotNull(cache.get(4), "Key 4 should be in the cache");
    }

    @Test
    public void testGetNonExistingKey() {
        assertNull(cache.get(10), "Non-existing key should return null");
    }

    @Test
    public void testCacheSize() {
        cache.put(1, "One");
        cache.put(2, "Two");
        cache.put(3, "Three");

        assertEquals(3, cache.size(), "Cache size should be 3");
    }
}
