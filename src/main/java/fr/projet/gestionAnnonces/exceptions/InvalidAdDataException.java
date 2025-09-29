package fr.projet.gestionAnnonces.exceptions;

import fr.projet.gestionAnnonces.models.enums.ErrorCodes;

import java.util.List;

public class InvalidAdDataException extends RuntimeException {

    private final ErrorCodes errorCode = ErrorCodes.INVALID_AD_DATA;
    private final List<String> errors;

    public InvalidAdDataException(String message, List<String> errors) {
        super(message);
        this.errors = errors;
    }

    public ErrorCodes getErrorCode() {
        return errorCode;
    }

    public List<String> getErrors() {
        return errors;
    }

}

