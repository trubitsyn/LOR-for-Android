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
