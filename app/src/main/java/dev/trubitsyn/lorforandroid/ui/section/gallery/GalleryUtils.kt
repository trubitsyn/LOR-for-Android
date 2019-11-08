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

package dev.trubitsyn.lorforandroid.ui.section.gallery

import dev.trubitsyn.lorforandroid.Const
import dev.trubitsyn.lorforandroid.util.StringUtils

object GalleryUtils {
    fun isGalleryUrl(url: String) = url.startsWith("gallery")

    fun getGalleryImagesUrl(url: String): String {
        val clean = StringUtils.removeParams(url)
        return Const.SITE_ROOT + "gallery" + clean.substring(clean.lastIndexOf("/"))
    }

    fun getMedium2xImageUrl(imagesUrl: String) = "$imagesUrl-med-2x.jpg"

    fun getMediumImageUrl(imagesUrl: String) = "$imagesUrl-med.jpg"
}
