package com.example.glpi.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.glpi.R;
import com.example.glpi.adapters.ListViewAdapter;
import com.example.glpi.api.get.Ticket;
import com.example.glpi.api.interfaces.DetailTicketsInterface;
import com.example.glpi.api.interfaces.JsonPlaceHolderApi;
import com.example.glpi.api.modelos.ProfileList;
import com.example.glpi.api.persistencia.RetroFitSingleton;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import kotlinx.coroutines.scheduling.TasksKt;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class ViewIncidenciaFragment extends Fragment implements DetailTicketsInterface {

    private final Retrofit retrofit = RetroFitSingleton.getInstance().getRetroFit();

    private final JsonPlaceHolderApi querys = retrofit.create(JsonPlaceHolderApi.class);
    private View view;
    private String authKey;
    private List<Ticket> ticketList;
    private Context context;
    private ListViewAdapter adapter;
    private RecyclerView recycView;


    public ViewIncidenciaFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        authKey = getArguments().getString("session_token");
        //System.out.println(authKey + "esta en un fragment");

        view = inflater.inflate(R.layout.fragment_view_incidencia,container,false);

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        recycView = view.findViewById(R.id.recyclerView);
        recycView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new ListViewAdapter(context , ticketList, this);
        recycView.setAdapter(adapter);
        getTicket();
    }


    public void getTicket(){

        //System.out.println(authToken + "este es mi token");

        Call<List<Ticket>> tickets = querys.getTicket(authKey);

        tickets.enqueue( new Callback<List<Ticket>>() {
            @Override
            public void onResponse(Call<List<Ticket>> call, Response<List<Ticket>> response) {

                if(response.isSuccessful()){
                    ticketList = response.body();
                    adapter.setData(ticketList);


                    /*
                    for(Ticket t : ticketList){

                        System.out.println(t.getId());
                        System.out.println(t.getName());
                        System.out.println(t.getContent());
                        System.out.println(t.getStatus());
                        System.out.println(t.getUserCreatorTicker());
                        System.out.println(t.getUrgency());

                    }

                     */



                    System.out.println("he terminado");


                }else{
                    try {
                        System.out.println(response.errorBody().string() + "b,kjasbd,fkjbasdhfb");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Ticket>> call, Throwable t) {
                System.out.println(t.getMessage());

            }
        });
    }

    @Override
    public void onItemClick(int position) {

        System.out.println(position);
        Fragment newFragment = new DetailViewFragment();
        Bundle bundle= new Bundle();
        bundle.putSerializable("Ticket", ticketList.get(position));
        bundle.putString("authToken", authKey);
        newFragment.setArguments(bundle);

        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, newFragment)
                .addToBackStack(null)
                .commit();

    }







    /*
    public void getTicketSync(){


        Call<List<Ticket>> ticketsCall = querys.getTicket(authKey);

        try {
            Response<List<Ticket>> response = ticketsCall.execute();

            if(response.isSuccessful()){
                ticketList = response.body();

                for(Ticket t : ticketList){

                    System.out.println(t.getName());
                    System.out.println(t.getContent());
                    System.out.println(t.getStatus());

                    //String contenido = t.getContent();
                    //editTextUsuario.setText(contenido);
                    System.out.println(t.getUrgency());

                }
            }else{
                System.out.println(response.errorBody().string());
            }



        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

     */

}