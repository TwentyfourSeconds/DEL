package twentyfour_seconds.com.del.notification;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;

public class MyInstanceIDListenerService extends FirebaseMessagingService {

    private static final String TAG = MyInstanceIDListenerService.class.getSimpleName();

    @Override
    public void onNewToken(String token) {

        Log.d(TAG, "Refreshed token: " + token);

        //ここで取得したInstanceIDをFirebaseに登録する。
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token){
        //FirebaseのUser情報に登録


    }


}
