package krt.com.cityguide.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import krt.com.cityguide.R;
import krt.com.cityguide.Utils.Utils;

public class ContactUSFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    // TODO: Rename and change types and number of parameters
    public static ContactUSFragment newInstance(String param1, String param2) {
        ContactUSFragment fragment = new ContactUSFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    View mainView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.fragment_contact_u, container, false);
        initView();
        return mainView;
    }

    String email, phone, personname, message;
    EditText editText1, editText2, editText3, editText4;

    private void initView() {

        Button button = (Button) mainView.findViewById(R.id.submitButton);
        editText1 = (EditText) mainView.findViewById(R.id.editName);
        editText2 = (EditText) mainView.findViewById(R.id.editMail);
        editText3 = (EditText) mainView.findViewById(R.id.editPhone);
        editText4 = (EditText) mainView.findViewById(R.id.editMessage);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendContactUS();
            }
        });
    }

    private void SendContactUS() {
        email = editText2.getText().toString();
        phone = editText3.getText().toString();
        personname =  editText1.getText().toString();
        message = editText4.getText().toString();
        if (!Utils.isEmailValid(email))
        {
            editText2.setError(getString(R.string.email_error));return;
        }
        if (!Utils.isValidMobile(phone))
        {
            editText3.setError(getString(R.string.phone_error));return;
        }
        if (personname.equals(""))
        {
            editText1.setError(getString(R.string.name_error));return;
        }
        if (message.equals(""))
        {
            editText1.setError(getString(R.string.message_error));return;
        }

//        Intent send = new Intent(Intent.ACTION_SEND);
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"nick@visitabuga.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, " Contact US");
        i.putExtra(Intent.EXTRA_TEXT   , message);
        try {
            startActivity(Intent.createChooser(i, "Contact US..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
//        startActivity(Intent.createChooser(send, "Contact US"));
    }


}
