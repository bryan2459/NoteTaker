package com.example.bryanpc.notetaker.sync;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;

import com.example.bryanpc.notetaker.R;
import com.example.bryanpc.notetaker.data.NoteProvider;

public class AddNote extends Activity {

    private static final String LOG_TAG = AddNote.class.getSimpleName();
    private boolean statusCheck;
    ContentValues values;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String currentDateTimeString = "  "+DateFormat.getDateTimeInstance().format(new Date());

        setContentView(R.layout.activity_add_note);


        ((TextView) findViewById(R.id.datetime_textview)).setText(currentDateTimeString);
        final CheckBox checkbox = (CheckBox) findViewById(R.id.checkDone);


        final int code = 0;
        checkbox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Show Toast message indicating the CheckBox's Checked state
                if (((CheckBox) v).isChecked()) {
                    setStatusCheck(true);
                } else {
                    setStatusCheck(false);
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void addBirthday(View view) {

        values = new ContentValues();

        values.put(NoteProvider.DATEID,
                ((TextView) findViewById(R.id.datetime_textview)).getText().toString());


        final CheckBox checkbox = (CheckBox) findViewById(R.id.checkDone);
        final int code = 0;
        checkbox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Show Toast message indicating the CheckBox's Checked state
                if (((CheckBox) v).isChecked()) {

                    setStatusCheck(true);
                } else {

                    setStatusCheck(false);
                }
            }
        });


        if(isStatusCheck())
            values.put(NoteProvider.NOTESTATUS,"true");
        else {values.put(NoteProvider.NOTESTATUS,"false");}

        EditText noteDetail = (EditText)findViewById(R.id.note_textview);
        if( noteDetail.getText().toString().trim().length() == 0 )
            noteDetail.setError( "Please write a note!" );
        else
        if (noteDetail.getText().toString().length() > 84)
            noteDetail.setError( "Note is too long!" );
        else
            {
            values.put(NoteProvider.NOTEDETAIL,
                    ((EditText) findViewById(R.id.note_textview)).getText().toString().trim());


            Uri uri = getContentResolver().insert(
                    NoteProvider.CONTENT_URI, values);


            Toast.makeText(this, " a note has been added", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            }
    }

    public boolean isStatusCheck() {
        return statusCheck;
    }

    public void setStatusCheck(boolean statusCheck) {
        this.statusCheck = statusCheck;
    }
}
