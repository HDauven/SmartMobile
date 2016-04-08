package grapp.com.grapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hein on 4/8/2016.
 */
public class MainPagerAdapter extends FragmentPagerAdapter {

    private final List<String> tabTitles = new ArrayList<String>() {{
        add("Groups");
        add("Activity");
        add("Chats");
    }};

    private List<Fragment> tabs = new ArrayList<>();

    public MainPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);

        initializeTabs();
    }

    private void initializeTabs() {
        tabs.add(HostFragment.newInstance(new ContentFragment()));
        tabs.add(HostFragment.newInstance(ContentFragment.newInstance(1, 15)));
        tabs.add(HostFragment.newInstance(ContentFragment.newInstance(2, 15)));
    }

    @Override
    public Fragment getItem(int position) {
        return tabs.get(position);
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles.get(position);
    }
}
