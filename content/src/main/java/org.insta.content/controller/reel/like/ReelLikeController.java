package org.insta.content.controller.reel.like;

import org.insta.content.service.reel.like.ReelLikeService;
import org.insta.content.service.reel.like.ReelLikeServiceImpl;

import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * <p>
 * RESTful controller class for managing likes on reels within the Instagram application.
 * </p>
 *
 * <p>
 * This class provides endpoints for adding and removing likes on reels.
 * </p>
 *
 * @author Mohamed Yasar
 * @version 1.0 6 Feb 2024
 * @see ReelLikeServiceImpl
 */
@Path("/reellike")
public final class ReelLikeController {

    private final ReelLikeService reelLikeService;

    /**
     * <p>
     * Restrict object creation outside of the class.
     * </p>
     */
    private ReelLikeController() {
        reelLikeService = ReelLikeServiceImpl.getInstance();
    }

    /**
     * <p>
     * Returns the singleton instance of the ReelLikeController class.
     * </p>
     *
     * @return The singleton instance of the ReelLikeController class.
     */
    public static ReelLikeController getInstance() {
        return InstanceHolder.REEL_LIKE_CONTROLLER;
    }

    /**
     * <p>
     * Endpoint for adding a like to a reel.
     * </p>
     *
     * @param reelId the ID of the reel
     * @param userId the ID of the user
     * @return the response in JSON format
     */
    @Path("/add/{reelId}/{userId}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public byte[] reelLike(@PathParam("reelId") final Long reelId, @PathParam("userId") final Long userId) {
        return reelLikeService.reelLike(reelId, userId);
    }

    /**
     * <p>
     * Endpoint for removing a like from a reel.
     * </p>
     *
     * @param id the ID of the like to be removed.
     * @return Return JSON response in the form of byte array.
     */
    @Path("/remove/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public byte[] reelUnlike(@PathParam("id") final Long id) {
        return reelLikeService.reelUnlike(id);
    }

    /**
     * <p>
     * Static class for creating singleton instance.
     * </p>
     */
    private static class InstanceHolder {

        private static final ReelLikeController REEL_LIKE_CONTROLLER = new ReelLikeController();
    }
}
