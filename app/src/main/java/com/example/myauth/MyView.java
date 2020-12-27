package com.example.myauth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MyView extends AppCompatActivity {
     private Button logout;
     private ImageView settings;
     FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_view);
        firebaseAuth= FirebaseAuth.getInstance();

        logout = findViewById(R.id.button_log_out);
        settings= findViewById(R.id.settings);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sign_out();
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings_move();
            }
        });
    }

    private void settings_move() {
        startActivity(new Intent(MyView.this,SettingsActivity.class));
        Toast.makeText(getApplicationContext(),"Going for settings",Toast.LENGTH_LONG).show();
      //  finish();
    }

    private void sign_out() {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(MyView.this,SignIn.class));
        Toast.makeText(getApplicationContext(),"Logging out..",Toast.LENGTH_LONG).show();
        finishAffinity();
    }
}