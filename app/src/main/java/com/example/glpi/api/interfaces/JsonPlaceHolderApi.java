package com.example.glpi.api.interfaces;

import com.example.glpi.api.get.InitSession;
import com.example.glpi.api.get.Ticket;
import com.example.glpi.api.modelos.ProfileData;
import com.example.glpi.api.modelos.ProfileList;
import com.example.glpi.api.modelos.TicketList;
import com.example.glpi.api.modelos.Users;

import java.util.List;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
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
    Call<List<Ticket>> setTicket(@Body TicketList ticket, @Query("session_token") String authToken);

    @Headers("Content-Type: application/json")
    @GET("getActiveProfile")
    Call<ProfileList> getActiveProfile(@Query("session_token") String authorization);

    @Headers("Content-Type: application/json")
    @GET("User/{user_id}/")
    Call<ProfileData> getTicketUser(@Path ("user_id") String userID, @Query("session_token") String authorization);

    @Headers("Content-Type: application/json")
    @PUT("Ticket/")
    Call<List> updateTicketData( @Body TicketList ticket, @Query("session_token")String authorization);

    @Headers("Content-Type: application/json")
    @DELETE("Ticket/{id}")
    Call<ResponseBody> deleteTicketData(@Path("id") int id, @Query("session_token") String authorization);

}
