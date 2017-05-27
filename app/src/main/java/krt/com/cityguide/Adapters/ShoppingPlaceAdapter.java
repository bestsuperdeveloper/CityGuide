package krt.com.cityguide.Adapters;

import android.app.Activity;
import android.content.Context;
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
import krt.com.cityguide.Models.ShoppingModel;
import krt.com.cityguide.R;

/**
 * Created by bryden on 1/10/17.
 */

public class ShoppingPlaceAdapter extends BaseAdapter {

    List<FirebaseObjectModel> list;
    MainActivity activity;

    LayoutInflater layoutInflater;
    public ShoppingPlaceAdapter(List<FirebaseObjectModel> list, MainActivity activity) {
        this.list = list;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public FirebaseObjectModel getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
        {
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.shopping_list_item, null);
        }
        TextView textView1, textView2;
        ImageButton imageButton;
        textView1 = (TextView) convertView.findViewById(R.id.textView9);
        textView2 = (TextView) convertView.findViewById(R.id.textView10);
        imageButton = (ImageButton) convertView.findViewById(R.id.imageButton5);


        final FirebaseObjectModel shoppingModel = getItem(position);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.shoppingModel = shoppingModel;
                try {
//                    activity.ReplceFragment(8);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        textView1.setText(shoppingModel.getName());
        textView2.setText(String.format("%.2f", shoppingModel.getDistance()) + "km");
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.shoppingModel = shoppingModel;
                activity.ReplceFragment(8);
            }
        });
        return convertView;
    }
}
