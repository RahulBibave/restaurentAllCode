package com.resmenu.firebase;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.util.Log;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.resmenu.R;
import com.resmenu.activity.Kitchen_TableActivity;
import com.resmenu.activity.MainActivity;


import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.resmenu.activity.AllMenuActivity.TAG;

public class MyFirebaseMessagingService extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        sendNotification(this,"New Order Recived","Res Menu");
        Log.e("aaaaaaaaaaaaaaaaa",""+remoteMessage);
    }

    private void sendNotification(Context context, String message, String title) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            NotificationChannel channel;
            if (Build.VERSION.SDK_INT >= 26) {
                channel = new NotificationChannel("default",
                        "Task",
                        NotificationManager.IMPORTANCE_DEFAULT);
                channel.setDescription("Task Channel");
                notificationManager.createNotificationChannel(channel);
            }
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, "default")
                    .setSmallIcon(R.drawable.ic_notifications_active_black)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setVibrate(new long[]{0, 500})
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
            notificationManager.notify(0, notificationBuilder.build());
        }
    }

}