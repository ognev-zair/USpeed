// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.ognev.game.uspeed.ormlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.ognev.game.uspeed.ormlite.dao.HistoryDao;
import com.ognev.game.uspeed.ormlite.dao.UserDao;

import java.sql.SQLException;

public class DBHelper extends OrmLiteSqliteOpenHelper
{

    private static final String DATABASE_NAME = "uspeed.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TAG = com/ognev/game/uspeed/ormlite/DBHelper.getSimpleName();
    private HistoryDao historyDao;
    private UserDao userDao;

    public DBHelper(Context context)
    {
        super(context, "uspeed.db", null, 2);
    }

    public HistoryDao getHistoryDao()
        throws SQLException
    {
        if (historyDao == null)
        {
            historyDao = new HistoryDao(getConnectionSource());
        }
        return historyDao;
    }

    public UserDao getUserDao()
        throws SQLException
    {
        if (userDao == null)
        {
            userDao = new UserDao(getConnectionSource());
        }
        return userDao;
    }

    public void onCreate(SQLiteDatabase sqlitedatabase, ConnectionSource connectionsource)
    {
        try
        {
            TableUtils.createTable(connectionsource, com/ognev/game/uspeed/ormlite/model/User);
            TableUtils.createTable(connectionsource, com/ognev/game/uspeed/ormlite/model/History);
            return;
        }
        catch (SQLException sqlexception)
        {
            Log.e(TAG, "error creating DB uspeed.db");
            throw new RuntimeException(sqlexception);
        }
    }

    public void onUpgrade(SQLiteDatabase sqlitedatabase, ConnectionSource connectionsource, int i, int j)
    {
        try
        {
            TableUtils.dropTable(connectionsource, com/ognev/game/uspeed/ormlite/model/User, true);
            onCreate(sqlitedatabase, connectionsource);
            return;
        }
        catch (SQLException sqlexception)
        {
            Log.e(TAG, (new StringBuilder()).append("error upgrading db uspeed.dbfrom ver ").append(i).toString());
            throw new RuntimeException(sqlexception);
        }
    }

    public void releaseHelper()
        throws SQLException
    {
        TableUtils.dropTable(connectionSource, com/ognev/game/uspeed/ormlite/model/History, false);
        TableUtils.createTable(connectionSource, com/ognev/game/uspeed/ormlite/model/History);
    }

}
