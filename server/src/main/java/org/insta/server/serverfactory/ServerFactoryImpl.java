package org.insta.server.serverfactory;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.insta.authentication.controller.UserAccountController;
import org.insta.content.controller.post.PostController;
import org.insta.content.controller.post.comment.PostCommentController;
import org.insta.content.controller.post.like.PostLikeController;
import org.insta.content.controller.post.share.PostShareController;
import org.insta.content.controller.reel.ReelController;
import org.insta.content.controller.reel.comment.ReelCommentController;
import org.insta.content.controller.reel.like.ReelLikeController;
import org.insta.content.controller.reel.share.ReelShareController;
import org.insta.content.controller.story.StoryController;
import org.insta.content.controller.story.like.StoryLikeController;
import org.insta.content.controller.story.share.StoryShareController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Implementation of the {@link ServerFactory} interface for creating JAX-RS server instances.
 * This implementation adds service classes to the server instance and configures it with a Jackson JSON provider.
 * </p>
 *
 * <p>
 * This class follows the Singleton pattern to ensure only one instance exists throughout the application.
 * </p>
 *
 * @author Mohamed Yasar
 * @version 1.0 6 Feb 2024
 * @see ServerFactory
 * @see JAXRSServerFactoryBean
 * @see JacksonJsonProvider
 */
public final class ServerFactoryImpl implements ServerFactory {

    private final JacksonJsonProvider jacksonJsonProvider;

    /**
     * <p>
     * Private constructor to restrict the creation of instances outside of the class.
     * Initializes the Jackson JSON provider.
     * </p>
     */
    private ServerFactoryImpl() {
        jacksonJsonProvider = new JacksonJsonProvider();
    }

    /**
     * <p>
     * Static class for creating singleton instance.
     * </p>
     */
    private static class InstanceHolder {

        private static final ServerFactory serverFactoryImpl = new ServerFactoryImpl();
    }

    /**
     * <p>
     * Returns the singleton instance of ServerFactoryImpl.
     * </p>
     *
     * @return The singleton instance of ServerFactoryImpl.
     */
    public static ServerFactory getInstance() {
        return InstanceHolder.serverFactoryImpl;
    }

    /**
     * <p>
     * Creates a JAX-RS server instance with the provided address and service classes.
     * </p>
     *
     * @param address      The address where the server will be hosted.
     * @param serviceClass The list of service classes to be added to the server.
     * @return A configured {@link JAXRSServerFactoryBean} instance.
     */
    private JAXRSServerFactoryBean createServer(final String address, final List<Object> serviceClass) {
        final JAXRSServerFactoryBean factory = new JAXRSServerFactoryBean();

        factory.setProvider(jacksonJsonProvider);
        factory.setAddress(address);
        factory.setServiceBeans(serviceClass);

        return factory;
    }

    /**
     * <p>
     * Adds service classes to the JAX-RS server instance and creates the server.
     * </p>
     *
     * @return A configured {@link JAXRSServerFactoryBean} instance with the added service classes.
     */
    public JAXRSServerFactoryBean addServiceClass() {
        final List<Object> serviceClasses = new ArrayList<>();

        serviceClasses.add(ReelController.getInstance());
        serviceClasses.add(ReelLikeController.getInstance());
        serviceClasses.add(ReelCommentController.getInstance());
        serviceClasses.add(ReelShareController.getInstance());
        serviceClasses.add(PostController.getInstance());
        serviceClasses.add(PostLikeController.getInstance());
        serviceClasses.add(PostCommentController.getInstance());
        serviceClasses.add(PostShareController.getInstance());
        serviceClasses.add(StoryController.getInstance());
        serviceClasses.add(StoryLikeController.getInstance());
        serviceClasses.add(StoryShareController.getInstance());
        serviceClasses.add(UserAccountController.getInstance());

        return createServer("/instagram", serviceClasses);
    }
}
