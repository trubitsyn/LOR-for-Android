package io.github.getsmp.lorforandroid.ui.section.forum;

import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import io.github.getsmp.lorforandroid.R;
import io.github.getsmp.lorforandroid.ui.section.SectionCommon;
import io.github.getsmp.lorforandroid.ui.util.ItemClickCallback;

public class ForumOverviewFragment extends SectionCommon {
    private final List<ForumOverviewItem> items = new ArrayList<ForumOverviewItem>();

    @Override
    protected List getDataSet() {
        return items;
    }

    @Override
    public int getItemsPerPage() {
        return 0;
    }

    @Override
    public int getMaxOffset() {
        return 0;
    }

    @Override
    public String getPath() {
        return "forum";
    }

    @Override
    public RequestParams getRequestParams() {
        return null;
    }

    @Override
    protected void generateDataSet(Element responseBody) {
        Elements sections = responseBody.select("div#bd").select("ul").first().select("li");
        for (Element section : sections) {
            items.add(new ForumOverviewItem(
                    section.select("a").first().attr("href").replace("/forum/", "").replace("/", ""),
                    section.select("a").first().ownText()
            ));
        }
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new ForumOverviewAdapter(items);
    }

    @Override
    protected void onItemClickCallback(int position) {
        if (items.get(position).getUrl().equals("club")) {
            Toast.makeText(context, R.string.error_access_denied, Toast.LENGTH_SHORT).show();
        } else {
            ((ItemClickCallback) context).onForumSectionRequested(items.get(position).getUrl(), items.get(position).getName());
        }
    }

    @Override
    protected boolean loadMoreAllowed() {
        return false;
    }

    @Override
    protected boolean showDividers() {
        return false;
    }
}
