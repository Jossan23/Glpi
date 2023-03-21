package com.example.glpi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.example.glpi.api.Base64Encoder;
import com.example.glpi.api.get.InitSession;
import com.example.glpi.interfaces.JsonPlaceHolderApi;
import com.example.glpi.persistencia.RetroFitSingleton;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {


    private static String authToken;

    Retrofit retrofit = RetroFitSingleton.getInstance().getRetroFit();

    JsonPlaceHolderApi querys = retrofit.create(JsonPlaceHolderApi.class);

    private TextView textView;
    private Context _context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _context = this;

        textView = findViewById(R.id.textView);

        autenticarUsuario("glpi","glpi");

    }

    /*
    private void getPostInitSession(){

        Call<List<Users>> call = querys.getPostInitSession();

        call.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {

                if(!response.isSuccessful()){
                    textView.setText("Codigo: " + response.code());
                    return;
                }

                List<Users> listaUsuario = response.body();

                for(Users u : listaUsuario){
                    String content = "usuario: " + u.getUsername() + "\n" +
                            "password: " + u.getPassword() + "\n";
                }
            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {

                textView.setText(t.getMessage());

            }
        });

    }


     */


    /*
    private void getUsers(){

        Users usuario = new Users("glpi","glpi");

        Call<ResponseBody> call = querys.getUser(usuario);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(!response.isSuccessful()){
                    //textView.setText(response.code());
                    System.out.println(response.code());
                    return;
                }

                //textView.setText(response.code());
                System.out.println(response.code());

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                //textView.setText(t.getMessage());
                System.out.println(t.getMessage());


            }
        });

    }

     */


    private void autenticarUsuario(String username, String password){
        String authorization = Base64Encoder.base64encode(username + ":" + password);

        Call<InitSession> call = querys.getSecret("Basic " + authorization.trim());

        call.enqueue(new Callback<InitSession>() {
            @Override
            public void onResponse(Call<InitSession> call, Response<InitSession> response) {
                if(response.isSuccessful()){
                    System.out.println(response.code());
                    System.out.println("Biennn");
                }else{
                    String errorMessage;
                    try {
                        errorMessage = response.errorBody().string();
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<InitSession> call, Throwable t) {

                System.out.println(t.getMessage());

            }
        });

    }
}