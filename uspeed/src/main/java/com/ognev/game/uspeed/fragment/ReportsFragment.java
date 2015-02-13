// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.ognev.game.uspeed.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ognev.game.uspeed.R;
import com.ognev.game.uspeed.adapter.ReportAdapter;
import com.ognev.game.uspeed.ormlite.HelperFactory;
import com.ognev.game.uspeed.ormlite.model.History;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.ognev.game.uspeed.fragment:
//            BaseFragment

public class ReportsFragment extends BaseFragment {

    private TextView address;
    private History bestHistory;
    private LinearLayout bestResult;
    private long count;
    private TextView countTesting;
    private TextView date;
    private TextView downloadSpeed;
    private TextView emptyText;
    private List histories;
    private TextView networkType;
    private ReportAdapter reportAdapter;
    private ListView reportsListView;
    private TextView uploadSpeed;

    public ReportsFragment() {
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        histories = new ArrayList();
        reportAdapter = new ReportAdapter(getActivity(), histories);
    }

    public View onCreateView(LayoutInflater layoutinflater, ViewGroup viewgroup, Bundle bundle) {
        View view;
        long l;
        view = layoutinflater.inflate(R.layout.report_view, null);
        bestResult = (LinearLayout) view.findViewById(R.id.bestResult);
        date = (TextView) view.findViewById(R.id.date);
        address = (TextView) view.findViewById(R.id.address);
        networkType = (TextView) view.findViewById(R.id.networkType);
        downloadSpeed = (TextView) view.findViewById(R.id.downloadSpeed);
        uploadSpeed = (TextView) view.findViewById(R.id.uploadSpeed);
        countTesting = (TextView) view.findViewById(R.id.countTesting);
        emptyText = (TextView) view.findViewById(R.id.emptyText);
        histories.clear();
        l = 0L;
        try {
            histories.addAll(HelperFactory.getHelper().getHistoryDao().queryForAll());
            l = HelperFactory.getHelper().getHistoryDao().countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        if (l > 0L) {
//            try {
//                bestHistory = HelperFactory.getHelper().getHistoryDao().selectTheBestResult();
//            } catch (SQLException sqlexception) {
//                sqlexception.printStackTrace();
//            }
//        }
        countTesting.setText((new StringBuilder()).append(l).append(" ").append(getString(R.string.time)).toString());
        reportAdapter.notifyDataSetChanged();

        SelectBestResult selectBestResult = new SelectBestResult();
        selectBestResult.execute();
        reportsListView = (ListView) view.findViewById(R.id.reportsList);
        reportsListView.setAdapter(reportAdapter);
        reportsListView.setEmptyView(emptyText);
        reportAdapter.notifyDataSetChanged();
        reportsListView.setOnScrollListener(new android.widget.AbsListView.OnScrollListener() {

            public void onScroll(AbsListView abslistview, int i, int j, int k) {
                int i1 = 0;
                while (i1 < reportsListView.getTouchables().size()) {
                    View view1 = ((View) reportsListView.getTouchables().get(i1)).findViewById(R.id.reportItem);
                    int j1;
                    if (i1 == 1) {
                        j1 = R.drawable.reports_item_active;
                    } else {
                        j1 = R.drawable.reports_item_inactive;
                    }
                    view1.setBackgroundResource(j1);
                    i1++;
                }
            }

            public void onScrollStateChanged(AbsListView abslistview, int i) {
            }

        });
        return view;
    }

    public void onResume() {
        super.onResume();
    }
    private class SelectBestResult extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            List<History> histories = new ArrayList<History>();
            try {
                histories.addAll(HelperFactory.getHelper().getHistoryDao().queryForAll());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if(histories.size() > 0) {
                History history;
                double max = Double.parseDouble(histories.get(0).getDownloadSpeed());
                for (int i = 0; i < histories.size(); i++)
                    if (max <= Double.parseDouble(histories.get(i).getDownloadSpeed())) {
                        max = Double.parseDouble(histories.get(i).getDownloadSpeed());
                        bestHistory = histories.get(i);
                    }

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (bestHistory != null) {
                date.setText(bestHistory.getDate());
                address.setText(bestHistory.getLocation());
                networkType.setText(bestHistory.getNetworkType());
                downloadSpeed.setText(bestHistory.getDownloadSpeed() + " " + bestHistory.getDownloadPostFix());
                uploadSpeed.setText(bestHistory.getUploadSpeed() + " " + bestHistory.getUploadPostFix());
                bestResult.setVisibility(View.VISIBLE);
            } else {
                bestResult.setVisibility(View.GONE);
            }
        }
    }
}
