package com.grantech.cinnagen.solife.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.grantech.cinnagen.solife.R;
import com.grantech.cinnagen.solife.adapters.InboxAdapter;
import com.grantech.cinnagen.solife.utils.Prefs;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Mansour Djawadi on 1/23/2018.
 */

public class InboxFragment extends BaseFragment
{
    private ExpandableListView inboxList;
    private InboxAdapter inboxAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_tips, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        Map<String, String> messages = Prefs.getInstance().messages;
        inboxAdapter = new InboxAdapter(Objects.requireNonNull(activity));
        for (Map.Entry<String, String> e:messages.entrySet())
            inboxAdapter.addChild(e.getKey(), Arrays.asList(e.getValue()));
        inboxList = view.findViewById(R.id.expandableListView);
        inboxList.setAdapter(inboxAdapter);
        inboxList.setOnGroupExpandListener(groupPosition -> {
            if( inboxAdapter == null )
                return;
            for(int i = 0; i < inboxAdapter.getGroupCount(); i++ )
                if( i != groupPosition )
                    inboxList.collapseGroup(i);
        });

        inboxAdapter.onDeleteListener = (String group) -> {
            inboxList.setAdapter(inboxAdapter);
            messages.remove(group);
            Prefs.getInstance().setObject(Prefs.KEY_MESSAGES, messages);
        };
    }
}