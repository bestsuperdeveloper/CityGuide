package krt.com.cityguide.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Benza on 1/16/17.
 */

public class Utils {
    public static void setListViewHeightBasedOnChildren(ListView listView, int length) {
        BaseAdapter listAdapter = (BaseAdapter) listView.getAdapter();
        int count = (listAdapter.getCount() > length) ? length : listAdapter.getCount();
        if (listAdapter == null) {
            // pre-condition
            return;
        }


        int totalHeight = 0;
        for (int i = 0; i < count; i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (count - 1)) + 6;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
    public static  SharedPreferences mpref;
    public static FirebaseUser currentUser;
    public static void SetStringData(Context context, String key, String data)
    {
        mpref = context.getSharedPreferences("cityguide", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mpref.edit();
        editor.putString(key, data);
        editor.commit();

    }

    public static boolean getString (String string)
    {
        if (string.equals("")) return false;
        if (string.equals("false")) return false;
        return true;
    }

    public static String getBool(boolean value)
    {
        if (value == false) return "false";
        return "true";
    }
    public static void hideSoftKeyboard(Context context, View v){
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
    public static boolean isEmailValid(String email)
    {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if(matcher.matches())
            return true;
        else
            return false;
    }
    public static String GetStringData(Context context, String key)
    {
        mpref = context.getSharedPreferences("cityguide", Context.MODE_PRIVATE);
        return mpref.getString(key, "");
    }
    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    public static void ShowToast(Context applicationContext, String s) {
        Toast.makeText(applicationContext, s, Toast.LENGTH_SHORT).show();
    }
    public static boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }
    public static LatLng getLocationFromAddress(Context context, String strAddress){

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = new LatLng(0.0, 0.0);

        try {
            address = coder.getFromLocationName(strAddress,5);
            if (address==null || address.size() == 0) {
                return p1;
            }
            Address location=address.get(0);

            Log.d("location", location.getLatitude() + "," + location.getLongitude());


            p1 = new LatLng((double) (location.getLatitude()),
                    (double) (location.getLongitude()));

            return p1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return p1;
    }

    public static String avoidNullString(String string)
    {
        if (string == null) return "";
        return string;
    }
    public static String getDayString(int index)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, index);
        SimpleDateFormat dateFormat = new SimpleDateFormat("M/D", Locale.ENGLISH);
        SimpleDateFormat weekFormat = new SimpleDateFormat("EEE", Locale.ENGLISH);
        String dateString = dateFormat.format(calendar.getTime());

        if (isHoliday(index) == true)
        {
            Log.d("holeday", "hol");
            return Constants.HOL;

        }
        String week = weekFormat.format(calendar.getTime());
//        int day_of_week = calendar.get(Calendar.DAY_OF_WEEK);
        return week.toLowerCase().substring(0, 3);
    }
    public static String getDayWeekString(int index)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, index);
        SimpleDateFormat dateFormat = new SimpleDateFormat("M/D", Locale.ENGLISH);
        SimpleDateFormat weekFormat = new SimpleDateFormat("EEE", Locale.ENGLISH);
        String week = weekFormat.format(calendar.getTime());
//        int day_of_week = calendar.get(Calendar.DAY_OF_WEEK);
        return week.toLowerCase().substring(0, 3);
    }
    public static String getAMPMString(Date date)
    {
        String format = "hh:mm a";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
        return sdf.format(date);
    }
    public static boolean isHoliday(int index)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, index);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH);
        SimpleDateFormat weekFormat = new SimpleDateFormat("EEE", Locale.ENGLISH);
        String dateString = dateFormat.format(calendar.getTime());
        for (int i = 0; i < Constants.holidays.length; i++)
        {
            if (dateString.equals(Constants.holidays[i])) return true;
        }
        return false;
    }


    public static String getTodayString(Context context, int index)
    {
        String format = "EEEE  d MMMM yyyy";
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, index);
//        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date date = calendar.getTime();
        String countryCode = Utils.GetStringData(context, Constants.LANG);
        SimpleDateFormat sdf;
        Locale spanish = new Locale("es", "ES");
        Locale english = Locale.ENGLISH;
        if (countryCode.equals("en"))
        {
            sdf = new SimpleDateFormat(format, english);
        }
        else
        {
            sdf = new SimpleDateFormat(format, spanish);
        }

//        sdf = new SimpleDateFormat(format, Locale.getDefault());
        return getFirstCapital(sdf.format(date));
    }

    public static String getFirstCapital(String string)
    {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

    public static int isOpened(String openTime, String closeTime, Date date)
    {
        if (openTime == null || closeTime == null) return -1;
        String apDate = getAMPMString(date);
        int du = getSecondsFromAMPM(apDate);
        int du1 = getSecondsFromAMPM(openTime);
        int du2 = getSecondsFromAMPM(closeTime);
        if (du >= du1 && du <= du2) return 0;
        if (du < du1) return 1;
        if (du > du2) return 2;
        return -1;
    }

    public static int getSecondsFromHours(String date)
    {
        String time = date; //mm:ss
        String[] units = time.split(":"); //will break the string up into an array
        int minutes = Integer.parseInt(units[0]); //first element
        int seconds = Integer.parseInt(units[1]); //second element
        int duration = 60 * minutes + seconds; //add up our values
        return duration;
    }

    public static int getSecondsFromAMPM(String date)
    {
        SimpleDateFormat weekFormat = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
        try {
            Date dd = weekFormat.parse(date);
            int hour = dd.getHours();
            int minutes = dd.getMinutes();
            Log.d("hour", hour + "," + minutes + " " + date);
            int seconds = hour * 60 + minutes;
            return seconds;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
