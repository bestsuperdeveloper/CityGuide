package krt.com.cityguide;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.squareup.picasso.Picasso;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import krt.com.cityguide.Adapters.CommonAdapter;
import krt.com.cityguide.Adapters.RecommendAdapter;
import krt.com.cityguide.Fragments.ContactUSFragment;
import krt.com.cityguide.Fragments.EventsFragment;
import krt.com.cityguide.Fragments.EventsSubFragments.MassTimeFragment;
import krt.com.cityguide.Fragments.EventsSubFragments.ObjectDetailFragment;
import krt.com.cityguide.Fragments.EventsSubFragments.ShoppingDetailFragment;
import krt.com.cityguide.Fragments.EventsSubFragments.ShoppingPlaceDetailFragment;
import krt.com.cityguide.Fragments.EventsSubFragments.ShoppingPlaceFragment;
import krt.com.cityguide.Fragments.HomeFragment;
import krt.com.cityguide.Fragments.HotelFragment;
import krt.com.cityguide.Fragments.MoreFragment;
import krt.com.cityguide.Fragments.PageMapFragment;
import krt.com.cityguide.Fragments.ParkingFragment;
import krt.com.cityguide.Fragments.RestaurantFragment;
import krt.com.cityguide.Models.ChurchModel;
import krt.com.cityguide.Models.FirebaseObjectModel;
import krt.com.cityguide.Models.ObjectModel;
import krt.com.cityguide.Models.ShoppingModel;
import krt.com.cityguide.Utils.Constants;
import krt.com.cityguide.Utils.Utils;

import static java.lang.Math.abs;

public class MainActivity extends BaseActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener  {

    private static final String GOOGLE_TAG = "google_tag";
    HomeFragment homeFragment;
    RestaurantFragment restaurantFragment;
    HotelFragment hotelFragment;
    ParkingFragment parkingFragment;
    EventsFragment eventsFragment;
    MoreFragment moreFragment;

    ImageView imageView;

    HashMap<String, FirebaseObjectModel> markers;
    String languageToLoad = "en";
    public List<ObjectModel> recommendRestList;
    public List<ObjectModel> commonRestList;
    public List<FirebaseObjectModel> tempList;
    RecommendAdapter restadapter;
    CommonAdapter commonAdapter;
    ImageButton imageButton;
    LinearLayout linearLayout;
    ListView commonListView, recommentListView;
    List<String> recommmedKey, commonkey;
    LinearLayout slidelayout;
    ImageView imgRest, imgHotel, imgParking, imgEvents, imgMore;
    TextView textRest, textHotel, textParking, textEvents, textMore, textTitle;
    RelativeLayout topbarLayout;
    MassTimeFragment massTimeFragment;
    ShoppingDetailFragment shoppingDetailFragment;
    ObjectDetailFragment objectDetailFragment;
    boolean homeflag = false;
    ShoppingPlaceFragment shoppingPlaceFragment;
    ContactUSFragment contactUSFragment;
    public List<ObjectModel> shoppingModelList;


    private final int delta = 10;
    private final float speed_delta = 800;
    public FirebaseObjectModel shoppingModel;

    HashMap<String, String> hashMarker;
    ImageButton btnSpain, btnUSA;
    SupportMapFragment mapFragment;


    JSONArray jsonArray;
    String url;
    GoogleMap map;
    private String TAG = "TAG";
    private FirebaseAuth mAuth;
    private int RC_SIGN_IN = 1001;
    List<FirebaseObjectModel> objectModelList;
    public List<FirebaseObjectModel> recommendModelList;
    public List<FirebaseObjectModel> commonModelList;

    ShoppingPlaceDetailFragment shoppingPlaceDetailFragment;
    public void GetFromPlaceAPI(final int id)
    {
        DatabaseReference ref = null;
        Log.d("snapshot", "GetFromPlaceAPI");

        map.clear();
        LatLng sydney = Constants.mylocation;
//    googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));
        Circle circle = map.addCircle(new CircleOptions()
                .center(sydney)
                .radius(500)
                .strokeColor(Color.parseColor("#C5CFDF"))
                .fillColor(0x55c5cfdf)
                .strokeWidth(5.0f));
        if (id == 0) {
            ref = FirebaseDatabase.getInstance().getReference("Restaurant");
        }
        else if (id == 1){
            ref = FirebaseDatabase.getInstance().getReference("Hotel");
        }
        else if (id == 2){
            ref = FirebaseDatabase.getInstance().getReference("Parking");
        }

        ShowProgressDialog();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                    Log.d("snapshot", dataSnapshot.toString());
                hashMarker = new HashMap<String, String>();
                recommendModelList = new ArrayList<FirebaseObjectModel>();
                commonModelList = new ArrayList<FirebaseObjectModel>();
                recommmedKey = new ArrayList<String>();
                commonkey = new ArrayList<String>();
                for (DataSnapshot post:dataSnapshot.getChildren())
                {
                    FirebaseObjectModel model = post.getValue(FirebaseObjectModel.class);
//                        Log.d("snapshot", model.getName());
                    if (model.getRecommend() != null && model.getRecommend().equals("Y"))
                    {
                        recommendModelList.add(model);
                        recommmedKey.add(model.getObjectId());
//                            model.calculateDistance(MainActivity.this);
                    }
                    else
                    {
                        commonkey.add(model.getObjectId());
                        Log.d("model_id", model.getObjectId());
                        commonModelList.add(model);
//                            model.calculateDistance(MainActivity.this);
                    }
                }
                new DrawGoogleMap().execute();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                FirebaseObjectModel Tempmodel = dataSnapshot.getValue(FirebaseObjectModel.class);
                FirebaseObjectModel model;
                String markerId = hashMarker.get(Tempmodel.getObjectId());

                if (Tempmodel.getRecommend().equals("Y"))
                {
                    int index = recommmedKey.indexOf(Tempmodel.getObjectId());
                    model = recommendModelList.get(index);
                    model.setRating(Tempmodel.getRating());
                    Log.d("key_1_2", Tempmodel.getObjectId() + " ," + model.getObjectId() + "," + index);
                    recommendModelList.set(index, model);
                    markers.put(markerId, model);
                    restadapter.notifyDataSetChanged();
                }
                else
                {
                    int index = commonkey.indexOf(Tempmodel.getObjectId());
                    model = commonModelList.get(index);
                    model.setRating(Tempmodel.getRating());
                    Log.d("key_1_2", Tempmodel.getObjectId() + " ," + model.getObjectId() + "," + index);
                    markers.put(markerId, model);
                    commonModelList.set(index, model);
                    commonAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private class DrawGoogleMap extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params) {
            markers = new HashMap<>();
            for (FirebaseObjectModel objectModel : recommendModelList)
                objectModel.calculateDistance(MainActivity.this);
            for (FirebaseObjectModel objectModel : commonModelList)
                objectModel.calculateDistance(MainActivity.this);
            int i, j, size;
            FirebaseObjectModel model1, model2;
            String key1, key2;
            size = recommendModelList.size();
            for (i = 0; i < size; i++)
            {
                for (j = i + 1; j < size; j++)
                {
                    model1 = recommendModelList.get(i); model2 = recommendModelList.get(j);
                    key1 = recommmedKey.get(i); key2 = recommmedKey.get(j);
                    if (model2.getDistance() < model1.getDistance())
                    {
                        recommendModelList.set(i, model2); recommendModelList.set(j, model1);
                        recommmedKey.set(i, key2); recommmedKey.set(j, key1);
                    }
                }
            }
            size = commonModelList.size();
            for (i = 0; i < size; i++)
            {
                for (j = i + 1; j < size; j++)
                {
                    model1 = commonModelList.get(i); model2 = commonModelList.get(j);
                    key1 = commonkey.get(i); key2 = commonkey.get(j);
                    if (model2.getDistance() < model1.getDistance())
                    {
                        commonModelList.set(i, model2); commonModelList.set(j, model1);
                        commonkey.set(i, key2); commonkey.set(j, key1);
                    }
                }
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

//            super.onPostExecute(aVoid);
            for (FirebaseObjectModel objectModel : recommendModelList)               DrawMarker(objectModel);
            for (FirebaseObjectModel objectModel : commonModelList)               DrawMarker(objectModel);
            restadapter = new RecommendAdapter(MainActivity.this, recommendModelList);
            commonAdapter = new CommonAdapter(MainActivity.this, commonModelList);
            recommentListView.setAdapter(restadapter);
            commonListView.setAdapter(commonAdapter);
            Utils.setListViewHeightBasedOnChildren(commonListView, 7);
            Utils.setListViewHeightBasedOnChildren(recommentListView, 4);
            HideProgressDialog();
        }
    }
    private void DrawMarker(final FirebaseObjectModel model)
    {

        if (model == null) return;
        if (model.getLatitude() == 0.0 && model.getLongitude() == 0) return;
        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        Bitmap bmp = Bitmap.createBitmap(40, 40, conf);
        Canvas canvas1 = new Canvas(bmp);

// paint defines the text color, stroke width and size
        Paint color = new Paint();
        color.setTextSize(50);
        color.setColor(getResources().getColor(R.color.highlight_color));
        canvas1.drawCircle(20, 20, 20, color);
        Marker marker = map.addMarker(new MarkerOptions().position(new LatLng(model.getLatitude(), model.getLongitude()))
                .title(model.getName())
                .snippet("Rate : " + model.getRate())
                .icon(BitmapDescriptorFactory.fromBitmap(bmp)));
        markers.put(marker.getId(), model);
        hashMarker.put(model.getObjectId(), marker.getId());
//        runOnUiThread(new Runnable()
//        {
//            @Override
//            public void run() {
//                if (model == null) return;
//                if (model.getLatitude() == 0.0 && model.getLongitude() == 0) return;
//                Bitmap.Config conf = Bitmap.Config.ARGB_8888;
//                Bitmap bmp = Bitmap.createBitmap(40, 40, conf);
//                Canvas canvas1 = new Canvas(bmp);
//
//// paint defines the text color, stroke width and size
//                Paint color = new Paint();
//                color.setTextSize(50);
//                color.setColor(getResources().getColor(R.color.highlight_color));
//                canvas1.drawCircle(20, 20, 20, color);
//                Marker marker = map.addMarker(new MarkerOptions().position(new LatLng(model.getLatitude(), model.getLongitude()))
//                        .title(model.getName())
//                        .snippet("Rate : " + model.getRate())
//                        .icon(BitmapDescriptorFactory.fromBitmap(bmp)));
//                markers.put(marker.getId(), model);
//                hashMarker.put(model.getObjectId(), marker.getId());
//            }
//        });

    }
//    private class DrawGoogleMap extends AsyncTask<String, String, String>
//    {
//        @Override
//        protected String doInBackground(String... params) {
//
//            for (FirebaseObjectModel objectModel : recommendModelList)               DrawMarker(objectModel);
//            for (FirebaseObjectModel objectModel : commonModelList)               DrawMarker(objectModel);
//           return "a";
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//
//
//            restadapter = new RecommendAdapter(MainActivity.this, recommendModelList);
//            commonAdapter = new CommonAdapter(MainActivity.this, commonModelList);
//            recommentListView.setAdapter(restadapter);
//            commonListView.setAdapter(commonAdapter);
//            Utils.setListViewHeightBasedOnChildren(commonListView, 7);
//            Utils.setListViewHeightBasedOnChildren(recommentListView, 4);
//            HideProgressDialog();
//        }
//    }



    public void OnfromFragmentToActivity(int index)
    {
        if (index == 0) {
            if (shoppingPlaceFragment != null) {
                FragmentManager manager = getSupportFragmentManager();
//            manager.popBackStack();
                FragmentTransaction transaction = manager.beginTransaction();
//            transaction("placefragmen");
                transaction.remove(shoppingPlaceFragment);
                transaction.commit();
//                if (getSupportFragmentManager().popBackStackImmediate()) {
////                    collapse_layout();
//                    getSupportFragmentManager().popBackStack();
//                }
            }
//            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        else if (index == 1)
        {
            FragmentManager manager = getSupportFragmentManager();
            manager.popBackStack();
        }
        else if (index == 2) {
            if (objectDetailFragment != null) {
                FragmentManager manager = getSupportFragmentManager();
//            manager.popBackStack();
                FragmentTransaction transaction = manager.beginTransaction();
//            transaction("placefragmen");
                transaction.remove(objectDetailFragment);
                transaction.commit();
                if (getSupportFragmentManager().popBackStackImmediate()) {
//                    collapse_layout();
                    getSupportFragmentManager().popBackStack();
                }
            }
//            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
//        findViewById(R.id.frame).setVisibility(View.GONE);
//        collapse_layout();
    }


    public void OnGoogle(View view)
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        if (mAuth.getCurrentUser() == null || Utils.GetStringData(this, Constants.GOOGLE_LOGIN) == null || !Utils.GetStringData(this, Constants.GOOGLE_LOGIN).equals("true"))
            signIn();
        else {
            mAuth.signOut();
            Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(@NonNull Status status) {

                }
            });
            textGoogle.setText(getString(R.string.google_logout));
            textUser.setText("");
            LoginManager.getInstance().logOut();
            Utils.SetStringData(this, Constants.GOOGLE_LOGIN, "false");
            imageProfile.setImageBitmap(null);
            Intent intent = getIntent();
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(getIntent());
            finish();
        }


    }


    boolean moreFlag = false;
    void clearFragment()
    {
        FragmentManager fm = getSupportFragmentManager();
        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }
    public void ReplceFragment(int id)  {

        btnSpain.setVisibility(View.INVISIBLE);
        btnUSA.setVisibility(View.INVISIBLE);
        Constants.object_kind = id;
        if (id == 0) {
//            currentUser = mAuth.getCurrentUser();
//            if (currentUser != null)  ReplceFragment(1);
//            if (currentUser == null)
//            {
//                Utils.ShowToast(this, getString(R.string.request_login)); return;
//            }
            GetFromPlaceAPI(id);
            clearFragment();
            toggle_flag = false;
            findViewById(R.id.frame).setVisibility(View.GONE);
            textTitle.setText(getString(R.string.tab_rest));
            collapse_layout();
            if (restaurantFragment == null)
                restaurantFragment = RestaurantFragment.newInstance("", "");
            FragmentManager fragmentManager = getSupportFragmentManager();
            topbarLayout.setBackgroundColor(getResources().getColor(R.color.layout_color));
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            if (homeflag == true) {
                fragmentTransaction.remove(homeFragment);
                homeflag = false;
            }
            if (moreFlag == true) {
                fragmentTransaction.remove(moreFragment);
                moreFlag = false;
            }
            if (massTimeFragment != null) fragmentTransaction.remove(massTimeFragment);
            if (shoppingPlaceFragment != null)
                fragmentTransaction.remove(shoppingPlaceFragment);
//            fragmentTransaction.replace(R.id.frame, restaurantFragment);
//
            fragmentTransaction.commit();
//            GetData(id);

//            bottomBar.setActiveTabColor(Color.parseColor("#66ffffff"));
        } else if (id == 1) {
//            currentUser = mAuth.getCurrentUser();
////            if (currentUser != null)  ReplceFragment(1);
//            if (currentUser == null)
//            {
//                Utils.ShowToast(this, getString(R.string.request_login)); return;
//            }
//            GetData(id);
            clearFragment();
            GetFromPlaceAPI(id);
            toggle_flag = false;
            textTitle.setText(getString(R.string.tab_hotel));
            findViewById(R.id.frame).setVisibility(View.GONE);

            collapse_layout();
            if (hotelFragment == null) hotelFragment = HotelFragment.newInstance("", "");
            topbarLayout.setBackgroundColor(getResources().getColor(R.color.layout_color));
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (shoppingPlaceFragment != null)
                fragmentTransaction.remove(shoppingPlaceFragment);
            if (homeflag == true) {
                fragmentTransaction.remove(homeFragment);
                homeflag = false;
            }
            if (massTimeFragment != null) fragmentTransaction.remove(massTimeFragment);
            if (moreFlag == true) {
                fragmentTransaction.remove(moreFragment);
                moreFlag = false;
            }
//            fragmentTransaction.replace(R.id.frame, hotelFragment);
            fragmentTransaction.commit();
        } else if (id == 2) {
            GetFromPlaceAPI(id);
            clearFragment();
            toggle_flag = false;
            findViewById(R.id.frame).setVisibility(View.GONE);
            textTitle.setText(getString(R.string.tab_parking));
            collapse_layout();

            if (parkingFragment == null) parkingFragment = ParkingFragment.newInstance("", "");
            topbarLayout.setBackgroundColor(getResources().getColor(R.color.layout_color));
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (homeflag == true) {
                fragmentTransaction.remove(homeFragment);
                homeflag = false;
            }
            if (moreFlag == true) {
                fragmentTransaction.remove(moreFragment);
                moreFlag = false;
            }

            if (massTimeFragment != null) fragmentTransaction.remove(massTimeFragment);
            if (shoppingPlaceFragment != null)
                fragmentTransaction.remove(shoppingPlaceFragment);
//            fragmentTransaction.replace(R.id.frame, parkingFragment);
            fragmentTransaction.commit();
        } else if (id == 3) {
            findViewById(R.id.frame).setVisibility(View.VISIBLE);
            if (massTimeFragment == null) massTimeFragment = massTimeFragment.newInstance("", "");
            clearFragment();
            topbarLayout.setBackgroundColor(getResources().getColor(R.color.layout_color));
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (homeflag == true) {
                fragmentTransaction.remove(homeFragment);
                homeflag = false;
            }
            if (shoppingPlaceFragment != null)
                fragmentTransaction.remove(shoppingPlaceFragment);

            if (massTimeFragment != null) fragmentTransaction.remove(massTimeFragment);
            fragmentTransaction.replace(R.id.frame, massTimeFragment);

            fragmentTransaction.commit();
            linearLayout.setVisibility(View.GONE);
        } else if (id == 5) {

            btnSpain.setVisibility(View.VISIBLE);
            btnUSA.setVisibility(View.VISIBLE);
            if (homeFragment == null) homeFragment = HomeFragment.newInstance("", "");
            FragmentManager fragmentManager = getSupportFragmentManager();
            textTitle.setText(getString(R.string.tab_more));
            topbarLayout.setBackgroundColor(getResources().getColor(R.color.transparent));
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.home_frame, homeFragment);
            fragmentTransaction.commit();
            linearLayout.setVisibility(View.GONE);
            homeflag = true;
            if (moreFlag == true) {
                fragmentTransaction.remove(moreFragment);
                moreFlag = false;
            }
            if (shoppingPlaceFragment != null)
                fragmentTransaction.remove(shoppingPlaceFragment);
//            if (mapFragment == null) mapFragment = new PageMapFragment();
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.replace(R.id.frame, mapFragment);
//            fragmentTransaction.commit();
        } else if (id == 4) {
            findViewById(R.id.frame).setVisibility(View.VISIBLE);
            if (moreFragment == null) moreFragment = MoreFragment.newInstance("", "");
            clearFragment();
            topbarLayout.setBackgroundColor(getResources().getColor(R.color.layout_color));
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (homeflag == true) {
                fragmentTransaction.remove(homeFragment);
                homeflag = false;
            }
            if (shoppingPlaceFragment != null)
                fragmentTransaction.remove(shoppingPlaceFragment);

            moreFlag = true;
            if (moreFragment != null) fragmentTransaction.remove(moreFragment);
            if (massTimeFragment != null) fragmentTransaction.remove(massTimeFragment);
            fragmentTransaction.replace(R.id.frame, moreFragment);

            fragmentTransaction.commit();
            linearLayout.setVisibility(View.GONE);

        } else if (id == 6) {
            findViewById(R.id.frame).setVisibility(View.VISIBLE);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (shoppingPlaceFragment != null)
                fragmentTransaction.remove(shoppingPlaceFragment);
            if (massTimeFragment != null) fragmentTransaction.remove(massTimeFragment);
            massTimeFragment = MassTimeFragment.newInstance("", "");
            topbarLayout.setBackgroundColor(getResources().getColor(R.color.layout_color));

            fragmentTransaction.replace(R.id.frame, massTimeFragment);
            fragmentTransaction.addToBackStack("church");
            fragmentTransaction.commit();
            linearLayout.setVisibility(View.GONE);
        }
        else if (id == 7)
        {
            findViewById(R.id.frame).setVisibility(View.VISIBLE);
//            if (shoppingDetailFragment == null)
            shoppingDetailFragment = ShoppingDetailFragment.newInstance("shop", "");
            topbarLayout.setBackgroundColor(getResources().getColor(R.color.layout_color));
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame, shoppingDetailFragment);
            fragmentTransaction.addToBackStack("shop");
            fragmentTransaction.commit();
            linearLayout.setVisibility(View.GONE);
        }



        else if (id == 8)
        {
            findViewById(R.id.frame).setVisibility(View.VISIBLE);
//            if (shoppingPlaceFragment == null)
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (shoppingPlaceFragment != null)
                fragmentTransaction.remove(shoppingPlaceFragment);
            shoppingPlaceFragment = ShoppingPlaceFragment.newInstance(shoppingModel, "");
            topbarLayout.setBackgroundColor(getResources().getColor(R.color.layout_color));


//            fragmentManager.

            fragmentTransaction.add(R.id.frame, shoppingPlaceFragment);
            fragmentTransaction.addToBackStack("stack");
//            fragmentTransaction.replace(R.id.frame, shoppingPlaceFragment);
            fragmentTransaction.commit();
            linearLayout.setVisibility(View.GONE);
        }
        else if (id == 9) {
            findViewById(R.id.frame).setVisibility(View.VISIBLE);
//            if (shoppingDetailFragment == null)
            shoppingDetailFragment = ShoppingDetailFragment.newInstance("attraction", "");

            topbarLayout.setBackgroundColor(getResources().getColor(R.color.layout_color));
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame, shoppingDetailFragment);
            fragmentTransaction.addToBackStack("attraction");
            fragmentTransaction.commit();

            linearLayout.setVisibility(View.GONE);
        }
        else if (id == 10) {
            findViewById(R.id.frame).setVisibility(View.VISIBLE);
//            if (shoppingDetailFragment == null)
            shoppingDetailFragment = ShoppingDetailFragment.newInstance("coffee", "");
            topbarLayout.setBackgroundColor(getResources().getColor(R.color.layout_color));
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame, shoppingDetailFragment);
            fragmentTransaction.addToBackStack("coffee");
            fragmentTransaction.commit();
            linearLayout.setVisibility(View.GONE);
        }
        else if (id == 11) {
            findViewById(R.id.frame).setVisibility(View.VISIBLE);
//            if (shoppingDetailFragment == null)
            shoppingDetailFragment = ShoppingDetailFragment.newInstance("travel", "");
            topbarLayout.setBackgroundColor(getResources().getColor(R.color.layout_color));
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.frame, shoppingDetailFragment);
            fragmentTransaction.addToBackStack("travel");
            fragmentTransaction.commit();
            linearLayout.setVisibility(View.GONE);
        }
        else if (id == 12) {
            findViewById(R.id.frame).setVisibility(View.VISIBLE);
//            if (shoppingDetailFragment == null)
            shoppingDetailFragment = ShoppingDetailFragment.newInstance("emer", "");
            topbarLayout.setBackgroundColor(getResources().getColor(R.color.layout_color));
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame, shoppingDetailFragment);
            fragmentTransaction.addToBackStack("emer");
            fragmentTransaction.commit();
            linearLayout.setVisibility(View.GONE);
        }
        else if (id == 13) {

        }
        else if (id == 14) {
        }
        else if (id == 15) {
//            if (contactUSFragment == null)
            contactUSFragment = ContactUSFragment.newInstance("", "");
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame, contactUSFragment);
            fragmentTransaction.addToBackStack("contact");
            fragmentTransaction.commit();
            linearLayout.setVisibility(View.GONE);
        }
    }

    public void HotelParkingRestaurant()
    {

        findViewById(R.id.frame).setVisibility(View.VISIBLE);
//            if (shoppingPlaceFragment == null)
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (objectDetailFragment != null)
            fragmentTransaction.remove(objectDetailFragment);
        objectDetailFragment = ObjectDetailFragment.newInstance(shoppingModel, "");
        topbarLayout.setBackgroundColor(getResources().getColor(R.color.layout_color));
//            fragmentManager.

        fragmentTransaction.add(R.id.frame, objectDetailFragment);
        fragmentTransaction.addToBackStack("stack");
//            fragmentTransaction.replace(R.id.frame, shoppingPlaceFragment);
        fragmentTransaction.commit();
//        linearLayout.setVisibility(View.GONE);

    }

    boolean toggle_flag;

    int fullHeight;


    void collapse_layout() {
        imageButton.setImageResource(R.drawable.up_arrow);
        if (linearLayout.getVisibility() == View.GONE) linearLayout.setVisibility(View.VISIBLE);
        ViewGroup.LayoutParams params = linearLayout.getLayoutParams();
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());
//        params.height = getResources().getDimensionPixelSize(R.dimen.item_height);
        params.height = height;
        linearLayout.setLayoutParams(params);

    }

    float initialX, initialY, finalX, finalY;

    void expand_layout() {
        imageButton.setImageResource(R.drawable.down_arrow);
        if (linearLayout.getVisibility() == View.GONE) linearLayout.setVisibility(View.VISIBLE);
        ViewGroup.LayoutParams params = linearLayout.getLayoutParams();

        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        linearLayout.setLayoutParams(params);

    }

    public void OnTimes(View view){
//        Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_SHORT).show();
        ReplceFragment(6);
    }

    public void OnShopping(View view){

        ReplceFragment(7);
    }

    public void OnAttract(View view) {
        ReplceFragment(9);
    }

    public void OnCoffeeShop(View view){
        ReplceFragment(10);

    }

    public void OnArrive(View view) {
//        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
//                Uri.parse("http://maps.google.com/maps?saddr=20.344,34.34&daddr=20.5666,45.345"));
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("google.navigation:q=Cl.+3+%2312-54%2C+Guadalajara+de+Buga%2C+Valle+del+Cauca%2C+Colombia"));
        startActivity(intent);

    }

    public void OnTravelAgency(View view){

        ReplceFragment(11);
    }

    public void OnEmergency(View view) {
        ReplceFragment(12);
    }

    public void OnContact(View view){
        ReplceFragment(15);
    }

    private void UpdateUI(FirebaseUser user)
    {
        Picasso.with(this).load(user.getPhotoUrl()).into(imageProfile);
        textUser.setText(user.getDisplayName());
        if (Utils.GetStringData(this, Constants.FACEBOOK_LOGIN).equals("true"))
            textFacebook.setText(getString(R.string.facebook_logout));
        if (Utils.GetStringData(this, Constants.GOOGLE_LOGIN).equals("true"))
            textGoogle.setText(getString(R.string.google_logout));
    }
    TextView textFacebook;
    CircularImageView imageProfile;
    TextView textUser;
    CallbackManager mCallbackManager;
    TextView textGoogle;
    public static GoogleApiClient mGoogleApiClient;
    //    @RequiresApi(api = Build.VERSION_CODES.M)

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    float oldtimestamp, newtimestamp;
    float speedX, speedY;
    private VelocityTracker mVelocityTracker = null, mVelocityTracker1 = null, mVelocityTracker2 = null;
    void init() {
//        bottomBar = (BottomBar) findViewById(R.id.bottomBar) ;

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        linearLayout = (LinearLayout) findViewById(R.id.mainFrame);
        textUser = (TextView) findViewById(R.id.textView2);
        textFacebook = (TextView) findViewById(R.id.text_facebook);
        textGoogle = (TextView) findViewById(R.id.text_google);
        imageProfile = (CircularImageView) findViewById(R.id.userprofile);
        topbarLayout = (RelativeLayout) findViewById(R.id.topBar);
//        ((ImageView) findViewById(R.id.imageView2)).setColorFilter(getResources().getColor(R.color.layout_color));
        relativeLayout = (RelativeLayout) findViewById(R.id.mainlayout);

        commonListView = (ListView) findViewById(R.id.commonList);
        recommentListView = (ListView) findViewById(R.id.recommendList);
        imageButton = (ImageButton) findViewById(R.id.imageButton4);
        imgRest = (ImageView) findViewById(R.id.imageView4);
        imgHotel = (ImageView) findViewById(R.id.imageView5);
        imgParking = (ImageView) findViewById(R.id.imageView6);
        imgEvents = (ImageView) findViewById(R.id.imageView7);
        imgMore = (ImageView) findViewById(R.id.imageView8);
        textTitle = (TextView) findViewById(R.id.textView16);
        textRest = (TextView) findViewById(R.id.textView11);
        textHotel = (TextView) findViewById(R.id.textView12);
        textParking = (TextView) findViewById(R.id.textView13);
        textEvents = (TextView) findViewById(R.id.textView14);
        textMore = (TextView) findViewById(R.id.textView15);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        btnSpain = (ImageButton) findViewById(R.id.imageButton3);
        btnUSA = (ImageButton) findViewById(R.id.imageButton6);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        Utils.currentUser =  mAuth.getCurrentUser();
        if (mAuth.getCurrentUser() != null)
        {
            UpdateUI(mAuth.getCurrentUser());
        }
        else
        {
            textFacebook.setText(getString(R.string.menu_item_1));
            imageProfile.setImageBitmap(null);
            textUser.setText("");
        }
        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        LinearLayout mDrawerList = (LinearLayout) findViewById(R.id.left_drawer);

        int width = getResources().getDisplayMetrics().widthPixels/2;
        DrawerLayout.LayoutParams params = (android.support.v4.widget.DrawerLayout.LayoutParams) mDrawerList.getLayoutParams();
        params.width = width;
        mDrawerList.setLayoutParams(params);
        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                toggle_flag = !toggle_flag;
                if (toggle_flag) {
                    expand_layout();
                } else {

                    collapse_layout();
                }
            }
        });
        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int index = motionEvent.getActionIndex();
                int action = motionEvent.getActionMasked();
                int pointerId = motionEvent.getPointerId(index);
                switch (action) {
                    case MotionEvent.ACTION_DOWN: {
                        initialX = motionEvent.getX();
                        initialY = motionEvent.getY();
                        oldtimestamp = (float) System.currentTimeMillis();
                        Log.d("touch", "down");
                        if(mVelocityTracker == null) {
                            // Retrieve a new VelocityTracker object to watch the velocity of a motion.
                            mVelocityTracker = VelocityTracker.obtain();
                        }
                        else {
                            // Reset the velocity tracker back to its initial state.
                            mVelocityTracker.clear();
                        }
                        // Add a user's movement to the tracker.
                        mVelocityTracker.addMovement(motionEvent);
                        return true;
                    }
                    case MotionEvent.ACTION_UP: {
                        Log.d("touch", "up");
                        finalX = motionEvent.getX();
                        finalY = motionEvent.getY();
                        newtimestamp = System.currentTimeMillis();
                        if (Math.abs(initialY - finalY) > delta) {
                            if (Math.abs(speedY) > speed_delta) {
                                if (initialY < finalY) {
                                    collapse_layout();
                                } else {
                                    expand_layout();
                                }
                            }
                        }
                        return true;
                    }
                    case MotionEvent.ACTION_MOVE:
                        mVelocityTracker.addMovement(motionEvent);
                        mVelocityTracker.computeCurrentVelocity(1000);
                        Log.d("", "X velocity: " +
                                VelocityTrackerCompat.getXVelocity(mVelocityTracker,
                                        pointerId));
                        Log.d("", "Y velocity: " +
                                VelocityTrackerCompat.getYVelocity(mVelocityTracker,
                                        pointerId));
                        speedY = VelocityTrackerCompat.getYVelocity(mVelocityTracker,
                                pointerId);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        if (mVelocityTracker != null) mVelocityTracker.recycle();
                        mVelocityTracker = null;
                        break;
                    default:
                        return MainActivity.super.onTouchEvent(motionEvent);
                }
                return MainActivity.super.onTouchEvent(motionEvent);
            }
        });
        commonListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int index = motionEvent.getActionIndex();
                int action = motionEvent.getActionMasked();
                int pointerId = motionEvent.getPointerId(index);
                switch (action) {
                    case MotionEvent.ACTION_DOWN: {
                        initialX = motionEvent.getX();
                        initialY = motionEvent.getY();
                        oldtimestamp = (float) System.currentTimeMillis();
                        Log.d("touch", "down");
                        if(mVelocityTracker1 == null) {
                            // Retrieve a new VelocityTracker object to watch the velocity of a motion.
                            mVelocityTracker1 = VelocityTracker.obtain();
                        }
                        else {
                            // Reset the velocity tracker back to its initial state.
                            mVelocityTracker1.clear();
                        }
                        // Add a user's movement to the tracker.
                        mVelocityTracker1.addMovement(motionEvent);
                        return true;
                    }
                    case MotionEvent.ACTION_UP: {
                        Log.d("touch", "up");
                        finalX = motionEvent.getX();
                        finalY = motionEvent.getY();
                        newtimestamp = System.currentTimeMillis();
                        if (Math.abs(initialY - finalY) > delta) {
                            if (Math.abs(speedY) > speed_delta) {
                                if (initialY < finalY) {
                                    collapse_layout();
                                } else {
                                    expand_layout();
                                }
                            }
                        }
                        return true;
                    }
                    case MotionEvent.ACTION_MOVE:
                        mVelocityTracker1.addMovement(motionEvent);
                        mVelocityTracker1.computeCurrentVelocity(1000);
                        Log.d("", "X velocity: " +
                                VelocityTrackerCompat.getXVelocity(mVelocityTracker1,
                                        pointerId));
                        Log.d("", "Y velocity: " +
                                VelocityTrackerCompat.getYVelocity(mVelocityTracker1,
                                        pointerId));
                        speedY = VelocityTrackerCompat.getYVelocity(mVelocityTracker1,
                                pointerId);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        if (mVelocityTracker1 != null) mVelocityTracker1.recycle();
                        mVelocityTracker1 = null;
                        break;
                    default:
                        return MainActivity.super.onTouchEvent(motionEvent);
                }
                return MainActivity.super.onTouchEvent(motionEvent);
            }
        });

        recommentListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int index = motionEvent.getActionIndex();
                int action = motionEvent.getActionMasked();
                int pointerId = motionEvent.getPointerId(index);
                switch (action) {
                    case MotionEvent.ACTION_DOWN: {
                        initialX = motionEvent.getX();
                        initialY = motionEvent.getY();
                        oldtimestamp = (float) System.currentTimeMillis();
                        Log.d("touch", "down");
                        if(mVelocityTracker2 == null) {
                            // Retrieve a new VelocityTracker object to watch the velocity of a motion.
                            mVelocityTracker2 = VelocityTracker.obtain();
                        }
                        else {
                            // Reset the velocity tracker back to its initial state.
                            mVelocityTracker2.clear();
                        }
                        // Add a user's movement to the tracker.
                        mVelocityTracker2.addMovement(motionEvent);
                        return true;
                    }
                    case MotionEvent.ACTION_UP: {
                        Log.d("touch", "up");
                        finalX = motionEvent.getX();
                        finalY = motionEvent.getY();
                        newtimestamp = System.currentTimeMillis();
                        if (Math.abs(initialY - finalY) > delta) {
                            if (Math.abs(speedY) > speed_delta) {
                                if (initialY < finalY) {
                                    collapse_layout();
                                } else {
                                    expand_layout();
                                }
                            }
                        }
                        return true;
                    }
                    case MotionEvent.ACTION_MOVE:
                        mVelocityTracker2.addMovement(motionEvent);
                        mVelocityTracker2.computeCurrentVelocity(1000);
                        Log.d("", "X velocity: " +
                                VelocityTrackerCompat.getXVelocity(mVelocityTracker2,
                                        pointerId));
                        Log.d("", "Y velocity: " +
                                VelocityTrackerCompat.getYVelocity(mVelocityTracker2,
                                        pointerId));
                        speedY = VelocityTrackerCompat.getYVelocity(mVelocityTracker2,
                                pointerId);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        if (mVelocityTracker2 != null) mVelocityTracker2.recycle();
                        mVelocityTracker2 = null;
                        break;
                    default:
                        return MainActivity.super.onTouchEvent(motionEvent);
                }
                return MainActivity.super.onTouchEvent(motionEvent);
            }
        });
        ReplceFragment(5);

        GetData();


        findViewById(R.id.imageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else
                    drawer.openDrawer(GravityCompat.START);
            }
        });
//        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
//            @Override
//            public void onTabSelected(@IdRes int tabId) {
//                if (tabId == R.id.tab_rest)
//                {
//                    ReplceFragment(0);
//                }
//                else if (tabId == R.id.tab_hotel)
//                {
//                    ReplceFragment(1);
//                }
//                else if (tabId == R.id.tab_parking)
//                {
//                    ReplceFragment(2);
//                }
//                else if (tabId == R.id.tab_events)
//                {
//                    ReplceFragment(3);
//                }
//                else if (tabId == R.id.tab_more)
//                {
//                    ReplceFragment(4);
//                }
//            }
//        });
        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError:" + error);
                Utils.ShowToast(getApplicationContext(), "Facebook Error");
            }
        });
    }
    FirebaseUser currentUser;
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        // [START_EXCLUDE silent]
        showProgressDialog();
        // [END_EXCLUDE]

        if (token == null) return;
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        hideProgressDialog();
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            currentUser = task.getResult().getUser();
                            Utils.SetStringData(getApplicationContext(), Constants.FACEBOOK_LOGIN, "true");
//                            General.SetStringData(getApplicationContext(), Constants.GOOGLE_LOGIN, "false");
//                            General.SetStringData(getApplicationContext(), Constants.EMAIL_LOGIN, "false");
                            //     General.ShowToast(getApplicationContext(), "Success");
                            Utils.currentUser = currentUser;
                            Google_FacebookUserUpdate();
                        }
                    }


                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void Google_FacebookUserUpdate() {
        UpdateUI(currentUser);
        Utils.SetStringData(getApplicationContext(), Constants.USER_EMAIL, currentUser.getEmail());
        Utils.SetStringData(getApplicationContext(), Constants.USER_NAME, currentUser.getDisplayName());
        Utils.SetStringData(getApplicationContext(), Constants.USER_ID, currentUser.getUid());
        Utils.SetStringData(getApplicationContext(), Constants.USER_LOGINED, "true");
//        Utils.SetStringData(getApplicationContext(), Constants.USER_PASSWORD, password);

        String string = currentUser.getDisplayName();
        String arr[] = string.split(" ");
        final String firstname = arr[0];
        final String lastname = arr[1];
        Utils.currentUser = currentUser;
        if (currentUser.getPhotoUrl() != null)
            Utils.SetStringData(getApplicationContext(), Constants.USER_PHOTO, currentUser.getPhotoUrl().toString());
        final String token = FirebaseInstanceId.getInstance().getToken();

//        final DatabaseReference temp = mDatabase.child(currentUser.getUid());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN)
        {
            //     Toast.makeText(this, "Google Sign In", Toast.LENGTH_SHORT).show();
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess())
            {
                GoogleSignInAccount account = result.getSignInAccount();
                Log.d("Success", "google");
                firebaseAuthWithGoogle(account);
            }
            else
            {
                Toast.makeText(getApplicationContext(), result.getStatus().toString(), Toast.LENGTH_LONG).show();
//                updateUI(null);
            }
        }
        else
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d(GOOGLE_TAG, "firebaseAuthWithgoogle:|" + account.getId());
        showProgressDialog();
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(GOOGLE_TAG, "signInWithCredential:OnConplete:" + task.isSuccessful());
                if (!task.isSuccessful())
                {
                    Log.w(GOOGLE_TAG, "signInWithCredential", task.getException());
                    Toast.makeText(getApplicationContext(), "Authentication failed", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    currentUser = task.getResult().getUser();
//                    Utils.SetStringData(getApplicationContext(), Constants.FACEBOOK_LOGIN, "false");
                    Utils.SetStringData(getApplicationContext(), Constants.GOOGLE_LOGIN, "true");
//                    General.SetStringData(getApplicationContext(), Constants.EMAIL_LOGIN, "false");
                    Utils.currentUser = currentUser;
                    Google_FacebookUserUpdate();


                }
                hideProgressDialog();
            }
        })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        return;
                    }
                });
    }

    private ProgressDialog progressDialog;

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.str_loading));
        progressDialog.show();
    }

    private void hideProgressDialog()
    {
        progressDialog.hide();
    }
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        imageView = (ImageView) findViewById(R.id.imageView2);


//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
    }

    private void GetData() {


    }

    private void FacebookLogIn()
    {
        if (isFacebookLoginded())
        {
            LoginManager.getInstance().logOut();
        }
        else
        {
            LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, Arrays.asList("email", "public_profile", "user_friends"));
        }
    }
    boolean isFacebookLoginded()
    {
        if (AccessToken.getCurrentAccessToken() != null)
        {
            return true;
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void OnFacebook(View view) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        if (mAuth.getCurrentUser() == null || Utils.GetStringData(this, Constants.FACEBOOK_LOGIN) == null || !Utils.GetStringData(this, Constants.FACEBOOK_LOGIN).equals("true"))
            FacebookLogIn();
        else {
            mAuth.signOut();
            textFacebook.setText(getString(R.string.menu_item_1));
            textUser.setText("");
            if (AccessToken.getCurrentAccessToken() != null)
            {
                //    General.ShowToast(getApplicationContext(), "already logined");
                LoginManager.getInstance().logOut();
            }
            Utils.SetStringData(this, Constants.FACEBOOK_LOGIN, "false");
            imageProfile.setImageBitmap(null);
            startActivity(getIntent());
            finish();
        }

    }

    public void OnHelp(View view) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.app_name))
                .setMessage(Constants.help_text)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
    }

    public void OnSettings(View view) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    int currentIndex;
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        Log.d("back", "back");
        if (getSupportFragmentManager().popBackStackImmediate()) {
//            collapse_layout();
            getSupportFragmentManager().popBackStack();
        }
        else
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void OnLegal(View view) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.app_name))
                .setMessage(Constants.legal_text)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void OnRest(View view) {
        textRest.setTextColor(getResources().getColor(R.color.highlight_color));
        imgRest.setColorFilter(getResources().getColor(R.color.highlight_color));
        textMore.setTextColor(getResources().getColor(R.color.white));
        textParking.setTextColor(getResources().getColor(R.color.white));
        textHotel.setTextColor(getResources().getColor(R.color.white));
        textEvents.setTextColor(getResources().getColor(R.color.white));
        imgHotel.setColorFilter(null);
        imgParking.setColorFilter(null);
        imgEvents.setColorFilter(null);
        imgMore.setColorFilter(null);
        currentUser = mAuth.getCurrentUser();

        ReplceFragment(0);


    }

    public void OnHotel(View view) {
        textHotel.setTextColor(getResources().getColor(R.color.highlight_color));
        imgHotel.setColorFilter(getResources().getColor(R.color.highlight_color));


        textMore.setTextColor(getResources().getColor(R.color.white));
        textParking.setTextColor(getResources().getColor(R.color.white));
        textRest.setTextColor(getResources().getColor(R.color.white));
        textEvents.setTextColor(getResources().getColor(R.color.white));


        imgRest.setColorFilter(null);
        imgParking.setColorFilter(null);
        imgEvents.setColorFilter(null);
        imgMore.setColorFilter(null);
        ReplceFragment(1);

    }

    public void OnParking(View view){
        textParking.setTextColor(getResources().getColor(R.color.highlight_color));
        imgParking.setColorFilter(getResources().getColor(R.color.highlight_color));


        textMore.setTextColor(getResources().getColor(R.color.white));
        textHotel.setTextColor(getResources().getColor(R.color.white));
        textRest.setTextColor(getResources().getColor(R.color.white));
        textEvents.setTextColor(getResources().getColor(R.color.white));


        imgRest.setColorFilter(null);
        imgHotel.setColorFilter(null);
        imgEvents.setColorFilter(null);
        imgMore.setColorFilter(null);
        ReplceFragment(2);
    }

    public void OnEvents(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.event_message);
        builder.setPositiveButton(R.string.str_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();

    }


    public void OnMisas(View view)
    {
        textEvents.setTextColor(getResources().getColor(R.color.highlight_color));
        imgEvents.setColorFilter(getResources().getColor(R.color.highlight_color));


        textMore.setTextColor(getResources().getColor(R.color.white));
        textHotel.setTextColor(getResources().getColor(R.color.white));
        textRest.setTextColor(getResources().getColor(R.color.white));
        textParking.setTextColor(getResources().getColor(R.color.white));


        imgRest.setColorFilter(null);
        imgHotel.setColorFilter(null);
        imgMore.setColorFilter(null);
        imgParking.setColorFilter(null);
        ReplceFragment(3);
    }
    public void OnMore(View view) {
        textMore.setTextColor(getResources().getColor(R.color.highlight_color));
        imgMore.setColorFilter(getResources().getColor(R.color.highlight_color));


        textEvents.setTextColor(getResources().getColor(R.color.white));
        textHotel.setTextColor(getResources().getColor(R.color.white));
        textRest.setTextColor(getResources().getColor(R.color.white));
        textParking.setTextColor(getResources().getColor(R.color.white));


        imgRest.setColorFilter(null);
        imgHotel.setColorFilter(null);
        imgEvents.setColorFilter(null);
        imgParking.setColorFilter(null);
        ReplceFragment(4);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // refresh your views here
        super.onConfigurationChanged(newConfig);
    }

    public void OnEng(View view) {
        languageToLoad = "en";
        SharedPreferences preferences = getSharedPreferences("city", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("lan", "en");
        editor.commit();
        Toast.makeText(this, "The language is changed to English.", Toast.LENGTH_SHORT).show();
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    public void OnSpain(View view) {
        languageToLoad = "es";
        SharedPreferences preferences = getSharedPreferences("city", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("lan", "es");
        editor.commit();
        Toast.makeText(this, "Language is changed to Spanish.", Toast.LENGTH_SHORT).show();
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }


    boolean mylocation_flag;

    @SuppressWarnings("ResourceType")
    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
        googleMap.getUiSettings().setMapToolbarEnabled(true);
        googleMap.setInfoWindowAdapter(new CustomInfoWindow());



        // For dropping a marker at a point on the Map
        LatLng sydney = Constants.mylocation;
//    googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));
        Circle circle = googleMap.addCircle(new CircleOptions()
                .center(sydney)
                .radius(500)
                .strokeColor(Color.parseColor("#C5CFDF"))
                .fillColor(0x55c5cfdf)
                .strokeWidth(5.0f)
        );
        mylocation_flag = false;
        final ImageView locationButton = (ImageView) mapFragment.getView().findViewById(2);
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
        CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(15).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//        GetData(0);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (!connectionResult.isSuccess())
            Toast.makeText(getApplicationContext(), "Connection Failed", Toast.LENGTH_LONG).show();
    }

    private class CustomInfoWindow implements GoogleMap.InfoWindowAdapter {
        private View view;

        public CustomInfoWindow() {

            view = getLayoutInflater().inflate(R.layout.custom_info_window,
                    null);

        }

        @Override
        public View getInfoWindow(Marker marker) {

            TextView textName = (TextView) view.findViewById(R.id.textView26);
            TextView textRating = (TextView) view.findViewById(R.id.textView18);
//            ImageView imageView = (ImageView) view.findViewById(R.id.imageView10);
            Log.d("marker", marker.getId());
            FirebaseObjectModel model = markers.get(marker.getId());
            if (model == null) return view;
            textName.setText(model.getName());
//            Calificación
            textRating.setText(getString(R.string.rating) + ": " + model.getRate());
            if (model.getPremium() != null) {
                String url = model.getPremium().get("content_1");
//        Log.d("imageurl", url);
                try {
                    if (url != null && !url.equals(""))

                    {
                        StorageReference storage = FirebaseStorage.getInstance().getReferenceFromUrl(url);
//                        Glide.with(MainActivity.this).using(new FirebaseImageLoader()).load(storage).into(imageView);
                    } else {
//                        imageView.setImageResource(R.drawable.no_photo_icon);
//                        Toast.makeText(MainActivity.this, getResources().getString(R.string.no_image), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
//                    Toast.makeText(MainActivity.this, getResources().getString(R.string.no_image), Toast.LENGTH_SHORT).show();
//                    imageView.setImageResource(R.drawable.no_photo_icon);
                }
            }
            else
            {
//                imageView.setImageResource(R.drawable.no_photo_icon);
            }
            return view;
        }


        @Override
        public View getInfoContents(Marker marker) {
            return null;
        }
    }
}
