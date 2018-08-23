package com.example.mohamed.movieapp.network;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.mohamed.movieapp.BuildConfig;
import com.example.mohamed.movieapp.data.ReviewData;
import com.example.mohamed.movieapp.listeners.OnPostExecution;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohamed on 11/24/2016.
 */

public class FetchReviews extends AsyncTask<String,Void,List<ReviewData>> {

    private Context context;
    private  static List<ReviewData>reviewDataList;
    public FetchReviews(Context context)
    {
        this.context=context;
    }
    public void setOnPostExecution(OnPostExecution onPostExecution) {
        this.onPostExecution = onPostExecution;
    }

    private OnPostExecution onPostExecution;


        @Override
    protected List<ReviewData> doInBackground(String... params) {
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
                Log.d("review line ", line);
            }
            response = builder.toString();
            Log.d("JSON RVIEW",response);
        } catch (MalformedURLException e) {
            Log.d("Url :","Your url is not valid " +e);
        } catch (IOException e) {
            Log.d("Connection Error :","Your Connection to Trailer is not valid " +e);
        }
        return getReviewData(response);
    }

    @Override
    protected void onPostExecute(List<ReviewData> reviewDatas) {
        onPostExecution.onPostReviewData( reviewDatas);
    }

    private List<ReviewData>getReviewData(String jsonStr)
    {
        List<ReviewData> reviewData=new ArrayList<>();
        try {
            JSONObject root=new JSONObject(jsonStr);
            JSONArray results=root.getJSONArray("results");
            ReviewData review;
            for (int i=0;i<results.length();i++) {
                JSONObject obj=results.getJSONObject(i);
                review=new ReviewData(obj.getString("content"),obj.getString("author"),obj.getString("url"));
                Log.d("Review",review.getAuther());
                reviewData.add(review);

            }
        } catch (JSONException e) {
            Log.d("JSON PARSING REVIEWS ERROR "," there is an error whin parsing json"+e);
        }
        return reviewData;
    }
}
