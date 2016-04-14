package csi.hdauven.com.csi_week_1;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class ReportActivity extends AppCompatActivity {

    LocationManager locationManager;
    LocationListener locationListener;
    Vibrator v;

    CriminalProvider criminalProvider;
    int chosenCriminalPosition;
    Criminal chosenCriminal;

    boolean isActivated = false;
    long[] pattern = new long[] { 20, 50, 100, 200, 40, 100 } ;

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

        // Get the Vibrator Service and attach it to our reference
        v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);

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

        setupLocationListener();
    }

    @Override
    protected void onStart() {
        super.onStart();

        Button btnSCAOn = (Button) findViewById(R.id.btnScaOn);
        btnSCAOn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                locationManager = (LocationManager) v.getContext().getSystemService(LOCATION_SERVICE);

                if (ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 25, locationListener);
            }
        });

        Button btnSCAOff = (Button) findViewById(R.id.btnScaOff);
        btnSCAOff.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                locationManager.removeUpdates(locationListener);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.removeUpdates(locationListener);
    }

    private void setupLocationListener() {
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double distance = location.distanceTo(chosenCriminal.lastKnownLocation);

                // Log all the information with regard to the user position, criminal position and
                // the distance between both
                Log.i("sca", "Distance: " + Double.toString(distance));
                Log.i("sca", "User Latitude: " + Double.toString(location.getLatitude()));
                Log.i("sca", "User Longitude: " + Double.toString(location.getLongitude()));
                Log.i("sca", "Criminal Latitude: " + Double.toString(chosenCriminal.lastKnownLocation.getLatitude()));
                Log.i("sca", "Criminal Longitude: " + Double.toString(chosenCriminal.lastKnownLocation.getLongitude()));

                if (distance < 100) {
                    v.vibrate(pattern, 1);
                    Log.i("sca", "Has Vibrator: " + v.hasVibrator());
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

                Toast.makeText(ReportActivity.this, "Provider " + provider + " has its status changed to " + status + "!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProviderEnabled(String provider) {
                Toast.makeText(ReportActivity.this, "Provider " + provider + " enabled!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProviderDisabled(String provider) {
                Toast.makeText(ReportActivity.this, "Provider " + provider + " disabled!", Toast.LENGTH_SHORT).show();
            }
        };
    }

}
