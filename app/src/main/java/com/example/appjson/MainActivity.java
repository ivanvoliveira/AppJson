package com.example.appjson;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.appjson.api.DataService;
import com.example.appjson.model.CEP;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private DataService service;
    private Call<CEP> call;

    private String cep;

    @BindView(R.id.textView_city) TextView textViewCEP;
    @BindView(R.id.textView_uf) TextView textViewUF;
    @BindView(R.id.textView_ibge) TextView textViewIBGE;
    @BindView(R.id.button_confirm) Button button;
    @BindView(R.id.editText_cep) EditText editTextCEP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://viacep.com.br/ws/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(DataService.class);
    }

    @OnClick(R.id.button_confirm)
    public void confirm(){
        if (!editTextCEP.getText().equals("")){
            cep = editTextCEP.getText().toString();
            call = service.getCEP(cep);
        }

        call.enqueue(new Callback<CEP>() {
            @Override
            public void onResponse(Call<CEP> call, Response<CEP> response) {
                if (response.isSuccessful()){
                    CEP cep = response.body();

                    textViewCEP.setText("Cidade: " + cep.getLocalidade());
                    textViewUF.setText("UF: " + cep.getUf());
                    textViewIBGE.setText("Habitantes: " + cep.getIbge());
                }
            }

            @Override
            public void onFailure(Call<CEP> call, Throwable t) {
                Log.i("Error:", t.getLocalizedMessage());
            }
        });
    }
}
