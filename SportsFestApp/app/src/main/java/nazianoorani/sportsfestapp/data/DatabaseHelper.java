package nazianoorani.sportsfestapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nazianoorani on 20/05/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "Match.db";
    private static final int DATABASE_VERSION = 2;
    static DatabaseHelper sInstance;
    public DatabaseHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    private  static final String CreateMatchTable = "CREATE TABLE " + DatabaseContract.ScheduleTable.TABLE_NAME + " ("
            + DatabaseContract.ScheduleTable._ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DatabaseContract.ScheduleTable.COlUMN_ID + " INTEGER NOT NULL,"
            + DatabaseContract.ScheduleTable.COLUMN_EVENT + " TEXT NOT NULL,"
            + DatabaseContract.ScheduleTable.COLUMN_TEAM_A_NAME + " TEXT NOT NULL,"
            + DatabaseContract.ScheduleTable.COLUMN_TEAM_B_NAME + " TEXT NOT NULL,"
            + DatabaseContract.ScheduleTable.COLUMN_MATCH_TIME + " TEXT NOT NULL,"
            + DatabaseContract.ScheduleTable.COLUMN_MATCH_DATE + " TEXT NOT NULL, "
            + " UNIQUE (" + DatabaseContract.ScheduleTable.COlUMN_ID + ") ON CONFLICT REPLACE)";

    private static final String CreateFavouriteTable = "CREATE TABLE " + DatabaseContract.FavouriteTable.TABLE_NAME + " ("
            + DatabaseContract.FavouriteTable._ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DatabaseContract.FavouriteTable.COLUMN_ID + " INTEGER NOT NULL,"
            + DatabaseContract.FavouriteTable.COLUMN_EVENT + " TEXT NOT NULL,"
            + DatabaseContract.FavouriteTable.COLUMN_EVENT_NO + " INTEGER NOT NULL, "
            + " UNIQUE (" + DatabaseContract.FavouriteTable.COLUMN_ID + ") ON CONFLICT REPLACE)";

    public static synchronized DatabaseHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CreateFavouriteTable);
        db.execSQL(CreateMatchTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        //Remove old values when upgrading.
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.ScheduleTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.FavouriteTable.TABLE_NAME);

    }
}
