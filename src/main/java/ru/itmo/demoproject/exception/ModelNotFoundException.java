package ru.itmo.demoproject.exception;

import java.util.UUID;

public class ModelNotFoundException extends RuntimeException {

    public ModelNotFoundException(String message) {
        super(message);
    }

    public ModelNotFoundException(Class<?> model, UUID id) {
        super(String.format("Can't find %s with id = %s. ", model.getSimpleName(), id.toString()));
    }
}
