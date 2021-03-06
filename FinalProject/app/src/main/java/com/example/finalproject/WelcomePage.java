package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.finalproject.dev3.ClinicAbout;
import com.example.finalproject.dev4.DayTimeResults;
import com.example.finalproject.dev4.FindClinic;
import com.example.finalproject.dev4.RateClinic;
import com.example.model.Employee;
import com.example.model.Patient;
import com.example.model.Person;
import com.example.model.Role;
import com.example.model.WalkInClinic;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class WelcomePage extends AppCompatActivity {
    private TextView welcome;
    private TextView logInAs;
    private Button buttonContinue;
    private String login="Admin";
    private Button logOutButton;
    private FirebaseAuth mFirebaseAuth;
    private boolean hasToRateClinic;
    private String clinicName=null;
    private String clinicAddress=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        welcome = (TextView) findViewById(R.id.welcomeText);
        logInAs = (TextView) findViewById(R.id.loggedInAsText);
        buttonContinue = (Button) findViewById(R.id.continueBtn);
        //buttonContinue.setVisibility(View.GONE);
        buttonContinue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openChoose();
                    }
                });

        final String userID = mFirebaseAuth.getInstance().getCurrentUser().getUid();
        final DatabaseReference db = FirebaseDatabase.getInstance().getReference("users/"+userID);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            //The key argument here must match that used in the other activity

            db.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot snapShot : dataSnapshot.getChildren()) {

                        //String name = dataSnapshot.child("name").getValue(String.class);
                        //String role = dataSnapshot.child("role").getValue(String.class);
                        Person user = dataSnapshot.getValue(Person.class);
                        String name = user.getName();
                        String role = user.getRole().toString();
                        login = role;
                        welcome.setText("Welcome, " + name);
                        logInAs.setText("You are logged in as: " + role);

                        if(login.equals("Patient")){
                            Patient patient = dataSnapshot.getValue(Patient.class);
                            if(patient.isItTimeToRate()){
                                hasToRateClinic=true;
                                clinicName = patient.getLastClinicName();
                                clinicAddress= patient.getLastClinicAddress();
                                db.setValue(user);

                            }
                        }
                    }
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

        }

        //user can log out, which will bring them to sign in page
        logOutButton = (Button) findViewById(R.id.logOutBtn);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity_goToSignIn();
            }
        });


    }


    public void openChoose(){
        if(login.equals("Admin")) {
            Intent intent = new Intent(this, AdminChoose.class);
            startActivity(intent);
        }
        else if(login.equals("Employee")){
            Intent intent = new Intent(this, ClinicAbout.class);
            startActivity(intent);
        }
        else if(login.equals("Patient")){
            System.err.println("Has to Rate");
            System.err.println(hasToRateClinic);
            if(hasToRateClinic){
                Intent intent = new Intent(this, RateClinic.class);
                intent.putExtra("clinicName",clinicName);
                intent.putExtra("clinicAddress",clinicAddress);
                startActivity(intent);
            }
            else {
                Intent intent = new Intent(this, FindClinic.class);
                startActivity(intent);
            }
        }
    }

    public void openactivity_goToSignIn(){
        Intent intent = new Intent(WelcomePage.this, activity_sign_in.class);
        mFirebaseAuth.getInstance().signOut();
        startActivity(intent);
    }


}
