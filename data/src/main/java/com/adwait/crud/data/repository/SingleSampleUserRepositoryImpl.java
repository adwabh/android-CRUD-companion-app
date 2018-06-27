package com.adwait.crud.data.repository;

import android.content.Context;

import com.adwait.crud.data.net.RestApi;
import com.adwait.crud.data.repository.base.AbstractObservableRepository;
import com.adwait.crud.domain.models.SampleUserListResponse;
import com.adwait.crud.domain.repository.SingleSampleUserRepository;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;

public class SingleSampleUserRepositoryImpl extends AbstractObservableRepository<SampleUserListResponse.SampleUser> implements SingleSampleUserRepository {

    @Inject
    public SingleSampleUserRepositoryImpl(Context context, RestApi restApi) {
        super(context, restApi);
    }

    @Override
    protected SOURCE insertSource() {
        return SOURCE.CLOUD_ONLY;
    }

    @Override
    protected SOURCE updateSource() {
        return SOURCE.CLOUD_ONLY;
    }

    @Override
    protected SOURCE deleteSource() {
        return SOURCE.CLOUD_ONLY;
    }

    @Override
    protected Observable<Boolean> insertToCloud(SampleUserListResponse.SampleUser model, Map<String, String> params) throws Exception {
        return mRestApi.addSampleUser(params);
    }

    @Override
    protected Observable<Boolean> updateToCloud(SampleUserListResponse.SampleUser model, Map<String, String> params) throws Exception {
        return mRestApi.updateSampleUser(params);
    }

    @Override
    protected Observable<Boolean> deleteFromCloud(SampleUserListResponse.SampleUser model, Map<String, String> params) throws Exception {
        return mRestApi.deleteSampleUser(params);
    }
}
