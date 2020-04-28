package com.grantech.cinnagen.solife.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.grantech.cinnagen.solife.R;
import com.grantech.cinnagen.solife.utils.FontsOverride;
import com.grantech.cinnagen.solife.utils.PersianCalendar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class InboxAdapter extends BaseExpandableListAdapter
{
    private List<Message> messages;
    private AppCompatActivity activity;
    public OnDeleteListener onDeleteListener;
    public OnReadListener onReadListener;


    /**
     * Classes that wish to be notified when delete button confirm.
     */
    public interface OnReadListener {
        /**
         * Called when delete confirm.
         */
        void onRead(int position);
    }

    /**
     * Classes that wish to be notified when delete button confirm.
     */
    public interface OnDeleteListener {
        /**
         * Called when delete confirm.
         */
        void onDelete(Message group);
    }

    @SuppressLint("UseSparseArrays")
    public InboxAdapter(AppCompatActivity activity)
    {
        this.activity = activity;
        this.messages = new ArrayList<>();
    }

    public void addGroup(Message message)
    {
        this.messages.add(message);
    }

    public void removeGroup(Message message)
    {
        this.messages.remove(message);
    }


    @Override
    public Object getGroup(int listPosition) {
        return this.messages.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.messages.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    @SuppressLint({"InflateParams", "ClickableViewAccessibility"})
    public View getGroupView(int listPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {
        Message message = (Message) getGroup(listPosition);
        if( convertView == null )
        {
            LayoutInflater layoutInflater = (LayoutInflater) this.activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert layoutInflater != null;
            convertView = layoutInflater.inflate(R.layout.list_expandable_group, null);
        }
        if( isExpanded && !message.read){
            message.read = true;
            this.onReadListener.onRead(listPosition);
        }
        AppCompatTextView listTitleTextView = convertView.findViewById(R.id.listTitle);
        listTitleTextView.setTypeface(listTitleTextView.getTypeface(), message.read ? Typeface.ITALIC : Typeface.BOLD);
        listTitleTextView.setText(message.title);
        return convertView;
    }


    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return messages.get(listPosition).text;
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return 1;
    }

    @Override
    @SuppressLint({"InflateParams", "ClickableViewAccessibility"})
    public View getChildView(int listPosition, final int expandedListPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {
        Message message = (Message) getGroup(listPosition);
        if( convertView == null ) {
            LayoutInflater layoutInflater = (LayoutInflater) this.activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert layoutInflater != null;
            convertView = layoutInflater.inflate(R.layout.list_inbox_item, null);
        }

        AppCompatTextView messageText = convertView.findViewById(R.id.inbox_message_text);
        messageText.setText(message.text);

        AppCompatTextView receivedAtText = convertView.findViewById(R.id.inbox_receive_text);
        PersianCalendar date = new PersianCalendar();
        date.setTimeInMillis(message.receivedAt);
        receivedAtText.setText(FontsOverride.convertToPersianDigits(date.getPersianLongDateAndTime()));

//        convertView.findViewById(R.id.inbox_share_button).setOnClickListener(v -> Toast.makeText(activity, "share", Toast.LENGTH_SHORT).show());
        convertView.findViewById(R.id.inbox_delete_button).setOnClickListener(v -> confirmDelete(message));
        return convertView;
    }

    private void confirmDelete(Message group) {
        new AlertDialog.Builder(activity)
                .setTitle(R.string.about_tel_button)
                .setMessage(R.string.about_tel_message)
                .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> {
                    onDeleteListener.onDelete(group);
                    removeGroup(group);
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

    public static class Message implements Serializable {
        private static final long serialVersionUID = 7829136421234571165L;
        public String title;
        public boolean read;
        public long receivedAt;
        public String text;
        public Message(String title, String text, long receivedAt, boolean read){
            super();
            this.read = read;
            this.title = title;
            this.text = text;
            this.receivedAt = receivedAt;
        }

    }
}