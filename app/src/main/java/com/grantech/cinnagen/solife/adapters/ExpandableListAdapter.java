package com.grantech.cinnagen.solife.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import androidx.appcompat.widget.AppCompatTextView;

import com.grantech.cinnagen.solife.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ExpandableListAdapter extends BaseExpandableListAdapter
{
    private Context context;
    private List<Integer> expandableListTitle;
    private HashMap<Integer, List<Integer>> expandableListMessages;

    @SuppressLint("UseSparseArrays")
    public ExpandableListAdapter(Context context)
    {
        this.context = context;
        this.expandableListTitle = new ArrayList<>();
        this.expandableListMessages = new HashMap<>();
    }

    public void addChild(int title, ArrayList<Integer> messages)
    {
        this.expandableListTitle.add(title);
        this.expandableListMessages.put(title, messages);
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return Objects.requireNonNull(this.expandableListMessages.get(this.expandableListTitle.get(listPosition))).get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    @SuppressLint("InflateParams")
    public View getChildView(int listPosition, final int expandedListPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {
        final Integer expandedListText = (Integer) getChild(listPosition, expandedListPosition);
        if( convertView == null )
        {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert layoutInflater != null;
            convertView = layoutInflater.inflate(R.layout.list_item, null);
        }
        AppCompatTextView expandedListTextView = convertView.findViewById(R.id.expandedListItem);
        expandedListTextView.setText(expandedListText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return Objects.requireNonNull(this.expandableListMessages.get(this.expandableListTitle.get(listPosition))).size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    @SuppressLint("InflateParams")
    public View getGroupView(int listPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {
        Integer listTitle = (Integer) getGroup(listPosition);
        if( convertView == null )
        {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert layoutInflater != null;
            convertView = layoutInflater.inflate(R.layout.list_group, null);
        }
        AppCompatTextView listTitleTextView = convertView.findViewById(R.id.listTitle);
        listTitleTextView.setText(listTitle);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }

}