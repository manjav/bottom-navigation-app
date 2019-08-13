package com.grantech.cinnagen.solife.utils;

import com.grantech.cinnagen.solife.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump
{
    public static HashMap<Integer, List<Integer>> getData()
    {
        HashMap<Integer, List<Integer>> expandableListDetail = new HashMap<>();
        expandableListDetail.put(R.string.home_injection, new ArrayList<Integer>(){{add(R.string.home_injection);}});
        expandableListDetail.put(R.string.home_calendar, new ArrayList<Integer>(){{add(R.string.home_calendar);}});
        expandableListDetail.put(R.string.home_tips, new ArrayList<Integer>(){{add(R.string.home_tips);}});
        return expandableListDetail;
    }
}