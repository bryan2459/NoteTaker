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
import com.example.bryanpc.notetaker.data.NoteProvider;
import com.example.bryanpc.notetaker.R;

import java.util.StringTokenizer;


public class UpdateNote extends Activity {
    private static final String LOG_TAG = UpdateNote.class.getSimpleName();
    ContentValues values;
    private boolean statusCheck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);
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

        Bundle extras = getIntent().getExtras();

        String idkeyList ="";
        String date ="";
        String noted = "";
        String done = "";
        if (extras != null) {
            idkeyList = extras.getString("idkey");
            date = extras.getString("date");
            noted = extras.getString("noted");
            done = extras.getString("done");
        }



        TextView textIdKey = (TextView)findViewById(R.id.idkey);
        textIdKey.setText(idkeyList);
        TextView textView1 = (TextView)findViewById(R.id.note_textview);
        // textView1.setText(noted+"*"+done+"*"+idkeyList);
        textView1.setText(noted);
        TextView textView2 = (TextView)findViewById(R.id.datetime_textview);
        textView2.setText(date);



        if (done.equals("*false*"))
        {
            CheckBox checkBox = (CheckBox)findViewById(R.id.checkDone);
            checkBox.setChecked(false);
        }
        if (done.equals("*true*"))
        {
            CheckBox checkBox = (CheckBox)findViewById(R.id.checkDone);
            checkBox.setChecked(true);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_update_note, menu);




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


    public void updateNote(View view)
    {
        String date_time = ((TextView) findViewById(R.id.datetime_textview)).getText().toString();

        String noted = ((TextView) findViewById(R.id.note_textview)).getText().toString().trim();

        String idKeyreq = ((TextView) findViewById(R.id.idkey)).getText().toString();

        String URL = "content://com.example.provider.NoteProv/notes/"+idKeyreq;


        Uri noteurl = Uri.parse(URL);
        values = new ContentValues();

        values.put(NoteProvider.DATEID,
                ((TextView) findViewById(R.id.datetime_textview)).getText().toString());
        if(isStatusCheck())
            values.put(NoteProvider.NOTESTATUS,"true");
        else {values.put(NoteProvider.NOTESTATUS,"false");}

        values.put(NoteProvider.NOTEDETAIL,
                ((EditText) findViewById(R.id.note_textview)).getText().toString());


        EditText noteDetail = (EditText)findViewById(R.id.note_textview);
        if( noteDetail.getText().toString().trim().length() == 0 )
            noteDetail.setError("Please write a note!");
        else
        if (noteDetail.getText().toString().length() > 84)
            noteDetail.setError( "Note is too long!" );
        else
         {
            int count = getContentResolver().update(noteurl, values, null, null);
            Toast.makeText(this, " note updated", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }



        if(isStatusCheck())
            Log.d("Update", "**Done= " + "true");
        else Log.d("Update","**Done= "+"false");
    }
    public void shareNote(View view) {
        boolean isChecked = ((CheckBox) findViewById(R.id.checkDone)).isChecked();


        String done="yet to be completed";
        if (isChecked)
        { done = "has been completed";}


        String date_time = ((TextView) findViewById(R.id.datetime_textview)).getText().toString();
        Log.d("Update", "date_time= " + date_time);

        String noted = ((TextView) findViewById(R.id.note_textview)).getText().toString();
        Log.d("Update", "Note= " + noted);

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "This note created: " + date_time + "\n" + noted + "\n" + done);
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "#From Note Taker");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }
    public void deleteNote(View view)
    {
        String idKeyreq = ((TextView) findViewById(R.id.idkey)).getText().toString();

        String URL = "content://com.example.provider.NoteProv/notes/"+idKeyreq;
        Uri noteurl = Uri.parse(URL);

        int count = getContentResolver().delete(
                noteurl, null, null);

        Toast.makeText(this, " note deleted", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public boolean isStatusCheck() {
        return statusCheck;
    }

    public void setStatusCheck(boolean statusCheck) {
        this.statusCheck = statusCheck;
    }
}
