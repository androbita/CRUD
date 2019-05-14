package com.ngopidevteam.pranadana.crud.network;

import com.ngopidevteam.pranadana.crud.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConfigRetrofit {

    public static Retrofit setInstance(){
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://192.168.60.205/mahasiswa_server/index.php/Server/")
                .build();
    }

    public static MahasiswaService getInstance(){
        return setInstance().create(MahasiswaService.class);
    }
}
