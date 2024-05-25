package org.insta.content.controller.story;

import org.insta.content.model.Story;
import org.insta.content.service.story.StoryService;
import org.insta.content.service.story.StoryServiceImpl;

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
 * RESTful controller class for managing story operations within the Instagram application.
 * </p>
 *
 * <p>
 * This class provides endpoints for adding, removing, and retrieving stories.
 * </p>
 *
 * @author [Author Name]
 * @version 1.0 [Date]
 * @see StoryService
 */
@Path("/story")
public final class StoryController {

    private final StoryService storyService;

    /**
     * <p>
     * Restrict object creation outside of the class.
     * </p>
     */
    private StoryController() {
        storyService = StoryServiceImpl.getInstance();
    }

    /**
     * <p>
     * Returns the singleton instance of the StoryController class.
     * </p>
     *
     * @return Singleton instance of StoryController class.
     */
    public static StoryController getInstance() {
        return InstanceHolder.storyControllerRest;
    }

    /**
     * <p>
     * Endpoint for adding a story.
     * </p>
     *
     * @param story the Story object to add.
     * @return Response in JSON format in the form of byte array.
     */
    @Path("/add")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public byte[] addStory(final Story story) {
        return storyService.addStory(story);
    }

    /**
     * <p>
     * Endpoint for removing a story.
     * </p>
     *
     * @param id ID of the story to remove.
     * @return Response in JSON format in the form of byte array.
     */
    @DELETE
    @Path("/remove/{storyId}")
    @Produces(MediaType.APPLICATION_JSON)
    public byte[] removeStory(@PathParam("storyId") final Long id) {
        return storyService.removeStory(id);
    }

    /**
     * <p>
     * Endpoint for retrieving a story.
     * </p>
     *
     * @param id ID of the story to retrieve
     * @return Response in JSON format in the form of byte array.
     */
    @GET
    @Path("/get/{storyId}")
    @Produces(MediaType.APPLICATION_JSON)
    public byte[] getStory(@PathParam("storyId") final Long id) {
        return storyService.getStory(id);
    }

    /**
     * <p>
     * Static class for creating singleton instance.
     * </p>
     */
    private static class InstanceHolder {

        private static final StoryController storyControllerRest = new StoryController();
    }
}
