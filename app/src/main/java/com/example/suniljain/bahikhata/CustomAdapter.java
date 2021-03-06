package com.example.suniljain.bahikhata;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter{
    Context context;
    LayoutInflater layoutInflater;

    CustomAdapter(Context context){
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return MainActivity.no_of_persons;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.listview_list_item, null);
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvAmount = (TextView) convertView.findViewById(R.id.tvAmount);

        SharedPreferences sharedPreferences = context.getSharedPreferences("sharedprefs", Context.MODE_PRIVATE);

        if(sharedPreferences.getString(position + "", null) != null){
            tvName.setText(sharedPreferences.getString(position + "", null));
        }

        if(sharedPreferences.getInt("amount" + position + "", 0) != 0){
            tvAmount.setText(sharedPreferences.getInt("amount" + position + "", 0));
        }

        return convertView;
    }

   /* private void getMySharedPreference(){

        return ;
    }*/
}
