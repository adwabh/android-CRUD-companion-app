package com.adwait.crud.data.repository;

import android.content.Context;

import com.adwait.crud.data.entity.mapper.DomainModelConverter;
import com.adwait.crud.data.net.RestApi;
import com.adwait.crud.data.repository.base.AbstractObservableRepository;
import com.adwait.crud.domain.models.SampleUserListResponse;
import com.adwait.crud.domain.repository.SampleUserListRepository;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by adwait on 21/03/18.
 */

public class SampleUserListRepositoryImpl extends AbstractObservableRepository<SampleUserListResponse> implements SampleUserListRepository {

    private DomainModelConverter domainModelConverter;

    @Inject
    public SampleUserListRepositoryImpl(Context context, RestApi restApi, DomainModelConverter domainModelConverter) {
        super(context, restApi);
        this.domainModelConverter = domainModelConverter;
    }

    @Override
    protected SOURCE fetchSource() {
        return SOURCE.CLOUD_ONLY;
    }

    @Override
    protected Observable<SampleUserListResponse> getFromCloud(Object id, Map<String, String> params) throws Exception {

        return mRestApi.getSampleUserList(params).map(domainModelConverter::convert);
    }

    @Override
    protected Observable<Boolean> insertLocal(SampleUserListResponse model) throws Exception {
        return Observable.just(true);
    }

    @Override
    protected Observable<Boolean> insertToCloud(SampleUserListResponse model, Map<String, String> params) throws Exception {
        return mRestApi.addSampleUser(params);
    }

    @Override
    protected Observable<Boolean> updateToCloud(SampleUserListResponse model, Map<String, String> params) throws Exception {
        return mRestApi.updateSampleUser(params);
    }

    @Override
    protected Observable<Boolean> deleteFromCloud(SampleUserListResponse model, Map<String, String> params) throws Exception {
        return mRestApi.deleteSampleUser(params);
    }
}
