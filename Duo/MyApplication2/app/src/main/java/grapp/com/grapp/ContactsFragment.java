package grapp.com.grapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.provider.ContactsContract;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import grapp.com.grapp.model.GrappContact;

/**
 * Created by Hein on 4/11/2016.
 */
public class ContactsFragment extends Fragment  {

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private List<GrappContact> contacts;
    private Context context;
    private ContactsAdapter adapter;
    private String provider;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.contacts_layout, null);

        ListView mContactsList = (ListView) view.findViewById(R.id.contact_list);

        contacts = new ArrayList<>();
        context = getActivity();

        // Acquire a reference to the system Location Manager
        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.POWER_LOW);
        provider = locationManager.getBestProvider(criteria, true);

        // Ask permission if it's not set for location
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION))
            {

            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        }

        // Ask permission if it's not set for contacts
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_CONTACTS)) {
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        }

        // Get contact names and put them in a list
        ContentResolver cr = getContext().getContentResolver();
        Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);

        // Move cursor through datatable with contacts
        while (phones.moveToNext())
        {
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            contacts.add(new GrappContact(name));
        }

        // Make a new adapter and pass to the list
        adapter = new ContactsAdapter(context, contacts);
        mContactsList.setAdapter(adapter);

        return view;
    }

    @Override
    public void onPause()
    {
        // Ask for permission and turn location updates off
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.removeUpdates(locationListener);
        super.onPause();
    }

    @Override
    public void onResume()
    {
        // Start location listener
        setupLocationService();
        super.onResume();
    }

    public void setupLocationService() {
        // Define a listener
        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // When location changes calculate distance between you and contacts
                for (int i = 0; i < adapter.contacts.size(); i++)
                {
                    adapter.contacts.get(i).Distance = Math.round(location.distanceTo(adapter.contacts.get(i).location));
                }
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

        // Register the listener with the Location Manager to receive location updates
        locationManager.requestLocationUpdates(provider, 0, 0, locationListener);
    }

    /**
     * @return the photo URI
     */
    public Uri getPhotoUri() {
        try {
            Cursor cur = getContext().getContentResolver().query(
                    ContactsContract.Data.CONTENT_URI,
                    null,
                    ContactsContract.Data.CONTACT_ID + "=" + this.getId() + " AND "
                            + ContactsContract.Data.MIMETYPE + "='"
                            + ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE + "'", null,
                    null);
            if (cur != null) {
                if (!cur.moveToFirst()) {
                    return null; // no photo
                }
            } else {
                return null; // error in cursor process
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        Uri person = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(getId()));
        return Uri.withAppendedPath(person, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
    }


    }

