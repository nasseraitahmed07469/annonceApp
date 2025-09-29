package fr.projet.gestionAnnonces.exceptions;

import fr.projet.gestionAnnonces.models.enums.ErrorCodes;

import java.util.List;

public class DuplicateAdException extends RuntimeException {

    private final ErrorCodes errorCode = ErrorCodes.DUPLICATE_AD;
    private final List<String> errors;

    public DuplicateAdException(String message, List<String> errors) {
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
