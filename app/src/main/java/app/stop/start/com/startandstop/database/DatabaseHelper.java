package app.stop.start.com.startandstop.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 3;
    protected static final String DATABASE_NAME = "startstop";
    protected static final String TABLE_TOTAL_TIME = "totaltime";
    protected static final String TABLE_TIME = "tabletime";
    protected static final String COL_NAME = "name";
    protected static final String COL_TIME = "time";
    protected static final String COL_START_DATE = "startdate";
    protected static final String COL_STOP_DATE = "stopdate";
    protected static final String COL_NOTE = "note";
    protected static final String COL_ID = "id";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String totalTime = "CREATE TABLE " + TABLE_TOTAL_TIME + " ( "
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_NAME + " TEXT, "
                + COL_TIME + " TEXT, "
                + COL_START_DATE + " TEXT, "
                + COL_STOP_DATE + " TEXT " + ")";

        String time = "CREATE TABLE " + TABLE_TIME + " ( "
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_NAME + " TEXT, "
                + COL_TIME + " TEXT, "
                + COL_START_DATE + " TEXT, "
                + COL_STOP_DATE + " TEXT, "
                + COL_NOTE + " TEXT " + ")";

        sqLiteDatabase.execSQL(totalTime);
        sqLiteDatabase.execSQL(time);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TOTAL_TIME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TIME);
        onCreate(sqLiteDatabase);
    }
}
