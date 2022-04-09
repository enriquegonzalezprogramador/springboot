package com.juniorgonzalez.spring.example.bean;

public class MyBeanWithPropertiesImplement implements MyBeanWithProperties {

    private String nombre, apellido;

    public MyBeanWithPropertiesImplement(String nombre, String apellido){
        this.nombre = nombre;
        this.apellido = apellido;
    }

    @Override
    public String function() {
        return nombre + "-" + apellido;
    }
}
