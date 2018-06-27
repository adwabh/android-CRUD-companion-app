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
package com.adwait.crud.presentation.internal.di.components;

import android.content.Context;

import com.adwait.crud.data.net.RestApi;
import com.adwait.crud.domain.executor.PostExecutionThread;
import com.adwait.crud.domain.executor.ThreadExecutor;
import com.adwait.crud.domain.repository.SampleUserListRepository;
import com.adwait.crud.domain.repository.SingleSampleUserRepository;
import com.adwait.crud.presentation.internal.di.modules.ApplicationModule;
import com.adwait.crud.presentation.navigation.Navigator;

import javax.inject.Singleton;

import dagger.Component;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
//  void inject(BaseActivity baseActivity);

  //Exposed to sub-graphs.
  Context context();
  ThreadExecutor threadExecutor();
  PostExecutionThread postExecutionThread();
  Navigator navigator();
  SampleUserListRepository sampleuserListRepository();
  SingleSampleUserRepository singleSampleUserRepository();
  RestApi restApi();
}
