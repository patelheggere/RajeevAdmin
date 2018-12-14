package com.patelheggere.rajeevadmin;

import android.app.Application;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RajeevAdminApplication extends Application {
    private static RajeevAdminApplication mInstance;
    private static DatabaseReference databaseReference;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        // ApiClient.intialise();
       /* if(isDeve()) {
            ApiClient.setBaseUrl(AppConstants.appBaseUrlDev);
        }
        else
        {
            ApiClient.setBaseUrl(AppConstants.appBaseUrl);

        }*/

    }

    public static synchronized DatabaseReference getFireBaseRef()
    {
        Log.d("", "getFireBaseRef: ");
        System.out.println("getdef");
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        if(BuildConfig.DEBUG) {
            System.out.println("debug");
            Log.d("", "getFireBaseRef: Debug");
            databaseReference = FirebaseDatabase.getInstance().getReference().child("test");
        }
        else {
            Log.d("", "getFireBaseRef: release");
            databaseReference = FirebaseDatabase.getInstance().getReference().child("prod");
        }
        return databaseReference;
    }

    public static synchronized RajeevAdminApplication getInstance() {
        return mInstance;
    }

}