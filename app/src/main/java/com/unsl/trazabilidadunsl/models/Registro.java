package com.unsl.trazabilidadunsl.models;

import java.sql.Timestamp;

public class Registro {
    private Integer id;
    private String estado;
    private Integer fkPersona;
    private Integer fkAcceso;
    private Timestamp horaIngreso;
    private Timestamp horaEgreso;
    private boolean habilitado;

    private Acceso acceso;
    private Persona persona;

    public Registro(){}

    public Registro(Integer id, String estado, Integer fkPersona, Integer fkAcceso, Timestamp horaIngreso, Timestamp horaEgreso)
    {
        this.id = id;
        this.estado = estado;
        this.fkPersona = fkPersona;
        this.fkPersona = fkAcceso;
        this.horaIngreso = horaIngreso;
        this.horaEgreso = horaEgreso;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getFkPersona() {
        return fkPersona;
    }

    public void setFkPersona(Integer fkPersona) {
        this.fkPersona = fkPersona;
    }

    public Integer getFkAcceso() {
        return fkAcceso;
    }

    public void setFkAcceso(Integer fkAcceso) {
        this.fkAcceso = fkAcceso;
    }

    public Timestamp getHoraIngreso() {
        return horaIngreso;
    }

    public void setHoraIngreso(Timestamp horaIngreso) {
        this.horaIngreso = horaIngreso;
    }

    public Timestamp getHoraEgreso() {
        return horaEgreso;
    }

    public void setHoraEgreso(Timestamp horaEgreso) {
        this.horaEgreso = horaEgreso;
    }

    public boolean isHabilitado()
    {
        return this.habilitado;
    }

    public void setHabilitado(boolean habilitado)
    {
        this.habilitado = habilitado;
    }

    public Acceso getAcceso()
    {
        return acceso;
    }

    public void setAcceso(Acceso acceso)
    {
        this.acceso = acceso;
    }

    public Persona getPersona()
    {
        return persona;
    }

    public void setPersona(Persona persona)
    {
        this.persona = persona;
    }

    @Override
    public String toString() {
        return "Registro{" + "id=" + id + ", estado=" + estado + ", fkPersona=" + fkPersona + ", fkAcceso=" + fkAcceso + ", horaIngreso=" + horaIngreso + ", horaEgreso=" + horaEgreso + ", habilitado=" + habilitado + '}';
    }
}
