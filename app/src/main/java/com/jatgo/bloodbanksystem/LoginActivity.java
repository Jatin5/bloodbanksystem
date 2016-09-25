package com.jatgo.bloodbanksystem;


import android.annotation.SuppressLint;
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

import com.jatgo.bloodbanksystem.Home;
import com.jatgo.bloodbanksystem.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    ArrayList<HashMap<String, String>> personList;
    String myJSON;
    JSONArray peoples = null;
    SharedPreferences sharedpreferences;
    private static final String TAG_RESULTS="result";
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


                if(email.equals(s1)&&pass.equals(s2))
                {
                    Intent i =new Intent(LoginActivity.this,Home.class);
                    startActivity(i);
                }
                else
                {
                    et2.setText("");
                    Toast.makeText(LoginActivity.this,"Incorrect Username or Password",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public void getData(){
        /*
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                HttpPost httppost = new HttpPost("");

                InputStream inputStream = null;
                String result = null;
                try {
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();

                    inputStream = entity.getContent();
                    // json is UTF-8 by default
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (Exception e) {
                    // Oops
                }
                finally {
                    try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result){
                myJSON=result;
                int flag = 0;
                try {
                    final EditText et1 = (EditText)findViewById(R.id.et1);
                    final EditText et2 = (EditText)findViewById(R.id.et2);
                    JSONObject jsonObj = new JSONObject(myJSON);
                    peoples = jsonObj.getJSONArray(TAG_RESULTS);
                    for (int i = 0; i < peoples.length(); i++) {
                        JSONObject c = peoples.getJSONObject(i);
                        if(et1.getText().toString().equals(c.getString("Email")) && et2.getText().toString().equals(c.getString("Password"))){
                            flag = 1;
                            sharedpreferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString("Email", c.getString("Email"));
                            editor.putString("Name", c.getString("Name"));
                            editor.putString("Phone", c.getString("Phone"));
                            editor.putString("BloodGroup", c.getString("BloodGroup"));
                            editor.commit();
                            break;
                        }else{
                            flag =0;
                        }
                    }
                    if(flag == 1){
                        Intent i = new Intent(LoginActivity.this,Home.class);
                        startActivity(i);
                    }else{
                        Toast.makeText(getApplicationContext(), "Not Registered", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e){
                    Log.e("log_tag","Error during Login: " + e.toString());
                }
            }
        }

        GetDataJSON g = new GetDataJSON();
        g.execute();
        */




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