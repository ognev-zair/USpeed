// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.ognev.game.uspeed.ormlite.model;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class History {

    @DatabaseField
    private String date;
    @DatabaseField
    private String downloadSpeed;
    @DatabaseField(generatedId = true)
    private Long id;
    @DatabaseField
    private String location;
    @DatabaseField
    private String networkType;
    @DatabaseField
    private String uploadSpeed;
    @DatabaseField
    private String downloadPostFix;
    @DatabaseField
    private String uploadPostFix;

    public History() {
    }

    public String getDate() {
        return date;
    }

    public String getDownloadSpeed() {
        return downloadSpeed;
    }

    public Long getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public String getNetworkType() {
        return networkType;
    }

    public String getUploadSpeed() {
        return uploadSpeed;
    }

    public void setDate(String s) {
        date = s;
    }

    public void setDownloadSpeed(String s) {
        downloadSpeed = s;
    }

    public void setId(Long long1) {
        id = long1;
    }

    public void setLocation(String s) {
        location = s;
    }

    public void setNetworkType(String s) {
        networkType = s;
    }

    public void setUploadSpeed(String s) {
        uploadSpeed = s;
    }

    public void setDownloadPostFix(String downloadPostFix) {
        this.downloadPostFix = downloadPostFix;
    }

    public String getDownloadPostFix() {
        return downloadPostFix;
    }

    public void setUploadPostFix(String uploadPostFix) {
        this.uploadPostFix = uploadPostFix;
    }

    public String getUploadPostFix() {
        return uploadPostFix;
    }
}
