package com.example.android.mufflefurnace;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.mufflefurnace.Data.ProgramContract;

/**
 * Created by admin on 7/21/2017.
 */

public class PointCursorAdapter extends CursorAdapter {

    public PointCursorAdapter(Context context,Cursor cursor){
        super(context, cursor, 0);
    }




    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_point, parent,false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

        TextView textViewTime  = (TextView) view.findViewById(R.id.time);
        TextView textViewTemperature = (TextView) view.findViewById(R.id.temperature);

        //Extract properties from cursor
        int time = cursor.getInt(cursor.getColumnIndexOrThrow(ProgramContract.ProgramEntry.COLUMN_TIME));
        int temperature  = cursor.getInt(cursor.getColumnIndexOrThrow(ProgramContract.ProgramEntry.COLUMN_TEMPERATURE));

        String timeString = Integer.toString(time);
        String temperatureString = Integer.toString(temperature);

        final int program_id = cursor.getInt(cursor.getColumnIndexOrThrow(ProgramContract.ProgramEntry._ID));


        textViewTime.setText(timeString);
        textViewTemperature.setText(temperatureString);
        // textViewCreated.setText(temperature);


    }



}
