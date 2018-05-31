/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.parenting.attendance.data.repository.datasource;

import android.content.Context;
import android.support.annotation.NonNull;

import com.parenting.attendance.data.entity.mapper.UserEntityJsonMapper;
import com.parenting.attendance.data.net.RestApi;
import com.parenting.attendance.data.net.RestApiImpl;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Factory that creates different implementations of {@link UserDataStore}.
 */
@Singleton
public class CryptoDataStoreFactory {

  private final Context context;

  @Inject
  CryptoDataStoreFactory(@NonNull Context context) {
    this.context = context.getApplicationContext();
  }

  /**
   * Create {@link UserDataStore} from a user id.
   */
  public CryptoDataStore create() {
    CryptoDataStore cryptoDataStore;

//    if (!this.userCache.isExpired() && this.userCache.isCached(userId)) {
//      cryptoDataStore = new CryptoDiskDatastore();
//    } else {
      cryptoDataStore = createCloudDataStore();
//    }

    return cryptoDataStore;
  }

  /**
   * Create {@link UserDataStore} to retrieve data from the Cloud.
   */
  public CryptoDataStore createCloudDataStore() {
    final UserEntityJsonMapper userEntityJsonMapper = new UserEntityJsonMapper();
    final RestApi restApi = new RestApiImpl(this.context, userEntityJsonMapper);

    return new CryptoCloudDatastore(restApi);
  }
}
