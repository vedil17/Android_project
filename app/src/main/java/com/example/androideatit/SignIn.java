package com.example.androideatit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androideatit.Common.Common;
import com.example.androideatit.Model.User;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignIn extends AppCompatActivity {
    EditText edtphone,edtpassword;
    Button btnSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edtphone=(MaterialEditText)findViewById(R.id.edtphone);
        edtpassword=(MaterialEditText)findViewById(R.id.edtpassword);
        btnSignIn=(Button)findViewById(R.id.btnSignIn);

        //init Firebase
         FirebaseDatabase database=FirebaseDatabase.getInstance();
        final DatabaseReference table_user=database.getReference("User");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog mDialog=new ProgressDialog(SignIn.this);
                mDialog.setMessage("Please waiting ...");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //check if user not exist in database
                        if (dataSnapshot.child(edtphone.getText().toString()).exists()) {

                            //get user information
                            mDialog.dismiss();
                            User user = dataSnapshot.child(edtphone.getText().toString()).getValue(User.class);
                            user.setPhone(edtphone.getText().toString());
                            if (user.getPassword().equals(edtpassword.getText().toString()))
                            {
                                Intent homeIntent=new Intent(SignIn.this,Home.class);
                                Common.currentUser=user;
                                startActivity(homeIntent);
                                finish();
                            }
                            else {
                                Toast.makeText(SignIn.this, "Wrong password !!!", Toast.LENGTH_LONG).show();
                            }
                        }
                        else {
                            mDialog.dismiss();
                            Toast.makeText(SignIn.this, "User does not exist in database !!!", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
