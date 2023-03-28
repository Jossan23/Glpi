package com.example.glpi.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.glpi.R;
import com.example.glpi.api.get.Ticket;
import com.example.glpi.api.interfaces.JsonPlaceHolderApi;
import com.example.glpi.api.modelos.ProfileData;
import com.example.glpi.api.modelos.ProfileList;
import com.example.glpi.api.persistencia.RetroFitSingleton;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailViewFragment extends Fragment {

    private Ticket ticket;
    private TextView textViewNameDetail,textViewUrgencyDetail,textViewStateDetail,textViewContentDetail,textViewUserDetail;
    private Button buttonUpdate, buttonDelete;
    private int status;
    private int urgencyTicket;
    private String authToken;
    private final Retrofit retrofit = RetroFitSingleton.getInstance().getRetroFit();

    private final JsonPlaceHolderApi querys = retrofit.create(JsonPlaceHolderApi.class);

    public DetailViewFragment() {
        // Required empty public constructor
    }

    public static DetailViewFragment newInstance() {
        DetailViewFragment fragment = new DetailViewFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail_view, container, false);

        ticket = (Ticket) getArguments().getSerializable("Ticket");
        authToken = getArguments().getString("authToken");

        getTicketUser();

        textViewUserDetail = view.findViewById(R.id.textViewUserDetail);
        textViewNameDetail = view.findViewById(R.id.textViewNameDetail);
        textViewContentDetail = view.findViewById(R.id.textViewContentDetail);
        textViewUrgencyDetail = view.findViewById(R.id.textViewUrgencyDetail);
        textViewStateDetail = view.findViewById(R.id.textViewStateDetail);
        buttonUpdate = view.findViewById(R.id.buttonUpdate);
        buttonDelete = view.findViewById(R.id.buttonDelete);

        status = ticket.getStatus();
        textViewNameDetail.setText(ticket.getName());
        textViewContentDetail.setText(ticket.getContent());

        switch(status){
            case 1:
                textViewStateDetail.setText("Estado: Incidencia creada");
                break;
            case 2:
                textViewStateDetail.setText("Estado: Asignada");
                break;
            case 3:
                textViewStateDetail.setText("Estado: Planificada");
                break;
            case 4:
                textViewStateDetail.setText("Estado: En espera");
                break;
            case 5:
                textViewStateDetail.setText("Estado: Resuelto");
                break;
            case 6:
                textViewStateDetail.setText("Estado: Cerrado");
                break;
        }

        urgencyTicket = ticket.getUrgency();

        switch(urgencyTicket){
            case 1:
                textViewUrgencyDetail.setText("Urgencia: Muy poco urgente");
                textViewUrgencyDetail.setTextColor(Color.rgb(0,255,0));
                break;

            case 2:
                textViewUrgencyDetail.setText("Urgencia: Poco urgente");
                textViewUrgencyDetail.setTextColor(Color.rgb(0,255,0));
                break;
            case 3:
                textViewUrgencyDetail.setText("Urgencia: Urgencia media");
                textViewUrgencyDetail.setTextColor(Color.rgb(255,170,0));
                break;
            case 4:
                textViewUrgencyDetail.setText("Urgencia: Urgente");
                textViewUrgencyDetail.setTextColor(Color.rgb(200,0,0));
                break;
            case 5:
                textViewUrgencyDetail.setText("Urgencia: Muy urgente");
                textViewUrgencyDetail.setTextColor(Color.rgb(255,0,0));
                break;
        }


        //textViewUrgencyDetail.setText(ticket.getUrgency());

        return view;
    }



    public void getTicketUser(){

        Call<ProfileData> profileCall = querys.getTicketUser(ticket.getUserCreatorTicker(),authToken);

        profileCall.enqueue(new Callback<ProfileData>() {
            @Override
            public void onResponse(Call<ProfileData> call, Response<ProfileData> response) {
                if(response.isSuccessful()){

                    textViewUserDetail.setText("Usuario: " + response.body().getName());

                }else{
                    try {
                        System.out.println(response.errorBody().string());

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileData> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

}