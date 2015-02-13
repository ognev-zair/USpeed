// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.ognev.game.uspeed.fragment;

//import com.facebook.widget.FacebookFragment;
import com.facebook.widget.FacebookFragment;
import com.ognev.game.uspeed.R;
import com.ognev.game.uspeed.activity.USpeedActivity;
import com.ognev.game.uspeed.fragment.login.FaceBookLoginFragment;
import com.ognev.game.uspeed.fragment.login.LoginFragment;

// Referenced classes of package com.ognev.game.uspeed.fragment:
//            ProfileFragment, SpeedometerFragment, ReportsFragment, MapFragment, 
//            SettingsFragment

public class BaseFragment extends FacebookFragment {

    public BaseFragment() {
    }

    public void onResume() {
        super.onResume();
        if (((USpeedActivity) getActivity()).getMenu() != null) {
            ((USpeedActivity) getActivity()).getMenu().getItem(0).setVisible(false);
            ((USpeedActivity) getActivity()).getMenu().getItem(1).setVisible(false);
        }
        if(this instanceof SpeedometerFragment) {
            getActivity().getActionBar().setTitle(getString(R.string.speedometer));
            if (((USpeedActivity) getActivity()).getMenu() != null) {
                ((USpeedActivity) getActivity()).getMenu().getItem(0).setVisible(true);
                ((USpeedActivity) getActivity()).getMenu().getItem(1).setVisible(true);
                return;
            }
        }

        if ((this instanceof ProfileFragment))
            getActivity().getActionBar().setTitle(getString(R.string.profile));
        if (this instanceof ReportsFragment) {
            getActivity().getActionBar().setTitle(getString(R.string.reports));
        }

        if (this instanceof MapFragment)
        getActivity().getActionBar().setTitle(getString(R.string.map));
        if(this instanceof SettingsFragment)
        getActivity().getActionBar().setTitle(getString(R.string.settings));
        if(this instanceof LoginFragment) {
            getActivity().getActionBar().setTitle(getString(R.string.registration));
            ((USpeedActivity) getActivity()).disableToggleMenu();
        }

    }
}
