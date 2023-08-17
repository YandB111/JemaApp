package com.jema.app.exceptions;

/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 19-Mar-2023
*
*/

public final class UserExistanceException extends RuntimeException {

    private static final long serialVersionUID = 5861310537366287163L;

    public UserExistanceException() {
        super();
    }

    public UserExistanceException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UserExistanceException(final String message) {
        super(message);
    }

    public UserExistanceException(final Throwable cause) {
        super(cause);
    }

}
