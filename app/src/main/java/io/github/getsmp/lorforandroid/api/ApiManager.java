/*
 * Copyright 2016 getsmp
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

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public enum ApiManager {
    INSTANCE;

    final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
    public final String ROOT = "https://www.linux.org.ru";
    final String API = ROOT + "/api/";

    private static ApiComments apiComments;
    private static ApiTopic apiTopic;

    private final Retrofit apiRestAdapter = new Retrofit.Builder()
            .baseUrl("https://www.linux.org.ru/api/")
            .addConverterFactory(GsonConverterFactory.create(GSON))
            .build();

    public ApiComments getApiComments() {
        if (apiComments == null) apiComments = apiRestAdapter.create(ApiComments.class);
        return apiComments;
    }

    public ApiTopic getApiTopic() {
        if (apiTopic == null) apiTopic = apiRestAdapter.create(ApiTopic.class);
        return apiTopic;
    }
}
