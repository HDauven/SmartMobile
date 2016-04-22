package grapp.com.grapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import grapp.com.grapp.model.GrappNotification;

/**
 * Created by Hein on 4/11/2016.
 */
public class NotificationsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notifications_layout, container, false);

        ListView listView = (ListView) view.findViewById(R.id.notifications_listview);

        List<GrappNotification> allItems = getAllItemObjects();
        NotificationsAdapter notificationsAdapter = new NotificationsAdapter(this.getContext(), allItems);
        listView.setAdapter(notificationsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Start new fragment and pass the selected notification using a bundle.
                Bundle arguments = new Bundle();
                arguments.putInt("position", position);
                Fragment swapFragment = new ProfileFragment();
                swapFragment.setArguments(arguments);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.containerView, swapFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

    private List<GrappNotification> getAllItemObjects() {
        List<GrappNotification> notifications = new ArrayList<>();

        notifications.add(new GrappNotification("Erwin added photo to Grapp Project", new Date(2016, 4, 15, 1, 2), "two", false));
        notifications.add(new GrappNotification("Erwin joined Group Grapp Project", new Date(2016, 4, 15, 1, 1), "one", true));

        return notifications;
    }
}
