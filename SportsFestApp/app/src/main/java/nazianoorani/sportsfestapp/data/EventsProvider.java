package nazianoorani.sportsfestapp.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by nazianoorani on 20/05/16.
 */
public class EventsProvider extends ContentProvider{
    static SQLiteQueryBuilder sQueryBuilder;
    static final int FAVOURITE = 100;
    static final int FAVOURITE_WITH_ID = 101;
    static final int SCHEDULE = 102;

    private UriMatcher muriMatcher = buildUriMatcher();
    private DatabaseHelper mOpenHelper;
    private static final UriMatcher mUriMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mUriMatcher.addURI(DatabaseContract.CONTENT_AUTHORITY, DatabaseContract.PATH_SCHEDULE,SCHEDULE);
        mUriMatcher.addURI(DatabaseContract.CONTENT_AUTHORITY, DatabaseContract.PATH_FAVOURITE,FAVOURITE);
        mUriMatcher.addURI(DatabaseContract.CONTENT_AUTHORITY,DatabaseContract.PATH_FAVOURITE+"/#", FAVOURITE_WITH_ID);
        return mUriMatcher;
    }

    static {
        sQueryBuilder = new SQLiteQueryBuilder();
        sQueryBuilder.setTables(DatabaseContract.ScheduleTable.TABLE_NAME);
    }

    private int match_uri(Uri uri)
    {
        String link = uri.toString();
        {
            if(link.contentEquals(DatabaseContract.BASE_CONTENT_URI.toString())) {
                return SCHEDULE;
            }
           if(link.contentEquals(DatabaseContract.ScheduleTable.CONTENT_URI.toString())) {
                return SCHEDULE;
            }
            else if(link.contentEquals(DatabaseContract.FavouriteTable.CONTENT_URI.toString())) {
                return FAVOURITE;
            }
        }
        return -1;
    }
    @Override
    public boolean onCreate() {
        mOpenHelper = DatabaseHelper.getInstance(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final int match = muriMatcher.match(uri);
        Cursor retCursor;
        if(match == SCHEDULE) {
            retCursor = mOpenHelper.getReadableDatabase().query(
                    DatabaseContract.ScheduleTable.TABLE_NAME,
                    projection, null, null, null, null, sortOrder);
        } else if(match == FAVOURITE){

            retCursor = mOpenHelper.getReadableDatabase().query(
                    DatabaseContract.FavouriteTable.TABLE_NAME,
                    projection, null, null, null, null, sortOrder);
        }else if( match == FAVOURITE_WITH_ID){
                retCursor = mOpenHelper.getReadableDatabase().query(
                        DatabaseContract.FavouriteTable.TABLE_NAME,
                        projection,
                        DatabaseContract.FavouriteTable.COLUMN_ID + "= ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);

        }
        else{
            throw new UnsupportedOperationException("Unknown Uri" + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(),uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = muriMatcher.match(uri);
        if(match == SCHEDULE){
            return DatabaseContract.ScheduleTable.CONTENT_TYPE;
        }else if(match == FAVOURITE){
            return DatabaseContract.FavouriteTable.CONTENT_TYPE;
        }else if(match == FAVOURITE_WITH_ID){
                return DatabaseContract.FavouriteTable.CONTENT_ITEM_TYPE;
        }
        else{
            throw new UnsupportedOperationException("Unknown uri :" + uri );
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = mUriMatcher.match(uri);
        Uri returnUri;
        db.beginTransaction();
        switch (match) {
            case FAVOURITE: {
                long _id = db.insert(DatabaseContract.FavouriteTable.TABLE_NAME, null, values);
                if (_id > 0){
                    returnUri = DatabaseContract.FavouriteTable.buildFavouritesURI(_id);
                    db.setTransactionSuccessful();
                }else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                db.endTransaction();
                break;
            }
            case SCHEDULE :
                long _id = db.insert(DatabaseContract.ScheduleTable.TABLE_NAME, null, values);
                if (_id > 0){
                    returnUri = DatabaseContract.ScheduleTable.CONTENT_URI;
                    db.setTransactionSuccessful();
                }else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                db.endTransaction();
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        db.close();
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = mUriMatcher.match(uri);
        int retVal;
        if(selection==null)
            selection = "1";
        switch (match){
            case FAVOURITE:
                retVal = db.delete(DatabaseContract.FavouriteTable.TABLE_NAME,selection,selectionArgs);
                break;
            case FAVOURITE_WITH_ID:
                retVal = db.delete(DatabaseContract.FavouriteTable.TABLE_NAME, DatabaseContract.FavouriteTable.COLUMN_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if(retVal!=0)
            getContext().getContentResolver().notifyChange(uri, null);
        db.close();
        return retVal;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = mUriMatcher.match(uri);
        int retVal;
        db.beginTransaction();
        switch (match){
            case FAVOURITE: {
                retVal = db.update(DatabaseContract.FavouriteTable.TABLE_NAME, values, selection, selectionArgs);
                db.setTransactionSuccessful();
                db.endTransaction();
                break;
            }default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if(retVal!=0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        db.close();
        return retVal;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int match = match_uri(uri);
        switch (match) {
            case SCHEDULE:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(DatabaseContract.ScheduleTable.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }

    }
}
