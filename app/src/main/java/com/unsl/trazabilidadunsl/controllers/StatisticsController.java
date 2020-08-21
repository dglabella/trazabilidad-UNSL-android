package com.unsl.trazabilidadunsl.controllers;

import com.unsl.trazabilidadunsl.models.Estadisticas;
import com.unsl.trazabilidadunsl.views.ErrorView;
import com.unsl.trazabilidadunsl.views.StatisticsView;

import java.io.IOException;
import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatisticsController implements Callback<Estadisticas>
{
    private static StatisticsController statisticsController;

    private StatisticsView statisticsView;
    private ErrorView errorView;

    private StatisticsController(StatisticsView statisticsView, ErrorView errorView)
    {
        this.statisticsView = statisticsView;
        this.errorView = errorView;
    }

    public static StatisticsController getInstance(StatisticsView statisticsView, ErrorView errorView)
    {
        if(StatisticsController.statisticsController == null)
            StatisticsController.statisticsController = new StatisticsController(statisticsView, errorView);
        return StatisticsController.statisticsController;
    }

    @Override
    public void onResponse(Call<Estadisticas> call, Response<Estadisticas> response)
    {
        this.statisticsView.reportStatistics(response.body());
    }

    @Override
    public void onFailure(Call<Estadisticas> call, Throwable t)
    {
        String message = "Statistics -> ";
        if (t instanceof SocketTimeoutException)
        {
            // "Connection Timeout";
            message +="Connection Timeout";
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
