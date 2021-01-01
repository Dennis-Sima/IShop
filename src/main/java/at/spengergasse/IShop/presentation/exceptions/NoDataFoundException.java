package at.spengergasse.IShop.presentation.exceptions;

import at.spengergasse.IShop.domain.*;

public final class NoDataFoundException extends RuntimeException{

    public NoDataFoundException(String message)
    {
        super(message);
    }

    public static NoDataFoundException forTypeAndId(Class clazz, Long id){
        String message = String.format("Cannot load instance of type '%s' with id: '%d'", clazz.getSimpleName(), id);
        return new NoDataFoundException(message);
    }



}
