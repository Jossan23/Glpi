package com.example.glpi.fragments;

import android.content.Context;
import android.os.Bundle;

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

import com.example.glpi.api.persistencia.GlpiQuerys;


public class AddIncidenciaFragment extends Fragment {

    //Fragmento dónde se añaden las incidencias.
    private String authToken;
    private final String[] urgency = {"Muy poco urgente", "Poco urgente", "Urgencia media","Urgencia alta", "Muy urgente"};
    private AutoCompleteTextView autoCompleteTextViewUrgency;
    private ArrayAdapter<String> adapterUrgency;
    private Button buttonAddIncidencia;
    private EditText editTextTituloIncidencia, editTextDescripcionIncidencia;
    private int urgencia;
    private int tipo;
    private Context context;

    private final String[] type ={"Incidencia", "Solicitud"};

    private AutoCompleteTextView autoCompleteTextViewType;
    private ArrayAdapter<String> adapterType;
    private final GlpiQuerys glpiQuerys = new GlpiQuerys();


    //Métodos necesarios de Android
    public AddIncidenciaFragment() {
        // Requiere un constructor vacío
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

        //Se pasa el token de sesión al nuevo fragmento y se asignan los valores de los elementos
        // de la interfaz.
        context = getActivity();
        authToken = getArguments().getString("session_token");

        editTextTituloIncidencia = view.findViewById(R.id.editTextTituloIncidencia);
        editTextDescripcionIncidencia = view.findViewById(R.id.editTextDescripcionIncidencia);

        buttonAddIncidencia = view.findViewById(R.id.buttonAddIncidencia);
        autoCompleteTextViewUrgency = view.findViewById(R.id.autoCompleteUrgency);
        adapterUrgency = new ArrayAdapter<String>(context, R.layout.urgency_layout, urgency);
        autoCompleteTextViewUrgency.setAdapter(adapterUrgency);

        autoCompleteTextViewType = view.findViewById(R.id.autoCompleteType);
        adapterType = new ArrayAdapter<String>(context, R.layout.urgency_layout, type);
        autoCompleteTextViewType.setAdapter(adapterType);


        //Si el usuario pincha en el seleccionable de la urgencia se abre un desplegable.
        //Dependiendo dónde pinche, me actualiza la urgencia al valor correspondiente.
        //El +1 es porque si pincho en la primera posición el programa establece que es un 0
        // y en GLPI empieza desde el 1.

        autoCompleteTextViewUrgency.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                urgencia = position + 1;
                System.out.println(urgencia);
            }
        });

        //Otro desplegable cómo el primero que tiene la misma funcionalidad.
        autoCompleteTextViewType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                tipo = position +1;
                System.out.println(tipo);
            }
        });



        //Boton que se encarga de añadir la incidencia. Si los campos están en blanco no deja
        // añadir la incidencia. Si todos los campos están rellenados se obtiene el valor de todos
        //los campos, se llama al método setTickets de la interfaz JsonPlaceHolderApi
        //y se añade el ticket.

        buttonAddIncidencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(autoCompleteTextViewType.getText().toString().isEmpty()){
                    Toast.makeText(context, "Introduce el tipo de ticket", Toast.LENGTH_SHORT).show();
                }else if(editTextTituloIncidencia.getText().toString().isEmpty()){
                    Toast.makeText(context, "Introduce un titulo", Toast.LENGTH_SHORT).show();
                }else if(editTextDescripcionIncidencia.getText().toString().isEmpty()){
                    Toast.makeText(context, "Introduce una descripcion", Toast.LENGTH_SHORT).show();
                }else if(autoCompleteTextViewUrgency.getText().toString().isEmpty()){
                    Toast.makeText(context, "Introduce la urgencia", Toast.LENGTH_SHORT).show();

                }else{
                    glpiQuerys.setTicket(context,authToken,editTextTituloIncidencia.getText().toString(),editTextDescripcionIncidencia.getText().toString(),1,urgencia,tipo);

                }
            }
        });
        return view;
    }
}