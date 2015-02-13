// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.ognev.game.uspeed.ormlite;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;

// Referenced classes of package com.ognev.game.uspeed.ormlite:
//            DBHelper

public class HelperFactory {

    private static DBHelper helper;

    public HelperFactory() {
    }

    public static DBHelper getHelper() {
        return helper;
    }

    public static void setHelper(Context context) {
        helper = (DBHelper) OpenHelperManager.getHelper(context, DBHelper.class);
    }
}
