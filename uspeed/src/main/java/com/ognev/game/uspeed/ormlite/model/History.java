// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.ognev.game.uspeed.ormlite.model;


public class History
{

    private String date;
    private String downloadSpeed;
    private Long id;
    private String location;
    private String networkType;
    private String uploadSpeed;

    public History()
    {
    }

    public String getDate()
    {
        return date;
    }

    public String getDownloadSpeed()
    {
        return downloadSpeed;
    }

    public Long getId()
    {
        return id;
    }

    public String getLocation()
    {
        return location;
    }

    public String getNetworkType()
    {
        return networkType;
    }

    public String getUploadSpeed()
    {
        return uploadSpeed;
    }

    public void setDate(String s)
    {
        date = s;
    }

    public void setDownloadSpeed(String s)
    {
        downloadSpeed = s;
    }

    public void setId(Long long1)
    {
        id = long1;
    }

    public void setLocation(String s)
    {
        location = s;
    }

    public void setNetworkType(String s)
    {
        networkType = s;
    }

    public void setUploadSpeed(String s)
    {
        uploadSpeed = s;
    }
}
