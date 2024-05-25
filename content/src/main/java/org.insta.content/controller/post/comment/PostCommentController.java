package org.insta.content.controller.post.comment;

import org.insta.content.dao.post.comment.PostCommentDAOImpl;
import org.insta.content.groups.CommentValidator;
import org.insta.content.model.Comment;
import org.insta.content.service.post.comment.PostCommentService;
import org.insta.content.service.post.comment.PostCommentServiceImpl;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * <p>
 * Manages Post comments.
 * </p>
 *
 * @author Mohamed Yasar
 * @version 1.0 6 Feb 2024
 * @see Comment
 * @see PostCommentDAOImpl
 * @see CommentValidator
 */
@Path("/postcomment")
public final class PostCommentController {

    private final PostCommentService postCommentService;

    /**
     * <p>
     * Private constructor to restrict the object creation outside of the class.
     * </p>
     */
    private PostCommentController() {
        postCommentService = PostCommentServiceImpl.getInstance();
    }

    /**
     * <p>
     * Returns the singleton instance of PostCommentController class.
     * </p>
     *
     * @return Singleton instance of PostCommentController class.
     */
    public static PostCommentController getInstance() {
        return InstanceHolder.POST_COMMENT_CONTROLLER;
    }

    /**
     * <p>
     * Adds a comment for the specified post.
     * </p>
     *
     * @param comment {@link Comment}The comment to be added.
     * @return Response containing the result of the operation in the form of byte array.
     */
    @Path("/add")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public byte[] postComment(final Comment comment) {
        return postCommentService.postComment(comment);
    }

    /**
     * <p>
     * Removes a comment with the specified ID.
     * </p>
     *
     * @param id The ID of the comment to be removed.
     * @return Response containing the result of the operation in the form of byte array.
     */
    @Path("/remove/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public byte[] removeComment(@PathParam("id") final Long id) {
        return postCommentService.deleteComment(id);
    }

    /**
     * <p>
     * Static class for creating singleton instance.
     * </p>
     */
    private static class InstanceHolder {

        private static final PostCommentController POST_COMMENT_CONTROLLER = new PostCommentController();
    }
}
