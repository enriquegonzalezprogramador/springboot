package com.juniorgonzalez.spring.example.bean;

public class MyBeanWithDependencyImplement implements MyBeanWithDependency{

    private MyOperation myOperation;

    public MyBeanWithDependencyImplement(MyOperation myOperation){
        this.myOperation = myOperation;
    }

    @Override
    public void printWithDependency() {
        int num = 1;

        System.out.println(myOperation.sum(num));

        System.out.println("Hello form Bean with dependency");
    }
}
