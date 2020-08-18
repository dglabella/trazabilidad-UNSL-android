package com.unsl.trazabilidadunsl.controllers;

import com.unsl.trazabilidadunsl.models.Persona;
import com.unsl.trazabilidadunsl.views.ErrorView;
import com.unsl.trazabilidadunsl.views.PersonView;
import java.io.IOException;
import java.net.SocketTimeoutException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonController implements Callback<Persona>
{
    private static PersonController personController;
    private PersonView personView;
    private ErrorView errorView;
    //private List<Persona> persons;
    private Persona person;

    private PersonController(PersonView personView, ErrorView errorView)
    {
        this.personView = personView;
        this.errorView = errorView;
    }

    public static PersonController getInstance(PersonView personView, ErrorView errorView)
    {
        if(PersonController.personController == null)
            PersonController.personController = new PersonController(personView, errorView);
        return PersonController.personController;
    }

    public PersonView getPersonView()
    {
        return this.personView;
    }


    @Override
    public void onResponse(Call<Persona> call, Response<Persona> response)
    {
        this.person = response.body();
    }

    @Override
    public void onFailure(Call<Persona> call, Throwable t)
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
