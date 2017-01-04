package com.jatgo.bloodbanksystem.Request;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jatgo.bloodbanksystem.LoginActivity;
import com.jatgo.bloodbanksystem.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Jatin on 23-09-2016.
 */
public class ReqFrag extends Fragment {

    ArrayList<HashMap<String, String>> jsonlist = new ArrayList<HashMap<String, String>>();
    int c=0, count;
    String did;

    TextView t1, t2, t3, t4;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Button b1;

        did= LoginActivity.id;

        View v=inflater.inflate(R.layout.fragment_request_blood,container,false);

        new getDataTask().execute("http://bloodbank-94437.onmodulus.net/api/status/"+did);

        Log.d("ggggg","http://bloodbank-94437.onmodulus.net/api/status/"+did);

//        while(c!=1){
//
//        }


        count = LoginActivity.count;

        t1 =(TextView)v.findViewById(R.id.t1);
        t2 =(TextView)v.findViewById(R.id.t2);
        t3 =(TextView)v.findViewById(R.id.t3);
        t4 =(TextView)v.findViewById(R.id.t4);



        b1=(Button)v.findViewById(R.id.b22);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new putDataTask().execute("http://bloodbank-94437.onmodulus.net/api/status/"+did);
                Toast.makeText(getContext(),"Request sent",Toast.LENGTH_LONG).show();
            }
        });

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
                JSONObject c = new JSONObject(s);
                String v1 = c.getString("Email");
                String v2 = c.getString("Password");
                String v3 = c.getString("Count");
                String v4 = c.getString("_id");
                String v5 = c.getString("Phone");
                String v6 = c.getString("Blood Group");
                String v7 = c.getString("FirstName");
                String v8 = c.getString("LastName");

                Log.d("oooooo",v4);

                HashMap<String, String> map = new HashMap<String, String>();

                map.put("Email", v1);
                map.put("Password", v2);
                map.put("Count", v3);
                map.put("_id", v4);
                map.put("Phone", v5);
                map.put("Blood Group", v6);
                map.put("FirstName", v7);
                map.put("LastName", v8);

                Log.d("ggggg",""+jsonlist.size());
                jsonlist.add(map);
                Log.d("ggggg",""+jsonlist.size());

//                JSONArray arr = new JSONArray(s);
//                for (int i = 0; i < arr.length(); i++) {
//                    try {
//
//
//                    }
//                    catch(Exception e)
//                    {
//                        e.printStackTrace();
//                    }
//                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }


            if(progressDialog!=null)
            {
                progressDialog.dismiss();
            }

            c=1;

            t1.setText(jsonlist.get(0).get("FirstName")+" "+jsonlist.get(0).get("LastName"));
            t2.setText(jsonlist.get(0).get("Phone"));
            t3.setText(jsonlist.get(0).get("Email"));
            t4.setText(jsonlist.get(0).get("Blood Group"));


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

    class putDataTask extends AsyncTask<String, Void, String> {

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
                postData(params[0]);
                return "Done";
            }
            catch (IOException e)
            {
                return "Network Error";
            }
            catch (JSONException k)
            {
                return("Data Invalid");
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            if(progressDialog!=null)
            {
                progressDialog.dismiss();
            }



        }
    }

    private void postData(String UrlPath) throws IOException, JSONException{


        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c.getTime());


        SimpleDateFormat df2 = new SimpleDateFormat("HH-mm-ss");
        String formattedtime = df2.format(c.getTime());

        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        count = LoginActivity.count;
        int cc = count+1;
        count=count+1;
        Log.d("ppppppp","Count is "+count+" and cc is "+cc);
        try
        {
            JSONObject obj = new JSONObject();
            obj.put("Count", count);
            obj.put("DonationDate"+count, formattedDate);
            obj.put("DR"+count, "1");
            obj.put("Time"+count, formattedtime);


            URL url = new URL(UrlPath);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(10000);
            urlConnection.setRequestMethod("PUT");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.connect();


            OutputStream outputStream = urlConnection.getOutputStream();
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            bufferedWriter.write(obj.toString());
            bufferedWriter.flush();


            String output1=urlConnection.getResponseMessage();
            int output2=urlConnection.getResponseCode();

            Log.d("aaaaaaaaaa",obj.toString());
            Log.d("aaaaaaaaaa",output1 + " - " + output2);




        }
        catch (Exception e) {

            Log.d("aaaaaaaaaaaaaaa","exception aa gayi");
            Log.d("aaaaaaaaaaa",e.getMessage());
//            Toast.makeText(test1.this, "Error", Toast.LENGTH_LONG).show();
            e.printStackTrace();

        }finally{
            if(bufferedReader != null)
            {
                bufferedReader.close();
            }
            if(bufferedWriter != null)
            {
                bufferedWriter.close();
            }
        }

//        return result.toString();

    }

}
