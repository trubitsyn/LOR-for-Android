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

package dev.trubitsyn.lorforandroid.ui.section.news

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import dev.trubitsyn.lorforandroid.AppDatabase
import dev.trubitsyn.lorforandroid.api.RemoteKey
import dev.trubitsyn.lorforandroid.api.RemoteKeyDao
import dev.trubitsyn.lorforandroid.site.SiteApi
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator @Inject constructor(
        private val query: String,
        private val api: SiteApi,
        private val database: AppDatabase,
        private val remoteKeyDao: RemoteKeyDao,
        private val newsDao: NewsDao
) : RemoteMediator<Int, NewsItem>() {
    override suspend fun load(
            loadType: LoadType,
            state: PagingState<Int, NewsItem>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.APPEND -> {
                    val remoteKey = database.withTransaction {
                        remoteKeyDao.remoteKeyByQuery(query)
                    }
                    if (remoteKey.nextKey == null) {
                        return MediatorResult.Success(
                                endOfPaginationReached = true
                        )
                    }
                    remoteKey.nextKey
                }
            }

            //val response = api.getNews()
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    remoteKeyDao.deleteByQuery(query)
                    //newsDao.deleteByQuery(query)
                }

                //remoteKeyDao.insertOrReplace(
                //        RemoteKey(query, response.nextKey)
                //)

                //dao.insertAll(response.)
            }
            MediatorResult.Success(
                    endOfPaginationReached = false//response.nextKey == null
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}