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

/**
 * Created by Hein on 4/19/2016.
 */
public class RegistrationActivity extends AppCompatActivity {
    // LogCat TAG
    private static final String TAG = RegistrationActivity.class.getSimpleName();

    private Button registerButton;
    private Button backButton;
    private EditText fullNameText;
    private EditText emailText;
    private EditText passwordText;
    private ProgressDialog progressDialog;
    private SessionManager sessionManager;
    private SQLiteHandler internalDatabase;
    JSONParser jsonParser = new JSONParser();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        sessionManager = new SessionManager(this);
        internalDatabase = new SQLiteHandler(getApplicationContext());

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        // Check if the user is already logged into Grapp
        if (sessionManager.getLoggedIn()) {
            // User is already logged in, redirect to the MainActivity
            Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        fullNameText = (EditText) findViewById(R.id.full_name_text_register);
        emailText = (EditText) findViewById(R.id.email_text_register);
        passwordText = (EditText) findViewById(R.id.password_text_register);

        registerButton = (Button) findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = fullNameText.getText().toString().trim();
                String email = emailText.getText().toString().trim();
                String password = passwordText.getText().toString().trim();
                new CreateUser().execute(name, email, password);
            }
        });

        backButton = (Button) findViewById(R.id.register_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private class CreateUser extends AsyncTask<String, String, String> {

        boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(RegistrationActivity.this);
            progressDialog.setMessage("Creating User");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... arguments) {
            int success;
            String username = arguments[0];
            String email = arguments[1];
            String password = arguments[2];
            try {
                // Building parameters
                List<NameValuePair> parameters = new ArrayList<>();
                parameters.add(new BasicNameValuePair("username", username));
                parameters.add(new BasicNameValuePair("email", email));
                parameters.add(new BasicNameValuePair("password", password));
                Log.d("request", "starting");
                // Make the HTTP request
                JSONObject json = jsonParser.makeHttpRequest(GrappURL.API_URL_REGISTER, "POST", parameters);
                // Check the log for a JSON response
                Log.d("User create attempt", json.toString());
                // JSON success tag
                success = json.getInt(GrappURL.TAG_SUCCESS);
                if (success == 1) {
                    Log.d("User created", json.toString());
                    sessionManager.setLogin(true);

                    // Insert user into the internal database
                    internalDatabase.addUser(username, email, json.getString("created_at"));

                    Intent i = new Intent(RegistrationActivity.this, MainActivity.class);
                    finish();
                    startActivity(i);
                    return json.getString(GrappURL.TAG_MESSAGE);
                } else {
                    Log.d("User creation failed", json.getString(GrappURL.TAG_MESSAGE));
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
                Toast.makeText(RegistrationActivity.this, url, Toast.LENGTH_LONG).show();
            }
        }
    }
}
