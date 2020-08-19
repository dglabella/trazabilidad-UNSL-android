package com.unsl.trazabilidadunsl.models;

public class RegistroCellPhone
{
    private Long idAcceso;
    private String encryptedData;

    public RegistroCellPhone()
    {
        super();
    }

    public RegistroCellPhone(Long idAcceso, String encryptedData)
    {
        super();
        this.idAcceso = idAcceso;
        this.encryptedData = encryptedData;
    }

    public Long getIdAcceso()
    {
        return idAcceso;
    }

    public void setIdAcceso(Long idAcceso)
    {
        this.idAcceso = idAcceso;
    }

    public String getEncryptedData()
    {
        return encryptedData;
    }

    public void setEncryptedData(String encryptedData)
    {
        this.encryptedData = encryptedData;
    }
}