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

package net.voxelplanet.lorforandroid.api;

import net.voxelplanet.lorforandroid.model.TopicItems;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface ApiSection {
    @GET("/topics")
    void getTopics(
            @Query("section") String section,
            @Query("group") Object group,
            @Query("fromDate") String fromDate,
            @Query("toDate") String toDate,
            @Query("limit") Object limit,
            @Query("offset") Object offset,
            @Query("tag") Object tag,
            @Query("notalks") Object notalks,
            @Query("commitMode") Object commitMode,
            @Query("author") Object author,
            Callback<TopicItems> response
    );
}
