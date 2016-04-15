package org.csiweek1;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class ReportActivity extends AppCompatActivity {

    private LocationManager locationManager;
    private LocationListener locationListener;
    private Criminal c;
    private Vibrator v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        CriminalProvider cp = new CriminalProvider(this);

        Intent i = getIntent();
        final int chosenCriminalPosition = i.getIntExtra("positionCriminal", -1);
        c = cp.GetCriminal(chosenCriminalPosition);

        Button back = (Button) findViewById(R.id.btnBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button btnSCAOn = (Button) findViewById(R.id.btnCsaOn);
        btnSCAOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupLocationService();
            }
        });

        Button btnCSAOff = (Button) findViewById(R.id.btnCsaOff);
        btnCSAOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                locationManager.removeUpdates(locationListener);
            }
        });


    }

    public void setupLocationService() {
        // Acquire a reference to the system Location Manager
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        // Define a listener that responds to location updates
        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                CalculateDistance(location, c.lastKnownLocation);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };

        // Register the listener with the Location Manager to receive location updates
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
    }

    public void CalculateDistance(Location location1, Location location2)
    {
        long[] pattern = new long[] { 20, 50, 100, 200, 40, 100 } ;
        if(location1.distanceTo(location2) < 100)
        {
            v.vibrate(pattern, -1);
        }
    }
}
