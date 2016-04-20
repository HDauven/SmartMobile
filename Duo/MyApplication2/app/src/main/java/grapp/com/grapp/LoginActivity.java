package grapp.com.grapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        internalDatabase = new SQLiteHandler(getApplicationContext());
        sessionManager = new SessionManager(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

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
                String email = emailText.getText().toString();
                String password = passwordText.getText().toString();

                if (email.trim().length() > 0 && password.trim().length() > 0) {
                    checkLogin(email, password);
                } else {
                    Snackbar.make(view, "Please, enter your credentials.", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * Method to verify the login credentials in the MySQL Database
     * @param email
     * @param password
     */
    private void checkLogin(final String email, final String password) {
        String tag_string_request = "request_login";

        progressDialog.setMessage("Logging in");
        showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                GrappURL.API_URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj  = new JSONObject(response);
                    boolean error = jObj .getBoolean("error");

                    // Check for error node in the JSON response
                    if (!error) {
                        // User is logged in successfully, create login session
                        sessionManager.setLogin(true);

                        // Store the user in SQLite
                        String uid = jObj .getString("uid");
                        // Get the user object
                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String created_at = user.getString("created_at");
                        // Inserting a row in the users table
                        internalDatabase.addUser(name, email, uid, created_at);
                        // Launch MainActivity
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Error in login. Get the error message
                        String errorMessage = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "JSON error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Post parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);

                return params;
            }
        };

        // Adding request to the queue
        GrappController.getInstance().addToRequestQueue(stringRequest, tag_string_request);
    }

    private void showDialog() {
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    private void hideDialog() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
