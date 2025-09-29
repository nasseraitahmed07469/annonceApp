package fr.projet.gestionAnnonces.models.dto;

import fr.projet.gestionAnnonces.models.enums.ErrorCodes;
import lombok.Builder;

import java.util.List;

@Builder
public record ErrorDto(

        Integer httpCode,
        ErrorCodes code,
        String message,
        List<String> errors

) {}