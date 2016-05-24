package nazianoorani.sportsfestapp;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Vector;

import nazianoorani.sportsfestapp.Adapter.SectionsPagerAdapter;
import nazianoorani.sportsfestapp.data.DatabaseContract;
import nazianoorani.sportsfestapp.drawerfragments.AboutFragment;
import nazianoorani.sportsfestapp.drawerfragments.ContactFragment;
import nazianoorani.sportsfestapp.dto.ScheduleDto;
import nazianoorani.sportsfestapp.networkmanager.AppController;
import nazianoorani.sportsfestapp.util.Constants;
import nazianoorani.sportsfestapp.util.NetworkUtil;
import nazianoorani.sportsfestapp.util.SnackBarUtil;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private LinearLayout fragCont;
    private TabLayout tabLayout;
    private ArrayList<ScheduleDto> listSchedule = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.setupWithViewPager(mViewPager);

        fragCont = (LinearLayout) findViewById(R.id.fragcontainer);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        getSchedule();

    }
    private void getSchedule() {
        //Enter URL

        if(NetworkUtil.isNetworkAvailable(MainActivity.this)) {
            String URL = Constants.BASE_URL + Constants.SCHEDULE_URL_ALL;
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        JSONArray jsonArray = response.getJSONArray("schedule");
                        if (jsonArray.length() == 0) {
//                            tvNoSchedule.setText(getString(R.string.no_schedule));
//                            tvNoSchedule.setVisibility(View.VISIBLE);
                        }
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            ScheduleDto scheduleDto = new ScheduleDto();
                            if (jsonObject.has("event")) {
                                scheduleDto.setEvent(jsonObject.getString("event"));
                            }
                            if (jsonObject.has("matchDate")) {
                                scheduleDto.setMatchDate(jsonObject.getString("matchDate"));
                            }

                            if (jsonObject.has("matchTime")) {
                                scheduleDto.setMatchTime(jsonObject.getString("matchTime"));
                            }

                            if (jsonObject.has("nameTeamA")) {
                                scheduleDto.setNameTeamA(jsonObject.getString("nameTeamA"));
                            }

                            if (jsonObject.has("nameTeamB")) {
                                scheduleDto.setNameTeamB(jsonObject.getString("nameTeamB"));
                            }
                            if (jsonObject.has("chestURLTeamA")) {
                                scheduleDto.setTeamAChestURL(jsonObject.getString("chestURLTeamA"));
                            }

                            if (jsonObject.has("chestURLTeamB")) {
                                scheduleDto.setTeamBChestURL(jsonObject.getString("chestURLTeamB"));
                            }
                            if (jsonObject.has("id")) {
                                scheduleDto.setId(jsonObject.getInt("id"));
                            }


                            listSchedule.add(scheduleDto);
                            updateDataBase(scheduleDto);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(MainActivity.this, volleyError.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

            AppController.getInstance().addToRequestQueue(request);
        }else {
            SnackBarUtil.display(MainActivity.this,getString(R.string.no_internet), Snackbar.LENGTH_LONG);
        }


    }

    ContentResolver resolver;
    public void updateDataBase(ScheduleDto dto){
        Vector<ContentValues> values = new Vector <ContentValues> (listSchedule.size());
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract.ScheduleTable.COlUMN_ID,dto .getId());
        contentValues.put(DatabaseContract.ScheduleTable.COLUMN_MATCH_DATE,dto .getMatchDate());
        contentValues.put(DatabaseContract.ScheduleTable.COLUMN_MATCH_TIME, dto.getMatchTime());
        contentValues.put(DatabaseContract.ScheduleTable.COLUMN_TEAM_A_NAME, dto.getNameTeamA());
        contentValues.put(DatabaseContract.ScheduleTable.COLUMN_TEAM_B_NAME, dto.getNameTeamB());
        contentValues.put(DatabaseContract.ScheduleTable.COLUMN_EVENT, dto.getEvent());
        values.add(contentValues);
        ContentValues[] insert_data = new ContentValues[values.size()];
        values.toArray(insert_data);
        resolver = getContentResolver();
        resolver.bulkInsert(
                DatabaseContract.BASE_CONTENT_URI,insert_data);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        fragCont.setVisibility(View.VISIBLE);
        mViewPager.setVisibility(View.GONE);
        tabLayout.setVisibility(View.GONE);
        if (id == R.id.home) {
            tabLayout.setVisibility(View.VISIBLE);
            mViewPager.setVisibility(View.VISIBLE);
            fragCont.setVisibility(View.GONE);
        } else if (id == R.id.contact) {

            getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragcontainer,
                    new ContactFragment()).commit();

        } else if (id == R.id.aboutus) {
            getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragcontainer,
                    new AboutFragment()).commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    /**
     * A placeholder fragment containing a simple view.
     */

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */

    @Override
    public void onBackPressed() {

        if(drawer.isDrawerOpen(navigationView))
        {
            drawer.closeDrawers();
            return;
        }

        fragCont.setVisibility(View.GONE);
        mViewPager.setVisibility(View.VISIBLE);
        tabLayout.setVisibility(View.VISIBLE);

            finish();
    }
}
