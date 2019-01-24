package twentyfour_seconds.com.del;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class tabcontrol_main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabcontent);

        //イベント作成画面のタブ管理を実行
        OriginalFragmentPagerAdapter adapter = new OriginalFragmentPagerAdapter(getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        //下部メニューボタンを押下したときの処理を記載
        ImageView menu_bar_home = findViewById(R.id.tab2).findViewById(R.id.menu_bar_home);
        ImageView menu_bar_event = findViewById(R.id.tab2).findViewById(R.id.menu_bar_event);
        ImageView menu_bar_chat = findViewById(R.id.tab2).findViewById(R.id.menu_bar_chat);
        ImageView menu_bar_mypage = findViewById(R.id.tab2).findViewById(R.id.menu_bar_mypage);

        tabcontrol_main.menuClickListener menuClickListener = new tabcontrol_main.menuClickListener();

        menu_bar_home.setOnClickListener(menuClickListener);
        menu_bar_event.setOnClickListener(menuClickListener);
        menu_bar_chat.setOnClickListener(menuClickListener);
        menu_bar_mypage.setOnClickListener(menuClickListener);

    }

    private class menuClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view){
            int id = view.getId();
            switch(id){
                case R.id.menu_bar_home:
                    //home画面へと飛ぶ処理
                    Intent intentHome = new Intent(tabcontrol_main.this, TopActivity.class);
                    startActivity(intentHome);
                    break;
                case R.id.menu_bar_event:
                    //イベント作成画面へと飛ぶ処理
                    Intent intentEvent = new Intent(tabcontrol_main.this, tabcontrol_main.class);
                    startActivity(intentEvent);
                    break;
                case R.id.menu_bar_chat:
                    //チャット画面へと飛ぶ処理
                    Intent intentchat = new Intent(tabcontrol_main.this, ChatDB.class);
                    startActivity(intentchat);
                    break;
                case R.id.menu_bar_mypage:
                    //マイページ画面へと飛ぶ処理
                    Intent intentMypage = new Intent(tabcontrol_main.this, MyPageActivity.class);
                    startActivity(intentMypage);
                    break;
            }
        }
    }
}
