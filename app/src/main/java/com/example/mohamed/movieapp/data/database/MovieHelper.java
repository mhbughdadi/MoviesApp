package com.example.mohamed.movieapp.data.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mohamed on 11/25/2016.
 */

public class MovieHelper extends SQLiteOpenHelper {
    private static final String DB_NAME="movie.db";
    private static final int DB_VER=1;
    private Context cotext;
    public MovieHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="create table "+MovieContract.TABLE_NAME +" ("+ MovieContract._ID +" INTEGER   PRIMARY KEY AUTOINCREMENT,"
                +MovieContract.TITLE +" TEXT NOT NULL ,"
                + MovieContract.ORIGINAL_TITLE +" TEXT NOT NULL ,"
                +MovieContract.OVERVIEW+" TEXT NOT NULL ,"
                +MovieContract.POSTER_PATH + " TEXT NOT NULL ,"
                +MovieContract.IMAGE +" BLOB  NOT NULL ,"
                +MovieContract.VOTE_COUNT+" TEXT NOT NULL  ,"
                +MovieContract.VOTE_AVERAGE +" TEXT NOT NULL ,"
                +MovieContract.POPULARITY + " TEXT NOT NULL ,"
                +MovieContract.RELEASE_DATE+ " TEXT NOT NULL  ,"
                +MovieContract.FAVORATE+" INTEGER NOT NULL );"
                ;
        db.execSQL(query);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+MovieContract.TABLE_NAME+" ;");
        onCreate(db);
    }
    public boolean isFavorateMovie(SQLiteDatabase db,int id)
    {
        Cursor cursor=db.query(MovieContract.TABLE_NAME,new String []{MovieContract._ID},MovieContract._ID+"=?",new String[]{String.valueOf(id)},null,null,null);
        if (cursor!=null&&cursor.getCount()>0)
            return true;
        return false;
    }
}
