package nazianoorani.sportsfestapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import nazianoorani.sportsfestapp.util.EventName;

/**
 * Created by nazianoorani on 20/04/16.
 */
public class ResultsFragment extends Fragment {

    int eventNo;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_results,container,false);


            Bundle bundle = getArguments();
            if (bundle != null) {
                eventNo = bundle.getInt("eventNo");
            }

        Button textView = (Button)view.findViewById(R.id.share);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                // showing temporary result
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Results: Event-"+EventName.getEventName(eventNo)+" KIET (winner) SRM (runnerup)");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((DetailsActivity) getActivity()).getSupportActionBar().setTitle(EventName.getEventName(eventNo)+" - Results");
        setHasOptionsMenu(true);
        return view;
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                break;
        }
        return true;
    }


}
