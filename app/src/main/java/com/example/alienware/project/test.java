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

public class test extends AppCompatActivity {

    ListView listView;
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    ArrayList<String> list;
    ArrayAdapter<String>adapter;
    Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        account=new Account();
        listView=(ListView)findViewById(R.id.listView);
        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference("account");

        list=new ArrayList<>();
        adapter=new ArrayAdapter<String>(this,R.layout.user_info,R.id.userInfo,list);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot accountSnapshot: dataSnapshot.getChildren()) {
                    account = accountSnapshot.getValue(Account.class);
                    list.add("Email: "+account.getEmail().toString().trim());
                    list.add("Password: "+account.getPassword().toString().trim());
                    list.add("Account Type: "+account.getAccountType().toString().trim());
                    list.add("**************************************");
                }
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
