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
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    EditText editTextLogin, editTextPassword, editTextPassword2,
            editTextEmail, editTextPhone, editTextAddress, editTextName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //if the user is already logged in we will directly start the profile activity
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, ContentActivity.class));
            return;
        }

        editTextLogin = (EditText) findViewById(R.id.editTextLogin);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextAddress = (EditText) findViewById(R.id.editTextAddress);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextPassword2 = (EditText) findViewById(R.id.editTextPassword2);

        Button buttonRegister = (Button) findViewById(R.id.buttonRegister);
        Button buttonGoToLogin = (Button) findViewById(R.id.buttonGoLogin);

        buttonRegister.setOnClickListener(view -> {
            registerUser();
        });

        buttonGoToLogin.setOnClickListener(view -> {
            // Launching All products Activity
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
        });



    }


    private void registerUser() {
        final String login = editTextLogin.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String phone = editTextPhone.getText().toString().trim();
        final String address = editTextAddress.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        final String password2 = editTextPassword2.getText().toString().trim();

        //first we will do the validations

        if (TextUtils.isEmpty(login)) {
            editTextLogin.setError("Введите логин");
            editTextLogin.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Введите почту");
            editTextEmail.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Введите правильную почту");
            editTextEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Введите пароль");
            editTextPassword.requestFocus();
            return;
        }

        if(!Objects.equals(password, password2)){
            editTextPassword2.setError("Пароли не совпадают");
            editTextPassword2.requestFocus();
            return;
        }

        //if it passes all the validations
        class RegisterUser extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("login", login);
                params.put("email", email);
                params.put("phone", phone);
                params.put("address", address);
                params.put("password", password);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_REGISTER, params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //hiding the progressbar after completion

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

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
//

                        //storing the user in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                        //starting the profile activity
                        finish();
                        startActivity(new Intent(getApplicationContext(), ContentActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        //executing the async task
        RegisterUser ru = new RegisterUser();
        ru.execute();
    }
}