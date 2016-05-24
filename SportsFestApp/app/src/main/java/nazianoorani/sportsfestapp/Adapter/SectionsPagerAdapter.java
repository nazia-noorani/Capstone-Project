package nazianoorani.sportsfestapp.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import nazianoorani.sportsfestapp.IndividualEventsFragment;
import nazianoorani.sportsfestapp.PlaceholderFragment;
import nazianoorani.sportsfestapp.TeamEventsFragment;

/**
 * Created by nazianoorani on 24/02/16.
 */
public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
    SparseArray<Fragment> registeredFragments = new SparseArray<>();


    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new IndividualEventsFragment();
            case 1:
                return new TeamEventsFragment();

        }
        return PlaceholderFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 2;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getRegisteredFragment(int pos) {
        return registeredFragments.get(pos);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "INDIVIDUAL EVENTS";
            case 1:
                return "TEAM EVENTS";
        }
        return null;
    }
}