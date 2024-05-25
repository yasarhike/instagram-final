package org.insta.content.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.insta.content.groups.StoryValidator;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * <p>
 * Represents a story.
 * </p>
 *
 * <p>
 * This class defines properties for a story, including the story ID, user ID,
 * content ID, and the story content itself.
 * </p>
 *
 * @author Mohamed Yasar
 * @version 1.0 6 Feb 2024
 * @see Common
 */
public final class Story extends Common {

    @NotNull(message = "Is private mst not be null", groups = StoryValidator.class)
    private boolean isPrivate;
    @Positive(message = "Story id must be in positive", groups = StoryValidator.class)
    private Long storyId;
    @NotNull(message = "Media field must not be null", groups = StoryValidator.class)
    private Media media;
    @NotNull(message = "Music field must not be null", groups = StoryValidator.class)
    private String music;
    @NotNull(message = "Text field must not be null", groups = StoryValidator.class)
    private String text;
    @Positive(message = "User id must be in positive", groups = StoryValidator.class)
    private Long userId;
    @NotNull(message = "User name must not be null", groups = StoryValidator.class)
    private String userName;
    private Timestamp timestamp;

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(final boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public Media getMedia() {
        return media;
    }

    public String getMusic() {
        return music;
    }

    public void setMusic(final String music) {
        this.music = music;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public Long getStoryId() {
        return storyId;
    }

    public void setStoryId(final Long storyId) {
        this.storyId = storyId;
    }

    public void setTimestamp(final Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Story) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, storyId);
    }
}
