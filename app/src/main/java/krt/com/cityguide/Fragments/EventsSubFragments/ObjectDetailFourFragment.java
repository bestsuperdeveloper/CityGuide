package krt.com.cityguide.Fragments.EventsSubFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import krt.com.cityguide.Models.FirebaseObjectModel;
import krt.com.cityguide.Models.RatingModel;
import krt.com.cityguide.R;
import krt.com.cityguide.Utils.Constants;
import krt.com.cityguide.custom.CustomRatingBar;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ObjectDetailFourFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ObjectDetailFourFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private FirebaseObjectModel mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ObjectDetailFourFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ObjectDetailFourFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ObjectDetailFourFragment newInstance(FirebaseObjectModel param1, String param2) {
        ObjectDetailFourFragment fragment = new ObjectDetailFourFragment();
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.fragment_object_detail_four, container, false);
        initView();
        return mainView;
    }
    CustomRatingBar ratingBar;
    TextView textScore;
    private void initView() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        ratingBar = (CustomRatingBar) mainView.findViewById(R.id.ratingBar1);
        textScore = (TextView) mainView.findViewById(R.id.textView22);

        calculateRating();
//        double val = Math.round(mParam1.getRate());
//        Log.d("value", val + "" + mParam1.getObjectId());

    }

    private void calculateRating() {
        FirebaseDatabase.getInstance().getReference(mParam1.getObjectKind())
                .child(mParam1.getObjectId())
                .child("rating").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, HashMap<String, String>> map =  (HashMap<String, HashMap<String, String>>) dataSnapshot.getValue();
                if (map != null)
                {
                    double s = 0.0, index = 0.0;
                    for (Map.Entry<String, HashMap<String, String>> entry : map.entrySet()) {
                        String key = entry.getKey();
                        HashMap<String, String> tab = entry.getValue();
                        RatingModel model = RatingModel.getFromHashMap(tab);
                        index = index + 1;
                        s = s + Double.parseDouble(model.getScore());
                    }
                    s = s / index;
                    double temp = Math.round(s * 10) / 10.0;
                    double value = Math.round(temp);
                    ratingBar.setScore((float) value);
                    ratingBar.setScrollToSelect(false);
                    ratingBar.setHalfStars(false);
                    textScore.setText(value + "" + "/5");
                }
                else
                {
                    ratingBar.setScore(0.0f);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
