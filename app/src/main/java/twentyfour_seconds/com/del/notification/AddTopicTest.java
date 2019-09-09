package twentyfour_seconds.com.del.notification;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import twentyfour_seconds.com.del.R;
import twentyfour_seconds.com.del.login.LoginActivity;
import twentyfour_seconds.com.del.search_event.RecruitmentListActivity;

public class AddTopicTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topic_test);


        //topic登録処理
        Button topicbutton = findViewById(R.id.topicbutton);

        AddTopicTest.topic_button_buttonClickListener topic_button_buttonClickListener = new AddTopicTest.topic_button_buttonClickListener();
        topicbutton.setOnClickListener(topic_button_buttonClickListener);


        //token登録処理
        Button tokenbutton = findViewById(R.id.tokenbutton);

        AddTopicTest.token_button_buttonClickListener token_button_buttonClickListener = new AddTopicTest.token_button_buttonClickListener();
        tokenbutton.setOnClickListener(token_button_buttonClickListener);

    }


    //topic登録ボタンを押下時
    public class topic_button_buttonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            topicRegistration();

        }
    }


    //token登録を押下時
    public class token_button_buttonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            tokenRegistration();

        }
    }



     private void topicRegistration() {

        //テストでここの画面に来たら、topicを登録する
        FirebaseMessaging.getInstance().subscribeToTopic("chattest")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "登録完了";
                        if (!task.isSuccessful()) {
                            msg = "登録失敗";
                        }
                        Log.d("Registration Topic", msg);
                        Toast.makeText(AddTopicTest.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void tokenRegistration() {

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("token get failed", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        Log.d("token get success", "token is = " + token);
                    }
                });
    }





}