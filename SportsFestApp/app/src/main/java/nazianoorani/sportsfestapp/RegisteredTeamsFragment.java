package nazianoorani.sportsfestapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import nazianoorani.sportsfestapp.Adapter.CustomAdapter;
import nazianoorani.sportsfestapp.dto.TeamDto;
import nazianoorani.sportsfestapp.networkmanager.AppController;
import nazianoorani.sportsfestapp.util.Constants;
import nazianoorani.sportsfestapp.util.EventName;
import nazianoorani.sportsfestapp.util.SnackBarUtil;

/**
 * Created by nazianoorani on 24/04/16.
 */
public class RegisteredTeamsFragment extends Fragment{
    ListView listView;
    ArrayList <TeamDto> listDto = new ArrayList<>();
    int eventNo;
    String eventName ="";
    CustomAdapter customAdapter;
    private ProgressDialog progressDialog;
    private final String LIST_STRING = "listDto";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registered_teams,container,false);
        Bundle bundle = getArguments();
        progressDialog = new ProgressDialog(getActivity());

        if(bundle !=  null){
            eventNo = bundle.getInt("eventNo");
            eventName = EventName.getEventName(eventNo);
        }
        if(savedInstanceState == null){
        progressDialog.setMessage(getString(R.string.loading));
        getTeamList();
        }else {
            listDto = savedInstanceState.getParcelableArrayList(LIST_STRING);
        }
        listView = (ListView) view.findViewById(R.id.listTeams);
        customAdapter = new CustomAdapter(getActivity(),listDto);
        listView.setAdapter(customAdapter);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((DetailsActivity) getActivity()).getSupportActionBar().setTitle(EventName.getEventName(eventNo)+" - Registered Teams");
        setHasOptionsMenu(true);
        return view;

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(LIST_STRING,listDto);
        super.onSaveInstanceState(outState);
    }

    public void getTeamList() {
        String url = Constants.BASE_URL+"get_reg_teams.php?event="+eventName;
        Log.e("event",eventName);
        Log.e("URL", url);

        progressDialog.show();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    progressDialog.dismiss();
                    if(!listDto.isEmpty()){
                        listDto.clear();
                    }
                    JSONArray jsonArray = response.getJSONArray("teams");
                    for(int i =0; i< jsonArray.length() ;i++){
                        JSONObject jsonObject =  jsonArray.getJSONObject(i);
                        TeamDto teamDto = new TeamDto();
                        if(jsonObject.has("name")){
                            teamDto.setName(jsonObject.getString("name"));
                        }
                        if(jsonObject.has("college")){
                            teamDto.setCollege(jsonObject.getString("college"));
                        }
                        if(jsonObject.has("phone")){
                            teamDto.setPhone(jsonObject.getString("phone"));
                        }
                        listDto.add(teamDto);
                        customAdapter.notifyDataSetChanged();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    SnackBarUtil.display(getActivity(),e.getMessage(), Snackbar.LENGTH_LONG);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),error.getMessage().toString(),Toast.LENGTH_LONG).show();
            }
        });
        AppController.getInstance().addToRequestQueue(request);

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
