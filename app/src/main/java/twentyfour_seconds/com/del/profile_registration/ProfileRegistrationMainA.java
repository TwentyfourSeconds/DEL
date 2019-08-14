package twentyfour_seconds.com.del.profile_registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import twentyfour_seconds.com.del.R;
import twentyfour_seconds.com.del.mypage.MyPageActivity;
import twentyfour_seconds.com.del.top_page.TopActivity;
import twentyfour_seconds.com.del.util.Common;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;

public class ProfileRegistrationMainA extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profileregistration);



        //編集可能な画面の部品に現在のデータをセット
        EditText nickname_profile = findViewById(R.id.nickname_profile);
        EditText selfIntroduction_profile = findViewById(R.id.selfintroduction_profile);
        EditText age_profile = findViewById(R.id.age_profile);
        EditText area_profile = findViewById(R.id.area_profile);
        TextView gender_profile = findViewById(R.id.gender_profile);

        nickname_profile.setText(Common.username);
        selfIntroduction_profile.setText(Common.profile);
        String ageStr = Common.age + "";
        age_profile.setText(ageStr);
        area_profile.setText(Common.location);
        //性別を設定
        String genderStr;
        if(Common.gender == 0){
            //コード値0は男
            genderStr = "男性";
        }else{
            //コード値1は女
            genderStr = "女性";
        }
        gender_profile.setText(genderStr);

        //セーブボタンを作成

        TextView profileSaveButton = findViewById(R.id.profileSaveButton);

        profileSaveButtonClickListener profileSaveButtonClickListener = new profileSaveButtonClickListener();
        profileSaveButton.setOnClickListener(profileSaveButtonClickListener);
    }


    //グループを検索ボタンを押下時の動き
    public class profileSaveButtonClickListener implements View.OnClickListener{
        public void onClick(View view){

            //名前を取得
            EditText nickname_profile = findViewById(R.id.nickname_profile);
            Common.username = nickname_profile.getText().toString();
            //自己紹介を取得
            EditText selfIntroduction_profile = findViewById(R.id.selfintroduction_profile);
            Common.profile = selfIntroduction_profile.getText().toString();
            //年齢を取得
            EditText age_profile = findViewById(R.id.age_profile);
            Common.age = Integer.valueOf(age_profile.getText().toString());
            //イベントの開催場所を取得
            EditText area_profile = findViewById(R.id.area_profile);
            Common.location = area_profile.getText().toString();
            //性別を取得
            TextView gender_profile = findViewById(R.id.gender_profile);
            String genderStr = gender_profile.getText().toString();

            //処理が完了してから、画面遷移を行い、再度データベースを読み込むので、latchを使用し、同期処理を行う。
            final CountDownLatch latch = new CountDownLatch(1);

            //genderは数字に変換して登録
            if(genderStr.equals("男性")){
                //男はコード値0
                Common.gender = 0;
            }else{
                //女はコード値1
                Common.gender = 1;
            }

            //DBに書き込みに行く
            //ユーザーのuid、ユーザー情報のデータベースリファレンス、引き継いできたユーザーimageを登録する
            String uid = FirebaseAuth.getInstance().getUid();
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/users");
            //firebaseで一部のデータを更新したいときは、Updateを使用する
            Map<String, Object> updateUserData = new HashMap<>();
            updateUserData.put("username", Common.username);
            updateUserData.put("age", Common.age);
            updateUserData.put("gender", Common.gender);
            updateUserData.put("location", Common.location);
            updateUserData.put("profile", Common.profile);

            //データをUpdate
            ref.child(uid).updateChildren(updateUserData).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d("RegionSearchActivity", "Finally we save the user to Firebase Databese");

                    //保存したらば、マイページ画面に帰る
                    Intent intentMypage = new Intent(getApplicationContext(), MyPageActivity.class);
                    startActivity(intentMypage);
                }
            });


        }
    }
}