package com.nz.submission4;


import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nz.submission4.adapter.TvAdapter;
import com.nz.submission4.item.TvShow;
import com.nz.submission4.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvFragment extends Fragment {
    private RecyclerView recyclerView;
    private TvAdapter adapter;
    ProgressDialog pd;
    MainViewModel model;


    public TvFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        setHasOptionsMenu(true);
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
        model = ViewModelProviders.of(this).get(MainViewModel.class);
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
    private final Observer<ArrayList<TvShow>> getSearchTelevision = new Observer<ArrayList<TvShow>>() {
        @Override
        public void onChanged(ArrayList<TvShow> tvShows) {
            adapter.setData(tvShows);
            pd.dismiss();
        }
    };
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        searchMovie(menu);
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem menuItem = menu.findItem(R.id.searchku);
        menuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                model.setListTv("tv");
                return true;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }else if(item.getItemId() == R.id.set_remider) {
            Intent intent = new Intent(getContext(), SetReminder.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    private void searchMovie(Menu menu) {
        SearchManager searchManager;
        if (getContext() != null) {
            searchManager = (SearchManager) getContext().getSystemService(Context.SEARCH_SERVICE);
            if (searchManager != null) {
                androidx.appcompat.widget.SearchView searchView =
                        (androidx.appcompat.widget.SearchView) (menu.findItem(R.id.searchku)).getActionView();
                if (getActivity() != null) {
                    searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
                }

                searchView.setIconifiedByDefault(true);
                searchView.setFocusable(true);
                searchView.setIconified(false);
                searchView.requestFocusFromTouch();
                searchView.setQueryHint("Cari Tv");


                androidx.appcompat.widget.SearchView.OnQueryTextListener queryTextListener = new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        model.getSearchTv().observe(Objects.requireNonNull(getActivity()),getSearchTelevision);
                        model.setSearchTelevision(query);
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        return false;
                    }
                };

                searchView.setOnQueryTextListener(queryTextListener);
            }
        }
    }
}
