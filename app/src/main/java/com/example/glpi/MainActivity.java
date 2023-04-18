package com.example.glpi;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.example.glpi.databinding.ActivityMainBinding;
import com.example.glpi.fragments.AddIncidenciaFragment;
import com.example.glpi.fragments.ViewIncidenciaFragment;

public class MainActivity extends AppCompatActivity {

    //Esta actividad es la encargada de manejar los diferentes fragmentos.
    //Cuando el usuario pulsa en la barra inferior de la pantalla, se cambia el fragmento.
    //También se pasa el token de sesion necesario para hacer las consultas a traves de
    //los diferentes fragmentos.



    private ActivityMainBinding binding;
    private Context _context;
    private AddIncidenciaFragment fragmentoIncidencia;
    private ViewIncidenciaFragment fragmentoView;
    private String authKey;
    Bundle extraFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _context = this;

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            authKey = extras.getString("session_token");

            System.out.println(authKey);
        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        extraFragment = new Bundle();
        extraFragment.putString("session_token", authKey);


        //Por defecto se abre el fragmento de Añadir Ticket
        fragmentoIncidencia = new AddIncidenciaFragment();
        fragmentoIncidencia.setArguments(extraFragment);
        changeFragment(fragmentoIncidencia);

        //Dependiendo de si el usuario quiere añadir o ver los tickets,la aplicación
        // detecta el cambio y dependendiendo del botón se cambia al fragmento necesario.

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch(item.getItemId()){

                case (R.id.crearIncidenciaMenu):
                    extraFragment = new Bundle();
                    extraFragment.putString("session_token", authKey);

                    fragmentoIncidencia = new AddIncidenciaFragment();
                    fragmentoIncidencia.setArguments(extraFragment);

                    changeFragment(fragmentoIncidencia);

                    break;

                case (R.id.verIncidenciasMenu):
                    //Fragmento incidencias. En este, se listan todas las incidencias
                    extraFragment = new Bundle();
                    extraFragment.putString("session_token", authKey);

                    fragmentoView = new ViewIncidenciaFragment();
                    fragmentoView.setArguments(extraFragment);

                    changeFragment(fragmentoView);
                    break;

            }
            return true;
        });
    }


    //Método que se usa para cambiar de fragmento.
    private void changeFragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.frameLayout,fragment);

        fragmentTransaction.commit();

    }
}
