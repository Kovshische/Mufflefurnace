package com.example.android.mufflefurnace;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.android.mufflefurnace.Data.ProgramContract;

import static com.example.android.mufflefurnace.R.id.action_edit_program_name;

public class ProgramEditActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    /**
     * Content URI for the existing program (null if it's a new pet)
     */


    private static final int EXISTING_PROGRAM_ID_LOADER = 1;
    private static final int POINTS_LOADER = 2;

    PointCursorAdapter mPointCursorAdapter;
    private Uri mCurrentProgramUri;
    private String mCurrentProgramName;
    private int mCurrentProgramId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_edit);


        Intent intent = getIntent();
        mCurrentProgramUri = intent.getData();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add_point);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProgramEditActivity.this, AddPointActivity.class);
                intent.setData(mCurrentProgramUri);
                startActivity(intent);
            }
        });

       //get current  mCurrentProgramId;



        //find the ListView which will be populated with the program data
        ListView petListView = (ListView) findViewById(R.id.list_view_points);

        mPointCursorAdapter = new PointCursorAdapter(this, null);
        petListView.setAdapter(mPointCursorAdapter);

        getSupportLoaderManager().initLoader(EXISTING_PROGRAM_ID_LOADER, null, this);
    }



    private void initPointLoader() {
        getSupportLoaderManager().initLoader(POINTS_LOADER, null, this);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_program_edit, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case action_edit_program_name:

                Intent intent = new Intent(ProgramEditActivity.this, AddProgramActivity.class);
                intent.setData(mCurrentProgramUri);
                startActivity(intent);
                // insertPet();
                return true;


            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;
            case android.R.id.home:
                // Navigate back to parent activity (ProgramViewActivity)
                Intent intent1 = new Intent(ProgramEditActivity.this, ProgramViewActivity.class);
                intent1.setData(mCurrentProgramUri);
                NavUtils.navigateUpTo(this, intent1);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id ==  EXISTING_PROGRAM_ID_LOADER)
        {
            if (mCurrentProgramUri == null) {
                return null;
            }
            String[] projection = {
                    ProgramContract.ProgramEntry.COLUMN_PROGRAM_NAME
            };

            return new CursorLoader(this,
                    mCurrentProgramUri,
                    projection,
                    null,
                    null,
                    null
            );
        }


        if (id ==  POINTS_LOADER)
        {
            String[] projectionForPoint = {
                    ProgramContract.ProgramEntry._ID,
                    ProgramContract.ProgramEntry.COLUMN_TIME,
                    ProgramContract.ProgramEntry.COLUMN_TEMPERATURE
            };

            return new CursorLoader(
                    this,
                    ProgramContract.ProgramEntry.CONTENT_URI_POINTS,
                    projectionForPoint,
                    null,
                    null,
                    null
            );
        }
        else return null;
        }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        switch (loader.getId()) {
            case EXISTING_PROGRAM_ID_LOADER:
            // Bail early if the cursor is null or there is less than 1 row in the cursor
            if (cursor == null || cursor.getCount() < 1) {
                return;
            }
            if (cursor.moveToFirst()) {
                int currentProgramIDIndex = cursor.getColumnIndex(ProgramContract.ProgramEntry.COLUMN_PROGRAM_NAME);
                mCurrentProgramName = cursor.getString(currentProgramIDIndex);

                setTitle(mCurrentProgramName);
            }
            initPointLoader();
                break;



            case POINTS_LOADER:
               mPointCursorAdapter.swapCursor(cursor);
        }


    }
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mPointCursorAdapter.swapCursor(null);
    }
}
