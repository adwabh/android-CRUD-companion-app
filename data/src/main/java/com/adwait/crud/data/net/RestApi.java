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

import com.adwait.crud.data.entity.SampleUserListEntity;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * RestApi for retrieving data from the network.
 */
public interface RestApi {

    void deleteSampleUserSync(Map<String, String> stringStringMap) throws IOException;

    public class ParamsBuilder{

        public static Map<String, String> buildDeleteWorkerParams(int position) {
            Map<String, String> ret = new LinkedHashMap<>();
            ret.put(PARAMS.ID, String.valueOf(position));
            return ret;
        }
    }

    public interface PARAMS {
        public static final String FSYM = "fsym";
        public static final String TSYM = "tsyms";
        public static final String EXCHANGE = "e";
        String MOBILE = "mobile";
        String PASSWORD = "pwd";
        String USERNAME = "username";
        String EMAIL = "email";
        String ID = "id";
    }

    //  String API_BASE_URL = "https://raw.githubusercontent.com/android10/Sample-Data/master/Android-CleanArchitecture/";
    String API_BASE_URL = "https://shrouded-oasis-81019.herokuapp.com/";//"https://min-api.cryptocompare.com";

    /** Api url for getting all users */
    String API_URL_GET_USER_LIST = API_BASE_URL + "users.json";
    /** Api url for getting a user profile: Remember to concatenate id + 'json' */
    String API_URL_GET_USER_DETAILS = API_BASE_URL + "user_";

    Observable<SampleUserListEntity> getSampleUserList(Map<String, String> params);

    Observable<Boolean> addSampleUser(Map<String, String> params);

    Observable<Boolean> updateSampleUser(Map<String, String> params);

    Observable<Boolean> deleteSampleUser(Map<String, String> params) throws IOException;
}
