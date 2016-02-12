package io.github.getsmp.lorforandroid.ui.section.forum.section;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.loopj.android.http.RequestParams;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import io.github.getsmp.lorforandroid.ui.section.SectionCommon;
import io.github.getsmp.lorforandroid.util.StringUtils;

public class ForumSectionFragment extends SectionCommon {
    private final List<ForumSectionItem> items = new ArrayList<ForumSectionItem>();
    private String group;

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
    protected void clearData() {
        offset = 0;
        items.clear();
    }

    @Override
    protected List getDataSet() {
        return items;
    }

    @Override
    public int getItemsPerPage() {
        return 30;
    }

    @Override
    public int getMaxOffset() {
        return 300;
    }

    @Override
    public String getPath() {
        return "forum/" + group;
    }

    @Override
    public RequestParams getRequestParams() {
        return new RequestParams("offset", offset);
    }

    @Override
    protected void generateDataSet(Element responseBody) {
        Elements entries = responseBody.select("tbody tr");
        for (Element entry : entries) {
            Element properties = entry.select("td").first();
            String bareAuthor = properties.ownText();
            items.add(new ForumSectionItem(
                    properties.select("a").first().attr("href"),
                    properties.select("a").first().ownText(),
                    StringUtils.tagsFromElements(properties.select("a").first().select("span.tag")),
                    bareAuthor.substring(bareAuthor.lastIndexOf("("), bareAuthor.lastIndexOf(")")).replaceAll("[()]", ""),
                    entry.select("td.dateinterval").first().select("time").first().ownText(),
                    StringUtils.addEnding(entry.select("td.numbers").first().ownText())
            ));
        }
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new ForumSectionAdapter(items);
    }

    @Override
    protected void onItemClickCallback(int position) {
        ((Callback) context).returnToActivity(items.get(position).getUrl());
    }

    interface Callback {
        void returnToActivity(String url);
    }
}
