package com.example.knowbeforeyoushop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    EditText mEmail,mPassword;
    Button mLoginBtn;
    TextView mCreateBtn, forgotTextLink;
    ProgressBar progressBar;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.Password);
        progressBar  = findViewById(R.id.progressBar);
        fAuth = FirebaseAuth.getInstance();
        mLoginBtn =  findViewById(R.id.loginBtr);
        mCreateBtn =  findViewById(R.id.createText);
        forgotTextLink = findViewById(R.id.forgotPassword);

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is required");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is required");
                    return;
                }

                if (password.length() < 6) {
                    mPassword.setError("Password must be more than 6 characters");
                    return;
                }


                //authenticate user

                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            String uid = task.getResult().getUser().getUid();

                            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                            firebaseDatabase.getReference().child("Users").child(uid).child("usertype")
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    int usertype = snapshot.getValue(Integer.class);
                                    if (usertype == 0) {
                                        Toast.makeText(Login.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                                        Intent in = new Intent(Login.this, MainActivity.class);
                                        startActivity(in);
                                    }

                                    if (usertype == 1) {
                                        Toast.makeText(Login.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                                        Intent in = new Intent(Login.this, AdminMainActivity.class);
                                        startActivity(in);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });


                        } else {
                            Toast.makeText(Login.this, "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                            //progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });



        forgotTextLink.setOnClickListener(view -> {
            final EditText resetMail = new EditText(view.getContext());
            final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
            passwordResetDialog.setTitle("Reset Password?");
            passwordResetDialog.setMessage("Enter your email to receive reset link");
            passwordResetDialog.setView(resetMail);

            passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //extract email and send reset link
                    String mail = resetMail.getText().toString();
                    fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(Login.this, "Reset link sent to your mail", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(e -> Toast.makeText(Login.this, "Error! Reset link not sent" + e.getMessage(), Toast.LENGTH_SHORT).show());
                }
            });

            passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //close dialog box
                }
            });

            passwordResetDialog.create().show();
            });

        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoCreate = new Intent(Login.this,Reg.class);
                startActivity(gotoCreate);
            }
        });
    }
}
