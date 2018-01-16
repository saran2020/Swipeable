package app.test.my.swipable;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Saran Sankaran on 1/16/18.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {


    private String[] title;

    public ViewPagerAdapter(final FragmentManager fm, final String[] title) {
        super(fm);
        this.title = title;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return MessageFragment.newInstance(title[position]);
    }

    @Override
    public int getCount() {
        return title.length;
    }

        @NonNull
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}