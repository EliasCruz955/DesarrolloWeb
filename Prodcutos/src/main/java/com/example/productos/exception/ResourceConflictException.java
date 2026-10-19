package com.example.productos.exception;

public class ResourceConflictException extends RuntimeException{

    public ResourceConflictException(String message){
        super(message);
    }
}
