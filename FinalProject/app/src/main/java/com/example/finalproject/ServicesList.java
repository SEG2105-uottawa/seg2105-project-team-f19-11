package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ServicesList extends AppCompatActivity {
    private ImageButton buttonBack ;
    private ListView serviceList;
    private ArrayList<HashMap<String,String>> serviceData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_list);

        serviceList = (ListView) findViewById(R.id.servicesList);
        serviceData = new ArrayList<HashMap<String, String>>();

        /*DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("clinics").child(clinicName);
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds: dataSnapshot.getChildren()) {
                            String name = ds.getValue(String.Class);
                            String role = ds.getChild().getValue(String.Class);
                            HashMap<String, String> datum = new HashMap<String, String>();
                            datum.put("Name", name);
                            datum.put("Role", email);
                            serviceData.add(datum);

                        }


                        SimpleAdapter adapter = new SimpleAdapter(ServicesList.this,serviceData,
                                android.R.layout.simple_list_item_2,
                                new String[]{"Name", "Role"}, new int[]{android.R.id.text1, android.R.id.text2});
                        serviceList.setAdapter(adapter);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError

                    }});


        */




    }
}
