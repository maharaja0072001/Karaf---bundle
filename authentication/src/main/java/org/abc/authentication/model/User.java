package org.abc.authentication.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * Represents an individual user who can create account, login, and place the orders. It encapsulates all the user related information.
 * </p>
 *
 * @author Maharaja S
 * @version 1.0
 */
public class User {

    private String name;
    private String emailId;
    private String mobileNumber;
    private String password;
    private int id;
    private List<String> addresses;

    public void setId(final int id) {
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setMobileNumber(final String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setEmailId(final String emailId) {
        this.emailId = emailId;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getPassword() {
        return password;
    }

    public void addAddress(String address) {
        addresses = (null == addresses) ? new ArrayList<>() : addresses;

        addresses.add(address);
    }

    public List<String> getAddresses() {
        return addresses;
    }

    @Override
    public boolean equals(final Object object) {
        if (null == object || getClass() != object.getClass()) {
            return false;
        }

        return this.hashCode() == object.hashCode();
    }

    @Override
    public int hashCode() {
        return Objects.hash(emailId, mobileNumber);
    }
}