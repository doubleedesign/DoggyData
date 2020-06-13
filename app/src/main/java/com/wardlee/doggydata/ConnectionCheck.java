package com.wardlee.doggydata;

import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;

public class ConnectionCheck {
    // Tag for debugging
    private static final String TAG = "ConnectionCheck";

    // Variables to be passed in from the activity or fragment calling for a connection check
    private Context thisContext;

    /**
     * Constructor
     */
    public ConnectionCheck(Context context) {
        thisContext = context;
    }

    /**
     * Utility function to check if the internet is available before trying to fetch live news
     * @link https://stackoverflow.com/questions/9570237/android-check-internet-connection/33764301
     *
     * @return bool
     */
    public boolean isInternetAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }


    /**
     * Method to show an error dialog if there's no internet connection
     */
    protected void showLoadingError(Context context) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Network error");
        alertDialog.setMessage("Please check your network connection so you can see the latest news from NewsAPI.org and access links in this application.");
        alertDialog.show();
    }
}
