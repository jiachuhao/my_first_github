package com.example.alienware.project;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class listservices extends ArrayAdapter<ServicesList> {
    private Activity context;
    private List<ServicesList> serviceslist;

    public listservices(Activity context, List<ServicesList>serviceslist){
        super(context, R.layout.list_services,serviceslist);
        this.context=context;
        this.serviceslist=serviceslist;

    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater=context.getLayoutInflater();

        View ViewServicesList=inflater.inflate(R.layout.list_services,null,true);
        TextView textViewName=(TextView)ViewServicesList.findViewById(R.id.textViewName);
        TextView textViewHourlyRate=(TextView)ViewServicesList.findViewById(R.id.textViewHourlyRate);

        ServicesList servicesList=serviceslist.get(position);

        textViewName.setText("Service Name: "+servicesList.getName());
        textViewHourlyRate.setText(servicesList.getHourlyRate()+" $/hour");

        return ViewServicesList;


    }


}
