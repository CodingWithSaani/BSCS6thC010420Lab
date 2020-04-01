package com.ekorydes.bscs6thc010420lab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainContentPage extends AppCompatActivity {

    private FirebaseAuth objectFirebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_content_page);

        objectFirebaseAuth=FirebaseAuth.getInstance();
        if(objectFirebaseAuth.getCurrentUser()!=null)
        {
            Toast.makeText(this, "You are sign in:"+objectFirebaseAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "No user is sign in", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (objectFirebaseAuth.getCurrentUser()!=null)
        {
            objectFirebaseAuth.signOut();
        }
    }
}
