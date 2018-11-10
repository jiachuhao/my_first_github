package com.example.alienware.project;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class admin extends AppCompatActivity {


    EditText editName;
    EditText editPassword;
    Button buttonAdd;
    Spinner spinnerType;

    DatabaseReference databaseAccount;
    ListView ListViewAccount;

    List<Account> accountList;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        databaseAccount=FirebaseDatabase.getInstance().getReference();

        editName=(EditText)findViewById(R.id.editEmail);
        editPassword=(EditText)findViewById(R.id.editPassword);;
        buttonAdd=(Button)findViewById(R.id.buttonAdd);
        spinnerType=(Spinner)findViewById(R.id.spinnerType);


        accountList=new ArrayList<>();
        buttonAdd.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                addAccount();
            }

        });

    }



    private void addAccount(){
        String email=editName.getText().toString().trim();
        String password=editPassword.getText().toString().trim();
        String type=spinnerType.getSelectedItem().toString();

        if(TextUtils.isEmpty(email)){
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
        else{

            String id=databaseAccount.push().getKey();
            Account account=new Account(email,password,type);
            databaseAccount.child(id).setValue(account);
            Toast.makeText(this,"Added", Toast.LENGTH_SHORT).show();

        }
    }

}
