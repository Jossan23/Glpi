package com.example.glpi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;

import com.example.glpi.api.get.InitSession;
import com.example.glpi.api.interfaces.JsonPlaceHolderApi;
import com.example.glpi.api.persistencia.RetroFitSingleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {


    private static String authToken;

    Retrofit retrofit = RetroFitSingleton.getInstance().getRetroFit();

    JsonPlaceHolderApi querys = retrofit.create(JsonPlaceHolderApi.class);

    private EditText editTextUsuario, editTextPassword;
    private Button botonLogin;
    private Context _context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _context = this;

        editTextUsuario = findViewById(R.id.editTextUsuario);
        editTextPassword = findViewById(R.id.editTextPassword);
        botonLogin = findViewById(R.id.botonLogin);

        autenticarUsuario("glpi","glpi");

    }


    public void autenticarUsuario(String username, String password){

        String base = username + ":" + password;


        String authorization = Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        Call<InitSession> call = querys.getUser("Basic " + authorization.trim());

        call.enqueue(new Callback<InitSession>() {
            @Override
            public void onResponse(Call<InitSession> call, Response<InitSession> response) {
                if(response.isSuccessful()){

                    authToken = response.body().getSessionToken();

                    //Toast.makeText(.this, "Usuario autenticado", Toast.LENGTH_SHORT).show();

                    System.out.println(authToken);


                    //System.out.println("Biennn");
                }else{
                    String errorMessage;
                    try {
                        errorMessage = response.errorBody().string() + "     asd";
                        System.out.println(errorMessage);
                        System.out.println("Algo falla");
                    } catch (Exception e) {
                        System.out.println(e);
                        System.out.println("MAL");
                    }
                }
            }

            @Override
            public void onFailure(Call<InitSession> call, Throwable t) {

                System.out.println(t.getMessage());
                System.out.println("Ni te conecta parguela");
            }
        });
    }
}