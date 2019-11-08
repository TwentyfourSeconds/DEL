package twentyfour_seconds.com.del.util;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import twentyfour_seconds.com.del.event_entry.EventEntry;

import twentyfour_seconds.com.del.event_management.EventManagementMaintenance;
import twentyfour_seconds.com.del.event_management.EventManagementList;
import twentyfour_seconds.com.del.mypage.MyPageActivity;
import twentyfour_seconds.com.del.R;
import twentyfour_seconds.com.del.top_page.TopActivity;

public class CustomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public class menuClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view){
            int id = view.getId();
            switch(id){
                case R.id.menu_bar_home:
                    //home画面へと飛ぶ処理
                    Intent intentHome = new Intent(getApplicationContext(), TopActivity.class);
                    startActivity(intentHome);
                    break;
                case R.id.menu_bar_event:
                    //イベント作成画面へと飛ぶ処理
                    Intent intentEvent = new Intent(getApplicationContext(), EventEntry.class);
                    startActivity(intentEvent);
                    break;
                case R.id.menu_bar_chat:
                    //チャット画面へと飛ぶ処理
                    Intent intentchat = new Intent(getApplicationContext(), EventManagementList.class);
                    startActivity(intentchat);
                    break;
                case R.id.menu_bar_mypage:
                    //マイページ画面へと飛ぶ処理
                    Intent intentMypage = new Intent(getApplicationContext(), MyPageActivity.class);
                    intentMypage.putExtra("id", "1");
                    startActivity(intentMypage);
                    break;
            }
        }
    }
}