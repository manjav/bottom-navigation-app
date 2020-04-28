package com.grantech.cinnagen.solife.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.grantech.cinnagen.solife.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class InboxAdapter extends BaseExpandableListAdapter
{
    private AppCompatActivity activity;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListMessages;
    public OnDeleteListener onDeleteListener;


    /**
     * Classes that wish to be notified when delete button confirm.
     */
    public interface OnDeleteListener {
        /**
         * Called when delete confirm.
         */
        void onDelete(String group);
    }

    @SuppressLint("UseSparseArrays")
    public InboxAdapter(AppCompatActivity activity)
    {
        this.activity = activity;
        this.expandableListTitle = new ArrayList<>();
        this.expandableListMessages = new HashMap<>();
    }

    public void addChild(String title, List<String> messages)
    {
        this.expandableListTitle.add(title);
        this.expandableListMessages.put(title, messages);
    }

    public void removeChild(String title)
    {
        this.expandableListTitle.remove(title);
        this.expandableListMessages.remove(title);
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
    @SuppressLint({"InflateParams", "ClickableViewAccessibility"})
    public View getChildView(int listPosition, final int expandedListPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {
        final String expandedListText = (String) getChild(listPosition, expandedListPosition);
        if( convertView == null )
        {
            LayoutInflater layoutInflater = (LayoutInflater) this.activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert layoutInflater != null;
            convertView = layoutInflater.inflate(R.layout.list_inbox_item, null);
        }
        AppCompatTextView expandedListTextView = convertView.findViewById(R.id.expandedListItem);
        expandedListTextView.setText(expandedListText);

        convertView.findViewById(R.id.inbox_share_button).setOnClickListener(v -> Toast.makeText(activity, "share", Toast.LENGTH_SHORT).show());
        convertView.findViewById(R.id.inbox_delete_button).setOnClickListener(v -> confirmDelete((String) getGroup(listPosition)));
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
    @SuppressLint({"InflateParams", "ClickableViewAccessibility"})
    public View getGroupView(int listPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {
        String listTitle = (String) getGroup(listPosition);
        if( convertView == null )
        {
            LayoutInflater layoutInflater = (LayoutInflater) this.activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert layoutInflater != null;
            convertView = layoutInflater.inflate(R.layout.list_expandable_group, null);
        }
        AppCompatTextView listTitleTextView = convertView.findViewById(R.id.listTitle);
        listTitleTextView.setText(listTitle);
        return convertView;
    }

    private void confirmDelete(String group) {
        new AlertDialog.Builder(activity)
                .setTitle(R.string.about_tel_button)
                .setMessage(R.string.about_tel_message)
                .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> {
                    onDeleteListener.onDelete(group);
                    removeChild(group);
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
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