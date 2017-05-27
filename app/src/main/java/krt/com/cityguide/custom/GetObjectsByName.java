package krt.com.cityguide.custom;

import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;
import java.util.List;

import krt.com.cityguide.Models.FirebaseObjectModel;

/**
 * Created by bryden on 1/18/17.
 */

public class GetObjectsByName extends AsyncTask<Object, String, String> {
    @Override
    protected String doInBackground(Object... params) {
        GoogleMap map = (GoogleMap) params[0];
        List<FirebaseObjectModel> objectModelList =  (List<FirebaseObjectModel>) params[1];
        for (FirebaseObjectModel model : objectModelList)
        {

        }

        return null;
    }
}
