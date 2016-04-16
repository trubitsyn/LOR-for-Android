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

package io.github.getsmp.lorforandroid.model;

import com.google.gson.annotations.Expose;

import java.util.Date;
import java.util.List;

public class Topic {
    @Expose private List<String> tags;
    @Expose private String url;
    @Expose private String title;
    @Expose private String message;
    @Expose private Date postDate;
    @Expose private Date lastModified;
    @Expose private Boolean sticky;
    @Expose private Integer commentsCount;
    @Expose private Integer favsCount;
    @Expose private Integer watchCount;
    @Expose private Integer postscore;
    @Expose private Author author;

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public Date getPostDate() {
        return postDate;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public Boolean getSticky() {
        return sticky;
    }

    public Integer getCommentsCount() {
        return commentsCount;
    }

    public Integer getFavsCount() {
        return favsCount;
    }

    public Integer getWatchCount() {
        return watchCount;
    }

    public Integer getPostscore() {
        return postscore;
    }

    public List<String> getTags() {
        return tags;
    }

    public Author getAuthor() {
        return author;
    }

}
