/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.parenting.attendance.data.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.parenting.attendance.data.entity.CryptoList;
import com.parenting.attendance.data.entity.LoginEntity;
import com.parenting.attendance.data.entity.PriceEntity;
import com.parenting.attendance.data.entity.UserEntity;
import com.parenting.attendance.data.entity.VerificationEntity;
import com.parenting.attendance.data.entity.mapper.UserEntityJsonMapper;
import com.parenting.attendance.data.exception.NetworkConnectionException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;

/**
 * {@link RestApi} implementation for retrieving data from the network.
 */
public class RestApiImpl implements RestApi {



    public enum REQUEST{
        INVALID(-1) {
            @Override
            public Map<String, String> toParams(String... params) {
                return null;
            }
        },
        PRICE(1) {
            @Override
            public Map<String, String> toParams(String... params) {
                Map<String,String> paramsmap = new HashMap<>();
                paramsmap.put(PARAMS.FSYM,params[0]);
                paramsmap.put(PARAMS.TSYM,params[1]);
                return paramsmap;
            }
        };

        private int id;
        REQUEST(int id){
            this.id = id;
        }
        public static REQUEST fromId(int id){
            for (REQUEST req:values()) {
                if(req.id == id){
                    return req;
                }
            }
            return INVALID;
        }

        public abstract Map<String, String> toParams(String... params);
    }

    private final Context context;
    private final UserEntityJsonMapper userEntityJsonMapper;

    /**
     * Constructor of the class
     *
     * @param context {@link android.content.Context}.
     * @param userEntityJsonMapper {@link UserEntityJsonMapper}.
     */
    @Inject
    public RestApiImpl(Context context, UserEntityJsonMapper userEntityJsonMapper) {
        if (context == null || userEntityJsonMapper == null) {
            throw new IllegalArgumentException("The constructor parameters cannot be null!!!");
        }
        this.context = context.getApplicationContext();
        this.userEntityJsonMapper = userEntityJsonMapper;
    }

    @Override
    public Observable<VerificationEntity> getVerification(Map<String, String> params) {
        return Observable.create(emitter -> {
            if(isThereInternetConnection()){

                VerificationEntity verificationEntity = doVerification(params);
                if (verificationEntity != null) {
                    emitter.onNext(verificationEntity);
                    emitter.onComplete();
                } else {
                    emitter.onError(new NetworkConnectionException());
                }

            } else {
                emitter.onError(new NetworkConnectionException());
            }
        });
    }

    @Override
    public Observable<LoginEntity> doLogin(Map<String, String> params) {
        return Observable.create(emitter -> {
            if(isThereInternetConnection()){

                LoginEntity loginEntity = getLogin(params);
                if (loginEntity != null) {
                    emitter.onNext(loginEntity);
                    emitter.onComplete();
                } else {
                    emitter.onError(new NetworkConnectionException());
                }

            } else {
                emitter.onError(new NetworkConnectionException());
            }
        });
    }

    private LoginEntity getLogin(Map<String, String> params) throws IOException {
        Call<LoginEntity> response = RestClient.getService(AttendanceService.class).getLogin(params);
        Response<LoginEntity> res =  response.execute();
        return res.body();
    }

    private VerificationEntity doVerification(Map<String, String> params) throws IOException {
        Call<VerificationEntity> response = RestClient.getService(AttendanceService.class).getVerification(params);
        Response<VerificationEntity> res =  response.execute();
        return res.body();
    }

    @Override
    public Observable<List<UserEntity>> userEntityList() {
        return Observable.create(emitter -> {
            if (isThereInternetConnection()) {
                try {
                    String responseUserEntities = getUserEntitiesFromApi();
                    if (responseUserEntities != null) {
                        emitter.onNext(userEntityJsonMapper.transformUserEntityCollection(
                                responseUserEntities));
                        emitter.onComplete();
                    } else {
                        emitter.onError(new NetworkConnectionException());
                    }
                } catch (Exception e) {
                    emitter.onError(new NetworkConnectionException(e.getCause()));
                }
            } else {
                emitter.onError(new NetworkConnectionException());
            }
        });
    }

    @Override
    public Observable<CryptoList> getCurryncyList(Map<String, String> params) {
        return Observable.create(emitter -> {
            if(isThereInternetConnection()){

                CryptoList responceCurrencyList = getCurrencyList(params);
                if (responceCurrencyList != null) {
                    emitter.onNext(responceCurrencyList);
                    emitter.onComplete();
                } else {
                    emitter.onError(new NetworkConnectionException());
                }

            } else {
                emitter.onError(new NetworkConnectionException());
            }
        });
    }

    private CryptoList getCurrencyList(Map<String, String> params) throws IOException {
        Call<CryptoList> response = RestClient.getService(BitTrackerService.class).getCurrencyList(params);
        Response<CryptoList> res =  response.execute();
        return res.body();
    }

    @Override
    public Observable<UserEntity> userEntityById(final int userId) {
        return Observable.create(emitter -> {
            if (isThereInternetConnection()) {
                try {
                    String responseUserDetails = getUserDetailsFromApi(userId);
                    if (responseUserDetails != null) {
                        emitter.onNext(userEntityJsonMapper.transformUserEntity(responseUserDetails));
                        emitter.onComplete();
                    } else {
                        emitter.onError(new NetworkConnectionException());
                    }
                } catch (Exception e) {
                    emitter.onError(new NetworkConnectionException(e.getCause()));
                }
            } else {
                emitter.onError(new NetworkConnectionException());
            }
        });
    }

    public Observable<PriceEntity> getPriceDataForCrypto(Map<String, String> params) {
        return Observable.create(emitter -> {
            if (isThereInternetConnection()) {
                try {
                    PriceEntity responseUserDetails = getPriceDetailsFromApi(params);
                    if (responseUserDetails != null) {
//                        PriceEntityJsonMapper priceEntitiyMapper = new PriceEntityJsonMapper();
                        emitter.onNext(responseUserDetails);
                        emitter.onComplete();
                    } else {
                        emitter.onError(new NetworkConnectionException());
                    }
                } catch (Exception e) {
                    emitter.onError(new NetworkConnectionException(e.getCause()));
                }
            } else {
                emitter.onError(new NetworkConnectionException());
            }
        });
    }

    private PriceEntity getPriceDetailsFromApi(Map<String, String> params) throws IOException {
        Call<PriceEntity> response = RestClient.getService(BitTrackerService.class).getPriceDetailsFromApi(params);
        Response<PriceEntity> res =  response.execute();
        return res.body();
    }

    private String getUserEntitiesFromApi() throws MalformedURLException {
        return ApiConnection.createGET(API_URL_GET_USER_LIST).requestSyncCall();
    }

    private String getUserDetailsFromApi(int userId) throws MalformedURLException {
        String apiUrl = API_URL_GET_USER_DETAILS + userId + ".json";
        return ApiConnection.createGET(apiUrl).requestSyncCall();
    }

    /**
     * Checks if the device has any active internet connection.
     *
     * @return true device with internet connection, otherwise false.
     */
    private boolean isThereInternetConnection() {
        boolean isConnected;

        ConnectivityManager connectivityManager =
                (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

        return isConnected;
    }
}
