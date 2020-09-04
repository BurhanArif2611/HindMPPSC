package com.hindmppsc.exam.Firebase;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.hindmppsc.exam.R;
import com.hindmppsc.exam.activity.Live_Classes.ReadCommentActivity;
import com.hindmppsc.exam.activity.LoginActivity;
import com.hindmppsc.exam.activity.NotificationActivity;
import com.hindmppsc.exam.database.UserProfileHelper;
import com.hindmppsc.exam.utility.ErrorMessage;
import com.hindmppsc.exam.utility.SavedData;

import org.json.JSONObject;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private NotificationUtils notificationUtils;
    String title = "";
    JSONObject jsonObject3;

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        SavedData.saveFCM_ID(s);
        Log.e("tokan", "Refreshed token:" + s);
        if (UserProfileHelper.getInstance().getUserProfileModel().size() > 0) {
            //UpdateFCMOnServer(s);
        }
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        /*Log.e(TAG, "From data: " + remoteMessage.getData().get("title"));
        Log.e(TAG, "From data>: " + remoteMessage.getData().get("body"));
        Log.e(TAG, "From data 1: " + remoteMessage.getNotification().toString());
        Log.e(TAG, "From data 2: " + remoteMessage.getNotification().getBody().toString());
        Log.e(TAG, "From data 3: " + remoteMessage.getNotification().getTitle().toString());*/

        try {
            Log.e(TAG, "From data: " + remoteMessage.getData().get("message").toString());
            String Response = remoteMessage.getData().get("message").toString();
            JSONObject jsonObject = new JSONObject(Response);
            title = jsonObject.getString("message");
            ErrorMessage.E("MyFirebaseMessagingService" + title);
            int cart_item = Integer.parseInt(SavedData.getAddToCart_Count()) + 1;
            SavedData.saveAddToCart_Count(String.valueOf(cart_item));
            handleNotification(title);

        } catch (Exception e) {
            e.printStackTrace();
            ErrorMessage.E("MyFirebaseMessagingService" + e.toString());
            try {
                String Response = remoteMessage.getData().get("body").toString();
                if (Response.contains("You Have One New Comment")) {
                    Admin_Comment(Response);
                }
            } catch (Exception e1) {}
        }


    }

    private void Admin_Comment(String title) {

        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(Config.Admin_Comment);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();

        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            final String ANDROID_CHANNEL_ID = "com.hindmppsc.exam.ANDROID";
            Intent intent = new Intent(getApplicationContext(), ReadCommentActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NetworkUtileforOreao mNotificationUtils = new NetworkUtileforOreao(getApplicationContext());
            Notification.Builder nb = new Notification.Builder(getApplicationContext(), ANDROID_CHANNEL_ID).
                    setSmallIcon(R.drawable.ic_launcher).setContentTitle("HIND MPPSC").setContentText(title).setAutoCancel(true).setSound(defaultSoundUri).setContentIntent(pendingIntent);
            mNotificationUtils.getManager().notify(0, nb.build());
        } else {
            Intent intent = new Intent(getApplicationContext(), ReadCommentActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this).
                    setSmallIcon(R.drawable.ic_launcher).setContentTitle("HIND MPPSC").setContentText(title).setAutoCancel(true).setSound(defaultSoundUri).setContentIntent(pendingIntent);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, notificationBuilder.build());
        }


    }


    private void handleNotification(String title) {
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(Config.NewRequest);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();

        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            final String ANDROID_CHANNEL_ID = "com.hindmppsc.exam.ANDROID";
            Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NetworkUtileforOreao mNotificationUtils = new NetworkUtileforOreao(getApplicationContext());
            Notification.Builder nb = new Notification.Builder(getApplicationContext(), ANDROID_CHANNEL_ID).
                    setSmallIcon(R.drawable.ic_launcher).setContentTitle("HIND MPPSC").setContentText(title).setAutoCancel(true).setSound(defaultSoundUri).setContentIntent(pendingIntent);
            mNotificationUtils.getManager().notify(0, nb.build());
        } else {
            Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this).
                    setSmallIcon(R.drawable.ic_launcher).setContentTitle("HIND MPPSC").setContentText(title).setAutoCancel(true).setSound(defaultSoundUri).setContentIntent(pendingIntent);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, notificationBuilder.build());
        }


    }


}

