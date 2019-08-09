package twentyfour_seconds.com.del.top_page;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import twentyfour_seconds.com.del.chat.UserDTO;
import twentyfour_seconds.com.del.create_user.RegisterActivity;
import twentyfour_seconds.com.del.util.Common;
import twentyfour_seconds.com.del.util.CustomActivity;
import twentyfour_seconds.com.del.R;
import twentyfour_seconds.com.del.search_event.RecruitmentListActivity;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;

public class TopActivity extends CustomActivity {

    UserDTO currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);

        //最初にログインしているかを確認する
        verifyUserIsLoggedIn();

        //toolbarを実装する
        // ツールバーをアクションバーとしてセット
        Toolbar toolbar_activityTop = (Toolbar) findViewById(R.id.toolbar_activityTop);
        toolbar_activityTop.setTitle("");
        setSupportActionBar(toolbar_activityTop);

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

    //toolbarに使用するmenuをここでinflateする
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activitytop, menu);
        return true;
    }

    //menuがクリックされた時の挙動を記載
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logoff:
                FirebaseAuth.getInstance().signOut();
                //ログオフした場合は、登録画面に戻る
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                //この一文を記載することで、元のログイン画面に戻れないようにする
                intent.addFlags(FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
        }
        return false;
    }

    //最初にログインしているかを確認する
    private void verifyUserIsLoggedIn() {
        String uid = FirebaseAuth.getInstance().getUid();
        if (uid == null) {
            //ログインしていない場合は、登録画面に戻る
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            //この一文を記載することで、元のログイン画面に戻れないようにする
            intent.addFlags(FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else{
            //ユーザー情報をコモンクラスに格納する。（以降はここの処理を使用）
            //firebaseより、イベントidでデータを取得する
            fetchCurrentUser();
            Log.d("TopActivity", "Now Login uid is " + uid);
        }
    }

    //現在のログイン者を取得
    private void fetchCurrentUser(){

        String uid = FirebaseAuth.getInstance().getUid();
        Log.d("fetchCurrentUser", " uid = " + uid);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("/users/" + uid);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentUser = dataSnapshot.getValue(UserDTO.class);
                Log.d("currentUser", " currentUser = " + currentUser);
                //コモンクラスに登録
                Common.uid = currentUser.getUid();
                Log.d("Common.uid","a" + Common.uid);
                Common.username = currentUser.getUsername();
                Common.age = currentUser.getAge();
                Common.gender = currentUser.getGender();
                Common.profile = currentUser.getProfile();
                Common.profileImageUrl = currentUser.getProfileImageUrl();
                Common.regionsetting = currentUser.getRegionSetting();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
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

}


