package org.spring.datingsite.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String entity, String attribute) {
        super(entity + " with such " + attribute + " not found.");
    }
}
