package com.example.glpi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.glpi.api.persistencia.GlpiQuerys;


public class LoginActivity extends AppCompatActivity {


    //Login Activity que permite la autenticación de usuarios en Glpi. Hace la consulta, si es satisfactoria
    //se pasa a la siguiente Actividad, si no, se notifica al usuario de que el usuario y la contraseña
    //no son correctos.

    private EditText editTextUsuario;
    private EditText editTextPassword;
    private Button botonLogin;
    private Context _context;
    private final GlpiQuerys glpiQuerys = new GlpiQuerys();


    //Si tienes un app token habilitado, debes deshabilitarlo, de lo contrario,
    //no va a funcionar la autenticación.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _context = this;

        editTextUsuario = findViewById(R.id.editTextUsuario);
        editTextPassword = findViewById(R.id.editTextPassword);
        botonLogin = findViewById(R.id.botonLogin);


        //Si el usuario pulsa en el botón de Login, se inicia el proceso de autenticación.
        botonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(editTextUsuario.getText().toString().isEmpty() || editTextPassword.getText().toString().isEmpty()){

                    Toast.makeText(_context, "Introduce tu usuario y contraseña", Toast.LENGTH_SHORT).show();
                }else{
                    glpiQuerys.autenticarUsuario(_context,editTextUsuario.getText().toString(),editTextPassword.getText().toString());
                }
            }
        });
    }

    /*
    public String autenticarUsuario(String username, String password){

        String base = username + ":" + password;

        String authorization = Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        Call<InitSession> call = querys.getUser("Basic " + authorization.trim());

        call.enqueue(new Callback<InitSession>() {
            @Override
            public void onResponse(Call<InitSession> call, Response<InitSession> response) {
                if(response.isSuccessful()){

                    authToken = response.body().getSessionToken();


                    Toast.makeText(LoginActivity.this, "Usuario autenticado", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(_context, MainActivity.class);
                    intent.putExtra("session_token", authToken);
                    startActivity(intent);


                    //System.out.println("Biennn");
                }else{
                    String errorMessage;
                    try {
                        errorMessage = response.errorBody().string();
                        System.out.println(errorMessage);
                        Toast.makeText(LoginActivity.this, "Compruebe credenciales", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        System.out.println(e);
                        System.out.println("MAL");
                    }
                }
            }

            @Override
            public void onFailure(Call<InitSession> call, Throwable t) {

                System.out.println(t.getMessage());
                Toast.makeText(LoginActivity.this, "Fallo de conexión, vuelve a intentarlo", Toast.LENGTH_SHORT).show();
            }
        });
        return authToken;
    }


     */



}