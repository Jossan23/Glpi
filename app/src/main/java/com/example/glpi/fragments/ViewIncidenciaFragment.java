package com.example.glpi.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.glpi.R;
import com.example.glpi.adapters.ListViewAdapter;
import com.example.glpi.api.modelos.Ticket;
import com.example.glpi.api.interfaces.DetailTicketsInterface;
import com.example.glpi.api.interfaces.JsonPlaceHolderApi;
import com.example.glpi.api.persistencia.RetroFitSingleton;

import java.io.IOException;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class ViewIncidenciaFragment extends Fragment implements DetailTicketsInterface {


    //Fragmento donde se ven las incidencias
    private final Retrofit retrofit = RetroFitSingleton.getInstance().getRetroFit();

    private final JsonPlaceHolderApi querys = retrofit.create(JsonPlaceHolderApi.class);
    private View view;
    private String authKey;
    private List<Ticket> ticketList;
    private Context context;
    private ListViewAdapter adapter;
    private RecyclerView recycView;


    //Métodos internos de Android para mostrar el fragmento.
    public ViewIncidenciaFragment() {
        // Requiere un constructor vacío
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Coge los argumentos pasados. En este caso, el token de sesión.

        authKey = getArguments().getString("session_token");
        view = inflater.inflate(R.layout.fragment_view_incidencia,container,false);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //Se asignan los elementos necesarios de la vista y se les da valor.
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        recycView = view.findViewById(R.id.recyclerView);
        recycView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new ListViewAdapter(context , ticketList, this);
        recycView.setAdapter(adapter);
        //Lamada al metodo que permite coger los tickets y listarlos
        getTicket();
    }


    public void getTicket(){


        //Este método asíncrono llama a la interfaz query y se le pasa
        // el token de sesión como parámetro.

        Call<List<Ticket>> tickets = querys.getTicket(authKey);

        tickets.enqueue( new Callback<List<Ticket>>() {
            @Override
            public void onResponse(Call<List<Ticket>> call, Response<List<Ticket>> response) {

                if(response.isSuccessful()){
                    //Si la respuesta es satisfactoria, se coge la lista de tickets y se le pasa
                    //al adaptador para que vaya mostrando los tickets mediante el método setData()
                    ticketList = response.body();
                    adapter.setData(ticketList);

                    System.out.println("he terminado");

                }else{
                    //Si no, se le comunica al usuario que ha habido un error al obtener los
                    //tickets en GLPI
                    try {
                        Toast.makeText(context, "Error al obtener los datos de GLPI", Toast.LENGTH_SHORT).show();
                        System.out.println(response.errorBody().string() + "b,kjasbd,fkjbasdhfb");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Ticket>> call, Throwable t) {
                //Si no se puede conectar, se comunica un error de conexión
                Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show();
                System.out.println(t.getMessage());

            }
        });
    }



    //Este método coge la posicion en la que el usuario hace click y se pasa al fragmento detalle
    //El fragmento detalle es dónde se muestran los detalles técnicos de los tickets creados
    //por los usuarios. Dependiendo de la posición se sabe dónde está pinchando el usuario,
    //por lo que, se le pasa al fragmento detalle los detalles del ticket
    // obteniendo el ticket de la posición de la lista en la que el usuario ha pinchado.

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