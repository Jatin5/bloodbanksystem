package com.jatgo.bloodbanksystem.Details;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jatgo.bloodbanksystem.LoginActivity;
import com.jatgo.bloodbanksystem.R;

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

public class Detail extends AppCompatActivity {


    ArrayList<HashMap<String, String>> jsonlist = new ArrayList<HashMap<String, String>>();
    int c=0, count;
    String did;

    Button b1;
    EditText e1, e2, e3, e4, e5;
    String s1, s2, s3, s4, s5;
    TextView t2, t3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        did= LoginActivity.id;

        new getDataTask().execute("http://bloodbank-94437.onmodulus.net/api/status/"+did);

        e1=(EditText)findViewById(R.id.e1);
        e2=(EditText)findViewById(R.id.e2);
        e3=(EditText)findViewById(R.id.e3);
        e4=(EditText)findViewById(R.id.e4);
        e5=(EditText)findViewById(R.id.e5);

        t2=(TextView)findViewById(R.id.t2);
        t3=(TextView)findViewById(R.id.t3);

        b1=(Button)findViewById(R.id.button1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new putDataTask().execute("http://bloodbank-94437.onmodulus.net/api/status/"+did);

                Toast.makeText(Detail.this,"Editted",Toast.LENGTH_LONG).show();
            }
        });

    }


    class getDataTask extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog =new ProgressDialog(Detail.this);
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
                String v10 = c.optString("DonationDate"+v3);

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
                map.put("DonationDate"+v3, v10);

                count = Integer.parseInt(v3);

                Log.d("ggggg",""+jsonlist.size());
                jsonlist.add(map);
                Log.d("ggggg",""+jsonlist.size());

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


            e1.setText(jsonlist.get(0).get("FirstName"));
            e5.setText(jsonlist.get(0).get("LastName"));
            e2.setText(jsonlist.get(0).get("Email"));
            e3.setText(jsonlist.get(0).get("Phone"));
            e4.setText(jsonlist.get(0).get("Blood Group"));

            t2.setText(jsonlist.get(0).get("DonationDate"+count));

        }
    }

    private String getData(String UrlPath) throws IOException {
        StringBuilder result = new StringBuilder();
        BufferedReader bufferedReader = null;

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

            progressDialog =new ProgressDialog(Detail.this);
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


        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;

        s1=e1.getText().toString();
        s2=e2.getText().toString();
        s3=e3.getText().toString();
        s4=e4.getText().toString();
        s5=e5.getText().toString();

        try
        {
            JSONObject obj = new JSONObject();
            obj.put("FirstName",s1);
            obj.put("LastName",s5);
            obj.put("Email",s2);
            obj.put("Phone",s3);
            obj.put("Blood Group",s4);


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
