package com.duha.blume.FCM;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;


public class MyFirebaseMessagingService extends FirebaseMessagingService {


    String value, type;
    int count = 0;
    NotificationManager notificationManager;
    String image, productId, title, message;
    String random_id;
    public static int NOTIFICATION_ID = 0;

    // new code


    private final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();
    private final String ANDROID_CHANNEL_ID = "e.abhiandroid.dentalapp2.fcm.ANDROID";
    private final String ANDROID_CHANNEL_NAME = "ANDROID CHANNEL";
    private NotificationManager mManager;



    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage == null)
            return;


        //Calling method to generate notification
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            createChannel();
            handleDataMessage(remoteMessage.getData());
        }else{
            handleDataMessage(remoteMessage.getData());
        }


    }

    private void handleDataMessage(Map<String, String> data) {

        image = data.get("image");
        title = data.get("title");
        message = data.get("message");
        productId = data.get("product_id");
        random_id = data.get("random_id");
        //Log.d("productId", productId);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            sendOreoNotification();
        }else{
            if (NOTIFICATION_ID != Integer.parseInt(random_id)) {
                NOTIFICATION_ID = Integer.parseInt(random_id);
                handleMessage(getApplicationContext());
            }

        }


    }

    @SuppressWarnings("deprecation")
    private void handleMessage(Context mContext) {
        Bitmap remote_picture = null;
        //if message and image url
        if (message != null && image != null) {
            try {
                Log.v("TAG_MESSAGE", "" + message);
                Log.v("TAG_IMAGE", "" + image);

                NotificationCompat.BigPictureStyle notiStyle = new NotificationCompat.BigPictureStyle();
                notiStyle.setSummaryText(message);

                try {
                    remote_picture = BitmapFactory.decodeStream((InputStream) new URL(image).getContent());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                notiStyle.bigPicture(remote_picture);
                notificationManager = (NotificationManager) mContext
                        .getSystemService(Context.NOTIFICATION_SERVICE);
                PendingIntent contentIntent = null;

                Intent gotoIntent = new Intent();
                gotoIntent.putExtra("id", productId);
                gotoIntent.setClassName(mContext, getApplicationContext().getPackageName()+".Activities.SplashScreen");//Start activity when user taps on notification.
                contentIntent = PendingIntent.getActivity(mContext,
                        (int) (Math.random() * 100), gotoIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                        mContext);
                Notification notification = mBuilder.setSmallIcon(android.R.drawable.stat_notify_more).setTicker(title).setWhen(0)
                        .setLargeIcon(((BitmapDrawable) getResources().getDrawable(android.R.drawable.stat_notify_more)).getBitmap())
                        .setAutoCancel(true)
                        .setContentTitle(title)
                        .setPriority(1)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                        .setContentIntent(contentIntent)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setContentText(message)
                        .setStyle(notiStyle).build();


                notification.flags = Notification.FLAG_AUTO_CANCEL;
                count++;
                notificationManager.notify(count, notification);//This will generate seperate notification each time server sends.

            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Send Oreo notification
     */

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void sendOreoNotification() {
        Bitmap remote_picture = null;
        if (message != null && image != null) {
            try {
                NotificationCompat.BigPictureStyle notiStyle = new NotificationCompat.BigPictureStyle();
                notiStyle.setSummaryText(message);

                try {
                    remote_picture = BitmapFactory.decodeStream((InputStream) new URL(image).getContent());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                notiStyle.bigPicture(remote_picture);


                Intent gotoIntent = new Intent();
                gotoIntent.putExtra("id", productId);
                gotoIntent.setClassName(getApplicationContext(), getApplicationContext().getPackageName() + ".Activities.SplashScreen");//Start activity when user taps on notification.

                PendingIntent pendingIntent = PendingIntent.getActivity(this, (int) (Math.random() * 100), gotoIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Notification.Builder notificationBuilder = new Notification.Builder(getApplicationContext(), ANDROID_CHANNEL_ID)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setSmallIcon(android.R.drawable.stat_notify_more)
                        .setAutoCancel(true)
                        .setPriority(Notification.PRIORITY_HIGH)
                        .setContentIntent(pendingIntent)
                        .setStyle(new Notification.BigPictureStyle()
                                .bigPicture(remote_picture)
                                .setBigContentTitle(title))
                        .setSound(defaultSoundUri)
                        ;

                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                notificationManager.notify(102, notificationBuilder.build());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createChannel() {

        // create android channel
        NotificationChannel androidChannel = new NotificationChannel(ANDROID_CHANNEL_ID,
                ANDROID_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        // Sets whether notifications posted to this channel should display notification lights
        androidChannel.enableLights(true);
        // Sets whether notification posted to this channel should vibrate.
        androidChannel.enableVibration(true);
        // Sets the notification light color for notifications posted to this channel
        androidChannel.setLightColor(Color.GREEN);
        // Sets whether notifications posted to this channel appear on the lockscreen or not
        androidChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(androidChannel);
    }


    /**
     * Getting the notification manger for push notification
     * */
    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }
}