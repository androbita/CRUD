package com.ngopidevteam.pranadana.crud.network;

import com.ngopidevteam.pranadana.crud.model.ResponseInsert;
import com.ngopidevteam.pranadana.crud.model.ResponseMahasiswa;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MahasiswaService {

    @GET("getMahasiswa")
    Call<ResponseMahasiswa> getMahasiswa();

    @FormUrlEncoded
    @POST("insertMahasiswa")
    Call<ResponseInsert> postMahasiswa(
            @Field("nim") String nim,
            @Field("nama") String nama,
            @Field("jurusan") String jurusan
    );

    @FormUrlEncoded
    @POST("updateMahasiswa")
    Call<ResponseMahasiswa> updateMahasiswa(
            @Field("id") String id,
            @Field("nim") String nim,
            @Field("nama") String nama,
            @Field("jurusan") String jurusan
    );

    @FormUrlEncoded
    @POST("deleteMahasiswa")
    Call<ResponseMahasiswa> deleteMahasiswa(
            @Field("id") String id
    );
}
