package com.jatgo.bloodbanksystem.Upcoming;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jatgo.bloodbanksystem.R;

import java.util.ArrayList;

/**
 * Created by Jatin on 12-09-2016.
 */
public class Camp_adp extends RecyclerView.Adapter<Camp_adp.ViewHolder> {

    public ArrayList<String> camps;
    Context context;

    public Camp_adp(ArrayList<String> camps, Context context) {
        this.camps = camps;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, null);
        ViewHolder rcv =new ViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.text.setText(camps.get(position));
    }

    @Override
    public int getItemCount() {
        return camps.size();
    }

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView text;


    public ViewHolder(View itemView) {
        super(itemView);

        text=(TextView)itemView.findViewById(R.id.textView);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();
    }


}
}