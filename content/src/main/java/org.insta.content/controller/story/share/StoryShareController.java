package org.insta.content.controller.story.share;

import org.insta.content.service.story.share.StoryShareService;
import org.insta.content.service.story.share.StoryShareServiceImpl;

import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * <p>
 * RESTful controller class for managing story sharing operations within the Instagram application.
 * </p>
 *
 * <p>
 * This class provides endpoints for sharing and unsharing stories.
 * </p>
 *
 * @author Mohamed Yasar
 * @version 1.0 6 Feb 2024
 * @see StoryShareService
 */
@Path("/storyshare")
public final class StoryShareController {

    private final StoryShareService storyShareService;

    /**
     * <p>
     * Restrict object creation outside of the class.
     * </p>
     */
    private StoryShareController() {
        storyShareService = StoryShareServiceImpl.getInstance();
    }

    /**
     * <p>
     * Returns the singleton instance of the StoryShareController class.
     * </p>
     *
     * @return The singleton instance of StoryShareController class.
     */
    public static StoryShareController getInstance() {
        return InstanceHolder.STORY_SHARE_CONTROLLER;
    }

    /**
     * <p>
     * Shares a story for the specified user id.
     * </p>
     *
     * @param storyId ID of the story to share.
     * @param userId  ID of the user to share with.
     * @return Response in JSON format in the form of byte array.
     */
    @Path("/add/{userId}/{storyId}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public byte[] storyShare(@PathParam("storyId") final Long storyId,
                             @PathParam("userId") final Long userId) {
        return storyShareService.storyShare(storyId, userId);
    }

    /**
     * <p>
     * Unshares a story.
     * </p>
     *
     * @param storyId the ID of the story to unshare.
     * @return Response in JSON format in the form of byte array.
     */
    @DELETE
    @Path("/remove/{storyId}")
    @Produces(MediaType.APPLICATION_JSON)
    public byte[] storyUnShare(@PathParam("storyId") final Long storyId) {
        return storyShareService.storyUnShare(storyId);
    }

    /**
     * <p>
     * Static class for creating singleton instance.
     * </p>
     */
    private static class InstanceHolder {

        private static final StoryShareController STORY_SHARE_CONTROLLER = new StoryShareController();
    }
}
