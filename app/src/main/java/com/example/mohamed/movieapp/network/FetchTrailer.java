package com.example.mohamed.movieapp.network;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.mohamed.movieapp.BuildConfig;
import com.example.mohamed.movieapp.data.TrailerData;
import com.example.mohamed.movieapp.listeners.OnPostExecution;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by Mohamed on 11/24/2016.
 */

public class FetchTrailer extends AsyncTask<String ,Void,List<TrailerData>> {
    private Context context;
    private  static List<TrailerData>trailerDataList;

    public void setOnPostExecution(OnPostExecution onPostExecution) {
        this.onPostExecution = onPostExecution;
    }

    private OnPostExecution onPostExecution;
    public FetchTrailer(Context context)
    {
        this.context=context;
    }

    public static List<TrailerData> getTrailerArray(){
        if (trailerDataList !=null)
        {
            trailerDataList = new ArrayList<TrailerData>();
        }
        else
        {

        }
        return  trailerDataList;
    }
    @Override
    protected List<TrailerData> doInBackground(String... params) {
        Uri uri=Uri.parse(params[0]).buildUpon().appendQueryParameter("api_key", BuildConfig.THE_MOVIEDB_API_KEY).build();
        URL url=null;
        StringBuilder builder;
        BufferedReader readr;
        String response="";
        try {
            url=new URL(uri.toString());
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedInputStream buffer = new BufferedInputStream(conn.getInputStream());
            readr = new BufferedReader(new InputStreamReader(buffer));

            builder = new StringBuilder();
            String line;
            while ((line = readr.readLine()) != null) {
                builder.append(line).append('\n');
//                Log.d("line", line);
            }
            response = builder.toString();

        } catch (MalformedURLException e) {
            Log.d("Url :","Your url is not valid " +e);
        } catch (IOException e) {
            Log.d("Connection Error :","Your Connection to Trailer is not valid " +e);
        }
        return getTrailerData(response);
    }

    @Override
    protected void onPostExecute(List<TrailerData> trailerDatas) {
        onPostExecution.onPostTrailerData( trailerDatas);
    }

    private List<TrailerData>getTrailerData(String jsonStr)
    {

        List<TrailerData>trailerData=new ArrayList<>();
        try {
            JSONObject root=new JSONObject(jsonStr);
            JSONArray results=root.getJSONArray("results");
            TrailerData trailer;
            for (int i=0;i<results.length();i++) {
                JSONObject obj=results.getJSONObject(i);
                trailer=new TrailerData(obj.getString("name"),obj.getString("key"));

                trailerData.add(trailer);

            }
        } catch (JSONException e) {
            Log.d("JSON PARSING "," there is an error whin parsing json"+e);
        }
        return trailerData;
    }
}
