package com.grantech.cinnagen.solife.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.grantech.cinnagen.solife.controls.CheckableItem;

import java.util.Arrays;

public class CheckableListAdapter extends ArrayAdapter
{
    public CheckableListAdapter(Context context, int resource, String[] objects)
    {
        super(context, resource, Arrays.asList(objects));
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent)
    {
        if( convertView == null )
            convertView = new CheckableItem(getContext());

        ((CheckableItem) convertView).setLabel((String) getItem(position));
        return convertView;
    }

/*
    public static void autoSize(ListView listView)
    {
        ListAdapter listAdapter = listView.getAdapter();
        if( listAdapter == null )
            return;

        // Get total height of all items.
        int numberOfItems = listAdapter.getCount();
        int totalItemsHeight = 0;
        for (int i = 0; i < numberOfItems; i++)
        {
            CheckableItem item = (CheckableItem) listAdapter.getView(i, null, listView);
//            item.measureText();
            item.measure(0,0);//View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.EXACTLY));
//            Log.i(Fragments.TAG, i +" " + item.getMeasuredHeight() + " " + item.getLayoutParams());
            totalItemsHeight += item.getMeasuredHeight();
        }

        // Get total height of all item dividers.
        int totalDividersHeight = listView.getDividerHeight() * (numberOfItems - 1);
        // Get padding
        int totalPadding = listView.getPaddingTop() + listView.getPaddingBottom();

        // Set list height.
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalItemsHeight + totalDividersHeight + totalPadding;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
*/
}