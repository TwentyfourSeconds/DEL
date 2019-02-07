package twentyfour_seconds.com.del;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.concurrent.CountDownLatch;

public class TopActivity extends CustomActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);

        //横スクロールに入るimageviewの横幅をプログラムより指定
        WindowManager wm = (WindowManager)getSystemService(WINDOW_SERVICE);
        Display disp = wm.getDefaultDisplay();

        //画像3枚の横幅を取得する
        final ImageView img12 = (ImageView) findViewById(R.id.imageView12);
        final ImageView img13 = (ImageView) findViewById(R.id.imageView13);
        final ImageView img14 = (ImageView) findViewById(R.id.imageView14);
        ViewGroup.LayoutParams params12 = img12.getLayoutParams();
        ViewGroup.LayoutParams params13 = img12.getLayoutParams();
        ViewGroup.LayoutParams params14 = img12.getLayoutParams();
        //画面サイズを取得
        Point realSize = new Point();
        disp.getRealSize(realSize);

        int realScreenWidth = realSize.x;
        int realScreenHeight = realSize.y;

        //  横幅のみ画面サイズに変更
        params12.width = realScreenWidth;
        img12.setLayoutParams(params12);
        params13.width = realScreenWidth;
        img13.setLayoutParams(params13);
        params14.width = realScreenWidth;
        img14.setLayoutParams(params14);

        //下部メニューボタンを押下したときの処理を記載
        ImageView menu_bar_home = findViewById(R.id.tab1).findViewById(R.id.menu_bar_home);
        ImageView menu_bar_event = findViewById(R.id.tab1).findViewById(R.id.menu_bar_event);
        ImageView menu_bar_chat = findViewById(R.id.tab1).findViewById(R.id.menu_bar_chat);
        ImageView menu_bar_mypage = findViewById(R.id.tab1).findViewById(R.id.menu_bar_mypage);

        menuClickListener menuClickListener = new menuClickListener();

        menu_bar_home.setOnClickListener(menuClickListener);
        menu_bar_event.setOnClickListener(menuClickListener);
        menu_bar_chat.setOnClickListener(menuClickListener);
        menu_bar_mypage.setOnClickListener(menuClickListener);

        //グループを検索ボタンを押下時、リクルートメントリストに遷移、データベースを読み込み、
        //一覧に候補を出力する
        Button searchbutton = findViewById(R.id.searchbutton);
        View.OnClickListener buttonClick = new buttonClickListener();
        searchbutton.setOnClickListener(buttonClick);

        //ジャンルボタンを押下時、リクルートメントリストに遷移、データベースを読み込み、
        //一覧に候補を出力する（例として、机に座ってガッツリとを押下時の挙動を作成）
        //Tag_mapを読み込み、tag_idを保持するrecruitment_list_idを取得。
        //recruitment_list_idを所有するrecruitment_listを取得
        ImageView hole_type = findViewById(R.id.hole_type);
        View.OnClickListener EventTypeClick = new EventTypeClickListener();
        hole_type.setOnClickListener(EventTypeClick);



    }

    //グループを検索ボタンを押下時の動き
    public class buttonClickListener implements View.OnClickListener{
        public void onClick(View view){
            //home画面へと飛ぶ処理
            TextView searchtext = findViewById(R.id.searchword);
            //検索文字列を取得
            String searchWord = searchtext.getText().toString();
            Intent intentMypage = new Intent(getApplicationContext(), RecruitmentListActivity.class);
            intentMypage.putExtra("searchWord", searchWord);
            //RecruitmentListActivity遷移時、遷移先で処理を分岐する
            // サーチワードから検索する場合、valueは1とする
            int value = 1;
            intentMypage.putExtra("VALUE",value);
            startActivity(intentMypage);
        }
    }

    // //ジャンルボタンを押下時
    public class  EventTypeClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id) {
                case R.id.hole_type:
                    //hole_typeのコード値を１とする
                    int tag_type = 1;
                    Intent intentMypage = new Intent(getApplicationContext(), RecruitmentListActivity.class);
                    intentMypage.putExtra("tag_type", 1);
                    //RecruitmentListActivity遷移時、遷移先で処理を分岐する
                    // tagから検索する場合、valueは2とする
                    int value = 2;
                    intentMypage.putExtra("VALUE",value);
                    startActivity(intentMypage);
                    break;
//                case R.id.hole_type:
//                    break;
//                case R.id.hole_type:
//                    break;
            }
        }
    }








//    public class menuClickListener implements View.OnClickListener{
//        @Override
//        public void onClick(View view){
//            int id = view.getId();
//            switch(id){
//                case R.id.menu_bar_home:
//                    //home画面へと飛ぶ処理
//                    Intent intentHome = new Intent(TopActivity.this, TopActivity.class);
//                    startActivity(intentHome);
//                    break;
//                case R.id.menu_bar_event:
//                    //イベント作成画面へと飛ぶ処理
//                    Intent intentEvent = new Intent(TopActivity.this, tabcontrol_main.class);
//                    startActivity(intentEvent);
//                    break;
//                case R.id.menu_bar_chat:
//                    //チャット画面へと飛ぶ処理
//                    Intent intentchat = new Intent(TopActivity.this, ChatDB.class);
//                    startActivity(intentchat);
//                    break;
//                case R.id.menu_bar_mypage:
//                    //マイページ画面へと飛ぶ処理
//                    Intent intentMypage = new Intent(TopActivity.this, MyPageActivity.class);
//                    startActivity(intentMypage);
//                    break;
//            }
//        }
//    }




}


