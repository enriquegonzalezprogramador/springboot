package com.juniorgonzalez.spring.example.bean;

public class MyBeanImplement implements MyBean{
    @Override
    public void print() {
        System.out.println("Hello from my implementation bean");
    }
}
