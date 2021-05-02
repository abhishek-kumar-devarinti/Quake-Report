package com.example.android.quakereport;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import java.net.URL;

import java.nio.charset.Charset;

import java.util.ArrayList;

import java.util.List;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {
    }


    public static List<Earthquake> fetchEartquakeData(String requesturl){
        URL url = createUrl(requesturl);

        String jsonResponse = null;
        try{
            jsonResponse = makeHttpRequest(url);
        }
        catch (IOException e){
            Log.e(LOG_TAG,"Error getting Input");
        }
        List<Earthquake> earthquakes = extractFeaturesFromJson(jsonResponse);
        return earthquakes;
    }



    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null){
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromInput(inputStream);
            }
            else{
                Log.e(LOG_TAG,"Response code is "+urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }
            if (inputStream != null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromInput(InputStream inputStream) throws IOException {
        StringBuilder builder = new StringBuilder();
        if (inputStream!= null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null){
                builder.append(line);
                line = bufferedReader.readLine();
            }
        }
        return builder.toString();
    }

    private static URL createUrl(String requesturl) {
        URL url = null;
        try {
               url = new URL(requesturl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    private static List<Earthquake> extractFeaturesFromJson(String jsonResponse) {
        List<Earthquake> list = new ArrayList<>();
        if (TextUtils.isEmpty(jsonResponse)){
            return null;
        }
        try {
            JSONObject jsonRootObject = new JSONObject(jsonResponse);

            JSONArray earthquakeArray = jsonRootObject.getJSONArray("features");
            for (int i=0;i<earthquakeArray.length();i++){
                JSONObject currentearthquakeObject = earthquakeArray.getJSONObject(i);
                JSONObject propertiesObject = currentearthquakeObject.getJSONObject("properties");
                Double mag = propertiesObject.getDouble("mag");
                String city = propertiesObject.getString("place");
                long time = propertiesObject.getLong("time");
                String url = propertiesObject.getString("url");
                list.add(new Earthquake(mag,city,time,url));
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}