package io.github.getsmp.lorforandroid.ui.section.forum.section;

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

public class ForumSectionFragment extends BaseCallbackFragment {
    private final AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
    private final List<ForumSection> items = new ArrayList<ForumSection>();

    @Override
    protected String getUrl(int position) {
        return items.get(position).getUrl();
    }

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
                    items.add(new ForumSection(
                            section.select("a").first().attr("href"),
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
        return new ForumSectionAdapter(items);
    }
}
