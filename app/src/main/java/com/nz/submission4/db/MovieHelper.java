package com.nz.submission4.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nz.submission4.item.Movie;
import com.nz.submission4.item.TvShow;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.nz.submission4.db.DatabaseContract.MovieColumns.DESCRIPTION;
import static com.nz.submission4.db.DatabaseContract.MovieColumns.ID_MOVIE;
import static com.nz.submission4.db.DatabaseContract.MovieColumns.IMAGE;
import static com.nz.submission4.db.DatabaseContract.MovieColumns.RATE;
import static com.nz.submission4.db.DatabaseContract.MovieColumns.TITLE;
import static com.nz.submission4.db.DatabaseContract.TABLE_MOVIE;
import static com.nz.submission4.db.DatabaseContract.TABLE_TV;
import static com.nz.submission4.db.DatabaseContract.TvColumns.DESCRIPTION_TV;
import static com.nz.submission4.db.DatabaseContract.TvColumns.ID_TV;
import static com.nz.submission4.db.DatabaseContract.TvColumns.IMAGE_TV;
import static com.nz.submission4.db.DatabaseContract.TvColumns.RATE_TV;
import static com.nz.submission4.db.DatabaseContract.TvColumns.TITLE_TV;

public class MovieHelper {
    private static final String DATABASE_TABLE = TABLE_MOVIE;
    private static final String DATABASE_TABLE1 = TABLE_TV;
    private static DatabaseHelper dataBaseHelper;
    private static MovieHelper INSTANCE;
    private static SQLiteDatabase database;

    public MovieHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static MovieHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }
    public void close() {
        dataBaseHelper.close();
        if (database.isOpen())
            database.close();
    }

    public ArrayList<Movie> getAllMovies() {
        ArrayList<Movie> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        Movie filmku;
        if (cursor.getCount() > 0) {
            do {
                filmku = new Movie();
                filmku.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ID_MOVIE)));
                filmku.setName(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                filmku.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                filmku.setRate(Double.parseDouble(cursor.getString(cursor.getColumnIndexOrThrow(RATE))));
                filmku.setPoster(cursor.getString(cursor.getColumnIndexOrThrow(IMAGE)));
                arrayList.add(filmku);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }
    public ArrayList<TvShow> getAllTv() {
        ArrayList<TvShow> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE1, null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        TvShow filmku;
        if (cursor.getCount() > 0) {
            do {
                filmku = new TvShow();
                filmku.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ID_TV)));
                filmku.setName(cursor.getString(cursor.getColumnIndexOrThrow(TITLE_TV)));
                filmku.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION_TV)));
                filmku.setRate(Double.parseDouble(cursor.getString(cursor.getColumnIndexOrThrow(RATE_TV))));
                filmku.setPoster(cursor.getString(cursor.getColumnIndexOrThrow(IMAGE_TV)));
                arrayList.add(filmku);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertMovie(Movie filmku) {
        ContentValues args = new ContentValues();
        args.put(ID_MOVIE, filmku.getId());
        args.put(TITLE, filmku.getName());
        args.put(DESCRIPTION, filmku.getOverview());
        args.put(RATE, filmku.getRate());
        args.put(IMAGE, filmku.getPoster());
        return database.insert(DATABASE_TABLE, null, args);
    }
    public long insertTv(TvShow filmku) {
        ContentValues args = new ContentValues();
        args.put(ID_TV, filmku.getId());
        args.put(TITLE_TV, filmku.getName());
        args.put(DESCRIPTION_TV, filmku.getOverview());
        args.put(RATE_TV, filmku.getRate());
        args.put(IMAGE_TV, filmku.getPoster());
        return database.insert(DATABASE_TABLE1, null, args);
    }

    public int updateMovie(Movie filmku) {
        ContentValues args = new ContentValues();
        args.put(ID_MOVIE, filmku.getId());
        args.put(TITLE, filmku.getName());
        args.put(DESCRIPTION, filmku.getOverview());
        args.put(RATE, filmku.getRate());
        args.put(IMAGE, filmku.getPoster());
        return database.update(DATABASE_TABLE, args, ID_MOVIE + "= '" + filmku.getId() + "'", null);
    }
    public int updateTv(TvShow filmku) {
        ContentValues args = new ContentValues();
        args.put(ID_TV, filmku.getId());
        args.put(TITLE_TV, filmku.getName());
        args.put(DESCRIPTION_TV, filmku.getOverview());
        args.put(RATE_TV, filmku.getRate());
        args.put(IMAGE_TV, filmku.getPoster());
        return database.update(DATABASE_TABLE1, args, ID_TV + "= '" + filmku.getId() + "'", null);
    }

    public int deleteMovie(int id) {
        return database.delete(TABLE_MOVIE, ID_MOVIE + " = '" + id + "'", null);
    }
    public int deleteTv(int id) {
        return database.delete(TABLE_TV, ID_TV + " = '" + id + "'", null);
    }
    //iki sing anyar
    public Cursor queryByIdProviderMovie(String id){
        return database.query(DATABASE_TABLE,null
                ,ID_MOVIE + " = ?"
                ,new String[]{id}
                ,null
                ,null
                ,null
                ,null);
    }
    public Cursor queryProviderMovie(){
        return database.query(DATABASE_TABLE1
                ,null
                ,null
                ,null
                ,null
                ,null
                ,_ID + " DESC");
    }
    public long insertProviderMovie(ContentValues values){
        return database.insert(DATABASE_TABLE,null,values);
    }
    public int updateProviderMovie(String id,ContentValues values){
        return database.update(DATABASE_TABLE,values,_ID +" = ?",new String[]{id} );
    }
    public int deleteProviderMovie(String id){
        return database.delete(DATABASE_TABLE,ID_MOVIE + " = ?", new String[]{id});
    }
    //Iki TV
    public Cursor queryByIdProviderTV(String id){
        return database.query(DATABASE_TABLE1,null
                ,ID_TV + " = ?"
                ,new String[]{id}
                ,null
                ,null
                ,null
                ,null);
    }
    public Cursor queryProviderTV(){
        return database.query(DATABASE_TABLE
                ,null
                ,null
                ,null
                ,null
                ,null
                ,_ID + " DESC");
    }
    public long insertProviderTV(ContentValues values){
        return database.insert(DATABASE_TABLE1,null,values);
    }
    public int updateProviderTV(String id,ContentValues values){
        return database.update(DATABASE_TABLE1,values,_ID +" = ?",new String[]{id} );
    }
    public int deleteProviderTV(String id){
        return database.delete(DATABASE_TABLE1,ID_TV + " = ?", new String[]{id});
    }
}
