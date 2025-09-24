package com.example.d424jones.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.d424jones.R;
import com.example.d424jones.database.Repository;
import com.example.d424jones.entities.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {


    private EditText usernameInput;
    private EditText passwordInput;
    private Button loginButton;
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        repository = new Repository(getApplication());

        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);

        repository.getUserByUsername("admin", user -> {
            if(user == null){
                repository.insertUser(new User("admin", "password123"));

            }
        });
      //  repository.insertUser(new User("admin", "password123"));
        loginButton.setOnClickListener(view -> {
                String username = usernameInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();

                if(username.isEmpty() || password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
                    return;
                }
                repository.getUserByUsername(username, user ->{
                        runOnUiThread(() -> {
                            if(user != null && repository.checkPassword(password, user.getPassword())){
                                Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();

                            } else{
                                Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();

                            }
                        });
                    });
                  });

        }
}
