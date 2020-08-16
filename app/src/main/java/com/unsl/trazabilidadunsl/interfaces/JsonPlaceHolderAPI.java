package com.unsl.trazabilidadunsl.interfaces;

import com.unsl.trazabilidadunsl.models.Acceso;
import com.unsl.trazabilidadunsl.models.Registro;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface JsonPlaceHolderAPI
{
    @GET("accesos")
    Call<List<Acceso>> getAccesses();

    @POST("registros")
    Call<Registro> postRegister(@Body Registro register);
}
