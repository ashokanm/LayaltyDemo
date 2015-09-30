package com.tritonitsolutions.loyaltydemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tritonitsolutions.layaltydemo.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by TritonDev on 28/9/2015.
 */
public class WomensAdapter extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String,String>> womens_data;
    LayoutInflater inflater;
    public WomensAdapter(Context context,ArrayList<HashMap<String,String>> mens_data){
        this.context=context;
        this.womens_data=mens_data;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return womens_data.size();
    }

    @Override
    public Object getItem(int position) {
        return womens_data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return womens_data.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        ViewHolder holder=null;
        if(convertView==null){
            vi=inflater.inflate(R.layout.women_row,parent,false);
            holder=new ViewHolder();
            holder.womens_category=(TextView)vi.findViewById(R.id.tv_women_category);
            holder.price_range=(TextView)vi.findViewById(R.id.tv_women_pricerange);
            vi.setTag(holder);
        }else {
            holder=(ViewHolder)vi.getTag();
        }
        HashMap<String,String> mens_list=womens_data.get(position);
        holder.womens_category.setText(mens_list.get(WomenActivity.TAG_WOMENS_NAME));
        holder.price_range.setText(mens_list.get(WomenActivity.TAG_PRICE_RANGE));
        return vi;
    }
    private class ViewHolder{
        TextView womens_category,price_range;

    }
}
