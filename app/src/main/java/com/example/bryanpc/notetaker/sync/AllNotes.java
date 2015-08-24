package com.example.bryanpc.notetaker.sync;


import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.StringTokenizer;

import com.example.bryanpc.notetaker.R;
import com.example.bryanpc.notetaker.data.NoteProvider;

public class AllNotes extends ListActivity {

    private static final String LOG_TAG = AllNotes.class.getSimpleName();
    String[] NOTE_ARRAY =
            new String[] { };

    static final ArrayList<String> list = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String find_query = prefs.getString(getString(R.string.pref_find_key),
                getString(R.string.pref_find_default));


        String URL = "content://com.example.provider.NoteProv/notes";
        Uri notes = Uri.parse(URL);

        Cursor c;
        if(find_query != null)
            c = getContentResolver().query(notes, null, find_query, null, null);
        else
            c = getContentResolver().query(notes, null, null, null, null);

        String result = "";
        list.clear();


        if (!c.moveToFirst()) {
            Toast.makeText(this, result + " no content yet!", Toast.LENGTH_LONG).show();
        } else {
            do {
                result = "|" + c.getString(c.getColumnIndex(NoteProvider.DATEID)) +
                        "|" + c.getString(c.getColumnIndex(NoteProvider.ID)) + "|" +
                        "|" + c.getString(c.getColumnIndex(NoteProvider.NOTEDETAIL)) + "|" +
                        "*" + c.getString(c.getColumnIndex(NoteProvider.NOTESTATUS))+"*";
                list.add(result);



            } while (c.moveToNext());

        }
        NOTE_ARRAY = list.toArray(NOTE_ARRAY);


        setListAdapter(new NoteArrayAdapter(this, NOTE_ARRAY));


    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        //get selected items
        String selectedValue = (String) getListAdapter().getItem(position);

        String s3;
        String idkey ="";
        String date ="";
        String noted ="";
        String done = "";

        int i = 0;
        StringTokenizer st2 = new StringTokenizer(selectedValue, "|");
        while (st2.hasMoreElements()) {


            s3 = st2.nextToken();

            if (i==0) date = s3;
            if (i==1) idkey = s3;
            if (i==2) noted = s3;
            if (i==3) done = s3;
            i++;

        }

        Intent intent = new Intent(this, UpdateNote.class);
        intent.putExtra("idkey",idkey);
        intent.putExtra("date",date);
        intent.putExtra("noted",noted);
        intent.putExtra("done",done);
        startActivity(intent);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }


}

