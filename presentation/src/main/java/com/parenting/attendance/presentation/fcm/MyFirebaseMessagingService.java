package com.parenting.attendance.presentation.fcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.parenting.attendance.R;
import com.parenting.attendance.presentation.view.activity.DashboardActivity;


/**
 * Created by apple on 25/05/16.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private static int count = 0;
    private static OnNotificationArrivedListener notificationListener;

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.

        ///  RemoteMessage.Notification notification = remoteMessage.getNotification();

        Log.e("remoteMessage", "MESSAGE RECEIVED!!");

        //Log.e("remoteMessage", ":" + remoteMessage.getNotification());
        //Log.e("remoteMessage", ":" + remoteMessage.getData());

        // String title = remoteMessage.getNotification().getTitle();
        String title = remoteMessage.getData().get("title");


        if (title == null) {
            title = "";
        }
        // String body = remoteMessage.getNotification().getBody();
        String body = remoteMessage.getData().get("body");

        if (body == null) {
            body = "";
        }

        Log.d(TAG, "Notification Message Body:1 " + remoteMessage.getNotification());
        Log.d(TAG, "Notification Message Body:1 " + remoteMessage.getNotification().getBody());
        Log.d(TAG, "Notification Message Body:2 " + body);


        try {
            if (body == null || body.equals(""))
                body = remoteMessage.getNotification().getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String type = "";
        try {
            type = remoteMessage.getData().get("type");
        } catch (Exception e) {
            e.printStackTrace();
        }

        String typeID = "";
        try {
            typeID = remoteMessage.getData().get("typeID");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String isOffer = "0";
        try {
            if (remoteMessage.getData() != null &&
                    remoteMessage.getData().containsKey("isOffer"))
                isOffer = remoteMessage.getData().get("isOffer");
        } catch (Exception e) {
            e.printStackTrace();
        }
        sendNotification(type, typeID, body, title, isOffer);
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param
     * @param messageBody FCM message body received.
     * @param isOffer
     */
    private void sendNotification(String type, String typeID, String messageBody, String title, String isOffer) {

        Intent intent = new Intent();
        try {
            intent.putExtra("type", type);
            intent.putExtra("typeID", typeID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.putExtra(Constant.isShowHomeScreen, "1");
//        if (isOffer.equalsIgnoreCase("1"))
//            intent.setClass(this, OffersActivity.class);
//        else if ((isOffer.equalsIgnoreCase("2")))
//            intent.setClass(this, OrderActivity.class);
//        else
//            intent.setClass(this, NotificationActivity.class);
        intent.setClass(this, DashboardActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(getNotificationIcon())
                .setColor(ContextCompat.getColor(getApplicationContext(), R.color.primary))
                .setContentTitle(getString(R.string.app_name))
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


      /*  ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> services = activityManager
                .getRunningTasks(Integer.MAX_VALUE);
        boolean isActivityFound = false;*/

       /* if (services.get(0).topActivity.getPackageName()
                .equalsIgnoreCase(getPackageName())) {
            isActivityFound = true;
        }
*/
        /*if (isActivityFound) {
            Log.d("getPackageName", ":" + getPackageName());
            if (notificationListener != null)
                notificationListener.OnNotificationArrived();

        } else {*/
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
        // }
        /*try {
            count = MazkaraPref.getInstance().getInt(getApplicationContext(), MazkaraPref.PreferenceKey.notification_count, 0);
            count++;
            Log.e("count", ":" + count);
            MazkaraPref.getInstance().saveInt(getApplicationContext(), MazkaraPref.PreferenceKey.notification_count, count);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }


    private int getNotificationIcon() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Log.e("LOLLIPOP", ":LOLLIPOP icon");
            return R.drawable.logo;
        } else {
            Log.e("LOLLIPOP", ":No LOLLIPOP icon");
            return R.drawable.logo;
        }

    }


    public static void setNotificationArrivedListener(OnNotificationArrivedListener listener) {
        notificationListener = listener;
    }

    public interface OnNotificationArrivedListener {

        /**
         * Called when an item in the navigation menu is selected.
         *
         * @return true to display the item as the selected item
         */
        public boolean OnNotificationArrived();
    }


}