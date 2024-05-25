package org.insta.content.controller.reel.share;

import org.insta.content.service.reel.share.ReelShareService;
import org.insta.content.service.reel.share.ReelShareServiceImpl;

import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * <p>
 * RESTful controller class for managing sharing operations for reels within the Instagram application.
 * </p>
 *
 * <p>
 * This class provides endpoints for sharing and removing shares on reels.
 * </p>
 *
 * @author Mohamed Yasar
 * @version 1.0 6 Feb 2024
 * @see ReelShareServiceImpl
 */
@Path("/reelshare")
public final class ReelShareController {

    private final ReelShareService reelShareService;

    /**
     * <p>
     * Restrict object creation outside of the class.
     * </p>
     */
    private ReelShareController() {
        reelShareService = ReelShareServiceImpl.getInstance();
    }

    /**
     * <p>
     * Returns the singleton instance of the ReelShareController class.
     * </p>
     *
     * @return The singleton instance of the ReelShareController class.
     */
    public static ReelShareController getInstance() {
        return InstanceHolder.REEL_SHARE_CONTROLLER;
    }

    /**
     * <p>
     * Endpoint for sharing a reel with a user.
     * </p>
     *
     * @param reelId ID of the reel to share.
     * @param userId ID of the user to share with.
     * @return Response in JSON format in the form of byte array.
     */
    @Path("/add/{reelId}/{userId}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public byte[] reelShare(@PathParam("reelId") final Long userId,
                            @PathParam("userId") final Long reelId) {
        return reelShareService.reelShare(userId, reelId);
    }

    /**
     * <p>
     * Endpoint for removing a shared reel.
     * </p>
     *
     * @param id ID of the shared reel to remove.
     * @return Response in JSON format in the form of byte array.
     */
    @Path("/remove/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public byte[] removeShare(@PathParam("id") final Long id) {
        return reelShareService.removeShare(id);
    }

    /**
     * <p>
     * Static class for creating singleton instance.
     * </p>
     */
    private static class InstanceHolder {

        private static final ReelShareController REEL_SHARE_CONTROLLER = new ReelShareController();
    }
}
