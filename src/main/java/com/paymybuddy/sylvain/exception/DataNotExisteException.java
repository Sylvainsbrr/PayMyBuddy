package com.paymybuddy.sylvain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class DataNotExisteException extends RuntimeException{

    /**
     *
     */
    private static final long serialVersionUID = -582616643185311224L;

    public DataNotExisteException(String message){super(message);}
}

