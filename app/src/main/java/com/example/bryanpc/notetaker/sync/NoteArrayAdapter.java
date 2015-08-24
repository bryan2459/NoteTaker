package com.example.bryanpc.notetaker.sync;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bryanpc.notetaker.R;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class NoteArrayAdapter extends ArrayAdapter<String> {
    private static final String LOG_TAG = NoteArrayAdapter.class.getSimpleName();
    private final Context context;
    private String[] values;


    public NoteArrayAdapter(Context context, String[] values) {
        super(context, R.layout.all_notes, values);


        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        View rowView = inflater.inflate(R.layout.all_notes, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        TextView textView2 = (TextView) rowView.findViewById(R.id.label2);
        TextView textView1 = (TextView) rowView.findViewById(R.id.idkey);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
        String s = values[position];


        StringTokenizer st = new StringTokenizer(s, "*");
        String s2;
        String done="";
        while (st.hasMoreElements()) {

            s2 = st.nextToken();

            if (s2.equals("false"))
            {  imageView.setImageResource(R.drawable.ic_launcher_cross); done="false"; }
            if (s2.equals("true"))
            {   imageView.setImageResource(R.drawable.ic_launcher_pass); done="true";}

        }

        String s3;
        String id ="";
        String date ="";
        String noted ="";

        int i = 0;
        StringTokenizer st2 = new StringTokenizer(s, "|");
        while (st2.hasMoreElements()) {


            s3 = st2.nextToken();

            if (i==0) date = s3;
            if (i==1) id = s3;
            if (i==2) noted = s3;
            i++;

        }


        textView.setText(date);
        textView1.setText(id);
        textView2.setText(noted);


        return rowView;
    }

}