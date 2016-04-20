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

                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                    registerUser(name, email, password);
                } else {
                    Snackbar.make(view, "Please enter all fields.", Snackbar.LENGTH_LONG).show();
                }
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

    private void registerUser(final String name, final String email, final String password) {
        // Tag that is used to cancel the request
        String tag_string_request = "request_register";

        progressDialog.setMessage("Registering account");
        showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                GrappURL.API_URL_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) { // User is successfully added to the MySQL database
                        // First, store the user in the SQLite database
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String created_at = user.getString("created_at");

                        // Insert user into the internal database
                        internalDatabase.addUser(name, email, uid, created_at);

                        Toast.makeText(getApplicationContext(), "User is succesfully registered.", Toast.LENGTH_LONG).show();

                        // Redirect user to the LoginActivity
                        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        String errorMessage = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Post parameters to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);

                return params;
            }
        };
        GrappController.getInstance().addToRequestQueue(stringRequest, tag_string_request);
    }

    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }
}
