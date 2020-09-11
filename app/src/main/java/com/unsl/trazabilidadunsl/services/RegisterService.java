package com.unsl.trazabilidadunsl.services;

import com.unsl.trazabilidadunsl.activities.AccessSelectionActivity;
import com.unsl.trazabilidadunsl.interfaces.JsonPlaceHolderAPI;
import com.unsl.trazabilidadunsl.models.RegistroCellPhone;
import com.unsl.trazabilidadunsl.models.RegistroVisitante;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterService
{
    public static String RESOURCE = "registros";
    private final static int REQUEST_CONNECT_TIMEOUT_TOLERANCE = 20;
    private final static int REQUEST_READ_TIMEOUT_TOLERANCE = 2;
    private final static int REQUEST_WRITE_TIMEOUT_TOLERANCE = 5;
    private static Callback<RegistroCellPhone> registroCellPhoneCallBack;
    private static Callback<RegistroVisitante> registroVisitanteCallBack;

    public static void setRegistroCellPhoneCallBack(Callback<RegistroCellPhone> callBack)
    {
        RegisterService.registroCellPhoneCallBack = callBack;
    }

    public static void setRegistroVisitanteCallBack(Callback<RegistroVisitante> callBack)
    {
        RegisterService.registroVisitanteCallBack = callBack;
    }

    public static void postRegister(RegistroCellPhone registroCellPhone)
    {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(REQUEST_CONNECT_TIMEOUT_TOLERANCE, TimeUnit.SECONDS)
                .readTimeout(REQUEST_READ_TIMEOUT_TOLERANCE, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_WRITE_TIMEOUT_TOLERANCE, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AccessSelectionActivity.API_HOSTNAME)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderAPI jsonPlaceHolderAPI = retrofit.create(JsonPlaceHolderAPI.class);
        Call<RegistroCellPhone> call = jsonPlaceHolderAPI.postRegister(registroCellPhone);

        //This will call (asynchronouslly)the OnResponse/OnErrorResponse method in Controller
        call.enqueue(registroCellPhoneCallBack);
    }

    public static void postRegister(RegistroVisitante VisitorRegistration)
    {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(REQUEST_CONNECT_TIMEOUT_TOLERANCE, TimeUnit.SECONDS)
                .readTimeout(REQUEST_READ_TIMEOUT_TOLERANCE, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_WRITE_TIMEOUT_TOLERANCE, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AccessSelectionActivity.API_HOSTNAME)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderAPI jsonPlaceHolderAPI = retrofit.create(JsonPlaceHolderAPI.class);
        Call<RegistroVisitante> call = jsonPlaceHolderAPI.postVisitorRegistration(VisitorRegistration);

        //This will call (asynchronouslly)the OnResponse/OnErrorResponse method in Controller
        call.enqueue(registroVisitanteCallBack);
    }
}
