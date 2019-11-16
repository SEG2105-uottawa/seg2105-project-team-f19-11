package com.example.finalproject.dev3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.finalproject.R;
import com.example.finalproject.ServicesList;
import com.example.model.Employee;
import com.example.model.WalkInClinic;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class PaymentsPage extends AppCompatActivity {

    private Button edit ;
    private ImageButton back ;
    private ListView methodList;
    private ListView insuranceList;
    private FirebaseAuth mFirebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments_page);

        edit = (Button) findViewById(R.id.editPaymentsBtn) ;
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity_editPaymentsPage();
            }
        });

        back = (ImageButton) findViewById(R.id.backBtn) ;
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity_aboutPage();
            }
        });

        methodList = (ListView) findViewById(R.id.methodList);
        insuranceList = (ListView) findViewById(R.id.insuranceList);


        String userID = mFirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("users/"+userID);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapShot : dataSnapshot.getChildren()) {
                    Employee user = dataSnapshot.getValue(Employee.class);
                    WalkInClinic clinic = user.getClinic();

                    /*SimpleAdapter adapter = new SimpleAdapter(PaymentsPage.this,clinic.getPayments(),
                            android.R.layout.simple_list_item_1,
                            new String[]{"Payment"}, new int[]{android.R.id.text1});
                    methodList.setAdapter(adapter);*/


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }


        });

    }

    public void openactivity_editPaymentsPage(){
        Intent intent = new Intent(this, EditPaymentsPage.class);
        startActivity(intent);
    }
    public void openactivity_aboutPage(){
        Intent intent = new Intent(this, ClinicAbout.class);
        startActivity(intent);
    }

}
