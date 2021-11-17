package com.yanki.consumer.app.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Maestros implements Serializable {
    private static final long serialVersionUID = 1L;
    private TypeMaestro type;
    private String valor;
    public enum TypeMaestro {
        TYPEDOCUMENT,
        OPERADORA
    }
}
