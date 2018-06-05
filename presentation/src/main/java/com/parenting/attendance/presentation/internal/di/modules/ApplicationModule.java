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
package com.parenting.attendance.presentation.internal.di.modules;

import android.content.Context;

import com.parenting.attendance.data.cache.UserCache;
import com.parenting.attendance.data.cache.UserCacheImpl;
import com.parenting.attendance.data.entity.mapper.DomainModelConverter;
import com.parenting.attendance.data.entity.mapper.UserEntityJsonMapper;
import com.parenting.attendance.data.executor.JobExecutor;
import com.parenting.attendance.data.net.RestApi;
import com.parenting.attendance.data.net.RestApiImpl;
import com.parenting.attendance.data.repository.CryptoDataRepository;
import com.parenting.attendance.data.repository.CryptoListRepositoryImpl;
import com.parenting.attendance.data.repository.LoginRepositoryImpl;
import com.parenting.attendance.data.repository.UserDataRepository;
import com.parenting.attendance.data.repository.VerificationRepositoryImpl;
import com.parenting.attendance.domain.executor.PostExecutionThread;
import com.parenting.attendance.domain.executor.ThreadExecutor;
import com.parenting.attendance.domain.repository.CryptoListRepository;
import com.parenting.attendance.domain.repository.CryptoRepository;
import com.parenting.attendance.domain.repository.LoginRepository;
import com.parenting.attendance.domain.repository.UserRepository;
import com.parenting.attendance.domain.repository.VerificationRepository;
import com.parenting.attendance.presentation.AndroidApplication;
import com.parenting.attendance.presentation.UIThread;
import com.parenting.attendance.presentation.mapper.PresentationModelConverter;
import com.parenting.attendance.presentation.navigation.Navigator;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class ApplicationModule {
  private final AndroidApplication application;

  public ApplicationModule(AndroidApplication application) {
    this.application = application;
  }

  @Provides @Singleton Context provideApplicationContext() {
    return this.application;
  }

  @Provides @Singleton ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
    return jobExecutor;
  }

  @Provides @Singleton PostExecutionThread providePostExecutionThread(UIThread uiThread) {
    return uiThread;
  }

  @Provides @Singleton
  UserCache provideUserCache(UserCacheImpl userCache) {
    return userCache;
  }

  @Provides @Singleton UserRepository provideUserRepository(UserDataRepository userDataRepository) {
    return userDataRepository;
  }

  @Provides @Singleton
  CryptoRepository providesCryptoRepository(CryptoDataRepository cryptoRepo){
    return cryptoRepo;
  }

  @Provides @Singleton
  CryptoListRepository providesCryptoListRepository(CryptoListRepositoryImpl cryptoListRepository){ return cryptoListRepository; }

  @Provides @Singleton
  VerificationRepository providesVerificationRepository(VerificationRepositoryImpl verificationRepository){ return verificationRepository; }

  @Provides @Singleton
  LoginRepository providesLoginRepository(LoginRepositoryImpl loginRepository){ return loginRepository; }

  @Provides @Singleton
  RestApi providesRestApi(){
    return new RestApiImpl(application,new UserEntityJsonMapper());
  }

  @Provides @Singleton
  DomainModelConverter providesDomainModelConverter(){ return new DomainModelConverter(); }

  @Provides @Singleton
  PresentationModelConverter providesPresentationModelConverter(){ return new PresentationModelConverter(); }

  @Provides @Singleton
  Navigator providesNavigator(){ return new Navigator();}
}
