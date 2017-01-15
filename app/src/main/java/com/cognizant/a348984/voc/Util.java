package com.cognizant.a348984.voc;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Prabakaran on 15-01-2017.
 */

public class Util {

    static final String LOG_TAG = "UtilClass";

    public static final String REQ_BASE_URL = "http://localhost:8984";

    public static final String REQ_LOGIN = "0100";
    public static final String REQ_REGISTER = "0200";
    public static final String REQ_CATEGORY = "0300";
    public static final String REQ_ADD_CATEGORY = "0310";
    public static final String REQ_QUESTION = "0400";
    public static final String REQ_SURVEY = "0500";
    public static final String REQ_PROGRESS = "0600";
    public static final String REQ_STATISTICS = "0700";

    public static final String API_KEY_PARM = "key";
    public static final String API_KEY_VALUE = "APPATHON";

    public static final String API_REQ_PARM = "req";
    public static final String API_REQ_VALUE_PARM = "req_value";

    public static String getDataFromUri(Uri uri){

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String jsonServerValue = null;
        try {
            URL urlTrailer = new URL(uri.toString());
            urlConnection = (HttpURLConnection) urlTrailer.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                Log.e(LOG_TAG, "No response from server");
                return null;
            }

            jsonServerValue = buffer.toString();
            Log.v(LOG_TAG, "JSON Value string: " + jsonServerValue);

        } catch (Exception e) {
            Log.e(LOG_TAG, "Exception", e);
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final Exception e) {
                    Log.e(LOG_TAG, "Error closing streams", e);
                }
            }
        }
        return jsonServerValue;
    }
}
