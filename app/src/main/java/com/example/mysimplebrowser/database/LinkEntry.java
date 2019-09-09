package com.example.mysimplebrowser.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "link")
public class LinkEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String linkName;
    private String linkUrl;

    @Ignore
    public LinkEntry(String linkName, String linkUrl) {
        this.id = id;
        this.linkName = linkName;
        this.linkUrl = linkUrl;
    }

    public LinkEntry(int id, String linkName, String linkUrl) {
        this.id = id;
        this.linkName = linkName;
        this.linkUrl = linkUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }
}
