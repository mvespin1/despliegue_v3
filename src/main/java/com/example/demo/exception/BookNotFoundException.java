package com.example.demo.exception;

public class BookNotFoundException extends RuntimeException {
    
    private final String data;
    private final String entity;

    public BookNotFoundException(String data, String entity) {
        super();
        this.data = data;
        this.entity = entity;
    }

    @Override
    public String getMessage() {
        return "No se encontró ningún libro para: " + this.entity + ", con el dato: " + data;
    }
} 