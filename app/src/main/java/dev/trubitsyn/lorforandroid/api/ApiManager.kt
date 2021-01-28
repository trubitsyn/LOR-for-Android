/*
 * Copyright (C) 2015-2021 Nikola Trubitsyn (getsmp)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package dev.trubitsyn.lorforandroid.api

import com.google.gson.GsonBuilder
import dev.trubitsyn.lorforandroid.Const
import dev.trubitsyn.lorforandroid.site.JsoupConverter
import dev.trubitsyn.lorforandroid.site.SiteApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

enum class ApiManager {
    INSTANCE;

    private val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
            .create()

    private val apiRestAdapter = Retrofit.Builder()
            .baseUrl(Const.SITE_ROOT + "api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    private val siteRestAdapter = Retrofit.Builder()
            .baseUrl(Const.SITE_ROOT)
            .addConverterFactory(JsoupConverter.FACTORY)
            .build()

    internal val siteApi: SiteApi by lazy { siteRestAdapter.create(SiteApi::class.java) }
}
