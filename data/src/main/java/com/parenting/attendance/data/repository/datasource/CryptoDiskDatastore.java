package com.parenting.attendance.data.repository.datasource;

import com.parenting.attendance.data.entity.PriceEntity;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by adwait on 27/09/17.
 */

public class CryptoDiskDatastore implements CryptoDataStore {
    @Override
    public Observable<List<PriceEntity>> cryptoPriceEntityList() {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Observable<PriceEntity> getPriceDataForCrypto(Map<String, String> params) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }
}
