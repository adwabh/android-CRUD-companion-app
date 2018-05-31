package com.parenting.attendance.data.repository.datasource.base;

import android.content.Context;

import com.parenting.attendance.data.cache.base.BaseCache;
import com.parenting.attendance.data.net.RestApi;
import com.parenting.attendance.domain.repository.Cacheble;


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
