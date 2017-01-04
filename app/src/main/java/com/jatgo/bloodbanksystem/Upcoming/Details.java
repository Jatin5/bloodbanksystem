package com.jatgo.bloodbanksystem.Upcoming;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.jatgo.bloodbanksystem.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Jatin on 04-12-2016.
 */
public class Details extends DialogFragment {


    public ArrayList<HashMap<String, String>> camps = new ArrayList<HashMap<String, String>>();
    Context context;

    public Details() {
    }

    public Details(ArrayList<HashMap<String, String>> camps, Context context) {
        this.camps = camps;
        this.context = context;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.details, container,
                false);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        WindowManager wm = (WindowManager)rootView.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        int height=display.getHeight();
        int width=display.getWidth();


        rootView.setMinimumWidth((int)(width));
        rootView.setMinimumHeight((int)(height * 0.9f));

        String arr[]={"Android is the customisable, easy-to-use operating system that powers more than a billion devices across the globe – from phones and tablets to watches, TV, cars and more to come.","A clean, simple, customisable home screen that comes with the power of Google Now: traffic alerts, weather and much more, just a swipe away.","Put the stuff that you care_onboarding about right on your home screen: the latest news, the weather or a stream of your recent photos.","Hail a taxi, find a recipe, run through a temple – Google Play has all the apps and games that let you make your Android device uniquely yours.","Four tips to make your switch to Android quick and easy.","Turn the little green Android mascot into you, your friends, anyone!"};

//        RecyclerView recyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_view);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerView.setAdapter(new CommentsAdp(arr,getContext()));

//        EditText editText=(EditText)rootView.findViewById(R.id.edit_text);
//        editText.getText();


        return rootView;
    }
}
