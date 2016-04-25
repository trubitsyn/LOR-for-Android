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

package io.github.getsmp.lorforandroid.api.model;

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
