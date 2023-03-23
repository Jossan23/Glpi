package com.example.glpi.api.interfaces;

import com.example.glpi.api.get.InitSession;
import com.example.glpi.api.get.Ticket;
import com.example.glpi.api.modelos.TicketList;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {

    /*
    @Headers("Content-Type: application/json")
    @GET("initSession")
    Call<Users> authToken(@Header("Authorization") String authKey);
     */



    @Headers("Content-Type: application/json")
    @GET("initSession")
    Call<InitSession> getUser(@Header("Authorization") String authorization);


    @Headers("Content-Type: application/json")
    @GET("Ticket")
    Call<List<Ticket>> getTicket(@Query("session_token") String authKey);

    @Headers("Content-Type: application/json")
    @POST("Ticket")
    Call<Ticket> setTicket(@Body TicketList ticketList, @Query("session_token") String authToken);

}
