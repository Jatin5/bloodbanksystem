package com.jatgo.bloodbanksystem.Donate;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.jatgo.bloodbanksystem.Home;
import com.jatgo.bloodbanksystem.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Jatin on 24-09-2016.
 */
public class DonateFrag extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.content_donate_blood,container,false);


/*
        sharedpreferences = getContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
*/
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        date = (EditText)v.findViewById(R.id.editText5);
        date.setInputType(InputType.TYPE_NULL);
        date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    final DatePicker datePicker = new DatePicker(getContext());
                    builder.setTitle("Set Date");
                    builder.setView(datePicker);
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int year = datePicker.getYear();
                            int month = datePicker.getMonth();
                            int day = datePicker.getDayOfMonth();
                            date.setText(year + "-" + (month+1) + "-" + day);
                        }
                    });
                    builder.show();
                }
            }
        });

        time = (EditText)v.findViewById(R.id.editText6);
        time.setInputType(InputType.TYPE_NULL);


        time.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    final TimePicker timePicker = new TimePicker(getContext());
                    builder.setTitle("Set Time");
                    builder.setView(timePicker);
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int hour = timePicker.getHour();
                            int min = timePicker.getMinute();
                            time.setText(hour + "-" + (min));
                        }
                    });
                    builder.show();
                }
            }
        });

        Button btn = (Button)v.findViewById(R.id.button22);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createEvent(v);
            }
        });


        return v;
    }

    private SimpleDateFormat dateFormatter;
    public SharedPreferences sharedpreferences;
    EditText date,time;


    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getContext(), Home.class);
        startActivity(myIntent);
        return true;

    }

    public void createEvent(View v) {
        String result = null;
        InputStream is = null;
        EditText editText1 = (EditText)v.findViewById(R.id.editText1);
        String v1 = editText1.getText().toString();
        EditText editText2 = (EditText)v.findViewById(R.id.editText2);
        String v2 = editText2.getText().toString();
        EditText editText3 = (EditText)v.findViewById(R.id.editText3);
        String v3 = editText3.getText().toString();
        EditText editText4 = (EditText)v.findViewById(R.id.editText4);
        String v4 = editText4.getText().toString();
        EditText editText5 = (EditText)v.findViewById(R.id.editText5);
        String v5 = editText5.getText().toString();
        EditText editText6 = (EditText)v.findViewById(R.id.editText6);
        String v6 = editText6.getText().toString();
        if(v1.equals("") || v2.equals("") || v3.equals("") || v4.equals("") || v5.equals("") || v6.equals("")){
            Toast.makeText(getActivity(), "Fill In All Details Properly", Toast.LENGTH_SHORT).show();
        }else {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
/*
            sharedpreferences = getActivity().getSharedPreferences("MyPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
*/
            nameValuePairs.add(new BasicNameValuePair("f1", v1));
            nameValuePairs.add(new BasicNameValuePair("f2", v2));
            nameValuePairs.add(new BasicNameValuePair("f3", v3));
            nameValuePairs.add(new BasicNameValuePair("f4", v4));
            nameValuePairs.add(new BasicNameValuePair("f5", v5));
            nameValuePairs.add(new BasicNameValuePair("f6", v6));
            StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.LAX);
            //http post
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("");
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();

                Log.i("log_tag", "connection success ");
            } catch (Exception e) {
                Log.i("log_tag", "Error in http connection " + e.toString());
                Log.e("log_tag", "Error in http connection " + e.toString());
                Toast.makeText(getContext(), "Connection fail", Toast.LENGTH_SHORT).show();

            }

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                    Intent i = new Intent(getContext(), Home.class);
                    startActivity(i);
                }
                is.close();
                result = sb.toString();
            } catch (Exception e) {
                Log.e("log_tag", "Error " + e.toString());
            }

            try {

                JSONObject json_data = new JSONObject(result);
                CharSequence w = (CharSequence) json_data.get("re");
                Toast.makeText(getContext(), "Slot Booked", Toast.LENGTH_SHORT).show();
                Log.i("log_tag", "Slot Booked");
                Intent i = new Intent(getContext(), Home.class);
                startActivity(i);

            } catch (JSONException e) {
                Toast.makeText(getContext(), "Error Occurred", Toast.LENGTH_SHORT).show();
                Log.i("log_tag", "Error " + e.toString());
                Log.e("log_tag", "Error " + e.toString());

            }
            Intent i = new Intent(getContext(), Home.class);
            startActivity(i);
        }
    }

}
