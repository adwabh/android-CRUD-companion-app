package com.parenting.attendance.data.repository.datasource.base;

import android.content.Context;

import com.parenting.attendance.data.cache.base.BaseCache;
import com.parenting.attendance.domain.repository.Cacheble;
import com.parenting.attendance.data.net.RestApi;


/**
 * Created by adwait on 29/06/17.
 */

public abstract class AbstractCloudDatastore<T extends Cacheble> implements Datastore<T> {

    protected   Context mContext;
    protected RestApi mRestApi;
    protected  Datastore<T> localDatastore;
    protected BaseCache mCache;

    public AbstractCloudDatastore(Context mContext, RestApi mRestApi, Datastore<T> localDatastore, BaseCache mCache) {
        this.mContext = mContext;
        this.mRestApi = mRestApi;
        this.localDatastore = localDatastore;
        this.mCache = mCache;
    }


}