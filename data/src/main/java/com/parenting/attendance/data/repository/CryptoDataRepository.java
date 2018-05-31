/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.parenting.attendance.data.repository;

import com.parenting.attendance.data.entity.mapper.PriceEntityDataMapper;
import com.parenting.attendance.data.repository.datasource.CryptoDataStore;
import com.parenting.attendance.data.repository.datasource.CryptoDataStoreFactory;
import com.parenting.attendance.domain.models.Price;
import com.parenting.attendance.domain.repository.CryptoRepository;
import com.parenting.attendance.domain.repository.UserRepository;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * {@link UserRepository} for retrieving user data.
 */
@Singleton
public class CryptoDataRepository implements CryptoRepository {

  private final CryptoDataStoreFactory cryptoDataStoreFactory;
  private PriceEntityDataMapper priceEntityDataMapper;

  /**
   * Constructs a {@link UserRepository}.
   * @param dataStoreFactory A factory to construct different data source implementations.
   * @param priceEntityDataMapper
   *
   */
  @Inject
  CryptoDataRepository(CryptoDataStoreFactory dataStoreFactory, PriceEntityDataMapper priceEntityDataMapper) {
    this.cryptoDataStoreFactory = dataStoreFactory;
    this.priceEntityDataMapper = priceEntityDataMapper;
  }

  @Override
  public Observable<List<Price>> cryptoPriceEntityList() {
    return null;
  }

  @Override
  public Observable<Price> getPriceDataForCrypto(Map<String, String> params) {
    final CryptoDataStore cryptoDataStore = this.cryptoDataStoreFactory.create();
    return cryptoDataStore.getPriceDataForCrypto(params).map(this.priceEntityDataMapper::transform);
  }

 /* @Override public Observable<List<User>> users() {
    //we always get all users from the cloud
    final CryptoDataStore cryptoDataStore = this.cryptoDataStoreFactory.createCloudDataStore();
    return cryptoDataStore.userEntityList().map(this.userEntityDataMapper::transform);
  }

  @Override public Observable<User> user(int userId) {
    final UserDataStore userDataStore = this.cryptoDataStoreFactory.create(userId);
    return userDataStore.userEntityDetails(userId).map(this.userEntityDataMapper::transform);
  }*/
}
