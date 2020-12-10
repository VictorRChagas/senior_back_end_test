package br.com.senior.techicaltest.vendas.config;

import br.com.senior.techicaltest.vendas.util.MessageUtil;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.NoResultException;
import java.util.Optional;

@ControllerAdvice
public class ExceptionAdviceConfiguration {

    private static final String DATABASE_SCHEMA = "PUBLIC.";
    private static final int DATABASE_SCHEMA_LENGTH = DATABASE_SCHEMA.length();
    private static final String UNIQUE_INDEX_SUFFIX = "_INDEX";

    private final MessageUtil messageUtil;

    public ExceptionAdviceConfiguration(MessageUtil messageUtil) {
        this.messageUtil = messageUtil;
    }

    @ResponseBody
    @ExceptionHandler(NoResultException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Error> noResultExceptionHandler(NoResultException e) {
        return ResponseEntity.of(Optional.of(new Error(e.getMessage())));
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(DataIntegrityViolationException e) {
        return Optional.ofNullable(e.getMessage())
                .map(it -> it.substring(
                        it.indexOf(DATABASE_SCHEMA) + DATABASE_SCHEMA_LENGTH,
                        it.indexOf(UNIQUE_INDEX_SUFFIX)
                ))
                .map(messageUtil::get)
                .map(it -> ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Error(it)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Error(e.getCause().getMessage())));
    }
}
