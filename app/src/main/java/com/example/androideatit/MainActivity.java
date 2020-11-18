package com.example.androideatit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.analytics.FirebaseAnalytics;

public class MainActivity extends AppCompatActivity {
    Button btnsignup,btnsignin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnsignin=findViewById(R.id.btnSignIn);
        btnsignup=findViewById(R.id.btnSignUp);

        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signin=new Intent(MainActivity.this,SignIn.class);
                startActivity(signin);
            }
        });

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup=new Intent(MainActivity.this,SignUp.class);
                startActivity(signup);
            }
        });
    }
}
