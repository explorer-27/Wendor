package com.example.ashu.wendor.Utility;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by ashu on 19/3/18.
 */

public class NetworkUtility {

    final static String BASE_URL = "http://wendor.in/internships/mock.json";

    //Creating URL for fetching JSON File....

    public static URL buildUrl() {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    //Getting JSON Response.....

    public static String getResponseFromHttpUrl(URL url) throws Exception {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        String JSONFILE = null;
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if (hasInput)
                JSONFILE = scanner.next();
            else
                JSONFILE = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JSONFILE;

    }
}
