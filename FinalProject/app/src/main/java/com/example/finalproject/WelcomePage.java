package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.model.Person;
import com.firebase.ui.auth.data.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class WelcomePage extends AppCompatActivity {
    private TextView welcome;
    private TextView logInAs;
    private Button buttonContinue;
    private FirebaseAuth mFirebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        welcome = (TextView) findViewById(R.id.welcomeText);
        logInAs = (TextView) findViewById(R.id.loggedInAsText);
        //buttonContinue = (Button) findViewById(R.id.);
        //buttonContinue.setVisibility(View.Gone);
        // buttonContinue.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //                openAdminChoose();
        //            }
        //        });

        final String userID = mFirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("users/"+userID);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            //The key argument here must match that used in the other activity

            db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //for(DataSnapshot snapShot : dataSnapshot.getChildren()) {

                    /*String name = dataSnapshot.child("name").getValue(String.class);
                    String role = dataSnapshot.child("role").getValue(String.class);*/
                    Person user = dataSnapshot.getValue(Person.class);
                    String name = user.getName();
                    String role = user.getRole();
                    welcome.setText("Welcome, " + name);
                    logInAs.setText("You are logged in as: " + role);
                    //}
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }


            });
        }
        else{
            welcome.setText("Welcome");
            logInAs.setText("You are logged in as: Administrator");
            //buttonContinue.setVisibility(View.VISIBLE);
        }


    }


    public void openAdminChoose(){
        Intent intent = new Intent(this, AdminChoose.class);
        startActivity(intent);
    }
}
