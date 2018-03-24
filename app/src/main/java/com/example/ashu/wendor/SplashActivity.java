package com.example.ashu.wendor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.net.URL;

public class SplashActivity extends AppCompatActivity {
    static Context appContext;
    boolean InternetStatus;
    private String imageDir = "imageDir";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        appContext = this;
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity

                if (sp.getBoolean("firstrun", true)) {
                    /*
                        *When App launches for the First time....
                        *Load json file...
                        *And Create a Database....
                    */

                    URL jsonUrl = NetworkUtility.buildUrl();
                    new QueryTask().execute(jsonUrl);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean("firstrun", false).commit();
                } else {
                    finish();
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    i.putExtra("json", "HEllo");
                    startActivity(i);
                }
            }
        }, 3000);

    }

    public boolean checkInternet() {
        boolean internetStatus = true;
        ConnectivityManager connec = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED || connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED
                || connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTING || connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING)
            internetStatus = true;
        else if (connec.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED || connec.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED)
            internetStatus = false;

        return internetStatus;
    }

    //Used for fetching json
    private class QueryTask extends AsyncTask<URL, Void, String> {
        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String searchResults = null;
            try {
                InternetStatus = checkInternet();
                if (InternetStatus)
                    searchResults = NetworkUtility.getResponseFromHttpUrl(searchUrl);
                else
                    searchResults = "No Internet";
            } catch (Exception e) {
                e.printStackTrace();
                searchResults = null;
            }
            return searchResults;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("Wendor", s);
            JsonData.getJsonData(s, SplashActivity.this);
            finish();
            Intent i = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(i);

        }
    }
}
