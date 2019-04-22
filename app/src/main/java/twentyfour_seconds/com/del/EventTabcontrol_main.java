package twentyfour_seconds.com.del;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

public class EventTabcontrol_main extends AppCompatActivity {

//    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabcontent);
        //ツールバーを設定する
//        toolbar = findViewById(R.id.toolBar);
//      　setSupportActionBar()：これによってToolbarをActionBarと同じ様に使う事ができる。
//        setSupportActionBar(toolbar);

        //イベントフラグメントページアダプターをインスタンス化
        EventFragmentPagerAdapter adapter = new EventFragmentPagerAdapter(getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(2);
        //アダプターをviewPagerにセット
        viewPager.setAdapter(adapter);
        //タブレイアウトに viewPagerをセット
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        //イベント作成
        ImageView plus = findViewById(R.id.plus);
        View.OnClickListener EventCreateClick = new EventTabcontrol_main.EventCreateClickListener();
        plus.setOnClickListener(EventCreateClick);
    }

    //イベント作成ボタンを押下時
    public class  EventCreateClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intentMypage = new Intent(getApplicationContext(), EventCreate.class);
            startActivity(intentMypage);
            }
        }

//    @Override
//    onCreateMenu():
//    Activity開始時にメニューを設定する為に呼ばれる
//    getMenuInflater().inflate(R.menu.main, menu);
//    これで設置したいメニューを指定する。
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.plus, menu);
//        return true;
//    }


}
