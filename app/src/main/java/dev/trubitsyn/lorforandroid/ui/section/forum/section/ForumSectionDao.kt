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

package dev.trubitsyn.lorforandroid.ui.section.forum.section

import androidx.room.Dao
import androidx.room.Query
import dev.trubitsyn.lorforandroid.ui.base.BaseDao
import kotlinx.coroutines.flow.Flow

@Dao
interface ForumSectionDao : BaseDao<ForumSectionItem> {
    @Query("SELECT * FROM forumsectionitem WHERE id = :id")
    fun findById(id: Long): ForumSectionItem

    @Query("SELECT * FROM forumsectionitem")
    fun getAll(): Flow<List<ForumSectionItem>>
}