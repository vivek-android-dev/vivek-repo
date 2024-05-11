package com.example.firstproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firstproject.API.RetroClient;
import com.example.firstproject.APIResponse.LoginResponse;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    EditText emailEditText, passwordEditText;
    Button loginButton;


    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.buttonLogin);

        findViewById(R.id.signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SignUp.class));
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                performLogin(email,password);
            }
        });
    }

    private void performLogin(String email, String password) {
        Toast.makeText(this, ""+email+password, Toast.LENGTH_SHORT).show();
        Call<LoginResponse> res = RetroClient.makeApi().login(email,password);
        res.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()){
                Toast.makeText(MainActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("LoginResponse", "onResponse: "+response.body().getMessage());
                }
                else
                    Toast.makeText(MainActivity.this, " onResponse "+response.isSuccessful(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("TAG", "onFailure: "+t.getMessage());
                Toast.makeText(MainActivity.this, " onResponse "+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onPause() {
        compositeDisposable.clear();
        super.onPause();
    }

    //    private void performLogin(final String email, final String password) {
//        new Thread(() -> {
//            HttpURLConnection urlConnection = null;
//            try {
//                URL url = new URL("http://localhost:3000/login/");
//                urlConnection = (HttpURLConnection) url.openConnection();
//                urlConnection.setRequestMethod("POST");
//                urlConnection.setRequestProperty("Content-Type", "application/json");
//                urlConnection.setDoOutput(true);
//
//                // Create JSON object with email and password
//                JSONObject jsonParam = new JSONObject();
//                jsonParam.put("email", email);
//                jsonParam.put("password", password);
//
//                // Send POST data
//                OutputStream os = urlConnection.getOutputStream();
//                os.write(jsonParam.toString().getBytes(StandardCharsets.UTF_8));
//                os.flush();
//                os.close();
//
//                // Get response
//                InputStream in = urlConnection.getInputStream();
//                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//                StringBuilder response = new StringBuilder();
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    response.append(line);
//                }
//                reader.close();
//
//                // Parse response JSON
//                JSONObject jsonResponse = new JSONObject(response.toString());
//
//                // Handle response on UI thread
//                final boolean success = jsonResponse.getBoolean("success");
//                runOnUiThread(() -> {
//                    if (success) {
//                        Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
//                        // Proceed to next activity or perform desired action
//                    } else {
//                        Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//            } catch (IOException | JSONException e) {
//                e.printStackTrace();
//            } finally {
//                if (urlConnection != null) {
//                    urlConnection.disconnect();
//                }
//            }
//        }).start();
//    }

}