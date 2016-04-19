package grapp.com.grapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
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

    private Button registrationButton;
    private Button loginButton;

    private EditText emailText;
    private EditText passwordText;

    private ProgressDialog progressDialog;

    private SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        registrationButton = (Button) findViewById(R.id.registration_button);
        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(intent);
            }
        });

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

        emailText = (EditText) findViewById(R.id.email_text_login);
        passwordText = (EditText) findViewById(R.id.password_text_login);
    }

    private void checkLogin(final String email, final String password) {
        String tag_string_request = "request_login";

        progressDialog.setMessage("Logging in");
        showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                GrappURL.API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideDialog();

                try {
                    JSONObject jUserId = new JSONObject(response);
                    String userId = jUserId.getString("user_id");
                    if (userId != null) {
                        sessionManager.setLogin(true);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        String errorMsg = jUserId.getString("error_msg");
                        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Post params to login url
                Map<String, String> params = new HashMap<>();
                params.put("tag", "login");
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
