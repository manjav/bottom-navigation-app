package com.grantech.cinnagen.solife.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.grantech.cinnagen.solife.R;
import com.grantech.cinnagen.solife.adapters.ExpandableListAdapter;
import com.grantech.cinnagen.solife.adapters.InboxAdapter;
import com.grantech.cinnagen.solife.utils.Prefs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Created by Belal on 1/23/2018.
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

        Set<Map.Entry<String, String>> messages = Prefs.getInstance().messages.entrySet();
        inboxAdapter = new InboxAdapter(Objects.requireNonNull(getContext()).getApplicationContext());
        for (Map.Entry<String, String> e:messages)
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
    }
}