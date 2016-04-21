package grapp.com.grapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import grapp.com.grapp.util.GrappURL;
import grapp.com.grapp.util.JSONParser;
import grapp.com.grapp.util.SQLiteHandler;
import grapp.com.grapp.util.SessionManager;

/**
 * Created by Hein on 4/19/2016.
 */
public class LoginActivity extends AppCompatActivity {
    // LogCat TAG
    private static final String TAG = LoginActivity.class.getSimpleName();

    private Button registrationButton;
    private Button loginButton;
    private EditText emailText;
    private EditText passwordText;
    private ProgressDialog progressDialog;
    private SessionManager sessionManager;
    private SQLiteHandler internalDatabase;
    JSONParser jsonParser = new JSONParser();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        internalDatabase = new SQLiteHandler(getApplicationContext());
        sessionManager = new SessionManager(this);

        emailText = (EditText) findViewById(R.id.email_text_login);
        passwordText = (EditText) findViewById(R.id.password_text_login);

        // Check if the user is already logged into Grapp
        if (sessionManager.getLoggedIn()) {
            // User is already logged in, redirect to the MainActivity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        // Find registration button and link it to the RegistrationActivity
        registrationButton = (Button) findViewById(R.id.registration_button);
        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(intent);
            }
        });

        // Find login button and execute login
        loginButton = (Button) findViewById(R.id.sign_in_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailText.getText().toString().trim();
                String password = passwordText.getText().toString().trim();
                new AttemptLogin().execute(email, password);
            }
        });
    }

    // AsyncTask creates a separate thread that runs along side the GUI thread.
    // This is done to prevent GUI lag from occurring.
    private class AttemptLogin extends AsyncTask<String, String, String> {

        boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("Logging in");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... arguments) {
            int success;
            String email = arguments[0];
            String password = arguments[1];
            try {
                // Building parameters
                List<NameValuePair> parameters = new ArrayList<>();
                parameters.add(new BasicNameValuePair("email", email));
                parameters.add(new BasicNameValuePair("password", password));
                Log.d("request", "starting");
                // Make the HTTP request
                JSONObject json = jsonParser.makeHttpRequest(GrappURL.API_URL_LOGIN, "POST", parameters);
                // Check the log for a JSON response
                Log.d("Login attempt", json.toString());
                // JSON success tag
                success = json.getInt(GrappURL.TAG_SUCCESS);
                if (success == 1) {
                    Log.d("Login successful", json.toString());
                    sessionManager.setLogin(true);

                    // Inserting a row in the users table
                    internalDatabase.addUser(json.getString("username"),
                            json.getString("email"), json.getString("created_at"));

                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    finish();
                    startActivity(i);
                    return json.getString(GrappURL.TAG_MESSAGE);
                } else {
                    Log.d("Login failed", json.getString(GrappURL.TAG_MESSAGE));
                    return json.getString(GrappURL.TAG_MESSAGE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String url) {
            progressDialog.dismiss();
            if (url != null) {
                Toast.makeText(LoginActivity.this, url, Toast.LENGTH_LONG).show();
            }
        }
    }
}
