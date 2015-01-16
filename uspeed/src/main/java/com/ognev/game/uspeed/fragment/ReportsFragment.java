// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.ognev.game.uspeed.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.ognev.game.uspeed.adapter.ReportAdapter;
import com.ognev.game.uspeed.ormlite.HelperFactory;
import com.ognev.game.uspeed.ormlite.model.History;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.ognev.game.uspeed.fragment:
//            BaseFragment

public class ReportsFragment extends BaseFragment
{

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

    public ReportsFragment()
    {
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        histories = new ArrayList();
        reportAdapter = new ReportAdapter(getActivity(), histories);
    }

    public View onCreateView(LayoutInflater layoutinflater, ViewGroup viewgroup, Bundle bundle)
    {
        View view;
        long l;
        view = layoutinflater.inflate(0x7f030022, null);
        bestResult = (LinearLayout)view.findViewById(0x7f06006e);
        date = (TextView)view.findViewById(0x7f060069);
        address = (TextView)view.findViewById(0x7f060059);
        networkType = (TextView)view.findViewById(0x7f06006a);
        downloadSpeed = (TextView)view.findViewById(0x7f06006b);
        uploadSpeed = (TextView)view.findViewById(0x7f06006c);
        countTesting = (TextView)view.findViewById(0x7f06006d);
        emptyText = (TextView)view.findViewById(0x7f060070);
        histories.clear();
        l = 0L;
        histories.addAll(HelperFactory.getHelper().getHistoryDao().queryForAll());
        l = HelperFactory.getHelper().getHistoryDao().countOf();
        if (l > 0L)
        {
            try
            {
                bestHistory = HelperFactory.getHelper().getHistoryDao().selectTheBestResult();
            }
            catch (SQLException sqlexception)
            {
                sqlexception.printStackTrace();
            }
        }
        countTesting.setText((new StringBuilder()).append(l).append(" ").append(getString(0x7f090080)).toString());
        reportAdapter.notifyDataSetChanged();
        if (bestHistory != null)
        {
            date.setText(bestHistory.getDate());
            address.setText(bestHistory.getLocation());
            networkType.setText(bestHistory.getNetworkType());
            downloadSpeed.setText(bestHistory.getDownloadSpeed());
            uploadSpeed.setText(bestHistory.getUploadSpeed());
            bestResult.setVisibility(0);
        } else
        {
            bestResult.setVisibility(8);
        }
        reportsListView = (ListView)view.findViewById(0x7f06006f);
        reportsListView.setAdapter(reportAdapter);
        reportsListView.setEmptyView(emptyText);
        reportAdapter.notifyDataSetChanged();
        reportsListView.setOnScrollListener(new android.widget.AbsListView.OnScrollListener() {

            final ReportsFragment this$0;

            public void onScroll(AbsListView abslistview, int i, int j, int k)
            {
                int i1 = 0;
                while (i1 < reportsListView.getTouchables().size()) 
                {
                    View view1 = ((View)reportsListView.getTouchables().get(i1)).findViewById(0x7f060067);
                    int j1;
                    if (i1 == 1)
                    {
                        j1 = 0x7f02007c;
                    } else
                    {
                        j1 = 0x7f02007d;
                    }
                    view1.setBackgroundResource(j1);
                    i1++;
                }
            }

            public void onScrollStateChanged(AbsListView abslistview, int i)
            {
            }

            
            {
                this$0 = ReportsFragment.this;
                super();
            }
        });
        return view;
    }

    public void onResume()
    {
        super.onResume();
    }

}
