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

package io.github.getsmp.lorforandroid.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.getsmp.lorforandroid.ui.util.ItemClickListener;

public abstract class BaseCallbackFragment extends BaseListFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        recyclerView.addOnItemTouchListener(new ItemClickListener(context, new ItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                onItemClickCallback(recyclerView.getChildAdapterPosition(view));
            }
        }));

        return view;
    }

    protected abstract void onItemClickCallback(int position);
}
