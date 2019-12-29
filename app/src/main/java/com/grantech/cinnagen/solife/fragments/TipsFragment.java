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

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Belal on 1/23/2018.
 */

public class TipsFragment extends BaseFragment
{
    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_tips, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        expandableListAdapter = new ExpandableListAdapter(Objects.requireNonNull(getContext()).getApplicationContext());
        expandableListAdapter.addChild(R.string.tips_0_title, new ArrayList<Integer>(){{add(R.string.tips_0_message);}});
        expandableListAdapter.addChild(R.string.tips_4_title, new ArrayList<Integer>(){{add(R.string.tips_4_message);}});
        expandableListAdapter.addChild(R.string.tips_7_title, new ArrayList<Integer>(){{add(R.string.tips_7_message);}});
        expandableListAdapter.addChild(R.string.tips_8_title, new ArrayList<Integer>(){{add(R.string.tips_8_message);}});
        expandableListAdapter.addChild(R.string.tips_1_title, new ArrayList<Integer>(){{add(R.string.tips_1_message_0);add(R.string.tips_1_message_1);add(R.string.tips_1_message_2);add(R.string.tips_1_message_3);add(R.string.tips_1_message_4);}});
        expandableListAdapter.addChild(R.string.tips_2_title, new ArrayList<Integer>(){{add(R.string.tips_2_message);}});
        expandableListAdapter.addChild(R.string.tips_5_title, new ArrayList<Integer>(){{add(R.string.tips_5_message);}});
        expandableListAdapter.addChild(R.string.tips_6_title, new ArrayList<Integer>(){{add(R.string.tips_6_message);}});
        expandableListAdapter.addChild(R.string.tips_3_title, new ArrayList<Integer>(){{add(R.string.tips_3_message);}});

        expandableListView = view.findViewById(R.id.expandableListView);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(groupPosition -> {
            if( expandableListAdapter == null )
                return;
            for( int i = 0; i < expandableListAdapter.getGroupCount(); i++ )
                if( i != groupPosition )
                    expandableListView.collapseGroup(i);
        });

/*        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition)
            {
              Toast.makeText(getContext().getApplicationContext(), expandableListTitle.get(groupPosition) + " List Collapsed.", Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getContext().getApplicationContext(),
                        expandableListTitle.get(groupPosition)
                                + " -> "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT
                )
                        .show();
                return false;
            }
        });*/
    }
}