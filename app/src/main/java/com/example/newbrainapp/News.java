package com.example.newbrainapp;

import java.io.Serializable;

public class News implements Serializable {
    private String newsName;
    private String newsImageId;
    private String newsContent = "";

    public News(String newsName, String newsImageId){
        setNewsImageId(newsImageId);
        setNewsName(newsName);
    }

    public String getNewsName() {
        return newsName;
    }

    public void setNewsImageId(String newsImageId) {
        this.newsImageId = newsImageId;
    }

    public String getNewsImageId() {

        return newsImageId;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public void setNewsName(String newsName) {
        this.newsName = newsName;
    }


}
