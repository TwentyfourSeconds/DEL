package twentyfour_seconds.com.del.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import twentyfour_seconds.com.del.R;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";
    private static final int NOTIFICATION_ID = 1;
    private static final String NOTIFICATION_CHANNEL_ID = "my_notification_channel";

    @Override
    //メッセージの受信処理
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_DEFAULT);

            // Configure the notification channel.
            notificationChannel.setDescription("Channel description");
            //プッシュが来た時に、ライトをつけるか
            notificationChannel.enableLights(true);
            //ライトの色
            notificationChannel.setLightColor(Color.RED);
            //バイブレーションの有無
            notificationChannel.enableVibration(true);
            //バイブレーションのパターン
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationManager.createNotificationChannel(notificationChannel);
        }


        NotificationCompat.Builder mBuilder;
        mBuilder = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Title")
                .setContentText(remoteMessage.getNotification().getBody())
                //Notificationをユーザに消されないようにする
//                .setOngoing(false)
                //ユーザーがタップしたときに削除する
                .setAutoCancel(true)
                .setChannelId(NOTIFICATION_CHANNEL_ID);

        notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}