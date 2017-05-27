package krt.com.cityguide.Fragments.EventsSubFragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import krt.com.cityguide.Adapters.ShoppingDetailAdapter;
import krt.com.cityguide.Adapters.ShoppingPlaceAdapter;
import krt.com.cityguide.MainActivity;
import krt.com.cityguide.Models.FirebaseObjectModel;
import krt.com.cityguide.Models.ShoppingModel;
import krt.com.cityguide.R;
import krt.com.cityguide.custom.LoopViewPager;
import me.relex.circleindicator.CircleIndicator;

public class ShoppingPlaceDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    LoopViewPager pager;
    CircleIndicator indicator;
    // TODO: Rename and change types of parameters
    private FirebaseObjectModel mParam1;
    private String mParam2;

    MainActivity mainActivity;
    ShoppingPlaceAdapter adapter;
    View mainView;
    ListView listView;
    public ShoppingPlaceDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShoppingPlaceDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShoppingPlaceDetailFragment newInstance(FirebaseObjectModel param1, String param2) {
        ShoppingPlaceDetailFragment fragment = new ShoppingPlaceDetailFragment();
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



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.fragment_shopping_place_detail, container, false);
        initView();
        return mainView;
    }

    private void initView() {
        pager = (LoopViewPager) mainView.findViewById(R.id.viewPager);
        indicator = (CircleIndicator) mainView.findViewById(R.id.indicator);
        pager.setAdapter(new ShoppingDetailAdapter(getChildFragmentManager(), mParam1));
        indicator.setViewPager(pager);
        pager.setCurrentItem(0);
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Toast.makeText(getContext(), "" + position , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


}
