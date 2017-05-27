package krt.com.cityguide.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import krt.com.cityguide.MainActivity;
import krt.com.cityguide.Models.ChurchModel;
import krt.com.cityguide.Models.ChurchTimeModel;
import krt.com.cityguide.Models.ObjectModel;
import krt.com.cityguide.R;
import krt.com.cityguide.Utils.Utils;

/**
 * Created by bryden on 1/10/17.
 */

public class ChurchAdapter extends BaseExpandableListAdapter {

    List<ChurchModel> list = new ArrayList<>();
    List<ChurchModel> originalList = new ArrayList<>();
    List<String> dateList = new ArrayList<>();
    List<String> holidayList = new ArrayList<>();
    MainActivity activity;
    int flag ;
    List<List<ChurchTimeModel>> listChurch;
    LayoutInflater layoutInflater;
    Date date;

    public ChurchAdapter(List<ChurchModel> list, MainActivity activity, Date date) {
        this.list = new ArrayList<>();
        this.originalList = new ArrayList<>();
        listChurch = new ArrayList<>();
        holidayList = new ArrayList<>();
        this.list.addAll(list);
        this.originalList.addAll(list);
        this.activity = activity;
        this.date = date;
        setData();
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getGroupCount() {
        return listChurch.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listChurch.get(groupPosition).size();
    }

    @Override
    public List<ChurchTimeModel> getGroup(int groupPosition) {
        return listChurch.get(groupPosition);
    }

    @Override
    public ChurchTimeModel getChild(int groupPosition, int childPosition) {
        return getGroup(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null)
        {
            convertView = layoutInflater.inflate(R.layout.church_group_item, null);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.textView9);

        TextView textholiday = (TextView) convertView.findViewById(R.id.textView23);

        textView.setText(dateList.get(groupPosition));
        textholiday.setText(holidayList.get(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null)
        {
            convertView = layoutInflater.inflate(R.layout.church_list_item, null);
        }

        TextView textView1 = (TextView) convertView.findViewById(R.id.textView9);
        TextView textView2 = (TextView) convertView.findViewById(R.id.textView10);
        ChurchTimeModel churchTimeModel = getChild(groupPosition, childPosition);
        textView1.setText(churchTimeModel.getChurchName());
        textView2.setText(churchTimeModel.getStarttime());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public void setData()
    {
        listChurch = new ArrayList<>();
        dateList = new ArrayList<>();
        holidayList = new ArrayList<>();
        List<ChurchTimeModel> temp = new ArrayList<>();
        for (int index = 0; index < 7; index++) {
            for (ChurchModel model : list) {
                for (ChurchTimeModel timeModel : model.currentOpenTimeList(index)) {
                    temp.add(timeModel);
                    Log.d("church_model", timeModel.getChurchName());
                    if (Utils.isHoliday(index)) {
                        holidayList.add(activity.getResources().getString(R.string.holiday));
                    } else {
                        holidayList.add("");
                    }
                }
            }
        }



        notifyDataSetChanged();
    }
    public void filterData(String query)
    {
        Log.d("search", "search");
        list.clear();;
        Log.d("original_size", originalList.size() + "size");
        if (query.equals(""))
        {
            list.addAll(originalList);
        }
        else
        {
            for (ChurchModel model : originalList)
            {
                if (model.getName().toLowerCase().contains(query.toLowerCase()))
                {
                    Log.d("name name", model.getName() + " " + query);
                    list.add(model);
                }
            }
        }
        Log.d("list_size", list.size() + "");
        setData();
        notifyDataSetChanged();
    }


}
