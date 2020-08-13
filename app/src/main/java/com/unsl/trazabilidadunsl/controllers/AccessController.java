package com.unsl.trazabilidadunsl.controllers;

import com.unsl.trazabilidadunsl.models.Acceso;
import com.unsl.trazabilidadunsl.services.AccesoService;
import com.unsl.trazabilidadunsl.views.AccessView;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccessController implements Callback<List<Acceso>>
{
    private AccessView accessView;

    public AccessController(AccessView accessView)
    {
        this.accessView = accessView;
    }

    public void getAccess()
    {
        AccesoService.setCallBack(this);
        AccesoService.getAll();
    }

    @Override
    public void onResponse(Call<List<Acceso>> call, Response<List<Acceso>> response)
    {
        if(response.isSuccessful())
        {
            this.accessView.manageAccessObtained(response.body());
        }
        else
        {
            this.accessView.anotherResponse(response.code());
        }
    }

    @Override
    public void onFailure(Call<List<Acceso>> call, Throwable t)
    {
        String message;
        if (t instanceof SocketTimeoutException)
        {
            // "Connection Timeout";
            message = "Connection Timeout";
        }
        else if (t instanceof IOException)
        {
            // "Timeout";
            message = "Timeout";
        }
        else
        {
            //Call was cancelled by user
            if(call.isCanceled())
            {
                System.out.println("Call was cancelled forcefully");
                message = "Call was cancelled forcefully";
            }
            else
            {
                //Generic error handling
                System.out.println("Network Error :: " + t.getLocalizedMessage());
                message = t.getLocalizedMessage();
            }
        }
        this.accessView.error(message);
    }
}
