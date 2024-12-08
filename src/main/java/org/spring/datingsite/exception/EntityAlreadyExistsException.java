package org.spring.datingsite.exception;

public class EntityAlreadyExistsException extends RuntimeException {
    public EntityAlreadyExistsException(String entity) {
        super(entity + " already exists.");
    }

    public EntityAlreadyExistsException(String entity, String attribute) {
        super(entity + " with such " + attribute + " already exists.");
    }
}
