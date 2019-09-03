package com.restapi.smart.security;

public class InvalidJwtException extends RuntimeException{

    public InvalidJwtException(String msg){
        super(msg);
    }

}
