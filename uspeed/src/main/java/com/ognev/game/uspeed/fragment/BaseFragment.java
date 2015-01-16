// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.ognev.game.uspeed.fragment;

import com.facebook.widget.FacebookFragment;
import com.ognev.game.uspeed.activity.USpeedActivity;
import com.ognev.game.uspeed.fragment.login.LoginFragment;

// Referenced classes of package com.ognev.game.uspeed.fragment:
//            ProfileFragment, SpeedometerFragment, ReportsFragment, MapFragment, 
//            SettingsFragment

public class BaseFragment extends FacebookFragment
{

    public BaseFragment()
    {
    }

    public void onResume()
    {
        super.onResume();
        if (!(this instanceof ProfileFragment)) goto _L2; else goto _L1
_L1:
        getActivity().getActionBar().setTitle(getString(0x7f090073));
        if (((USpeedActivity)getActivity()).getMenu() != null)
        {
            ((USpeedActivity)getActivity()).getMenu().getItem(0).setVisible(false);
            ((USpeedActivity)getActivity()).getMenu().getItem(1).setVisible(false);
        }
_L4:
        return;
_L2:
        if (!(this instanceof SpeedometerFragment))
        {
            break; /* Loop/switch isn't completed */
        }
        getActivity().getActionBar().setTitle(getString(0x7f09007a));
        if (((USpeedActivity)getActivity()).getMenu() != null)
        {
            ((USpeedActivity)getActivity()).getMenu().getItem(0).setVisible(true);
            ((USpeedActivity)getActivity()).getMenu().getItem(1).setVisible(true);
            return;
        }
        if (true) goto _L4; else goto _L3
_L3:
        if (this instanceof ReportsFragment)
        {
            getActivity().getActionBar().setTitle(getString(0x7f09007c));
            ((USpeedActivity)getActivity()).getMenu().getItem(0).setVisible(false);
            ((USpeedActivity)getActivity()).getMenu().getItem(1).setVisible(false);
            return;
        }
        if (!(this instanceof MapFragment))
        {
            break; /* Loop/switch isn't completed */
        }
        getActivity().getActionBar().setTitle(getString(0x7f09006b));
        if (((USpeedActivity)getActivity()).getMenu() != null)
        {
            ((USpeedActivity)getActivity()).getMenu().getItem(0).setVisible(false);
            ((USpeedActivity)getActivity()).getMenu().getItem(1).setVisible(false);
            return;
        }
        if (true) goto _L4; else goto _L5
_L5:
        if (!(this instanceof SettingsFragment))
        {
            continue; /* Loop/switch isn't completed */
        }
        getActivity().getActionBar().setTitle(getString(0x7f090079));
        if (((USpeedActivity)getActivity()).getMenu() != null)
        {
            ((USpeedActivity)getActivity()).getMenu().getItem(0).setVisible(false);
            ((USpeedActivity)getActivity()).getMenu().getItem(1).setVisible(false);
            return;
        }
        continue; /* Loop/switch isn't completed */
        if (!(this instanceof LoginFragment)) goto _L4; else goto _L6
_L6:
        getActivity().getActionBar().setTitle(getString(0x7f090069));
        if (((USpeedActivity)getActivity()).getMenu() != null)
        {
            ((USpeedActivity)getActivity()).getMenu().getItem(0).setVisible(false);
            ((USpeedActivity)getActivity()).getMenu().getItem(1).setVisible(false);
            return;
        }
        if (true) goto _L4; else goto _L7
_L7:
    }
}
