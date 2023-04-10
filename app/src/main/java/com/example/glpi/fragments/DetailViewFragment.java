package com.example.glpi.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.glpi.R;
import com.example.glpi.api.get.Ticket;
import com.example.glpi.api.interfaces.JsonPlaceHolderApi;
import com.example.glpi.api.modelos.DocumentItem;
import com.example.glpi.api.modelos.ProfileData;
import com.example.glpi.api.modelos.TicketList;
import com.example.glpi.api.persistencia.RetroFitSingleton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
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
    private AutoCompleteTextView autoCompleteStatusDetail;
    private ArrayAdapter<String> adapterStatusDetail;
    private Context context;
    private final Retrofit retrofit = RetroFitSingleton.getInstance().getRetroFit();

    private final JsonPlaceHolderApi querys = retrofit.create(JsonPlaceHolderApi.class);

    private String[] statusDetail = {"Nuevo", "En curso(asignada)", "En curso (planificada)","En espera","Resuelto", "Cerrado"};
    private ImageView imageViewDetail;
    private int documentId;


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
        context = getActivity();

        getTicketUser();
        System.out.println(ticket.getId() + "asdasdasd");

        textViewUserDetail = view.findViewById(R.id.textViewUserDetail);
        textViewNameDetail = view.findViewById(R.id.textViewNameDetail);
        textViewContentDetail = view.findViewById(R.id.textViewContentDetail);
        textViewUrgencyDetail = view.findViewById(R.id.textViewUrgencyDetail);
        textViewStateDetail = view.findViewById(R.id.textViewStateDetail);
        buttonUpdate = view.findViewById(R.id.buttonUpdate);
        buttonDelete = view.findViewById(R.id.buttonDelete);
        imageViewDetail = view.findViewById(R.id.imageViewDetail);

        autoCompleteStatusDetail = view.findViewById(R.id.autoCompleteStatusDetail);


        getTicketImage();

        textViewNameDetail.setText(ticket.getName());
        textViewContentDetail.setText(ticket.getContent());

        status = ticket.getStatus();


        autoCompleteStatusDetail.setText(statusDetail[status -1]);


        switch(status){
            case 1:
                textViewStateDetail.setText("Estado: Creada");
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


        adapterStatusDetail = new ArrayAdapter<String>(context, R.layout.urgency_layout, statusDetail);
        autoCompleteStatusDetail.setAdapter(adapterStatusDetail);

        autoCompleteStatusDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                status = position + 1;
                System.out.println(status);
            }
        });


        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTicketData();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteTicket();
            }
        });

        //textViewUrgencyDetail.setText(ticket.getUrgency());

        return view;
    }





    public void getTicketImage(){

        Call<List<DocumentItem>> documentCall = querys.showTicketImage(ticket.getId(), authToken);

        documentCall.enqueue(new Callback<List<DocumentItem>>() {
            @Override
            public void onResponse(Call<List<DocumentItem>> call, Response<List<DocumentItem>> response) {
                if(response.isSuccessful()){
                    if (response.body().isEmpty()){
                        Toast.makeText(context, "No se ha encontrado una imagen", Toast.LENGTH_SHORT).show();
                    }else{
                        documentId = response.body().get(0).getId();
                        //System.out.println(documentId + "en el metodo");

                        //System.out.println(documentId + "en el glide");
                        Glide.with(context)
                                .load("http://192.168.1.22/apirest.php/Document/" + documentId + "?session_token=" + authToken + "&alt=media")
                                .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                                .error(android.R.drawable.stat_notify_error)
                                .into(imageViewDetail);
                    }

                }else{
                    System.out.println(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<DocumentItem>> call, Throwable t) {

                System.out.println(t.getMessage());

            }
        });

    }


    public void getTicketUser(){

        Call<ProfileData> profileCall = querys.getTicketUser(ticket.getUserCreatorTicker(),authToken);

        profileCall.enqueue(new Callback<ProfileData>() {
            @Override
            public void onResponse(Call<ProfileData> call, Response<ProfileData> response) {
                if(response.isSuccessful()){

                    textViewUserDetail.setText("Usuario: " + response.body().getName());
                    System.out.println("asd");
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

    public void updateTicketData(){

        System.out.println(status + "en el metodo");
        Ticket ticketUpdate = new Ticket(ticket.getId(),ticket.getUrgency(),status);
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
                        System.out.println(response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

            }

            @Override
            public void onFailure(Call<List> call, Throwable t) {
                System.out.println(t.getMessage());

            }
        });

    }


    public void deleteTicket(){

        Call<ResponseBody> ticketDeleteCall = querys.deleteTicketData(ticket.getId(), authToken);

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
                        System.out.println(response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println(t.getMessage());

            }
        });
    }
}