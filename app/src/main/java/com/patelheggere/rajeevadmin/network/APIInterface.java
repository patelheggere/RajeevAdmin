package com.patelheggere.rajeevadmin.network;

import com.patelheggere.rajeevadmin.model.NotificationReqModel;
import com.patelheggere.rajeevadmin.model.NotificationRespModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface APIInterface {
    String BASE_URL = "https://fcm.googleapis.com/";
    @POST("fcm/send")
    Call<NotificationRespModel> sendNotifications(@Header("Content-Type") String type, @Header("Authorization") String key, @Body NotificationReqModel model);
}
