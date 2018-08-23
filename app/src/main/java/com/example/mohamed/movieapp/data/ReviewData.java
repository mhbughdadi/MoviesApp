package com.example.mohamed.movieapp.data;

import java.io.Serializable;

/**
 * Created by Mohamed on 11/24/2016.
 */

public class ReviewData implements Serializable {
    private String content;
    private String auther;
    private String link;

    public String getContent() {
        return content;
    }

    public void setContent(String name) {
        this.content = name;
    }

    public void setAuther(String auther) {
        this.auther = auther;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAuther() {
        return auther;
    }

    public String getLink() {
        return link;
    }

    public ReviewData(String content, String auther, String link) {
        this.content = content;
        this.auther = auther;
        this.link = link;
    }
}
