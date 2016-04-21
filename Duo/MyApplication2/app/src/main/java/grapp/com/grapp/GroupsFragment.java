package grapp.com.grapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import grapp.com.grapp.model.GrappGroup;

/**
 * Created by Hein on 4/11/2016.
 */
public class GroupsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.groups_layout, container, false);

        GridView gridView = (GridView) view.findViewById(R.id.gridview);

        List<GrappGroup> allItems = getAllItemObject();
        GroupsAdapter groupsAdapter = new GroupsAdapter(this.getContext(), allItems);
        gridView.setAdapter(groupsAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Start new fragment and pass the group name using a bundle.
                Toast.makeText(view.getContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
                // GroupFragment swapFragment = new GroupFragment();
                // Bundle arguments = new Bundle();
                // arguments.putInt(GroupFragment.ARG_POSITION, position);
                // swapFragment.setArguments(args);
                // getFragmentManager().beginTransaction().replace()
            }
        });

        return view;
    }

    private List<GrappGroup> getAllItemObject() {
        GrappGroup grappGroup = null;

        List<GrappGroup> groups = new ArrayList<>();
        groups.add(new GrappGroup("Grapp Project", "one"));
        groups.add(new GrappGroup("Vacation", "two"));
        groups.add(new GrappGroup("Maatwerk", "three"));
        groups.add(new GrappGroup("S42", "four"));
        groups.add(new GrappGroup("Dream Team", "five"));
        groups.add(new GrappGroup("Family", "six"));
        groups.add(new GrappGroup("Badminton", "seven"));
        groups.add(new GrappGroup("Proftaak", "eight"));
        groups.add(new GrappGroup("Grapp Project", "one"));
        groups.add(new GrappGroup("Vacation", "two"));
        groups.add(new GrappGroup("Maatwerk", "three"));
        groups.add(new GrappGroup("S42", "four"));
        groups.add(new GrappGroup("Dream Team", "five"));
        groups.add(new GrappGroup("Family", "six"));
        groups.add(new GrappGroup("Badminton", "seven"));
        groups.add(new GrappGroup("Proftaak", "eight"));
        groups.add(new GrappGroup("Grapp Project", "one"));
        groups.add(new GrappGroup("Vacation", "two"));
        groups.add(new GrappGroup("Maatwerk", "three"));
        groups.add(new GrappGroup("S42", "four"));
        groups.add(new GrappGroup("Dream Team", "five"));
        groups.add(new GrappGroup("Family", "six"));
        groups.add(new GrappGroup("Badminton", "seven"));
        groups.add(new GrappGroup("Proftaak", "eight"));

        return groups;
    }
}
