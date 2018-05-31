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
package com.parenting.attendance.data.entity.mapper;

import com.parenting.attendance.data.entity.PriceEntity;
import com.parenting.attendance.data.entity.UserEntity;
import com.parenting.attendance.domain.models.Price;
import com.parenting.attendance.domain.models.User;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Mapper class used to transform {@link UserEntity} (in the data layer) to {@link User} in the
 * domain layer.
 */
@Singleton
public class PriceEntityDataMapper {

  @Inject
  PriceEntityDataMapper() {}

  /**
   * Transform a {@link PriceEntity} into an {@link Price}.
   *
   * @param priceEntity Object to be transformed.
   * @return {@link Price} if valid {@link UserEntity} otherwise null.
   */
  public Price transform(PriceEntity priceEntity) {
    Price price = null;
    if (priceEntity != null) {
      price = new Price();
      price.setINR(priceEntity.getINR());
      price.setUSD(priceEntity.getUSD());
    }
    return price;
  }

  /**
   * Transform a List of {@link UserEntity} into a Collection of {@link User}.
   *
   * @param userEntityCollection Object Collection to be transformed.
   * @return {@link User} if valid {@link UserEntity} otherwise null.
   */
  /*public List<User> transform(Collection<UserEntity> userEntityCollection) {
    final List<User> userList = new ArrayList<>(20);
    for (UserEntity userEntity : userEntityCollection) {
      final User user = transform(userEntity);
      if (user != null) {
        userList.add(user);
      }
    }
    return userList;
  }*/
}
