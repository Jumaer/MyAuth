package com.example.myauth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {
    private EditText user_name,user_email,user_age,user_pass1,user_pass2;
    private Button sign_up;
    private TextView move_SignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);





        user_name = findViewById(R.id.sign_up_name);
        user_age = findViewById(R.id.sign_up_age);
        user_email = findViewById(R.id.sign_up_email);
        user_pass1 = findViewById(R.id.sign_up_password);
        user_pass2 = findViewById(R.id.sign_up_conform_password);

        sign_up = findViewById(R.id.button_sign_up);
        move_SignIn = findViewById(R.id.move_sign_in);




        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               register_Sign_up();
            }
        });



        move_SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move_sign_in();
            }
        });
    }

    private void move_sign_in() {
        startActivity(new Intent(SignUp.this,SignIn.class));
    }

    private void register_Sign_up() {
        Toast.makeText(getApplicationContext()," This feature is updating..",Toast.LENGTH_LONG).show();

    }
}