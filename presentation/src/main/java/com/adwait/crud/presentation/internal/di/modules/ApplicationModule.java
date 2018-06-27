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
package com.adwait.crud.presentation.internal.di.modules;

import android.content.Context;

import com.adwait.crud.data.entity.mapper.DomainModelConverter;
import com.adwait.crud.data.executor.JobExecutor;
import com.adwait.crud.data.net.RestApi;
import com.adwait.crud.data.net.RestApiImpl;
import com.adwait.crud.data.repository.SampleUserListRepositoryImpl;
import com.adwait.crud.data.repository.SingleSampleUserRepositoryImpl;
import com.adwait.crud.domain.executor.PostExecutionThread;
import com.adwait.crud.domain.executor.ThreadExecutor;
import com.adwait.crud.domain.repository.SampleUserListRepository;
import com.adwait.crud.domain.repository.SingleSampleUserRepository;
import com.adwait.crud.presentation.AndroidApplication;
import com.adwait.crud.presentation.UIThread;
import com.adwait.crud.presentation.mapper.PresentationModelConverter;
import com.adwait.crud.presentation.navigation.Navigator;

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
  SampleUserListRepository providesSampleUserListRepository(SampleUserListRepositoryImpl sampleUserListRepository){ return sampleUserListRepository; }

  @Provides @Singleton
  SingleSampleUserRepository providesSingleSampleUserRepository(SingleSampleUserRepositoryImpl singleSampleUserRepository){ return singleSampleUserRepository; }

  @Provides @Singleton
  RestApi providesRestApi(){
    return new RestApiImpl(application);
  }

  @Provides @Singleton
  DomainModelConverter providesDomainModelConverter(){ return new DomainModelConverter(); }

  @Provides @Singleton
  PresentationModelConverter providesPresentationModelConverter(){ return new PresentationModelConverter(); }

  @Provides @Singleton
  Navigator providesNavigator(){ return new Navigator();}
}
