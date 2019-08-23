package twentyfour_seconds.com.del.trash;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import twentyfour_seconds.com.del.chat.ChatDB;
import twentyfour_seconds.com.del.management_event.EventTabcontrol_main;
import twentyfour_seconds.com.del.R;
import twentyfour_seconds.com.del.mypage.MyPageActivity;
import twentyfour_seconds.com.del.top_page.TopActivity;

public class EventEntryFragment extends Fragment implements View.OnClickListener{

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
    public EventEntryFragment() {
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
//            //インサート用SQL文字列の用意。
//            String sqlInsert = "INSERT INTO entry_event (image, title, name, area, local, date, term, deadline, current_num, sum, comment) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//            //SQL文字列を元にプリペアドステートメントを取得。
//            SQLiteStatement stmt = db.compileStatement(sqlInsert);
//            //変数のバイド。
//            stmt.bindString(1, "imageA");
//            stmt.bindString(2, "○○の脱出");
//            stmt.bindString(3, "name");
//            stmt.bindString(4, "area");
//            stmt.bindString(5, "local");
//            stmt.bindString(6, "date");
//            stmt.bindString(7, "term");
//            stmt.bindString(8, "deadline");
//            stmt.bindLong(9, 1);
//            stmt.bindLong(10, 4);
//            stmt.bindString(11, "comment");
//            //インサートSQLの実行。
//            stmt.executeInsert();

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
                event.put("id", "" + cursor.getInt(0));
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

        entry_event.setOnItemClickListener(new ListItemClickListener());

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

    /**
     * リストがタップされたときの処理が記述されたメンバクラス。
     */
    private class ListItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //タップされた行のデータを取得。
            Map<String, Object> item = (Map<String, Object>) parent.getItemAtPosition(position);

            String idNum = String.valueOf(item.get("id"));
            Log.d("id", idNum);

//            // インテントへのインスタンス生成
//            Intent intent = new Intent(getContext(), RecruitmentDetailActivity.class);
//            //　インテントに値をセット
//            intent.putExtra("id", idNum);
//            // サブ画面の呼び出し
//            startActivity(intent);
        }
    }
}