package com.nz.favorit;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import static com.nz.favorit.DatabaseContract.MovieColumns.IMAGE;
import static com.nz.favorit.DatabaseContract.MovieColumns.TITLE;
import static com.nz.favorit.DatabaseContract.getColumnString;

public class MovieAdapter extends CursorAdapter {
    public MovieAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, viewGroup, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if (cursor != null){
            TextView movieName = view.findViewById(R.id.movie_name);
            ImageView gambar = view.findViewById(R.id.img_movie);
            movieName.setText(getColumnString(cursor,TITLE));
            Glide.with(context)
                    .load(getColumnString(cursor,IMAGE))
                    .apply(new RequestOptions().override(350,550))
                    .into(gambar);
        }
    }
    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }
}
