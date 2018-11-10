package com.example.alienware.project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.*;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private Button buttonRegister;
    private EditText editName;
    private EditText editPassword;
    private TextView change;
    private Spinner spinner;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    boolean check1=true;
    boolean check2=true;
    boolean check3=true;

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter2;
    Account account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth=FirebaseAuth.getInstance();

        progressDialog=new ProgressDialog(this);

        buttonRegister=(Button)findViewById(R.id.buttonRegister);
        editName=(EditText)findViewById(R.id.editName);
        editPassword=(EditText)findViewById(R.id.editPassword);
        change=(TextView) findViewById(R.id.change);

        spinner=(Spinner)findViewById(R.id.editType);
        ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(this,R.array.Account_Type,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        buttonRegister.setOnClickListener(this);
        change.setOnClickListener(this);

        account = new Account();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("account");
        adapter2 = new ArrayAdapter<String>(this, R.layout.user_info, R.id.userInfo);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int x=0;
                for (DataSnapshot accountSnapshot : dataSnapshot.getChildren()) {
                    account = accountSnapshot.getValue(Account.class);
                    if (account.getAccountType().equals("Admin")) {
                        check3 = false;
                        x++;

                    }
                }
                if(x==0){
                    check3=true;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    private void saveUserInfo(){
        String email=editName.getText().toString().trim();
        String password=editPassword.getText().toString().trim();
        String type=spinner.getSelectedItem().toString();
        Account account=new Account(email,password,type);
        FirebaseUser user=firebaseAuth.getCurrentUser();
        databaseReference=FirebaseDatabase.getInstance().getReference("account");
        databaseReference.child(user.getUid()).setValue(account);
    }
    private void registerUser(){
        String name=editName.getText().toString().trim();
        String password=editPassword.getText().toString().trim();
        String type=spinner.getSelectedItem().toString().trim();
        if(TextUtils.isEmpty(name)){
            Toast.makeText(this,"Please enter your E-mail", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter your password", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(type)){
            Toast.makeText(this,"Please select a account type", Toast.LENGTH_SHORT).show();
            return;
        }

        if (type.equals("Admin")){
            check1=false;
        }
        if (!type.equals("Admin")){
            check1=true;
        }

        if(check1==true||check1==false&&check3==true) {
            progressDialog.setMessage("Registering...");
            progressDialog.show();
            firebaseAuth.createUserWithEmailAndPassword(name, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                saveUserInfo();
                                Toast.makeText(MainActivity.this, "Registered Successful", Toast.LENGTH_SHORT).show();
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(MainActivity.this, "Could not Registered, pls try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        if(check1==false&&check3==false){
            Toast.makeText(MainActivity.this,"There is already an admin account",Toast.LENGTH_SHORT).show();
        }


    }
    @Override


    public void onClick(View view) {
        if(view==buttonRegister){
            registerUser();



        }
        if(view==change){
            startActivity(new Intent(this,Login.class));
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String type=parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),type,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
