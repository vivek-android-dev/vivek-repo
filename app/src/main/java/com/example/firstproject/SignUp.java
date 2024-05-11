package com.example.firstproject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.firstproject.API.RetroClient;
import com.example.firstproject.APIResponse.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity {

    EditText name,email,password;
    Button loginbtn;
    TextView signup;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        name = findViewById(R.id.editTextName);
        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        signup = findViewById(R.id.login);
        loginbtn = findViewById(R.id.register);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               goLogin(name.getText().toString(),email.getText().toString(),password.getText().toString());
            }
        });
    }

    private void goLogin(String name, String email, String password) {

        Call<LoginResponse> res = RetroClient.makeApi().register(name,email,password);
        res.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().getMessage()!=null) {
                        if (response.body().getMessage().equals("Registration successful")) {
                            Toast.makeText(SignUp.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (response.body().getMessage().equals("User already exists")) {
                            Toast.makeText(SignUp.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(SignUp.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}