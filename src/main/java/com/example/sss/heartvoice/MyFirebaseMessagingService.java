package com.example.sss.heartvoice;

/**
 * Created by user on 2017/12/18.
 */

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import ks.common.utils.DLog;
import ks.common.utils.firebase.KWFirebaseMessagingService;

public class MyFirebaseMessagingService extends KWFirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        DLog.d("Firebase - Title: " + remoteMessage.getNotification().getTitle() +
                ", Body: " + remoteMessage.getNotification().getBody());
    }
} 