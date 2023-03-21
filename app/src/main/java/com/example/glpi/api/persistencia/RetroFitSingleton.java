package com.example.glpi.api.persistencia;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroFitSingleton {

    private static RetroFitSingleton instance;
    private Retrofit retroFitInstance;

    private RetroFitSingleton(){

        retroFitInstance = new Retrofit.Builder().baseUrl("http://192.168.1.22/apirest.php/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    public static RetroFitSingleton getInstance(){

        if(instance == null){

            instance = new RetroFitSingleton();
        }

        return instance;
    }

    public Retrofit getRetroFit() {
        return retroFitInstance;
    }

}
