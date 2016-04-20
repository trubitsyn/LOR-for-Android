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

package io.github.getsmp.lorforandroid.ui.section;

public class Item {
    private final String url;
    private final String title;
    private final String groupTitle;
    private final String tags;
    private final String date;
    private final String author;
    private final String comments;

    public Item(String url, String title, String groupTitle, String tags, String date, String author, String comments) {
        this.url = url;
        this.title = title;
        this.groupTitle = groupTitle;
        this.date = date;
        this.comments = comments;
        this.tags = tags;
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getComments() {
        return comments;
    }

    public String getTags() {
        return tags;
    }

    public String getAuthor() {
        return author;
    }

    public String getGroupTitle() {
        return groupTitle;
    }
}
