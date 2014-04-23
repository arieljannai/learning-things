package com.arja.explosm.ch;

/**
 * Created by Ariel on 20/04/2014.
 */
public class Comic {

    private int comicId;
    private String filename;
    private String imageUrl;
    private String comicName;
    private Author author;

    public int getComicId() {
        return comicId;
    }

    public String getFilename() {
        return filename;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getComicName() {
        return comicName;
    }

    public Comic(int comics_id, String image_url, Author author) {
        this.comicId = comics_id;
        this.imageUrl = image_url;
        this.author = author;
        this.filename = image_url.substring(image_url.lastIndexOf('/'), image_url.length());
        this.comicName = this.filename.substring(0, this.filename.indexOf('.'));
    }
}
