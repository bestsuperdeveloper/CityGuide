package krt.com.cityguide.Models;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import krt.com.cityguide.Utils.Constants;
import krt.com.cityguide.Utils.Utils;

/**
 * Created by bryden on 1/18/17.
 */

public class FirebaseObjectModel implements Serializable{
    private String city;
    private String name;
    private String phone;
    private String recommend;
    private String street;
    private HashMap<String, String> premium;
    private double distance;
    private double latitude;
    private double longitude;
    private double rate;
    private String email;

    private String objectKind;
    private String objectId;
    private HashMap<String, HashMap<String, String>> rating;
    private HashMap<String, Object> worktime;
    public FirebaseObjectModel() {
    }

    public FirebaseObjectModel(String city, String name, String phone, String recommend, String street, HashMap<String, String> premium, String objectKind, HashMap<String, HashMap<String, String>> rating, HashMap<String, Object> worktime, String objectId) {
        this.city = city;
        this.name = name;
        this.phone = phone;
        this.recommend = recommend;
        this.street = street;
        this.premium = premium;
        this.objectKind = objectKind;
        this.rating = rating;
        this.worktime = worktime;
        this.objectId = objectId;
    }

    public FirebaseObjectModel(String city, String name, String phone, String recommend, String street, HashMap<String, String> premium, String email, String objectKind, String objectId, HashMap<String, HashMap<String, String>> rating) {
        this.city = city;
        this.name = name;
        this.phone = phone;
        this.recommend = recommend;
        this.street = street;
        this.premium = premium;
        this.email = email;
        this.objectKind = objectKind;
        this.objectId = objectId;
        this.rating = rating;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public FirebaseObjectModel(String city, String name, String phone, String recommend, String street, HashMap<String, String> premium, String objectKind, String objectId, HashMap<String, HashMap<String, String>> rating) {
        this.city = city;
        this.name = name;
        this.phone = phone;
        this.recommend = recommend;
        this.street = street;
        this.premium = premium;
        this.objectKind = objectKind;
        this.objectId = objectId;
        this.rating = rating;
    }

    public FirebaseObjectModel(HashMap<String, Object> worktime) {
        this.worktime = worktime;
    }

    public HashMap<String, Object> getWorktime() {
        return worktime;
    }

    public void setWorktime(HashMap<String, Object> worktime) {
        this.worktime = worktime;
    }

    public FirebaseObjectModel(String city, String name, String phone, String recommend, String street, HashMap<String, String> premium, String objectId, HashMap<String, HashMap<String, String>> rating) {
        this.city = city;
        this.name = name;
        this.phone = phone;
        this.recommend = recommend;
        this.street = street;
        this.premium = premium;
        this.objectId = objectId;
        this.rating = rating;
    }

    public FirebaseObjectModel(String city, String name, String phone, String recommend, String street, HashMap<String, String> premium) {
        this.city = city;
        this.name = name;
        this.phone = phone;
        this.recommend = recommend;
        this.street = street;
        this.premium = premium;
        this.distance = 0.0;
        this.latitude = 0.0;
        this.longitude = 0.0;

    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
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

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public HashMap<String, String> getPremium() {
        return premium;
    }

    public void setPremium(HashMap<String, String> premium) {
        this.premium = premium;
    }

    public void calculateDistance(Context context)
    {

        try {
            LatLng latLng = Utils.getLocationFromAddress(context, this.city + this.street);
            this.longitude = latLng.longitude;
            this.latitude = latLng.latitude;
            double dist = Utils.distance(Constants.mylocation.latitude, Constants.mylocation.longitude, latLng.latitude, latLng.longitude);
            this.distance = dist;
        }
        catch (Exception e) {
            Log.d("address_exception", e.getMessage());
            this.distance = 0.0;
            this.longitude = 0.0;
            this.latitude = 0.0;
        }

    }

    public String getObjectKind() {
        return objectKind;
    }

    public void setObjectKind(String objectKind) {
        this.objectKind = objectKind;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public FirebaseObjectModel(String city, String name, String phone, String recommend, String street, HashMap<String, String> premium, HashMap<String, HashMap<String, String>> rating) {
        this.city = city;
        this.name = name;
        this.phone = phone;
        this.recommend = recommend;
        this.street = street;
        this.premium = premium;
        this.rating = rating;
    }

    public HashMap<String, HashMap<String, String>> getRating() {
        return rating;
    }

    public void setRating(HashMap<String, HashMap<String, String>> rating) {
        this.rating = rating;
    }

    public double getRate() {
        HashMap<String, HashMap<String, String>> time = getRating();
        if (time == null) return 0.0f;
        double s = 0.0, index = 0.0;
        for (Map.Entry<String, HashMap<String, String>> entry : time.entrySet()) {
            String key = entry.getKey();
            HashMap<String, String> tab = entry.getValue();
            RatingModel model = RatingModel.getFromHashMap(tab);
            index = index + 1;
            s = s + Double.parseDouble(model.getScore());
        }
        s = s / index;
        double temp = Math.round(s * 10) / 10.0;
        return temp;
    }

    public void Refresh()
    {

    }


    public void setRate(double rate) {
        this.rate = rate;
    }
}
