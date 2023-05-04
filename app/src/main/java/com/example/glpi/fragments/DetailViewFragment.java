package com.example.glpi.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
import com.example.glpi.api.modelos.Ticket;
import com.example.glpi.api.interfaces.JsonPlaceHolderApi;
import com.example.glpi.api.modelos.DocumentItem;
import com.example.glpi.api.modelos.ProfileData;
import com.example.glpi.api.persistencia.GlpiQuerys;
import com.example.glpi.api.persistencia.RetroFitSingleton;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

//Fragmento donde se detallan las incidencias


public class DetailViewFragment extends Fragment {


    //Inicializacion de variables

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
    private GlpiQuerys glpiQuerys = new GlpiQuerys();


    public DetailViewFragment() {
        // Required empty public constructor
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

        //Asignacion de valores en los diferentes elementos de la vista

        textViewUserDetail = view.findViewById(R.id.textViewUserDetail);
        textViewNameDetail = view.findViewById(R.id.textViewNameDetail);
        textViewContentDetail = view.findViewById(R.id.textViewContentDetail);
        textViewUrgencyDetail = view.findViewById(R.id.textViewUrgencyDetail);
        textViewStateDetail = view.findViewById(R.id.textViewStateDetail);
        buttonUpdate = view.findViewById(R.id.buttonUpdate);
        buttonDelete = view.findViewById(R.id.buttonDelete);
        imageViewDetail = view.findViewById(R.id.imageViewDetail);

        autoCompleteStatusDetail = view.findViewById(R.id.autoCompleteStatusDetail);

        getTicketUser();
        getTicketImage();

        textViewNameDetail.setText(ticket.getName());
        textViewContentDetail.setText(ticket.getContent());


        //Llamada al objeto para coger y rellenar la vista detalle con los valores que se han pasado
        // al constructor

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


        //Coge la urgencia del ticket y dependiendo de la urgencia se pone un texto u otro y se
        // cambia el color.

        urgencyTicket = ticket.getUrgency();

        switch(urgencyTicket){
            case 1:
                textViewUrgencyDetail.setText("Urgencia: Muy poco urgente");
                textViewUrgencyDetail.setTextColor(Color.rgb(0,255,0));
                break;

            case 2:
                textViewUrgencyDetail.setText("Urgencia: Poco urgente");
                textViewUrgencyDetail.setTextColor(Color.rgb(0,205,0));
                break;
            case 3:
                textViewUrgencyDetail.setText("Urgencia: Urgencia media");
                textViewUrgencyDetail.setTextColor(Color.rgb(255,170,0));
                break;
            case 4:
                textViewUrgencyDetail.setText("Urgencia: Urgente");
                textViewUrgencyDetail.setTextColor(Color.rgb(180,0,0));
                break;
            case 5:
                textViewUrgencyDetail.setText("Urgencia: Muy urgente");
                textViewUrgencyDetail.setTextColor(Color.rgb(255,0,0));
                break;
        }

        //Desplegable que muestra el estado del ticket. También sirve para modificar
        // el estado del ticket

        adapterStatusDetail = new ArrayAdapter<String>(context, R.layout.urgency_layout, statusDetail);
        autoCompleteStatusDetail.setAdapter(adapterStatusDetail);

        autoCompleteStatusDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                status = position + 1;
                System.out.println(status);
            }
        });

        //Si se presiona el botón de actualizar se llama a la consulta para que actualice el valor
        //del ticket

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                glpiQuerys.updateTicketData(context,authToken,ticket.getId(),ticket.getUrgency(),status);
            }
        });

        //Si se presiona el botón de borrar se llama a la consulta para que borre el ticket

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                glpiQuerys.deleteTicket(context, authToken,ticket.getId());
            }
        });

        //textViewUrgencyDetail.setText(ticket.getUrgency());

        return view;
    }



    //Métodos secundarios que carga la imagen del ticket(si la hay)

    public void getTicketImage(){

        Call<List<DocumentItem>> documentCall = querys.showTicketImage(ticket.getId(), authToken);

        documentCall.enqueue(new Callback<List<DocumentItem>>() {
            @Override
            public void onResponse(Call<List<DocumentItem>> call, Response<List<DocumentItem>> response) {
                if(response.isSuccessful()){
                    if (response.body().isEmpty()){
                        /*
                        Glide.with(context)
                                .load("http://192.168.1.22/apirest.php/Document/" + documentId + "?session_token=" + authToken + "&alt=media")
                                .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                                .error(R.mipmap.glpi)
                                .into(imageViewDetail);

                         */

                        //Toast.makeText(context, "No se ha encontrado una imagen", Toast.LENGTH_SHORT).show();
                    }else{
                        documentId = response.body().get(0).getId();

                        Glide.with(context)
                                .load("http://192.168.1.22/apirest.php/Document/" + documentId + "?session_token=" + authToken + "&alt=media")
                                .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                                .error(android.R.drawable.stat_notify_error)
                                .into(imageViewDetail);
                    }

                }else{
                    Toast.makeText(context, "Ha ocurrido un error al cargar la imagen", Toast.LENGTH_SHORT).show();
                    System.out.println(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<DocumentItem>> call, Throwable t) {

                Toast.makeText(context, "Error al obtener la imagen. Revise su conexión. ", Toast.LENGTH_SHORT).show();
                System.out.println(t.getMessage());

            }
        });

    }


    //Método secundario que carga el usuario que ha creado el ticket
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