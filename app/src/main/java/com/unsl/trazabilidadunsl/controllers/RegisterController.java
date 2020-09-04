package com.unsl.trazabilidadunsl.controllers;

import com.unsl.trazabilidadunsl.models.RegistroCellPhone;
import com.unsl.trazabilidadunsl.models.RegistroVisitante;
import com.unsl.trazabilidadunsl.services.RegisterService;
import com.unsl.trazabilidadunsl.views.ErrorView;
import com.unsl.trazabilidadunsl.views.RegisterView;
import java.io.IOException;
import java.net.SocketTimeoutException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterController<T> implements Callback<T>
{
    private static RegisterController registerController;

    private RegisterView registerView;
    private ErrorView errorView;

    public static RegisterController getInstance()
    {
        if(RegisterController.registerController == null)
            RegisterController.registerController = new RegisterController();

        return RegisterController.registerController;
    }

    public RegisterView getRegisterView()
    {
        return registerView;
    }

    public void setRegisterView(RegisterView registerView)
    {
        this.registerView = registerView;
    }

    public ErrorView getErrorView()
    {
        return errorView;
    }

    public void setErrorView(ErrorView errorView) {
        this.errorView = errorView;
    }

    public void createRegister(RegistroCellPhone registroCellPhone)
    {
        RegisterService.setRegistroCellPhoneCallBack((Callback<RegistroCellPhone>) this);
        RegisterService.postRegister(registroCellPhone);
    }

    public void createRegister(RegistroVisitante VisitorRegistration)
    {
        RegisterService.setRegistroVisitanteCallBack((Callback<RegistroVisitante>) this);
        RegisterService.postRegister(VisitorRegistration);
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response)
    {
        if(response.isSuccessful())
        {
            this.registerView.registerDone();
        }
        else
        {
            this.errorView.anotherResponse(response.code());
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t)
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
            message += "ENTRADA INVALIDA.";
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
