package org.csiweek1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class CriminalList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criminal_list);

        CriminalProvider criminalProvider = new CriminalProvider(getApplicationContext() );
        final List<Criminal> criminals = criminalProvider.GetCriminals();

        ListView listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(new CriminalListAdapter(this, criminals));

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //Get the name from the array that is in the same position as the chosen listitem.
                Criminal criminal = criminals.get(position);
                //Todo start intent and pass name using putExtra
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("name", criminal.name);
                startActivity(intent);
            }
        });

    }
}
