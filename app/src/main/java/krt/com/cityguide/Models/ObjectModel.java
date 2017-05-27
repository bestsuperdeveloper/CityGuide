package krt.com.cityguide.Models;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Comparator;

import krt.com.cityguide.Utils.Constants;
import krt.com.cityguide.Utils.Utils;

/**
 * Created by bryden on 1/4/17.
 */

public class ObjectModel implements Comparable<ObjectModel>, Serializable {

    private double rate;
    private double distance;
    private String name;
    private double lat;
    private double lon;

    private String phone;
    private String address;
    private Context context;
    private JSONObject detailInformation;
    private String placeId;

    private String photoURL;

    public ObjectModel(double rate, double distance, String name, double lat, double lon, JSONObject detailInformation, String placeId, String photoURL) {
        this.rate = rate;
        this.distance = distance;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.detailInformation = detailInformation;
        this.placeId = placeId;
        this.photoURL = photoURL;
    }

    public ObjectModel(Context context) {
        this.context = context;
    }

    public JSONObject getDetailInformation() {
        return detailInformation;
    }

    public void setDetailInformation(JSONObject detailInformation) {
        this.detailInformation = detailInformation;
    }

    public ObjectModel(double rate, double distance, String name, double lat, double lon, String placeId, String photoURL) {
        this.rate = rate;
        this.distance = distance;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.placeId = placeId;
        this.photoURL = photoURL;
    }

    public ObjectModel(double rate, double distance, String name, double lat, double lon, String placeId) {
        this.rate = rate;
        this.distance = distance;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.placeId = placeId;
    }

    public ObjectModel() {
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public ObjectModel(double rate, double distance, String name, double lat, double lon) {
        this.rate = rate;
        this.distance = distance;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }

    public ObjectModel(double rate, double distance, String name, double lat, double lon, String phone, String address, Context context, JSONObject detailInformation, String placeId, String photoURL) {
        this.rate = rate;
        this.distance = distance;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.phone = phone;
        this.address = address;
        this.context = context;
        this.detailInformation = detailInformation;
        this.placeId = placeId;
        this.photoURL = photoURL;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    @Override
    public int compareTo(ObjectModel o) {
        if (this.distance > o.getDistance())
            return 1;
        else if (this.distance < o.getDistance())
            return -1;
        return 0;
    }
    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String PLACES_DETAIL_API_BASE = "https://maps.googleapis.com/maps/api/place/details/json?";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";
    private static final String TAG = "TAG";

    public static ObjectModel getObjectModel(FirebaseObjectModel firebaseObjectModel, Context cont)
    {
        ObjectModel model = new ObjectModel();
        model.setName(firebaseObjectModel.getName());
        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();

        JSONObject jsonObject;
        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);

            sb.append("?input=" + URLEncoder.encode(firebaseObjectModel.getName() + " " + firebaseObjectModel.getCity()  , "utf8") );
            sb.append("&key=AIzaSyBqFwcKfug0sRfl0VFwz9bqQarpA8ZNk9k");

            Log.d("url", firebaseObjectModel.getName() + ": " + sb.toString());
            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
            Log.d(TAG, jsonResults.toString());
//            JSONObject result = new JSONObject(jsonResults.toString());

        } catch (MalformedURLException e) {
            Log.e(TAG, "Error processing Places API URL", e);
            return model;
        } catch (IOException e) {
            Log.e(TAG, "Error connecting to Places API", e);
            return model;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        try {
            jsonObject = new JSONObject(jsonResults.toString());
            JSONArray array = jsonObject.getJSONArray("predictions");
            double latitude = 0.0;
            double longitude = 0.0;
            LatLng loc = Utils.getLocationFromAddress(cont, firebaseObjectModel.getStreet() + firebaseObjectModel.getCity());

            latitude = loc.latitude;
            longitude = loc.longitude;

            Log.d("placeloction", "" + latitude + " , " + longitude);
            model.setAddress(firebaseObjectModel.getStreet() + " " + firebaseObjectModel.getCity());
            model.setPhone(firebaseObjectModel.getPhone());
            model.setLat(loc.latitude);
            model.setLon(loc.longitude);
            if (latitude == 0.0 && longitude == 0.0)
                model.setDistance(0.0f);
            else
                model.setDistance(Utils.distance(Constants.mylocation.latitude, Constants.mylocation.longitude, latitude, longitude));
            if (array.length() == 0) return model;

            JSONObject result = new JSONObject(jsonResults.toString()).getJSONArray("predictions").getJSONObject(0);

            String placeID = result.getString("place_id");
            model.setPlaceId(placeID);
            JSONObject temp = getFromDetail(placeID);

//            if (temp.has("geometry")) {
//                latitude = temp.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
//                longitude = temp.getJSONObject("geometry").getJSONObject("location").getDouble("lng");
//                model.setLat(latitude); model.setLon(longitude);
//            }

            if (temp.has("rating"))
            {
                model.setRate(temp.getDouble("rating"));
            }
            if (temp.has("photos"))
            {
                String photoID = temp.getJSONArray("photos").getJSONObject(0).getString("photo_reference");
                int width = temp.getJSONArray("photos").getJSONObject(0).getInt("width");
                String phoURL = Constants.PLACE_PHOTO_URL + "?width=" + width + "&photoreference=" + photoID +
                        "&key=AIzaSyBqFwcKfug0sRfl0VFwz9bqQarpA8ZNk9k";
                model.setPhotoURL(phoURL);
            }
//            if (temp.has("name"))
            model.setName(firebaseObjectModel.getName());


            model.setDetailInformation(temp);

            return model;

        } catch (JSONException e) {
            e.printStackTrace();
            return model;
        }

//        return null;
    }

    public static JSONObject getFromDetail(String placeID)
    {
        JSONObject jsonObject = new JSONObject();
        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();

        try {
            StringBuilder sb = new StringBuilder(PLACES_DETAIL_API_BASE);

            sb.append("placeid=" + URLEncoder.encode(placeID, "utf8"));
            sb.append("&key=AIzaSyBqFwcKfug0sRfl0VFwz9bqQarpA8ZNk9k");
            Log.d("Detail", placeID + "," + sb.toString());
            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
            Log.d("Detail", jsonResults.toString());
            jsonObject = new JSONObject(jsonResults.toString());
            if (jsonObject.has("result"))
            return jsonObject.getJSONObject("result");



        } catch (MalformedURLException e) {
            Log.e(TAG, "Error processing Places API URL", e);
            return jsonObject;
        } catch (IOException e) {
            Log.e(TAG, "Error connecting to Places API", e);
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return jsonObject;
    }
}
