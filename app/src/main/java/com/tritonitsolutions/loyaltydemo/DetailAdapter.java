package com.tritonitsolutions.loyaltydemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tritonitsolutions.layaltydemo.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by TritonDev on 8/10/2015.
 */
public class DetailAdapter extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String,String>> mens_data;
    LayoutInflater inflater;

    public DetailAdapter(Context context, ArrayList<HashMap<String, String>> mens_data) {
        this.context=context;
        this.mens_data=mens_data;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return mens_data.size();
    }

    @Override
    public Object getItem(int position) {
        return mens_data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mens_data.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v =convertView;
        ViewHolder holder=null;
        if(convertView==null){
            v=inflater.inflate(R.layout.detail_row,parent,false);
            holder=new ViewHolder();
            holder.img=(ImageView)v.findViewById(R.id.img_product);
            holder.name=(TextView)v.findViewById(R.id.tv_name);
            holder.size=(TextView)v.findViewById(R.id.tv_size);
            holder.price=(TextView)v.findViewById(R.id.tv_price);
            v.setTag(holder);
        }else {
            holder=(ViewHolder)v.getTag();
        }
        HashMap<String,String> data=mens_data.get(position);
        holder.name.setText(data.get(""));
        holder.size.setText(data.get(""));
        holder.price.setText(data.get(""));
        Picasso.with(context).load(data.get("")).into(holder.img);

        return v;
    }
    private class ViewHolder{
        ImageView img;
        TextView name,size,price;
    }
}
