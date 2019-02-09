package twentyfour_seconds.com.del;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class EventManagementDetailActivity extends AppCompatActivity {

    private Button dissolution;
    private Button recruitment_quit;
    private ListView entry_request;
    private ListView entry_member;
    private List<Map<String, String>> list = new ArrayList<>();
    private String[] from = {"image", "name", "join", "message", "approval", "unapproved"};
    private int[] to = new int[6];
    private SimpleAdapter adapter;
    //処理の分岐を決める変数
    private int id;
    private String title;
    private String deadline;
    //文字列検索時の検索用語
    private String searchWord;
    //top画面より渡されたタグ情報
    private int tag_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruitment_list);

        entry_request = findViewById(R.id.entry_request);
        entry_member = findViewById(R.id.entry_member);

        //latchは1
        final CountDownLatch latch = new CountDownLatch(1);
        // インテントを取得
        Intent intent = getIntent();
        // インテントに保存されたデータから、どの処理を動かすかを判断
        id = intent.getIntExtra("id", 0);
        title = intent.getStringExtra("title");
        deadline = intent.getStringExtra("deadline");

        for(int i = 0; i < Common.titleList.size(); i++) {
//            Log.d("size", ""+Common.titleList.size());
//            Log.d("i", ""+i);
            Map<String, String> menu = new HashMap<>();
            menu.put("id", Common.idList.get(i));
            menu.put("image", Common.imageList.get(i));
            menu.put("name", Common.titleList.get(i));
            menu.put("join", Common.areaList.get(i));
            menu.put("local", Common.localList.get(i));
            menu.put("term", Common.termList.get(i));
            menu.put("deadline", Common.deadlineList.get(i));
            menu.put("member", Common.memberList.get(i));
            list.add(menu);
        }



//        for(int i = 0; i < 10; i++) {
//            Map<String, String> menu = new HashMap<>();
//            menu.put("image", "写真");
//            menu.put("title", "○○の脱出");
//            menu.put("area", "神奈川県");
//            menu.put("local", "赤レンガ");
//            menu.put("term", "男女不問　20代");
//            menu.put("deadline", "12/24");
//            menu.put("member", "1/4");
//            list.add(menu);
//        }

//        to[0] = R.id.image;
//        to[1] = R.id.name;
//        to[2] = R.id.join;
//        to[3] = R.id.message;
//        to[4] = R.id.approval;
//        to[5] = R.id.unapproved;

        adapter = new SimpleAdapter(EventManagementDetailActivity.this, list, R.layout.row, from, to);

        entry_request.setAdapter(adapter);

        //コンテキストメニューをリストビューに登録。
        registerForContextMenu(entry_request);

    }

}
