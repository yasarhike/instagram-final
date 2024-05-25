package org.insta.authentication.model;

import jakarta.validation.constraints.NotNull;
import org.insta.authentication.groups.UserCredentialsValidator;

/**
 * <p>
 * Contains the address details of the user.
 * </p>
 *
 * @author Mohamed Yasar k
 * @version 1.0 6 Feb 2024
 */
public final class Address {

    @NotNull(message = "Country must not be null", groups = UserCredentialsValidator.class)
    private String country;
    @NotNull(message = "Country code must not be null", groups = UserCredentialsValidator.class)
    private String countryCode;
    @NotNull(message = "State must not be null", groups = UserCredentialsValidator.class)
    private String state;
    @NotNull(message = "Door number must not be null", groups = UserCredentialsValidator.class)
    private int doorNumber;
    @NotNull(message = "Street name must not be null", groups = UserCredentialsValidator.class)
    private String streetName;

    public int getDoorNumber() {
        return doorNumber;
    }

    public void setDoorNumber(final int doorNumber) {
        this.doorNumber = doorNumber;
    }

    public String getState() {
        return state;
    }

    public void setState(final String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(final String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(final String countryCode) {
        this.countryCode = countryCode;
    }

    public void setStreetName(final String streetName) {
        this.streetName = streetName;
    }
}

