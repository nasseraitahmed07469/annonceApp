package fr.projet.gestionAnnonces.exceptions;

import fr.projet.gestionAnnonces.models.enums.ErrorCodes;

public class AdNotFoundException extends RuntimeException {

    private final ErrorCodes errorCode = ErrorCodes.AD_NOT_FOUND;

    public AdNotFoundException(Long id) {
        super("Ad not found with id: " + id);
    }

    public ErrorCodes getErrorCode() {
        return errorCode;
    }
}