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
package com.parenting.attendance.domain.interactor;

import com.fernandocejas.arrow.checks.Preconditions;
import com.parenting.attendance.domain.executor.PostExecutionThread;
import com.parenting.attendance.domain.executor.ThreadExecutor;
import com.parenting.attendance.domain.models.User;
import com.parenting.attendance.domain.models.VerificationResponse;
import com.parenting.attendance.domain.repository.Cacheble;
import com.parenting.attendance.domain.repository.UserRepository;
import com.parenting.attendance.domain.repository.VerificationRepository;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * This class is an implementation of {@link UseCase} that represents a use case for
 * retrieving data related to an specific {@link User}.
 */
public class GetPhoneVerification extends UseCase<VerificationResponse, Map<String,String>> {

  private final VerificationRepository verificationRepository;

  @Inject
  GetPhoneVerification(VerificationRepository verificationRepository, ThreadExecutor threadExecutor,
                       PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.verificationRepository = verificationRepository;
  }

  @Override Observable<VerificationResponse> buildUseCaseObservable(Map<String,String> params) {
    Preconditions.checkNotNull(params);
    Observable<VerificationResponse> observableResult = null;
    try {
      observableResult = this.verificationRepository.get(Cacheble.KEY_TEMP,params);
    } catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      return observableResult;
    }
  }

}
