package krt.com.cityguide.Fragments.EventsSubFragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;




import com.squareup.okhttp.Authenticator;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import net.soroushjavdan.androidmandrillinterface.Attachment;
import net.soroushjavdan.androidmandrillinterface.EmailMessage;
import net.soroushjavdan.androidmandrillinterface.MandrillMessage;
import net.soroushjavdan.androidmandrillinterface.Recipient;

import java.io.IOException;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import krt.com.cityguide.Models.FirebaseObjectModel;
import krt.com.cityguide.Models.RatingModel;
import krt.com.cityguide.R;
import krt.com.cityguide.Utils.Constants;
import krt.com.cityguide.Utils.Utils;
import krt.com.cityguide.custom.CustomRatingBar;


public class ObjectDetailThreeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private FirebaseObjectModel mParam1;
    private String mParam2;

    OkHttpClient httpClient;

    public ObjectDetailThreeFragment() {
        // Required empty public constructor
    }

    private final int MAX_WORDS = 100;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ObjectDetailFragmentThree.
     */
    // TODO: Rename and change types and number of parameters
    public static ObjectDetailThreeFragment newInstance(FirebaseObjectModel param1, String param2) {
        ObjectDetailThreeFragment fragment = new ObjectDetailThreeFragment();
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
            mParam1 = (FirebaseObjectModel)getArguments().getSerializable(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    View mainView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.fragment_object_detail_fragment_three, container, false);
        initView();
        return mainView;
    }

    EditText editComment;
    LinearLayout linearLayout;
    CustomRatingBar ratingBar;
    Button giveRate;
    FirebaseUser user;
    TextView textLength;
    String name;
    private void initView() {

        user = FirebaseAuth.getInstance().getCurrentUser();
        linearLayout = (LinearLayout) mainView.findViewById(R.id.rating_layout);
        ratingBar = (CustomRatingBar)mainView.findViewById(R.id.ratingBar1);
        giveRate = (Button) mainView.findViewById(R.id.button2);
        textLength = (TextView) mainView.findViewById(R.id.textView28);
        linearLayout.setVisibility(View.GONE);
        ratingBar.setHalfStars(false);
        ratingBar.setScore(0.0f);
        editComment = (EditText) mainView.findViewById(R.id.editText) ;

        giveRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GiveRate();
            }
        });

        editComment.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                int wordsLength = countWords(s.toString());// words.length;
                int wordsLength = s.toString().length();// words.length;
                textLength.setText(wordsLength + "/" + "100");
                // count == 0 means a new word is going to start
                if (wordsLength >= MAX_WORDS) {
                    setCharLimit(editComment, editComment.getText().length());
                } else {
                    removeFilter(editComment);
                }

//                tvWordCount.setText(String.valueOf(wordsLength) + "/" + MAX_WORDS);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        if (user == null)
        {
            Toast.makeText(getContext(), getActivity().getResources().getString(R.string.request_login), Toast.LENGTH_SHORT).show();
            ratingBar.setEnabled(false);
            ratingBar.setClickable(false);
            ratingBar.setScrollToSelect(false);
            return;
        }
        else {
            ratingBar.setEnabled(true);
            ratingBar.setClickable(true);
            uid = user.getUid();
            name = user.getDisplayName();
            ratingBar.setOnScoreChanged(new CustomRatingBar.IRatingBarCallbacks() {
                @Override
                public void scoreChanged(float score) {
                    if (score < 1.0)
                    {
                        linearLayout.setVisibility(View.INVISIBLE);
                    }
                    else
                    {
                        linearLayout.setVisibility(View.VISIBLE);
                    }
                }
            });
            Log.d("objectkind_id", mParam1.getObjectId() + " " + mParam1.getObjectKind() + " " + uid);

            FirebaseDatabase.getInstance().getReference(mParam1.getObjectKind())
                    .child(mParam1.getObjectId())
                    .child("rating").child(uid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.d("rate_data", dataSnapshot.toString());
                    HashMap<String, String> value = (HashMap<String, String>) dataSnapshot.getValue();
                    if (value != null)
                    {
                        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.already_rate), Toast.LENGTH_LONG).show();
                        ratingBar.setScrollToSelect(false);
                        linearLayout.setVisibility(View.INVISIBLE);
                    }
                    else
                    {
//                    GiveRate();
                        ratingBar.setScrollToSelect(true);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }
    String uid;

    private void CheckExists()
    {

    }
    private void GiveRate() {

        RatingModel rating  = new RatingModel();
        DatabaseReference ref = null;
        final String comments = editComment.getText().toString();
        if (Constants.object_kind == 0)
           ref = FirebaseDatabase.getInstance().getReference("Restaurant").child(mParam1.getObjectId()).child("rating").child(uid);
        else if (Constants.object_kind == 1)
            ref = FirebaseDatabase.getInstance().getReference("Hotel").child(mParam1.getObjectId()).child("rating").child(uid);
        else if (Constants.object_kind == 2)
            ref = FirebaseDatabase.getInstance().getReference("Parking").child(mParam1.getObjectId()).child("rating").child(uid);

        ref = FirebaseDatabase.getInstance().getReference(mParam1.getObjectKind()).child(mParam1.getObjectId()).child("rating").child(uid);
//        String key = ref.getKey();
        rating.setObjectId(uid);
        rating.setUserId(uid);


        String dateString = "" + Utils.getTodayString(getContext(), 0);
        rating.setComment(comments);
        rating.setScore(ratingBar.getScore() + "");
        rating.setCreateAt(dateString);
        rating.setName(name);
        HashMap<String, String> map = rating.getHashMap();
        ref.setValue(map);
        ref.setValue(map, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null)
                {
                    showWelcomeDialog();
                    if (comments.equals(""))
                    {

                    }
                    else {
                        if (mParam1.getEmail() == null || mParam1.getEmail().equals("")) {
                            sendComments(comments, Constants.default_mail);
                        }
                        else
                        {
                            sendComments(comments, mParam1.getEmail());
                        }
                    }
                }
            }
        });

//        ref.setValue(map);
    }

    private void sendComments(String comments, String email)
    {
        /*
        MandrillMessage allMessage = new MandrillMessage(Constants.MANDRILL_KEY);
        EmailMessage message = new EmailMessage();
        message.setFromEmail("info@soroushjavdan.net");
        message.setFromName("buddy");
        message.setHtml("<p>Crash report</p>");
        message.setText("blah blah blah .... ");
        message.setSubject("error");
        Recipient recipient = new Recipient();
        List<Recipient> recipients = new ArrayList<Recipient>();
        recipient.setEmail("bestsuperdeveloper@gmail.com");
        recipient.setName("Dmitry");
        recipients.add(recipient);
        Attachment attachment = new Attachment();
        List<Attachment> attachments = new ArrayList<Attachment>();

        attachment.setType("audio/mpeg");
        attachment.setName("Image name");
        attachment.setContent("set your Base64 encode of your file");
        attachments.add(attachment);

        message.setTo(recipients);
        message.setAttachments(attachments);
        allMessage.setMessage(message);
        allMessage.send(); */
        String from = user.getEmail();
//        from = "bestsuperdeveloper@gmail.com";
        String to = email;
//        to = "dmitrykhudin120@gmail.com";
        String subject = "Post Comment";
        String text = comments;
        Log.d("result_email", from + to + subject + text);
        new sendEmail().execute(new String[]{from, to, subject, text});
//        sendEmail(from, to, subject, text);
    }

    private void showWelcomeDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.feedback_title);
        builder.setMessage(R.string.welcome_submit_feedback);
        builder.setPositiveButton(R.string.str_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();
    }

    private class sendEmail extends AsyncTask<String, String, String>
    {
        public sendEmail() {

        }

        @Override
        protected String doInBackground(String... params) {
            httpClient = new OkHttpClient();
            String credentials = "api" + ":" + Constants.MAILGUN_API;
            String basic =
                    "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP).replace("\n", "");
            Request.Builder builder = new Request.Builder();

            builder.addHeader("Authorization", basic);
            RequestBody body = new FormEncodingBuilder()
                    .add("from", params[0])
                    .add("to", params[1])
                    .add("subject", params[2])
                    .add("text", params[3])
                     .build();

            Request request = builder.url(Constants.MAILGUN_URL).post(body).build();

            try {
                Response response = httpClient.newCall(request).execute();
                if (!response.isSuccessful())
                {

                    return null;
                }
                else
                {
                    return response.body().toString();
                }
            }
             catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s == null)
            {
                Log.d("mailsend", "null");
            }
            else
            {
//                showWelcomeDialog();
                Log.d("reuslt", s);
            }
        }
    }

    protected void sendEmail(String from, String to, String subject, String contents) {
        Log.i("Send email", "");
//        String[] TO = {""};
//        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
//        emailIntent.putExtra(Intent.FROM)
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
//        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, contents);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send comments..."));
//            finish();
            Log.d("tag", "Finished sending email...");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getContext(), "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private int countWords(String s) {
        String trim = s.trim();
        if (trim.isEmpty())
            return 0;
        return trim.split("\\s+").length; // separate string around spaces
    }

    private InputFilter filter;

    private void setCharLimit(EditText et, int max) {
        filter = new InputFilter.LengthFilter(max);

        et.setFilters(new InputFilter[] { filter });
        Toast.makeText(getActivity(), R.string.text_limit, Toast.LENGTH_LONG).show();
    }

    private void removeFilter(EditText et) {
        if (filter != null) {
            et.setFilters(new InputFilter[0]);
            filter = null;
        }
    }
}
