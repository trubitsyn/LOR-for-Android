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
import android.content.SharedPreferences
import android.preference.PreferenceManager

import dev.trubitsyn.lorforandroid.R

object PreferenceUtils {
    fun shouldLoadImagesOnMobileData(context: Context): Boolean {
        return getPrefs(context).getBoolean(context.getString(R.string.pref_load_images), false)
    }

    fun shouldLoadImagesNow(context: Context): Boolean {
        return if (NetworkUtils.isMobileData(context)) {
            shouldLoadImagesOnMobileData(context)
        } else true
    }

    private fun getPrefs(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }
}