package com.example.myauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static java.lang.Integer.parseInt;

public class SignUp extends AppCompatActivity {
    private EditText user_name,user_email,user_age,user_pass1,user_pass2;
    private Button sign_up;
    private TextView move_SignIn;
    private ProgressBar sign_up_progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //from firebase auth creating..
        mAuth = FirebaseAuth.getInstance();

       // progress bar initialize and visibility setup..
        sign_up_progressBar= findViewById(R.id.progressBar_sign_up);
        sign_up_progressBar.setVisibility(View.GONE);

        // User information field id initialize..
        user_name = findViewById(R.id.sign_up_name);
        user_age = findViewById(R.id.sign_up_age);
        user_email = findViewById(R.id.sign_up_email);
        user_pass1 = findViewById(R.id.sign_up_password);
        user_pass2 = findViewById(R.id.sign_up_conform_password);

       // For sign up button..
        sign_up = findViewById(R.id.button_sign_up);
        // if user have an account..then click text..
        move_SignIn = findViewById(R.id.move_sign_in);



      // if you are creating new id ..
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               register_Sign_up();
            }
        });


      // if user have an account..
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

// Data is putting..
        String name = user_name.getText().toString().trim();
        String email = user_email.getText().toString().trim();
        String age = user_age.getText().toString().trim();
        String password1 = user_pass1.getText().toString().trim();
        String password2 = user_pass2.getText().toString().trim();



        // Name checker..

        if(name.isEmpty()){
            user_name.setError("Name field is must");
            user_name.requestFocus();
            return;
        }
        if(!name.isEmpty() && name.length()>22){
            user_name.setError("Name is too long");
            user_name.requestFocus();
            return;
        }


        // Age checker..

        if(age.isEmpty()){
            user_age.setError("Age field is must");
            user_age.requestFocus();
            return;
        }

        try{
           if( Integer.parseInt(age)<12)  {
            user_age.setError("Too young, need more than 11 years");
            user_age.requestFocus();
            return;
        }
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext()," Something Wrong in age field..  Error: "
                    +e.getCause().getMessage(),Toast.LENGTH_LONG).show();
        }


      // Email Check


        if(email.isEmpty()){
            user_email.setError("This email is must");
            user_email.requestFocus();
            return;
        }
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            user_email.setError("Need a valid email");
            user_email.requestFocus();
            return;
        }


        //password check ..


        if(password1.isEmpty()){
            user_pass1.setError("Need a password at least 7 char or letters,numbers,symbols");
            user_pass1.requestFocus();
            return;
        }
        if(password2.isEmpty()){
            user_pass2.setError(" Need a pass to match with the first");
            user_pass2.requestFocus();
            return;
        }
        if(password1.length()<7){
            user_pass1.setError("Need a password at least 7 char or letters,numbers,symbols");
            user_pass1.requestFocus();
            return;
        }
        if(password2.length()<7){
            user_pass2.setError("Need a password at least 7 char or letters,numbers,symbols");
            user_pass2.requestFocus();
            return;
        }



     // Sign Up/ Creating the Account..

        if(!name.isEmpty() && !password2.isEmpty() && !password1.isEmpty() && !email.isEmpty() && !age.isEmpty()){
            // Starting the progress
            sign_up_progressBar.setVisibility(View.VISIBLE);
            try{
          // if password matches..
                if(password1.matches(password2)){

                    mAuth.createUserWithEmailAndPassword(email, password1)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information

                                        FirebaseDatabaseUsers databaseUsers = new FirebaseDatabaseUsers(name,email,age);
                                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                                        DatabaseReference myRef = database.getReference("myAuthUsers");
                                        myRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(databaseUsers).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){

                                                    Toast.makeText(getApplicationContext(), "Authentication Successful, no Error.",
                                                            Toast.LENGTH_SHORT).show();

                                                  // Main Work
                                                    startActivity(new Intent(SignUp.this,MyView.class));
                                                    finish();


                                                    // Second Work
                                                    sign_up_progressBar.setVisibility(View.GONE);


                                                }
                                                else{
                                                    // Main Work
                                                        Toast.makeText(getApplicationContext(), "Authentication is not Successful,  Error: "
                                                                        +task.getException().getCause().getMessage(),
                                                                Toast.LENGTH_SHORT).show();
                                                    // Second Work
                                                        sign_up_progressBar.setVisibility(View.GONE);


                                                }

                                            }
                                        });

                                    }


                                    else if(task.isCanceled()){
                                        // Main Work
                                        Toast.makeText(getApplicationContext(), "Authentication canceled.. Error:"
                                                        +task.getException().getCause().getMessage(),
                                                Toast.LENGTH_SHORT).show();
                                        // Second Work
                                        sign_up_progressBar.setVisibility(View.GONE);
                                    }
                                    else {
                                        // If sign in fails, display a message to the user.
                                        if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                            Toast.makeText(getApplicationContext(), "Already used this email..",
                                                    Toast.LENGTH_LONG).show();
                                            //progressbar gone
                                            sign_up_progressBar.setVisibility(View.GONE);
                                        }
                                        else{
                                            Toast.makeText(getApplicationContext(), "Authentication failed.. Error:"
                                                            +task.getException().getCause().getMessage(),
                                                    Toast.LENGTH_SHORT).show();
                                            sign_up_progressBar.setVisibility(View.GONE);
                                        }


                                    }


                                    // ...
                                }
                            });


                }
          // if password doesn't match ..
                else{
                    // Second Work
                    sign_up_progressBar.setVisibility(View.GONE);
                    // Main Work
                    Toast.makeText(getApplicationContext()," Two passwords doesn't match.. "
                           ,Toast.LENGTH_LONG).show();
                }

            }
            catch (Exception m){
                // Main Work
                sign_up_progressBar.setVisibility(View.GONE);
                // Second Work
                Toast.makeText(getApplicationContext()," Something Wrong in Authentication..  Error: "
                        +m.getCause().getMessage(),Toast.LENGTH_LONG).show();
            }

        }

    }


}