package krt.com.cityguide.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import krt.com.cityguide.MainActivity;
import krt.com.cityguide.Models.FirebaseObjectModel;
import krt.com.cityguide.Models.ObjectModel;
import krt.com.cityguide.R;
import krt.com.cityguide.Utils.Constants;
import krt.com.cityguide.Utils.Utils;
import krt.com.cityguide.custom.CustomRatingBar;

/**
 * Created by bryden on 1/4/17.
 */

public class CommonAdapter extends BaseAdapter {

    private MainActivity context;
    private List<FirebaseObjectModel> list;
    private LayoutInflater layoutInflater;

    public CommonAdapter() {
    }

    public CommonAdapter(MainActivity context, List<FirebaseObjectModel> list) {
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
            view = layoutInflater.inflate(R.layout.common_list_item, null);
        }

        TextView textView1, textView2, textView3;

        textView1 = (TextView) view.findViewById(R.id.textView9);
        textView2 = (TextView) view.findViewById(R.id.textView10);
        CustomRatingBar ratingBar = (CustomRatingBar) view.findViewById(R.id.ratingBar1);
        ratingBar.setEnabled(false);
        ratingBar.setScrollToSelect(false);
        FirebaseObjectModel model = getItem(i);
        if (model != null) {
//        if (model.getRate() != null)
            ratingBar.setScore((float) model.getRate());
            textView1.setText(String.format("%.1f", model.getDistance()) + "Km");
            textView2.setText(model.getName());
        }
        ratingBar.setOnScoreChanged(new CustomRatingBar.IRatingBarCallbacks() {
            @Override
            public void scoreChanged(float score) {
//                Toast.makeText(context, "" + score, Toast.LENGTH_LONG).show();
                if (Utils.currentUser == null)
                    Toast.makeText(context, context.getString(R.string.request_login), Toast.LENGTH_LONG).show();
                else
                {

                }
            }
        });

        ImageButton imageButton = (ImageButton) view.findViewById(R.id.imageButton5);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.shoppingModel = getItem(i);
                context.HotelParkingRestaurant();
            }
        });
//        textView1 = (TextView) view.findViewById(R.id.textView9);
        return view;
    }
}

