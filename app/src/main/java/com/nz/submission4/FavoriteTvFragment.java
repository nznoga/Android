package com.nz.submission4;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nz.submission4.adapter.TvAdapter;
import com.nz.submission4.db.MovieHelper;
import com.nz.submission4.item.TvShow;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteTvFragment extends Fragment {
    private RecyclerView recyclerView;
    private TvAdapter adapter;
    private ArrayList<TvShow> tvList;


    public FavoriteTvFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_tv, container, false);
        recyclerView = view.findViewById(R.id.rvf_tv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter= new TvAdapter(getContext());
        MovieHelper item =new MovieHelper(getContext());
        item.open();
        tvList=new ArrayList<>();
        tvList=item.getAllTv();
        adapter.setData(tvList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return view;
    }

    @Override
    public void onStart(){
        super.onStart();
        MovieHelper item =new MovieHelper(getContext());
        tvList=item.getAllTv();
        adapter.setData(tvList);
        recyclerView.setAdapter(adapter);
    }

}
