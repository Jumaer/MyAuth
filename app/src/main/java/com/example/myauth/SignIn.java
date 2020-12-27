package com.example.myauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {
    private EditText signIn_email,signIn_pass;
    private Button sign_in;
    private TextView move_sign_up, forget_password;
    private ProgressBar sign_in_p;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        signIn_email= findViewById(R.id.email_sign_in);
        signIn_pass = findViewById(R.id.password_sign_in);
        sign_in = findViewById(R.id.button_sign_in);
        move_sign_up = findViewById(R.id.go_sign_up);
        forget_password = findViewById(R.id.forgotten_pass);
        sign_in_p = findViewById(R.id.progressBar_sign_in);
        sign_in_p.setVisibility(View.GONE);



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
       //


        String email = signIn_email.getText().toString().trim();
        String password = signIn_pass.getText().toString().trim();

        if(email.isEmpty()){
            signIn_email.setError("Give Your email");
            signIn_email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            signIn_email.setError("Give your valid email");
            signIn_email.requestFocus();
            return;
        }



        if(password.isEmpty()){
            signIn_pass.setError("This pass is must");
            signIn_pass.requestFocus();
            return;
        }
        if(password.length()<7){
            signIn_pass.setError(" Pass must be more than 7 char or exact 7 char");
            signIn_pass.requestFocus();
            return;
        }

        sign_in_p.setVisibility(View.VISIBLE);

        if(!email.isEmpty()&& !password.isEmpty()){

            try{

                try{
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information

                                        // Main Work
                                        startActivity(new Intent(SignIn.this,MyView.class));
                                        finish();
                                        //Second Work..
                                        Toast.makeText(getApplicationContext(), "Authentication Successful...",
                                                Toast.LENGTH_LONG).show();

                                        sign_in_p.setVisibility(View.GONE);

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        sign_in_p.setVisibility(View.GONE);
                                        Toast.makeText(getApplicationContext(), "Authentication failed. ",
                                                Toast.LENGTH_LONG).show();

                                    }

                                    // ...
                                }
                            });
                }
                catch (Exception C){
                    Toast.makeText(getApplicationContext()," Something Wrong..   Error : "
                            +C.getCause().getMessage(),Toast.LENGTH_LONG).show();
                    sign_in_p.setVisibility(View.GONE);
                }

            }
            catch (Exception e){
                Toast.makeText(getApplicationContext()," Something Wrong..   Error : "
                        +e.getCause().getMessage(),Toast.LENGTH_LONG).show();
                sign_in_p.setVisibility(View.GONE);

            }

        }
        else{
            Toast.makeText(getApplicationContext()," Something Wrong..",Toast.LENGTH_LONG).show();
            sign_in_p.setVisibility(View.GONE);
        }
    }
}