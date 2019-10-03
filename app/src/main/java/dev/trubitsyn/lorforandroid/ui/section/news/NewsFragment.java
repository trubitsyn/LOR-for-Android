/*
 * Copyright (C) 2015-2016 Nikola Trubitsyn
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package dev.trubitsyn.lorforandroid.ui.section.news;

import androidx.recyclerview.widget.RecyclerView;

import com.loopj.android.http.RequestParams;

import dev.trubitsyn.lorforandroid.ui.section.Item;
import dev.trubitsyn.lorforandroid.ui.section.ItemFactory;
import dev.trubitsyn.lorforandroid.ui.section.SectionFragment;
import dev.trubitsyn.lorforandroid.ui.util.ItemClickCallback;

public class NewsFragment extends SectionFragment {
    @Override
    protected ItemFactory getItemFactory() {
        return new NewsItemFactory();
    }

    @Override
    public int getItemsPerPage() {
        return 20;
    }

    @Override
    public int getMaxOffset() {
        return 200;
    }

    @Override
    public String getPath() {
        return "news";
    }

    @Override
    public RequestParams getRequestParams() {
        return new RequestParams("offset", offset);
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new NewsAdapter(items);
    }

    @Override
    protected void onItemClickCallback(int position) {
        Object item = items.get(position);
        String url;
        if (item instanceof MiniNewsItem) {
            url = ((MiniNewsItem) items.get(position)).getUrl();
        } else if (item instanceof Item) {
            url = ((Item) items.get(position)).getUrl();
        } else
            throw new ClassCastException("Object cannot be cast neither to MiniNewsItem nor to Item.");

        ((ItemClickCallback) context).onTopicRequested(url);
    }
}
