package org.insta.content.model;

/**
 * <p>
 * Common class representing an entity with an ID.
 * </p>
 *
 * @author Mohamed Yasar
 * @version 1.0 6 Feb 2024
 * @see Post
 * @see Reel
 * @see Story
 */
public sealed abstract class Common permits Comment, Post, Reel, Story {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }
}
