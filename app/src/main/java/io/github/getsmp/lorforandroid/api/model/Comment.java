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

package io.github.getsmp.lorforandroid.api.model;

import com.google.gson.annotations.Expose;

import java.util.Date;

public class Comment {
    @Expose private Integer id;
    @Expose private Author author;
    @Expose private String processedMessage;
    @Expose private Boolean deletable;
    @Expose private Boolean editable;
    @Expose private Boolean deleted;
    @Expose private Date postdate;
    @Expose private Userpic userpic;
    @Expose private Reply reply;
    @Expose private EditSummary editSummary;

    public Integer getId() {
        return id;
    }

    public Author getAuthor() {
        return author;
    }

    public String getProcessedMessage() {
        return processedMessage;
    }

    public Boolean getDeletable() {
        return deletable;
    }

    public Boolean getEditable() {
        return editable;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public Date getPostdate() {
        return postdate;
    }

    public Userpic getUserpic() {
        return userpic;
    }

    public Reply getReply() {
        return reply;
    }

    public EditSummary getEditSummary() {
        return editSummary;
    }

}
