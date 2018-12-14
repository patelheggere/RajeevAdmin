package com.patelheggere.rajeevadmin.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.patelheggere.rajeevadmin.R;
import com.patelheggere.rajeevadmin.model.JobUpdatesModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class JobFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private EditText etDept, etTitle, etQuallification, etNotification, etLastDate, etExamMode, etPaymentType,  etDocuments, etExamDate, etNoofPost, etWebsite;

    private EditText knDept, knTitle, knQuallification, knNotification, knLastDate, knExamMode, knPaymentType,  knDocuments, knExamDate, knNoofPost, knWebsite;

    private View mView;

    private DatabaseReference databaseReference, databaseReference2;

    private int year, month, day;

    private Calendar calendar;

    DatePickerDialog  ExamDate, LastDate, Notif;



    public JobFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static JobFragment newInstance(String param1, String param2) {
        JobFragment fragment = new JobFragment();
        Bundle args = new Bundle();
       /* args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);*/
        fragment.setArguments(args);
        return fragment;
    }

    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(getActivity(),
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    //showDate(arg1, arg2+1, arg3,);
                }
            };

    private void showDate(int year, int month, int day, EditText view) {
        view.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }

    private void showdate()
    {
         calendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                //updateLabel();
            }

        };

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
         /*   mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);*/
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView =  inflater.inflate(R.layout.fragment_job, container, false);
        FirebaseDatabase  firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference2 = firebaseDatabase.getReference().child("ka").child("job");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("en").child("job");
        initEnglishView();
        initkannadaViews();

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        //showDate(year, month+1, day);
        Button btnSubmit = mView.findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                JobUpdatesModel jobUpdatesModel = new JobUpdatesModel();
                jobUpdatesModel.setDept(etDept.getText().toString());
                jobUpdatesModel.setTitle(etTitle.getText().toString());
                jobUpdatesModel.setDocuments(etDocuments.getText().toString());
                jobUpdatesModel.setExamDate(etExamDate.getText().toString());
                jobUpdatesModel.setExamMode(etExamMode.getText().toString());
                jobUpdatesModel.setLastDate(etLastDate.getText().toString());
                jobUpdatesModel.setNoPosts(etNoofPost.getText().toString());
                jobUpdatesModel.setNotiDate(etNotification.getText().toString());
                jobUpdatesModel.setPaymentMode(etPaymentType.getText().toString());
                jobUpdatesModel.setQualification(etQuallification.getText().toString());
                jobUpdatesModel.setWebsite(etWebsite.getText().toString());

                databaseReference.push().setValue(jobUpdatesModel);


                JobUpdatesModel jobUpdatesModel2 = new JobUpdatesModel();
                jobUpdatesModel2.setDept(knDept.getText().toString());
                jobUpdatesModel2.setTitle(knTitle.getText().toString());
                jobUpdatesModel2.setDocuments(knDocuments.getText().toString());
                jobUpdatesModel2.setExamDate(knExamDate.getText().toString());
                jobUpdatesModel2.setExamMode(knExamMode.getText().toString());
                jobUpdatesModel2.setLastDate(knLastDate.getText().toString());
                jobUpdatesModel2.setNoPosts(knNoofPost.getText().toString());
                jobUpdatesModel2.setNotiDate(knNotification.getText().toString());
                jobUpdatesModel2.setPaymentMode(knPaymentType.getText().toString());
                jobUpdatesModel2.setQualification(knQuallification.getText().toString());
                jobUpdatesModel2.setWebsite(knWebsite.getText().toString());

                databaseReference2.push().setValue(jobUpdatesModel2);

                initEnglishView();
                initkannadaViews();


            }
        });

          ExamDate = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                Log.d("", "onDateSet: "+monthOfYear);
                newDate.set(year, monthOfYear, dayOfMonth);
                etExamDate.setText(dateFormat.format(newDate.getTime()));
                knExamDate.setText(dateFormat.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

          Notif = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                etNotification.setText(dateFormat.format(newDate.getTime()));
                knNotification.setText(dateFormat.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


          LastDate = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                etLastDate.setText(dateFormat.format(newDate.getTime()));
                knLastDate.setText(dateFormat.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        etNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        etExamDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        etLastDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return mView;
    }

    private void initkannadaViews() {

        knDept = mView.findViewById(R.id.knet_dept);
        knTitle = mView.findViewById(R.id.knet_title);
        knDocuments = mView.findViewById(R.id.knet_documents);
        knExamDate = mView.findViewById(R.id.knet_exam_date);
        knExamMode = mView.findViewById(R.id.knet_exam_mode);
        knLastDate = mView.findViewById(R.id.knet_last_date);
        knNoofPost = mView.findViewById(R.id.knet_no_po);
        knNotification = mView.findViewById(R.id.knet_noti_date);
        knPaymentType = mView.findViewById(R.id.knet_payment);
        knQuallification = mView.findViewById(R.id.knet_qualification);
        knWebsite = mView.findViewById(R.id.knet_website);
    }



    final Calendar newCalendar = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");



    private void initEnglishView() {
        etDept = mView.findViewById(R.id.en_et_dept);
        etTitle = mView.findViewById(R.id.en_et_title);
        etDocuments = mView.findViewById(R.id.en_et_documents);
        etExamDate = mView.findViewById(R.id.en_et_exam_date);
        etExamMode = mView.findViewById(R.id.en_et_exam_mode);
        etLastDate = mView.findViewById(R.id.en_et_last_date);
        etNoofPost = mView.findViewById(R.id.en_et_no_po);
        etNotification = mView.findViewById(R.id.en_et_noti_date);
        etPaymentType = mView.findViewById(R.id.en_et_payment);
        etQuallification = mView.findViewById(R.id.en_et_qualification);
        etWebsite = mView.findViewById(R.id.en_et_website);



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


