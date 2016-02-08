package io.github.getsmp.lorforandroid.ui.section.forum;

import android.support.v7.widget.RecyclerView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import io.github.getsmp.lorforandroid.ui.base.BaseCallbackFragment;
import io.github.getsmp.lorforandroid.ui.util.ItemClickCallback;

public class ForumOverviewFragment extends BaseCallbackFragment {
    private final AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
    private final List<ForumOverviewItem> items = new ArrayList<ForumOverviewItem>();

    @Override
    protected void getListItems() {
        startRefresh();

        asyncHttpClient.get("https://www.linux.org.ru/forum/", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String resp = null;
                try {
                    resp = new String(responseBody, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    // Will never execute
                }

                Elements sections = Jsoup.parse(resp).body().select("div#bd li");
                for (Element section : sections) {
                    items.add(new ForumOverviewItem(
                            section.select("a").first().attr("href").replace("/forum/", ""),
                            section.select("a").first().ownText()
                    ));
                }

                adapter.notifyDataSetChanged();
                stopRefresh();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                networkError();
            }
        });
    }

    @Override
    protected void clearData() {
        items.clear();
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new ForumOverviewAdapter(items);
    }

    @Override
    protected void onItemClickCallback(int position) {
        ((ItemClickCallback) context).onForumSectionRequested(items.get(position).getUrl(), items.get(position).getName());
    }
}
