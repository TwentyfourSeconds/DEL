package twentyfour_seconds.com.del;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Eventkanriflagment extends Fragment implements View.OnClickListener{

    private List<String> resultList = new ArrayList<>();
    private List<String> result = new ArrayList<>();

    //コンストラクタ
    public Eventkanriflagment() {
    }

    @Nullable
    @Override
    // Fragmentで表示するViewを作成するメソッド
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 先ほどのレイアウトをここでViewとして作成します
        return inflater.inflate(R.layout.eventkanri, container, false);
    }

    @Override
    // Viewが生成し終わった時に呼ばれるメソッド
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //データベースヘルパーオブジェクトを作成。
        EntryEventDB eeDB = new EntryEventDB(getContext());
        //データベースヘルパーオブジェクトからデータベース接続オブジェクトを取得。
        SQLiteDatabase db = eeDB.getWritableDatabase();
        try {
            //主キーによる検索SQL文字列の用意。
            String sql = "SELECT * FROM entry_event";
            //SQLの実行。
            Cursor cursor = db.rawQuery(sql, null);
            String result = "";
            while (cursor.moveToNext()) {
                //カラムのインデックス値を取得。
                int idxNote = cursor.getColumnIndex("result");
                //カラムのインデックス値を元に実際のデータを取得。
                result = cursor.getString(idxNote);
            }
            //感想のEditTextの各画面部品を取得しデータベースの値を反映。
//            EditText etNote = findViewById(R.id.etNote);
//            etNote.setText(result);
        }
        finally {
            //データベース接続オブジェクトの解放。
            db.close();
        }

        //        //下部メニューボタンを押下したときの処理を記載
        ImageView menu_bar_home = view.findViewById(R.id.tab3).findViewById(R.id.menu_bar_home);
        ImageView menu_bar_event = view.findViewById(R.id.tab3).findViewById(R.id.menu_bar_event);
        ImageView menu_bar_chat = view.findViewById(R.id.tab3).findViewById(R.id.menu_bar_chat);
        ImageView menu_bar_mypage = view.findViewById(R.id.tab3).findViewById(R.id.menu_bar_mypage);

        menu_bar_home.setOnClickListener(this);
        menu_bar_event.setOnClickListener(this);
        menu_bar_chat.setOnClickListener(this);
        menu_bar_mypage.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch(id){
            case R.id.menu_bar_home:
                //home画面へと飛ぶ処理
                Intent intentHome = new Intent(getActivity(), TopActivity.class);
                startActivity(intentHome);
                break;
            case R.id.menu_bar_event:
                //イベント作成画面へと飛ぶ処理
                Intent intentEvent = new Intent(getActivity(), EventTabcontrol_main.class);
                startActivity(intentEvent);
                break;
            case R.id.menu_bar_chat:
                //チャット画面へと飛ぶ処理
                Intent intentchat = new Intent(getActivity(), ChatDB.class);
                startActivity(intentchat);
                break;
            case R.id.menu_bar_mypage:
                //マイページ画面へと飛ぶ処理
                Intent intentMypage = new Intent(getActivity(), MyPageActivity.class);
                startActivity(intentMypage);
                break;
        }
    }

}