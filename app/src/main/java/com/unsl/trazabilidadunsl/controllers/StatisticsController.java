package com.unsl.trazabilidadunsl.controllers;

import com.unsl.trazabilidadunsl.models.Estadisticas;
import com.unsl.trazabilidadunsl.services.StatisticsServices;
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

    public static StatisticsController getInstance()
    {
        if(StatisticsController.statisticsController == null)
            StatisticsController.statisticsController = new StatisticsController();
        return StatisticsController.statisticsController;
    }

    public StatisticsView getStatisticsView() {
        return statisticsView;
    }

    public void setStatisticsView(StatisticsView statisticsView) {
        this.statisticsView = statisticsView;
    }

    public ErrorView getErrorView() {
        return errorView;
    }

    public void setErrorView(ErrorView errorView) {
        this.errorView = errorView;
    }

    public void getStatistics(Integer accessId)
    {
        StatisticsServices.setCallBack(this);
        StatisticsServices.getStatistics(accessId);
    }

    @Override
    public void onResponse(Call<Estadisticas> call, Response<Estadisticas> response)
    {
        if(response.isSuccessful())
        {
            this.statisticsView.reportStatistics(response.body());
        }
        else
        {
            this.errorView.anotherResponse(response.code());
        }
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
