package com.ggvc.consumoservicios;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PostActivity extends AppCompatActivity {
    TextView tvRespuesta;
    EditText etNombre, etTrabajo;
    Button btProcesar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        tvRespuesta=findViewById(R.id.lblRespuesta);
        etNombre=findViewById(R.id.txtNombre);
        etTrabajo=findViewById(R.id.txtTrabajo);
        btProcesar=findViewById(R.id.btnProcesar);

        btProcesar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProcesarAPI();
            }
        });

    }
    public void ProcesarAPI(){
        String URl="https://jsonplaceholder.typicode.com/posts";
        String envioNombre=etNombre.getText().toString();
        String envioTrabajo=etTrabajo.getText().toString();
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestBody postBody=new FormBody.Builder()
                        .add("userId",envioNombre)
                        .add("id",envioTrabajo)
                        .build();

                Request request=new Request.Builder()
                        .url(URl)
                        .post(postBody)
                        .build();

                OkHttpClient cliente=new OkHttpClient();
                Call call=cliente.newCall(request);

                try {
                    Response respuesta=call.execute();
                    String serverResponse=respuesta.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            tvRespuesta.setText(serverResponse);
                        }
                    });




                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


            }
        }).start();




    }
}