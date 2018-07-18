package com.example.raihansarkar.onlinegeneraldiary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by raiha on 1/11/2018.
 */

public class PoliceListAdapter extends BaseAdapter{

    Context context;
    ArrayList<PoliceStation> arrayList;

    public PoliceListAdapter(Context context, ArrayList<PoliceStation> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }



    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return arrayList.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.police_list_sample, null);
        }

        TextView namePst=(TextView)convertView.findViewById(R.id.pstName);

        namePst.setText(arrayList.get(position).getName());
        return convertView;
    }


}
