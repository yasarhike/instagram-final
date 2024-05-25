package org.insta.content.service.post;

import org.insta.content.dao.post.PostServiceDAO;
import org.insta.content.dao.post.PostServiceDAOImpl;
import org.insta.content.groups.PostValidator;
import org.insta.content.model.Post;
import org.insta.wrapper.jsonvalidator.JsonResponseHandler;

import java.util.Optional;

/**
 * <p>
 * Implementation class for managing posts.
 * </p>
 *
 * @author Mohamed Yasar
 * @version 1.0 6 Feb 2024
 */
public final class PostServiceImpl implements PostService {

    private final JsonResponseHandler jsonResponseHandler;
    private PostServiceDAO postServiceDAO;


    /**
     * <p>
     * Private constructor to restrict the object creation outside of the class.
     * </p>
     */
    private PostServiceImpl() {
        postServiceDAO = PostServiceDAOImpl.getInstance();
        jsonResponseHandler = JsonResponseHandler.getInstance();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Returns the singleton instance of PostServiceImplementation class.
     * </p>
     *
     * @return The singleton instance of PostServiceImplementation class.
     */
    public static PostService getInstance() {
        return InstanceHolder.postServiceImplementation;
    }

    /**
     * {@inheritDoc}
     *
     * @param post the post to be added
     * @return a byte array representing the result of the operation
     */
    public byte[] addPost(final Post post) {
        final byte[] violations = jsonResponseHandler.validate(post, PostValidator.class);

        return violations.length > 0 ? violations
                : jsonResponseHandler.responseWithID(postServiceDAO.addPost(post), violations);
    }

    /**
     * {@inheritDoc}
     *
     * @param postId the ID of the post to be removed
     * @return a byte array representing the result of the operation
     */
    public byte[] removePost(final Long postId) {
        return jsonResponseHandler.responseWithStatus(postServiceDAO.removePost(postId));
    }

    /**
     * {@inheritDoc}
     *
     * @param id the ID of the post to be retrieved
     * @return a byte array representing the retrieved post
     */
    public byte[] getPost(final Long id) {
        final Optional<Post> post = postServiceDAO.getPost(id);

        return post.isPresent() ? jsonResponseHandler.objectResponse(post.get())
                : jsonResponseHandler.responseWithStatus(false);
    }

    public void setReplacer(final PostServiceDAO postServiceDAO) {
        this.postServiceDAO = postServiceDAO;
    }

    /**
     * <p>
     * Static class for creating singleton instance.
     * </p>
     */
    private static class InstanceHolder {

        private static final PostService postServiceImplementation = new PostServiceImpl();
    }
}