package dev.trubitsyn.lorforandroid.site

import dev.trubitsyn.lorforandroid.ui.section.forum.ForumOverviewItem
import dev.trubitsyn.lorforandroid.ui.section.forum.section.ForumSectionItem
import dev.trubitsyn.lorforandroid.ui.section.gallery.GalleryItem
import dev.trubitsyn.lorforandroid.ui.section.news.NewsItem
import dev.trubitsyn.lorforandroid.ui.section.tracker.TrackerItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SiteApi {

    @GET(value = "news")
    fun getNews(
            @Query(value = "offset") offset: Int
    ): Call<List<NewsItem>>

    @GET(value = "gallery")
    fun getGallery(
            @Query(value = "offset") offset: Int
    ): Call<List<GalleryItem>>

    @GET(value = "tracker")
    fun getTracker(
            @Query(value = "filter") filter: String,
            @Query(value = "offset") offset: Int
    ): Call<List<TrackerItem>>

    @GET(value = "forum")
    fun getForum(): Call<List<ForumOverviewItem>>

    @GET(value = "forum/{section}")
    fun getForumSection(
            @Path(value = "section") section: String,
            @Query(value = "offset") offset: Int
    ): Call<List<ForumSectionItem>>
}