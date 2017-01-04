package com.jatgo.bloodbanksystem;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    ArrayList<HashMap<String, String>> personList;
    String myJSON;
    public static String id , bg;
    public static int count;
    int c=0;
    JSONArray peoples = null;
    SharedPreferences sharedpreferences;
    ArrayList<HashMap<String, String>> jsonlist = new ArrayList<HashMap<String, String>>();
    private static final boolean AUTO_HIDE = true;
    private static final int AUTO_HIDE_DELAY_MILLIS = 0;
    private static final int UI_ANIMATION_DELAY = 300;

    private final Handler mHideHandler = new Handler();
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
        }
    };
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
            }
        }
    };
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        sharedpreferences=getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor=sharedpreferences.edit();
        editor.putString("email","test@test.com");
        editor.putString("pass","test");
        editor.commit();

        new getDataTask().execute("http://bloodbank-94437.onmodulus.net/api/status");

        Button btn = (Button)findViewById(R.id.buttonlogin);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getData();
                String email = sharedpreferences.getString("email",null);
                String pass = sharedpreferences.getString("pass",null);

                EditText et1= (EditText)findViewById(R.id.et1);
                EditText et2= (EditText)findViewById(R.id.et2);

                String s1=et1.getText().toString();
                String s2=et2.getText().toString();

                Log.d("aaaaaaaaaaa","One");
                int i=0;
                try {

                    if(email.equals(s1)&&pass.equals(s2))
                    {

                        id="5843107c9be7b6581e00002a";
                        count=0;
                        bg="B+";
                        Intent j =new Intent(LoginActivity.this,Home.class);
                        startActivity(j);
                    }
                    else {

                        while (c != 1) {
                            Log.d("aaaaaaaaaaa","Atak Gaya");
                        }

                        int r = 0;

                        for (i = 0; i < jsonlist.size(); i++) {
                            Log.d("aaaaaaaaaaa", "Three");
                            if (jsonlist.get(i).get("Email").equals(s1) && jsonlist.get(i).get("Password").equals(s2)) {
                                r=1;
                                Log.d("qqqqqqqq",jsonlist.get(i).get("Email")+" "+jsonlist.get(i).get("Password"));
                                id = jsonlist.get(i).get("_id");
                                bg = jsonlist.get(i).get("Blood Group");
                                count = Integer.parseInt(jsonlist.get(i).get("Count"));
                                Intent j = new Intent(LoginActivity.this, Home.class);
                                startActivity(j);
                                Log.d("aaaaaaaaaaa", "Two");
                                break;
                            }

                        }
                        if(r==0)
                        {
                            et2.setText("");
                            Toast.makeText(LoginActivity.this,"Incorrect Username or Password",Toast.LENGTH_LONG).show();

                        }
                    }

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        });
    }


    class getDataTask extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog =new ProgressDialog(LoginActivity.this);
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
                        String v1 = c.getString("Email");
                        String v2 = c.getString("Password");
                        String v3 = c.getString("Count");
                        String v4 = c.getString("_id");

                        Log.d("oooooo",v4);

                        HashMap<String, String> map = new HashMap<String, String>();

                        map.put("Email", v1);
                        map.put("Password", v2);
                        map.put("Count", v3);
                        map.put("_id", v4);


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

            c=1;

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

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        delayedHide(0);
    }

    private void hide() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

}
