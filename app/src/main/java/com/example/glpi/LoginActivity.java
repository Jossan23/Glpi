package com.example.glpi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.glpi.api.get.InitSession;
import com.example.glpi.api.get.Ticket;
import com.example.glpi.api.interfaces.JsonPlaceHolderApi;
import com.example.glpi.api.modelos.ProfileList;
import com.example.glpi.api.modelos.TicketList;
import com.example.glpi.api.persistencia.RetroFitSingleton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {


    private String authToken;

    private final Retrofit retrofit = RetroFitSingleton.getInstance().getRetroFit();

    private final JsonPlaceHolderApi querys = retrofit.create(JsonPlaceHolderApi.class);

    private EditText editTextUsuario;
    private EditText editTextPassword;
    private Button botonLogin;
    private Context _context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _context = this;

        editTextUsuario = findViewById(R.id.editTextUsuario);
        editTextPassword = findViewById(R.id.editTextPassword);
        botonLogin = findViewById(R.id.botonLogin);

        autenticarUsuario("glpi","glpi");

        //getTicket();

        //setTicket();

        //getActiveProfile();
    }


    public String autenticarUsuario(String username, String password){

        String base = username + ":" + password;


        String authorization = Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        Call<InitSession> call = querys.getUser("Basic " + authorization.trim());

        call.enqueue(new Callback<InitSession>() {
            @Override
            public void onResponse(Call<InitSession> call, Response<InitSession> response) {
                if(response.isSuccessful()){

                    authToken = response.body().getSessionToken();


                    Toast.makeText(LoginActivity.this, "Usuario autenticado", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(_context, MainActivity.class);
                    intent.putExtra("session_token", authToken);
                    startActivity(intent);


                    //System.out.println("Biennn");
                }else{
                    String errorMessage;
                    try {
                        errorMessage = response.errorBody().string() + "asd";
                        System.out.println(errorMessage);
                        System.out.println("Algo falla");
                    } catch (Exception e) {
                        System.out.println(e);
                        System.out.println("MAL");
                    }
                }
            }

            @Override
            public void onFailure(Call<InitSession> call, Throwable t) {

                System.out.println(t.getMessage());
                System.out.println("Ni te conecta parguela");
            }
        });
        return authToken;
    }



    public void setTicket(){

        Ticket ticket = new Ticket("Meter tinta a la impresora", "Contenido jejajjas", 2,1);
        List<Ticket> ticketList= new ArrayList<Ticket>();
        ticketList.add(ticket);
        TicketList ticketListClass = new TicketList(ticketList);

        Call<Ticket>  tickets = querys.setTicket(ticketListClass,"sqemlv2vjjn52ck65m3qtuqnn0");

        tickets.enqueue(new Callback<Ticket>() {
            @Override
            public void onResponse(Call<Ticket> call, Response<Ticket> response) {

                if(response.isSuccessful()){


                    System.out.println("Sucessful");
                    //Ticket ticket = response.body();
                    //System.out.println(ticket.getName());


                }else{
                    try {
                        System.out.println(response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

            }

            @Override
            public void onFailure(Call<Ticket> call, Throwable t) {

                System.out.println(t.getMessage());
            }
        });
    }


    public void getActiveProfile(){

        Call<ProfileList> profileCall = querys.getActiveProfile("vhcftl8trmo0hhbmiis31mi39p");

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
}