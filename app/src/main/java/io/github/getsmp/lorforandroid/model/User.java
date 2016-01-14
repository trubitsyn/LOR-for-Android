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

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User {
    @Expose
    private final List<String> favTags = new ArrayList<String>();
    @Expose
    private final List<Object> ignoredTags = new ArrayList<Object>();
    @Expose
    private final List<String> ignoredUsers = new ArrayList<String>();
    @Expose
    private Integer id;
    @Expose
    private String nick;
    @Expose
    private String url;
    @Expose
    private Date regDate;
    @Expose
    private Date lastVisit;
    @Expose
    private String status;
    @Expose
    private Integer score;

    public Integer getId() {
        return id;
    }

    public String getNick() {
        return nick;
    }

    public String getUrl() {
        return url;
    }

    public Date getRegDate() {
        return regDate;
    }

    public Date getLastVisit() {
        return lastVisit;
    }

    public String getStatus() {
        return status;
    }

    public Integer getScore() {
        return score;
    }

    public List<String> getFavTags() {
        return favTags;
    }

    public List<Object> getIgnoredTags() {
        return ignoredTags;
    }

    public List<String> getIgnoredUsers() {
        return ignoredUsers;
    }

}
