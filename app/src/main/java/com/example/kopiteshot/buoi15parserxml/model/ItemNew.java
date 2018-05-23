package com.example.kopiteshot.buoi15parserxml.model;

/**
 * Created by Kopiteshot on 5/14/2017.
 */

public class ItemNew {
    private String title;
    private String description;
    private String pubDate;
    private String link;
    private String image;
    private String guid;
    private String htmlcode;

    public String getHtmlcode() {
        return htmlcode;
    }

    public void setHtmlcode(String htmlcode) {
        this.htmlcode = htmlcode;
    }

    public ItemNew(String title, String description, String pubDate, String link, String image, String guid, String htmlcode) {
        this.title = title;
        this.description = description;
        this.pubDate = pubDate;
        this.link = link;
        this.image = image;
        this.guid = guid;
        this.htmlcode = htmlcode;

    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
