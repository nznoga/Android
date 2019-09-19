package com.nz.submission4.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.nz.submission4.R;
import com.nz.submission4.db.MovieHelper;
import com.nz.submission4.item.Movie;

import java.util.ArrayList;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{
    private ArrayList<Movie> mWidgetItems =new ArrayList<>();
    private final Context mContext;
    StackRemoteViewsFactory(Context context) {
        mContext = context;
    }
    @Override
    public void onCreate() {
        mWidgetItems.clear();
        MovieHelper item =new MovieHelper(mContext);
        item.open();
        mWidgetItems=item.getAllMovies();
    }

    @Override
    public void onDataSetChanged() {
        final long identityToken = Binder.clearCallingIdentity();
        mWidgetItems.clear();
        MovieHelper item =new MovieHelper(mContext);
        item.open();
        mWidgetItems=item.getAllMovies();
        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mWidgetItems.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
//        Glide.with(mContext)
//                .asBitmap()
//                .load(mWidgetItems.get(position).getPoster())
//                .into(rv);
        //rv.setImageViewBitmap(R.id.imageView, mWidgetItems.get(position).getPoster());
        try {
            Bitmap bitmap = Glide.with(mContext)
                    .asBitmap()
                    .load(mWidgetItems.get(position).getPoster())
                    .submit(512, 512)
                    .get();

            rv.setImageViewBitmap(R.id.imageView, bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Bundle extras = new Bundle();
        extras.putInt(ImageBannerWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
