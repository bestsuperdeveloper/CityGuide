package krt.com.cityguide.Fragments.EventsSubFragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import krt.com.cityguide.Adapters.ShoppingDetailAdapter;
import krt.com.cityguide.Adapters.ShoppingPlaceAdapter;
import krt.com.cityguide.CityGuideApplication;
import krt.com.cityguide.MainActivity;
import krt.com.cityguide.Models.ChurchTimeModel;
import krt.com.cityguide.Models.FirebaseObjectModel;
import krt.com.cityguide.Models.ObjectModel;
import krt.com.cityguide.R;
import krt.com.cityguide.Utils.Constants;
import krt.com.cityguide.Utils.Utils;


public class ShoppingDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ShoppingDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShoppingDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShoppingDetailFragment newInstance(String param1, String param2) {
        ShoppingDetailFragment fragment = new ShoppingDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    View mainView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mainView = inflater.inflate(R.layout.fragment_shopping_detail, container, false);
        initView();
        return mainView;
    }
    ProgressDialog progressDialog;
    private void showDialog()
    {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getString(R.string.str_loading));
        progressDialog.show();
    }

    private String modelKind;
    private void hideDialog()
    {
        progressDialog.dismiss();
    }

    ListView listView;
    MainActivity mainActivity;
    ShoppingPlaceAdapter adapter;
    TextView textTitle;
    private void initView()
    {
        listView = (ListView) mainView.findViewById(R.id.listView);
        mainActivity = (MainActivity) getActivity();
//        textTitle = (TextView) mainView.findViewById(R.id.textView16);
//        GetData();
        GetFirebaseData();
    }

    DatabaseReference ref;

    JSONArray jsonArray;
    List<FirebaseObjectModel> tempList;
    String url;
    private void GetFirebaseData() {
        if (mParam1.equals("shop"))
        {
            ref = FirebaseDatabase.getInstance().getReference(Constants.TABLE_COMPRAS);
            modelKind = Constants.TABLE_COMPRAS;
        }
        else if (mParam1.equals("attraction"))
        {
            ref = FirebaseDatabase.getInstance().getReference(Constants.TABLE_ATTRACTION);
            modelKind = Constants.TABLE_ATTRACTION;
        }
        else if (mParam1.equals("coffee"))
        {
            ref = FirebaseDatabase.getInstance().getReference(Constants.TABLE_CAFETERIA);
            modelKind = Constants.TABLE_CAFETERIA;
        }
        else if (mParam1.equals("arrive"))
        {
//            ref = FirebaseDatabase.getInstance().getReference(Constants.TABLE_);
        }
        else if (mParam1.equals("travel"))
        {
            ref = FirebaseDatabase.getInstance().getReference(Constants.TABLE_AGENCY);
            modelKind = Constants.TABLE_AGENCY;
        }
        else if (mParam1.equals("emer"))
        {
            ref = FirebaseDatabase.getInstance().getReference(Constants.TABLE_URGENCY);
            modelKind = Constants.TABLE_URGENCY;
        }
        showDialog();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tempList = new ArrayList<FirebaseObjectModel>();
                Log.d("post_value", dataSnapshot.toString());
                FirebaseObjectModel model;
                for (DataSnapshot postSnap : dataSnapshot.getChildren())
                {
                    model = postSnap.getValue(FirebaseObjectModel.class);
//                    model.calculateDistance(mainActivity);
                    tempList.add(model);
                    model.setObjectKind(modelKind);
                }

                Collections.sort(tempList, new Comparator<FirebaseObjectModel>() {
                    @Override
                    public int compare(FirebaseObjectModel s1, FirebaseObjectModel s2) {
                        double d1 = s1.getDistance();
                        double d2 = s2.getDistance();
                        if (d1 < d2) return -1;
                        if (d1 > d2) return 1;
                        return 0;
//                        return d1 - d2;
                    }
                });
                new task().execute();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                hideDialog();
            }
        });
    }

    private class task extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params) {
            for (FirebaseObjectModel model : tempList)
            {
                model.calculateDistance(getActivity());
            }
            Collections.sort(tempList, new Comparator<FirebaseObjectModel>() {
                @Override
                public int compare(FirebaseObjectModel s1, FirebaseObjectModel s2) {
                    double d1 = s1.getDistance();
                    double d2 = s2.getDistance();
                    if (d1 < d2) return -1;
                    if (d1 > d2) return 1;
                    return 0;
//                        return d1 - d2;
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
            adapter = new ShoppingPlaceAdapter(tempList, mainActivity);
            listView.setAdapter(adapter);
            hideDialog();
        }

    }

    // TODO: Rename method, update argument and hook method into UI event


}
