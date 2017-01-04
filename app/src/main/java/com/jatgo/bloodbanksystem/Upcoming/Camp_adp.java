package com.jatgo.bloodbanksystem.Upcoming;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.jatgo.bloodbanksystem.Details.Detail;
import com.jatgo.bloodbanksystem.Home;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by Jatin on 12-09-2016.
 */

public class Camp_adp extends RecyclerView.Adapter<Camp_adp.ViewHolder> {

    ArrayList<HashMap<String, String>> jsonlist = new ArrayList<HashMap<String, String>>();
    public ArrayList<HashMap<String, String>> camps = new ArrayList<HashMap<String, String>>();
    Context context;
    String Did, Cid, cc, dd;
    int pos=0;
    public static int count = 0;
    int c=0;

    static int flag = 0;
    String tm;
    String dt;

    public Camp_adp(ArrayList<HashMap<String, String>> camps, Context context) {
        this.camps = camps;
        this.context = context;

        Did = LoginActivity.id;
        new getDataTask().execute("http://bloodbank-94437.onmodulus.net/api/status/"+Did);


        Log.d("ppppp",Did +"        "+count);

        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        dt = df.format(c.getTime());


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, null);
        ViewHolder rcv =new ViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        pos = position;

        holder.text1.setText(camps.get(position).get("Name"));
        holder.text2.setText(camps.get(position).get("Hospital"));
        holder.text3.setText(camps.get(position).get("Start Date").substring(0,10));
        holder.text4.setText(camps.get(position).get("End Date").substring(0,10));

    }

    @Override
    public int getItemCount() {
        return camps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView text1;
        public TextView text2;
        public TextView text3;
        public TextView text4;
        public EditText e1,e2;

        Button b1;

        public ViewHolder(View itemView) {
            super(itemView);

            text1=(TextView)itemView.findViewById(R.id.textView);
            text2=(TextView)itemView.findViewById(R.id.textView2);
            text3=(TextView)itemView.findViewById(R.id.textView3);
            text4=(TextView)itemView.findViewById(R.id.textView33);
            e1=(EditText)itemView.findViewById(R.id.e1);
            e2=(EditText)itemView.findViewById(R.id.e2);
            b1=(Button)itemView.findViewById(R.id.bn);




            e1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        final TimePicker timePicker = new TimePicker(context);
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

                                int hour, minutes;

                                if (Build.VERSION.SDK_INT >= 23 )
                                {
                                    hour = timePicker.getHour();
                                    minutes = timePicker.getMinute();
                                }
                                else
                                {
                                    hour = timePicker.getCurrentHour();
                                    minutes = timePicker.getCurrentMinute();
                                }

                                e1.setText(hour + "-" + (minutes));
                                tm = hour + "-" + (minutes);

                            }
                        });
                        builder.show();
                    }
                }
            });

            e2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        final DatePicker datePicker = new DatePicker(context);
                        builder.setTitle("Set Time");
                        builder.setView(datePicker);
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                int day, month, year;

                                day = datePicker.getDayOfMonth();
                                month = datePicker.getMonth()+1;
                                year = datePicker.getYear();

                                e2.setText(year + "-" + month + "-"+ day);
                                dt = year + "-" + month + "-"+ day;
                            }
                        });
                        builder.show();
                    }
                }
            });



            b1.setOnClickListener(new View.OnClickListener() {



                Date startDate, endDate, todayDate, lastDate;

                @Override
                public void onClick(View view) {

                    try{

                        String s7 = camps.get(pos).get("Start Date");
                        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        startDate = (Date)formatter.parse(s7);

                        String s8 = camps.get(pos).get("End Date");
                        endDate = (Date)formatter.parse(s8);
                        todayDate = (Date)formatter.parse(dt);

                        String s9 = jsonlist.get(0).get("DonationDate"+count);
                        lastDate = (Date)formatter.parse(s9);
                        Log.d("pppppppp",lastDate.toString());
                        lastDate.setMonth(lastDate.getMonth()+3);
                        Log.d("pppppppp",lastDate.toString());
                    }
                    catch (ParseException e)
                    {
                        e.printStackTrace();
                    }

                    Log.d("eeeee",e1.getText() + " "+ e2.getText());
                    if(flag==0)
                    {

                        if(e1.getText().length()<3  || e2.getText().length()<5 || startDate.after(todayDate) || endDate.before(todayDate))
                        {
                            Toast.makeText(context,"Please enter valid date and time",Toast.LENGTH_LONG).show();
                        }
                        else if (startDate.before(lastDate))
                        {
                            Toast.makeText(context, "Too Early to make another donation", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Cid = camps.get(getPosition()).get("_id");

                            new putDataTask().execute("http://bloodbank-94437.onmodulus.net/api/status/"+Did);

                            Log.d("ppppp","http://bloodbank2-94266.onmodulus.net/api/status/"+Did);

                            b1.setText("Booked");
                            b1.setClickable(false);
                            b1.setBackgroundColor(Color.parseColor("#90b42a"));

                        }

                    }
                    else
                    {

                        if(e1.getText().length()<3  || e2.getText().length()<5 || startDate.after(todayDate) || endDate.before(todayDate))
                        {
                            Toast.makeText(context,"Please enter valid date and time",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Cid = camps.get(getPosition()).get("_id");

                            new putDataTask().execute("http://bloodbank-94437.onmodulus.net/api/status/"+Did);

                            Log.d("ppppp","http://bloodbank2-94266.onmodulus.net/api/status/"+Did);

                            b1.setText("Booked");
                            b1.setClickable(false);
                            b1.setBackgroundColor(Color.parseColor("#90b42a"));

                            flag = 0;

                        }

                    }

                }
            });

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT)
//                            .show();
//
//                }
//            });
        }
    }


    class putDataTask extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog =new ProgressDialog(context);
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
        int cc = count+1;
        count=count+1;
        LoginActivity.count+=1;
        Log.d("ppppppp","Count is "+count+" and cc is "+cc);
        try
        {
            JSONObject obj = new JSONObject();
            obj.put("DonationId"+count, Cid);
            obj.put("DonationDate"+count, dt);
            obj.put("Count", count);
            obj.put("DR"+count, "0");
            obj.put("Time"+count, tm);


            URL url = new URL(UrlPath);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(10000);
            urlConnection.setRequestMethod("PUT");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.connect();

            Log.d("aaaaaaaaaa","Done1");

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

    class getDataTask extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog =new ProgressDialog(context);
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
                String v9 = c.optString("DonationId"+v3);
                String v10 = c.optString("DonationDate"+v3);

                int ctr = Integer.parseInt(v3);

                while(v9=="")
                {
                    if(ctr<=0)
                    {
                        flag = 1;
                        break;
                    }
                    v9=c.optString("DonationId"+ctr);
                    v10=c.optString("DonationDate"+ctr);
                    ctr--;
                }


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
                map.put("DonationId"+v3, v9);
                map.put("DonationDate"+v3, v10);

                String sss = v10;
                Log.d("ggggg",""+jsonlist.size() + "   "+ map.get("DonationDate"+v3) + " " + v10);
                jsonlist.add(map);
                Log.d("ggggg",""+jsonlist.size() + "   "+ jsonlist.get(0).get("DonationDate"+v3) + " " + v10);


                cc=v9;
                dd=v10;

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

            try
            {
                count = Integer.parseInt(jsonlist.get(0).get("Count"));
                Log.d("pppppppppp",Did +"        "+count);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

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