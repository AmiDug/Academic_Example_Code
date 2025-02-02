package io.collective;

import java.time.Clock;
import java.util.HashMap;

public class SimpleAgedCache {

    private final HashMap <Object, ExpirableEntry> cache = new HashMap();
    private final Clock clock;

    public SimpleAgedCache(Clock clock) {
        this.clock = clock;
    }

    public SimpleAgedCache() {
        this.clock = Clock.systemDefaultZone();
    }

    public void put(Object key, Object value, int retentionInMillis) {
        if(0 < retentionInMillis) {
            long expiration = retentionInMillis + clock.millis();
            ExpirableEntry entry = new ExpirableEntry(value, expiration);
            cache.put(key, entry);
        }
    }

    public boolean isEmpty() {
        return cache.isEmpty();
    }

    public int size() {
        cacheCleaning();
        return cache.size();
    }

    public Object get(Object key) {
        ExpirableEntry expirable = cache.get(key);
        if(expirable != null) {
            return expirable.obj;
        }
        return null;
    }

    private void cacheCleaning(){
        cache.entrySet().removeIf(x -> x.getValue().expiration());
    }

    private class ExpirableEntry
    {
        private final long expiry;
        final Object obj;

        ExpirableEntry(Object obj, long expiry) {
            this.expiry = expiry;
            this.obj = obj;
        }

        boolean expiration(){
            return expiry < clock.millis();
        }
    }
}