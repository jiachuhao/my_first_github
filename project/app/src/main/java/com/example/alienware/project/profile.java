package com.example.alienware.project;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class profile extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private TextView textName;
    private TextView textViewType;
    private Button buttonCheck;
    private Button buttonServices;
    private Button buttonLogout;

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    ArrayAdapter<String> adapter;
    Account account;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth=FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()==null){
            startActivity(new Intent(this,Login.class));
        }
        final FirebaseUser user=firebaseAuth.getCurrentUser();

        buttonCheck=(Button)findViewById(R.id.buttonSave);
        buttonServices=(Button)findViewById(R.id.buttonServices);
        textName=(TextView)findViewById(R.id.textName);
        textViewType=(TextView)findViewById(R.id.textViewType);


        textName.setText("Dear User: "+user.getEmail());

        buttonLogout=(Button)findViewById(R.id.buttonLogout);

        buttonLogout.setOnClickListener(this);
        buttonCheck.setOnClickListener(this);
        buttonServices.setOnClickListener(this);

        account=new Account();

        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference("account");
        adapter=new ArrayAdapter<String>(this,R.layout.user_info,R.id.userInfo);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String type;
                for(DataSnapshot accountSnapshot: dataSnapshot.getChildren()) {
                    account = accountSnapshot.getValue(Account.class);
                    if(account.getEmail().equals(user.getEmail())){
                        textViewType.setText("You are login as: "+account.getAccountType());
                        type=account.getAccountType();
                        if(type.equals("Admin")){
                            buttonCheck.setVisibility(View.VISIBLE);
                            buttonServices.setVisibility(View.VISIBLE);
                        }
                    }

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    @Override
    public void onClick(View view) {
        if(view==buttonLogout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this,Login.class));
        }
        if(view==buttonCheck){
            startActivity(new Intent(this,test.class));

        }
        if(view==buttonServices){
            startActivity(new Intent(this,Services.class));

        }
    }
}
