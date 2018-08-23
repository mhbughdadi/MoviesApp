package com.example.mohamed.movieapp.data;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Mohamed on 11/11/2016.
 */

public class MovieData implements Parcelable
{
    int id;
    String title;
    String originalTitle;
    String poster;
    int voteCount;
    double voteAverage;
    double popularity;
    String backPath;
    String releaseDate;
    String overview;
    Bitmap posterBitmap;
    boolean favorate=false;

    protected MovieData(Parcel in) {
        id = in.readInt();
        title = in.readString();
        originalTitle = in.readString();
        poster = in.readString();
        voteCount = in.readInt();
        voteAverage = in.readDouble();
        popularity = in.readDouble();
        backPath = in.readString();
        releaseDate = in.readString();
        overview = in.readString();
        posterBitmap = in.readParcelable(Bitmap.class.getClassLoader());
        favorate = in.readByte() != 0;
    }

    public static final Creator<MovieData> CREATOR = new Creator<MovieData>() {
        @Override
        public MovieData createFromParcel(Parcel in) {
            return new MovieData(in);
        }

        @Override
        public MovieData[] newArray(int size) {
            return new MovieData[size];
        }
    };

    public void setPosterBitmap(Bitmap posterBitmap) {
        this.posterBitmap = posterBitmap;
    }

    public Bitmap getPosterBitmap() {
        return posterBitmap;
    }

    public MovieData(int id, String title, String originalTitle,
                     String overview, String poster, String backPath,
                     String releaseDate, int voteCount, double popularity,
                     double voteAverage ) {
        this.id = id;
        this.title = title;
        this.originalTitle = originalTitle;
        this.poster = poster;
        this.voteCount = voteCount;

        this.voteAverage = voteAverage;
        this.overview = overview;

        this.releaseDate = releaseDate;
        this.backPath = backPath;
        this.popularity = popularity;

    }

    public boolean isFavorate() {
        return favorate;
    }

    public void setFavorate(boolean favorate) {
        this.favorate = favorate;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getPoster() {
        return poster;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public double getPopularity() {
        return popularity;
    }

    public String getBackPath() {
        return backPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getOverview() {
        return overview;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(originalTitle);
        dest.writeString(poster);
        dest.writeInt(voteCount);
        dest.writeDouble(voteAverage);
        dest.writeDouble(popularity);
        dest.writeString(backPath);
        dest.writeString(releaseDate);
        dest.writeString(overview);
        dest.writeParcelable(posterBitmap, flags);
        dest.writeByte((byte) (favorate ? 1 : 0));
    }
}
