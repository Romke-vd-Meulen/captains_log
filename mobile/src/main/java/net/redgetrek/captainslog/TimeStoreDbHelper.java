package net.redgetrek.captainslog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Data helper for timing data
 */
public class TimeStoreDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "TimeStore.db";
    public static final String TABLE_NAME = "TimeStore";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE  " + TABLE_NAME + " ( id INTEGER PRIMARY KEY, time_started DATETIME, time_stopped DATETIME, description TEXT )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public TimeStoreDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    /**
     * Gives the latest entry in the database, or NULL if not found
     * @return TimeStoreEntry|null
     */
    public TimeStoreEntry getLatestEntry() {
        Cursor c = getReadableDatabase().query(
                TABLE_NAME,         // The table to query
                null,               // Return all columns
                null,               // The columns for the WHERE clause
                null,               // The values for the WHERE clause
                null,               // don't group the rows
                null,               // don't filter by row groups
                "id DESC",          // The sort order
                "1"                 // Limit
        );
        if (c.getCount() == 0){
            return null;
        }
        c.moveToFirst();
        try {
            String stopped = c.getString(c.getColumnIndexOrThrow("time_stopped"));
            return new TimeStoreEntry(
                    new SimpleDateFormat("yyyy-MMM-d H:m:s").parse(c.getString(c.getColumnIndexOrThrow("time_started"))),
                    stopped == null ? null : new SimpleDateFormat("yyyy-MMM-d H:m:s").parse(stopped),
                    c.getString(c.getColumnIndexOrThrow("description"))
            );
        } catch(ParseException e) {
            return null;
        } catch(IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * Gives the latest entry in the database, or NULL if not found
     * @return TimeStoreEntry|null
     */
    public ArrayList<TimeStoreEntry> getTodayEntries() {
        Calendar date = new GregorianCalendar();
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        String start = new SimpleDateFormat("yyyy-MMM-d H:m:s").format(date.getTime());
        date.add(Calendar.DAY_OF_MONTH, 1);
        String stop = new SimpleDateFormat("yyyy-MMM-d H:m:s").format(date.getTime());

        Cursor c = getReadableDatabase().query(
                TABLE_NAME,         // The table to query
                null,               // Return all columns
                "time_started > '" + start + "' AND time_stopped IS NOT NULL AND time_stopped < '" + stop + "'",       // The columns for the WHERE clause
                null,               // The values for the WHERE clause
                null,               // don't group the rows
                null,               // don't filter by row groups
                "time_started ASC" // The sort order
        );

        ArrayList<TimeStoreEntry> list = new ArrayList<TimeStoreEntry>();
        c.moveToFirst();
        while(!c.isLast()) {
            try {
                list.add(new TimeStoreEntry(
                        new SimpleDateFormat("yyyy-MMM-d H:m:s").parse(c.getString(c.getColumnIndexOrThrow("time_started"))),
                        new SimpleDateFormat("yyyy-MMM-d H:m:s").parse(c.getString(c.getColumnIndexOrThrow("time_stopped"))),
                        c.getString(c.getColumnIndexOrThrow("description"))
                ));
            } catch(ParseException e) {

            }
            c.moveToNext();
        }

        return list;
    }

    public long storeEntry(TimeStoreEntry e) {
        ContentValues values = new ContentValues();
        if (e.getId() > 0) {
            values.put("id", e.getId());
        }
        values.put("time_started", new SimpleDateFormat("yyyy-MMM-d H:m:s").format(e.getStarted()));
        if (e.getStopped() != null) {
            values.put("time_stopped", new SimpleDateFormat("yyyy-MMM-d H:m:s").format(e.getStopped()));
        }
        values.put("description", e.getDescription());

        long id = getWritableDatabase().insert(
                TABLE_NAME,
                "id",
                values
        );
        e.setId(id);
        return id;
    }

}
