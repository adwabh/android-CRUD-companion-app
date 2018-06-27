package com.adwait.crud.data.repository.datasource.base;

import android.content.Context;

import com.adwait.crud.data.cache.base.BaseCache;
import com.adwait.crud.data.net.RestApi;
import com.adwait.crud.domain.repository.Cacheble;


/**
 * Created by adwait on 29/06/17.
 */

public abstract class AbstractObservableCloudDatastore<T extends Cacheble> implements ObservableDatastore<T> {

    protected   Context mContext;
    protected RestApi mRestApi;
    protected  ObservableDatastore<T> localDatastore;
    protected BaseCache mCache;

    public AbstractObservableCloudDatastore(Context mContext, RestApi mRestApi, ObservableDatastore<T> localDatastore, BaseCache mCache) {
        this.mContext = mContext;
        this.mRestApi = mRestApi;
        this.localDatastore = localDatastore;
        this.mCache = mCache;
    }
}
