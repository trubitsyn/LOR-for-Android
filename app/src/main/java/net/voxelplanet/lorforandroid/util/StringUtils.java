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
    public static String clearUrl(String url) {
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
}
