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

package dev.trubitsyn.lorforandroid.api

import com.google.gson.GsonBuilder
import dev.trubitsyn.lorforandroid.Const
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

enum class ApiManager {
    INSTANCE;

    private val GSON = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create()
    private val apiRestAdapter = Retrofit.Builder()
            .baseUrl(Const.SITE_ROOT + "api/")
            .addConverterFactory(GsonConverterFactory.create(GSON))
            .build()

    internal val apiComments: ApiComments by lazy { apiRestAdapter.create(ApiComments::class.java) }
    internal val apiTopic: ApiTopic by lazy { apiRestAdapter.create(ApiTopic::class.java) }
}