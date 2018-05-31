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

import com.parenting.attendance.data.entity.CryptoList;
import com.parenting.attendance.data.entity.LoginEntity;
import com.parenting.attendance.data.entity.PriceEntity;
import com.parenting.attendance.data.entity.UserEntity;
import com.parenting.attendance.data.entity.VerificationEntity;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * RestApi for retrieving data from the network.
 */
public interface RestApi {

    public interface PARAMS {
        public static final String FSYM = "fsym";
        public static final String TSYM = "tsyms";
        public static final String EXCHANGE = "e";
        String MOBILE = "mobile";
        String PASSWORD = "pwd";
    }

    //  String API_BASE_URL = "https://raw.githubusercontent.com/android10/Sample-Data/master/Android-CleanArchitecture/";
    String API_BASE_URL = "https://min-api.cryptocompare.com";

    /** Api url for getting all users */
    String API_URL_GET_USER_LIST = API_BASE_URL + "users.json";
    /** Api url for getting a user profile: Remember to concatenate id + 'json' */
    String API_URL_GET_USER_DETAILS = API_BASE_URL + "user_";

    /**
     * Retrieves an {@link Observable} which will emit a List of {@link UserEntity}.
     */
    Observable<List<UserEntity>> userEntityList();

    Observable<CryptoList> getCurryncyList(Map<String, String> params);

    /**
     * Retrieves an {@link Observable} which will emit a {@link UserEntity}.
     *
     * @param userId The user id used to get user data.
     */
    Observable<UserEntity> userEntityById(final int userId);

    Observable<PriceEntity> getPriceDataForCrypto(Map<String, String> params);

    Observable<VerificationEntity> getVerification(Map<String, String> params);

    Observable<LoginEntity> doLogin(Map<String, String> params);

}
