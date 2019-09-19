package com.nz.submission4;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nz.submission4.adapter.TvAdapter;
import com.nz.submission4.item.TvShow;
import com.nz.submission4.viewmodel.MainViewModel;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvFragment extends Fragment {
    private RecyclerView recyclerView;
    private TvAdapter adapter;
    ProgressDialog pd;


    public TvFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        recyclerView = view.findViewById(R.id.rv_movie);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        initViews();
        return view;
    }

    private void initViews() {
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Fetching Movies...");
        pd.setCancelable(false);
        pd.show();
        adapter = new TvAdapter(getActivity());
        adapter.notifyDataSetChanged();
        MainViewModel model = ViewModelProviders.of(this).get(MainViewModel.class);
        model.getTv().observe(this, getTvs);
        model.setListTv("tv");
    }
    private Observer<ArrayList<TvShow>> getTvs = new Observer<ArrayList<TvShow>>() {
        @Override
        public void onChanged(ArrayList<TvShow> tvShows) {
            adapter.setData(tvShows);
            recyclerView.setAdapter(adapter);
            pd.dismiss();
        }
    };
}
