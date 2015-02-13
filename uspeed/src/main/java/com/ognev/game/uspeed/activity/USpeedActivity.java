// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.ognev.game.uspeed.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import com.ognev.game.uspeed.R;
import com.ognev.game.uspeed.fragment.Item;
import com.ognev.game.uspeed.fragment.MapFragment;
import com.ognev.game.uspeed.fragment.ProfileFragment;
import com.ognev.game.uspeed.fragment.ReportsFragment;
import com.ognev.game.uspeed.fragment.SettingsFragment;
import com.ognev.game.uspeed.fragment.SpeedometerFragment;
import com.ognev.game.uspeed.fragment.login.LoginFragment;
import com.ognev.game.uspeed.ormlite.HelperFactory;

import net.simonvt.menudrawer.Position;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

// Referenced classes of package com.ognev.game.uspeed.activity:
//            BaseMenuListActivity

public class USpeedActivity extends BaseMenuListActivity {

    private static final String STATE_CURRENT_FRAGMENT = "net.simonvt.menudrawer.samples.FragmentSample";
    private String currentFragmentTag;
    private boolean isLogged;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    public USpeedActivity() {
    }

    private Fragment getFragment(String s) {
        Fragment obj;

        obj = mFragmentManager.findFragmentByTag(s);
        switch (Integer.parseInt(s)) {
//            case 1:
//                if (obj == null) {
//                    obj = new FaceBookLoginFragment();
//                }

//                break;
            case 0:
                if (obj == null) {
                    obj = new ProfileFragment();
                }

                break;
            case 1:
                if (obj == null) {
                    obj = new SpeedometerFragment();
                }
                break;
            case 2:

                if (obj == null) {
                    obj = new ReportsFragment();
                }
                break;
            case 3:

                if (obj == null) {
                    obj = new MapFragment();
                }
                break;
            case 4:

                if (obj == null) {
                    obj = new SettingsFragment();
                }
                break;


            default:
                if (obj == null) {
                    obj = new LoginFragment();
                }

        }
        return obj;
    }
    private void getOverflowMenu() {

        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if(menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    private void getOverflowMenu() throws IllegalAccessException {
//        ViewConfiguration viewconfiguration;
//        Field field;
//        try {
//            viewconfiguration = ViewConfiguration.get(this);
//            field = viewconfiguration.getDeclaredField("sHasPermanentMenuKey");
//        } catch (Exception exception) {
//            exception.printStackTrace();
//            return;
//        }
////        if (field == null) {
////            break MISSING_BLOCK_LABEL_28;
////        }
//        field.setAccessible(true);
//        field.setBoolean(viewconfiguration, false);
//    }

    public void attachFragment(int i, Fragment fragment, String s) {
        if (fragment != null) {
            if (fragment.isDetached()) {
                ensureTransaction();
                mFragmentTransaction.attach(fragment);
            } else if (!fragment.isAdded()) {
                ensureTransaction();
                mFragmentTransaction.add(i, fragment, s);
                return;
            }
        }
    }

    public void commitTransactions() {
        if (mFragmentTransaction != null && !mFragmentTransaction.isEmpty()) {
            mFragmentTransaction.commit();
            mFragmentTransaction = null;
        }
    }

    public void detachFragment(Fragment fragment) {
        if (fragment != null && !fragment.isDetached()) {
            ensureTransaction();
            mFragmentTransaction.detach(fragment);
        }
    }

    public void disableToggleMenu() {
        mMenuDrawer.setTouchMode(0);
        isLogged = false;
    }

    public void enableToggleMenu() {
        mMenuDrawer.setTouchMode(1);
        isLogged = true;
    }

    protected FragmentTransaction ensureTransaction() {
        if (mFragmentTransaction == null) {
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.setTransition(4099);
        }
        return mFragmentTransaction;
    }

    protected int getDragMode() {
        return 1;
    }

    protected Position getDrawerPosition() {
        return Position.LEFT;
    }

    public Menu getMenu() {
        return menu;
    }

    public FragmentManager getmFragmentManager() {
        return mFragmentManager;
    }

    public void onBackPressed() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.leaving_soon));
        builder.setNegativeButton(R.string.no, new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int i) {
                dialoginterface.dismiss();
            }

        });
        builder.setPositiveButton(getString(R.string.yes), new android.content.DialogInterface.OnClickListener() {


            public void onClick(DialogInterface dialoginterface, int i) {
                finish();
            }

        });
        builder.show();
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getOverflowMenu();
        Signature asignature[] = new Signature[0];
        int i = 0;
        try {
            asignature = getPackageManager().getPackageInfo("com.ognev.game.uspeed", 64).signatures;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        Signature signature = asignature[i];
        MessageDigest messagedigest = null;
        try {
            messagedigest = MessageDigest.getInstance("SHA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        messagedigest.update(signature.toByteArray());
        Log.d("KeyHash:", Base64.encodeToString(messagedigest.digest(), 0));
        mFragmentManager = getSupportFragmentManager();
        currentFragmentTag = "99";
        try {
            if (HelperFactory.getHelper().getUserDao().queryForAll().size() != 0)
                currentFragmentTag = "1";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        isLogged = false;
        mMenuDrawer.setEnabled(false);
        mMenuDrawer.setTouchMode(0);
        attachFragment(mMenuDrawer.getContentContainer().getId(), getFragment(currentFragmentTag), currentFragmentTag);
        commitTransactions();
        if (android.os.Build.VERSION.SDK_INT >= 14) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mMenuDrawer.setOnDrawerStateChangeListener(new net.simonvt.menudrawer.MenuDrawer.OnDrawerStateChangeListener() {


            public void onDrawerSlide(float f, int k) {
            }

            public void onDrawerStateChange(int k, int l) {
                if (l == 0) {
                    commitTransactions();
                }
            }
        });
        isLogged = true;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        this.menu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    protected void onMenuItemClicked(int i, Item item) {
        if (i != Integer.parseInt(currentFragmentTag)) {
            if (currentFragmentTag != null) {
                detachFragment(getFragment(currentFragmentTag));
            }
            currentFragmentTag = (new StringBuilder()).append(i).append("").toString();
            attachFragment(mMenuDrawer.getContentContainer().getId(), getFragment(currentFragmentTag), currentFragmentTag);
        }
        mMenuDrawer.closeMenu();
    }

    public boolean onOptionsItemSelected(MenuItem menuitem) {
        boolean s = false;
        switch (menuitem.getItemId()) {
            case R.id.testMode:
                s = true;
                Toast.makeText(this, "testMode", Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent("speedometerMode");
            intent1.putExtra("isOnline", false);
            sendBroadcast(intent1);
                break;
            case R.id.onlineMode:
                s = true;
                Toast.makeText(this, "onlineMode", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent("speedometerMode");
            intent.putExtra("isOnline", true);
            sendBroadcast(intent);
                break;
        }
        if (isLogged && !s) {
            mMenuDrawer.toggleMenu();
        }
        return super.onOptionsItemSelected(menuitem);

    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString("net.simonvt.menudrawer.samples.FragmentSample", currentFragmentTag);
    }

}
