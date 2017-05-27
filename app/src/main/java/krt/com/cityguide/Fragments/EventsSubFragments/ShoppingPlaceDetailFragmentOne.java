package krt.com.cityguide.Fragments.EventsSubFragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import krt.com.cityguide.CityGuideApplication;
import krt.com.cityguide.MainActivity;
import krt.com.cityguide.Models.ChurchTimeModel;
import krt.com.cityguide.Models.FirebaseObjectModel;
import krt.com.cityguide.Models.ObjectModel;
import krt.com.cityguide.Models.ShoppingModel;
import krt.com.cityguide.Models.TimeModel;
import krt.com.cityguide.R;
import krt.com.cityguide.Utils.Utils;


public class ShoppingPlaceDetailFragmentOne extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private FirebaseObjectModel mParam1;
    private String mParam2;

    TextView textMon, textTue, textWed, textThu, textFri, textSat, textSun;
    TextView timeMon, timeTue, timeWed, timeThu, timeFri, timeSat, timeSun;
    ListView listView;
    TextView textPhone, textAddress;//, timeWed, timeThu, timeFri, timeSat, timeSun;
    public ShoppingPlaceDetailFragmentOne() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShoppingPlaceDetailFragmentOne.
     */
    // TODO: Rename and change types and number of parameters
    public static ShoppingPlaceDetailFragmentOne newInstance(FirebaseObjectModel param1, String param2) {
        ShoppingPlaceDetailFragmentOne fragment = new ShoppingPlaceDetailFragmentOne();
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

    TextView textOpen;
    String place_id;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.fragment_shopping_place_detail_fragment_one, container, false);
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

    private void hideDialog()
    {
        progressDialog.dismiss();
    }
    ImageView imageView;
    private void initView() {
//        place_id = mParam1.getPlaceId();
        textPhone = (TextView) mainView.findViewById(R.id.textTel);
        textAddress = (TextView) mainView.findViewById(R.id.textView27);
        textOpen = (TextView) mainView.findViewById(R.id.timeOpen);
        listView = (ListView) mainView.findViewById(R.id.listView);
        String address = Utils.avoidNullString(mParam1.getStreet()) + ", " + Utils.avoidNullString(mParam1.getCity()) ;
        textAddress.setText(address);
//        textView18
//        imageView = (ImageView) mainView.findViewById(R.id.imageView2);

        GetFirebaseData();

//        GetData();

    }

    String phonenumber = "", address = "";
    String name = "", openText = "";
    boolean openState;

    String photoURL = "";



    List<TimeModel> timeModelList;
    private void GetFirebaseData()
    {
        String phone = mParam1.getPhone();
        if (phone != null && !phone.equals(""))
        {
            textPhone.setText("Tel: " + phone);
        }
        else
            textPhone.setText("");
        HashMap<String, Object> workTime = mParam1.getWorktime();
        ReadModel(workTime);
//        if (workTime != null)
//        {
//            HashMap<String, String> monTime = workTime.get("mon");
//            HashMap<String, String> tueTime = workTime.get("tue");
//            HashMap<String, String> wedTime = workTime.get("wed");
//            HashMap<String, String> thuTime = workTime.get("thu");
//            HashMap<String, String> friTime = workTime.get("fri");
//            HashMap<String, String> satTime = workTime.get("sat");
//            HashMap<String, String> sunTime = workTime.get("sun");
//            ReadModel(workTime);
//            if (monTime != null)
//            {
//                String closetime = monTime.get("close");
//                String opentime = monTime.get("open");
//                try {
//                    timeMon.setText(opentime + " - " + closetime);
//                    textMon.setText(getContext().getString(R.string.str_mon));
//                } catch (Exception e){}
//            }
//            if (tueTime != null)
//            {
//                String closetime = tueTime.get("close");
//                String opentime = tueTime.get("open");
//                try {
//                    timeTue.setText(opentime + " - " + closetime);
//                    textTue.setText(getContext().getString(R.string.str_tue));
//                }catch (Exception e){}
//            }
//            if (wedTime != null)
//            {
//                String closetime = wedTime.get("close");
//                String opentime = wedTime.get("open");
//                try {
//                    timeWed.setText(opentime + " - " + closetime);
//                    textWed.setText(getContext().getString(R.string.str_wed));
//                }catch (Exception e){}
//            }
//            if (thuTime != null)
//            {
//                String closetime = thuTime.get("close");
//                String opentime = thuTime.get("open");
//                try {
//                    timeThu.setText(opentime + " - " + closetime);
//                    textThu.setText(getContext().getString(R.string.str_thu));
//                }  catch (Exception e){}
//            }
//            if (friTime != null)
//            {
//                String closetime = friTime.get("close");
//                String opentime = friTime.get("open");
//                try {
//                    timeFri.setText(opentime + " - " + closetime);
//                    textFri.setText(getContext().getString(R.string.str_fri));
//                } catch (Exception e){}
//            }
//            if (satTime != null)
//            {
//                String closetime = satTime.get("close");
//                String opentime = satTime.get("open");
//                try {
//                    timeSat.setText(opentime + " - " + closetime);
//                    textSat.setText(getContext().getString(R.string.str_sat));
//                }catch (Exception e){}
//            }
//            if (sunTime != null)
//            {
//                String closetime = sunTime.get("close");
//                String opentime = sunTime.get("open");
//                try {
//                    timeSun.setText(opentime + " - " + closetime);
//                    textSun.setText(getContext().getString(R.string.str_sun));
//                } catch (Exception e){
//
//                }
//            }
//
//            Date date = Calendar.getInstance().getTime();
//            String weekDay = Utils.getDayString(0);
//            Log.d("weekday", weekDay);
//            HashMap<String, String> currentWorkTime = workTime.get(weekDay);
//            String closetime = currentWorkTime.get("close");
//            String opentime = currentWorkTime.get("open");
//            int value = Utils.isOpened(opentime, closetime, date);
//            String result = "";
//            if (value == 1)
//            {
//                result = getContext().getString(R.string.str_will_open);
//            }
//            else if (value == 2)
//            {
//                result = getContext().getString(R.string.closed) ;
//            }
//            else if (value == 0)
//            {
//                result = getContext().getString(R.string.str_current_open) ;
//            }
//
//            textOpen.setText(result);
//        }
    }

    List<workTimeModel> workTimeModelList;
    HashMap<String, String> weekStringMap;

    private void ReadModel(HashMap<String, Object> worktime) {
        workTimeModelList = new ArrayList<>();
        String[] week = {"mon", "tue", "wed", "thu", "fri", "sat", "sun"};
        weekStringMap = new HashMap<>();
        listView.setDivider(null);
        weekStringMap.put("mon", getActivity().getString(R.string.str_mon));
        weekStringMap.put("tue", getActivity().getString(R.string.str_tue));
        weekStringMap.put("wed", getActivity().getString(R.string.str_wed));
        weekStringMap.put("thu", getActivity().getString(R.string.str_thu));
        weekStringMap.put("fri", getActivity().getString(R.string.str_fri));
        weekStringMap.put("sat", getActivity().getString(R.string.str_sat));
        weekStringMap.put("sun", getActivity().getString(R.string.str_sun));
        String weekText = "";
        for (int i = 0; i < 7; i++)
        {
            HashMap<String, HashMap<String, String>> time =  (HashMap<String, HashMap<String, String>>) worktime.get(week[i]);
            if (!weekText.equals(week[i])) weekText = week[i];
            int idex = 0;
            for (Map.Entry<String, HashMap<String, String>> entry : time.entrySet()) {
                String key = entry.getKey();
                if (idex == 0) weekText = week[i]; else weekText = "";
                HashMap<String, String> tab = entry.getValue();
                workTimeModel mo = new workTimeModel(weekText, tab.get("open"), tab.get("close"));

//            if (churchTimeModel.isOpned(nowDate) == 2)
                workTimeModelList.add(mo);
                idex++;
            }
        }
        WorkTimeAdapter adapter = new WorkTimeAdapter(getActivity());
        String weekDay = Utils.getDayWeekString(0);
        HashMap<String, HashMap<String, String>> workTime =  (HashMap<String, HashMap<String, String>>) worktime.get(weekDay);
        Log.d("weekday", weekDay);
        Date date = Calendar.getInstance().getTime();
        boolean flag = false;
        for (Map.Entry<String, HashMap<String, String>> entry : workTime.entrySet()) {
            String key = entry.getKey();

            HashMap<String, String> tab = entry.getValue();
            workTimeModel mo = new workTimeModel(weekText, tab.get("open"), tab.get("close"));
            String opentime = tab.get("open");
            String closetime = tab.get("close");
            int value = Utils.isOpened(opentime, closetime, date);
            if (value == 0) { flag = true; break;}
        }
        String result;
        listView.setAdapter(adapter);
        if (flag == true)
        {
            result = getContext().getString(R.string.str_current_open) ;
        }
        else
        {
            result = getContext().getString(R.string.closed) ;
        }
        textOpen.setText(result);
    }
    /*
    private void GetData() {

        weekday = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        showDialog();
//        showData();
        textPhone.setText("tel: " + mParam1.getPhone());
        name = mParam1.getName();

        final String url = "https://maps.googleapis.com/maps/api/place/details/json?placeid=" + place_id + "&key=AIzaSyBqFwcKfug0sRfl0VFwz9bqQarpA8ZNk9k";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
//                txt.setText(s);
                Log.d("url", url);
                Log.d("fragmentone", response.toString());
                try {
                    if (response.getJSONObject("result").has("international_phone_number"))
                         phonenumber = response.getJSONObject("result").getString("international_phone_number");
                    if (response.getJSONObject("result").has("formatted_address"))
                      address = response.getJSONObject("result").getString("formatted_address");
                    if (response.getJSONObject("result").has("opening_hours")) {
                        openState = response.getJSONObject("result").getJSONObject("opening_hours").getBoolean("open_now");
                    }

                    timeModelList = new ArrayList<>();
                    JSONArray jsonArray;

                    JSONObject jsonObject;

                    if (response.getJSONObject("result").has("opening_hours")) {
                        jsonArray = response.getJSONObject("result").getJSONObject("opening_hours").getJSONArray("periods");
//                        int i, l = jsonArray.length();
                        int ss = jsonArray.length();
                        Log.d("size", "jsonarray " + ss);
                        Log.d("opening", response.getJSONObject("result").getJSONObject("opening_hours").getString("weekday_text"));
                        JSONArray jsonArr = response.getJSONObject("result").getJSONObject("opening_hours").getJSONArray("weekday_text");
                        int i, l = jsonArr.length();
//                    temp = temp.substring(1, temp.length() - 1);
//                    String[] arr = temp.split(",");
                        for (i = 0; i < l; i++) {
//                        jsonObject = jsonArray.getJSONObject(i);

                            Log.d("array", jsonArr.getString(i));
                        }
                        if (openState == true) {
                            openText = getString(R.string.str_current_open) + " " + (jsonArr.getString(weekday - 1).split(": ")[1]).split("-")[0];
                        } else {
                            openText = getString(R.string.closed) ;
                        }
                        textOpen.setText(openText);
                        textMon.setText(jsonArr.getString(0).split(": ")[0]);
                        textTue.setText(jsonArr.getString(1).split(": ")[0]);
                        textWed.setText(jsonArr.getString(2).split(": ")[0]);
                        textThu.setText(jsonArr.getString(3).split(": ")[0]);
                        textFri.setText(jsonArr.getString(4).split(": ")[0]);
                        textSat.setText(jsonArr.getString(5).split(": ")[0]);
                        textSun.setText(jsonArr.getString(6).split(": ")[0]);
//
                        timeMon.setText(jsonArr.getString(0).split(": ")[1]);
                        timeTue.setText(jsonArr.getString(1).split(": ")[1]);
                        timeWed.setText(jsonArr.getString(2).split(": ")[1]);
                        timeThu.setText(jsonArr.getString(3).split(": ")[1]);
                        timeFri.setText(jsonArr.getString(4).split(": ")[1]);
                        timeSat.setText(jsonArr.getString(5).split(": ")[1]);
                        timeSun.setText(jsonArr.getString(6).split(": ")[1]);
                    }
                    else
                    {
                        try {
                            textOpen.setText(getActivity().getString(R.string.no_result));
                        }
                        catch (Exception e){};
                    }
                    if (response.getJSONObject("result").has("photos")) {
                    String reference = response.getJSONObject("result").getJSONArray("photos").getJSONObject(0).getString("photo_reference");

                        int width = response.getJSONObject("result").getJSONArray("photos").getJSONObject(0).getInt("width");
                        photoURL = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=" + width +
                                "&photoreference=" +
                                reference +
                                "&key=AIzaSyBqFwcKfug0sRfl0VFwz9bqQarpA8ZNk9k";
                        Picasso.with(getActivity()).load(photoURL).error(R.drawable.no_photo_icon).into(imageView);

                    }
                    else
                    {
                        imageView.setImageResource(R.drawable.no_photo_icon);
                    }

                    Log.d("url", photoURL);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                hideDialog();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
//                txt.setText("Some error occurred!!");
                hideDialog();
            }
        });

        CityGuideApplication.getInstance().addToRequestQueue(request, "detailRest");

    }
    */
/*
    private void showData() {
        JSONObject response = mParam1.getDetailInformation();
        if (response == null)
        {
            textPhone.setText(getActivity().getResources().getString(R.string.no_information));
            return;
        }
        try {
            if (response.has("international_phone_number"))
                phonenumber = response.getString("international_phone_number");
            if (response.has("formatted_address"))
                address = response.getString("formatted_address");
            if (response.has("opening_hours")) {
                openState = response.getJSONObject("opening_hours").getBoolean("open_now");
            }
            textPhone.setText("tel: " + phonenumber);
            textAddress.setText(address);
            timeModelList = new ArrayList<>();
            JSONArray jsonArray;

            JSONObject jsonObject;
            name = mParam1.getName();
            textName.setText(name);
            if (response.has("opening_hours")) {
                jsonArray = response.getJSONObject("opening_hours").getJSONArray("periods");
//                        int i, l = jsonArray.length();
                int ss = jsonArray.length();
                Log.d("size", "jsonarray " + ss);
                Log.d("opening", response.getJSONObject("opening_hours").getString("weekday_text"));
                JSONArray jsonArr = response.getJSONObject("opening_hours").getJSONArray("weekday_text");
                int i, l = jsonArr.length();
//                    temp = temp.substring(1, temp.length() - 1);
//                    String[] arr = temp.split(",");
                for (i = 0; i < l; i++) {
//                        jsonObject = jsonArray.getJSONObject(i);

                    Log.d("array", jsonArr.getString(i));
                }
                if (openState == true) {
                    openText = getString(R.string.str_current_open) + " " + (jsonArr.getString(weekday - 1).split(": ")[1]).split("-")[0];
                } else {
                    openText = getString(R.string.closed) ;
                }
                textOpen.setText(openText);
                textMon.setText(jsonArr.getString(0).split(": ")[0]);
                textTue.setText(jsonArr.getString(1).split(": ")[0]);
                textWed.setText(jsonArr.getString(2).split(": ")[0]);
                textThu.setText(jsonArr.getString(3).split(": ")[0]);
                textFri.setText(jsonArr.getString(4).split(": ")[0]);
                textSat.setText(jsonArr.getString(5).split(": ")[0]);
                textSun.setText(jsonArr.getString(6).split(": ")[0]);
//
                timeMon.setText(jsonArr.getString(0).split(": ")[1]);
                timeTue.setText(jsonArr.getString(1).split(": ")[1]);
                timeWed.setText(jsonArr.getString(2).split(": ")[1]);
                timeThu.setText(jsonArr.getString(3).split(": ")[1]);
                timeFri.setText(jsonArr.getString(4).split(": ")[1]);
                timeSat.setText(jsonArr.getString(5).split(": ")[1]);
                timeSun.setText(jsonArr.getString(6).split(": ")[1]);
            }
            else
            {
                textOpen.setText(getString(R.string.no_result));
            }
            if (response.has("photos")) {
                String reference = response.getJSONArray("photos").getJSONObject(0).getString("photo_reference");

                int width = response.getJSONArray("photos").getJSONObject(0).getInt("width");
                photoURL = mParam1.getPhotoURL();
                Log.d("photourl", photoURL);
                Picasso.with(getActivity()).load(photoURL).error(R.drawable.no_photo_icon).into(imageView);

            }
            else
            {
                imageView.setImageResource(R.drawable.no_photo_icon);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }*/

    private class WorkTimeAdapter extends BaseAdapter
    {
        LayoutInflater layoutInflater;
        private Context context;

        public WorkTimeAdapter(Context context) {
            this.context = context;
            layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return workTimeModelList.size();
        }

        @Override
        public workTimeModel getItem(int position) {
            return workTimeModelList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null)
            {
                convertView = layoutInflater.inflate(R.layout.worktime_list_item, null);
            }
            TextView work, week;
            week = (TextView) convertView.findViewById(R.id.textWeek);
            work = (TextView) convertView.findViewById(R.id.timeWeek);
            workTimeModel model = getItem(position);
            if (!model.getWeekDay().equals("")) week.setText(weekStringMap.get(model.getWeekDay()));
            else week.setText("");
            work.setText(model.getOpentime() + " - " + model.getClosetime());
            return convertView;
        }
    }

    class workTimeModel {
        private String weekDay, opentime, closetime;

        public workTimeModel() {
        }

        public workTimeModel(String weekDay, String opentime, String closetime) {
            this.weekDay = weekDay;
            this.opentime = opentime;
            this.closetime = closetime;
        }

        public String getWeekDay() {
            return weekDay;
        }

        public void setWeekDay(String weekDay) {
            this.weekDay = weekDay;
        }

        public String getOpentime() {
            return opentime;
        }

        public void setOpentime(String opentime) {
            this.opentime = opentime;
        }

        public String getClosetime() {
            return closetime;
        }

        public void setClosetime(String closetime) {
            this.closetime = closetime;
        }
    }

}
