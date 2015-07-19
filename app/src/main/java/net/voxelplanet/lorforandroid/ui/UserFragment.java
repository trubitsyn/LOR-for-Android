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

package net.voxelplanet.lorforandroid.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import net.voxelplanet.lorforandroid.R;
import net.voxelplanet.lorforandroid.api.ApiManager;
import net.voxelplanet.lorforandroid.model.User;
import net.voxelplanet.lorforandroid.util.StringUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class UserFragment extends Fragment {
    @Bind(R.id.userId) TextView id;
    @Bind(R.id.userNick) TextView nick;
    @Bind(R.id.userUrl) TextView url;
    @Bind(R.id.userRegDate) TextView regDate;
    @Bind(R.id.userLastVisit) TextView lastVisit;
    @Bind(R.id.userStatus) TextView status;
    @Bind(R.id.userScore) TextView score;
    @Bind(R.id.userFavTags) TextView favTags;
    @Bind(R.id.userIgnoredTags) TextView ignoredTags;
    @Bind(R.id.userIgnoredUsers) TextView ignoredUsers;
    private Activity activity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void getInfo() {
        // TODO: Implement user authorization
        ApiManager.API_MANAGER.getApiUser().getUser("anonymous", new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                id.setText(user.getId());
                nick.setText(user.getNick());
                url.setText(user.getUrl());
                regDate.setText(StringUtils.getDate(user.getRegDate()));
                lastVisit.setText(StringUtils.getDate(user.getLastVisit()));
                status.setText(user.getStatus());
                score.setText(user.getScore());
                favTags.setText(TextUtils.join(", ", user.getFavTags()));
                ignoredTags.setText(TextUtils.join(", ", user.getIgnoredTags()));
                ignoredUsers.setText(TextUtils.join(", ", user.getIgnoredUsers()));
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(activity, R.string.error_network, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
