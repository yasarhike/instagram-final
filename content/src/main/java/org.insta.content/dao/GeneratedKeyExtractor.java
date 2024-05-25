package org.insta.content.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.insta.content.exception.FetchDataFailedException;
import org.insta.content.model.Common;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * <p>
 * Utility class for setting IDs retrieved from a database query result.
 * </p>
 *
 * @author Mohamed Yasar
 * @version 1.0 6 Feb 2024
 */
public final class GeneratedKeyExtractor {

    private final Logger LOGGER = LogManager.getLogger(GeneratedKeyExtractor.class);

    /**
     * <p>
     * Private constructor to restrict object creation outside of the class.
     * </p>
     */
    private GeneratedKeyExtractor() {
    }

    /**
     * <p>
     * Returns the singleton instance of the GeneratedKeyExtractor class.
     * </p>
     *
     * @return The singleton instance of GeneratedKeyExtractor class.
     */
    public static GeneratedKeyExtractor getInstance() {
        return InstanceHolder.GENERATED_KEY_EXTRACTOR;
    }

    /**
     * <p>
     * Fetch the ID retrieved from the database query result to the provided entity.
     * </p>
     *
     * @param preparedStatement the PreparedStatement object used for the query
     * @param object            the entity to set the ID for
     * @return the ID set to the entity
     */
    public <T extends Common> Optional<Long> fetchKey(final PreparedStatement preparedStatement, final T object) {

        try (final ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
            if (resultSet.next()) {
                object.setId(resultSet.getLong("id"));
            }

            return Optional.ofNullable(object.getId());
        } catch (SQLException ignored) {
            LOGGER.error("Data fetch failed");
            throw new FetchDataFailedException("Data fetch failed");
        }
    }

    /**
     * <p>
     * Retrieves the ID from the database query result.
     * </p>
     *
     * @param preparedStatement PreparedStatement object used for the query.
     * @return the retrieved ID
     */
    public Optional<Long> fetchKey(final PreparedStatement preparedStatement) {
        try (final ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
            if (resultSet.next()) {

                return Optional.of(resultSet.getLong("id"));
            }

            return Optional.empty();
        } catch (SQLException ignored) {
            LOGGER.error("Data fetch failed");
            throw new FetchDataFailedException("Data fetch failed");
        }
    }

    /**
     * <p>
     * Static class for creating singleton instance.
     * </p>
     */
    private static class InstanceHolder {

        private static final GeneratedKeyExtractor GENERATED_KEY_EXTRACTOR = new GeneratedKeyExtractor();
    }
}
