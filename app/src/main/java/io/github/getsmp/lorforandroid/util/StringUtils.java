/*
 * Copyright 2016 getsmp
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

package io.github.getsmp.lorforandroid.util;

import android.text.Spanned;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

public class StringUtils {
    private static final StringBuilder sb = new StringBuilder();

    /*
    * Removes part of url after topic id
    * @param url /forum/general/12336213/page1?lastmod=1454852134842
    * @return /forum/general/12336213
    * */
    public static String removeParams(String url) {
        if (url.split("/").length > 3) {
            return url.substring(0, url.lastIndexOf("/"));
        } else return url.split("\\?")[0];
    }

    public static String tagsFromStrings(List<String> in) {
        resetStringBuilder();
        for (String s : in) {
            sb.append("#").append(s).append(" ");
        }
        return sb.toString();
    }

    public static String tagsFromElements(Elements e) {
        resetStringBuilder();
        for (Element tag : e) {
            sb.append("#").append(tag.ownText()).append(" ");
        }
        return sb.toString();
    }

    private static void resetStringBuilder() {
        sb.setLength(0);
        sb.trimToSize();
    }

    public static String removeSectionName(String in) {
        return in.substring(in.indexOf("-") + 2, in.length());
    }

    public static String numericStringToHumanReadable(String commentsCount) {
        if (commentsCount.equals("-")) {
            return "Нет комментариев";
        }

        int parsed = 0;
        try {
            parsed = Integer.parseInt(commentsCount.substring(commentsCount.length() - 1));
        } catch (NumberFormatException ignored) {}

        switch (parsed) {
            case 1:
                return commentsCount + " комментарий";
            case 2:
            case 3:
            case 4:
                return commentsCount + " комментария";
            default:
                return commentsCount + " комментариев";
        }
    }

    public static CharSequence removeLineBreak(Spanned spanned) {
        return spanned.subSequence(0, spanned.length() - 2);
    }

    public static boolean isClub(String url) {
        return url.contains("club");
    }
}
