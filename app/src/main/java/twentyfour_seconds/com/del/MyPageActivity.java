package twentyfour_seconds.com.del;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MyPageActivity extends AppCompatActivity {

    private ImageView icon;
    private TextView topName;
    private TextView selfIntroduction;
    private TextView name;
    private TextView gender;
    private TextView location;
    private TextView age;
    private TextView entryTrust;
    private TextView planTrust;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        icon = findViewById(R.id.icon);
        topName = findViewById(R.id.topName);
        selfIntroduction = findViewById(R.id.selfIntroduction);
        name = findViewById(R.id.name);
        gender = findViewById(R.id.gender);
        location = findViewById(R.id.location);
        age = findViewById(R.id.age);
        entryTrust = findViewById(R.id.entryTrust);
        planTrust = findViewById(R.id.planTrust);


//        icon.setImageResource(R.drawable.);
        topName.setText("takuma");
        name.setText("takuma");
        selfIntroduction.setText("自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介");
        gender.setText("男");
        location.setText("東京");
        age.setText("29才");
        entryTrust.setText("50");
        planTrust.setText("50");


        //下部メニューボタンを押下したときの処理を記載
        ImageView menu_bar_home = findViewById(R.id.mypage_tab).findViewById(R.id.menu_bar_home);
        ImageView menu_bar_event = findViewById(R.id.mypage_tab).findViewById(R.id.menu_bar_event);
        ImageView menu_bar_chat = findViewById(R.id.mypage_tab).findViewById(R.id.menu_bar_chat);
        ImageView menu_bar_mypage = findViewById(R.id.mypage_tab).findViewById(R.id.menu_bar_mypage);

        MyPageActivity.menuClickListener menuClickListener = new MyPageActivity.menuClickListener();

        menu_bar_home.setOnClickListener(menuClickListener);
        menu_bar_event.setOnClickListener(menuClickListener);
        menu_bar_chat.setOnClickListener(menuClickListener);
        menu_bar_mypage.setOnClickListener(menuClickListener);

    }

    public class menuClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view){
            int id = view.getId();
            switch(id){
                case R.id.menu_bar_home:
                    //home画面へと飛ぶ処理
                    Intent intentHome = new Intent(MyPageActivity.this, TopActivity.class);
                    startActivity(intentHome);
                    break;
                case R.id.menu_bar_event:
                    //イベント作成画面へと飛ぶ処理
                    Intent intentEvent = new Intent(MyPageActivity.this, EventTabcontrol_main.class);
                    startActivity(intentEvent);
                    break;
                case R.id.menu_bar_chat:
                    //チャット画面へと飛ぶ処理
                    Intent intentchat = new Intent(MyPageActivity.this, ChatDB.class);
                    startActivity(intentchat);
                    break;
                case R.id.menu_bar_mypage:
                    //マイページ画面へと飛ぶ処理
                    Intent intentMypage = new Intent(MyPageActivity.this, MyPageActivity.class);
                    startActivity(intentMypage);
                    break;
            }
        }



//        Intent intent = getIntent();
//        int iconId = getResources().getIdentifier(intent.getStringExtra("icon"), "drawable", getPackageName());
//        icon.setImageResource(iconId);
//        topName.setText(intent.getStringExtra("name"));
//        name.setText(intent.getStringExtra("name"));
//        selfIntroduction.setText(intent.getStringExtra("selfIntroduction"));
//        gender.setText(intent.getStringExtra("gender"));
//        location.setText(intent.getStringExtra("location"));
//        age.setText(intent.getStringExtra("age") + "才");
//        entryTrust.setText(intent.getStringExtra("entryTrust"));
//        planTrust.setText(intent.getStringExtra("planTrust"));
    }
}
