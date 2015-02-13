// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.ognev.game.uspeed.ormlite.dao;

import android.os.AsyncTask;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.ognev.game.uspeed.ormlite.HelperFactory;
import com.ognev.game.uspeed.ormlite.model.History;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HistoryDao extends BaseDaoImpl {

    public HistoryDao(ConnectionSource connectionsource)
            throws SQLException {
        super(connectionsource, History.class);
    }

    public void clearHistory()
            throws SQLException {
        queryBuilder().reset();
        deleteBuilder().reset();
        clearObjectCache();
    }

    public History selectTheBestResult() throws SQLException {
        QueryBuilder querybuilder = queryBuilder();
        querybuilder.orderBy("downloadSpeed", false);
        querybuilder.limit(Long.valueOf(1L));
        return (History) HelperFactory.getHelper().getHistoryDao().queryForFirst(querybuilder.prepare());
    }

    private class selectBestResult extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            List<History> histories = new ArrayList<History>();
            try {
                histories.addAll(queryForAll());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            History history;
            int max = Integer.parseInt(histories.get(0).getDownloadSpeed());
            for(int i = 0; i < histories.size(); i++)
                if(max < Integer.parseInt(histories.get(i).getDownloadSpeed())) {
                    max = Integer.parseInt(histories.get(i).getDownloadSpeed());
                    history = histories.get(i);
                }

            return null;
        }
    }
}
