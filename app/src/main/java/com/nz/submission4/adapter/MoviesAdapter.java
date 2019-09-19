package com.nz.submission4.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nz.submission4.MovieDetailActivity;
import com.nz.submission4.R;
import com.nz.submission4.item.Movie;

import java.util.ArrayList;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<Movie> movieList=new ArrayList<>();

    public MoviesAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(ArrayList<Movie> items) {
        movieList.clear();
        movieList.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MoviesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesAdapter.MyViewHolder holder, final int position) {
        final Movie movieItems = movieList.get(position);
        holder.movieName.setText(movieItems.getName());
        Glide.with(mContext)
                .load(movieItems.getPoster())
                .apply(new RequestOptions().override(350,550))
                .into(holder.imgPhoto);

        holder.Llayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveWithDataIntent = new Intent(mContext,MovieDetailActivity.class);
                moveWithDataIntent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movieList.get(position));
                mContext.startActivity(moveWithDataIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView movieName;
        LinearLayout Llayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto=itemView.findViewById(R.id.img_movie);
            movieName=itemView.findViewById(R.id.movie_name);
            Llayout=itemView.findViewById(R.id.linear);
        }
    }
}
