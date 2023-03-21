package com.example.glpi.api.interfaces;

import com.example.glpi.api.get.InitSession;
import com.example.glpi.api.modelos.Users;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface JsonPlaceHolderApi {

    @Headers("Content-Type: application/json")
    @GET("initSession")
    Call<Users> authToken(@Header("Authorization") String authKey);



    @Headers("Content-Type: application/json")
    @GET("initSession")
    Call<InitSession> getUser(@Header("Authorization") String authorization);


}
