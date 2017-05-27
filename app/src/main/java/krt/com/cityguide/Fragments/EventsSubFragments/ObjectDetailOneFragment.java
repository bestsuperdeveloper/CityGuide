package krt.com.cityguide.Fragments.EventsSubFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import krt.com.cityguide.Models.FirebaseObjectModel;
import krt.com.cityguide.R;
import krt.com.cityguide.Utils.Utils;


public class ObjectDetailOneFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private FirebaseObjectModel mParam1;
    private String mParam2;


    public ObjectDetailOneFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ObjectDetailOneFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ObjectDetailOneFragment newInstance(FirebaseObjectModel param1, String param2) {
        ObjectDetailOneFragment fragment = new ObjectDetailOneFragment();
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
        mainView = inflater.inflate(R.layout.fragment_object_detail_one, container, false);
        initView();
        return mainView;
    }

    private void initView() {
        TextView textPhone, textPremium_6, textPremium_7, textPremium_3;
        TextView textCity, textStreet;
        textPhone = (TextView) mainView.findViewById(R.id.phone);
        textPremium_3 = (TextView) mainView.findViewById(R.id.premium_3);
        textPremium_6 = (TextView) mainView.findViewById(R.id.premium_6);
        textPremium_7 = (TextView) mainView.findViewById(R.id.premium_7);
        textCity = (TextView) mainView.findViewById(R.id.city);


        textStreet = (TextView) mainView.findViewById(R.id.street);
        try {
            textStreet.setText(Utils.avoidNullString(mParam1.getStreet()));
            textCity.setText(Utils.avoidNullString(mParam1.getCity()));
            textPhone.setText(Utils.avoidNullString(mParam1.getPhone()));
            if (mParam1.getPremium() != null) {
                textPremium_3.setText(Utils.avoidNullString(mParam1.getPremium().get("content_3")));
                textPremium_6.setText(Utils.avoidNullString(mParam1.getPremium().get("content_6")));
                textPremium_7.setText(Utils.avoidNullString(mParam1.getPremium().get("content_7")));
                textPremium_3.setMovementMethod(new ScrollingMovementMethod());
            }
            else
            {
                textPremium_3.setText("");
                textPremium_6.setText("");
                textPremium_7.setText("");
            }
        }
        catch (Exception e){
            Log.d("exception", e.getMessage());
        }
    }


}
