package csi.hdauven.com.csi_week_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Hello!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Button buttonStartReportActivity = (Button) findViewById(R.id.btnReport);
        buttonStartReportActivity.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentReport = new Intent(getApplicationContext(), ReportActivity.class);
                startActivity(intentReport);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        // Get the intent belonging to the calling activity
        Intent intentFromCriminalsList = getIntent();
        // Retrieve the CriminalName string, given by the calling activity to the new activity
        String criminalName = intentFromCriminalsList.getStringExtra("CriminalName");
        String criminalGender = intentFromCriminalsList.getStringExtra("CriminalGender");
        int criminalAge = intentFromCriminalsList.getIntExtra("CriminalAge", 0);
        String tmpCriminalAge = String.valueOf(criminalAge);
        String criminalBounty = intentFromCriminalsList.getStringExtra("CriminalBounty");
        // Get the TextView that should contain the name of the criminal
        TextView tvNameValue = (TextView) findViewById(R.id.tvNameValue);
        TextView tvGenderValue = (TextView) findViewById(R.id.tvGenderValue);
        TextView tvAgeValue = (TextView) findViewById(R.id.tvAgeValue);
        TextView tvBountyValue = (TextView) findViewById(R.id.tvBountyValue);
        // Set the TextViews text to the name of the criminal
        tvNameValue.setText(criminalName);
        tvGenderValue.setText(criminalGender);
        tvAgeValue.setText(tmpCriminalAge);
        tvBountyValue.setText(criminalBounty);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
