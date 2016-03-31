package csi.hdauven.com.csi_week_1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ReportActivity extends AppCompatActivity {

    LocationManager locationManager;
    CriminalProvider criminalProvider;
    int chosenCriminalPosition;
    Criminal chosenCriminal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the intent belonging to the calling activity
        Intent intentFromCriminalsList = getIntent();

        // Get the position of the chosen criminal from the intent
        chosenCriminalPosition = intentFromCriminalsList.getIntExtra("chosenCriminalPosition", 0);

        // Create the CriminalProvider, which holds the criminals
        criminalProvider = new CriminalProvider(getApplicationContext());

        // Get the chosen criminal from the CriminalProvider, through the position
        chosenCriminal = criminalProvider.GetCriminal(chosenCriminalPosition);

        setContentView(R.layout.activity_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Drawable criminalMugshot = chosenCriminal.mugshot;
        ImageView imgMugshot = (ImageView) findViewById(R.id.imgViewReport);
        imgMugshot.setImageDrawable(criminalMugshot);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Button btnBackToMain = (Button) findViewById(R.id.btnBack);
        btnBackToMain.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBackToMain = new Intent(getApplicationContext(), MainActivity.class);
                intentBackToMain.putExtra("chosenCriminalPosition", chosenCriminalPosition);
                startActivity(intentBackToMain);
            }
        });

        setupLocationService();

        Button btnSCAOn = (Button) findViewById(R.id.btnScaOn);
        btnSCAOn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LinearLayout.class);
                startActivity(intent);
            }
        });
    }

    public void setupLocationService() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 25, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });

    }

}
