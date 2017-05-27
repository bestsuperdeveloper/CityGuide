package krt.com.cityguide.Fragments.EventsSubFragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.PopupWindowCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import de.halfbit.pinnedsection.PinnedSectionListView;
import krt.com.cityguide.Adapters.ChurchAdapter;
import krt.com.cityguide.Adapters.NewChurchAdapter;
import krt.com.cityguide.Adapters.ShoppingPlaceAdapter;
import krt.com.cityguide.CityGuideApplication;
import krt.com.cityguide.MainActivity;
import krt.com.cityguide.Models.ChurchModel;
import krt.com.cityguide.Models.ObjectModel;
import krt.com.cityguide.R;
import krt.com.cityguide.Utils.Constants;
import krt.com.cityguide.Utils.Utils;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class MassTimeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MassTimeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MassTimeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MassTimeFragment newInstance(String param1, String param2) {
        MassTimeFragment fragment = new MassTimeFragment();
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
    Spinner spinner;
    JSONArray jsonArray;
    List<ObjectModel> tempList;
    List<ChurchModel> churchModels;
    List<String> churchNameList;
    String url;
    EditText editText;
    MainActivity mainActivity;
    Button button;
    NewChurchAdapter adapter;
    Date date;
    PinnedSectionListView listView;
    TextView textChurch;
    TextView textInformation;
    PopupWindowCompat popupWindowCompat;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainActivity = (MainActivity) getActivity();
        mainView =  inflater.inflate(R.layout.fragment_mass_time, container, false);


        initView();
        return mainView;
    }

    private void expandAll()
    {
//        int size = adapter.getGroupCount();
//        for (int i = 0; i < size; i++)
//        {
//            listView.expandGroup(i);
//        }
    }
    boolean popupFlag;
    String searchText;
    ProgressDialog progressDialog;
    TextView textChurchName, textAddress, textPhone, textToday, textHoliday;
    ImageView imageChurch;
    private void showDialog()
    {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getString(R.string.str_loading));
        progressDialog.show();
    }

    private void hideDialog()
    {
        progressDialog.dismiss();
    }
    private void initView() {
//        editText = (EditText) mainView.findViewById(R.id.editText);
        date = Calendar.getInstance().getTime();
        button = (Button) mainView.findViewById(R.id.searchButton);
        textInformation = (TextView) mainView.findViewById(R.id.textView21);
        spinner = (Spinner) mainView.findViewById(R.id.spinner);
        listView = (PinnedSectionListView) mainView.findViewById(R.id.churchList);
        textChurch = (TextView)mainView.findViewById(R.id.textView20);
        textToday = (TextView)mainView.findViewById(R.id.textView9);
        textHoliday = (TextView)mainView.findViewById(R.id.textView23);
        textChurch.setText("");
//        textToday.setText(Utils.getTodayString());
//        if (Utils.isHoliday())
//            textHoliday.setText(getResources().getString(R.string.holiday));
//        else
//            textHoliday.setText("");
        textInformation.setVisibility(View.INVISIBLE);
        LayoutInflater layoutInflater
                = (LayoutInflater)getContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
//                View popupView = layoutInflater.inflate(R.layout.popup_church_window, null);
        final View popupView = layoutInflater.inflate(R.layout.popup_church_window, null);
        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        textChurchName = (TextView)popupView.findViewById(R.id.textView1);
        textAddress = (TextView)popupView.findViewById(R.id.textView2);
        textPhone = (TextView)popupView.findViewById(R.id.textView3);
        imageChurch = (ImageView) popupView.findViewById(R.id.imageView9);
        popupView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        textInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.showAsDropDown(textInformation, 50, -30);
                ChurchModel model = getSearch(searchText);
                textChurchName.setText(model.getName());
                textAddress.setText(model.getCity() + " " + model.getStreet());
                textPhone.setText(model.getPhone());
                StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(model.getPicture());
                Log.d("model_picture", model.getPicture());
                Glide.with(getContext()).using(new FirebaseImageLoader()).load(storageRef).into(imageChurch);
//                Picasso.with(getContext()).load(model.getPicture()).error(R.drawable.cast_album_art_placeholder).into(imageChurch);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                searchText = editText.getText().toString();

//                Utils.hideSoftKeyboard(mainView.getContext(), editText);
                adapter.searchString(searchText);
                Utils.setListViewHeightBasedOnChildren(listView, 7);
                Log.d("search", searchText);
                if (searchText.equals(""))
                    textInformation.setVisibility(View.INVISIBLE);
                else
                    textInformation.setVisibility(View.VISIBLE);
//                adapter.notifyDataSetChanged();
//                expandAll();
//                listView.notifyAll();
//                listView.setAdapter(adapter);
//                GetData();
            }
        });
        GetFirebase();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                searchText = churchNameList.get(position);
                if (searchText.equals(""))
                {
                    textInformation.setVisibility(View.INVISIBLE);
                }
                textChurch.setText(searchText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    ChurchModel selectedModel;

    public ChurchModel getSearch(String searchName)
    {
        for (ChurchModel model : churchModels)
        {
            if (model.getName().equals(searchName)) return model;
        }
        return null;
    }
    private void GetFirebase()
    {
        churchModels = new ArrayList<>();
        showDialog();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Church");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ChurchModel model;
                churchModels = new ArrayList<>();
                churchNameList = new ArrayList<String>();
                try {
                    churchNameList.add("");
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Log.d("church", postSnapshot.toString());
                        model = postSnapshot.getValue(ChurchModel.class);
//                    if (model.currentOpenTimeList(date) == null) continue;
//                    if (model.currentOpenTimeList(date).size() == 0) continue;
                        churchModels.add(model);
                        churchNameList.add(model.getName());
                    }
                    Collections.sort(churchModels, new Comparator<ChurchModel>() {
                        @Override
                        public int compare(ChurchModel s1, ChurchModel s2) {
                            return s1.getName().compareToIgnoreCase(s2.getName());
                        }
                    });
                    adapter = new NewChurchAdapter(mainActivity, churchModels);
                    listView.setAdapter(adapter);
//                    adapter.searchString("");
                    adapter.notifyDataSetChanged();
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                            android.R.layout.simple_spinner_item, churchNameList);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(dataAdapter);
                    Utils.setListViewHeightBasedOnChildren(listView, 7);
//                    expandAll();
                }
                catch (Exception e){}
                hideDialog();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                hideDialog();
            }
        });
    }
    private void GetData()
    {

       {
//            textTitle.setText(R.string.more_emergencies);
           if (searchText.equals(""))
           {
               url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + String.valueOf(Constants.mylocation.latitude) + "," +
                       String.valueOf(Constants.mylocation.longitude) +
                       "&radius=50000&type=church&keyword=church" + "&key=AIzaSyDJ44q4FHR2N9JUp0XAmKOqItUVp5ksQEE";
           }
           else
           {
               url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + String.valueOf(Constants.mylocation.latitude) + "," +
                       String.valueOf(Constants.mylocation.longitude) +
                       "&radius=50000&type=church&keyword=church" + "&name=" + searchText + "&key=AIzaSyDJ44q4FHR2N9JUp0XAmKOqItUVp5ksQEE";
           }

        }
//        else if (mParam1.equals("emer")) {
//            textTitle.setText(R.string.more_emergencies);
//            url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + String.valueOf(Constants.mylocation.latitude) + "," +
//                    String.valueOf(Constants.mylocation.longitude) +
//                    "&rankBy=radius&keyword=emergency&key=AIzaSyDJ44q4FHR2N9JUp0XAmKOqItUVp5ksQEE";
//        }

        final JsonObjectRequest jsonObjectReq = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG", response.toString());
                        try {
                            jsonArray = response.getJSONArray("results");
                            Log.d("TAG", jsonArray.toString());
                            int l = jsonArray.length();
                            LatLng sydney = Constants.mylocation;
//    googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));
                            tempList = new ArrayList<>();
                            for (int i = 0; i < l; i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                if (jsonObject.has("place_id")) {
                                    double lat = jsonObject.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
                                    double lon = jsonObject.getJSONObject("geometry").getJSONObject("location").getDouble("lng");
                                    String name = jsonObject.getString("name");
                                    String place_id = jsonObject.getString("place_id");
                                    Log.d("name", jsonObject.getString("icon"));
                                    double rating;
                                    String photo_url = "";
                                    if (jsonObject.has("rating"))rating = jsonObject.getDouble("rating"); else rating = 0.0;
                                    if (jsonObject.has("photos")) {
                                        String photo_reference = jsonObject.getJSONArray("photos").getJSONObject(0).getString("photo_reference");
                                        double photo_width = jsonObject.getJSONArray("photos").getJSONObject(0).getDouble("width");
                                        photo_url = "https://maps.googleapis.com/maps/api/place/photo?width=" + photo_width +
                                                "&photoreference=" + photo_reference + "&key=AIzaSyDJ44q4FHR2N9JUp0XAmKOqItUVp5ksQEE";
                                    }
                                    ObjectModel model = new ObjectModel();
                                    model.setLat(lat);
                                    model.setLon(lon);
                                    model.setName(name);
                                    model.setRate(rating);
                                    model.setPhotoURL(photo_url);
                                    model.setPlaceId(place_id);
                                    double distance = Utils.distance(Constants.mylocation.latitude, Constants.mylocation.longitude,
                                            lat, lon);
                                    model.setDistance(distance);
                                    tempList.add(model);

//                                    map.addMarker(new MarkerOptions().position(new LatLng(model.getLat(), model.getLon())));
                                }
                            }
                            Log.d("size", tempList.size() + "size");
                            Collections.sort(tempList);
//                            adapter = new ShoppingPlaceAdapter( tempList, mainActivity);
//
//                            listView.setAdapter(adapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("url", url);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG", "Error: " + error.getMessage());

            }
        });


        // Adding JsonObject request to request queue
        CityGuideApplication.getInstance().addToRequestQueue(jsonObjectReq, "shopping");
    }

}
