package com.nz.submission4;

import androidx.appcompat.app.AppCompatActivity;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.google.android.material.snackbar.Snackbar;
import com.nz.submission4.db.DatabaseContract;
import com.nz.submission4.db.DatabaseHelper;
import com.nz.submission4.db.MovieHelper;
import com.nz.submission4.item.Movie;
import com.nz.submission4.widget.ImageBannerWidget;

import static com.nz.submission4.db.DatabaseContract.CONTENT_URI_MOVIE;
import static com.nz.submission4.db.DatabaseContract.MovieColumns.DESCRIPTION;
import static com.nz.submission4.db.DatabaseContract.MovieColumns.ID_MOVIE;
import static com.nz.submission4.db.DatabaseContract.MovieColumns.IMAGE;
import static com.nz.submission4.db.DatabaseContract.MovieColumns.RATE;
import static com.nz.submission4.db.DatabaseContract.MovieColumns.TITLE;
import static com.nz.submission4.db.DatabaseContract.TABLE_MOVIE;

public class MovieDetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "extra_movie";
    TextView name,plotSynopsis,userrating;
    ImageView photo;
    private Movie movie;
    private MovieHelper movieHelper;
    MaterialFavoriteButton materialFavoriteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        photo=findViewById(R.id.img_movie_detail);
        plotSynopsis=findViewById(R.id.d_description_movie);
        name= findViewById(R.id.d_name_movie);
        userrating= findViewById(R.id.vote_average);
        materialFavoriteButton = findViewById(R.id.favorite);

        movie =getIntent().getParcelableExtra(EXTRA_MOVIE);

        name.setText(movie.getName());
        plotSynopsis.setText(movie.getOverview());
        userrating.setText(Double.toString(movie.getRate()));
        Glide.with(this)
                .load(movie.getPoster())
                .apply(new RequestOptions().override(350,550))
                .into(photo);

        if (Exist(movie.getName())){
            materialFavoriteButton.setFavorite(true);
            materialFavoriteButton.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
                @Override
                public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                    if (favorite){
                        Save();
                        Snackbar.make(buttonView,"Added to Favorite",Snackbar.LENGTH_SHORT).show();
                        updatewidget();
                    }else {
                        Uri uri=Uri.parse(CONTENT_URI_MOVIE+"/"+movie.getId());
                        getContentResolver().delete(uri,null, null );
                        Snackbar.make(buttonView,"Removed from Favorite",Snackbar.LENGTH_SHORT).show();
                        updatewidget();
                    }
                }
            });
        }else {
            materialFavoriteButton.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
                @Override
                public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                    if (favorite) {
                        Save();
                        Snackbar.make(buttonView, "Added to Favorite", Snackbar.LENGTH_SHORT).show();
                        updatewidget();
                    } else {
                        Uri uri=Uri.parse(CONTENT_URI_MOVIE+"/"+movie.getId());
                        getContentResolver().delete(uri,null, null );
                        Snackbar.make(buttonView, "Removed from Favorite", Snackbar.LENGTH_SHORT).show();
                        updatewidget();
                    }
                }
            });
        }
    }
    private void Save(){
        ContentValues args = new ContentValues();
        args.put(ID_MOVIE,movie.getId());
        args.put(TITLE, movie.getName());
        args.put(DESCRIPTION, movie.getOverview());
        args.put(RATE, movie.getRate());
        args.put(IMAGE, movie.getPoster());
        getContentResolver().insert(CONTENT_URI_MOVIE, args);
    }
    public boolean Exist(String item){
        String pilih=DatabaseContract.MovieColumns.TITLE+" =?";
        String[] pilihArg={item};
        String limit="1";
        movieHelper= new MovieHelper(this);
        movieHelper.open();
        DatabaseHelper dataBaseHelper= new DatabaseHelper(MovieDetailActivity.this);
        SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
        Cursor cursor= database.query(TABLE_MOVIE,null,pilih,pilihArg,null,null,null,limit);
        boolean exists;
        exists=(cursor.getCount() > 0);
        cursor.close();
        movieHelper.close();
        return exists;
    }
    public void updatewidget(){
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        ComponentName thisWidget = new ComponentName(this, ImageBannerWidget.class);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.stack_view);
    }
}
