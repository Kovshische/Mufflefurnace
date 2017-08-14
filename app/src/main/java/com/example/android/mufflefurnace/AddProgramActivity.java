package com.example.android.mufflefurnace;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.mufflefurnace.Data.ProgramContract;
import com.example.android.mufflefurnace.Data.ProgramDbHelper;


public class AddProgramActivity extends AppCompatActivity {

    private static String LOG_TAG =AddProgramActivity.class.getSimpleName();

    private String addProgramMessage;

    /**
     * EditText field to enter the program's name
     */
    private EditText mProgramName;

    private ProgramDbHelper mDbHelper = new ProgramDbHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_program);

        // Find all relevant views that we will need to read user input from
        mProgramName =(EditText) findViewById(R.id.edit_pet_name);

    }

    //Show toast
    void displayToast(String text) {

        Toast toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
        toast.show();
        Log.i(LOG_TAG, "toast displayed");
        // toast.setGravity(Gravity.BOTTOM,0,0);
    }

    private void insertProgram (){
        String nameString = mProgramName.getText().toString().trim();

        ContentValues values = new ContentValues();
        values.put(ProgramContract.ProgramEntry.COLUMN_PROGRAM_NAME, nameString);

        Uri newUri = getContentResolver().insert(ProgramContract.ProgramEntry.CONTENT_URI_PROGRAMS, values);

        if (newUri == null){
            //If the  new content URI is null, then there was an error with insertion
            displayToast("Error with saving program");
            Log.i (LOG_TAG,"Error with saving program");
        } else {
            addProgramMessage = "Program saved successful";
            displayToast(addProgramMessage);
            Log.i(LOG_TAG, "New row is " + newUri.toString());
        }
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
                insertProgram();
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

}
