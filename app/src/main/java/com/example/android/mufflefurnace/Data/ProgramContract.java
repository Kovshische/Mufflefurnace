package com.example.android.mufflefurnace.Data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by admin on 7/17/2017.
 */

public final class ProgramContract {

    //Constants to access
    public static final String CONTENT_AUTHORITY = "com.example.android.programs";
    public static final String PATH_PROGRAMS = "programs";
    public static final String PATH_POINTS = "points";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static abstract class ProgramEntry implements BaseColumns {

        public static final Uri CONTENT_URI_PROGRAMS = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PROGRAMS);
        public static final Uri CONTENT_URI_POINTS = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_POINTS);


        // Table Programs
        public static final String TABLE_PROGRAMS = "programs";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_PROGRAM_NAME = "program_name";
        public static final String COLUMN_CREATED_AT = "crated_at";
        public static final String COLUMN_UPDATED_AT = "updated_at";

        // Table Points
        public static final String TABLE_POINTS = "points";

        public static final String COLUMN_PROGRAM_ID = "program_id";
        public static final String COLUMN_POINT_NAME = "point_name";
        public static final String COLUMN_TEMPERATURE = "temperature";
        public static final String COLUMN_TIME = "time";

        /**
         * The MIME type of the {@link #CONTENT_URI_PROGRAMS} for a list of programs.
         */
        public static final String CONTENT_LIST_PROGRAMS_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY +"/" +PATH_PROGRAMS;

        /**
         * The MIME type of the {@link #CONTENT_URI_PROGRAMS} for a list of programs.
         */
        public static final String CONTENT_LIST_POINTS_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY +"/" +PATH_POINTS;

        /**
         * The MIME type of the {@link #CONTENT_URI_PROGRAMS} for a single program.
         */
        public static final  String CONTENT_ITEM_PROGRAM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY +"/" +PATH_PROGRAMS;

        /**
         * The MIME type of the {@link #CONTENT_URI_PROGRAMS} for a single pet.
         */
        public static final  String CONTENT_ITEM_POINT_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY +"/" +PATH_POINTS;

    }

}
