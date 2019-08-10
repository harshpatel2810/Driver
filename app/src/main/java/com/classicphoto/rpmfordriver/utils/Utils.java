package com.classicphoto.rpmfordriver.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;

import com.classicphoto.rpmfordriver.R;

/**
 * Created by pc on 17-04-2017.
 */

public class Utils {
    public static boolean connectionAvailable(final Context c) {

        ConnectivityManager cm = (ConnectivityManager) c
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm != null && cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isConnected())
            return true;
        else {
            ((Activity) c).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(c,
                            c.getString(R.string.err_network),
                            Toast.LENGTH_LONG).show();
                }
            });
            return false;
        }
    }
}
