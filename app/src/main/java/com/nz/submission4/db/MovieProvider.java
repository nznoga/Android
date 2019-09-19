package com.nz.submission4.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.nz.submission4.db.DatabaseContract.AUTHORITY;
import static com.nz.submission4.db.DatabaseContract.CONTENT_URI_MOVIE;

public class MovieProvider extends ContentProvider {
    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;
    private static final int TV = 3;
    private static final int TV_ID = 4;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {

        // content://com.dicoding.mynotesapp/note
        sUriMatcher.addURI(AUTHORITY, DatabaseContract.TABLE_MOVIE, MOVIE);

        // content://com.dicoding.mynotesapp/note/id
        sUriMatcher.addURI(AUTHORITY,
                DatabaseContract.TABLE_MOVIE+ "/#",
                MOVIE_ID);
        sUriMatcher.addURI(AUTHORITY, DatabaseContract.TABLE_TV, TV);
        sUriMatcher.addURI(AUTHORITY,
                DatabaseContract.TABLE_TV+ "/#",
                TV_ID);
    }
    private MovieHelper movieHelper;
    @Override
    public boolean onCreate() {
        movieHelper = MovieHelper.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        movieHelper.open();
        Cursor cursor;
        switch(sUriMatcher.match(uri)){
            case MOVIE:
                cursor = movieHelper.queryProviderMovie();
                break;
            case MOVIE_ID:
                cursor = movieHelper.queryByIdProviderMovie(uri.getLastPathSegment());
                break;
            case TV:
                cursor = movieHelper.queryProviderTV();
                break;
            case TV_ID:
                cursor = movieHelper.queryByIdProviderTV(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        if (cursor != null){
            cursor.setNotificationUri(getContext().getContentResolver(),uri);
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        long added ;

        switch (sUriMatcher.match(uri)){
            case MOVIE:
                movieHelper.open();
                added = movieHelper.insertProviderMovie(contentValues);
                movieHelper.close();
                break;
            case TV:
                movieHelper.open();
                added = movieHelper.insertProviderTV(contentValues);
                movieHelper.close();
                break;
            default:
                added = 0;
                break;
        }

        if (added > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return Uri.parse(CONTENT_URI_MOVIE + "/" + added);

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case MOVIE_ID:
                movieHelper.open();
                deleted =  movieHelper.deleteProviderMovie(uri.getLastPathSegment());
                movieHelper.close();
                break;
            case TV_ID:
                movieHelper.open();
                deleted =  movieHelper.deleteProviderTV(uri.getLastPathSegment());
                movieHelper.close();
                break;
            default:
                deleted = 0;
                break;
        }

        if (deleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        int updated ;
        switch (sUriMatcher.match(uri)) {
            case MOVIE_ID:
                updated =  movieHelper.updateProviderMovie(uri.getLastPathSegment(),contentValues);
                break;
            case TV_ID:
                updated =  movieHelper.updateProviderTV(uri.getLastPathSegment(),contentValues);
                break;
            default:
                updated = 0;
                break;
        }

        if (updated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return updated;
    }
}
