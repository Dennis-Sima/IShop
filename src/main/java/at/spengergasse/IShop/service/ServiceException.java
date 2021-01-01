package at.spengergasse.IShop.service;

import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

public class ServiceException extends RuntimeException{

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public static final ServiceException forPersistenceException(PersistenceException pEx) {
        String message = String.format("Problems during persistence operation detected! Root cause was: '%s'", pEx.getMessage());
        return new ServiceException(message, pEx);
    }
}
