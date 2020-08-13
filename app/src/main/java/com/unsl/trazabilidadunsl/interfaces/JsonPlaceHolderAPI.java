package com.unsl.trazabilidadunsl.interfaces;

import com.unsl.trazabilidadunsl.models.Acceso;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderAPI
{
    @GET("accesos")
    Call<List<Acceso>> getAccesos();
}
