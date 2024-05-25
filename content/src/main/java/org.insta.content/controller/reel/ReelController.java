package org.insta.content.controller.reel;

import org.insta.content.model.Reel;
import org.insta.content.service.reel.ReelService;
import org.insta.content.service.reel.ReelServiceImpl;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * <p>
 * Implementation of the ReelControllerRest class for managing reels.
 * </p>
 *
 * @author Mohamed Yasar
 * @version 1.0 6 Feb 2024
 * @see ReelService
 */
@Path("/reel")
public final class ReelController {

    private final ReelService reelService;

    /**
     * <p>
     * Restrict the object creation outside of the class.
     * </p>
     */
    private ReelController() {
        reelService = ReelServiceImpl.getInstance();
    }

    /**
     * <p>
     * Returns the singleton instance of ReelController.
     * </p>
     *
     * @return The singleton instance of ReelController.
     */
    public static ReelController getInstance() {
        return InstanceHolder.REEL_CONTROLLER;
    }

    /**
     * <p>
     * Endpoint for removing a reel.
     * </p>
     *
     * @param reelId ID of the reel to remove.
     * @return Response in JSON format in the form of byte array.
     */
    @DELETE
    @Path("/remove/{reelid}")
    @Produces(MediaType.APPLICATION_JSON)
    public byte[] removeReel(@PathParam("reelid") final Long reelId) {
        return reelService.removeReel(reelId);
    }

    /**
     * <p>
     * Endpoint for adding a reel for the specified user.
     * </p>
     *
     * @param reel Reel object to add.
     * @return Response in JSON format in the form of byte array.
     */
    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public byte[] addReel(final Reel reel) {
        return reelService.addReel(reel);
    }

    /**
     * <p>
     * Endpoint for retrieving a reel.
     * </p>
     *
     * @param reelId ID of the reel to retrieve.
     * @return Response in JSON format in the form of byte array.
     */
    @GET
    @Path("/get/{reelId}")
    @Produces(MediaType.APPLICATION_JSON)
    public byte[] getReel(@PathParam("reelId") final Long reelId) {
        return reelService.getReel(reelId);
    }

    /**
     * <p>
     * Static class for creating singleton instance.
     * </p>
     */
    private static class InstanceHolder {

        private static final ReelController REEL_CONTROLLER = new ReelController();
    }
}
