package com.parenting.attendance.data.repository.base;

import android.content.Context;

import com.parenting.attendance.data.cache.base.AbstractObservableBaseCache;
import com.parenting.attendance.data.cache.base.BaseCache;
import com.parenting.attendance.data.net.RestApi;
import com.parenting.attendance.data.repository.datasource.base.AbstractObservableCachedDatastore;
import com.parenting.attendance.data.repository.datasource.base.AbstractObservableCloudDatastore;
import com.parenting.attendance.data.repository.datasource.base.Datastore;
import com.parenting.attendance.data.repository.datasource.base.ObservableDatastore;
import com.parenting.attendance.data.repository.datasource.factory.base.AbstractObservableBaseFactory;
import com.parenting.attendance.domain.repository.Cacheble;
import com.parenting.attendance.domain.repository.ObservableRepository;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * Created by adwait on 29/06/17.
 */

public abstract class AbstractObservableRepository<T extends Cacheble> implements ObservableRepository<T> {

    public static final String INSERT_TO_CLOUD = "insertToCloud: ";
    public static final String UPDATE_TO_CLOUD = "updateToCloud: ";
    public static final String GET_FROM_CLOUD = "getFromCloud: ";
    public static final String DELETE_FROM_CLOUD = "deleteFromCloud: ";


    public static final String INSERT_LOCAL = "insertLocal: ";
    public static final String UPDATE_LOCAL = "updateLocal: ";
    public static final String GET_LOCAL = "getLocal: ";
    public static final String DELETE_LOCAL = "deleteLocal: ";
    private final PassThroughDatastore passthroughDatastore;
    private String name;

    protected enum SOURCE{
        BOTH(0),
        CLOUD_ONLY(1),
        LOCAL_ONLY(2),
        BOTH_PASS_THROUGH(3),
        INVALID(-1);

        int id;
        SOURCE(int id){
            this.id =id;
        }

        public static SOURCE fromId(int id){
            for (SOURCE f : values()) {
                if (f.id == id) return f;
            }
            return INVALID;
        }
    }

    protected RestApi mRestApi;
    protected Context mContext;
    protected Factory mFactory;
    protected FactoryWrapper mWrapper;
    protected Cache mCache;
    protected LocalDatastore localDatastore;
    protected CloudDatastore cloudDatastore;
    /**
     * Wrapper for @link Factory class adds functionality to pre-select datastore source
     * based on exposed methods in the AbstractBaseRepository class
     * */
    protected class FactoryWrapper{
        private Factory factoryInstance;

        public FactoryWrapper(Factory factoryInstance) {
            this.factoryInstance = factoryInstance;
        }

        public ObservableDatastore<T> create(SOURCE source){
            switch (source){
                case CLOUD_ONLY:
                    return factoryInstance.createCloudDatastore();
                case LOCAL_ONLY:
                    return factoryInstance.createLocalDatastore();
                case BOTH_PASS_THROUGH:
                    return passthroughDatastore;
                case INVALID:
                case BOTH:
                default:
                    return factoryInstance.create();
            }
        }
    }

    public AbstractObservableRepository(Context context, RestApi restApi) {
        this.name = getClass().getSimpleName();
        this.mRestApi = restApi;
        this.mContext = context;
        localDatastore = new LocalDatastore(mContext);
        mCache = new Cache(mContext,localDatastore,getClass().getSimpleName());
        cloudDatastore = new CloudDatastore(mContext, this.mRestApi,localDatastore,mCache);
        passthroughDatastore = new PassThroughDatastore(mContext,cloudDatastore,localDatastore);
        this.mFactory = new Factory(mContext);
        this.mWrapper = new FactoryWrapper(mFactory);
    }

    @Override
    public boolean insert(T model, Map<String, String> params) throws Exception {
        return mWrapper.create(insertSource()).insert(model, params);
    }

    @Override
    public boolean update(T model, Map<String, String> params) throws Exception {
        return mWrapper.create(updateSource()).update(model, params);
    }

    @Override
    public Observable<T> get(Object id, Map<String, String> params) throws Exception {
        return mWrapper.create(fetchSource()).get(id,params);
    }

    @Override
    public boolean delete(T model, Map<String, String> params) throws Exception {
        return mWrapper.create(deleteSource()).delete(model, params);
    }

    /**
     *  datastore implementation for pass through cache implementation,this approach hits the api each time while returning a local copy
     * */
    protected class PassThroughDatastore implements ObservableDatastore<T> {
        private AbstractObservableCachedDatastore<T> localDatastore;
        ObservableDatastore<T> cloudDatastore;
        public PassThroughDatastore(Context context,ObservableDatastore<T> cloudDatastore,AbstractObservableCachedDatastore<T> localDatastore) {
            this.cloudDatastore = cloudDatastore;
            this.localDatastore = localDatastore;
        }

        @Override
        public boolean insert(T model, Map<String, String> params) throws Exception {
            try {
                cloudDatastore.insert(model, params);
            } catch (Exception e) {
            }
            return localDatastore.insert(model, params);
        }

        @Override
        public boolean update(T model, Map<String, String> params) throws Exception {
            try {
                cloudDatastore.update(model, params);
            } catch (Exception e) {
            }
            return localDatastore.update(model, params);
        }

        @Override
        public Observable<T> get(Object id, Map<String, String> params) throws Exception {
            try {
                cloudDatastore.get(((String)id),params);
            } catch (Exception e) {
            }
            return localDatastore.get(((String)id),params);
        }

        @Override
        public boolean delete(T model, Map<String, String> params) throws Exception {
            try {
                cloudDatastore.delete(model, params);
            } catch (Exception e) {
            }
            return localDatastore.delete(model, params);
        }
    }

    /**
     * Local datastore implementation which utilizes exposed methods in AbstractBaseRepository
     * to allow implementations in sub-classes to be invoked
     * */
    private class LocalDatastore extends AbstractObservableCachedDatastore<T> {

        public LocalDatastore(Context context) {
            super(context);
        }

        @Override
        protected Observable<T> getStoredValueFor(String s, Map<String, String> params) throws Exception {
            return getLocal(s,params);
        }

        @Override
        protected boolean insertOnStorage(T model) throws Exception {
            return insertLocal(model);
        }

        @Override
        protected boolean updateOnStorage(T model) throws Exception {
            return updateLocal(model);
        }

        @Override
        protected boolean deleteOnStorage(T model) throws Exception {
            return deleteLocal(model);
        }
    }

    /**
     * Cloud datastore implementation which utilizes exposed methods in AbstractBaseRepository
     * to allow implementations in sub-classes to be invoked
     * */
    private class CloudDatastore extends AbstractObservableCloudDatastore<T> {

        public CloudDatastore(Context mContext, RestApi mRestApi, ObservableDatastore<T> localDatastore, BaseCache mCache) {
            super(mContext, mRestApi, localDatastore, mCache);
        }

        @Override
        public boolean insert(T model, Map<String, String> params) throws Exception {
            return insertToCloud(model, params);
        }

        @Override
        public boolean update(T model, Map<String, String> params) throws Exception {
            return updateToCloud(model, params);
        }

        @Override
        public Observable<T> get(Object id, Map<String, String> params) throws Exception {
            Observable<T> ret;
            ret = getFromCloud(id,params);
            ret.subscribeWith(new Observer<T>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(T value) {
                    if (value!=null) {
                        try {
                            localDatastore.insert(value, params);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        mCache.setLastCacheUpdateTimeMillis();
                    }
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            });

            return ret;
        }

        @Override
        public boolean delete(T model, Map<String, String> params) throws Exception {
            return deleteFromCloud(model, params);
        }
    }
    /**
     * Cache implementation for AbstractBaseRepository
     *
     * */
    private class Cache extends AbstractObservableBaseCache<AbstractObservableCachedDatastore<T>> {

        public Cache(Context context, AbstractObservableCachedDatastore<T> databaseDatastore, String lastCacheUpdate) {
            super(context, databaseDatastore, lastCacheUpdate);
        }

        @Override
        protected String getPreferanceName() {
            return name;
        }
    }
    /**
     * Factory implementation for AbstractBaseRepository
     *
     * */
    protected class Factory extends AbstractObservableBaseFactory<ObservableDatastore<T>,AbstractObservableBaseCache<AbstractObservableCachedDatastore<T>>> {

        public Factory(Context context) {
            super(context);
        }

        @Override
        public ObservableDatastore<T> createCloudDatastore() {
            return AbstractObservableRepository.this.cloudDatastore;
        }

        @Override
        public ObservableDatastore<T> createLocalDatastore() {
            return AbstractObservableRepository.this.localDatastore;
        }

        @Override
        protected AbstractObservableBaseCache<AbstractObservableCachedDatastore<T>> createCache() {
            return AbstractObservableRepository.this.mCache;
        }
    }
    /**
     * Override this method in subclass to pre-set datastore preference for insert operation,
     * default implementation will allow invocation from either cloud or local based on cache criteria without any bias
     * */
    protected SOURCE insertSource(){return SOURCE.BOTH_PASS_THROUGH;}
    /**
     * Override this method in subclass to pre-set datastore preference for update operation,
     * default implementation will allow invocation from either cloud or local based on cache criteria without any bias
     * */
    protected SOURCE updateSource(){return SOURCE.BOTH_PASS_THROUGH;}
    /**
     * Override this method in subclass to pre-set datastore preference for delete operation,
     * default implementation will allow invocation from either cloud or local based on cache criteria without any bias
     * */
    protected SOURCE deleteSource(){return SOURCE.BOTH_PASS_THROUGH;}
    /**
     * Override this method in subclass to pre-set datastore preference for get operation,
     * default implementation will allow invocation from either cloud or local based on cache criteria without any bias
     * */
    protected SOURCE fetchSource(){return SOURCE.BOTH_PASS_THROUGH;}

    protected boolean insertToCloud(T model, Map<String, String> params) throws Exception{
        throw new UnsupportedOperationException(INSERT_TO_CLOUD+ Datastore.UNSUPPORTED_MESSAGE);
    }

    protected boolean updateToCloud(T model, Map<String, String> params) throws Exception{
        throw new UnsupportedOperationException(UPDATE_TO_CLOUD+ Datastore.UNSUPPORTED_MESSAGE);
    }

    protected Observable<T> getFromCloud(Object id, Map<String, String> params) throws Exception{
        throw new UnsupportedOperationException(GET_FROM_CLOUD+ Datastore.UNSUPPORTED_MESSAGE);
    }

    protected boolean deleteFromCloud(T model, Map<String, String> params) throws Exception{
        throw new UnsupportedOperationException(DELETE_FROM_CLOUD+ Datastore.UNSUPPORTED_MESSAGE);
    }

    protected boolean insertLocal(T model) throws Exception{
        throw new UnsupportedOperationException(INSERT_LOCAL+ Datastore.UNSUPPORTED_MESSAGE);
    }

    protected boolean updateLocal(T model) throws Exception{
        throw new UnsupportedOperationException(UPDATE_LOCAL+ Datastore.UNSUPPORTED_MESSAGE);
    }

    protected Observable<T> getLocal(Object id, Map<String, String> params) throws Exception{
        throw new UnsupportedOperationException(GET_LOCAL+ Datastore.UNSUPPORTED_MESSAGE);
    }

    protected boolean deleteLocal(T model) throws Exception{
        throw new UnsupportedOperationException(UPDATE_LOCAL+ Datastore.UNSUPPORTED_MESSAGE);
    }

    /**
     * this method will forcefully clear the cache forcing a network call as well as clear the local datastore mem - cache*/
    public void forceExpire() {
        mCache.forceExpire();
        localDatastore.evictAll();
    }
}
