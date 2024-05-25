package org.insta.databaseconnection.exception;

import org.insta.exception.DefaultException;

/**
 * <p>
 * Exception for database connection failed.
 * </p>
 *
 * @see RuntimeException
 *
 * @author Mohamed Yasar
 * @version 1.0 6 Feb 2024
 */
public final class DatabaseConnectionFailedException extends DefaultException {

    /**
     * <p>
     * Constructs an InstagramException with the specified detail message.
     * </p>
     *
     * @param message Refers the exception message.
     */
    public DatabaseConnectionFailedException(final String message) {
        super(message);
    }
}
