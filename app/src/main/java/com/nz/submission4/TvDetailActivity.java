package com.nz.submission4;

import androidx.appcompat.app.AppCompatActivity;

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
import com.nz.submission4.item.TvShow;

import static com.nz.submission4.db.DatabaseContract.CONTENT_URI_TV;
import static com.nz.submission4.db.DatabaseContract.TABLE_TV;
import static com.nz.submission4.db.DatabaseContract.TvColumns.DESCRIPTION_TV;
import static com.nz.submission4.db.DatabaseContract.TvColumns.ID_TV;
import static com.nz.submission4.db.DatabaseContract.TvColumns.IMAGE_TV;
import static com.nz.submission4.db.DatabaseContract.TvColumns.RATE_TV;
import static com.nz.submission4.db.DatabaseContract.TvColumns.TITLE_TV;

public class TvDetailActivity extends AppCompatActivity {
    public static final String EXTRA_TV = "extra_tv";
    TextView name,plotSynopsis,userrating;
    ImageView photo;
    private TvShow tvShow;
    private MovieHelper movieHelper;
    MaterialFavoriteButton materialFavoriteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_detail);

        photo=findViewById(R.id.img_tv_detail);
        plotSynopsis=findViewById(R.id.d_description_tv);
        name= findViewById(R.id.d_name_tv);
        userrating= findViewById(R.id.vote_average);
        materialFavoriteButton = findViewById(R.id.favorite);

        tvShow =getIntent().getParcelableExtra(EXTRA_TV);

        name.setText(tvShow.getName());
        plotSynopsis.setText(tvShow.getOverview());
        userrating.setText(Double.toString(tvShow.getRate()));
        Glide.with(this)
                .load(tvShow.getPoster())
                .apply(new RequestOptions().override(350,550))
                .into(photo);
        if (Exist(tvShow.getName())){
            materialFavoriteButton.setFavorite(true);
            materialFavoriteButton.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
                @Override
                public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                    if (favorite){
                        Save();
                        Snackbar.make(buttonView,"Added to Favorite",Snackbar.LENGTH_SHORT).show();
                    }else {
                        Uri uri= Uri.parse(CONTENT_URI_TV+"/"+tvShow.getId());
                        getContentResolver().delete(uri,null, null );
                        Snackbar.make(buttonView,"Removed from Favorite",Snackbar.LENGTH_SHORT).show();
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
                    } else {
                        Uri uri= Uri.parse(CONTENT_URI_TV+"/"+tvShow.getId());
                        getContentResolver().delete(uri,null, null );
                        Snackbar.make(buttonView, "Removed from Favorite", Snackbar.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private boolean Exist(String name) {
        String pilih= TITLE_TV+" =?";
        String[] pilihArg={name};
        String limit="1";
        movieHelper= new MovieHelper(this);
        movieHelper.open();
        DatabaseHelper dataBaseHelper= new DatabaseHelper(TvDetailActivity.this);
        SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
        Cursor cursor= database.query(TABLE_TV,null,pilih,pilihArg,null,null,null,limit);
        boolean exists;
        /*if (cursor==null){
            exists=false;
        }*/
        exists=(cursor.getCount() > 0);
        cursor.close();
        movieHelper.close();
        return exists;
    }
    private void Save(){
        ContentValues args = new ContentValues();
        args.put(ID_TV, tvShow.getId());
        args.put(TITLE_TV, tvShow.getName());
        args.put(DESCRIPTION_TV, tvShow.getOverview());
        args.put(RATE_TV, tvShow.getRate());
        args.put(IMAGE_TV, tvShow.getPoster());
        getContentResolver().insert(CONTENT_URI_TV, args);
    }
}
