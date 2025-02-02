package io.collective;

import java.time.Clock;
import java.util.HashMap;

//The SimpleAgedCache class contains a Hashmap containing a custom object known as ExpirableEntry that can track and remove objects that expire
public class SimpleAgedCache {

    private final HashMap <Object, ExpirableEntry> cache = new HashMap();
    private final Clock clock;

    public SimpleAgedCache(Clock clock) {
        this.clock = clock;
    }

    public SimpleAgedCache() {
        this.clock = Clock.systemDefaultZone();
    }

    // Takes a key value pair and how long it should be retained and enters a key and a custom object into the cache HashMap
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

    //Retrieves an object from the cache by associating it with a key parameter
    public Object get(Object key) {
        ExpirableEntry expirable = cache.get(key);
        if(expirable != null) {
            return expirable.obj;
        }
        return null;
    }

    //Removes cache entries that are past their expiration
    private void cacheCleaning(){
        cache.entrySet().removeIf(x -> x.getValue().expiration());
    }

    //Custom object that contains the value part of a key/value chain but adds expiration tracking
    private class ExpirableEntry{
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