package com.example.javaquizhub.model;

public enum Category {
    JAVA_CORE("Java Core"),
    STREAMS("Stream API"),
    MULTITHREADING("Multithreading"),
    INHERITANCE("Inheritance"),
    ALGORITHMS("Algorithms");

    private final String formattedName;

    public String getFormattedName(){
        return this.formattedName;
    }

    Category(String formattedName){
        this.formattedName = formattedName;
    }

}

