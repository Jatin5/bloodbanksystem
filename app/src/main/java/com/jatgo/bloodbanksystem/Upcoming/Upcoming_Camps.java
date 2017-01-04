package com.jatgo.bloodbanksystem.Upcoming;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.jatgo.bloodbanksystem.LoginActivity;
import com.jatgo.bloodbanksystem.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Jatin on 11-09-2016.
 */
public class Upcoming_Camps extends Fragment {


    ArrayList<HashMap<String, String>> jsonlist = new ArrayList<HashMap<String, String>>();

    private static final String Name = "Name";
    private static final String Org = "Organising Committee";
    private static final String Hospital = "Hospital";
    private static final String Phone = "Phone";
    private static final String Start = "Start Date";
    private static final String End = "End Date";
    private static final String Stime = "Start Time";
    private static final String Etime = "End Time";
    private static final String Pass = "Password";
    private static final String Location = "Location";
    private static final String RegLink = "Reglink";
    private static final String Email = "Email";
    private static final String Details = "Details";
    private static final String Donations = "Donations";


    Camp_adp adp;
    LinearLayoutManager mLinearLayoutManager;

    int ct=0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("aaaaaa","Hello");
        adp=new Camp_adp(jsonlist,getContext());
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
        if(ct==0)
        {
            new getDataTask().execute("http://bloodbank-94437.onmodulus.net/api/status2");
            ct++;
        }

        return v;
    }

    class getDataTask extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog =new ProgressDialog(getContext());
            progressDialog.setMessage("Loading Data ...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            try{
                return getData(params[0]);
            }
            catch (Exception e)
            {
                return "Network Error";
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try
            {
                System.out.println(s);
                JSONArray arr = new JSONArray(s);
                for (int i = 0; i < arr.length(); i++) {
                    try {

                        JSONObject c = arr.getJSONObject(i);
                        String v1 = c.getString(Name);
                        String v2 = c.getString(Org);
                        String v3 = c.getString(Hospital);
                        String v4 = c.getString(Phone);
                        String v5 = c.getString(Start);
                        String v6 = c.getString(Stime);
                        String v7 = c.getString(Location);
                        String v8 = c.getString("_id");
                        String v9 = c.getString(End);

                        HashMap<String, String> map = new HashMap<String, String>();

                        map.put(Name, v1);
                        map.put(Org, v2);
                        map.put(Hospital, v3);
                        map.put(Phone, v4);
                        map.put(Start, v5);
                        map.put(Stime, v6);
                        map.put(Location, v7);
                        map.put("_id", v8);
                        map.put(End, v9);

                        String s7 = map.get(Start);
                        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        Date startDate = (Date)formatter.parse(s7);

                        Calendar cl = Calendar.getInstance();

                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        String formattedDate = df.format(cl.getTime());
                        Date todayDate = (Date)formatter.parse(formattedDate);

                        if(startDate.after(todayDate))
                        {
                            jsonlist.add(map);
                        }


                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }


            if(progressDialog!=null)
            {
                progressDialog.dismiss();
            }

            adp.notifyDataSetChanged();


        }
    }

    private String getData(String UrlPath) throws IOException {
        StringBuilder result = new StringBuilder();
        BufferedReader bufferedReader = null;

        String name;

        try
        {

            URL url = new URL(UrlPath);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(10000);
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while((line = bufferedReader.readLine()) != null)
            {
                result.append(line).append("\n");
            }

            String s = result.toString();

            return s;




        }finally {
            if(bufferedReader != null)
            {
                bufferedReader.close();
            }
        }
    }

}

