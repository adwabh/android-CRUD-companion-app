package com.parenting.attendance.data.repository.datasource.base;

import android.content.Context;
import android.util.LruCache;

import com.parenting.attendance.domain.repository.Cacheble;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * Created by adwait on 08/06/17.
 */

public abstract class AbstractObservableCachedDatastore<T extends Cacheble> implements ObservableDatastore<T>,Observer<T> {
    protected Context mContext;
    private LruCache<String, T> memCache;
    private T cached;

    public AbstractObservableCachedDatastore(Context context) {
        mContext = context;
        memCache = new LruCache<>(100);
    }

    public AbstractObservableCachedDatastore(Context context, int cacheSize) {
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
    public Observable<T> get(Object id, Map<String, String> params) throws Exception {
        String key = (String)id;
        cached = memCache.get(key);
        if (cached != null) {
            return Observable.just(cached);
        }
        Observable<T> ret = getStoredValueFor(String.valueOf(key),params);

        ret.subscribeWith(this);

        return Observable.just(cached);
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T value) {
        cached = value;
        memCache.put(value.key(), cached);
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }

    public void evictAll(){
        memCache.evictAll();
    }

    public void remove(String key){
        memCache.remove(key);
    }

    protected abstract Observable<T> getStoredValueFor(String s, Map<String, String> params) throws Exception;

    protected abstract boolean insertOnStorage(T model) throws Exception;

    protected abstract boolean updateOnStorage(T model) throws Exception;

    protected abstract boolean deleteOnStorage(T model) throws Exception;

}
