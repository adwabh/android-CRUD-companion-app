package com.adwait.crud.data.repository.datasource.base;

import android.content.Context;
import android.util.LruCache;

import com.adwait.crud.domain.repository.Cacheble;

import java.util.Map;


/**
 * Created by adwait on 08/06/17.
 */

public abstract class AbstractCachedDatastore<T extends Cacheble> implements Datastore<T> {
    protected Context mContext;
    private LruCache<String, T> memCache;

    public AbstractCachedDatastore(Context context) {
        mContext = context;
        memCache = new LruCache<>(100);
    }

    public AbstractCachedDatastore(Context context,int cacheSize) {
        mContext = context;
        memCache = new LruCache<>(cacheSize);
    }

    @Override
    public boolean insert(T model, Map<String, String> params) throws Exception {
            memCache.put(model.key(),model);
        return insertOnStorage(model);
    }

    @Override
    public boolean update(T model, Map<String, String> params) throws Exception {
        memCache.put(model.key(),model);
        return updateOnStorage(model);
    }

    @Override
    public boolean delete(T model, Map<String, String> params) throws Exception {
        memCache.remove(model.key());
        return deleteOnStorage(model);
    }

    @Override
    public T get(Object id, Map<String, String> params) throws Exception {
        String key = (String)id;
        T cached = memCache.get(key);
        if (cached != null) {
            return cached;
        }
        cached = getStoredValueFor(String.valueOf(key),params);
        memCache.put(key, cached);
        return cached;
    }

    public void evictAll(){
        memCache.evictAll();
    }

    public void remove(String key){
        memCache.remove(key);
    }

    protected abstract T getStoredValueFor(String s, Map<String, String> params) throws Exception;

    protected abstract boolean insertOnStorage(T model) throws Exception;

    protected abstract boolean updateOnStorage(T model) throws Exception;

    protected abstract boolean deleteOnStorage(T model) throws Exception;

}
