package com.nz.submission4.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nz.submission4.R;
import com.nz.submission4.TvDetailActivity;
import com.nz.submission4.item.TvShow;

import java.util.ArrayList;

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<TvShow> TvList=new ArrayList<>();

    public TvAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(ArrayList<TvShow> items) {
        TvList.clear();
        TvList.addAll(items);
        //TvList=items;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public TvAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tv,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvAdapter.MyViewHolder holder, final int position) {
        final TvShow tvItems = TvList.get(position);
        holder.tvName.setText(tvItems.getName());
        holder.tvDescription.setText(tvItems.getOverview());
        Glide.with(mContext)
                .load(tvItems.getPoster())
                .apply(new RequestOptions().override(350,550))
                .into(holder.imgPhoto);

        holder.relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,tvItems.getName(),Toast.LENGTH_SHORT).show();
            }
        });

        holder.btnDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveWithDataIntent = new Intent(mContext,TvDetailActivity.class);
                moveWithDataIntent.putExtra(TvDetailActivity.EXTRA_TV, TvList.get(position));
                mContext.startActivity(moveWithDataIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return TvList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvDescription;
        ImageView imgPhoto;
        Button btnDescription;
        RelativeLayout relative;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tv_name);
            tvDescription=itemView.findViewById(R.id.tv_description);
            imgPhoto=itemView.findViewById(R.id.img_tv);
            btnDescription=itemView.findViewById(R.id.btnDescription);
            relative=itemView.findViewById(R.id.relative);
        }
    }
}
