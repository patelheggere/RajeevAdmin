package com.patelheggere.rajeevadmin.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.patelheggere.rajeevadmin.R;
import com.patelheggere.rajeevadmin.RajeevAdminApplication;
import com.patelheggere.rajeevadmin.model.NewsModel;
import com.patelheggere.rajeevadmin.model.NewsModel2;
import com.patelheggere.rajeevadmin.model.NotificationReqModel;
import com.patelheggere.rajeevadmin.model.NotificationRespModel;
import com.patelheggere.rajeevadmin.model.UserDetails;
import com.patelheggere.rajeevadmin.network.APIClient;
import com.patelheggere.rajeevadmin.network.APIInterface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class NewsFragment extends Fragment {


    private static final String TAG = "NewsFragment";
    private OnFragmentInteractionListener mListener;
    private View mView;
    private EditText editTextTitleKannada, editTextTitleEnglish;
    private Button buttonSubmit, buttonUpload, buttonSelect;
    private DatabaseReference databaseReferenceKannada, databaseReferenceEnglish;
    private boolean isEnUpdated, isKnUpdated;
    NewsModel model, model2;
    String msg1, msg2;
    int count;
    private EditText mEditTextTitle, mEditTextMessage;
    private EditText mEditTextEnglishDesc, mEditTextKannadaDesc, mEditTextURL;
    private ImageView imageViewUploaded;
    private CheckBox mCheckBoxNotification;
    private LinearLayout linearLayoutNotification;
    private DatabaseReference databaseReferenceUsers;

    private APIInterface apiInterface;
    private String[]token;
    private int countNo;
    private ArrayList<String> listTokens;
    private final int PICK_IMAGE_REQUEST = 71;
    FirebaseStorage storage;
    StorageReference storageReference;
    private DatabaseReference kannada, english;
    private Uri filePath;
    private NewsModel2 newsModelKannada;
    private NewsModel2 newsModelEnglish;
    private String mPhotoURL;



    public NewsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static NewsFragment newInstance(String param1, String param2) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        /*args.putString(ARG_PARAM1, param1);
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
        mView = inflater.inflate(R.layout.fragment_news, container, false);
        editTextTitleEnglish = mView.findViewById(R.id.en_et_news);
        editTextTitleKannada = mView.findViewById(R.id.kn_et_news);
        mEditTextKannadaDesc = mView.findViewById(R.id.kn_et_news_desc);
        mEditTextEnglishDesc = mView.findViewById(R.id.en_et_news_desc);

        mEditTextMessage = mView.findViewById(R.id.et_message);
        mEditTextTitle = mView.findViewById(R.id.en_et_subject);
        mEditTextURL = mView.findViewById(R.id.et_url);

        buttonSubmit = mView.findViewById(R.id.btn_submit);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        mCheckBoxNotification = mView.findViewById(R.id.checkBoxNotification);


        linearLayoutNotification = mView.findViewById(R.id.linearLayoutNotification);
        imageViewUploaded = mView.findViewById(R.id.iv_uploaded);
        buttonSubmit = mView.findViewById(R.id.btn_submit);
        buttonUpload = mView.findViewById(R.id.btn_upload);
        buttonSelect = mView.findViewById(R.id.btn_select);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        getUsersToken();

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
                isEnUpdated = false;
                isKnUpdated = false;
                sendData();
                if(mCheckBoxNotification.isChecked())
                {
                    sendNotification();
                }
            }
        });

        if(mCheckBoxNotification.isChecked())
        {
            linearLayoutNotification.setVisibility(View.VISIBLE);
        }
        else {
            linearLayoutNotification.setVisibility(View.GONE);
        }

        mCheckBoxNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    linearLayoutNotification.setVisibility(View.VISIBLE);
                }
                else {
                    linearLayoutNotification.setVisibility(View.GONE);
                }
            }
        });

        return mView;
    }

    private void getUsersToken() {
        databaseReferenceUsers = RajeevAdminApplication.getFireBaseRef().child("users");
        databaseReferenceUsers.keepSynced(true);
        databaseReferenceUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                countNo =(int)dataSnapshot.getChildrenCount();
           token = new String[(int)dataSnapshot.getChildrenCount()];
           listTokens = new ArrayList<>();
                for (DataSnapshot snapshot: dataSnapshot.getChildren())
                {
                   for(DataSnapshot snap: snapshot.getChildren())
                   {
                       Log.d(TAG, "onDataChange: "+snap.getValue(UserDetails.class).getToken());
                       listTokens.add(snap.getValue(UserDetails.class).getToken());
                   }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sendNotification()
    {
        if(mEditTextMessage.getText().toString().isEmpty() || mEditTextTitle.getText().toString().isEmpty())
        {
            Toast.makeText(getContext(),"Message and Title should not be empty", Toast.LENGTH_LONG);
            return;
        }

        NotificationReqModel notificationReqModel = new NotificationReqModel();
        notificationReqModel.setCollapse_key("type_a");
        String tokens[]=listTokens.toArray(new String[listTokens.size()]);
        notificationReqModel.setRegistration_ids(tokens);
        NotificationReqModel.Notification ob = new NotificationReqModel.Notification();
        ob.setBody(mEditTextMessage.getText().toString());
        ob.setTitle(mEditTextTitle.getText().toString());
        notificationReqModel.setNotification(ob);

        NotificationReqModel.Data ob2 = new NotificationReqModel.Data();
        ob2.setBody(mEditTextMessage.getText().toString());
        ob2.setTitle(mEditTextTitle.getText().toString());
        notificationReqModel.setData(ob2);
        mEditTextTitle.setText("");
        mEditTextMessage.setText("");

    String type = "application/json";
    String toke = "key=AIzaSyDGWkW7s5rCOtNQaFd7ZpEegx3ngBnFUGU";

       Call<NotificationRespModel> apiInterfaceCall = apiInterface.sendNotifications(type, toke,notificationReqModel);

        apiInterfaceCall.enqueue(new Callback<NotificationRespModel>() {
            @Override
            public void onResponse(Call<NotificationRespModel> call, Response<NotificationRespModel> response) {

                Log.d(TAG, "onResponse: "+response.isSuccessful());
                Log.d(TAG, "onResponse: "+response.message());
               // Log.d(TAG, "onResponse: "+response.body().getMulticast_id());
            }

            @Override
            public void onFailure(Call<NotificationRespModel> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getLocalizedMessage());
            }
        });

    }
    private void getData() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String strDate= formatter.format(date);

        Log.d(TAG, "getData: "+databaseReferenceEnglish.toString());
        databaseReferenceEnglish.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                model = new NewsModel();
                model = dataSnapshot.getValue(NewsModel.class);

                if(model!=null) {
                    count = model.getCount();
                    msg1 = model.getMessage();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: english");
            }
        });

        databaseReferenceKannada.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                model2 = new NewsModel();
                model2 = dataSnapshot.getValue(NewsModel.class);
                if(model2!=null)
                msg2 = model2.getMessage();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: kananda");
            }
        });

    }

    private void sendData() {

        databaseReferenceEnglish = RajeevAdminApplication.getFireBaseRef();
        databaseReferenceKannada = RajeevAdminApplication.getFireBaseRef();
        databaseReferenceEnglish = databaseReferenceEnglish.child("news").child("en");
        databaseReferenceKannada = databaseReferenceKannada.child("news").child("ka");

        newsModelEnglish = new NewsModel2();
        newsModelKannada = new NewsModel2();

        /*if(!editTextTitleEnglish.getText().toString().isEmpty())
        {
            newsModelEnglish.setTitle(editTextTitleEnglish.getText().toString());
        }
        else {
            editTextTitleEnglish.setError("Please English enter Title");
            return;
        }*/
        if(!editTextTitleKannada.getText().toString().isEmpty())
        {
            newsModelKannada.setTitle(editTextTitleKannada.getText().toString());
        }else {
            editTextTitleKannada.setError(getString(R.string.kan_title_error));
         return;
        }
        if(!mEditTextKannadaDesc.getText().toString().isEmpty())
        {
            newsModelKannada.setDescription(mEditTextKannadaDesc.getText().toString());
        }else {
            mEditTextKannadaDesc.setError(getString(R.string.kan_desc_error));
        }
        if(!mEditTextURL.getText().toString().isEmpty())
        {
            newsModelKannada.setLinkURL(mEditTextURL.getText().toString());
        }
        if(mPhotoURL!=null || !mPhotoURL.isEmpty())
        {
            newsModelKannada.setImageURL(mPhotoURL);
            newsModelEnglish.setImageURL(mPhotoURL);
        }
        newsModelKannada.setProfileURL("https://firebasestorage.googleapis.com/v0/b/rajeev-8a4b5.appspot.com/o/download.jpg?alt=media&token=466083a5-f4f8-40d5-b683-80ef6f318344");
        newsModelEnglish.setProfileURL("https://firebasestorage.googleapis.com/v0/b/rajeev-8a4b5.appspot.com/o/download.jpg?alt=media&token=466083a5-f4f8-40d5-b683-80ef6f318344");

        newsModelEnglish.setDateTime(System.currentTimeMillis());
        newsModelKannada.setDateTime(System.currentTimeMillis());

      String key = databaseReferenceEnglish.push().getKey();
      newsModelEnglish.setNewsKey(key);
      newsModelKannada.setNewsKey(key);
      databaseReferenceEnglish.child(key).setValue(newsModelEnglish);
      databaseReferenceKannada.child(key).setValue(newsModelKannada);

    }

    private void uploadImage() {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            final StorageReference ref = storageReference.child("images/news/"+ UUID.randomUUID().toString());
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
                                    mPhotoURL = downloadUrl.toString();
                                   // textViewURL.setText(downloadUrl.toString());
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
