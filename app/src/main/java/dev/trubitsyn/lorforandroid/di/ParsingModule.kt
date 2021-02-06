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
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.trubitsyn.lorforandroid.site.HtmlParser
import dev.trubitsyn.lorforandroid.site.JsoupParser
import dev.trubitsyn.lorforandroid.site.adapter.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ParsingModule {

    @Singleton
    @Provides
    fun provideHtmlParser(): HtmlParser {
        return JsoupParser.Builder()
                .registerDocumentAdapter(AbstractNewsItemDocumentAdapter())
                .registerDocumentAdapter(ForumItemDocumentAdapter())
                .registerDocumentAdapter(ForumSectionItemDocumentAdapter())
                .registerDocumentAdapter(GalleryItemDocumentAdapter())
                .registerDocumentAdapter(TrackerItemDocumentAdapter())
                .build()
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
                .create()
    }
}