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
package com.adwait.crud.data.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import com.adwait.crud.data.entity.CommonEntity;
import com.adwait.crud.data.entity.SampleUserListEntity;
import com.adwait.crud.data.exception.NetworkConnectionException;
import com.adwait.crud.domain.Constant;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
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

    /**
     * Constructor of the class
     *  @param context {@link Context}.
     *
     */
    @Inject
    public RestApiImpl(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("The constructor parameters cannot be null!!!");
        }
        this.context = context.getApplicationContext();
    }


    @Override
    public void deleteSampleUserSync(Map<String, String> params) throws IOException {
        String id = params.get(PARAMS.ID);
        Call<CommonEntity> response = RestClient.getService(CRUDService.class).deleteSampleUserFromApi(id);
        Response<CommonEntity> res =  response.execute();
    }

    @Override
    public Observable<SampleUserListEntity> getSampleUserList(Map<String, String> params) {
        return Observable.create(emitter -> {
            if(isThereInternetConnection()){
                SampleUserListEntity sampleUserListEntity = getSampleUserListFromApi(params);
                if (sampleUserListEntity != null) {
                    emitter.onNext(sampleUserListEntity);
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
    public Observable<Boolean> addSampleUser(Map<String, String> params) {
        return Observable.create(emitter -> {
            if(isThereInternetConnection()){
                try {
                    Boolean addResult = addSampleUserToApi(params);
                    if (addResult != null) {
                        emitter.onNext(addResult);
                        emitter.onComplete();
                    } else {
                        emitter.onError(new NetworkConnectionException());
                    }
                } catch (Exception e){
                    e.printStackTrace();
                    emitter.onError(e);
                }
            } else {
                emitter.onError(new NetworkConnectionException());
            }
        });
    }

    private boolean addSampleUserToApi(Map<String, String> params) throws IOException {
        boolean ret = false;
        SampleUserListEntity.SampleUser entity = new SampleUserListEntity.SampleUser();
        entity.setUsername(params.get(PARAMS.USERNAME));
        entity.setEmail(params.get(PARAMS.EMAIL));
        Call<CommonEntity> request = RestClient.getService(CRUDService.class).addSampleUserToApi(entity);
        Response<CommonEntity> res =  request.execute();
        if(res.body()!=null){
            CommonEntity body = res.body();
            if(body.getResponseCode()!=null){
                if(!TextUtils.isEmpty(body.getResponseCode())){
                    if(TextUtils.equals(CommonEntity.ResponseCodes.OK,body.getResponseCode())){
                        ret = true;
                    }
                }
            }
        }
        return ret;
    }

    @Override
    public Observable<Boolean> updateSampleUser(Map<String, String> params) {
        return Observable.just(false);
    }

    @Override
    public Observable<Boolean> deleteSampleUser(Map<String, String> params) throws IOException {
        boolean ret = false;
        String id = params.get(PARAMS.ID);
        Call<CommonEntity> response = RestClient.getService(CRUDService.class).deleteSampleUserFromApi(id);
        Response<CommonEntity> res =  response.execute();
        if(res.body()!=null){
            CommonEntity body = res.body();
            if(body.getResponseCode()!=null){
                if(!TextUtils.isEmpty(body.getResponseCode())){
                    if(TextUtils.equals(CommonEntity.ResponseCodes.OK,body.getResponseCode())){
                        ret = true;
                    }
                }
            }
        }
        return Observable.just(ret);
    }



    private SampleUserListEntity getSampleUserListFromApi(Map<String, String> params) throws IOException {
        Response<SampleUserListEntity> res = null;
        Call<SampleUserListEntity> response = RestClient.getService(CRUDService.class).getSampleUserList(params);
        try {
            res = response.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
