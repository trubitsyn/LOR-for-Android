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

package dev.trubitsyn.lorforandroid.ui.section.tracker

import androidx.annotation.StringDef

@Retention(AnnotationRetention.SOURCE)
@StringDef(
        TrackerFilter.ALL,
        TrackerFilter.MAIN,
        TrackerFilter.NO_TALKS,
        TrackerFilter.TECH
)
annotation class TrackerFilter {
    companion object {
        const val ALL = "all"
        const val MAIN = "main"
        const val NO_TALKS = "notalks"
        const val TECH = "tech"

        private val values = arrayOf(
                ALL,
                MAIN,
                NO_TALKS,
                TECH
        )

        operator fun get(i: Int) = values[i]

        val size = values.size
    }
}
