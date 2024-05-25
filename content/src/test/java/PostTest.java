import org.insta.content.controller.post.PostController;
import org.insta.content.model.Media;
import org.insta.content.model.Post;
import org.insta.wrapper.jsonvalidator.JsonResponseHandler;
import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;

import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * <p>
 * Unit tests for the PostController using properties-based mock data.
 * This class tests the functionalities of creating, deleting, and retrieving posts.
 * </p>
 *
 * @see PostController
 * @see Post
 * @see JsonResponseHandler
 */
public class PostTest {

    private static final Properties properties;
    private static final Set<String> identifiers;

    static {
        properties = readPropertiesFile("src/test/resources/user.properties");
        identifiers = getIdentifiers();
    }

    private PostController postController;
    private JsonResponseHandler jsonResponseHandler;

    /**
     * Reads properties from the specified file.
     *
     * @param filename the name of the properties file to read
     * @return the loaded Properties object
     */
    private static Properties readPropertiesFile(String filename) {
        final Properties props = new Properties();
        try (final FileReader reader = new FileReader(filename)) {
            props.load(reader);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return props;
    }

    /**
     * Retrieves unique identifiers from the properties file.
     *
     * @return a Set of unique identifiers
     */
    public static Set<String> getIdentifiers() {
        return properties.stringPropertyNames().stream()
                .map(key -> key.split("\\.")[0])
                .collect(Collectors.toSet());
    }

    /**
     * Sets up the test environment by initializing necessary components.
     */
    @Before
    public void setUp() {
        postController = PostController.getInstance();
        jsonResponseHandler = JsonResponseHandler.getInstance();
        postController.getPostService().setReplacer(PostDAOTest.getInstance());
    }

    /**
     * Tests the creation of a post by adding a post and verifying the returned ID.
     */
    @Test
    public void createPost() {
        final Post post = new Post();

        for (final String identifier : identifiers) {
            if ("post4".equals(identifier)) {
                post.setCaption(properties.getProperty(identifier + ".caption"));
                post.setPrivate(Boolean.parseBoolean(properties.getProperty(identifier + ".private")));
                post.setPostId(Long.parseLong(properties.getProperty(identifier + ".postId")));
                post.setType(Media.getMedia(Integer.parseInt(properties.getProperty(identifier + ".type"))));
                post.setUserName(properties.getProperty(identifier + ".userName"));
                post.setUserId(Long.parseLong(properties.getProperty(identifier + ".userId")));
            }
        }

        final Map<?, ?> result = jsonResponseHandler.getTableId(postController.addPost(post));
        assertThat(result.get("id"), equalTo(post.getPostId()));
    }

    /**
     * <p>
     * Tests the deletion of posts by deleting each post and verifying the success status.
     * </p>
     */
    @Test
    public void deletePost() {
        for (final String identifier : identifiers) {
            final byte[] result = postController.deletePost(Long.parseLong(properties.getProperty(identifier + ".postId")));
            assert (jsonResponseHandler.getStatus(result));
        }
    }

    /**
     * <p>
     * Tests the retrieval of posts by getting each post and verifying the returned ID.
     * </p>
     */
    @Test
    public void getPost() {
        for (final String identifier : identifiers) {
            final byte[] result = postController.getPost(Long.parseLong(properties.getProperty(identifier + ".postId")));

            assert (jsonResponseHandler.getObjectId(result).equals(Long.parseLong(properties.getProperty(identifier + ".postId"))));
        }
    }
}
