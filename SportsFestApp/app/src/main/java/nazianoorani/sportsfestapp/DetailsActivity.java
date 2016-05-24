package nazianoorani.sportsfestapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by nazianoorani on 24/02/16.
 */
public class DetailsActivity extends AppCompatActivity {
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        toolbar = (Toolbar) findViewById(R.id.details_toolbar);
        setSupportActionBar(toolbar);
        int eventNo;
        Bundle bundle = new Bundle();
        if(getIntent().hasExtra("event")){
            eventNo = getIntent().getIntExtra("event",0);
            bundle.putInt("event",eventNo);
        }
        if(savedInstanceState == null){
            FragmentManager  fm = getSupportFragmentManager();
            Fragment fragment = new DetailsFragment();
            fragment.setArguments(bundle);
            fm.beginTransaction().replace(R.id.fragment_container,fragment).commit();
        }
    }

}
