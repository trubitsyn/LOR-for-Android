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
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.getsmp.lorforandroid.R;

public class TabFragment extends Fragment {
    @Bind(R.id.viewpager) ViewPager viewPager;
    @Bind(R.id.tabs) TabLayout tabLayout;
    private FragmentStatePagerAdapter adapter;

    public void setAdapter(FragmentStatePagerAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tabs, container, false);
        ButterKnife.bind(this, view);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
