package krt.com.cityguide.Models;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import krt.com.cityguide.Utils.Utils;

/**
 * Created by bryden on 1/25/17.
 */

public class ChurchModel implements Serializable{
    private String city;
    private String name;
    private String phone;
    private String street;
    private String picture;
    private HashMap<String, Object> worktime;

    public ChurchModel(String city, String name, String phone, String street, HashMap<String, Object> worktime) {
        this.city = city;
        this.name = name;
        this.phone = phone;
        this.street = street;
        this.worktime = worktime;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public ChurchModel(String city, String name, String phone, String street, String picture, HashMap<String, Object> worktime) {
        this.city = city;
        this.name = name;
        this.phone = phone;
        this.street = street;
        this.picture = picture;
        this.worktime = worktime;
    }

    public ChurchModel() {
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public HashMap<String, Object> getWorktime() {
        return worktime;
    }

    public void setWorktime(HashMap<String, Object> worktime) {
        this.worktime = worktime;
    }
    public List<ChurchTimeModel> allOpenTimeList(int index)
    {
        List<ChurchTimeModel> arr = new ArrayList<>();
        Date nowDate = Calendar.getInstance().getTime();
        String day = Utils.getDayString(index);
        Log.d("today", day);
        ChurchTimeModel churchTimeModel;
        HashMap<String, Object> map = getWorktime();
        if (map == null) return arr;
        HashMap<String, HashMap<String, String>> time = ( HashMap<String, HashMap<String, String>>) getWorktime().get(day);
        if (time == null) return arr;
        for (Map.Entry<String, HashMap<String, String>> entry : time.entrySet()) {
            String key = entry.getKey();
            HashMap<String, String> tab = entry.getValue();
            churchTimeModel = new ChurchTimeModel(tab.get("duration"), tab.get("time"), this.name);
//            if (churchTimeModel.isOpned(nowDate) == 2)
            arr.add(churchTimeModel);
        }

        Collections.sort(arr, new Comparator<ChurchTimeModel>() {
            @Override
            public int compare(ChurchTimeModel s1, ChurchTimeModel s2) {
                int du1 = Utils.getSecondsFromAMPM(s1.getStarttime());
                int du2 = Utils.getSecondsFromAMPM(s2.getStarttime());
                return du1 - du2;
            }
        });
        return arr;
    }

    public List<ChurchTimeModel> currentOpenTimeList(int index)
    {
        List<ChurchTimeModel> arr = new ArrayList<>();
        Date nowDate = Calendar.getInstance().getTime();
         String day = Utils.getDayString(index);
        Log.d("today", day);
        ChurchTimeModel churchTimeModel;
        HashMap<String, Object> map = getWorktime();
        if (map == null) return arr;
        HashMap<String, HashMap<String, String>> time = ( HashMap<String, HashMap<String, String>>) getWorktime().get(day);
        if (time == null) return arr;
        for (Map.Entry<String, HashMap<String, String>> entry : time.entrySet()) {
            String key = entry.getKey();
            HashMap<String, String> tab = entry.getValue();
            churchTimeModel = new ChurchTimeModel(tab.get("duration"), tab.get("time"), this.name);
            if (churchTimeModel.isOpned(nowDate) == 1)
                arr.add(churchTimeModel);
        }

        Collections.sort(arr, new Comparator<ChurchTimeModel>() {
            @Override
            public int compare(ChurchTimeModel s1, ChurchTimeModel s2) {
                int du1 = Utils.getSecondsFromAMPM(s1.getStarttime());
                int du2 = Utils.getSecondsFromAMPM(s2.getStarttime());
                return du1 - du2;
            }
        });
        return arr;
    }

}
