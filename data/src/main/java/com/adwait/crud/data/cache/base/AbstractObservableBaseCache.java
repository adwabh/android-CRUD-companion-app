package com.adwait.crud.data.cache.base;

import android.content.Context;

import com.adwait.crud.data.cache.FileManager;
import com.adwait.crud.data.repository.datasource.base.AbstractObservableCachedDatastore;

import javax.inject.Inject;


/**
 * Created by adwait on 20/03/17.
 */

public abstract class AbstractObservableBaseCache<T extends AbstractObservableCachedDatastore> implements BaseCache {

//    private static final String SETTINGS_FILE_NAME = "io.app.eatrepeat_preferences_skipuser";
    protected static final long EXPIRATION_TIME = 60 * 10 * 1000;
    private static final int MAX_SIZE = 100;
    private String SETTINGS_KEY_LAST_CACHE_UPDATE /*= RestaurantCache.class.getSimpleName() + "_update"*/;
    private static AbstractObservableBaseCache ourInstance;
    @Inject
    protected FileManager fileManager;
//    private LruCache<Long, Cacheble> memCache;
    private Context context;
    private T databaseDatastore;

    public AbstractObservableBaseCache(Context context, T databaseDatastore, String lastCacheUpdate) {
        this.context = context;
        this.databaseDatastore = databaseDatastore;
//        memCache = new LruCache<>(MAX_SIZE);

        SETTINGS_KEY_LAST_CACHE_UPDATE = lastCacheUpdate;
    }

//    public static AbstractBaseCache getInstance(Context context, AddSkipUserDatastore databaseDatastore, String lastCacheUpdate) {
////        if(ourInstance == null){
//        ourInstance = new AbstractBaseCache(context, databaseDatastore, lastCacheUpdate);
////        }
//        return new AbstractBaseCache(context, databaseDatastore, lastCacheUpdate);
//    }

    @Override
    public boolean isCached(long Id) throws Exception {
//        return isInMemCache(Id) || databaseDatastore.get(String.valueOf(Id)) != null;
        return databaseDatastore.get(String.valueOf(Id),null) != null;
    }

    @Override
    public boolean isExpired() {
        long currentTime = System.currentTimeMillis();
        long lastUpdateTime = this.getLastCacheUpdateTimeMillis();

        boolean expired = ((currentTime - lastUpdateTime) > EXPIRATION_TIME);

        if (expired) {
            this.evictAll();
        }
        return expired;
    }

    @Override
    public void evictAll() {
//        memCache.evictAll();
        databaseDatastore.evictAll();
    }

    @Override
    public void setLastCacheUpdateTimeMillis() {
        final long currentMillis = System.currentTimeMillis();
        this.fileManager.writeToPreferences(this.context, getPreferanceName(),
                SETTINGS_KEY_LAST_CACHE_UPDATE, currentMillis);
    }

    @Override
    public long getLastCacheUpdateTimeMillis() {
        return this.fileManager.getFromPreferences(this.context, getPreferanceName(),
                SETTINGS_KEY_LAST_CACHE_UPDATE);
    }

//    public void put(long key, Cacheble model) {
//        memCache.put(key, model);
//    }
//
//    public Cacheble get(long key) throws Exception {
//        Cacheble cached = memCache.get(key);
//        if (cached != null)
//            return cached;
//        cached = databaseDatastore.get(String.valueOf(key));
//        put(key, cached);
//        return cached;
//    }

//    private boolean isInMemCache(long Id) {
//        return memCache.get(Id) != null;
//    }


    protected abstract String getPreferanceName();

    public void forceExpire(){
        this.fileManager.writeToPreferences(this.context, getPreferanceName(),
                SETTINGS_KEY_LAST_CACHE_UPDATE, 0L);
    }
}



































