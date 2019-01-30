package twentyfour_seconds.com.del;

import android.content.Intent;
import android.graphics.Point;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

public class TopActivity extends AppCompatActivity {

    @Override
//    //onSavedInstanceState：Activity破棄前にActivityの内部データを保存する
//    Bundleインスタンスっていうのは、画面遷移をする際、次の画面（Activity）に渡したいデータを詰め込んでおく箱みたいなものです。
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

    }

    public class menuClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view){
            int id = view.getId();
            switch(id){
                case R.id.menu_bar_home:
                    //home画面へと飛ぶ処理
                    Intent intentHome = new Intent(TopActivity.this, TopActivity.class);
                    startActivity(intentHome);
                    break;
                case R.id.menu_bar_event:
                    //イベント作成画面へと飛ぶ処理（イベント作成画面はフラグメントなため、ロジックが異なる）
                    // Fragmentを作成します
                    EventCreateflagment fragment = new EventCreateflagment();
                    // Fragmentの追加や削除といった変更を行う際は、Transactionを利用します
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    // 新しく追加を行うのでaddを使用します
                    // 他にも、よく使う操作で、replace removeといったメソッドがあります
                    // メソッドの1つ目の引数は対象のViewGroupのID、2つ目の引数は追加するfragment
                    transaction.add(R.id.ConstraintLayout, fragment);
                    // 最後にcommitを使用することで変更を反映します
                    transaction.commit();
                    break;
                case R.id.menu_bar_chat:
                    //チャット画面へと飛ぶ処理
                    Intent intentchat = new Intent(TopActivity.this, ChatDB.class);
                    startActivity(intentchat);
                    break;
                case R.id.menu_bar_mypage:
                    //マイページ画面へと飛ぶ処理
                    Intent intentMypage = new Intent(TopActivity.this, MyPageActivity.class);
                    startActivity(intentMypage);
                    break;
            }
        }
    }
}


