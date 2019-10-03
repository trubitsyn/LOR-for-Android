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

package dev.trubitsyn.lorforandroid.util

import android.content.Context
import android.net.ConnectivityManager

object NetworkUtils {
    fun isMobileData(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return if (activeNetwork != null) {
            activeNetwork.type == ConnectivityManager.TYPE_MOBILE
        } else false
    }
}
