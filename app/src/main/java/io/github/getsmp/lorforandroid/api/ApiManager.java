/*
 * Copyright 2015 getsmp
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.getsmp.lorforandroid.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public enum ApiManager {
    API_MANAGER;

    final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
    final String ROOT = "https://www.linux.org.ru";
    final String API = ROOT + "/api";

    final RestAdapter apiRestAdapter = new retrofit.RestAdapter.Builder()
            .setConverter(new GsonConverter(GSON))
            .setEndpoint(API)
            .build();

    final RestAdapter rootRestAdapter = new retrofit.RestAdapter.Builder()
            .setEndpoint(ROOT)
            .setLogLevel(RestAdapter.LogLevel.FULL)
            .build();

    public ApiComments getApiComments() {
        return apiRestAdapter.create(ApiComments.class);
    }

    public ApiFilter getApiFilter() {
        return rootRestAdapter.create(ApiFilter.class);
    }

    public ApiNotifications getApiNotifications() {
        return rootRestAdapter.create(ApiNotifications.class);
    }

    public ApiTopic getApiTopic() {
        return apiRestAdapter.create(ApiTopic.class);
    }

    public ApiTracker getApiTracker() {
        return apiRestAdapter.create(ApiTracker.class);
    }

    public ApiUser getApiUser() {
        return apiRestAdapter.create(ApiUser.class);
    }
}
