/*
 * Copyright (c) 2016 getsmp
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
        for (String s : in) {
            sb.append("#").append(s).append(" ");
        }
        String s = sb.toString();
        resetStringBuilder();
        return s;
    }

    public static String tagsFromElements(Elements e) {
        for (Element tag : e) {
            sb.append("#").append(tag.ownText()).append(" ");
        }
        String s = sb.toString();
        resetStringBuilder();
        return s;
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
