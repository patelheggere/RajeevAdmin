package com.patelheggere.rajeevadmin.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.patelheggere.rajeevadmin.R;
import com.patelheggere.rajeevadmin.RajeevAdminApplication;
import com.patelheggere.rajeevadmin.model.GalleryModel;

import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class GallryFragment extends Fragment {


    private static final String TAG = "GallryFragment";
    private OnFragmentInteractionListener mListener;
    private View mView;
    private Button buttonSelect, buttonUpload, buttonSubmit;
    private TextView textViewURL;
    private EditText mEditTextKannada, mEditTextEnglish, mEditTextEnglishTitle, mEditTextKannadaTitle;
    private Uri filePath;
    private DatabaseReference kannada, english;
    private ImageView imageViewUploaded;

    FirebaseStorage storage;
    StorageReference storageReference;

    private final int PICK_IMAGE_REQUEST = 71;

    public GallryFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static GallryFragment newInstance(String param1, String param2) {
        GallryFragment fragment = new GallryFragment();
        Bundle args = new Bundle();
      /*  args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);*/
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
        mView =  inflater.inflate(R.layout.fragment_gallry, container, false);
        initViews();
        return  mView;
    }

    private void initViews() {

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        kannada = RajeevAdminApplication.getFireBaseRef();
        english = RajeevAdminApplication.getFireBaseRef();
        kannada = kannada.child("gallery").child("ka");
        english = english.child("gallery").child("en");

        mEditTextEnglish = mView.findViewById(R.id.en_et_desc);
        mEditTextKannada = mView.findViewById(R.id.kn_et_desc);
        textViewURL = mView.findViewById(R.id.tv_url);
        buttonSelect = mView.findViewById(R.id.btn_select);
        buttonSubmit = mView.findViewById(R.id.btn_submit);
        buttonUpload = mView.findViewById(R.id.btn_upload);

        mEditTextEnglishTitle = mView.findViewById(R.id.en_et_title);
        mEditTextKannadaTitle = mView.findViewById(R.id.kn_et_title);

        imageViewUploaded = mView.findViewById(R.id.iv_uploaded);


        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });

        buttonSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mEditTextEnglish.getText().toString()==null ||mEditTextEnglish.getText().toString().isEmpty() )
                {
                    return;
                }
                if(textViewURL.getText().toString()==null ||textViewURL.getText().toString().isEmpty() )
                {
                    return;
                }
                if(mEditTextEnglishTitle.getText().toString()==null ||mEditTextEnglishTitle.getText().toString().isEmpty() )
                {
                    return;
                }


                if(mEditTextKannada.getText().toString()==null ||mEditTextKannada.getText().toString().isEmpty() )
                {
                    return;
                }

                if(mEditTextKannadaTitle.getText().toString()==null ||mEditTextKannadaTitle.getText().toString().isEmpty() )
                {
                    return;
                }


                GalleryModel model = new GalleryModel();
                model.setDesc(mEditTextEnglish.getText().toString());
                model.setUrl(textViewURL.getText().toString());
                model.setTitle(mEditTextEnglishTitle.getText().toString());

                GalleryModel model2 = new GalleryModel();
                model2.setUrl(textViewURL.getText().toString());
                model2.setDesc(mEditTextKannada.getText().toString());
                model2.setTitle(mEditTextKannadaTitle.getText().toString());


                kannada.push().setValue(model2);
                english.push().setValue(model);

                mEditTextEnglish.setText("");
                mEditTextEnglishTitle.setText("");
                mEditTextKannada.setText("");
                mEditTextKannadaTitle.setText("");
                textViewURL.setText("");
                textViewURL.setVisibility(View.INVISIBLE);
                imageViewUploaded.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            imageViewUploaded.setImageURI(filePath);
            imageViewUploaded.setVisibility(View.VISIBLE);
        }
    }

    private void uploadImage() {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            final StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            //UploadTask uploadTask =
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Uri downloadUrl = uri;
                                    textViewURL.setText(downloadUrl.toString());
                                    //imageViewUploaded.setVisibility(View.VISIBLE);
                                }
                            });

                            Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                        }

            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });

        }
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
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
