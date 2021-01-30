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

package dev.trubitsyn.lorforandroid.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.trubitsyn.lorforandroid.site.HtmlConverterFactory
import dev.trubitsyn.lorforandroid.site.HtmlParser
import dev.trubitsyn.lorforandroid.site.SiteApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ParsingRetrofit

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideSiteApi(@ParsingRetrofit retrofit: Retrofit): SiteApi {
        return retrofit.create(SiteApi::class.java)
    }

    @ApiRetrofit
    @Provides
    fun provideApiRetrofit(client: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
                .baseUrl(SITE_ROOT + "api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
    }

    @ParsingRetrofit
    @Provides
    fun provideParsingRetrofit(client: OkHttpClient, parser: HtmlParser): Retrofit {
        return Retrofit.Builder()
                .baseUrl(SITE_ROOT)
                .addConverterFactory(HtmlConverterFactory.create(parser))
                .client(client)
                .build()
    }

    @Provides
    fun provideHttpClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
    }

    @Provides
    fun provideLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }

    companion object {
        const val SITE_ROOT = "https://www.linux.org.ru/"
    }
}