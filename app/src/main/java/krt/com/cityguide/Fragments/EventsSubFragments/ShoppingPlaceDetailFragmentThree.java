package krt.com.cityguide.Fragments.EventsSubFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import krt.com.cityguide.Models.FirebaseObjectModel;
import krt.com.cityguide.Models.ObjectModel;
import krt.com.cityguide.Models.ShoppingModel;
import krt.com.cityguide.R;


public class ShoppingPlaceDetailFragmentThree extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private FirebaseObjectModel mParam1;
    private String mParam2;


    public ShoppingPlaceDetailFragmentThree() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShoppingPlaceDetailFragmentThree.
     */
    // TODO: Rename and change types and number of parameters
    public static ShoppingPlaceDetailFragmentThree newInstance(FirebaseObjectModel param1, String param2) {
        ShoppingPlaceDetailFragmentThree fragment = new ShoppingPlaceDetailFragmentThree();
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
        return inflater.inflate(R.layout.fragment_shopping_place_detail_fragment_three, container, false);
    }


}
