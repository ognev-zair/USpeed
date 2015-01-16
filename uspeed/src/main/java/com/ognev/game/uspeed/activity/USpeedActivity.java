// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.ognev.game.uspeed.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.Toast;
import com.ognev.game.uspeed.fragment.login.FaceBookLoginFragment;
import com.ognev.game.uspeed.fragment.login.LoginFragment;
import com.ognev.game.uspeed.ormlite.HelperFactory;
import com.ognev.game.uspeed.fragment.Item;
import com.ognev.game.uspeed.fragment.MapFragment;
import com.ognev.game.uspeed.fragment.ProfileFragment;
import com.ognev.game.uspeed.fragment.ReportsFragment;
import com.ognev.game.uspeed.fragment.SettingsFragment;
import com.ognev.game.uspeed.fragment.SpeedometerFragment;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import net.simonvt.menudrawer.MenuDrawer;
import net.simonvt.menudrawer.Position;

// Referenced classes of package com.ognev.game.uspeed.activity:
//            BaseMenuListActivity

public class USpeedActivity extends BaseMenuListActivity
{

    private static final String STATE_CURRENT_FRAGMENT = "net.simonvt.menudrawer.samples.FragmentSample";
    private String currentFragmentTag;
    private boolean isLogged;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    public USpeedActivity()
    {
    }

    private Fragment getFragment(String s)
    {
        Integer.parseInt(s);
        JVM INSTR tableswitch 0 5: default 44
    //                   0 67
    //                   1 88
    //                   2 109
    //                   3 130
    //                   4 151
    //                   5 172;
           goto _L1 _L2 _L3 _L4 _L5 _L6 _L7
_L1:
        Object obj;
        obj = mFragmentManager.findFragmentByTag(s);
        if (obj == null)
        {
            obj = new FaceBookLoginFragment();
        }
_L9:
        return ((Fragment) (obj));
_L2:
        obj = mFragmentManager.findFragmentByTag(s);
        if (obj == null)
        {
            return new ProfileFragment();
        }
        continue; /* Loop/switch isn't completed */
_L3:
        obj = mFragmentManager.findFragmentByTag(s);
        if (obj == null)
        {
            return new SpeedometerFragment();
        }
        continue; /* Loop/switch isn't completed */
_L4:
        obj = mFragmentManager.findFragmentByTag(s);
        if (obj == null)
        {
            return new ReportsFragment();
        }
        continue; /* Loop/switch isn't completed */
_L5:
        obj = mFragmentManager.findFragmentByTag(s);
        if (obj == null)
        {
            return new MapFragment();
        }
        continue; /* Loop/switch isn't completed */
_L6:
        obj = mFragmentManager.findFragmentByTag(s);
        if (obj == null)
        {
            return new SettingsFragment();
        }
        continue; /* Loop/switch isn't completed */
_L7:
        obj = mFragmentManager.findFragmentByTag(s);
        if (obj == null)
        {
            return new LoginFragment();
        }
        if (true) goto _L9; else goto _L8
_L8:
    }

    private void getOverflowMenu()
    {
        ViewConfiguration viewconfiguration;
        Field field;
        try
        {
            viewconfiguration = ViewConfiguration.get(this);
            field = android/view/ViewConfiguration.getDeclaredField("sHasPermanentMenuKey");
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            return;
        }
        if (field == null)
        {
            break MISSING_BLOCK_LABEL_28;
        }
        field.setAccessible(true);
        field.setBoolean(viewconfiguration, false);
    }

    public void attachFragment(int i, Fragment fragment, String s)
    {
        if (fragment != null)
        {
            if (fragment.isDetached())
            {
                ensureTransaction();
                mFragmentTransaction.attach(fragment);
            } else
            if (!fragment.isAdded())
            {
                ensureTransaction();
                mFragmentTransaction.add(i, fragment, s);
                return;
            }
        }
    }

    public void commitTransactions()
    {
        if (mFragmentTransaction != null && !mFragmentTransaction.isEmpty())
        {
            mFragmentTransaction.commit();
            mFragmentTransaction = null;
        }
    }

    public void detachFragment(Fragment fragment)
    {
        if (fragment != null && !fragment.isDetached())
        {
            ensureTransaction();
            mFragmentTransaction.detach(fragment);
        }
    }

    public void disableToggleMenu()
    {
        mMenuDrawer.setTouchMode(0);
        isLogged = false;
    }

    public void enableToggleMenu()
    {
        mMenuDrawer.setTouchMode(1);
        isLogged = true;
    }

    protected FragmentTransaction ensureTransaction()
    {
        if (mFragmentTransaction == null)
        {
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.setTransition(4099);
        }
        return mFragmentTransaction;
    }

    protected int getDragMode()
    {
        return 1;
    }

    protected Position getDrawerPosition()
    {
        return Position.LEFT;
    }

    public Menu getMenu()
    {
        return menu;
    }

    public FragmentManager getmFragmentManager()
    {
        return mFragmentManager;
    }

    public void onBackPressed()
    {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle(getString(0x7f090053));
        builder.setNegativeButton(getString(0x7f09006d), new android.content.DialogInterface.OnClickListener() {

            final USpeedActivity this$0;

            public void onClick(DialogInterface dialoginterface, int i)
            {
                dialoginterface.dismiss();
            }

            
            {
                this$0 = USpeedActivity.this;
                super();
            }
        });
        builder.setPositiveButton(getString(0x7f090084), new android.content.DialogInterface.OnClickListener() {

            final USpeedActivity this$0;

            public void onClick(DialogInterface dialoginterface, int i)
            {
                onBackPressed();
            }

            
            {
                this$0 = USpeedActivity.this;
                super();
            }
        });
        builder.show();
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        getOverflowMenu();
        Signature asignature[];
        int i;
        asignature = getPackageManager().getPackageInfo("com.ognev.game.uspeed", 64).signatures;
        i = asignature.length;
        int j = 0;
_L2:
        if (j >= i)
        {
            break; /* Loop/switch isn't completed */
        }
        Signature signature = asignature[j];
        MessageDigest messagedigest = MessageDigest.getInstance("SHA");
        messagedigest.update(signature.toByteArray());
        Log.d("KeyHash:", Base64.encodeToString(messagedigest.digest(), 0));
        j++;
        continue; /* Loop/switch isn't completed */
        NoSuchAlgorithmException nosuchalgorithmexception;
        nosuchalgorithmexception;
        break; /* Loop/switch isn't completed */
        if (true) goto _L2; else goto _L1
_L1:
        mFragmentManager = getSupportFragmentManager();
        currentFragmentTag = "0";
        if (HelperFactory.getHelper().getUserDao().queryForAll().size() != 0) goto _L4; else goto _L3
_L3:
        currentFragmentTag = "5";
        isLogged = false;
        mMenuDrawer.setEnabled(false);
        mMenuDrawer.setTouchMode(0);
_L6:
        attachFragment(mMenuDrawer.getContentContainer().getId(), getFragment(currentFragmentTag), currentFragmentTag);
        commitTransactions();
        if (android.os.Build.VERSION.SDK_INT >= 14)
        {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mMenuDrawer.setOnDrawerStateChangeListener(new net.simonvt.menudrawer.MenuDrawer.OnDrawerStateChangeListener() {

            final USpeedActivity this$0;

            public void onDrawerSlide(float f, int k)
            {
            }

            public void onDrawerStateChange(int k, int l)
            {
                if (l == 0)
                {
                    commitTransactions();
                }
            }

            
            {
                this$0 = USpeedActivity.this;
                super();
            }
        });
        return;
_L4:
        try
        {
            isLogged = true;
        }
        catch (SQLException sqlexception)
        {
            sqlexception.printStackTrace();
        }
        if (true) goto _L6; else goto _L5
_L5:
        android.content.pm.PackageManager.NameNotFoundException namenotfoundexception;
        namenotfoundexception;
        if (true) goto _L1; else goto _L7
_L7:
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(0x7f0b0000, menu);
        this.menu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    protected void onMenuItemClicked(int i, Item item)
    {
        if (i != Integer.parseInt(currentFragmentTag))
        {
            if (currentFragmentTag != null)
            {
                detachFragment(getFragment(currentFragmentTag));
            }
            currentFragmentTag = (new StringBuilder()).append(i).append("").toString();
            attachFragment(mMenuDrawer.getContentContainer().getId(), getFragment(currentFragmentTag), currentFragmentTag);
        }
        mMenuDrawer.closeMenu();
    }

    public boolean onOptionsItemSelected(MenuItem menuitem)
    {
        menuitem.getItemId();
        JVM INSTR lookupswitch 3: default 40
    //                   16908332: 127
    //                   2131099767: 46
    //                   2131099768: 88;
           goto _L1 _L2 _L3 _L4
_L1:
        return super.onOptionsItemSelected(menuitem);
_L3:
        Toast.makeText(this, "testMode", 0).show();
        Intent intent1 = new Intent("speedometerMode");
        intent1.putExtra("isOnline", false);
        sendBroadcast(intent1);
        continue; /* Loop/switch isn't completed */
_L4:
        Toast.makeText(this, "onlineMode", 0).show();
        Intent intent = new Intent("speedometerMode");
        intent.putExtra("isOnline", true);
        sendBroadcast(intent);
        continue; /* Loop/switch isn't completed */
_L2:
        if (isLogged)
        {
            mMenuDrawer.toggleMenu();
        }
        if (true) goto _L1; else goto _L5
_L5:
    }

    protected void onSaveInstanceState(Bundle bundle)
    {
        super.onSaveInstanceState(bundle);
        bundle.putString("net.simonvt.menudrawer.samples.FragmentSample", currentFragmentTag);
    }

}
