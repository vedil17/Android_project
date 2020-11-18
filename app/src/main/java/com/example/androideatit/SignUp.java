package com.example.androideatit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.androideatit.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignUp extends AppCompatActivity {

    MaterialEditText edtphone,edtname,edtpassword;
    Button btnsignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtphone=(MaterialEditText)findViewById(R.id.edtphone);
        edtname=(MaterialEditText)findViewById(R.id.edtname);
        edtpassword=(MaterialEditText)findViewById(R.id.edtpassword);

        btnsignup=findViewById(R.id.btnSignUp);
        //init Firebase
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        final DatabaseReference table_user=database.getReference("User");

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog mDialog=new ProgressDialog(SignUp.this);
                mDialog.setMessage("Please waiting ...");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(edtphone.getText().toString()).exists()){
                            mDialog.dismiss();
                            Toast.makeText(SignUp.this, "Phone number already register !!!", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            mDialog.dismiss();
                            User user=new User(edtname.getText().toString(),edtpassword.getText().toString());
                            table_user.child(edtphone.getText().toString()).setValue(user);
                            Toast.makeText(SignUp.this, "Sign Up successfully !", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }
}
