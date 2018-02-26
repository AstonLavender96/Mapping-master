package com.example.a2lavea02.mapping;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ListView;
import android.widget.TextView;
import android.content.Context;

/**
 * Created by 2lavea02 on 26/02/2018.
 */

public class ListViewActivity extends ListActivity
{
    String[] data;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        data = new String[] {"Bike Map View", "Hike Bike Map View" };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
        setListAdapter(adapter);
    }
    public void onListItemClick(ListView lv, View view, int index, long id)
    {

    }
    public class MyAdapter extends ArrayAdapter<String>
    {
        public MyAdapter()
        {
            super(ListViewActivity.this, android.R.layout.simple_list_item_1, data);
        }

        public View getView(int index, View convertView, ViewGroup parent)
        {
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.poientry, parent, false);
            TextView title = (TextView)view.findViewById(R.id.poi_desc),
                    detail = (TextView)view.findViewById(R.id.poi_desc);
            title.setText(data[index]);
            detail.setText(details[index]);
            return view;
        }
    }
}
