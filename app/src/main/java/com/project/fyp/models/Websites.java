package com.project.fyp.models;

public class Websites {
    String url, imageLink;

    public Websites(String url, String imageLink) {
        this.url = url;
        this.imageLink = imageLink;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}
