/*
 * Copyright 2015 getsmp
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.voxelplanet.lorforandroid.util;

import android.text.Spanned;

import net.voxelplanet.lorforandroid.ui.section.SectionEnum;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StringUtils {
    public static String getDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.US);
        return simpleDateFormat.format(date);
    }

    public static String getCurrentDate() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date());
    }

    @Deprecated
    public static String fixUrl(String url) {
        int length = url.length() - url.replace("/", "").length();
        if (length > 3) {
            return url.substring(0, url.lastIndexOf("/"));
        } else if (url.contains("?")) {
            return url.split("\\?")[0];
        }
        return url;
    }

    public static CharSequence removeLineBreak(Spanned spanned) {
        return spanned.subSequence(0, spanned.length() - 2);
    }

    @Deprecated
    public static boolean isGallery(String url) {
        return url.contains(SectionEnum.gallery.name());
    }

    @Deprecated
    public static String getImageUrl(String url, String size) {
        // Size can be either "med" or "med-2x"
        String fixed = fixUrl(url);
        if (isGallery(fixed)) {
            return "https://linux.org.ru/" + SectionEnum.gallery.name() + fixed.substring(fixed.lastIndexOf("/")) + "-" + size + ".jpg";
        }
        return null;
    }
}
