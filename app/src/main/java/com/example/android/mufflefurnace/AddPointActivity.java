package com.example.android.mufflefurnace;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.mufflefurnace.Data.ProgramContract;

public class AddPointActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private Uri mCurrentUri;
    private Uri mCurrentProgramUri;
    private Uri mCurrentPointUri;

    private int mCurrentProgramID;

    private EditText timeEditText;
    private EditText temperatureEditText;

    private static final String TIME_SEPARATOR = ":";
    private static int LENGTH_SEPARATOR = 1;

    TextView testTextView;

    private static final int EXISTING_PROGRAM_ID_LOADER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_point);

        setTitle(R.string.add_point_add_point);

        timeEditText = (EditText) findViewById(R.id.add_point_time);
        temperatureEditText = (EditText) findViewById(R.id.add_point_temperature);

        //Examine the intent that was used to launch this activity
        Intent intent = getIntent();
        mCurrentProgramUri = intent.getData();

        //Get current program ID




        //testing time to seconds

        testTextView = (TextView)findViewById(R.id.testTime);

        Button testButton = (Button) findViewById(R.id.testButton);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String time = timeEditText.getText().toString();
                //String timeString = timeEditText.getText().toString().trim();
                int timeInteger = timeToInteger(time);
                testTextView.setText(Integer.toString(timeInteger) );

            }
        });

        getSupportLoaderManager().initLoader(EXISTING_PROGRAM_ID_LOADER, null, this);

    }


    private void insertPoint(){
        //get data from the fields
        // Use trim to eliminate leading or trailing white space
        String temperatureString = temperatureEditText.getText().toString().trim();
        int temperatureInteger = Integer.parseInt(temperatureString);

        String timeString = timeEditText.getText().toString().trim();
        int timeInteger = timeToInteger(timeString);
    }

    private int timeToInteger (String timeString){
        int timeInSeconds;
        int hours;
        int minutes;
        String minutesString;

        if (timeString.contains(TIME_SEPARATOR)){
            int i = timeString.indexOf(TIME_SEPARATOR);
            Integer lengthTime = timeString.length();

            hours = Integer.parseInt(timeString.substring(0,i));

            minutesString = timeString.substring(i+LENGTH_SEPARATOR, lengthTime);

                    if (minutesString.contains(TIME_SEPARATOR)){
                        int ii = minutesString.indexOf(TIME_SEPARATOR);
                        minutes = Integer.parseInt(minutesString.substring(0,ii));
                    }

                    else {
                        minutes = Integer.parseInt(minutesString);
                    }

        }
        else {
            minutes = 0;
            hours = Integer.parseInt(timeString);
        }

        timeInSeconds = (hours*60)+minutes;

        return timeInSeconds;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save pet to the data base
  //              insertPoint();
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

/*
    private int getmCurrentProgramID (){

        //Examine the intent that was used to launch this activity
        Intent intent = getIntent();
        mCurrentProgramUri = intent.getData();

        String [] projection = {
                ProgramContract.ProgramEntry._ID
        };


        Cursor cursor = getContentResolver().query(
                mCurrentProgramUri,
                projection,
                null,
                null,
                null
        );

        mCurrentProgramID = cursor.getInt(cursor.getColumnIndexOrThrow(ProgramContract.ProgramEntry._ID));

        return mCurrentProgramID;

    }

*/
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (mCurrentProgramUri == null){
            return null;
        }
        String [] projection = {
                ProgramContract.ProgramEntry._ID
        };

        return new CursorLoader(this,
                mCurrentProgramUri,
                projection,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Bail early if the cursor is null or there is less than 1 row in the cursor
        if (cursor == null || cursor.getCount() <1){
            return;
        }
        if (cursor.moveToFirst()){
           int  currentProgramIDIndex = cursor.getColumnIndex(ProgramContract.ProgramEntry._ID);
            mCurrentProgramID = cursor.getInt(currentProgramIDIndex);

            setTitle(Integer.toString(mCurrentProgramID));
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}


