package com.example.glpi;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.glpi.api.modelos.Ticket;
import com.example.glpi.api.interfaces.JsonPlaceHolderApi;
import com.example.glpi.api.persistencia.RetroFitSingleton;
import com.example.glpi.databinding.ActivityMainBinding;
import com.example.glpi.fragments.AddIncidenciaFragment;
import com.example.glpi.fragments.ViewIncidenciaFragment;

import java.util.List;

import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Context _context;
    private AddIncidenciaFragment fragmentoIncidencia;
    private ViewIncidenciaFragment fragmentoView;
    private String authKey;
    private List<Ticket> ticketList;
    Bundle extraFragment;
    private final Retrofit retrofit = RetroFitSingleton.getInstance().getRetroFit();

    private final JsonPlaceHolderApi querys = retrofit.create(JsonPlaceHolderApi.class);

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

        fragmentoIncidencia = new AddIncidenciaFragment();
        fragmentoIncidencia.setArguments(extraFragment);
        changeFragment(fragmentoIncidencia);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch(item.getItemId()){

                case R.id.crearIncidenciaMenu:
                    extraFragment = new Bundle();
                    extraFragment.putString("session_token", authKey);

                    fragmentoIncidencia = new AddIncidenciaFragment();
                    fragmentoIncidencia.setArguments(extraFragment);

                    changeFragment(fragmentoIncidencia);

                    break;

                case R.id.verIncidenciasMenu:
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

    private void changeFragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.frameLayout,fragment);

        fragmentTransaction.commit();

    }
}
