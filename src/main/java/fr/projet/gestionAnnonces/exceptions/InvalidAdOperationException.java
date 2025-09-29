package fr.projet.gestionAnnonces.exceptions;

import fr.projet.gestionAnnonces.models.enums.ErrorCodes;

public class InvalidAdOperationException extends RuntimeException {

    private final ErrorCodes errorCode = ErrorCodes.INVALID_AD_OPERATION;

    public InvalidAdOperationException(String message) {
        super(message);
    }

    public ErrorCodes getErrorCode() {
        return errorCode;
    }
}