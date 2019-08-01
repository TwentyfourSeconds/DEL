package twentyfour_seconds.com.del.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import twentyfour_seconds.com.del.util.Common;
import twentyfour_seconds.com.del.util.CustomActivity;
import twentyfour_seconds.com.del.R;
import twentyfour_seconds.com.del.top_page.TopActivity;

public class LoginActivityOld extends CustomActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        //端末情報を取得し、ユーザーidを付与する
        String uniqueID = UUID.randomUUID().toString();
        Log.d("uniqueID", uniqueID);
        final CountDownLatch latch = new CountDownLatch(1);


        User_id_table_read userDBread = new User_id_table_read(uniqueID, latch);
        userDBread.execute();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.d("user_id", "user_id = " + Common.user_id);

        //user_idを読み込んだ時に、レコードがない場合は、新規作成を行う。
        final CountDownLatch latch2 = new CountDownLatch(1);
        int user_id = 0;

        if(Common.user_id == null){
            //user_idがない場合は新たに採番する
            User_id_table_write userDBwrite = new User_id_table_write(uniqueID,user_id,latch);
            userDBwrite.execute();
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else{
        }

        //TopActivityに遷移
        Intent intentTopActivity = new Intent(getApplicationContext(), TopActivity.class);
        startActivity(intentTopActivity);

    }
}