package org.tutorials.wproject1.exception;

public class ResourceAlreadyExistException extends RuntimeException {

    private static final long serialVersionUID=-9079454849611061072L;

    public ResourceAlreadyExistException() {
        super();
    }

    public ResourceAlreadyExistException(final String message) {
        super(message);
    }

}
