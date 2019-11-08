/*
 * Copyright (C) 2015-2019 Nikola Trubitsyn
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
import android.text.Spanned
import dev.trubitsyn.lorforandroid.R
import org.jsoup.select.Elements

object StringUtils {
    private val sb = StringBuilder()

    /*
    * Removes part of url after topic id
    * @param url /forum/general/12336213/page1?lastmod=1454852134842
    * @return /forum/general/12336213
    * */
    fun removeParams(url: String): String {
        return if (url.split("/".toRegex()).dropLastWhile(String::isEmpty).size > 3) {
            url.substring(0, url.lastIndexOf("/"))
        } else
            url.split("\\?".toRegex()).dropLastWhile(String::isEmpty)[0]
    }

    fun tagsFromStrings(strings: List<String>): String {
        strings.forEach {
            sb.append("#").append(it).append(" ")
        }
        sb.toString().let {
            resetStringBuilder()
            return it
        }
    }

    fun tagsFromElements(e: Elements): String {
        e.forEach {
            sb.append("#").append(it.ownText()).append(" ")
        }
        sb.toString().let {
            resetStringBuilder()
            return it
        }
    }

    private fun resetStringBuilder() {
        sb.setLength(0)
        sb.trimToSize()
    }

    fun removeSectionName(s: String) = s.substring(s.indexOf("—") + 2, s.length)

    fun readableCommentsCount(context: Context, count: String): String {
        val quantity = when (count) {
            "-" -> 0
            else -> count.substring(count.length - 1).toIntOrNull() ?: 0
        }
        return context.resources.getQuantityString(R.plurals.comments, quantity, quantity)
    }

    fun removeLineBreak(spanned: Spanned) = spanned.subSequence(0, spanned.length - 2)

    fun isClub(url: String) = url.contains("club")
}
