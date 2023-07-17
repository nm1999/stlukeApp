package com.chaplaincy.stlukeapp.DashboardActivities;

public class Music {
    private String song;
    private String songtitle;

    public Music(String song, String songtitle) {
        this.song = song;
        this.songtitle = songtitle;
    }


    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getSongtitle() {
        return songtitle;
    }

    public void setSongtitle(String songtitle) {
        this.songtitle = songtitle;
    }

    @Override
    public String toString() {
        return "Music{" +
                "song='" + song + '\'' +
                ", songtitle='" + songtitle + '\'' +
                '}';
    }

}

