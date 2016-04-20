/*
 *
 *  * Copyright 2016 getsmp
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package io.github.getsmp.lorforandroid.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import io.github.getsmp.lorforandroid.R;

public class PreferenceUtils {
    public static boolean shouldLoadImagesOnMobileData(Context context) {
        return getPrefs(context).getBoolean(context.getString(R.string.pref_load_images), false);
    }

    public static boolean shouldLoadImagesNow(Context context) {
        if (NetworkUtils.isMobileData(context)) {
            return shouldLoadImagesOnMobileData(context);
        }
        return true;
    }

    private static SharedPreferences getPrefs(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}
