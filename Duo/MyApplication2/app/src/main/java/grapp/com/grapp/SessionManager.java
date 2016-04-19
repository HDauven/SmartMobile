package grapp.com.grapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Hein on 4/19/2016.
 */
public class SessionManager {
    // LogCat TAG
    private static String TAG = SessionManager.class.getSimpleName();

    // Shared Preferences, Editor and Context
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;
    private Context context;

    // SharedPreferences mode
    int PRIVATE_MODE = 0;

    // SharedPreferences variable name
    private static final String PREF_NAME = "GrappLogin";
    private static final String IS_LOGGED_IN = "is_logged_in";

    // Constructor
    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        sharedPreferencesEditor = sharedPreferences.edit();
    }

    // Sets the login status of the user
    public void setLogin(boolean status) {
        sharedPreferencesEditor.putBoolean(IS_LOGGED_IN, status);
        sharedPreferencesEditor.commit();
        Log.d(TAG, "User session modified");
    }

    // Gets the login status of the user
    public boolean getLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }
}
