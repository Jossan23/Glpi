package com.example.glpi.interfaces;

import com.example.glpi.api.get.InitSession;
import com.example.glpi.modelos.Users;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {

    @Headers("Content-Type: application/json")
    @GET("initSession")
    Call<InitSession> authToken(@Header("Authorization") String authKey);



    @Headers("Content-Type: application/json")
    @GET("initSession")
    Call<InitSession> getSecret(@Header("Authorization") String authorization);


}
