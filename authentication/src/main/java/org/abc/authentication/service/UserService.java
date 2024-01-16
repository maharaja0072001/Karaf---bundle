package org.abc.authentication.service;

import org.abc.authentication.model.User;

/**
 * <p>
 * Provides the service for the User.
 * </p>
 *
 * @author Maharaja S
 * @version 1.0
 */
public interface UserService {

    /**
     * <p>
     * Checks if the user already exists. if not then creates a new user.
     * </p>
     *
     * @return true if the user created or false if user already exists.
     * @param user Refers the {@link User}to be created.
     */
    boolean createUser(final User user);

    /**
     * <p>
     * Gets the existing user by the given credentials.
     * </p>
     *
     * @param emailIdOrMobileNumber Refers the email id or mobile number of the user
     * @param password Refers the password of the user.
     * @return {@link User} if the credentials are correct and the user exists or null otherwise.
     */
    User getUser(final String emailIdOrMobileNumber, final String password);

    /**
     * <p>
     * Updates the details of the user.
     * </p>
     *
     * @param user Refers the current {@link User}.
     */
    void updateDetails(final User user);
}
