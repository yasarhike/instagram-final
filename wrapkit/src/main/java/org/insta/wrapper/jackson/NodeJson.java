package org.insta.wrapper.jackson;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * <p>
 * Class representing a JSON node wrapper.
 * </p>
 *
 * <p>
 * This class encapsulates a {@link JsonNode} and provides methods for setting and
 * getting the underlying JsonNode.
 * </p>
 *
 * @author Mohamed Yasar
 * @version 1.0 6 Feb 2024
 * @see JsonNode
 */
public class NodeJson {

    private JsonNode jsonNode;

    /**
     * <p>
     * Constructs a NodeJson with the specified JsonNode.
     * </p>
     *
     * @param jsonNode the JsonNode to be wrapped
     */
    public NodeJson(final JsonNode jsonNode) {
        this.jsonNode = jsonNode;
    }

    /**
     * <p>
     * Default constructor for NodeJson.
     * </p>
     */
    public NodeJson() {
    }

    /**
     * <p>
     * Returns the wrapped JsonNode.
     * </p>
     *
     * @return the wrapped JsonNode
     */
    public JsonNode getJsonNode() {
        return this.jsonNode;
    }

    /**
     * <p>
     * Sets the JsonNode.
     * </p>
     *
     * @param jsonNode the JsonNode to be set
     */
    public void setJsonNode(final JsonNode jsonNode) {
        this.jsonNode = jsonNode;
    }
}

