package nazianoorani.sportsfestapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import nazianoorani.sportsfestapp.R;
import nazianoorani.sportsfestapp.dto.TeamDto;

/**
 * Created by nazianoorani on 24/04/16.
 */
public class CustomAdapter extends BaseAdapter {

    ArrayList <TeamDto> listDto = new ArrayList<>();
    Context context ;
    public CustomAdapter (Context context, ArrayList<TeamDto>listDto){
        this.context = context;
        this.listDto = listDto;

    }
    @Override
    public int getCount() {
        return listDto.size();
    }

    @Override
    public Object getItem(int position) {
        return listDto.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_items, parent, false);
        }
        TextView txtName,txtCollege,txtPhone;
        txtName = (TextView) convertView.findViewById(R.id.txtName);
        txtCollege = (TextView) convertView.findViewById(R.id.txtCollege);
        txtPhone = (TextView) convertView.findViewById(R.id.txtPhone);
        txtName.setText(listDto.get(position).getName());
        txtCollege.setText(listDto.get(position).getCollege());
        txtPhone.setText(listDto.get(position).getPhone());
        return convertView;
    }
}
