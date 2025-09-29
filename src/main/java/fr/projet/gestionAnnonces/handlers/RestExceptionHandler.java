package fr.projet.gestionAnnonces.handlers;

import fr.projet.gestionAnnonces.exceptions.AdNotFoundException;
import fr.projet.gestionAnnonces.exceptions.DuplicateAdException;
import fr.projet.gestionAnnonces.exceptions.InvalidAdDataException;
import fr.projet.gestionAnnonces.exceptions.InvalidAdOperationException;
import fr.projet.gestionAnnonces.models.dto.ErrorDto;
import fr.projet.gestionAnnonces.models.enums.ErrorCodes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(AdNotFoundException.class)
    public ResponseEntity<ErrorDto> handleAdNotFoundException(AdNotFoundException ex, WebRequest request) {
        final HttpStatus status = HttpStatus.NOT_FOUND;
        final ErrorDto errorDto = ErrorDto.builder()
                .httpCode(status.value())
                .code(ex.getErrorCode())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorDto, status);
    }

    @ExceptionHandler(InvalidAdOperationException.class)
    public ResponseEntity<ErrorDto> handleInvalidAdOperationException(InvalidAdOperationException ex, WebRequest request) {
        final HttpStatus status = HttpStatus.BAD_REQUEST;
        final ErrorDto errorDto = ErrorDto.builder()
                .httpCode(status.value())
                .code(ex.getErrorCode())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorDto, status);
    }

    @ExceptionHandler(InvalidAdDataException.class)
    public ResponseEntity<ErrorDto> handleInvalidAdDataException(InvalidAdDataException ex, WebRequest request) {
        final HttpStatus status = HttpStatus.BAD_REQUEST;
        final ErrorDto errorDto = ErrorDto.builder()
                .httpCode(status.value())
                .code(ex.getErrorCode())
                .message(ex.getMessage())
                .errors(ex.getErrors())
                .build();
        return new ResponseEntity<>(errorDto, status);
    }

    @ExceptionHandler(DuplicateAdException.class)
    public ResponseEntity<ErrorDto> handleDuplicateAdException(DuplicateAdException ex, WebRequest request) {
        final HttpStatus status = HttpStatus.CONFLICT;
        final ErrorDto errorDto = ErrorDto.builder()
                .httpCode(status.value())
                .code(ex.getErrorCode())
                .message(ex.getMessage())
                .errors(ex.getErrors())
                .build();
        return new ResponseEntity<>(errorDto, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleGenericException(Exception ex, WebRequest request) {
        final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        final ErrorDto errorDto = ErrorDto.builder()
                .httpCode(status.value())
                .code(ErrorCodes.UNKNOWN_ERROR)
                .message("An unexpected error occurred")
                .errors(List.of(ex.getMessage()))
                .build();
        return new ResponseEntity<>(errorDto, status);
    }
}