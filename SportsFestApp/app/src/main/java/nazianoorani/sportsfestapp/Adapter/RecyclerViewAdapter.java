package nazianoorani.sportsfestapp.Adapter;

/**
 * Created by nazianoorani on 24/04/16.
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import nazianoorani.sportsfestapp.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolders> {

    private Context context;

    ArrayList <Integer> imageArray  = new ArrayList<Integer> ();
    public RecyclerViewAdapter(Context context ,ArrayList <Integer> imageArray ) {
        this.context = context;
        this.imageArray = imageArray;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_item, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {

        try {
            ((BitmapDrawable) holder.img.getDrawable()).getBitmap().recycle();
        }catch (Exception e)
        {e.printStackTrace();}
        holder.img.setImageResource(imageArray.get(position));
    }

    @Override
    public int getItemCount() {
        return imageArray.size();
    }

    public class RecyclerViewHolders extends RecyclerView.ViewHolder{

        ImageView img;
        public RecyclerViewHolders(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img_gallery);
        }
    }

}