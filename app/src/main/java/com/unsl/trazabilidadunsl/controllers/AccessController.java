package com.unsl.trazabilidadunsl.controllers;

import com.unsl.trazabilidadunsl.models.Acceso;
import com.unsl.trazabilidadunsl.services.AccessService;
import com.unsl.trazabilidadunsl.views.AccessView;
import com.unsl.trazabilidadunsl.views.ErrorView;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccessController implements Callback<List<Acceso>>
{
    private static AccessController accessController;

    private AccessView accessView;
    private ErrorView errorView;

    private AccessController(AccessView accessView, ErrorView errorView)
    {
        this.accessView = accessView;
        this.errorView = errorView;
    }

    public static AccessController getInstance(AccessView accessView, ErrorView errorView)
    {
        if(AccessController.accessController == null)
            AccessController.accessController = new AccessController(accessView, errorView);
        return AccessController.accessController;
    }

    public void getAccesses()
    {
        AccessService.setCallBack(this);
        AccessService.getAll();
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
            this.errorView.anotherResponse(response.code());
        }
    }

    @Override
    public void onFailure(Call<List<Acceso>> call, Throwable t)
    {
        String message = "Access -> ";
        if (t instanceof SocketTimeoutException)
        {
            // "Connection Timeout";
            message += "Connection Timeout";
        }
        else if (t instanceof IOException)
        {
            // "Timeout";
            message += "Timeout";
        }
        else
        {
            //Call was cancelled by user
            if(call.isCanceled())
            {
                System.out.println("Call was cancelled forcefully");
                message += "Call was cancelled forcefully";
            }
            else
            {
                //Generic error handling
                System.out.println("Network Error : " + t.getLocalizedMessage());
                message += t.getLocalizedMessage();
            }
        }
        this.errorView.error(message);
    }
}