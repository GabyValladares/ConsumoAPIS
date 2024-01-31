package com.ggvc.consumoservicios;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {
    EditText edNum1, edNum2;
    Button btProcesar;
    TextView tvResultado;
    String respuesta="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edNum1=findViewById(R.id.txtNum1);
        edNum2=findViewById(R.id.txtNum2);
        btProcesar=findViewById(R.id.btnEnviar);
        tvResultado=findViewById(R.id.lblResultado);


        btProcesar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConsumirAPI();
            }
        });

    }
    public void ConsumirAPI(){
        //DIRECCIÓN URL DEL SERVICIO A CONSUMIR
        String url="https://jsonplaceholder.typicode.com/todos/1";
        //OBJETO PARA EL USO DE PROTOCOLO HTTP
        OkHttpClient cliente = new OkHttpClient();
        //CONSTRUIMOS EL REQUERIMIENTO DEL TIPO DE API (GET,POST,PUT, DELETE)
        Request get = new Request.Builder()
                .url(url)
                .build();
        //A TRAVÉS DE OKHTTP LLAMAMOS AL SERVICIO Y ENCOLAMOS LAS PETICIONES
        cliente.newCall(get).enqueue(new Callback() {



            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    //OBTENEMOS LA RESPUESTA
                    ResponseBody responseBody = response.body();
                    if (!response.isSuccessful()) {
                        throw new IOException("Unexpected code " + response);

                    }else{

                    respuesta=responseBody.string();

                    // A TRAVÉS DEL USO DE HILOS PARALELAMENTE A LA CONSULTA DEL SERVIDOR MOSTRAMOS LA RESPUESTA
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            /*tvResultado.setText(respuesta);
                            Toast.makeText(MainActivity.this,"El resultado es:"+respuesta,Toast.LENGTH_LONG).show();*/
                            try {
                                JSONObject json=new JSONObject(respuesta);
                                tvResultado.setText(json.getString("title"));
                                edNum1.setText(json.getString("userId"));
                                edNum2.setText(json.getString("id"));
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }


                        }
                    });}



                    Log.i("data", responseBody.string());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });



    }
}