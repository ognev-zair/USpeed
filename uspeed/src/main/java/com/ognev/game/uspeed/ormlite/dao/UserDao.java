// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.ognev.game.uspeed.ormlite.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

public class UserDao extends BaseDaoImpl
{

    public UserDao(ConnectionSource connectionsource)
        throws SQLException
    {
        super(connectionsource, com/ognev/game/uspeed/ormlite/model/User);
    }

    public void changeSystemLanguage(boolean flag)
        throws SQLException
    {
        UpdateBuilder updatebuilder = updateBuilder();
        updatebuilder.where().eq("id", "me");
        updatebuilder.updateColumnValue("systemLanguage", Boolean.valueOf(flag));
        updatebuilder.update();
    }
}
