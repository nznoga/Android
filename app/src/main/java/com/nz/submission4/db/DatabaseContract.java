package com.nz.submission4.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static String TABLE_MOVIE = "movie";
    public static String TABLE_TV = "tv";
    public static final class MovieColumns implements BaseColumns {
        public static String ID_MOVIE = "id_movie";
        public static String TITLE = "title";
        public static String DESCRIPTION = "description";
        public static String RATE = "rate";
        public static String IMAGE = "image";
    }
    public static final class TvColumns implements BaseColumns{
        public static String ID_TV = "id_tv";
        public static String TITLE_TV = "title";
        public static String DESCRIPTION_TV = "description";
        public static String RATE_TV = "rate";
        public static String IMAGE_TV = "image";
    }
    public static final String AUTHORITY = "com.nz.submission4";
    public static final Uri CONTENT_URI_MOVIE = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_MOVIE)
            .build();
    public static final Uri CONTENT_URI_TV = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_TV)
            .build();
    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }
    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt( cursor.getColumnIndex(columnName) );
    }
    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong( cursor.getColumnIndex(columnName) );
    }
}
