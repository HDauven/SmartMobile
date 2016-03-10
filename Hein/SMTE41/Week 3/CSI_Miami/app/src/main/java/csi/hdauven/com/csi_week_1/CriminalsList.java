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

public class CriminalsList extends AppCompatActivity {

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
        final String[] criminals = getResources().getStringArray(R.array.names);

        listView.setAdapter(
                new ArrayAdapter<String>(
                        this,
                        android.R.layout.simple_list_item_1,
                        criminals
                )
        );

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the name from the array that is in the same position as the chosen listitem.
                String name = criminals[position];
                // Start intent and pass name using putExtra.
                Intent intentDisplayCriminalInformation = new Intent(getApplicationContext(), MainActivity.class);
                intentDisplayCriminalInformation.putExtra("CriminalName", name);
                startActivity(intentDisplayCriminalInformation);
            }
        });
    }

}
