package com.juniorgonzalez.spring.example.component;

import org.springframework.stereotype.Component;

@Component
public class ComponentTwoImplement implements  ComponentDependency{
    @Override
    public void greet() {
        System.out.println("Hello world form the component two");
    }
}
