package com.grantech.cinnagen.solife.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.grantech.cinnagen.solife.controls.CheckableItem;

import java.util.Arrays;

public class CheckableListAdapter extends ArrayAdapter
{
    public CheckableListAdapter(Context context, int resource, String[] objects)
    {
        super(context, resource, Arrays.asList(objects));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if( convertView == null )
            convertView = new CheckableItem(getContext());

        ((CheckableItem) convertView).setLabel((String) getItem(position));
        return convertView;
    }
}