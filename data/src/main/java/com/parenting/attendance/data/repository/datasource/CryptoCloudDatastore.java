package com.parenting.attendance.data.repository.datasource;

import com.parenting.attendance.data.entity.PriceEntity;
import com.parenting.attendance.data.net.RestApi;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by adwait on 26/09/17.
 */

public class CryptoCloudDatastore implements CryptoDataStore {
    private final RestApi restApi;

    public CryptoCloudDatastore(RestApi restApi) {
        this.restApi = restApi;
    }

    @Override
    public Observable<List<PriceEntity>> cryptoPriceEntityList() {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Observable<PriceEntity> getPriceDataForCrypto(Map<String, String> params) {
        return restApi.getPriceDataForCrypto(params);
    }
}
