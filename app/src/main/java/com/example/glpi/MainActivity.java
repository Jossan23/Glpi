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

    ActivityMainBinding binding;
    Context _context;
    String authKey;

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

        changeFragment(new AddIncidenciaFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch(item.getItemId()){

                case R.id.crearIncidenciaMenu:
                    changeFragment(new AddIncidenciaFragment());
                    break;

                case R.id.verIncidenciasMenu:
                    changeFragment(new ViewIncidenciaFragment());
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
