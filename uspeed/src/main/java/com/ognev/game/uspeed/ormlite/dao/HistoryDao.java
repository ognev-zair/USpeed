// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.ognev.game.uspeed.ormlite.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.ognev.game.uspeed.ormlite.HelperFactory;
import com.ognev.game.uspeed.ormlite.model.History;

import java.sql.SQLException;

public class HistoryDao extends BaseDaoImpl
{

    public HistoryDao(ConnectionSource connectionsource)
        throws SQLException
    {
        super(connectionsource, com/ognev/game/uspeed/ormlite/model/History);
    }

    public void clearHistory()
        throws SQLException
    {
        queryBuilder().reset();
        deleteBuilder().reset();
        clearObjectCache();
    }

    public History selectTheBestResult()
        throws SQLException
    {
        QueryBuilder querybuilder = queryBuilder();
        querybuilder.orderBy("downloadSpeed", true);
        querybuilder.limit(Long.valueOf(1L));
        return (History) HelperFactory.getHelper().getHistoryDao().queryForFirst(querybuilder.prepare());
    }
}
