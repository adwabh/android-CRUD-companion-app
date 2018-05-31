package com.parenting.attendance.data.repository;

import android.content.Context;

import com.parenting.attendance.data.net.RestApi;
import com.parenting.attendance.data.repository.base.AbstractObservableRepository;
import com.parenting.attendance.domain.models.CryptoList;
import com.parenting.attendance.data.entity.mapper.DomainModelConverter;
import com.parenting.attendance.domain.repository.CryptoListRepository;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by adwait on 20/11/17.
 */
@Singleton
public class CryptoListRepositoryImpl extends AbstractObservableRepository<CryptoList> implements CryptoListRepository {

    private DomainModelConverter mDomainConverter;

    @Inject
    public CryptoListRepositoryImpl(Context context, RestApi restApi, DomainModelConverter domainConverter) {
        super(context, restApi);
        this.mDomainConverter = domainConverter;
    }

    @Override
    protected SOURCE fetchSource() {
        return SOURCE.CLOUD_ONLY;
    }

    @Override
    protected Observable<CryptoList> getFromCloud(Object id, Map<String, String> params) throws Exception {
        return mRestApi.getCurryncyList(params).map(mDomainConverter::convert);
    }

    @Override
    protected boolean insertLocal(CryptoList model) throws Exception {
        return true;
    }
}


