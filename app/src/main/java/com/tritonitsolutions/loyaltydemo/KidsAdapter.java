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
public class KidsAdapter extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String,String>> kids_data;
    LayoutInflater inflater;
    public KidsAdapter(Context context,ArrayList<HashMap<String,String>> mens_data){
        this.context=context;
        this.kids_data=mens_data;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return kids_data.size();
    }

    @Override
    public Object getItem(int position) {
        return kids_data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return kids_data.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        ViewHolder holder=null;
        if(convertView==null){
            vi=inflater.inflate(R.layout.kids_row,parent,false);
            holder=new ViewHolder();
            holder.kids_category=(TextView)vi.findViewById(R.id.tv_kids_category);
            holder.price_range=(TextView)vi.findViewById(R.id.tv_kids_pricerange);
            vi.setTag(holder);
        }else {
            holder=(ViewHolder)vi.getTag();
        }
        HashMap<String,String> mens_list=kids_data.get(position);
        holder.kids_category.setText(mens_list.get(KidsActivity.TAG_KIDS_NAME));
        holder.price_range.setText(mens_list.get(KidsActivity.TAG_PRICE_RANGE));

        return vi;
    }
    private class ViewHolder{
        TextView kids_category,price_range;

    }
}
