package org.abc.dbconnection.exceptions;

import org.abc.exception.CustomException;

/**
 * <p>
 * Provides information on file unavailable error.
 * </p>
 *
 * @author Maharaja S
 * @version 1.0
 */
public class FileUnavailableException extends CustomException {

    /**
     * <p>
     * Default constructor of FileUnavailableException class.
     * </p>
     *
     * @param message Refers the message to be displayed.
     */
    public FileUnavailableException(final String message) {
        super(message);
    }
}
