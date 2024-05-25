package org.insta.content.controller.post.like;

import org.insta.content.service.post.like.PostLikeService;
import org.insta.content.service.post.like.PostLikeServiceImpl;

import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * <p>
 * Manages post likes.
 * </p>
 *
 * @author Mohamed Yasar
 * @version 1.0 6 Feb 2024
 * @see PostLikeService
 */
@Path("/postlike")
public final class PostLikeController {

    private final PostLikeService postLikeService;

    /**
     * <p>
     * Private constructor to restrict the object creation outside of the class.
     * </p>
     */
    private PostLikeController() {
        postLikeService = PostLikeServiceImpl.getInstance();
    }

    /**
     * <p>
     * Returns the singleton instance of PostLikeController class.
     * </p>
     *
     * @return The singleton instance of PostLikeController class.
     */
    public static PostLikeController getInstance() {
        return InstanceHolder.PostLikeController;
    }

    /**
     * <p>
     * Adds a like for the specified post by the specified user.
     * </p>
     *
     * @param userId The ID of the user who likes the post.
     * @param postId The ID of the post to be liked.
     * @return Response containing the result of the operation in the form of byte array.
     */
    @Path("/add/{postId}/{userId}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public byte[] postLike(@PathParam("userId") final Long userId,
                           @PathParam("postId") final Long postId) {
        return postLikeService.postLike(userId, postId);
    }

    /**
     * <p>
     * Removes a like for the specified post by the specified user.
     * </p>
     *
     * @param postId The ID of the post to be unliked.
     * @return Response containing the result of the operation in the form of byte array.
     */
    @Path("/remove/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public byte[] postUnlike(@PathParam("id") final Long postId) {
        return postLikeService.postUnlike(postId);
    }

    /**
     * <p>
     * Static class for creating singleton instance.
     * </p>
     */
    private static class InstanceHolder {

        private static final PostLikeController PostLikeController = new PostLikeController();
    }
}
