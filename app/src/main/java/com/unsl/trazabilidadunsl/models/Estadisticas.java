package com.unsl.trazabilidadunsl.models;

public class Estadisticas
{
    private Integer totales;
    private Integer enTransito;

    public Integer getTotales()
    {
        return totales;
    }

    public void setTotales(Integer totales)
    {
        this.totales = totales;
    }

    public Integer getEnTransito() {
        return enTransito;
    }

    public void setEnTransito(Integer enTransito)
    {
        this.enTransito = enTransito;
    }

    @Override
    public String toString()
    {
        return "Estadisticas{" + "totalRegistros=" + totales + ", registrosPendientes=" + enTransito + '}';
    }
}