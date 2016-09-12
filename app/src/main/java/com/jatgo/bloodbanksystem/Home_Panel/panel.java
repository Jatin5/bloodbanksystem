package com.jatgo.bloodbanksystem.Home_Panel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jatgo.bloodbanksystem.R;

/**
 * Created by Jatin on 12-09-2016.
 */
public class panel extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.panel, container, false);
        return v;
    }
}
