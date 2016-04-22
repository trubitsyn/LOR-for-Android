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

package io.github.getsmp.lorforandroid.ui.section.gallery;

import io.github.getsmp.lorforandroid.Const;
import io.github.getsmp.lorforandroid.util.StringUtils;

public class GalleryUtils {
    public static boolean isGalleryUrl(String url) {
        return url.startsWith("gallery");
    }

    public static String getGalleryImagesUrl(String url) {
        String clean = StringUtils.removeParams(url);
        return Const.SITE_ROOT + "gallery" + clean.substring(clean.lastIndexOf("/"));
    }

    public static String getMedium2xImageUrl(String imagesUrl) {
        return imagesUrl + "-med-2x.jpg";
    }

    public static String getMediumImageUrl(String imagesUrl) {
        return imagesUrl + "-med.jpg";
    }
}
