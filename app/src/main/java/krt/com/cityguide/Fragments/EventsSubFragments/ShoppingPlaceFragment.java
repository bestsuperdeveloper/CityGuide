package krt.com.cityguide.Fragments.EventsSubFragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import krt.com.cityguide.Adapters.CommonAdapter;
import krt.com.cityguide.Adapters.RecommendAdapter;
import krt.com.cityguide.Adapters.ShoppingDetailAdapter;
import krt.com.cityguide.CityGuideApplication;
import krt.com.cityguide.MainActivity;
import krt.com.cityguide.Models.FirebaseObjectModel;
import krt.com.cityguide.Models.ObjectModel;
import krt.com.cityguide.Models.ShoppingModel;
import krt.com.cityguide.R;
import krt.com.cityguide.Utils.Constants;
import krt.com.cityguide.Utils.Utils;
import krt.com.cityguide.custom.LoopViewPager;
import me.relex.circleindicator.CircleIndicator;

public class ShoppingPlaceFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private FirebaseObjectModel mParam1;
    private String mParam2;

    ViewPager pager;
    CircleIndicator indicator;

    public ShoppingPlaceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShoppingPlaceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShoppingPlaceFragment newInstance(FirebaseObjectModel param1, String param2) {
        ShoppingPlaceFragment fragment = new ShoppingPlaceFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = (FirebaseObjectModel) getArguments().getSerializable(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    View mainView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.fragment_shopping_place, container, false);
        initView();
        return mainView;
    }
    TextView textName, textBack;
    MainActivity mainActivity;
    ImageView restImage;
    int currentIndex;
    private void initView()
    {
        textName = (TextView) mainView.findViewById(R.id.textView17);
        textBack = (TextView) mainView.findViewById(R.id.textView19);
        textName.setText(Utils.avoidNullString(mParam1.getName()));
        restImage = (ImageView) mainView.findViewById(R.id.imageView2);
        HashMap<String, String> map = mParam1.getPremium();
        if (map == null) return;
        String url = mParam1.getPremium().get("content_1");
//        Log.d("imageurl", url);
        try {
            if (url != null && !url.equals(""))

            {
                StorageReference storage = FirebaseStorage.getInstance().getReferenceFromUrl(url);

                Glide.with(getContext()).using(new FirebaseImageLoader()).load(storage).into(restImage);
            }
            else
            {
                Toast.makeText(getContext(), getContext().getResources().getString(R.string.no_image), Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e){
            Toast.makeText(getContext(), getContext().getResources().getString(R.string.no_image), Toast.LENGTH_SHORT).show();
        }
        mainActivity = (MainActivity) getActivity();
        textBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (currentIndex == 0)
                    mainActivity.OnfromFragmentToActivity(0);
//                else
//                {
//                    pager.setCurrentItem(currentIndex - 1);
//                }
            }
        });
        pager = (ViewPager) mainView.findViewById(R.id.viewPager);
        indicator = (CircleIndicator) mainView.findViewById(R.id.indicator);
        pager.setAdapter(new ShoppingDetailAdapter(getChildFragmentManager(), mParam1));
        indicator.setViewPager(pager);
        pager.setCurrentItem(0);
        currentIndex = 0;
//        pager.setOn
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                Toast.makeText(getContext(), "" + position , Toast.LENGTH_SHORT).show();
                currentIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


}
