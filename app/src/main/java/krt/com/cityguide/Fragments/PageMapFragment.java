/***
  Copyright (c) 2012 CommonsWare, LLC
  Licensed under the Apache License, Version 2.0 (the "License"); you may not
  use this file except in compliance with the License. You may obtain a copy
  of the License at http://www.apache.org/licenses/LICENSE-2.0. Unless required
  by applicable law or agreed to in writing, software distributed under the
  License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
  OF ANY KIND, either express or implied. See the License for the specific
  language governing permissions and limitations under the License.
  
  From _The Busy Coder's Guide to Android Development_
    https://commonsware.com/Android
 */

package krt.com.cityguide.Fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import krt.com.cityguide.Models.ObjectModel;
import krt.com.cityguide.R;
import krt.com.cityguide.Utils.Constants;

public class PageMapFragment extends SupportMapFragment implements
        OnMapReadyCallback {
  private boolean needsInit=false;

  private GoogleMap googleMap;
  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    if (savedInstanceState == null) {
      needsInit=true;
    }

    getMapAsync(this);
  }
  private boolean mylocation_flag;
  @SuppressWarnings("ResourceType")
  @Override
  public void onMapReady(final GoogleMap map) {
    if (needsInit) {

      CameraUpdate center=
          CameraUpdateFactory.newLatLng(Constants.mylocation);
      CameraUpdate zoom= CameraUpdateFactory.zoomTo(12);

      map.moveCamera(center);
      map.animateCamera(zoom);
    }
    googleMap = map;
    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
      // TODO: Consider calling
      //    ActivityCompat#requestPermissions
      // here to request the missing permissions, and then overriding
      //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
      //                                          int[] grantResults)
      // to handle the case where the user grants the permission. See the documentation
      // for ActivityCompat#requestPermissions for more details.
      return;
    }
    googleMap.setMyLocationEnabled(true);



    // For dropping a marker at a point on the Map
    LatLng sydney = Constants.mylocation;
//    googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));
    Circle circle = googleMap.addCircle(new CircleOptions()
            .center(sydney)
            .radius(300)
            .strokeColor(Color.parseColor("#C5CFDF"))
            .fillColor(Color.parseColor("#C5CFDF"))
            .strokeWidth(1.0f)
    );
    mylocation_flag = false;
    final ImageView locationButton = (ImageView) getView().findViewById(2);
    locationButton.setImageResource(R.drawable.ic_my_location_off);
//                locationButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Toast.makeText(getContext(), "image", Toast.LENGTH_SHORT).show();
//                    }
//                });
    googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
      @Override
      public boolean onMyLocationButtonClick() {
        if (mylocation_flag == false) {
          locationButton.setImageResource(R.drawable.ic_my_location_on);
          mylocation_flag = true;

//          Toast.makeText(getContext(), "imagE", Toast.LENGTH_SHORT).show();
        }
        return false;
      }
    });
    googleMap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {
      @Override
      public void onCameraMoveStarted(int i) {
        if (i == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE)
        {
          if (mylocation_flag == true)
          {
            locationButton.setImageResource(R.drawable.ic_my_location_off);
            mylocation_flag = false;
          }
        }
      }
    });
    CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(10).build();
    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//    googleMap = map;
//    addMarker(map, 40.748963847316034, -73.96807193756104, R.string.un,
//              R.string.united_nations);
//    addMarker(map, 40.76866299974387, -73.98268461227417,
//              R.string.lincoln_center, R.string.lincoln_center_snippet);
//    addMarker(map, 40.765136435316755, -73.97989511489868,
//              R.string.carnegie_hall, R.string.practice_x3);
//    addMarker(map, 40.70686417491799, -74.01572942733765,
//              R.string.downtown_club, R.string.heisman_trophy);
  }

  private void addMarker(GoogleMap map, double lat, double lon,
                         int title, int snippet) {
    map.addMarker(new MarkerOptions().position(new LatLng(lat, lon))
                                     .title(getString(title))
                                     .snippet(getString(snippet)));
  }
  public void addObjectMarker(ObjectModel objectModel)
  {
//     googleMap.addCircle(new CircleOptions().center(new LatLng(objectModel.getLat(), objectModel.getLon()))
//     .fillColor(0x000000).radius(1));
//      if (objectModel == null || googleMap == null) return;
      try {
          Bitmap.Config conf = Bitmap.Config.ARGB_8888;
          Bitmap bmp = Bitmap.createBitmap(20, 20, conf);
          Canvas canvas1 = new Canvas(bmp);

// paint defines the text color, stroke width and size
          Paint color = new Paint();
          color.setTextSize(35);
          color.setColor(Color.BLACK);
          canvas1.drawCircle(10, 10, 10, color);
          googleMap.addMarker(new MarkerOptions().position(new LatLng(objectModel.getLat(), objectModel.getLon()))
                  .title(objectModel.getName())
                  .snippet("snippet")
                  .icon(BitmapDescriptorFactory.fromBitmap(bmp)));
      } catch (NullPointerException e)
      {
          Log.d("error", e.getMessage());
      }

  }
}