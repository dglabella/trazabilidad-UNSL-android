package com.unsl.trazabilidadunsl.models;

public class Estadisticas
{
    private Integer totales;
    private Integer enTransito;
    private Integer totalesVisitantes;

    private Integer enTransitoVisitantes;

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

    public Integer getTotalesVisitantes() {
        return totalesVisitantes;
    }

    public void setTotalesVisitantes(Integer totalesVisitantes) {
        this.totalesVisitantes = totalesVisitantes;
    }

    public Integer getEnTransitoVisitantes() {
        return enTransitoVisitantes;
    }

    public void setEnTransitoVisitantes(Integer enTransitoVisitantes) {
        this.enTransitoVisitantes = enTransitoVisitantes;
    }

    @Override
    public String toString()
    {
        return "Estadisticas{" + "totalRegistros=" + totales + ", registrosPendientes=" + enTransito + '}';
    }
}