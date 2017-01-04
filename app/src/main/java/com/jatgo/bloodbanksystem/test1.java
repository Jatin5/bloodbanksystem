package com.jatgo.bloodbanksystem;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


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
import java.util.ArrayList;
import java.util.HashMap;

public class test1 extends AppCompatActivity {

    TextView t;
    Button b;
    ArrayList<HashMap<String, String>> jsonlist = new ArrayList<HashMap<String, String>>();
    ListView lv ;

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
    //     "Name": "BLOOD DONATION CAMP 1",
    //     "Organising Committee": "PGI Group",
    //     "Hospital": "PGI",
    //     "Phone": "8899008899",
    //     "Start Date": "2016-12-08",
    //     "End Date": "2016-12-10",
    //     "Start Time": "09:00",
    //     "End Time": "17:00",
    //     "Password": "camp1",
    //     "Location": "Chandigarh",
    //     "Reglink": "https://example.com/form.htm",
    //     "Email": "pgigroup@gmail.com",
    //     "Details": "All important details present here.",
    //     "Donations": "0"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        t = (TextView)findViewById(R.id.textView8);
        b = (Button)findViewById(R.id.bn);
        lv=(ListView)findViewById(R.id.list);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new postDataTask().execute("http://bloodbank2-94248.onmodulus.net/api/status3");
            }
        });
        new getDataTask().execute("http://bloodbank2-94248.onmodulus.net/api/status2");

    }

    class getDataTask extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog =new ProgressDialog(test1.this);
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
                        String v6 = c.getString(End);
                        String v7 = c.getString(Location);
                        String v8 = c.getString("_id");

                        HashMap<String, String> map = new HashMap<String, String>();

                        map.put(Name, v1);
                        map.put(Org, v2);
                        map.put(Hospital, v3);
                        map.put(Phone, v4);
                        map.put(Start, v5);
                        map.put(End, v6);
                        map.put(Location, v7);
                        map.put("id", v8);

                        jsonlist.add(map);
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

            ListAdapter adapter=new SimpleAdapter(test1.this,jsonlist,R.layout.listitem,new String[]{Name,Hospital,"id"},new int[]{R.id.textView3,R.id.textView4,R.id.textView5});
            //setListAdapter(adapter);

            lv.setAdapter(adapter);

        }
    }

    private String getData(String UrlPath) throws IOException{
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

    class postDataTask extends AsyncTask<String, Void, String>{

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog =new ProgressDialog(test1.this);
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

            //new getDataTask().execute("http://192.168.1.108:9000/api/status");
//            ListAdapter adapter=new SimpleAdapter(test1.this,jsonlist,R.layout.listitem,new String[]{Name,Likes,Comments},new int[]{R.id.textView3,R.id.textView4,R.id.textView5});
//            //setListAdapter(adapter);
//
//            lv.setAdapter(adapter);


        }
    }

    private void postData(String UrlPath) throws IOException, JSONException{
        StringBuilder result = new StringBuilder();
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;


        try
        {
            JSONObject obj = new JSONObject();
            obj.put("username","Jatin2");
            obj.put("password","Jatin2");

//            obj.put("FirsName", "Jatin");
//            obj.put("LastName", "Goyal");
//            obj.put("Phone", "7896541235");
//            obj.put("Email", "dfsf@afs.com");

            URL url = new URL(UrlPath);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            //urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(10000);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");


            Log.d("aaaaaaaaaa","Done1");


            OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
            wr.write(obj.toString());
            wr.flush();

            urlConnection.connect();
//            OutputStream outputStream = urlConnection.getOutputStream();
//            outputStream.write(obj.toString().getBytes("UTF-8"));
////            bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
////            bufferedWriter.write(obj.toString().getBytes("UTF-8"));
////            bufferedWriter.flush();
//            outputStream.close();

            String output1=urlConnection.getResponseMessage();
            Log.d("aaaaaaaaaa",obj.toString());
            Log.d("aaaaaaaaaa",output1);




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
