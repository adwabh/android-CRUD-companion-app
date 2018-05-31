package com.parenting.attendance.data.repository;

import android.content.Context;

import com.parenting.attendance.data.entity.mapper.DomainModelConverter;
import com.parenting.attendance.data.net.RestApi;
import com.parenting.attendance.data.repository.base.AbstractObservableRepository;
import com.parenting.attendance.domain.models.LoginResponse;
import com.parenting.attendance.domain.repository.LoginRepository;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by adwait on 21/03/18.
 */

public class LoginRepositoryImpl extends AbstractObservableRepository<LoginResponse> implements LoginRepository {

    private DomainModelConverter domainModelConverter;

    @Inject
    public LoginRepositoryImpl(Context context, RestApi restApi, DomainModelConverter domainModelConverter) {
        super(context, restApi);
        this.domainModelConverter = domainModelConverter;
    }

    @Override
    protected SOURCE fetchSource() {
        return SOURCE.CLOUD_ONLY;
    }

    @Override
    protected Observable<LoginResponse> getFromCloud(Object id, Map<String, String> params) throws Exception {
        return mRestApi.doLogin(params).map(domainModelConverter::convert);
    }

    @Override
    protected boolean insertLocal(LoginResponse model) throws Exception {
        return true;
    }

}
