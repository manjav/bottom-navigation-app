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
        expandableListDetail.put(R.string.title_navi_0, new ArrayList<Integer>(){{add(R.string.title_navi_0);}});
        expandableListDetail.put(R.string.title_navi_1, new ArrayList<Integer>(){{add(R.string.title_navi_1);}});
        expandableListDetail.put(R.string.title_navi_2, new ArrayList<Integer>(){{add(R.string.title_navi_2);}});
        return expandableListDetail;
    }
}