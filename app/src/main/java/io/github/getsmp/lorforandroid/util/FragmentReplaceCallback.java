package io.github.getsmp.lorforandroid.util;

import android.support.v4.app.Fragment;

public interface FragmentReplaceCallback {
    void replace(int containerId, Fragment fragment, String tag);
}
