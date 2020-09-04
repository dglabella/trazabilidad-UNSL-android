package com.unsl.trazabilidadunsl.interfaces;

import com.unsl.trazabilidadunsl.models.Acceso;
import com.unsl.trazabilidadunsl.models.Estadisticas;
import com.unsl.trazabilidadunsl.models.Persona;
import com.unsl.trazabilidadunsl.models.RegistroCellPhone;
import com.unsl.trazabilidadunsl.models.RegistroVisitante;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonPlaceHolderAPI
{
    @GET("accesos")
    Call<List<Acceso>> getAccesses();

    @GET("personas/{id}")
    Call<Persona> getPerson(@Path("id") Integer id);

    //@POST("registros")
    //Call<Registro> postRegister(@Body Registro Registro);

    @POST("registros/cellPhone")
    Call<RegistroCellPhone> postRegister(@Body RegistroCellPhone registerCellPhone);

    @GET("registros/statistics")
    Call<Estadisticas> getStatistics(@Query("accesoId") Integer accessId);

    @POST("registrosvisitantes")
    Call<RegistroVisitante> postVisitorRegistration(@Body RegistroVisitante visitorRegister);
}
