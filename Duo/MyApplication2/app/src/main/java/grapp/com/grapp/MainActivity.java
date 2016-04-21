package grapp.com.grapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

import grapp.com.grapp.util.SQLiteHandler;
import grapp.com.grapp.util.SessionManager;

public class MainActivity extends AppCompatActivity {

    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    private SessionManager sessionManager;
    private SQLiteHandler internalDatabase;

    private MenuItem activeMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        internalDatabase = new SQLiteHandler(getApplicationContext());
        sessionManager = new SessionManager(this);
        if (!sessionManager.getLoggedIn()) {
            logoutUser();
        }

        // Fetching user details from sqlite
        HashMap<String, String> user = internalDatabase.getUserDetails();

        String name = user.get("name");
        String email = user.get("email");

        // Setup the DrawerLayout and NavigationView.
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.navigationView);

        // Inflate the first fragment, which is also the holder of the tabbed swipe view.
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView, new TabFragment()).commit();

        // Setup click events on the Navigation View Items.
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();

                if (menuItem.getItemId() == R.id.nav_item_home) {
                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.replace(R.id.containerView, new TabFragment()).commit();
                }

                if (menuItem.getItemId() == R.id.nav_item_contacts) {
                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.replace(R.id.containerView, new ContactsFragment()).commit();
                }

                if (menuItem.getItemId() == R.id.nav_item_profile) {
                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.replace(R.id.containerView, new ProfileFragment()).commit();
                }

                if (menuItem.getItemId() == R.id.nav_item_logout) {
                    logoutUser();
                }
                return false;
            }
        });

        // Setup DrawerToggle of the Toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.app_name, R.string.app_name);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        // Get the Header
        View header = mNavigationView.getHeaderView(0);

        // Get the navigation drawers header values
        TextView tvUsername = (TextView) header.findViewById(R.id.header_username);
        TextView tvEmail = (TextView) header.findViewById(R.id.header_email);
        ImageView ivProfileImage = (ImageView) header.findViewById(R.id.header_profile_image);

        // Set the headers values
        tvUsername.setText(name);
        tvEmail.setText(email);
        // Set the user image in the header
        int imageResourceId = this.getResources().getIdentifier(
                "default_avatar", "drawable", this.getPackageName());
        Bitmap src = BitmapFactory.decodeResource(this.getResources(), imageResourceId);
        RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(this.getResources(), src);
        drawable.setCircular(true);
        ivProfileImage.setImageDrawable(drawable);
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }

    private void logoutUser() {
        sessionManager.setLogin(false);
        internalDatabase.deleteUsers();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
