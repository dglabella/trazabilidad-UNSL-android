package com.unsl.trazabilidadunsl.models;

import java.util.Date;

public class RegistroVisitante
{
    private Long id;
    private Date horaIngreso;
    private Date horaEgreso;
    private Acceso acceso;
    private Long fkAcceso;
    private Integer dni;
    private String nombre;
    private String apellido;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Date getHoraIngreso()
    {
        return horaIngreso;
    }

    public void setHoraIngreso(Date horaIngreso)
    {
        this.horaIngreso = horaIngreso;
    }

    public Date getHoraEgreso()
    {
        return horaEgreso;
    }

    public void setHoraEgreso(Date horaEgreso)
    {
        this.horaEgreso = horaEgreso;
    }

    public Acceso getAcceso()
    {
        return acceso;
    }

    public void setAcceso(Acceso acceso)
    {
        this.acceso = acceso;
    }

    public Long getFkAcceso()
    {
        return fkAcceso;
    }

    public void setFkAcceso(Long fkAcceso)
    {
        this.fkAcceso = fkAcceso;
    }

    public Integer getDni()
    {
        return dni;
    }

    public void setDni(Integer dni)
    {
        this.dni = dni;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getApellido()
    {
        return apellido;
    }

    public void setApellido(String apellido)
    {
        this.apellido = apellido;
    }
}
