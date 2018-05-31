package com.parenting.attendance.data.repository;

import android.content.Context;

import com.parenting.attendance.data.entity.mapper.DomainModelConverter;
import com.parenting.attendance.data.net.RestApi;
import com.parenting.attendance.data.repository.base.AbstractBaseRepository;
import com.parenting.attendance.data.repository.base.AbstractObservableRepository;
import com.parenting.attendance.domain.models.VerificationResponse;
import com.parenting.attendance.domain.repository.VerificationRepository;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by adwait on 19/03/18.
 */

public class VerificationRepositoryImpl extends AbstractObservableRepository<VerificationResponse> implements VerificationRepository{

    private DomainModelConverter mDomainConverter;

    @Inject
    public VerificationRepositoryImpl(Context context, RestApi restApi, DomainModelConverter domainConverter) {
        super(context, restApi);
        this.mDomainConverter = domainConverter;
    }

    @Override
    protected SOURCE fetchSource() {
        return SOURCE.CLOUD_ONLY;
    }

    @Override
    protected Observable<VerificationResponse> getFromCloud(Object id, Map<String, String> params) throws Exception {
        return mRestApi.getVerification(params).map(mDomainConverter::convert);
    }

    @Override
    protected boolean insertLocal(VerificationResponse model) throws Exception {
        return true;
    }
}
