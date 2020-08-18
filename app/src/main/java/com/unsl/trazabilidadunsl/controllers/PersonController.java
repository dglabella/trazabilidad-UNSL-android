package com.unsl.trazabilidadunsl.controllers;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

import com.unsl.trazabilidadunsl.models.Persona;
import com.unsl.trazabilidadunsl.services.PersonService;
import com.unsl.trazabilidadunsl.views.ErrorView;
import com.unsl.trazabilidadunsl.views.PersonView;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Iterator;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonController implements Callback<Persona>
{
    private static PersonController personController;
    private PersonView personView;
    private ErrorView errorView;
    private List<Persona> persons;
    private Persona person;

    private Context context;

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

    public void initRegister(Integer personId)
    {
        //RegisterService.setCallBack(this);
        //RegisterService.postRegister(register);
        PersonService.setCallBack(this);
        PersonService.getById(personId);

        /*
        if(p != null){
            RegisterController.getInstance().recordEvent(PersonController.getPersonaSelected().getId());
            RegisterController.getInstance().getStatistics(accesoSelected.getId());
            view.mostrarPersona(p);
        }
        else{
            view.error("ERROR DE CONEXIÓN CON EL SERVICIO");
        }
        */
    }


    @Override
    public void onResponse(Call<Persona> call, Response<Persona> response)
    {
        this.person = response.body();

        for(int i = 0; i < 100; i++)
        {
            Log.d("MESSAGE ------ ", person.getId()+" --- "+ person.getNombre()+" "+person.getApellido());
        }

        /*
        this.persons = response.body();
        Iterator<Persona> personsIterator = this.persons.iterator();
        Persona person;
        while(personsIterator.hasNext())
        {
            person = personsIterator.next();
            Log.d("MESSAGE ------ ", person.getId()+" --- "+ person.getNombre()+" "+person.getApellido());
            Log.d("MESSAGE ------ ", person.getId()+" --- "+ person.getNombre()+" "+person.getApellido());
            Log.d("MESSAGE ------ ", person.getId()+" --- "+ person.getNombre()+" "+person.getApellido());
            Log.d("MESSAGE ------ ", person.getId()+" --- "+ person.getNombre()+" "+person.getApellido());
            Log.d("MESSAGE ------ ", person.getId()+" --- "+ person.getNombre()+" "+person.getApellido());
            Log.d("MESSAGE ------ ", person.getId()+" --- "+ person.getNombre()+" "+person.getApellido());
            Log.d("MESSAGE ------ ", person.getId()+" --- "+ person.getNombre()+" "+person.getApellido());
            Log.d("MESSAGE ------ ", person.getId()+" --- "+ person.getNombre()+" "+person.getApellido());
            Log.d("MESSAGE ------ ", person.getId()+" --- "+ person.getNombre()+" "+person.getApellido());
        }
        */
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
