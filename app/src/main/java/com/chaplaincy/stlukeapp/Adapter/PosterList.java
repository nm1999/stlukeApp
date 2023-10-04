package com.chaplaincy.stlukeapp.Adapter;

public class PosterList {
    private int poster_image;
    private String poster_description;

    public PosterList(int poster_image, String poster_description) {
        this.poster_image = poster_image;
        this.poster_description = poster_description;
    }

    public int getPoster_image() {
        return poster_image;
    }

    public void setPoster_image(int poster_image) {
        this.poster_image = poster_image;
    }

    public String getPoster_description() {
        return poster_description;
    }

    public void setPoster_description(String poster_description) {
        this.poster_description = poster_description;
    }
}
