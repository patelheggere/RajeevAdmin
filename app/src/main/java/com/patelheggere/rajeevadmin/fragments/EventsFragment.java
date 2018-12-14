package com.patelheggere.rajeevadmin.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.patelheggere.rajeevadmin.R;
import com.patelheggere.rajeevadmin.RajeevAdminApplication;
import com.patelheggere.rajeevadmin.model.EventModel;

public class EventsFragment extends Fragment {


    private OnFragmentInteractionListener mListener;
    private View mView;
    private EditText mEditTextTitle, mEditTextDesc, mEditTextDate, mEditTextPlace, mEditTextTitleKannada, mEditTexDateKannada, mEditTextPlaceKannada, mEditTextDescKannada;
    private Button mButtonSubmit;
    private DatabaseReference databaseReferenceEnglish, databaseReferenceKannada;

    public EventsFragment() {
        // Required empty public constructor
    }


    public static EventsFragment newInstance(String param1, String param2) {
        EventsFragment fragment = new EventsFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           /* mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);*/
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView =  inflater.inflate(R.layout.fragment_events, container, false);
        initViews();
        return mView;
    }

    private void initViews() {
        mEditTextDate = mView.findViewById(R.id.et_date);
        mEditTextDesc = mView.findViewById(R.id.et_desc);
        mEditTextPlace = mView.findViewById(R.id.et_place);
        mEditTextTitle = mView.findViewById(R.id.et_title);
        mButtonSubmit = mView.findViewById(R.id.btn_submit);

        mEditTexDateKannada = mView.findViewById(R.id.kn_et_date);
        mEditTextPlaceKannada = mView.findViewById(R.id.kn_et_place);
        mEditTextDescKannada = mView.findViewById(R.id.kn_et_desc);
        mEditTextTitleKannada = mView.findViewById(R.id.kn_et_title);


        mButtonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData();
            }
        });
    }

    private void sendData() {
        String date = mEditTextDate.getText().toString();
        String place = mEditTextPlace.getText().toString();
        String title = mEditTextTitle.getText().toString();
        String desc = mEditTextDesc.getText().toString();
        if(desc!=null ||place!=null || date!=null || title!=null)
        {
            databaseReferenceEnglish = RajeevAdminApplication.getFireBaseRef();
            databaseReferenceKannada = RajeevAdminApplication.getFireBaseRef();
            databaseReferenceEnglish = databaseReferenceEnglish.child("events").child("en");
            databaseReferenceKannada = databaseReferenceKannada.child("events").child("ka");
            EventModel model1 = new EventModel();
            EventModel model2 = new EventModel();

            model1.setDate(date);
            model1.setDesc(desc);
            model1.setPlace(place);
            model1.setTitle(title);

            model2.setTitle(mEditTextTitleKannada.getText().toString());
            model2.setDate(mEditTexDateKannada.getText().toString());
            model2.setPlace(mEditTextPlaceKannada.getText().toString());
            model2.setDesc(mEditTextDescKannada.getText().toString());

            databaseReferenceKannada.push().setValue(model2);
            databaseReferenceEnglish.push().setValue(model1);

            mEditTextTitleKannada.setText("");
            mEditTexDateKannada.setText("");
            mEditTextPlaceKannada.setText("");
            mEditTextDescKannada.setText("");

            mEditTextDate.setText("");
            mEditTextPlace.setText("");
            mEditTextTitle.setText("");
            mEditTextDesc.setText("");



        }
        else {
            //Snackbar.make(android.R.id.content, "Enter all the fields", Snackbar.LENGTH_LONG);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
