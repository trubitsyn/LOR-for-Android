package dev.trubitsyn.lorforandroid.ui.comment

import androidx.paging.PageKeyedDataSource
import dev.trubitsyn.lorforandroid.api.ApiManager
import dev.trubitsyn.lorforandroid.api.model.Comments
import org.w3c.dom.Comment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentDataSource : PageKeyedDataSource<Int, Comment>() {
    val COMMENTS_PER_PAGE = 50

    fun load() {
        val url = ""
        val page = 1
        val call = ApiManager.INSTANCE.apiComments.getComments(url, page)
        call.enqueue(object : Callback<Comments> {
            override fun onResponse(call: Call<Comments>, response: Response<Comments>) {
                response.body()?.comments?.let {
                    // TODO
                }
            }

            override fun onFailure(call: Call<Comments>, t: Throwable) {
            }
        })
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Comment>) {
        TODO("not implemented")
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Comment>) {
        TODO("not implemented")
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Comment>) {
        TODO("not implemented")
    }
}