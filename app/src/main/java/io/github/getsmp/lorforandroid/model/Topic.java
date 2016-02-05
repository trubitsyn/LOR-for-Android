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

package io.github.getsmp.lorforandroid.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Topic {
    private final List<String> tags = new ArrayList<String>();
    private String url;
    private String title;
    private String message;
    private Date postDate;
    private Date lastModified;
    private Boolean sticky;
    private Integer commentsCount;
    private Integer favsCount;
    private Integer watchCount;
    private Integer postscore;
    private Author author;

    public Topic(String url, String title, String message, Date postDate, Date lastModified, Boolean sticky, Integer commentsCount, Integer favsCount, Integer watchCount, Integer postscore, Author author) {
        this.url = url;
        this.title = title;
        this.message = message;
        this.postDate = postDate;
        this.lastModified = lastModified;
        this.sticky = sticky;
        this.commentsCount = commentsCount;
        this.favsCount = favsCount;
        this.watchCount = watchCount;
        this.postscore = postscore;
        this.author = author;
    }

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
