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
    String[] data, description;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        data = new String[] {"Bike Map View", "Hike Bike Map View" };
        description = new String[] {"This will show you the normal bike map view.", "This will show you the Hike Bike map view."};
        ArrayAdapter<String> adapter = new MyAdapter();
        setListAdapter(adapter);
    }
    public void onListItemClick(ListView lv, View view, int index, long id)
    {
        super.onListItemClick(lv, view, index, id);



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
            View view = inflater.inflate(R.layout.activity_list_view, parent, false);
            TextView title = (TextView)view.findViewById(R.id.list_name),
                    detail = (TextView)view.findViewById(R.id.list_desc);
            title.setText(data[index]);
            detail.setText(description[index]);
            return view;
        }

    }
}
