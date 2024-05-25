package org.insta.wrapper.jsonvalidator;

import org.insta.wrapper.exception.JsonWrapperException;
import org.insta.wrapper.hibernate.Validate;
import org.insta.wrapper.jackson.MapperObject;
import org.insta.wrapper.jackson.NodeArray;
import org.insta.wrapper.jackson.NodeJson;
import org.insta.wrapper.jackson.NodeObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>
 * Utility class for validating objects and generating response payloads.
 * </p>
 *
 * <p>
 * This class provides methods for validating objects, generating success and failure
 * response payloads, and manual response payloads for various scenarios.
 * </p>
 *
 * @author Mohamed Yasar
 * @version 1.0 6 Feb 2024
 * @see Validate
 * @see MapperObject
 * @see NodeObject
 * @see NodeArray
 */
public final class JsonResponseHandler {

    private final Validate validate;

    /**
     * <p>
     * Constructs an ObjectValidator instance and initializes the Validate instance.
     * </p>
     */
    private JsonResponseHandler() {
        validate = Validate.getInstance();
    }

    /**
     * <p>
     * Returns the singleton instance of ObjectValidator.
     * </p>
     *
     * @return The singleton instance of ObjectValidator.
     */
    public static JsonResponseHandler getInstance() {
        return InstanceHolder.JSON_RESPONSE_HANDLER;
    }

    /**
     * <p>
     * Validates the specified object against the specified validation groups and
     * returns the validation result as a byte array.
     * </p>
     *
     * @param <T>    The type of the object to validate.
     * @param object The object to validate.
     * @param groups The validation groups to apply.
     * @return The validation result as a byte array.
     */
    public <T> byte[] validate(final T object, final Class<?> groups) {
        try {
            final MapperObject mapperObject = new MapperObject();
            final NodeObject nodeObject = mapperObject.getObjectNode();

            validate.validate(object, groups).forEach(violation -> nodeObject.objectNode().put(violation.getPropertyPath().toString(), violation.getMessage()));

            return !nodeObject.objectNode().isEmpty() ? mapperObject.getObjectMapper().writeValueAsString(nodeObject.objectNode()).getBytes() : new byte[]{};
        } catch (Exception exception) {
            throw new JsonWrapperException("Parse Operation failed");
        }
    }

    /**
     * <p>
     * Generates a success response payload with the specified table ID and violations data.
     * </p>
     *
     * @param optionalId The ID of the table.
     * @param violations The violations data.
     * @return The success response payload as a byte array.
     */
    public byte[] responseWithID(final Optional<Long> optionalId, final byte[] violations) {
        try {
            final MapperObject mapperObject = new MapperObject();
            final NodeObject response = mapperObject.getObjectNode();
            final NodeObject tableData = mapperObject.getObjectNode();
            final NodeArray nodeArray = mapperObject.getArrayNode();

            if (optionalId.isPresent()) {
                tableData.put("id", optionalId.get());
            } else {
                tableData.put("status", "invalid credentials");
            }

            response.setValue("data", tableData.objectNode());
            response.setValue("Violations ", mapperObject.readTree(violations));
            nodeArray.add(response.objectNode());

            return mapperObject.writeValueAsString(nodeArray.getArrayNode()).getBytes();
        } catch (Exception ignored) {
            throw new JsonWrapperException("Parse operation failed");
        }
    }

    /**
     * <p>
     * Generates a response payload for the specified object.
     * </p>
     *
     * @param <T>    The type of the object.
     * @param object The object.
     * @return The response payload as a byte array.
     */
    public <T> byte[] objectResponse(final T object) {
        if (Objects.isNull(object)) return stringManualResponse("User not found");
        try {
            final MapperObject mapperObject = new MapperObject();

            return mapperObject.writeValueAsString(object).getBytes();
        } catch (Exception ignored) {
            throw new JsonWrapperException("Parse operation failed");
        }
    }

    /**
     * <p>
     * Generates a manual response payload with the specified result.
     * </p>
     *
     * @param result The result of the operation.
     * @return The manual response payload as a byte array.
     */
    public byte[] responseWithStatus(final boolean result) {
        try {
            final MapperObject mapperObject = new MapperObject();
            final NodeArray nodeArray = mapperObject.getArrayNode();
            final NodeObject nodeObject = mapperObject.getObjectNode();
            if (result) {
                nodeObject.put("status", "successful");
            } else {
                nodeObject.put("status", "Failed");
            }
            nodeArray.add(nodeObject.objectNode());

            return mapperObject.writeValueAsString(nodeArray.getArrayNode()).getBytes();
        } catch (Exception ignored) {
            throw new JsonWrapperException("Parse operation failed");
        }
    }

    /**
     * <p>
     * Generates a manual response payload with the specified result as a string.
     * </p>
     *
     * @param result The result string.
     * @return The manual response payload as a byte array.
     */
    public byte[] stringManualResponse(final String result) {
        try {
            final MapperObject mapperObject = new MapperObject();
            final NodeArray nodeArray = mapperObject.getArrayNode();
            final NodeObject nodeObject = mapperObject.getObjectNode();

            nodeObject.put("status", result);
            nodeArray.add(nodeObject.objectNode());

            return mapperObject.writeValueAsString(nodeArray.getArrayNode()).getBytes();
        } catch (Exception exception) {
            throw new JsonWrapperException("Parse failed");
        }
    }

    /**
     * <p>
     * Extracts a table ID from the given JSON result.
     * </p>
     *
     * @param result the JSON result as a byte array
     * @return a map containing the extracted ID
     */
    public Map<String, String> getTableId(final byte[] result) {
        try {
            final MapperObject mapperObject = new MapperObject();
            final NodeJson nodeJson = mapperObject.getNodeJson();
            Map<String, String> resultMap = new HashMap<>();

            nodeJson.setJsonNode(mapperObject.readTree(result));
            if (nodeJson.getJsonNode().isArray() && nodeJson.getJsonNode().size() > 0) {
                nodeJson.setJsonNode(nodeJson.getJsonNode().get(0).get("data"));

                if (nodeJson.getJsonNode().has("id")) {
                    resultMap.put("id", nodeJson.getJsonNode().get("id").toString());
                }
            }

            return resultMap;
        } catch (Exception e) {
            throw new JsonWrapperException("Parse operation failed");
        }
    }

    /**
     * <p>
     * Checks the status of the given JSON result.
     * </p>
     *
     * @param result the JSON result as a byte array
     * @return true if the status is "successful", otherwise false
     */
    public boolean getStatus(final byte[] result) {
        try {
            final MapperObject mapperObject = new MapperObject();
            final NodeJson nodeJson = new NodeJson();

            nodeJson.setJsonNode(mapperObject.readTree(result));

            if (nodeJson.getJsonNode().isArray() && nodeJson.getJsonNode().size() > 0) {
                nodeJson.setJsonNode(nodeJson.getJsonNode().get(0));

                if (nodeJson.getJsonNode().has("status")) {
                    String status = nodeJson.getJsonNode().get("status").asText();

                    return "successful".equalsIgnoreCase(status);
                }
            }
            return false;
        } catch (Exception e) {
            throw new JsonWrapperException("Parse operation failed");
        }
    }

    /**
     * <p>
     * Extracts the user ID from the given JSON result.
     * </p>
     *
     * @param result the JSON result as a byte array
     * @return the user ID as a Long, or null if not found
     */
    public Long getObjectId(final byte[] result) {
        final MapperObject mapperObject = new MapperObject();
        final NodeJson nodeJson = new NodeJson();

        nodeJson.setJsonNode(mapperObject.readTree(result));

        if (nodeJson.getJsonNode().has("userId")) {
            return nodeJson.getJsonNode().get("userId").asLong();
        }

        return null;
    }

    /**
     * <p>
     * Static class for creating singleton instance.
     * </p>
     */
    private static class InstanceHolder {

        private static final JsonResponseHandler JSON_RESPONSE_HANDLER = new JsonResponseHandler();
    }
}
