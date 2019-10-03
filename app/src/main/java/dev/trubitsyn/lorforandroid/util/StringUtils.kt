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

import android.text.Spanned

import org.jsoup.nodes.Element
import org.jsoup.select.Elements

object StringUtils {
    private val sb = StringBuilder()

    /*
    * Removes part of url after topic id
    * @param url /forum/general/12336213/page1?lastmod=1454852134842
    * @return /forum/general/12336213
    * */
    fun removeParams(url: String): String {
        return if (url.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray().size > 3) {
            url.substring(0, url.lastIndexOf("/"))
        } else
            url.split("\\?".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
    }

    fun tagsFromStrings(`in`: List<String>): String {
        for (s in `in`) {
            sb.append("#").append(s).append(" ")
        }
        val s = sb.toString()
        resetStringBuilder()
        return s
    }

    fun tagsFromElements(e: Elements): String {
        for (tag in e) {
            sb.append("#").append(tag.ownText()).append(" ")
        }
        val s = sb.toString()
        resetStringBuilder()
        return s
    }

    private fun resetStringBuilder() {
        sb.setLength(0)
        sb.trimToSize()
    }

    fun removeSectionName(`in`: String): String {
        return `in`.substring(`in`.indexOf("-") + 2, `in`.length)
    }

    fun numericStringToHumanReadable(commentsCount: String): String {
        if (commentsCount == "-") {
            return "Нет комментариев"
        }

        var parsed = 0
        try {
            parsed = Integer.parseInt(commentsCount.substring(commentsCount.length - 1))
        } catch (ignored: NumberFormatException) {
        }

        when (parsed) {
            1 -> return "$commentsCount комментарий"
            2, 3, 4 -> return "$commentsCount комментария"
            else -> return "$commentsCount комментариев"
        }
    }

    fun removeLineBreak(spanned: Spanned): CharSequence {
        return spanned.subSequence(0, spanned.length - 2)
    }

    fun isClub(url: String): Boolean {
        return url.contains("club")
    }
}
