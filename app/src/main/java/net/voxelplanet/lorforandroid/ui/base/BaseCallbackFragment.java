/*
 * Copyright 2015 getsmp
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

package net.voxelplanet.lorforandroid.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.voxelplanet.lorforandroid.ui.util.ItemClickCallback;
import net.voxelplanet.lorforandroid.ui.util.ItemClickListener;

public abstract class BaseCallbackFragment extends BaseListFragment {
    private ItemClickCallback callback;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        callback = (ItemClickCallback) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View newView = super.onCreateView(inflater, container, savedInstanceState);
        recyclerView.addOnItemTouchListener(new ItemClickListener(activity, new ItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                callback.onItemClicked(getUrl(recyclerView.getChildPosition(view)));
            }
        }));

        return newView;
    }

    protected abstract String getUrl(int position);
}
