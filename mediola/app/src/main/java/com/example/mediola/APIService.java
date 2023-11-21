package com.example.mediola;



import com.example.mediola.Notifications.MyResponse;
import com.example.mediola.Notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAAUZGe2k:APA91bFEm0WbEiY5yibpoWcgqs9qYDLgkXuSN96bnn3Pmw0dWi1AttDiuj2SvWojsgkRxGIsLMxKvmUedp8g_LK0OINyUtK71C5YqC4bv5Pyllbjbf9aB-Uk0O2xILaC86CfvysSVfQb"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
