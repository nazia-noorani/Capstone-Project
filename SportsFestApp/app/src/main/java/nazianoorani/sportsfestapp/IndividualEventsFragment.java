package nazianoorani.sportsfestapp;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import nazianoorani.sportsfestapp.data.DatabaseContract;
import nazianoorani.sportsfestapp.dto.EventDto;
import nazianoorani.sportsfestapp.networkmanager.AppController;
import nazianoorani.sportsfestapp.util.Constants;
import nazianoorani.sportsfestapp.util.NetworkUtil;
import nazianoorani.sportsfestapp.util.SnackBarUtil;

import static android.R.drawable.btn_star;
import static android.R.drawable.btn_star_big_on;

/**
 * Created by nazianoorani on 24/02/16.
 */
public class IndividualEventsFragment extends Fragment  {
    RecyclerView recyclerIndiEvents;
    IndiEventsAdapter adapter;

    ArrayList <EventDto> arrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events,container,false);

        if(savedInstanceState == null){
            getEvents();
        }else {
            arrayList = savedInstanceState.getParcelableArrayList(getString(R.string.eventList));

        }
        recyclerIndiEvents = (RecyclerView) view.findViewById(R.id.recycler_indivi_events);
        adapter = new IndiEventsAdapter(getActivity(),arrayList);
        initRecycler();
        return view;
    }

    private void getEvents() {
        //Enter URL
        String URL = Constants.BASE_URL+Constants.FETCH_INDIVIDUAL_EVENTS;
        if(NetworkUtil.isNetworkAvailable(getActivity())) {
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

//                    progressDialog.dismiss();
                    if (!arrayList.isEmpty()) {
                        arrayList.clear();
                    }
                    try {
                        JSONArray jsonArray = response.getJSONArray("events");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            EventDto eventDto = new EventDto();
                            if (jsonObject.has("event")) {
                                eventDto.setEvent(jsonObject.getString("event"));
                            }
                            if (jsonObject.has("eventImageURL")) {
                                eventDto.setEventImageURL(jsonObject.getString("eventImageURL"));
                            }

                            if (jsonObject.has("eventNo")) {
                                eventDto.setEventNo(jsonObject.getInt("eventNo"));
                            }
                            if (jsonObject.has("id")) {
                                eventDto.setId(jsonObject.getInt("id"));
                            }

                            arrayList.add(eventDto);
                            adapter.notifyDataSetChanged();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(getActivity(), volleyError.getMessage(), Toast.LENGTH_LONG);
                }
            });

            AppController.getInstance().addToRequestQueue(request);
        }else {
            SnackBarUtil.display(getActivity(),getString(R.string.no_internet), Snackbar.LENGTH_LONG);

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(getString(R.string.eventList),arrayList);
        super.onSaveInstanceState(outState);
    }

    private void initRecycler() {
        recyclerIndiEvents.setAdapter(adapter);
        recyclerIndiEvents.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public class IndiEventsAdapter extends RecyclerView.Adapter<IndiEventsAdapter.IndiviEventsViewHolder> {

        ContentResolver resolver;
        Context context;
        ArrayList<EventDto> list;

        public IndiEventsAdapter(Context context, ArrayList<EventDto> list) {
            this.context = context;
            this.list = list;
        }


        @Override
        public IndiviEventsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_indivi_events, parent, false);
            IndiviEventsViewHolder viewHolder = new IndiviEventsViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final IndiviEventsViewHolder holder,final int position) {
            String imgURL = Constants.BASE_URL + Constants.IMAGE_URL + arrayList.get(position).getEventImageURL();
            Picasso.with(context)
                    .load(imgURL)
                    .into(holder.imgEvent);
            holder.tvEvent.setText(arrayList.get(position).getEvent());

            if ((isFavourtie(String.valueOf(arrayList.get(position).getId())))) {
                holder.btnFav.setBackgroundResource(btn_star_big_on);

            }
            holder.btnFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateDatabase(isFavourtie(String.valueOf(arrayList.get(position).getId())), String.valueOf(arrayList.get(position).getId()), holder.btnFav,arrayList.get(position));
                }
            });
        }


        @Override
        public int getItemCount() {
            return list.size();
        }


        public boolean isFavourtie(String movieId) {
            resolver = getActivity().getContentResolver();
            boolean retVal = false;
            Cursor movieCursor = resolver.query(DatabaseContract.FavouriteTable.
                    buildFavouriteEventUriWithEventId(movieId), null, null, null, null);
            if (1 == movieCursor.getCount() )
                retVal =  true;
            return retVal;
        }

        public void updateDatabase(boolean isFavourite, String movieId, Button btnFav, EventDto eventDto) {
            if (isFavourite) {
            /* Movie already in Favourites - Delete the movie from DB*/
                resolver.delete(DatabaseContract.FavouriteTable.
                        buildFavouriteEventUriWithEventId(movieId), null, null);
                SnackBarUtil.display(getActivity(), "unFavourite", Snackbar.LENGTH_LONG);
                btnFav.setBackgroundResource(btn_star);
            } else {
            /*Add values into DB*/
                ContentValues contentValues = new ContentValues();
                contentValues.put(DatabaseContract.FavouriteTable.COLUMN_ID, eventDto.getId());
                contentValues.put(DatabaseContract.FavouriteTable.COLUMN_EVENT, eventDto.getEvent());
                contentValues.put(DatabaseContract.FavouriteTable.COLUMN_EVENT_NO, eventDto.getEventNo());
                resolver.insert(DatabaseContract.FavouriteTable.CONTENT_URI, contentValues);
                SnackBarUtil.display(getActivity(), "Favourite", Snackbar.LENGTH_LONG);
                btnFav.setBackgroundResource(btn_star_big_on);
            }
        }

        public class IndiviEventsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            TextView tvEvent;
            ImageView imgEvent;
            Button btnFav;


            public IndiviEventsViewHolder(View itemView) {
                super(itemView);
                tvEvent = (TextView) itemView.findViewById(R.id.event_name);
                imgEvent = (ImageView) itemView.findViewById(R.id.imgEvent);
                btnFav = (Button) itemView.findViewById(R.id.btn_fav);

                imgEvent.setOnClickListener(this);
                tvEvent.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.event_name:
                    case R.id.imgEvent:
                        Intent intent = new Intent(getActivity(), DetailsActivity.class);
                        intent.putExtra("event", arrayList.get(getLayoutPosition()).getEventNo());
                        getActivity().startActivity(intent);
                        break;

                }
            }


        }
    }
}

