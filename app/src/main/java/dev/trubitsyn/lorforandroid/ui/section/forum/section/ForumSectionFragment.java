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

package dev.trubitsyn.lorforandroid.ui.section.forum.section;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.loopj.android.http.RequestParams;

import dev.trubitsyn.lorforandroid.ui.section.Item;
import dev.trubitsyn.lorforandroid.ui.section.ItemFactory;
import dev.trubitsyn.lorforandroid.ui.section.SectionFragment;

public class ForumSectionFragment extends SectionFragment {
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
    protected ItemFactory getItemFactory() {
        return new ForumSectionItemFactory();
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
    protected RecyclerView.Adapter getAdapter() {
        return new ForumSectionAdapter(items);
    }

    @Override
    protected void onItemClickCallback(int position) {
        ((Callback) context).returnToActivity(((Item) items.get(position)).getUrl());
    }

    interface Callback {
        void returnToActivity(String url);
    }
}
