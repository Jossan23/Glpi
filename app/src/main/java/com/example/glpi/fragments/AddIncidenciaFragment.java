package com.example.glpi.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.glpi.R;
import com.example.glpi.api.get.Ticket;
import com.example.glpi.api.interfaces.JsonPlaceHolderApi;
import com.example.glpi.api.modelos.TicketList;
import com.example.glpi.api.persistencia.RetroFitSingleton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class AddIncidenciaFragment extends Fragment {

    private final Retrofit retrofit = RetroFitSingleton.getInstance().getRetroFit();
    private final JsonPlaceHolderApi querys = retrofit.create(JsonPlaceHolderApi.class);
    private String authToken;

    String[] urgency = {"Muy poco urgente", "Poco urgente", "Urgencia media","Urgencia alta", "Muy urgente"};
    AutoCompleteTextView autoCompleteTextViewUrgency;
    ArrayAdapter<String> adapterUrgency;
    Button buttonAddIncidencia;
    private EditText editTextTituloIncidencia, editTextDescripcionIncidencia;
    int urgencia;
    int tipo;
    Context context;

    String[] type ={"Incidencia", "Solicitud"};

    AutoCompleteTextView autoCompleteTextViewType;
    ArrayAdapter<String> adapterType;


    public AddIncidenciaFragment() {
        // Requiere un constructor vacío
    }

    public static AddIncidenciaFragment newInstance() {
        AddIncidenciaFragment fragment = new AddIncidenciaFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_add_incidencia, container, false);
        context = getActivity();
        authToken = getArguments().getString("session_token");
        //System.out.println(authToken + "esta en el fragment de crear incidencia");

        editTextTituloIncidencia = view.findViewById(R.id.editTextTituloIncidencia);
        editTextDescripcionIncidencia = view.findViewById(R.id.editTextDescripcionIncidencia);

        buttonAddIncidencia = view.findViewById(R.id.buttonAddIncidencia);
        autoCompleteTextViewUrgency = view.findViewById(R.id.autoCompleteUrgency);
        adapterUrgency = new ArrayAdapter<String>(context, R.layout.urgency_layout, urgency);
        autoCompleteTextViewUrgency.setAdapter(adapterUrgency);

        autoCompleteTextViewType = view.findViewById(R.id.autoCompleteType);
        adapterType = new ArrayAdapter<String>(context, R.layout.urgency_layout, type);
        autoCompleteTextViewType.setAdapter(adapterType);




        autoCompleteTextViewUrgency.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                urgencia = position + 1;
                System.out.println(urgencia);
            }
        });

        autoCompleteTextViewType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                tipo = position +1;
                System.out.println(tipo);
            }
        });


        buttonAddIncidencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(editTextTituloIncidencia.getText().toString().isEmpty()){

                    Toast.makeText(context, "Introduce un titulo", Toast.LENGTH_SHORT).show();
                }else if(editTextDescripcionIncidencia.getText().toString().isEmpty()){
                    Toast.makeText(context, "Introduce una descripcion", Toast.LENGTH_SHORT).show();

                }else if(autoCompleteTextViewUrgency.getText().toString().isEmpty()){
                    Toast.makeText(context, "Introduce la urgencia", Toast.LENGTH_SHORT).show();
                }else{
                    setTicket();
                }
            }
        });

        return view;
    }


    public void setTicket(){


        Ticket ticket = new Ticket(editTextTituloIncidencia.getText().toString(),editTextDescripcionIncidencia.getText().toString(),1,urgencia, tipo);
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

                Toast.makeText(context, "Ha ocurrido un error al cargar todos los datos", Toast.LENGTH_SHORT).show();
            }
        });
    }


}