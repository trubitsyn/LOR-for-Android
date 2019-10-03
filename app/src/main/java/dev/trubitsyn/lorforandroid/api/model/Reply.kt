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

package dev.trubitsyn.lorforandroid.api.model

import com.google.gson.annotations.Expose
import java.util.*

class Reply {
    @Expose
    val id: Int? = null
    @Expose
    val author: String? = null
    @Expose
    val postdate: Date? = null
    @Expose
    val samePage: Boolean? = null

}
