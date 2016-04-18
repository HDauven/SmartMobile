package grapp.com.grapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Hein on 4/19/2016.
 */
public class SessionManager {
    // Shared Preferences, Editor and Context
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;
    private Context context;

    // SharedPreferences variable name
    private static final String IS_LOGGED_IN = "is_logged_in";

    // Constructor
    public SessionManager(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.context = context;
    }

    // Sets the login status of the user
    public boolean setLogin(boolean status) {
        sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putBoolean(IS_LOGGED_IN, status);
        sharedPreferencesEditor.commit();
        return true;
    }

    // Gets the login status of the user
    public boolean getLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }
}
