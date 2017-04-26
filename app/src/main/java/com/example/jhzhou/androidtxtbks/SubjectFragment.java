package com.example.jhzhou.androidtxtbks;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by jhzhou on 4/24/17.
 */

public class SubjectFragment extends Fragment {

    public static final String[] TEMPS = new String[]{
            "Algorithms" , "Computers", "Cooking", "Education", "Science", "Biography & Autobiography"
    };

    private static final String LIST = "list";

    private ArrayList<String> subjectList;

    public static SubjectFragment getInstance() {
        SubjectFragment fragment = new SubjectFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            subjectList = savedInstanceState.getStringArrayList(LIST);
        } else {
            initailList();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_subject, container, false);
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
