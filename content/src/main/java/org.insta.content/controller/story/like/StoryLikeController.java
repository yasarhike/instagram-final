package org.insta.content.controller.story.like;

import org.insta.content.service.story.like.StoryLikeService;
import org.insta.content.service.story.like.StoryLikeServiceImpl;

import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * <p>
 * Managing story like operations.
 * </p>
 *
 * @author Mohamed Yasar
 * @version 1.0 6 Feb 2024
 * @see StoryLikeService
 */
@Path("/storylike")
public final class StoryLikeController {

    private final StoryLikeService storyLikeService;

    /**
     * <p>
     * Restrict the object creation outside of the class.
     * </p>
     */
    private StoryLikeController() {
        storyLikeService = StoryLikeServiceImpl.getInstance();
    }

    /**
     * <p>
     * Returns the singleton instance of StoryLikeController class.
     * </p>
     *
     * @return The singleton instance of StoryLikeController class.
     */
    public static StoryLikeController getInstance() {
        return InstanceHolder.STORY_LIKE_CONTROLLER;
    }

    /**
     * <p>
     * Endpoint for adding a like to a specific story.
     * </p>
     *
     * @param userId  ID of the user
     * @param storyId ID of the story
     * @return Response in JSON format in the form of byte array.
     */
    @Path("/add/{userId}/{storyId}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public byte[] storyLike(@PathParam("userId") final Long userId,
                            @PathParam("storyId") final Long storyId) {
        return storyLikeService.storyLike(userId, storyId);
    }

    /**
     * <p>
     * Endpoint for removing a like from a story.
     * </p>
     *
     * @param id the ID of the like to be removed.
     * @return Response in JSON format in the form of byte array.
     */
    @DELETE
    @Path("/remove/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public byte[] storyUnLike(@PathParam("id") final Long id) {
        return storyLikeService.storyUnlike(id);
    }

    /**
     * <p>
     * Static class for creating singleton instance.
     * </p>
     */
    private static class InstanceHolder {

        private static final StoryLikeController STORY_LIKE_CONTROLLER = new StoryLikeController();
    }
}
