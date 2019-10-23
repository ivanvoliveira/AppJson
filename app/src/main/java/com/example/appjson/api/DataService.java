package com.example.appjson.api;

import com.example.appjson.model.CEP;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DataService {

    @GET("{cep}/json/")
    Call<CEP> getCEP(@Path("cep") String cep);

}
