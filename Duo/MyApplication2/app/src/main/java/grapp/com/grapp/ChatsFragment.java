package grapp.com.grapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import grapp.com.grapp.model.GrappChat;

/**
 * Created by Hein on 4/11/2016.
 */
public class ChatsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chats_layout, container, false);

        ListView listView = (ListView) view.findViewById(R.id.chats_listview);

        List<GrappChat> allItems = getAllItemObjects();
        ChatsAdapter chatsAdapter = new ChatsAdapter(this.getContext(), allItems);
        listView.setAdapter(chatsAdapter);

        return view;
    }

    private List<GrappChat> getAllItemObjects() {
        List<GrappChat> chats = new ArrayList<>();
        chats.add(new GrappChat("Grapp Chat", "one", "Hey Hein, doorwerken!"));
        chats.add(new GrappChat("Mam", "two"));
        chats.add(new GrappChat("4Chan", "three", "Weird"));
        chats.add(new GrappChat("Gezin", "four", "Neem je boter mee?"));
        chats.add(new GrappChat("Grapp Chat 2", "five"));
        chats.add(new GrappChat("test", "six"));
        chats.add(new GrappChat("test2", "seven"));
        chats.add(new GrappChat("test3", "eight"));

        return chats;
    }
}
