package org.insta.content.dao.reel;

import org.insta.content.dao.GeneratedKeyExtractor;
import org.insta.content.exception.reel.ReelCreationFailedException;
import org.insta.content.exception.reel.ReelException;
import org.insta.content.exception.reel.ReelRemovalFailedException;
import org.insta.content.exception.reel.ReelRetrivalFailedException;
import org.insta.content.model.Reel;
import org.insta.content.sqlinjector.reel.ReelSqlInjector;
import org.insta.databaseconnection.DatabaseConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

/**
 * <p>
 * Managing user reels.
 * </p>
 *
 * @author Mohamed Yasar
 * @version 1.0 6 Feb 2024
 * @see ReelServiceDAO
 * @see GeneratedKeyExtractor
 * @see DatabaseConnection
 */
public final class ReelServiceDAOImpl implements ReelServiceDAO {

    private static final Logger LOGGER = LogManager.getLogger(ReelServiceDAOImpl.class);
    private final ReelSqlInjector reelSqlInjector;
    private final Connection connection;
    private final GeneratedKeyExtractor generatedKeyExtractor;

    /**
     * <p>
     * Restrict object creation outside of the class
     * </p>
     */
    private ReelServiceDAOImpl() {
        connection = DatabaseConnection.get();
        generatedKeyExtractor = GeneratedKeyExtractor.getInstance();
        reelSqlInjector = ReelSqlInjector.getInstance();
    }

    /**
     * <p>
     * Returns the singleton instance of ReelServiceDAOImpl class.
     * </p>
     *
     * @return The singleton instance of ReelServiceDAOImpl class.
     */
    public static ReelServiceDAO getInstance() {
        return InstanceHolder.reelServiceDAOImpl;
    }

    /**
     * {@inheritDoc}
     *
     * @param reel The reel to be added.
     * @return The ID of the added reel if successful, otherwise 0.
     */
    @Override
    public Optional<Long> addReel(final Reel reel) {
        try (final PreparedStatement preparedStatement = connection.prepareStatement(
                reelSqlInjector.getInsertQuery(), Statement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(true);
            preparedStatement.setLong(1, reel.getUserId());
            preparedStatement.setString(2, reel.getCaption());
            preparedStatement.setString(3, reel.getDuration());

            if (preparedStatement.executeUpdate() > 0) {
                return generatedKeyExtractor.fetchKey(preparedStatement);
            }

            return Optional.empty();
        } catch (SQLException ignored) {
            LOGGER.error("Reel creation failed");
            throw new ReelCreationFailedException("Reel creation failed");
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param reelId The ID of the reel to be deleted.
     * @return True if the reel is deleted successfully, otherwise false.
     */
    public boolean removeReel(final Long reelId) {
        try (final PreparedStatement preparedStatement = connection.prepareStatement(
                reelSqlInjector.getDeleteQuery())) {

            connection.setAutoCommit(true);
            preparedStatement.setLong(1, reelId);

            return preparedStatement.executeUpdate() > 0;
        } catch (final SQLException ignored) {
            LOGGER.error("Reel removal failed");
            throw new ReelRemovalFailedException("Reel removal failed");
        }
    }

    /**
     * <p>
     * Retrieves a reel based on its ID.
     * </p>
     *
     * @param reelId The ID of the reel to be retrieved.
     * @return The retrieved reel, or null if not found.
     */
    public Optional<Reel> getReel(final Long reelId) {
        final Reel reel = new Reel();

        try (final PreparedStatement preparedStatement = connection.prepareStatement(
                reelSqlInjector.getSelectQuery())) {
            preparedStatement.setLong(1, reelId);
            final ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {

                return mapResultSetToReel(reel, resultSet);
            }

            return Optional.empty();
        } catch (Exception exception) {
            LOGGER.error("Reel retrival failed");
            throw new ReelRetrivalFailedException("Reel retrival failed");
        }
    }

    /**
     * <p>
     * Sets unique properties of a reel based on the retrieved ResultSet.
     * </p>
     *
     * @param reel      The reel object to be populated with unique properties.
     * @param resultSet The ResultSet containing reel data.
     * @return The populated reel object if successful, otherwise null.
     */
    private Optional<Reel> mapResultSetToReel(final Reel reel, final ResultSet resultSet) {
        try {
            reel.setReelId(resultSet.getLong(1));
            reel.setUserId(resultSet.getLong(2));
            reel.setUserName(resultSet.getString(3));
            reel.setCaption(resultSet.getString(4));
            reel.setPrivate(resultSet.getBoolean(5));
            reel.setTimestamp(resultSet.getTimestamp(6));
            reel.setDuration(resultSet.getString(7));

            return Optional.ofNullable(reel);
        } catch (Exception exception) {
            LOGGER.error("Resultset insertion in object failed exception");
            throw new ReelException("Resultset insertion in object failed exception");
        }
    }

    /**
     * <p>
     * Static class for creating singleton instance.
     * </p>
     */
    private static class InstanceHolder {

        private static final ReelServiceDAO reelServiceDAOImpl = new ReelServiceDAOImpl();
    }
}
