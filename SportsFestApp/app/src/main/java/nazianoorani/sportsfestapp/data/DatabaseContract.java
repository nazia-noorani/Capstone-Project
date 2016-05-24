package nazianoorani.sportsfestapp.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by nazianoorani on 20/05/16.
 */
public class DatabaseContract {
    public static final String CONTENT_AUTHORITY = "nazianoorani.sportsfestapp";
    // Build Base URI for content provider
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_SCHEDULE = "schedule";
    public static final String PATH_FAVOURITE = "favourite";


    public static final class ScheduleTable implements BaseColumns {

        public static final String TABLE_NAME = "schedule_events";


        public static final String COLUMN_EVENT = "event";
        public static final String COLUMN_TEAM_A_NAME = "teamA_name";
        public static final String COLUMN_TEAM_B_NAME = "teamB_name";
        public static final String COLUMN_MATCH_TIME = "match_time";
        public static final String COLUMN_MATCH_DATE = "match_date";
        public static final String COlUMN_ID = "id";


        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_SCHEDULE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SCHEDULE;

    }

    public static final class FavouriteTable implements BaseColumns{

        public static final String TABLE_NAME = "favourite_events";
        public static final String COLUMN_EVENT = "event";
        public static final String COLUMN_EVENT_NO = "event_no";
        public static final String COLUMN_ID = "id";
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVOURITE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVOURITE;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVOURITE;

        public static Uri buildFavouritesURI(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        // content://..../MovieId
        public static Uri buildFavouriteEventUriWithEventId(String movieId) {
            return CONTENT_URI.buildUpon().appendPath(movieId).build();
        }


    }
}
