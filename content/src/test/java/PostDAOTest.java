import org.insta.content.dao.post.PostServiceDAO;
import org.insta.content.model.Media;
import org.insta.content.model.Post;

import java.io.FileReader;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

public class PostDAOTest implements PostServiceDAO {

    private final static Properties properties;
    private final static Set<String> identifiers;
    private static PostDAOTest postDAOTest;

    static {
        properties = readPropertiesFile("src/test/resources/db.properties");
        identifiers = getIdentifiers();
    }

    private PostDAOTest() {
    }

    /**
     * <p>
     * Reads properties from the specified file.
     * </p>
     *
     * @param filename the name of the properties file to read
     * @return the loaded Properties object
     */
    private static Properties readPropertiesFile(final String filename) {
        final Properties props = new Properties();

        try (final FileReader reader = new FileReader(filename)) {
            props.load(reader);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return props;
    }

    /**
     * <p>
     * Retrieves unique identifiers from the properties file.
     * </p>
     *
     * @return a Set of unique identifiers
     */
    public static Set<String> getIdentifiers() {
        return properties.stringPropertyNames().stream()
                .map(key -> key.split("\\.")[0])
                .collect(Collectors.toSet());
    }

    /**
     * <p>
     * Gets the singleton instance of PostDAOTest.
     * </p>
     *
     * @return the singleton instance of PostDAOTest
     */
    public static PostDAOTest getInstance() {
        return Objects.isNull(postDAOTest) ? postDAOTest = new PostDAOTest() : postDAOTest;
    }

    /**
     * <p>
     * Posts a video or image for the user account.
     * </p>
     *
     * @param post the post to be added
     * @return the ID of the added post, or 0 if unsuccessful
     */
    @Override
    public Optional<Long> addPost(final Post post) {
        return Optional.ofNullable(post.getPostId());
    }

    /**
     * <p>
     * Deletes a post for the user account.
     * </p>
     *
     * @param postId the ID of the post to be removed
     * @return true if the post is removed successfully, otherwise false
     */
    @Override
    public boolean removePost(final Long postId) {
        for (final String identifier : identifiers) {
            if (postId.equals(Long.parseLong(properties.getProperty(identifier + ".postId")))) {

                return true;
            }
        }

        return false;
    }

    /**
     * <p>
     * Retrieves a post with the specified ID.
     * </p>
     *
     * @param id the ID of the post to be retrieved
     * @return the retrieved post, or null if not found
     */
    @Override
    public Optional<Post> getPost(final Long id) {
        for (final String posts : identifiers) {
            if (id.equals(Long.parseLong(properties.getProperty(posts + ".postId")))) {
                return setPost(posts);
            }
        }
        return Optional.empty();
    }

    /**
     * <p>
     * Sets the post details from the properties file based on the identifier.
     * </p>
     *
     * @param identifier the identifier for the post
     * @return an Optional containing the populated Post object
     */
    public Optional<Post> setPost(final String identifier) {
        final Post post = new Post();
        post.setCaption(properties.getProperty(identifier + ".caption"));
        post.setPrivate(Boolean.parseBoolean(properties.getProperty(identifier + ".private")));
        post.setPostId(Long.parseLong(properties.getProperty(identifier + ".postId")));
        post.setType(Media.getMedia(Integer.parseInt(properties.getProperty(identifier + ".type"))));
        post.setUserName(properties.getProperty(identifier + ".userName"));
        post.setUserId(Long.parseLong(properties.getProperty(identifier + ".userId")));

        return Optional.ofNullable(post);
    }
}
