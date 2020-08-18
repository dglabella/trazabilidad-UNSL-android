package com.unsl.trazabilidadunsl.controllers;

import com.unsl.trazabilidadunsl.activities.MainActivity;
import com.unsl.trazabilidadunsl.models.Registro;
import com.unsl.trazabilidadunsl.services.RegisterService;
import com.unsl.trazabilidadunsl.views.ErrorView;
import com.unsl.trazabilidadunsl.views.RegisterView;
import java.io.IOException;
import java.net.SocketTimeoutException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterController implements Callback<Registro>
{
    private static RegisterController registerController;

    private RegisterView registerView;
    private ErrorView errorView;

    public RegisterController(RegisterView registerView, ErrorView errorView)
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

    public void createRegister(Integer personId)
    {
        RegisterService.setCallBack(this);
        Registro register = new Registro();
        register.setFkAcceso(MainActivity.getSelectedAccess().getId());
        register.setFkPersona(personId);
        register.setEstado("ACTIVO");
        RegisterService.postRegister(register);
    }

    @Override
    public void onResponse(Call<Registro> call, Response<Registro> response)
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
    public void onFailure(Call<Registro> call, Throwable t)
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
        this.errorView.error(message);
    }
}
