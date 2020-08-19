package com.unsl.trazabilidadunsl.models;

public class Acceso
{
    private Integer id;
    private String descripcion;
    private String estado;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getDescripcion()
    {
        return descripcion;
    }

    public void setDescripcion(String descripcion)
    {
        this.descripcion = descripcion;
    }

    public String getEstado()
    {
        return estado;
    }

    public void setEstado(String estado)
    {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return this.getId()+"-"+this.getDescripcion();
    }
}
