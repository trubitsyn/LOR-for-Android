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

package net.voxelplanet.lorforandroid.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class Adapter {
    private static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
    private static final String ROOT = "https://www.linux.org.ru";
    public static final RestAdapter directRestAdapter = new retrofit.RestAdapter.Builder()
            .setEndpoint(ROOT)
            .setLogLevel(RestAdapter.LogLevel.FULL)
            .build();
    private static final String API = ROOT + "/api";
    public static final RestAdapter restAdapter = new retrofit.RestAdapter.Builder()
            .setConverter(new GsonConverter(GSON))
            .setLogLevel(RestAdapter.LogLevel.FULL)
            .setEndpoint(API)
            .build();

    // TODO: Consider using Singleton for storing public variables
}
