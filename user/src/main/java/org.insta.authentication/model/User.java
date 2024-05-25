package org.insta.authentication.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.insta.authentication.groups.UserCredentialsValidator;

import java.util.Objects;

/**
 * <p>
 * Contains the user details.
 * </p>
 *
 * @author Mohamed Yasar k
 * @version 1.0 6 Feb 2024
 * @see Address
 */
public final class User {

    @PositiveOrZero(message = "User id must be positive or zero", groups = UserCredentialsValidator.class)
    private Long userId;
    @NotNull(message = "Name must not be null", groups = UserCredentialsValidator.class)
    private String name;
    @NotNull(message = "Mobile must not be null", groups = UserCredentialsValidator.class)
    private String mobileNumber;
    @NotNull(message = "Email must not be null", groups = UserCredentialsValidator.class)
    private String email;
    @NotNull(message = "Password must not be null", groups = UserCredentialsValidator.class)
    private String password;
    @Valid
    @NotNull(message = "Address must not be null", groups = UserCredentialsValidator.class)
    private Address address;

    public User() {
        this.address = new Address();
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(final Address address) {
        this.address = address;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(final String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String userName) {
        this.name = userName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof User) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, mobileNumber);
    }
}
