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

import io.github.getsmp.lorforandroid.ui.section.Item;

public class GalleryItem extends Item {
    private final String imageUrl;
    private final String medium2xImageUrl;
    private final String mediumImageUrl;

    public GalleryItem(String url, String title, String groupTitle, String tags, String date, String author, String comments, String imageUrl, String medium2xImageUrl, String mediumImageUrl) {
        super(url, title, groupTitle, tags, date, author, comments);
        this.imageUrl = imageUrl;
        this.medium2xImageUrl = medium2xImageUrl;
        this.mediumImageUrl = mediumImageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getMedium2xImageUrl() {
        return medium2xImageUrl;
    }

    public String getMediumImageUrl() {
        return mediumImageUrl;
    }
}
