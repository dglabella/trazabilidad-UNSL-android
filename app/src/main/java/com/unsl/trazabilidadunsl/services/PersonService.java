package com.unsl.trazabilidadunsl.services;

import com.unsl.trazabilidadunsl.activities.AccessSelectionActivity;
import com.unsl.trazabilidadunsl.interfaces.JsonPlaceHolderAPI;
import com.unsl.trazabilidadunsl.models.Persona;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PersonService
{
    public static String RESOURCE = "personas";
    private final static int REQUEST_CONNECT_TIMEOUT_TOLERANCE = 20;
    private final static int REQUEST_READ_TIMEOUT_TOLERANCE = 2;
    private final static int REQUEST_WRITE_TIMEOUT_TOLERANCE = 5;
    private static Callback<Persona> callBack;

    public static void setCallBack(Callback<Persona> callBack)
    {
        PersonService.callBack = callBack;
    }

    public static void getById(Integer personId)
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
        Call<Persona> call = jsonPlaceHolderAPI.getPerson(personId);

        //This will call (asynchronouslly)the OnResponse/OnErrorResponse method in Controller
        call.enqueue(callBack);
    }
}
