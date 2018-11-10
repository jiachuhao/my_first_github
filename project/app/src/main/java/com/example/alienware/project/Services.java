package com.example.alienware.project;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AlertDialogLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.List;

public class Services extends AppCompatActivity implements View.OnClickListener{

    private Button buttonAdd;
    private Button buttonBack;

    private EditText editName;
    private EditText editHourlyRate;

    ListView listViewServices;
    ArrayAdapter<String>adapter;
    ServicesList servicesList;
    List<ServicesList>servicesLists;

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        database=FirebaseDatabase.getInstance();

        databaseReference=database.getReference("services");
        listViewServices=(ListView) findViewById(R.id.listViewServices);
        servicesLists=new ArrayList<>();

        buttonAdd=(Button)findViewById(R.id.buttonAdd);
        buttonBack=(Button)findViewById(R.id.buttonBack);

        editName=(EditText)findViewById(R.id.editName);
        editHourlyRate=(EditText)findViewById(R.id.editHourlyRate);
        buttonAdd.setOnClickListener(this);
        buttonBack.setOnClickListener(this);



        listViewServices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ServicesList servicesList=servicesLists.get(position);
                showUpdate(servicesList.getId(),servicesList.getName(),servicesList.getHourlyRate());
            }
        });

    }
    protected void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                servicesLists.clear();
                for(DataSnapshot servicesSnapshot: dataSnapshot.getChildren()){
                    ServicesList servicesList=servicesSnapshot.getValue(ServicesList.class);
                    servicesLists.add(servicesList);
                }
                listservices adapter=new listservices(Services.this,servicesLists);
                listViewServices.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showUpdate(final String services_id, String services_name,String services_hourlyRate){
        AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(this);
        LayoutInflater inflater=getLayoutInflater();
        final View dialogView=inflater.inflate(R.layout.update, null);
        dialogBuilder.setView(dialogView);

        final TextView textViewName=(TextView)dialogView.findViewById(R.id.textViewName);
        final EditText editTextName=(EditText)dialogView.findViewById(R.id.editTextName);
        final TextView textViewHourlyRate=(TextView)dialogView.findViewById(R.id.textViewHourlyRate);
        final EditText editTextHourlyRate=(EditText)dialogView.findViewById(R.id.editTextHourlyRate);
        final Button buttonCancel=(Button)dialogView.findViewById(R.id.buttonCancel);
        final Button buttonUpdate=(Button)dialogView.findViewById(R.id.buttonUpdate);
        final Button buttonDelete=(Button)dialogView.findViewById(R.id.buttonDelete);

        dialogBuilder.setTitle("Updating Services: "+services_name);


        final AlertDialog alertDialog=dialogBuilder.create();
        alertDialog.show();

        editTextName.setHint("("+services_name+")");
        editTextHourlyRate.setHint("("+services_hourlyRate+" $)");

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=editTextName.getText().toString().trim();
                String hourlyRate=editTextHourlyRate.getText().toString().trim();
                if(TextUtils.isEmpty(name)){
                    editTextName.setError("You have to enter a service name");
                    return;
                }
                if(TextUtils.isEmpty(hourlyRate)){
                    editTextHourlyRate.setError("You have to enter the hourly rate");
                    return;
                }
                upDateServices(services_id,name,hourlyRate);
                alertDialog.dismiss();

            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteService(services_id);
                alertDialog.dismiss();
            }
        });


    }
    private void deleteService(String service_id){
        DatabaseReference ds=FirebaseDatabase.getInstance().getReference("services").child(service_id);
        ds.removeValue();
        Toast.makeText(this, "Services has been removed Successful",Toast.LENGTH_SHORT).show();

    }
    private boolean upDateServices(String id, String name, String hourlyRate){
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("services").child(id);
        ServicesList servicesList=new ServicesList(id,name,hourlyRate);
        databaseReference.setValue(servicesList);
        Toast.makeText(this, "Services information updated Successful",Toast.LENGTH_SHORT).show();
        return true;


    }


    private void AddServices(){
        String name=editName.getText().toString().trim();
        String hourlyRate=editHourlyRate.getText().toString().trim();
        //String type=spinner.getSelectedItem().toString().trim();
        if(TextUtils.isEmpty(name)){
            Toast.makeText(this,"Please enter a service name", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(hourlyRate)){
            Toast.makeText(this,"Please enter the hourly rate of the service", Toast.LENGTH_SHORT).show();
            return;
        }



        databaseReference=FirebaseDatabase.getInstance().getReference("services");
        String id=databaseReference.push().getKey();
        ServicesList servicesList=new ServicesList(id,name,hourlyRate);
        databaseReference.child(id).setValue(servicesList);
        Toast.makeText(Services.this, "Added Successful", Toast.LENGTH_SHORT).show();


    }
    public void onClick(View view) {
        if(view==buttonAdd){
            AddServices();
        }
        if(view==buttonBack){
            startActivity(new Intent(this,profile.class));

        }
    }


}
