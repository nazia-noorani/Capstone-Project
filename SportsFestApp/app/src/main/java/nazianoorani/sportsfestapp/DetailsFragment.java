package nazianoorani.sportsfestapp;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.CircularArray;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import nazianoorani.sportsfestapp.Adapter.SlidingImageAdapter;

/**
 * Created by nazianoorani on 19/04/16.
 */
public class DetailsFragment extends Fragment  {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    ArrayList<String> arrayList = new ArrayList<String>() {{
        add("Rules");
        add("Schedule");
        add("Results");
        add("Register");
        add("Registered Teams");
    }};

    int eventNo;
    RecyclerView recyclerView;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private  final int pageSize =3;
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();
    private RecyclerAdapter recyclerAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_indivi_event_details, container, false);


        Bundle bundle = getArguments();
        if(bundle!= null){
            eventNo = bundle.getInt("event");
            if(ImagesArray.size() != 0){
                ImagesArray.clear();
            }
            getImagesArray();

        }

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_details);
        recyclerAdapter = new RecyclerAdapter();
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        setHasOptionsMenu(true);
//        listAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, arrayList);
//        listView.setAdapter(listAdapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                openDetails(position);
//
//            }
//        });
        return view;
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

    private void init(CirclePageIndicator indicator, final ViewPager viewPager) {

        viewPager.setAdapter(new SlidingImageAdapter(getActivity(),ImagesArray));
        indicator.setViewPager(viewPager);
        final float density = getResources().getDisplayMetrics().density;
//Set circle indicator radius
        indicator.setRadius(5 * density);
   //
        //       No of pages set to 3!!
        NUM_PAGES = pageSize;

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }
    public ArrayList<Integer> getImagesArray() {
        switch (eventNo){
            case 1 :
                ImagesArray.add(R.mipmap.img_bad1);
                ImagesArray.add(R.mipmap.img_bad1);
                ImagesArray.add(R.mipmap.img_bad1);
                ((DetailsActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.badminton));
//                    imageView.setBackgroundResource(R.mipmap.ic_badminton);
                break;
            case 2 :
                ImagesArray.add(R.mipmap.img_tt1);
                ImagesArray.add(R.mipmap.img_tt1);
                ImagesArray.add(R.mipmap.img_tt1);
                ((DetailsActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.table_tennis));
//                    imageView.setBackgroundResource(R.mipmap.ic_tt);
                break;
            case 3 :
                ImagesArray.add(R.mipmap.img_lt1);
                ImagesArray.add(R.mipmap.img_lt1);
                ImagesArray.add(R.mipmap.img_lt1);
                ((DetailsActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.lawn_tennis));
//                    imageView.setBackgroundResource(R.mipmap.ic_lt);
                break;
            case 4 :
                ImagesArray.add(R.drawable.athletics);
                ImagesArray.add(R.drawable.athletics);
                ImagesArray.add(R.drawable.athletics);
                ((DetailsActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.athletics));
//                    imageView.setBackgroundResource(R.mipmap.ic_ckt);
                break;
            case 5 :
                ImagesArray.add(R.drawable.chess);
                ImagesArray.add(R.drawable.chess);
                ImagesArray.add(R.drawable.chess);
                ((DetailsActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.chess));
//                    imageView.setBackgroundResource(R.mipmap.ic_ckt);
                break;
            case 6 :
                ImagesArray.add(R.mipmap.img_ckt1);
                ImagesArray.add(R.mipmap.img_ckt1);
                ImagesArray.add(R.mipmap.img_ckt1);
                ((DetailsActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.cricket));
//                    imageView.setBackgroundResource(R.mipmap.ic_ckt);
                break;
            case 7 :
                ImagesArray.add(R.mipmap.img_fb1);
                ImagesArray.add(R.mipmap.img_fb1);
                ImagesArray.add(R.mipmap.img_fb1);
                ((DetailsActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.football));
//                    imageView.setBackgroundResource(R.mipmap.ic_fb);
                break;
            case 8 :
                ImagesArray.add(R.mipmap.img_bb1);
                ImagesArray.add(R.mipmap.img_bb1);
                ImagesArray.add(R.mipmap.img_bb1);
                ((DetailsActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.basketball));
//                    imageView.setBackgroundResource(R.mipmap.ic_bb);
                break;
            case 9 :
                ImagesArray.add(R.mipmap.img_vb1);
                ImagesArray.add(R.mipmap.img_vb1);
                ImagesArray.add(R.mipmap.img_vb1);
                ((DetailsActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.volleyball));
//                    imageView.setBackgroundResource(R.mipmap.ic_ckt);
                break;



        }
        return ImagesArray;
    }

    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder viewHolder =  null;
            if(viewType == TYPE_HEADER){
                View viewHeader = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_details_header,parent,false);
                viewHolder = new ViewHolderDetailsHeader(viewHeader);
            }else if(viewType == TYPE_ITEM ) {
                View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_item, parent, false);
                viewHolder = new ViewHolderDetailsItem(viewItem);
            }
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if(holder instanceof ViewHolderDetailsHeader){
                init(((ViewHolderDetailsHeader) holder).indicator,((ViewHolderDetailsHeader) holder).viewPager);
            }else if(holder instanceof  ViewHolderDetailsItem){
                ((ViewHolderDetailsItem) holder).tvList.setText(arrayList.get(position - 1));
            }

        }

        @Override
        public int getItemViewType(int position) {
            if(position == TYPE_HEADER){
                return TYPE_HEADER;
            }
            else {
                return TYPE_ITEM;
            }
        }

        @Override
        public int getItemCount() {
            // + 1 for the header
            return arrayList.size() + 1;
        }

        public class ViewHolderDetailsHeader extends RecyclerView.ViewHolder{
            CirclePageIndicator indicator;
            ViewPager viewPager;
            public ViewHolderDetailsHeader(View itemView) {
                super(itemView);
                indicator = (CirclePageIndicator)
                        itemView.findViewById(R.id.indicator);
                viewPager = (ViewPager) itemView.findViewById(R.id.pager);
            }
        }

        public class ViewHolderDetailsItem extends RecyclerView.ViewHolder implements View.OnClickListener{
            TextView tvList;
            public ViewHolderDetailsItem(View itemView) {
                super(itemView);
                tvList = (TextView) itemView.findViewById(R.id.tv_list);
                tvList.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                openDetails(getLayoutPosition() - 1);
            }
            public  void openDetails(int position){
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putInt("eventNo",eventNo);
                Fragment fragment;
                switch (position){
                    case 0:
                        fragment = new RulesFragment();
                        fragment.setArguments(bundle);
                        fragmentManager.beginTransaction().addToBackStack(null).
                                replace(R.id.fragment_container, fragment).commit();
                        break;

                    case 1 :
                        fragment = new ScheduleFragment();
                        fragment.setArguments(bundle);
                        fragmentManager.beginTransaction().addToBackStack(null).
                                replace(R.id.fragment_container, fragment).commit();
                        break;

                    case 2 :
                        fragment = new ResultsFragment();
                        fragment.setArguments(bundle);
                        fragmentManager.beginTransaction().addToBackStack(null).
                                replace(R.id.fragment_container, fragment).commit();
                        break;
                    case 3 :
                        fragment = new RegisterFragment();
                        fragment.setArguments(bundle);
                        fragmentManager.beginTransaction().addToBackStack(null).
                                replace(R.id.fragment_container, fragment).commit();
                        break;
                    case 4 :
                        fragment = new RegisteredTeamsFragment();
                        fragment.setArguments(bundle);
                        fragmentManager.beginTransaction().addToBackStack(null).
                                replace(R.id.fragment_container, fragment).commit();
                        break;

                }
            }
        }
    }


}

