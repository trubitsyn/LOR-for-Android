/*
 * Copyright 2016 getsmp
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

package io.github.getsmp.lorforandroid.ui.section.forum;

import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

import io.github.getsmp.lorforandroid.R;
import io.github.getsmp.lorforandroid.ui.section.SectionCommon;
import io.github.getsmp.lorforandroid.ui.util.ItemClickCallback;

public class ForumOverviewFragment extends SectionCommon {
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
        ForumOverviewItem item = (ForumOverviewItem) items.get(position);
        if (item.getUrl().equals("club")) {
            Toast.makeText(context, R.string.error_access_denied, Toast.LENGTH_SHORT).show();
        } else {
            ((ItemClickCallback) context).onForumSectionRequested(item.getUrl(), item.getName());
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
