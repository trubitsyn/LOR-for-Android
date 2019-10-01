/*
 * Copyright (C) 2015-2016 Nikola Trubitsyn
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package dev.trubitsyn.lorforandroid.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dev.trubitsyn.lorforandroid.Const;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public enum ApiManager {
    INSTANCE;

    private final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();

    private static ApiComments apiComments;
    private static ApiTopic apiTopic;

    private final Retrofit apiRestAdapter = new Retrofit.Builder()
            .baseUrl(Const.SITE_ROOT + "api/")
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
