package net.voxelplanet.lorforandroid.ui.tracker;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TrackerFragmentPagerAdapter extends FragmentPagerAdapter {
    private final String tabs[] = { "Все", "Основные", "Без talks", "Технические"};

    public TrackerFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return TrackerFragment.newInstance(TrackerFilterEnum.values()[position]);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }
}
