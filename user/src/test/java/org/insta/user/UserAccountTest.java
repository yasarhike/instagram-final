package org.insta.user;

import org.insta.authentication.controller.UserAccountController;
import org.insta.authentication.dao.UserAccountDAO;
import org.insta.authentication.dao.UserAccountDAOImpl;
import org.insta.authentication.model.Address;
import org.insta.authentication.model.User;
import org.insta.wrapper.jsonvalidator.JsonResponseHandler;
import org.junit.Test;

import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * <p>
 * Test class for checking the workflow of the userAccountController.
 * </p>
 *
 * @author Mohamed Yasar
 * @version 1.0 6 Feb 2024
 * @see JsonResponseHandler
 * @see UserAccountDAOImpl
 */
public class UserAccountTest {

    private final UserAccountDAO userAccountDAO;
    private final UserAccountController userAccountController;
    private final JsonResponseHandler jsonResponseHandler;

    /**
     * <p>
     * Initializes the UserAccountTest class by setting up the necessary mocks and
     * injecting them into the UserAccountController.
     * </p>
     */
    public UserAccountTest() {
        userAccountDAO = mock(UserAccountDAOImpl.class);
        userAccountController = UserAccountController.getInstance();
        jsonResponseHandler = JsonResponseHandler.getInstance();
        userAccountController.getUserService().setReplacer(userAccountDAO);
    }

    /**
     * <p>
     * Tests the deleteProfile method of UserAccountController.
     * Verifies the behavior when deleting a user profile with a valid and invalid ID.
     * </p>
     */
    @Test
    public void deleteUser() {
        when(userAccountDAO.deleteProfile(1l)).thenReturn(true);
        when(userAccountDAO.deleteProfile(2l)).thenReturn(false);

        final byte[] idPresent = userAccountController.deleteProfile(1l);
        final byte[] idAbsent = userAccountController.deleteProfile(2l);

        assertTrue(jsonResponseHandler.getStatus(idPresent));
        assertFalse(jsonResponseHandler.getStatus(idAbsent));
    }

    /**
     * <p>
     * Tests the createProfile method of UserAccountController.
     * Verifies the behavior when creating a new user profile.
     * </p>
     */
    @Test
    public void createUser() {
        Address address = new Address();
        address.setCountry("India");
        address.setCountryCode("IN");
        address.setState("Karnataka");
        address.setDoorNumber(91);
        address.setStreetName("MG Road");

        User user = new User();
        user.setAddress(address);
        user.setUserId(1L);
        user.setName("Yasarhike");
        user.setMobileNumber("1234567890");
        user.setEmail("yasarhike19@gmail.com");
        user.setPassword("Password123");
        when(userAccountDAO.createProfile(user)).thenReturn(Optional.empty());

        final byte[] result = userAccountController.createUser(user);
        final Map<String, String> response = jsonResponseHandler.getTableId(result);

        if (response.containsKey("id")) {
            assert (Long.parseLong(response.get("id")) == 1l);
        }
    }

    /**
     * <p>
     * Tests the getProfile method of UserAccountController.
     * Verifies the behavior when retrieving a user profile with a valid ID.
     * </p>
     */
    @Test
    public void getUser() {
        final User user = new User();

        user.setMobileNumber("9788734219");
        user.setPassword("Yasar@123");
        user.setName("Yasar");
        user.setUserId(1l);

        when(userAccountDAO.getProfile(1l)).thenReturn(Optional.of(user));
        final byte[] result = userAccountController.getProfile(1l);

        assertTrue(jsonResponseHandler.getObjectId(result).equals(1l));
    }
}
