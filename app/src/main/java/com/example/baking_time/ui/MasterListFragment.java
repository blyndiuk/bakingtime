package com.example.baking_time.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.baking_time.R;
import com.example.baking_time.model.Recipe;

import java.util.ArrayList;

public class MasterListFragment extends Fragment {

    //   OnTextClickListener mCallBack;
    ArrayList<String> mShortDescriptions;

    // Mandatory empty constructor
    public MasterListFragment() {
    }

    Recipe recipe;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            recipe = bundle.getParcelable("i");
        } else
            Log.i("Bundle is null", "null");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("MasterListFragment ", " here");
        final View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);


        if (recipe == null) {
            return rootView;
        }

        mShortDescriptions = new ArrayList<>();
        for (int i = 0; i < recipe.getSteps().size(); i++) {
            String stepDesc = "Step " + (i + 1) + ": " + recipe.getSteps().get(i).getShortDescription();
            mShortDescriptions.add(stepDesc);
        }

        RecyclerView recyclerView = rootView.findViewById(R.id.rv_master_list);

        MasterListRVAdapter reviewsAdapter = new MasterListRVAdapter(recipe, getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(reviewsAdapter);

        // Return the root view
        return rootView;
    }
}
