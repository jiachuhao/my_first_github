package com.example.alienware.project;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class test2 extends AppCompatActivity {

    ListView listView;
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    ServicesList servicesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);

        servicesList = new ServicesList();
        listView = (ListView) findViewById(R.id.listView);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("services");

        list = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.user_info, R.id.userInfo, list);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot servicesListSnapshot : dataSnapshot.getChildren()) {
                    servicesList = servicesListSnapshot.getValue(ServicesList.class);
                    list.add("Name: " + servicesList.getName().toString().trim());
                    list.add("Rate: " + servicesList.getHourlyRate().toString().trim());

                }
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
