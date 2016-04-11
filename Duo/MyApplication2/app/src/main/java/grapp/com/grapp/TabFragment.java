package grapp.com.grapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Hein on 4/11/2016.
 */
public class TabFragment extends Fragment {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate tab_layout and setup Views.
        View view = inflater.inflate(R.layout.tab_layout, null);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);

        // Set an adapter for the ViewPager.
        viewPager.setAdapter(new TabPagerAdapter(getChildFragmentManager()));

        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    class TabPagerAdapter extends FragmentPagerAdapter {
        public TabPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        // Returns a fragment, based on its position.
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new GroupsFragment();
                case 1:
                    return new NotificationsFragment();
                case 2:
                    return new ChatsFragment();
            }
            return null;
        }


        @Override
        public int getCount() {
            return int_items;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Groups";
                case 1:
                    return "Notifications";
                case 2:
                    return "Chats";
            }
            return null;
        }
    }
}
