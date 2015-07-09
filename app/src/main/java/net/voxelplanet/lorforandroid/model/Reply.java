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

package net.voxelplanet.lorforandroid.model;

import com.google.gson.annotations.Expose;

import java.util.Date;

public class Reply {
    @Expose
    private Integer id;
    @Expose
    private String author;
    @Expose
    private Date postdate;
    @Expose
    private Boolean samePage;

    public Integer getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public Date getPostdate() {
        return postdate;
    }

    public Boolean getSamePage() {
        return samePage;
    }

}
