package nazianoorani.sportsfestapp.widget;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.text.SimpleDateFormat;
import java.util.Date;

import nazianoorani.sportsfestapp.R;
import nazianoorani.sportsfestapp.data.DatabaseContract;

/**
 * Created by nazianoorani on 20/05/16.
 */
public class WidgetRemoteViewService extends RemoteViewsService {
    private static final String[] FORECAST_COLUMNS = {
            DatabaseContract.ScheduleTable.COLUMN_EVENT,
            DatabaseContract.ScheduleTable.COLUMN_TEAM_A_NAME,
            DatabaseContract.ScheduleTable.COLUMN_TEAM_B_NAME,
            DatabaseContract.ScheduleTable.COLUMN_MATCH_TIME,
            DatabaseContract.ScheduleTable.COLUMN_MATCH_DATE
    };

    // indices must match the projection
    private static final int INDEX_EVENT = 0;
    private static final int INDEX_TEAM_A = 1;
    private static final int INDEX_TEAM_B = 2;
    private static final int INDEX_TIME = 3;
    private static final int INDEX_MATCH_DATE = 4;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory() {
            private Cursor data = null;
            @Override
            public void onCreate() {

            }

            @Override
            public void onDataSetChanged() {

                if (data != null) {
                    data.close();
                }
                // This method is called by the app hosting the widget (e.g., the launcher)
                // However, our ContentProvider is not exported so it doesn't have access to the
                // data. Therefore we need to clear (and finally restore) the calling identity so
                // that calls use our process and permission
                final long identityToken = Binder.clearCallingIdentity();
                data = getContentResolver().query(DatabaseContract.ScheduleTable.CONTENT_URI, FORECAST_COLUMNS, DatabaseContract.ScheduleTable.COLUMN_MATCH_DATE + " = ",
                        new String[] {new SimpleDateFormat("yyyy-MM-dd").format(new Date())}, DatabaseContract.ScheduleTable.COLUMN_MATCH_TIME );


                Binder.restoreCallingIdentity(identityToken);
            }

            @Override
            public void onDestroy() {
                if (data != null) {
                    data.close();
                    data = null;
                }
            }

            @Override
            public int getCount() {
                if(data != null){
                    return data.getCount();}
                else { return 0;}
            }

            @Override
            public RemoteViews getViewAt(int position) {

                if (position == AdapterView.INVALID_POSITION ||
                        data == null || !data.moveToPosition(position)) {
                    return null;
                }
                RemoteViews views = new RemoteViews(getPackageName(),
                        R.layout.widget_detail_list_item);


                String teamAName = data.getString(INDEX_TEAM_A);
                String teamBName = data.getString(INDEX_TEAM_B);
//                int homeCrest = Utilies.getTeamCrestByTeamName(homeName, getApplicationContext());
//                int awayCrest = Utilies.getTeamCrestByTeamName(awayName, getApplicationContext());
//                String matchScore = Utilies.getScores(data.getInt(INDEX_HOME_GOALS), data.getInt(INDEX_AWAY_GOALS));
                String matchTime= data.getString(INDEX_TIME);
                String matchDate= data.getString(INDEX_MATCH_DATE);
                String event= data.getString(INDEX_EVENT);



                views.setTextViewText(R.id.wid_teamA, teamAName);
                views.setTextViewText(R.id.wid_teamB, teamBName);
//                views.setImageViewResource(R.id.home_crest, homeCrest);
//                views.setImageViewResource(R.id.away_crest, awayCrest);
//                views.setTextViewText(R.id.widget_score_textview, matchScore);
                views.setTextViewText(R.id.widget_data_event,event);
                views.setTextViewText(R.id.widget_data_txt, matchTime +" "+ matchDate);

                final Intent fillInIntent = new Intent();
                Uri matchUri = DatabaseContract.ScheduleTable.CONTENT_URI;
                fillInIntent.setData(matchUri);
                views.setOnClickFillInIntent(R.id.widget, fillInIntent);



                return views;
            }

            @Override
            public RemoteViews getLoadingView() {
                return new RemoteViews(getPackageName(), R.layout.widget_detail_list_item);
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
//                if (data.moveToPosition(position))
                return position;
//                    return data.getLong(INDEX_MATCH_ID);
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };
    }

}
