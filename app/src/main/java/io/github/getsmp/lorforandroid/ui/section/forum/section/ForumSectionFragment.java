package io.github.getsmp.lorforandroid.ui.section.forum.section;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import io.github.getsmp.lorforandroid.R;
import io.github.getsmp.lorforandroid.ui.base.BaseCallbackFragment;
import io.github.getsmp.lorforandroid.util.StringUtils;

public class ForumSectionFragment extends BaseCallbackFragment {
    private final AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
    private final List<ForumSectionItem> items = new ArrayList<ForumSectionItem>();
    private String group;
    private int offset;

    public static ForumSectionFragment newInstance(String group) {
        ForumSectionFragment forumSectionFragment = new ForumSectionFragment();
        Bundle args = new Bundle();
        args.putString("group", group);
        forumSectionFragment.setArguments(args);
        return forumSectionFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        group = getArguments().getString("group");
    }

    @Override
    protected String getUrl(int position) {
        return items.get(position).getUrl();
    }

    @Override
    protected void getListItems() {
        if (offset <= 300) {
            startRefresh();

            RequestParams params = new RequestParams();
            params.add("offset", String.valueOf(offset));
            asyncHttpClient.get("https://www.linux.org.ru/forum/" + group, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String resp = null;
                    try {
                        resp = new String(responseBody, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        // Will never execute
                    }

                    Elements entries = Jsoup.parse(resp).body().select("tbody tr");
                    for (Element entry : entries) {
                        Element properties = entries.select("td").first();
                        String bareAuthor = properties.text();
                        items.add(new ForumSectionItem(
                                properties.select("a").first().attr("href"),
                                properties.select("a").text(),
                                StringUtils.tagsFromElements(properties.select("a").first().select("span.tag")),
                                bareAuthor.substring(bareAuthor.indexOf("("), bareAuthor.indexOf(")")),
                                entry.select("td.dateinterval").first().select("time").first().ownText(),
                                entries.select("td.numbers").first().ownText()
                        ));
                    }

                    offset += 30;
                    adapter.notifyDataSetChanged();
                    stopRefresh();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    networkError();
                }
            });
        } else Toast.makeText(context, R.string.error_no_more_data, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void clearData() {
        offset = 0;
        items.clear();
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new ForumSectionAdapter(items);
    }
}
