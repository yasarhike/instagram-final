package org.insta.content.service.reel.like;

import org.insta.content.dao.reel.like.ReelLikeDAO;
import org.insta.content.dao.reel.like.ReelLikeDAOImpl;
import org.insta.wrapper.jsonvalidator.JsonResponseHandler;

/**
 * <p>
 * Service implementation for managing reel liking operations.
 * </p>
 *
 * <p>
 * This class provides methods for adding and removing likes on reels, allowing users to like and unlike reels.
 * </p>
 *
 * @author Mohamed Yasar
 * @version 1.0 6 Feb 2024
 * @see ReelLikeServiceImpl
 * @see ReelLikeDAOImpl
 * @see JsonResponseHandler
 */
public class ReelLikeServiceImpl implements ReelLikeService{

    private final ReelLikeDAO reelLikeDAOImpl;
    private final JsonResponseHandler jsonResponseHandler;

    /**
     * <p>
     * Restrict object creation outside of the class
     * </p>
     */
    private ReelLikeServiceImpl() {
        reelLikeDAOImpl = ReelLikeDAOImpl.getInstance();
        jsonResponseHandler = JsonResponseHandler.getInstance();
    }

    /**
     * <p>
     *  Static class for creating singleton instance.
     * </p>
     */
    private static class InstanceHolder {

        private static final ReelLikeService reelLikeService = new ReelLikeServiceImpl();
    }

    /**
     * <p>
     * Returns the singleton instance of ReelLikeServiceImpl class.
     * </p>
     *
     * @return The singleton instance of ReelLikeServiceImpl class.
     */
    public static ReelLikeService getInstance() {
        return InstanceHolder.reelLikeService;
    }

    /**
     * {@inheritDoc}
     *
     * @param reelId Refers to the id of the reel.
     * @param userId Refers to the id of the user.
     * @return A byte array representing the result of the operation.
     */
    public byte[] reelLike(final Long reelId, final Long  userId) {
        return jsonResponseHandler.responseWithID(reelLikeDAOImpl.reelLike(reelId, userId), new byte[]{});
    }

    /**
     *{@inheritDoc}
     *
     * @param id Refers to the ID of the user.
     * @return A byte array representing the result of the operation.
     */

    public byte[] reelUnlike(final Long  id) {
        return jsonResponseHandler.responseWithStatus(reelLikeDAOImpl.reelUnlike(id));
    }
}
