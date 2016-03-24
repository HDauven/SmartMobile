package csi.hdauven.com.csi_week_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class CriminalsList extends AppCompatActivity {

    List<Criminal> criminals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criminalslist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Welcome to the CSI Criminals app", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Get a reference to the listview
        ListView listView = (ListView) findViewById(R.id.listView);
        // Get a reference to the list with names
        final String[] names = getResources().getStringArray(R.array.names);
        final String[] genders = getResources().getStringArray(R.array.genders);
        final int[] ages = getResources().getIntArray(R.array.ages);
        final String[] bounties = getResources().getStringArray(R.array.bounties);

        listView.setAdapter(
                new ArrayAdapter<String>(
                        this,
                        android.R.layout.simple_list_item_1,
                        names
                )
        );

        /*listView.setAdapter(
                new ArrayAdapter<String>(
                        this,
                        android.R.layout.simple_list_item_1,
                        bounties
                )
        );*/

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the name from the array that is in the same position as the chosen listitem.
                String name = names[position];
                String gender = genders[position];
                int age = ages[position];
                String bounty = bounties[position];
                // Start intent and pass name using putExtra.
                Intent intentDisplayCriminalInformation = new Intent(getApplicationContext(), MainActivity.class);
                intentDisplayCriminalInformation.putExtra("CriminalName", name);
                intentDisplayCriminalInformation.putExtra("CriminalGender", gender);
                intentDisplayCriminalInformation.putExtra("CriminalAge", age);
                intentDisplayCriminalInformation.putExtra("CriminalBounty", bounty);
                startActivity(intentDisplayCriminalInformation);
            }
        });

        //*****************************************************//
        //Some example code below on how to use CriminalProvider:
        CriminalProvider criminalProvider = new CriminalProvider(getApplicationContext());
        criminals = criminalProvider.GetCriminals();

        CriminalListAdapter criminalListAdapter =
                new CriminalListAdapter(getApplicationContext(), criminals);

        listView.setAdapter(criminalListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Start intent and pass name using putExtra.
                Intent intentDisplayCriminalInformation = new Intent(getApplicationContext(), MainActivity.class);
                intentDisplayCriminalInformation.putExtra("chosenCriminalPosition", position);
                startActivity(intentDisplayCriminalInformation);
            }
        });

    }

}
