package com.example.knowbeforeyoushop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    public static final String TAG = "TAG";

    EditText mFullName,mEmail,mPassword;
    Button mRegisterBtn;
    TextView mLoginBtr;
    FirebaseAuth fAuth=FirebaseAuth.getInstance();
    ProgressBar progressBar;
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText mFullName    =  findViewById(R.id.fullName);
        EditText mEmail       =  findViewById(R.id.Email);
        EditText mPassword    =  findViewById(R.id.Password);
        mRegisterBtn =  findViewById(R.id.registerBtn);
        mLoginBtr    =  findViewById(R.id.createText);

        fAuth  = FirebaseAuth.getInstance();
        progressBar  = findViewById(R.id.progressBar);

        if(fAuth.getCurrentUser() != null){
            Intent intent=new Intent(Register.this,MainActivity.class);
            startActivity(intent);
            finish();
        }


        mRegisterBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String fullName=mFullName.getText().toString();

                if(TextUtils.isEmpty(fullName)){
                    mFullName.setError("Name is required");
                    return;
                }

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is required");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is required");
                    return;
                }

                if(password.length() < 6){
                    mPassword.setError("Password must be more than 6 characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);



                //register the user in firebase

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {

                            //send verification mail


                            FirebaseUser fuser = fAuth.getCurrentUser();
                            fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Register.this, "Verification email sent",Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: Email not sent" + e.getMessage());
                                }
                            });

                            String uid=task.getResult().getUser().getUid();
                            Users user = new Users(uid,fullName,email,password,0);


                            firebaseDatabase.getReference().child("Users").child(uid).setValue(user);


                            Toast.makeText(Register.this, "User created", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(Register.this,MainActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(Register.this,"Error"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        mLoginBtr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoLogin = new Intent(Register.this,Login.class);
                startActivity(gotoLogin);
            }
        });

    }
}