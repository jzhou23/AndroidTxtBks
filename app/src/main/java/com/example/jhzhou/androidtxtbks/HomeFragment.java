package com.example.jhzhou.androidtxtbks;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by jhzhou on 4/24/17.
 */

public class HomeFragment extends Fragment {

    private static HomeFragment instance = null;

    private BookAdapter mAdapter;

    private RecyclerView mRecyclerView;

    private HomeFragment() {

    }

    public static synchronized HomeFragment getInstance() {
        if (instance == null) {
            instance = new HomeFragment();
        }
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new BookAdapter(getActivity(), new ArrayList<Book>());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.home_fragment_recyclerView);
        mRecyclerView.setAdapter(mAdapter);

    }
}
