package org.insta.content.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.insta.content.groups.PostValidator;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * <p>
 * Contain post details of the user.
 * </p>
 *
 * @author Mohamed Yasar
 * @version 1.0 6 Feb 2024
 * @see Common
 */
public final class Post extends Common {

    @Positive(message = "User id must be positive", groups = PostValidator.class)
    private Long userId;
    private Media type;
    private String userName;
    @NotNull(message = "Is private must not be null", groups = PostValidator.class)
    private boolean isPrivate;
    @NotNull(message = "Caption must not be null", groups = PostValidator.class)
    private String caption;
    @Positive(message = "Post id must be in positive", groups = PostValidator.class)
    private Long postId;
    private Timestamp timestamp;

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(final boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(final String caption) {
        this.caption = caption;
    }

    public Media getType() {
        return type;
    }

    public void setType(final Media type) {
        this.type = type;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(final Long postId) {
        this.postId = postId;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public void setTimestamp(final Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Post) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, postId);
    }
}
