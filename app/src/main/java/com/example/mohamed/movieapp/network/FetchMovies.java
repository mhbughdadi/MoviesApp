package com.example.mohamed.movieapp.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.mohamed.movieapp.BuildConfig;
import com.example.mohamed.movieapp.data.MovieData;
import com.example.mohamed.movieapp.listeners.OnPostExecution;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohamed on 11/18/2016.
 */

public class FetchMovies extends AsyncTask<String, Void, List<MovieData>> {
    private OnPostExecution post;
    private ProgressDialog progressDialog;
    Context context;

    public FetchMovies(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {

        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);
        progressDialog.show();
        super.onPreExecute();

    }

    @Override
    protected List<MovieData> doInBackground(String... params) {
        URL url = null;
        HttpURLConnection conn;
        InputStream in;
        String response = null;
        BufferedReader readr;
        StringBuilder builder;
        try {
            Uri uri = Uri.parse(params[0]).buildUpon()
                    .appendQueryParameter("api_key", BuildConfig.THE_MOVIEDB_API_KEY).build();
            url = new URL(uri.toString());
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedInputStream buffer = new BufferedInputStream(conn.getInputStream());
            readr = new BufferedReader(new InputStreamReader(buffer));

            builder = new StringBuilder();
            String line;
            while ((line = readr.readLine()) != null) {
                builder.append(line).append('\n');
                Log.d("line", line);
            }
            response = builder.toString();
            Log.d("JSONSTRIKNG ", response);

        } catch (MalformedURLException e) {
//           Toast.makeText(context,"Error in url"+e.toString(),Toast.LENGTH_SHORT).show();
            Log.d("Debug issue  ", "There is an problem while URL....." + e.toString());
        } catch (IOException e) {
//            Toast.makeText(context, "Error in Connection "+e.toString(), Toast.LENGTH_SHORT).show();
            Log.d("Debug issue  ", "There is an problem while OPENNING CONNECTION....." + e.toString());
        }


        return getMovieData(response);
    }

    private List<MovieData> getMovieData(String response) {

        List<MovieData> data;
        data = new ArrayList<>();
        if (response != null) {

            try {
                JSONObject root = new JSONObject(response);
                JSONArray results = root.getJSONArray("results");

                for (int i = 0; i < results.length(); i++) {
                    JSONObject movie = results.getJSONObject(i);
                    String title = movie.getString("title");
                    String originalTitle = movie.getString("original_title");
                    String overview = movie.getString("overview");
                    String poster = movie.getString("poster_path");
                    String backPath = movie.getString("backdrop_path");
                    String releaseDate = movie.getString("release_date");
                    int id = movie.getInt("id");
                    int voteCount = movie.getInt("vote_count");
                    double voteAverage = movie.getDouble("vote_average");
                    double popularity = movie.getDouble("popularity");
                    data.add(new MovieData(id, title, originalTitle, overview, poster, backPath, releaseDate, voteCount, popularity, voteAverage));
                }
            } catch (JSONException e) {
//                Toast.makeText(context, "Error in parsing JSON STR" + e.toString(), Toast.LENGTH_SHORT).show();
                Log.d("Debug issue  ", "There is an problem while JSONPARSING....." + e.toString());
            }


        }

        return data;
    }

    @Override
    protected void onPostExecute(List<MovieData> movieData) {
        post.onPostMovieExec(movieData);
        if (progressDialog != null) {
//            loader.hide();
            progressDialog.dismiss();
        }
        super.onPostExecute(movieData);
    }


    public OnPostExecution getPost() {
        return post;
    }

    public void setOnPostExecution(OnPostExecution post) {
        this.post = post;
    }


}
