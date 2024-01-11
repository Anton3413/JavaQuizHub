package com.example.javaquizhub.model;

public enum Category {
    JAVA_CORE("Java Core"),
    STREAMS("Stream API"),
    MULTITHREADING("Multithreading"),
    INHERITANCE("Inheritance"),
    ALGORITHMS("Algorithms");

   private final String name;

    private final String getName(){
        return this.name;
    }

    Category(String name){
        this.name = name;
    }
}

