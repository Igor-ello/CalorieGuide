package com.obsessed.calorieguide.data.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "intake_table")
public abstract class Intake {
    @SerializedName("type")
    protected String type;
    @PrimaryKey(autoGenerate = true)
    protected int id;
    protected String description;
    protected int calories;
    protected int author_id;
    protected int likes;
    protected byte[] picture;
    protected boolean isLiked;


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public int getCalories() {
        return calories;
    }
    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getAuthor_id() {
        return author_id;
    }
    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public int getLikes() {
        return likes;
    }
    public void setLikes(int likes) {
        this.likes = likes;
    }

    public byte[] getPicture() {
        return picture;
    }
    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public boolean getIsLiked() {
        return isLiked;
    }
    public void setIsLiked(boolean liked) {
        isLiked = liked;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
}
