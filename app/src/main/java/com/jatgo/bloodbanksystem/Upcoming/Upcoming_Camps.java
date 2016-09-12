package com.jatgo.bloodbanksystem.Upcoming;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jatgo.bloodbanksystem.R;

import java.util.ArrayList;

/**
 * Created by Jatin on 11-09-2016.
 */
public class Upcoming_Camps extends Fragment {

    Camp_adp adp;
    ArrayList<String> camps;
    LinearLayoutManager mLinearLayoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        camps = new ArrayList<>();
        camps.add("PGI");
        camps.add("PEC");
        adp=new Camp_adp(camps,getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.upcoming, container, false);

        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);


        RecyclerView  r= (RecyclerView)v.findViewById(R.id.my_recycler_view);
        r.setLayoutManager(mLinearLayoutManager);
        r.setAdapter(adp);

        return v;
    }
}
