package krt.com.cityguide.Adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.halfbit.pinnedsection.PinnedSectionListView;
import krt.com.cityguide.Models.ChurchModel;
import krt.com.cityguide.Models.ChurchTimeModel;
import krt.com.cityguide.R;
import krt.com.cityguide.Utils.Utils;

/**
 * Created by bryden on 2/13/17.
 */

public class NewChurchAdapter extends BaseAdapter implements PinnedSectionListView.PinnedSectionListAdapter {


    private List<ChurchModel> originalList = new ArrayList<>();
    private List<ChurchTimeModel> timeModels= new ArrayList<>();
    private List<ChurchTimeModel> findtimeModels= new ArrayList<>();
    private Activity context;
    private String searchString = "";
    LayoutInflater layoutInflater;
    public NewChurchAdapter(Activity context, List<ChurchModel> originalList) {
        this.context = context;
        this.originalList.addAll(originalList);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        searchData();
        searchString("");
    }

    public void searchString(String string)
    {
        findtimeModels = new ArrayList<>();
        List<ChurchTimeModel> temp  = new ArrayList<>();
        if (string.equals(""))
        {
            int listIndex = 0;
            for (int index = 0; index < 7; index ++) {
                ChurchTimeModel model = new ChurchTimeModel("day", Utils.getDayString(index), "");
                model.setSectionIndex(index);
                model.setListIndex(listIndex++);

                temp = new ArrayList<>();
                for (ChurchModel churchModel : originalList) {
                    for (ChurchTimeModel timeModel : churchModel.allOpenTimeList(index)) {
                        timeModel.setListIndex(listIndex++);
                        timeModel.setSectionIndex(index);
                        temp.add(timeModel);
                    }
                }
                Collections.sort(temp, new Comparator<ChurchTimeModel>() {
                    @Override
                    public int compare(ChurchTimeModel s1, ChurchTimeModel s2) {
                        int du1 = Utils.getSecondsFromAMPM(s1.getStarttime());
                        int du2 = Utils.getSecondsFromAMPM(s2.getStarttime());
                        return du1 - du2;
                    }
                });
                if (temp.size() > 0) {
                    findtimeModels.add(model);
                    findtimeModels.addAll(temp);
                }
            }
            Log.d("find_size", findtimeModels.size() + " " );
            notifyDataSetChanged();
        }
        else
        {
            boolean flag = false;
            int listIndex = 0;
            for (int index = 0; index < 7; index ++) {
                ChurchTimeModel model = new ChurchTimeModel("day", Utils.getDayString(index), "");
                model.setSectionIndex(index);
                model.setListIndex(listIndex++);

                temp = new ArrayList<>();
                for (ChurchModel churchModel : originalList) {
                    if (churchModel.getName().equals(string)) {
                        for (ChurchTimeModel timeModel : churchModel.allOpenTimeList(index)) {
                            timeModel.setListIndex(listIndex++);
                            timeModel.setSectionIndex(index);
                            temp.add(timeModel);
                        }
                    }
                }
                Collections.sort(temp, new Comparator<ChurchTimeModel>() {
                    @Override
                    public int compare(ChurchTimeModel s1, ChurchTimeModel s2) {
                        int du1 = Utils.getSecondsFromAMPM(s1.getStarttime());
                        int du2 = Utils.getSecondsFromAMPM(s2.getStarttime());
                        return du1 - du2;
                    }
                });
                if (temp.size() > 0) {
                    findtimeModels.add(model);
                    findtimeModels.addAll(temp);
                }
            }

            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return findtimeModels.size();
    }

    @Override
    public ChurchTimeModel getItem(int position) {
        return findtimeModels.get(position);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        ChurchTimeModel model = getItem(position);
        if (model.getDuration().equals("day"))
        {
            return 1;
        }
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        ChurchTimeModel churchTimeModel = getItem(position);
        if (convertView == null)
        {
            if (type == 0)
                convertView = layoutInflater.inflate(R.layout.church_list_item, null);
            else
                convertView = layoutInflater.inflate(R.layout.church_group_item, null);
        }
        if (type == 0) {
            TextView textView1 = (TextView) convertView.findViewById(R.id.textView9);
            TextView textView2 = (TextView) convertView.findViewById(R.id.textView10);

            textView1.setText(churchTimeModel.getChurchName());
            textView2.setText(churchTimeModel.getStarttime());
        }
        else
        {
            TextView textView = (TextView) convertView.findViewById(R.id.textView9);

            TextView textholiday = (TextView) convertView.findViewById(R.id.textView23);
            if (Utils.isHoliday(churchTimeModel.getSectionIndex())) {
                textholiday.setText(context.getResources().getString(R.string.holiday));
                textholiday.setVisibility(View.VISIBLE);

            }
            else {
                textholiday.setText("");
                textholiday.setVisibility(View.GONE);
            }
            textView.setText(Utils.getTodayString(context, churchTimeModel.getSectionIndex()));
//
        }
        return convertView;
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return (viewType == 1);
    }
}
