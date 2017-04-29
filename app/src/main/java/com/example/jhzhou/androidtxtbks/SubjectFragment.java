package com.example.jhzhou.androidtxtbks;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jhzhou on 4/24/17.
 */

public class SubjectFragment extends Fragment {

    public static final String[] TEMPS = new String[]{
            "Algorithms" , "Computers", "Cooking", "Education", "Science", "Biography & Autobiography"
    };

    private static final String LIST = "list";

    private ArrayList<String> subjectList;

    @BindView(R.id.subject_list_view) ListView subjectListView;

    public static SubjectFragment getInstance() {
        SubjectFragment fragment = new SubjectFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subject, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            subjectList = savedInstanceState.getStringArrayList(LIST);
        } else {
            initailList();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), R.layout.list_view_item, subjectList);
        subjectListView.setAdapter(adapter);
        subjectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // todo
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(LIST, subjectList);
    }

    public void initailList() {
        subjectList = new ArrayList<>();
        for (String temp: TEMPS) {
            subjectList.add(temp);
        }
        Collections.sort(subjectList);
    }
}
