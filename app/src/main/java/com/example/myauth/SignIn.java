package com.example.myauth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignIn extends AppCompatActivity {
    private EditText signIn_email,signIn_pass;
    private Button sign_in;
    private TextView move_sign_up, forget_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signIn_email= findViewById(R.id.email_sign_in);
        signIn_pass = findViewById(R.id.password_sign_in);
        sign_in = findViewById(R.id.button_sign_in);
        move_sign_up = findViewById(R.id.go_sign_up);
        forget_password = findViewById(R.id.forgotten_pass);



        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInAccount();
            }
        });

        move_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sign_up_move();
            }
        });
        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passForget();

            }
        });



    }

    private void passForget() {
        Toast.makeText(getApplicationContext()," This feature is updating..",Toast.LENGTH_LONG).show();
    }

    private void sign_up_move() {
        startActivity(new Intent(SignIn.this,SignUp.class));
    }

    private void signInAccount() {
        Toast.makeText(getApplicationContext()," This feature is updating..",Toast.LENGTH_LONG).show();
    }
}