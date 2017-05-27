package krt.com.cityguide.Adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import krt.com.cityguide.MainActivity;
import krt.com.cityguide.Models.FirebaseObjectModel;
import krt.com.cityguide.Models.ObjectModel;
import krt.com.cityguide.R;

/**
 * Created by bryden on 1/4/17.
 */

public class RecommendAdapter extends BaseAdapter {

    private MainActivity context;
    private List<FirebaseObjectModel> list;
    private LayoutInflater layoutInflater;
    public RecommendAdapter() {
    }

    public RecommendAdapter(MainActivity context, List<FirebaseObjectModel> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public FirebaseObjectModel getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null)
        {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.recommend_list_item, null);
        }

        TextView textView1, textView2, textView3;

        textView1 = (TextView) view.findViewById(R.id.textView9);
        textView2 = (TextView) view.findViewById(R.id.textView10);
        FirebaseObjectModel model = getItem(i);
        textView1.setText(String.format("%.1f", model.getDistance()) + "Km");
        textView2.setText(model.getName());
        ImageButton imageButton = (ImageButton) view.findViewById(R.id.imageButton5);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getItem(i).getPremium() == null) return;
                context.shoppingModel = getItem(i);

                context.HotelParkingRestaurant();
//                context.shoppingModel = getItem(i);
//                context.ReplceFragment(8);

            }
        });
//        textView1 = (TextView) view.findViewById(R.id.textView9);
        return view;
    }
}
