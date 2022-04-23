package com.example.Khanalas_food;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    EditText editTextLogin, editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Khanalas_food_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //if the user is already logged in we will directly start the profile activity
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, ContentActivity.class));
            return;
        }

        editTextLogin = (EditText) findViewById(R.id.editTextLogin);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        Button buttonLogin = (Button) findViewById(R.id.buttonLogin);
        Button buttonGoToRegister = (Button) findViewById(R.id.buttonGoToRegister);

        buttonLogin.setOnClickListener(view -> {
            userLogin();
        });

        buttonGoToRegister.setOnClickListener(view -> {
            // Launching All products Activity
            Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(i);
        });
    }

    private void userLogin() {
        //first getting the values
        final String login = editTextLogin.getText().toString();
        final String password = editTextPassword.getText().toString();

        //validating inputs
        if (TextUtils.isEmpty(login)) {
            editTextLogin.setError("Введите логин");
            editTextLogin.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Введите пароль");
            editTextPassword.requestFocus();
            return;
        }

        //if everything is fine

        class UserLogin extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getBaseContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //getting the user from the response
                        JSONObject userJson = obj.getJSONObject("user");

                        //creating a new user object
                        User user = new User(
                                userJson.getInt("id"),
                                userJson.getString("login"),
                                userJson.getString("email"),
                                userJson.getString("phone"),
                                userJson.getString("address"),
                                userJson.getString("name")
                        );

                        //storing the user in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                        //starting the profile activity
                        finish();
                        startActivity(new Intent(getApplicationContext(), ContentActivity.class));
                    } else {
                        Toast.makeText(getBaseContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                        editTextLogin.setError("Неверный логин или пароль");
                        editTextLogin.requestFocus();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("login", login);
                params.put("password", password);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_LOGIN, params);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();
    }
}