package org.insta.content.dao.story.share;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.insta.content.exception.story.storyshare.StoryShareFailedException;
import org.insta.content.exception.story.storyshare.StoryShareRemovalFailedException;
import org.insta.content.dao.GeneratedKeyExtractor;
import org.insta.content.sqlinjector.story.share.StoryShareSqlInjector;
import org.insta.databaseconnection.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

/**
 * <p>
 * Managing user story share.
 * </p>
 *
 * @author Mohamed Yasar
 * @version 1.0 6 Feb 2024
 * @see StoryShareDAO
 */
public class StoryShareDAOImpl implements StoryShareDAO {

    private static final Logger LOGGER = LogManager.getLogger(StoryShareDAOImpl.class);
    private final Connection connection;
    private final GeneratedKeyExtractor generatedKeyExtractor;
    private final StoryShareSqlInjector storyShareQueryStructureBuilder;

    /**
     * <p>
     * Restrict object creation outside of the class
     * </p>
     */
    private StoryShareDAOImpl() {
        connection = DatabaseConnection.get();
        generatedKeyExtractor = GeneratedKeyExtractor.getInstance();
        storyShareQueryStructureBuilder = StoryShareSqlInjector.getInstance();
    }

    /**
     * <p>
     *  Static class for creating singleton instance.
     * </p>
     */
    private static class InstanceHolder {

        private static final StoryShareDAO storyShareDAO = new StoryShareDAOImpl();
    }

    /**
     * <p>
     * Returns the singleton instance of StoryShareDAOImpl class.
     * </p>
     *
     * @return The singleton instance of StoryShareDAOImpl class.
     */
    public static StoryShareDAO getInstance() {
        return InstanceHolder.storyShareDAO;
    }

    /**
     * {@inheritDoc}
     *
     * @param storyId  The ID of the story to be shared.
     * @param sharedBy The ID of the user who shared the story.
     * @return The ID of the added share if successful, otherwise 0.
     */
    @Override
    public Optional<Long> addShare(final Long storyId, final Long sharedBy) {
        try (final PreparedStatement preparedStatement = connection.prepareStatement(
                storyShareQueryStructureBuilder.getInsertQuery(), Statement.RETURN_GENERATED_KEYS)) {

            connection.setAutoCommit(true);
            preparedStatement.setLong(1, storyId);
            preparedStatement.setLong(2, sharedBy);

            if (preparedStatement.executeUpdate() > 0) {
                return generatedKeyExtractor.fetchKey(preparedStatement);
            }

            return Optional.empty();
        } catch (final SQLException ignored) {
            LOGGER.error("Story share failed");
            throw new StoryShareFailedException("Story share failed");
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param id The ID of the story to be unshared.
     * @return True if the share is successfully removed, otherwise false.
     */
    @Override
    public boolean removeShare(final Long id) {
        try (final PreparedStatement preparedStatement = connection.prepareStatement(
                storyShareQueryStructureBuilder.getDeleteQuery())) {

            connection.setAutoCommit(true);
            preparedStatement.setLong(1, id);

            return preparedStatement.executeUpdate() > 0;
        } catch (final SQLException ignored) {
            LOGGER.error("Story share remove failed");
            throw new StoryShareRemovalFailedException("Story share remove failed");
        }
    }
}
