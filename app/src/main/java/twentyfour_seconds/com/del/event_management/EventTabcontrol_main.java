package twentyfour_seconds.com.del.event_management;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import twentyfour_seconds.com.del.event_create.EventCreate1;
import twentyfour_seconds.com.del.R;

public class EventTabcontrol_main extends AppCompatActivity {

//    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabcontent);

        //toolbarを実装する
        // ツールバーをアクションバーとしてセット
        Toolbar toolbar_activityTop = (Toolbar) findViewById(R.id.toolbar_activityTop);
        toolbar_activityTop.setTitle("");
        setSupportActionBar(toolbar_activityTop);


        //イベントフラグメントページアダプターをインスタンス化
        EventFragmentPagerAdapter adapter = new EventFragmentPagerAdapter(getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(2);
        //アダプターをviewPagerにセット
        viewPager.setAdapter(adapter);
        //タブレイアウトに viewPagerをセット
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

    }

    //イベント作成ボタンを押下時（前までは画像にリスナをセットしていた
    public class  EventCreateClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intentMypage = new Intent(getApplicationContext(), EventCreate1.class);
            startActivity(intentMypage);
            }
        }

    //toolbarに使用するmenuをここでinflateする
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.event_management, menu);
        return true;
    }

    //menuがクリックされた時の挙動を記載
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.createevent:
                Intent intentMypage = new Intent(getApplicationContext(), EventCreate1.class);
                startActivity(intentMypage);
                break;
        }
        return false;
    }

}
