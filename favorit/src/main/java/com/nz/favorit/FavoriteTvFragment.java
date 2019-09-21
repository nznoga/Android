package com.nz.favorit;


import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nz.favorit.Adapter.TvAdapter;

import static com.nz.favorit.DatabaseContract.CONTENT_URI_MOVIE;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteTvFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor>,
        AdapterView.OnItemClickListener{
    private TvAdapter adapter;
    ListView lvNotes;
    private final int LOAD_NOTES_ID = 110;
    LoaderManager loaderManager;

    public FavoriteTvFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_favorite_tv, container, false);
        lvNotes = view.findViewById(R.id.rvf_tv);
        adapter = new TvAdapter(getContext(), null, true);
        lvNotes.setAdapter(adapter);
        loaderManager = getLoaderManager();
        loaderManager.initLoader(LOAD_NOTES_ID, null, this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(getContext(), CONTENT_URI_MOVIE, null, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
