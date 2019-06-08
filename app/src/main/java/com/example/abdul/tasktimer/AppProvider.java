package com.example.abdul.tasktimer;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public class AppProvider extends ContentProvider {

    private static final String TAG = "AppProvider";
    private AppDatabase openHelper;
    private static final UriMatcher uriMatcher = buildUriMatcher();
    static final String CONTENT_AUTHORITY = "com.example.abdul.tasktimer.provider";
    public static final Uri CONTENT_AUTHORITY_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private static final int TASKS = 100;
    private static final int TASKS_ID = 101;
    private static final int TIMINGS = 200;
    private static final int TIMINGS_ID = 201;

    /*
    private static final int TASKS_DURATION = 300;
    private static final int TASKS_DURATION_ID = 301;
    */

    private static final int TASKS_DURATION = 400;
    private static final int TASKS_DURATION_ID = 401;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(CONTENT_AUTHORITY, TasksContract.TABLE_NAME, TASKS);
        matcher.addURI(CONTENT_AUTHORITY, TasksContract.TABLE_NAME + "/#", TASKS_ID);
//        matcher.addURI(CONTENT_AUTHORITY, TimingContract.TABLE_NAME, TIMINGS);
//        matcher.addURI(CONTENT_AUTHORITY, TimingContract.TABLE_NAME + "/#", TIMINGS_ID);
//        matcher.addURI(CONTENT_AUTHORITY, DurationContract.TABLE_NAME, TASKS_DURATION);
//        matcher.addURI(CONTENT_AUTHORITY, DurationContract.TABLE_NAME + "/#", TASKS_DURATION_ID);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        openHelper = AppDatabase.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.d(TAG, "query: uri -> " + uri);
        final int match = uriMatcher.match(uri);
        Log.d(TAG, "query: match -> " + match);
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        switch(match) {
            case TASKS:
                queryBuilder.setTables(TasksContract.TABLE_NAME);
                break;
            case TASKS_ID:
                queryBuilder.setTables(TasksContract.TABLE_NAME);
                long taskID = TasksContract.getTaskID(uri);
                queryBuilder.appendWhere(TasksContract.Columns._ID + "=" + taskID);
                break;
//            case TIMINGS:
//                queryBuilder.setTables(TimingsContract.TABLE_NAME);
//                break;
//            case TIMINGS_ID:
//                queryBuilder.setTables(TimingsContract.TABLE_NAME);
//                long timingID = TimingsContract.getTimingID(uri);
//                queryBuilder.appendWhere(TimingsContract.Columns._ID + "=" + timingID);
//                break;
//            case TASKS_DURATION:
//                queryBuilder.setTables(DurationsContract.TABLE_NAME);
//                break;
//            case TASKS_DURATION_ID:
//                queryBuilder.setTables(DurationsContract.TABLE_NAME);
//                long durationID = DurationsContract.getDurationID(uri);
//                queryBuilder.appendWhere(DurationsContract.Columns._ID + "=" + durationID);
//                break;
            default:
                throw new IllegalArgumentException("Unknown URI + " + uri);
        }
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

}
