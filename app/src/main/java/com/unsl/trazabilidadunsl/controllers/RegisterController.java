package com.unsl.trazabilidadunsl.controllers;

import com.unsl.trazabilidadunsl.models.RegistroCellPhone;
import com.unsl.trazabilidadunsl.services.RegisterService;
import com.unsl.trazabilidadunsl.views.ErrorView;
import com.unsl.trazabilidadunsl.views.RegisterView;
import java.io.IOException;
import java.net.SocketTimeoutException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterController implements Callback<RegistroCellPhone>
{
    private static RegisterController registerController;

    private RegisterView registerView;
    private ErrorView errorView;

    private RegisterController(RegisterView registerView, ErrorView errorView)
    {
        this.registerView = registerView;
        this.errorView = errorView;
    }

    public static RegisterController getInstance(RegisterView registerView, ErrorView errorView)
    {
        if(RegisterController.registerController == null)
            RegisterController.registerController = new RegisterController(registerView, errorView);
        return RegisterController.registerController;
    }

    public void createRegister(RegistroCellPhone registroCellPhone)
    {
        RegisterService.setCallBack(this);
        RegisterService.postRegister(registroCellPhone);
    }

    @Override
    public void onResponse(Call<RegistroCellPhone> call, Response<RegistroCellPhone> response)
    {
        if(response.isSuccessful())
        {
            this.registerView.registerDone(response.body());
        }
        else
        {
            this.errorView.anotherResponse(response.code());
        }
    }

    @Override
    public void onFailure(Call<RegistroCellPhone> call, Throwable t)
    {
        String message = "Register -> ";
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
