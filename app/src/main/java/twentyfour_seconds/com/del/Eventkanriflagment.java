package twentyfour_seconds.com.del;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Eventkanriflagment extends Fragment implements View.OnClickListener{

    private List<Integer> id = new ArrayList<>();
    private List<String> image = new ArrayList<>();
    private List<String> title = new ArrayList<>();
    private List<String> name = new ArrayList<>();
    private List<String> area = new ArrayList<>();
    private List<String> local = new ArrayList<>();
    private List<String> date = new ArrayList<>();
    private List<String> term = new ArrayList<>();
    private List<String> deadline = new ArrayList<>();
    private List<Integer> current_num = new ArrayList<>();
    private List<Integer> sum = new ArrayList<>();
    private List<String> comment = new ArrayList<>();

    private ListView entry_event;

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

        entry_event = view.findViewById(R.id.entry_event).findViewById(R.id.entry_event);

        List<Map<String, String>> eventList = new ArrayList<>();
        Map<String, String> event;
        //データベースヘルパーオブジェクトを作成。
        EntryEventDB eeDB = new EntryEventDB(getContext());
        //データベースヘルパーオブジェクトからデータベース接続オブジェクトを取得。
        SQLiteDatabase db = eeDB.getWritableDatabase();
        try {
            //インサート用SQL文字列の用意。
            String sqlInsert = "INSERT INTO entry_event VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            //SQL文字列を元にプリペアドステートメントを取得。
            SQLiteStatement stmt = db.compileStatement(sqlInsert);
            //変数のバイド。
            stmt.bindLong(1, 1);
            stmt.bindString(2, "imageA");
            stmt.bindString(3, "○○の脱出");
            stmt.bindString(4, "name");
            stmt.bindString(5, "area");
            stmt.bindString(6, "local");
            stmt.bindString(7, "date");
            stmt.bindString(8, "term");
            stmt.bindString(9, "deadline");
            stmt.bindLong(10, 1);
            stmt.bindLong(11, 4);
            stmt.bindString(12, "comment");
            //インサートSQLの実行。
            stmt.executeInsert();

            //主キーによる検索SQL文字列の用意。
            String sql = "SELECT * FROM entry_event";
            //SQLの実行。
            Cursor cursor = db.rawQuery(sql, null);
            String result = "";
            while (cursor.moveToNext()) {
                //カラムのインデックス値を元に実際のデータを取得。
                id.add(cursor.getInt(0));
                image.add(cursor.getString(1));
                title.add(cursor.getString(2));
                name.add(cursor.getString(3));
                area.add(cursor.getString(4));
                local.add(cursor.getString(5));
                date.add(cursor.getString(6));
                term.add(cursor.getString(7));
                deadline.add(cursor.getString(8));
                current_num.add(cursor.getInt(9));
                sum.add(cursor.getInt(10));
                comment.add(cursor.getString(11));
                event = new HashMap<>();
                event.put("title", cursor.getString(2));
                event.put("date", cursor.getString(6));
                eventList.add(event);
            }


            //SimpleAdapter第4引数from用データの用意。
            String[] from = {"title", "date"};
            //SimpleAdapter第5引数to用データの用意。
            int[] to = {android.R.id.text1, android.R.id.text2};
            //SimpleAdapterを生成。
            SimpleAdapter adapter = new SimpleAdapter(getContext(), eventList, android.R.layout.simple_list_item_2, from, to);
            //アダプタの登録。
            entry_event.setAdapter(adapter);
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