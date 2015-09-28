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

package io.github.getsmp.lorforandroid.api;

import io.github.getsmp.lorforandroid.model.Comments;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

public interface ApiComments {
    @GET("/{topic}/comments")
    void getComments(
            @Path(value = "topic", encode = false) String topic,
            @Query("page") int page, Callback<Comments> response
    );

    @POST("/add_comment.jsp")
    void addComment(
            @Query("topic") int topic,
            @Query("replyto") int replyTo,
            @Query("title") String title,
            @Query("msg") String message,
            @Query("csrf") String csrf
    );

    @POST("/delete_comment.jsp")
    void deleteComment(
            @Query("msgid") int messageId,
            @Query("reason") String reason,
            @Query("csrf") String csrf
    );
}
