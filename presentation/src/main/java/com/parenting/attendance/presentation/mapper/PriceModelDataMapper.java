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
package com.parenting.attendance.presentation.mapper;

import com.parenting.attendance.domain.models.Price;
import com.parenting.attendance.domain.models.User;
import com.parenting.attendance.presentation.internal.di.PerActivity;
import com.parenting.attendance.presentation.model.PriceModel;
import com.parenting.attendance.presentation.model.UserModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

/**
 * Mapper class used to transform {@link Price} (in the domain layer) to {@link PriceModel} in the
 * presentation layer.
 */
@PerActivity
public class PriceModelDataMapper {

  @Inject
  public PriceModelDataMapper() {}

  /**
   * Transform a {@link Price} into an {@link PriceModel}.
   *
   * @param price Object to be transformed.
   * @return {@link PriceModel}.
   */
  public PriceModel transform(Price price) {
    if (price == null) {
      throw new IllegalArgumentException("Cannot transform a null value");
    }
    final PriceModel priceModel = new PriceModel();
    priceModel.setUSD(price.getUSD());
    priceModel.setINR(price.getINR());
    return priceModel;
  }

  /**
   * Transform a Collection of {@link User} into a Collection of {@link UserModel}.
   *
   * @param priceCollection Objects to be transformed.
   * @return List of {@link UserModel}.
   */
  public Collection<PriceModel> transform(Collection<Price> priceCollection) {
    Collection<PriceModel> priceModelCollection;

    if (priceCollection != null && !priceCollection.isEmpty()) {
      priceModelCollection = new ArrayList<>();
      for (Price price : priceCollection) {
        priceModelCollection.add(transform(price));
      }
    } else {
      priceModelCollection = Collections.emptyList();
    }

    return priceModelCollection;
  }
}
