package com.ekorydes.bscs6thc010420lab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInAct extends AppCompatActivity {

    private EditText emailET,passwordET;
    private Button signInUserBtn;

    private ProgressBar objectProgressBar;
    private FirebaseAuth objectFirebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        connectXMLToJava();
        objectFirebaseAuth=FirebaseAuth.getInstance();
    }

    private void connectXMLToJava()
    {
        try
        {
            emailET=findViewById(R.id.userEmailET);
            passwordET=findViewById(R.id.userPasswordET);

            signInUserBtn=findViewById(R.id.signInUser);
            objectProgressBar=findViewById(R.id.signInUserProgressBar);

            signInUserBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    signInUser();
                }
            });
        }
        catch (Exception e)
        {
            Toast.makeText(this, "connectXMLToJava:"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void signInUser()
    {
        try
        {
            if(!emailET.getText().toString().isEmpty() && !passwordET.getText().toString().isEmpty())
            {
                if(objectFirebaseAuth!=null)
                {
                    if(objectFirebaseAuth.getCurrentUser()!=null)
                    {
                        objectFirebaseAuth.signOut();
                        Toast.makeText(this, "Sign Out Successfully", Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        objectProgressBar.setVisibility(View.VISIBLE);
                        signInUserBtn.setEnabled(false);

                        objectFirebaseAuth.signInWithEmailAndPassword(emailET.getText().toString(),
                                passwordET.getText().toString())
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        Toast.makeText(SignInAct.this, "User sign in successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SignInAct.this,MainContentPage.class));

                                        finish();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        objectProgressBar.setVisibility(View.INVISIBLE);
                                        signInUserBtn.setEnabled(true);

                                        Toast.makeText(SignInAct.this, "Fails to sign in user:"+
                                                e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }
            }
            else if(emailET.getText().toString().isEmpty())
            {
                Toast.makeText(this, "Please enter the email", Toast.LENGTH_SHORT).show();
                emailET.requestFocus();
            }
            else if(passwordET.getText().toString().isEmpty())
            {
                Toast.makeText(this, "Please enter the password", Toast.LENGTH_SHORT).show();
                passwordET.requestFocus();
            }
        }
        catch (Exception e)
        {
            Toast.makeText(this, "signInUser:"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
