package com.parenting.attendance.data.repository.datasource.factory.base;

import android.content.Context;

import com.parenting.attendance.data.cache.base.BaseCache;
import com.parenting.attendance.data.repository.datasource.base.ObservableDatastore;


/**
 * Created by adwait on 29/06/17.
 */

public abstract class AbstractObservableBaseFactory<T extends ObservableDatastore,C extends BaseCache> {
    protected Context mContext;
    protected C mCache;
    protected T localDatastore;
    protected T cloudDatastore;

    public AbstractObservableBaseFactory(Context context) {
        this.mContext = context;
        localDatastore = createLocalDatastore();
        mCache = createCache();
        cloudDatastore = createCloudDatastore();
    }

    public T create() {
        if (!mCache.isExpired()) {
            return localDatastore;
        } else {
            return cloudDatastore;
        }
    }

    public abstract T createCloudDatastore();

    public abstract T createLocalDatastore();

    protected abstract C createCache();
}
