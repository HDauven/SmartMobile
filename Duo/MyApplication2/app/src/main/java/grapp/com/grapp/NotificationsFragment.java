package grapp.com.grapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

        return view;
    }

    private List<GrappNotification> getAllItemObjects() {
        List<GrappNotification> notifications = new ArrayList<>();

        notifications.add(new GrappNotification("Erwin added photo to Grapp Project", new Date(2016, 4, 15, 1, 2), "two", false));
        notifications.add(new GrappNotification("Erwin joined Group Grapp Project", new Date(2016, 4, 15, 1, 1), "one", true));

        return notifications;
    }
}
