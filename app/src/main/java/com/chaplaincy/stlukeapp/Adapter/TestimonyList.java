package com.chaplaincy.stlukeapp.Adapter;

public class TestimonyList {
    private String username;
    private String created_at;
    private String description;
    private int profile_pc;
    private String like;
    private String comment;

    public TestimonyList(String username, String created_at, String description, int profile_pc, String like, String comment) {
        this.username = username;
        this.created_at = created_at;
        this.description = description;
        this.profile_pc = profile_pc;
        this.like = like;
        this.comment = comment;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getProfile_pc() {
        return profile_pc;
    }

    public void setProfile_pc(int profile_pc) {
        this.profile_pc = profile_pc;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
