package grapp.com.grapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import grapp.com.grapp.model.GrappModule;

/**
 * Created by Hein on 4/21/2016.
 */
public class GroupFragment extends Fragment {

    final static String ARG_POSITION = "position";
    private int currentPosition = -1;
    private String groupTitle;
    private String groupSubtitle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        savedInstanceState = getArguments();
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt("position");
            groupTitle = savedInstanceState.getString("title");
            groupSubtitle = savedInstanceState.getString("subtitle");
        }
        View view = inflater.inflate(R.layout.group_layout, container, false);

        GridView gridView = (GridView) view.findViewById(R.id.group_modules);

        List<GrappModule> allItems = getAllItemObject();
        ModulesAdapter modulesAdapter = new ModulesAdapter(this.getContext(), allItems);
        gridView.setAdapter(modulesAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Start new fragment and pass the selected group using a bundle.
                Toast.makeText(getContext(), "Module selected", Toast.LENGTH_SHORT).show();
            }
        });
        Toast.makeText(view.getContext(), "Selected item: " + String.valueOf(currentPosition), Toast.LENGTH_SHORT).show();

        TextView tvGroupName = (TextView) view.findViewById(R.id.group_title);
        tvGroupName.setText(groupTitle);

        TextView tvGroupSubtitle = (TextView) view.findViewById(R.id.group_title_sub);
        tvGroupSubtitle.setText(groupSubtitle);

        return view;
    }

    private List<GrappModule> getAllItemObject() {
        List<GrappModule> modules = new ArrayList<>();
        modules.add(new GrappModule("Chat", "chat"));
        modules.add(new GrappModule("Calendar", "calendar"));
        modules.add(new GrappModule("Pictures", "photo"));
        modules.add(new GrappModule("Ranking", "ranking"));
        modules.add(new GrappModule("Tournament", "tournament"));
        modules.add(new GrappModule("Take photo", "camera"));

        return modules;
    }
}
