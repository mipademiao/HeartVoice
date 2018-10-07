package com.example.sss.heartvoice;

/**
 * Created by user on 2017/12/18.
 */

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import ks.common.utils.DLog;
public class MyInstanceIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();
        DLog.d("Firebase - Token: " + token);
    }
}