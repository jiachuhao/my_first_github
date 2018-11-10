package com.example.alienware.project;

public class ServicesList {
    private String id;
    private String name;
    private String hourlyRate;

    public ServicesList(){

    }
    public ServicesList(String id,String name, String hourlyRate){
        this.id=id;
        this.name=name;
        this.hourlyRate=hourlyRate;

    }
    public String getId() {

        return id;
    }
    public String getName() {

        return name;
    }

    public String getHourlyRate() {

        return hourlyRate;
    }

}
