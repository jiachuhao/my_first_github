package com.example.alienware.project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.*;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private Button buttonLogin;
    private EditText editTextName;
    private EditText editTextPassword;
    private TextView change;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    Account account;

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    ArrayAdapter<String> adapter;
    String login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextName=(EditText)findViewById(R.id.editName);
        editTextPassword=(EditText)findViewById(R.id.editPassword);
        buttonLogin=(Button)findViewById(R.id.buttonLogin);
        change=(TextView)findViewById(R.id.change);

        progressDialog=new ProgressDialog(this);
        firebaseAuth=FirebaseAuth.getInstance();

        buttonLogin.setOnClickListener(this);
        change.setOnClickListener(this);



    }
    private void userLogin(){
        String name=editTextName.getText().toString().trim();

        String password=editTextPassword.getText().toString().trim();
        if(TextUtils.isEmpty(name)){
            Toast.makeText(this,"Please enter your E-mail", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter your password", Toast.LENGTH_SHORT).show();
            return;
        }


        progressDialog.setMessage("Login...");
        progressDialog.show();



        firebaseAuth.signInWithEmailAndPassword(name,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            progressDialog.dismiss();
                            databaseReference=FirebaseDatabase.getInstance().getReference();



                            finish();

                            startActivity(new Intent(getApplicationContext(),profile.class));
                        }
                        else {
                        progressDialog.dismiss();
                        Toast.makeText(Login.this, "Incorrect email or password, pls check your input", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    @Override
    public void onClick(View view) {
        if(view==buttonLogin){
            userLogin();

        }
        if(view==change){
            startActivity(new Intent(this,MainActivity.class));
        }
    }
}
