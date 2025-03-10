package com.example.glpi.api.persistencia;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.example.glpi.MainActivity;
import com.example.glpi.R;
import com.example.glpi.api.modelos.InitSession;
import com.example.glpi.api.modelos.Ticket;
import com.example.glpi.api.interfaces.JsonPlaceHolderApi;
import com.example.glpi.api.modelos.TicketList;
import com.example.glpi.fragments.ViewIncidenciaFragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GlpiQuerys {

    //Clase donde se guardan las consultas para añadir tickets, actualizar los tickets, borrar el ticket
    // y autenticar usuario.
    private final Retrofit retrofit = RetroFitSingleton.getInstance().getRetroFit();
    private final JsonPlaceHolderApi querys = retrofit.create(JsonPlaceHolderApi.class);
    private String authToken;

    public void setTicket(Context context, String authToken, String titulo, String descripcion, int status, int urgencia, int tipo){

        Ticket ticket = new Ticket(titulo,descripcion,status,urgencia,tipo);
        List<Ticket> ticketList= new ArrayList<Ticket>();
        ticketList.add(ticket);
        TicketList ticketListClass = new TicketList(ticketList);

        Call<List<Ticket>> tickets = querys.setTicket(ticketListClass,authToken);

        tickets.enqueue(new Callback<List<Ticket>>() {
            @Override
            public void onResponse(Call<List<Ticket>> call, Response<List<Ticket>> response) {

                if(response.isSuccessful()){

                    Toast.makeText(context, "Ticket creado satisfactoriamente", Toast.LENGTH_LONG).show();
                    System.out.println("Sucessful");
                    //Ticket ticket = response.body();
                    //System.out.println(ticket.getName());


                }else{
                    try {
                        System.out.println(response.errorBody().string());
                        Toast.makeText(context, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

            }

            @Override
            public void onFailure(Call<List<Ticket>> call, Throwable t) {

                Toast.makeText(context, "Ha ocurrido un error al crear el ticket", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateTicketData(Context context, String authToken,int id, int urgency, int status){

        //System.out.println(status + "en el metodo");
        Ticket ticketUpdate = new Ticket(id,urgency,status);
        List<Ticket> ticketList= new ArrayList<Ticket>();
        ticketList.add(ticketUpdate);
        TicketList ticketListClass = new TicketList(ticketList);

        Call<List> ticketUpdateCall = querys.updateTicketData(ticketListClass, authToken);

        ticketUpdateCall.enqueue(new Callback<List>() {
            @Override
            public void onResponse(Call<List> call, Response<List> response) {

                if(response.isSuccessful()){
                    Toast.makeText(context, "Ticket actualizado", Toast.LENGTH_SHORT).show();
                    System.out.println(response.code());
                }else{
                    try {
                        Toast.makeText(context, "Error al actualizar el ticket", Toast.LENGTH_SHORT).show();
                        System.out.println(response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

            }

            @Override
            public void onFailure(Call<List> call, Throwable t) {
                System.out.println(t.getMessage());
                Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show();


            }
        });

    }


    public void deleteTicket(Context context, String authToken, int id){

        Call<ResponseBody> ticketDeleteCall = querys.deleteTicketData(id, authToken);

        ticketDeleteCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.isSuccessful()){

                    try {
                        System.out.println(response.body().string());
                        Toast.makeText(context, "Ticket borrado", Toast.LENGTH_SHORT).show();

                        Fragment newFragment = new ViewIncidenciaFragment();

                        Bundle bundle= new Bundle();
                        bundle.putString("session_token", authToken);
                        newFragment.setArguments(bundle);

                        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();

                        fragmentManager.beginTransaction()
                                .replace(R.id.frameLayout, newFragment)
                                .addToBackStack(null)
                                .commit();

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    try {
                        Toast.makeText(context, "Error al borrar el ticket", Toast.LENGTH_SHORT).show();
                        System.out.println(response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show();
                System.out.println(t.getMessage());

            }
        });
    }

    public String autenticarUsuario(Context context, String username, String password){

        String base = username + ":" + password;

        String authorization = Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        Call<InitSession> call = querys.getUser("Basic " + authorization.trim());

        call.enqueue(new Callback<InitSession>() {
            @Override
            public void onResponse(Call<InitSession> call, Response<InitSession> response) {
                if(response.isSuccessful()){

                    authToken = response.body().getSessionToken();


                    Toast.makeText(context, "Usuario autenticado", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("session_token", authToken);
                    context.startActivity(intent);

                }else{
                    String errorMessage;
                    try {
                        errorMessage = response.errorBody().string();
                        System.out.println(errorMessage);
                        Toast.makeText(context, "Compruebe credenciales", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<InitSession> call, Throwable t) {

                System.out.println(t.getMessage());
                Toast.makeText(context, "Fallo de conexión, vuelve a intentarlo", Toast.LENGTH_SHORT).show();
            }
        });
        return authToken;
    }
}


/*
    public void getActiveProfile(){

        Call<ProfileList> profileCall = querys.getActiveProfile(authToken);

        profileCall.enqueue(new Callback<ProfileList>() {
            @Override
            public void onResponse(Call<ProfileList> call, Response<ProfileList> response) {
                if(response.isSuccessful()){

                    System.out.println(response.body().getData().getName());

                }else{
                    try {
                        System.out.println(response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileList> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

     */