package twentyfour_seconds.com.del.mypage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.CountDownLatch;

import twentyfour_seconds.com.del.R;
import twentyfour_seconds.com.del.profile_registration.profileRegistrationMain;
import twentyfour_seconds.com.del.util.Common;
import twentyfour_seconds.com.del.util.CustomActivity;

public class MyPageActivityOld extends CustomActivity {

    private ImageView iconProfile;
    private TextView topNameProfile;
    private TextView selfIntroductionProfile;
    private TextView nameProfile;
    private TextView genderProfile;
    private TextView areaProfile;
    private TextView ageProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        iconProfile = findViewById(R.id.icon);
        topNameProfile = findViewById(R.id.userName);
        selfIntroductionProfile = findViewById(R.id.selfIntroduction);
        nameProfile = findViewById(R.id.name);
        genderProfile = findViewById(R.id.gender);
        areaProfile = findViewById(R.id.location);
        ageProfile = findViewById(R.id.age_profile);

        // インテントを取得
        Intent intent = getIntent();

        // インテントに保存されたデータを取得
        int id = Integer.valueOf(intent.getStringExtra("id"));

        Log.d("マイページ　id" ,id + "");

        final CountDownLatch latch = new CountDownLatch(1);
        PersonDBRead pdb = new PersonDBRead(id, latch);
        pdb.execute();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        topNameProfile.setText(Common.personName);
        selfIntroductionProfile.setText(Common.personSelfIntroduction);
        if(Common.personGender == 0) {
            genderProfile.setText("男");
        } else {
            genderProfile.setText("女");
        }
        areaProfile.setText(Common.personLocation);
        ageProfile.setText("" + Common.personAge);

        //プロフィール編集画面に遷移する

        Button profileEdit = findViewById(R.id.profileEditButton);
        View.OnClickListener buttonClick = new MyPageActivityOld.profileClickListener();
        profileEdit.setOnClickListener(buttonClick);


        //下部メニューボタンを押下したときの処理を記載
        ImageView menu_bar_home = findViewById(R.id.mypage_tab).findViewById(R.id.menu_bar_home);
        ImageView menu_bar_event = findViewById(R.id.mypage_tab).findViewById(R.id.menu_bar_event);
        ImageView menu_bar_chat = findViewById(R.id.mypage_tab).findViewById(R.id.menu_bar_chat);
        ImageView menu_bar_mypage = findViewById(R.id.mypage_tab).findViewById(R.id.menu_bar_mypage);

        MyPageActivityOld.menuClickListener menuClickListener = new MyPageActivityOld.menuClickListener();

        menu_bar_home.setOnClickListener(menuClickListener);
        menu_bar_event.setOnClickListener(menuClickListener);
        menu_bar_chat.setOnClickListener(menuClickListener);
        menu_bar_mypage.setOnClickListener(menuClickListener);

    }

    //グループを検索ボタンを押下時の動き
    public class profileClickListener implements View.OnClickListener{
        public void onClick(View view){
            Intent intent_profileRegistrationMain = new Intent(getApplicationContext(), profileRegistrationMain.class);
            String topNameString = topNameProfile.getText().toString();
            String selfIntroductionString = selfIntroductionProfile.getText().toString();
            //性別は男か女を変換しているため、Commonの値を持ってくる
            String genderString = Common.personGender + "";
            String areaString = areaProfile.getText().toString();
            //ageはテキストに出すために文字列
            String ageString = ageProfile.getText().toString();

            intent_profileRegistrationMain.putExtra("topName",topNameString);
            intent_profileRegistrationMain.putExtra("selfIntroduction",selfIntroductionString);
            intent_profileRegistrationMain.putExtra("gender",genderString);
            intent_profileRegistrationMain.putExtra("area",areaString);
            intent_profileRegistrationMain.putExtra("ageString",ageString);

            startActivity(intent_profileRegistrationMain);
        }
    }
}
