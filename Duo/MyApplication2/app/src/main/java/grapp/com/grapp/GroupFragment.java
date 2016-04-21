package grapp.com.grapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by Hein on 4/21/2016.
 */
public class GroupFragment extends Fragment {

    final static String ARG_POSITION = "position";
    private int currentPosition = -1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        savedInstanceState = getArguments();
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt("position");
        }
        View view = inflater.inflate(R.layout.profile_layout, container, false);
        Toast.makeText(view.getContext(), "Selected item: " + String.valueOf(currentPosition), Toast.LENGTH_SHORT).show();

        return view;
    }
}
