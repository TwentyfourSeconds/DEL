package twentyfour_seconds.com.del.profile_registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.CountDownLatch;

import twentyfour_seconds.com.del.R;
import twentyfour_seconds.com.del.mypage.MyPageActivity;

public class profileRegistrationMainOld  extends AppCompatActivity {


    String topName;
    String selfIntroduction;
    String gender;
    String area;
    String ageString;
    int id = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profileregistration);

        //引き継いできた情報をリストに渡す。
        Receivedata();

        //編集可能な画面の部品に現在のデータをセット
        EditText nickname_profile = findViewById(R.id.nickname_profile);
        EditText selfIntroduction_profile = findViewById(R.id.selfintroduction_profile);
        EditText age_profile = findViewById(R.id.age_profile);
        EditText area_profile = findViewById(R.id.area_profile);
        TextView gender_profile = findViewById(R.id.gender_profile);

        nickname_profile.setText(topName);
        selfIntroduction_profile.setText(selfIntroduction);
        age_profile.setText(ageString);
        area_profile.setText(area);
        gender_profile.setText(gender);

        //セーブボタンを作成

        TextView profileSaveButton = findViewById(R.id.profileSaveButton);

        profileSaveButtonClickListener profileSaveButtonClickListener = new profileSaveButtonClickListener();
        profileSaveButton.setOnClickListener(profileSaveButtonClickListener);
    }

    //引き継いできた情報をリストに渡す。
    private void Receivedata(){
        Intent intent = getIntent();
        this.topName = intent.getStringExtra("topName");
        this.selfIntroduction = intent.getStringExtra("selfIntroduction");
        this.gender = intent.getStringExtra("gender");
        this.area = intent.getStringExtra("area");
        this.ageString = intent.getStringExtra("ageString");
    }

    //グループを検索ボタンを押下時の動き
    public class profileSaveButtonClickListener implements View.OnClickListener{
        public void onClick(View view){

            //名前を取得
            EditText nickname_profile = findViewById(R.id.nickname_profile);
            topName = nickname_profile.getText().toString();
            //自己紹介を取得
            EditText selfIntroduction_profile = findViewById(R.id.selfintroduction_profile);
            selfIntroduction = selfIntroduction_profile.getText().toString();
            //年齢を取得
            EditText age_profile = findViewById(R.id.age_profile);
            ageString = age_profile.getText().toString();
            //イベントの開催場所を取得
            EditText area_profile = findViewById(R.id.area_profile);
            area = area_profile.getText().toString();
            //性別を取得
            TextView gender_profile = findViewById(R.id.gender_profile);
            gender = gender_profile.getText().toString();

            //処理が完了してから、画面遷移を行い、再度データベースを読み込むので、latchを使用し、同期処理を行う。
            final CountDownLatch latch = new CountDownLatch(1);

            //ageは数字に変換して登録
            int age = Integer.valueOf(ageString);
            //DBに書き込みに行く
            PersonDBWrite PersonDBWrite = new PersonDBWrite(id,topName,selfIntroduction,area,age,gender,latch);
            Log.d("来た1" ,"来た1");
            PersonDBWrite.execute();
            try {
                //処理が完了するまで待機
                Log.d("来た2" ,"来た2");
                latch.await();
                Log.d("来た3" ,"来た3");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //ラッチで順番待ちをしないといけない。
            Log.d("来た4" ,"来た4");
            //保存したらば、マイページ画面に帰る
            Intent intentMypage = new Intent(getApplicationContext(), MyPageActivity.class);
            intentMypage.putExtra("id", "1");
            startActivity(intentMypage);
        }
    }
}