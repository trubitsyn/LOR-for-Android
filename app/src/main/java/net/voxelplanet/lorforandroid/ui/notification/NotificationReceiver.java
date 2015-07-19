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

package net.voxelplanet.lorforandroid.ui.notification;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import net.voxelplanet.lorforandroid.R;
import net.voxelplanet.lorforandroid.api.ApiManager;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        ApiManager.API_MANAGER.getApiNotifications().getCount(null, new Callback<Integer>() {
            @Override
            public void success(Integer count, Response response) {
                showNotification(context, count);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(context, R.string.error_network_notification, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showNotification(Context context, Integer count) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle(count + " " + context.getString(R.string.notification_title))
                .setContentText(context.getString(R.string.app_name));
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1, builder.build());
    }
}
