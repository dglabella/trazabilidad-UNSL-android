package com.unsl.trazabilidadunsl.views;

import com.unsl.trazabilidadunsl.models.Acceso;

import java.util.List;

public interface AccessView
{
    void manageAccessObtained(List<Acceso> access);

    void error(String message);

    void anotherResponse(int code);
}
