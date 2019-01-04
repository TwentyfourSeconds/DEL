package twentyfour_seconds.com.del;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class RecruitmentListActivity extends AppCompatActivity {

    private ListView lsRecruitment;
    private List<Map<String, String>> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        try {
//            Class.forName("twentyfour_seconds.com.del.DetectionDB");
//        } catch (ClassNotFoundException e) {
//        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruitment_list);

        lsRecruitment = findViewById(R.id.lsRecruitment);

//        // インテントを取得
//        Intent intent = getIntent();
//        // インテントに保存されたデータを取得
//        String searchWord = intent.getStringExtra("searchWord");

        final CountDownLatch latch = new CountDownLatch(1);
//        DetectionDB ddb = new DetectionDB(searchWord, latch);
        DetectionDB ddb = new DetectionDB(latch);
        ddb.execute();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        for(int i = 0; i < Common.titleList.size(); i++) {
            Log.d("size", ""+Common.titleList.size());
            Log.d("i", ""+i);
            Map<String, String> menu = new HashMap<>();
            menu.put("id", Common.idList.get(i));
            menu.put("image", Common.imageList.get(i));
            menu.put("title", Common.titleList.get(i));
            menu.put("area", Common.areaList.get(i));
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

        String[] from = {"image", "title", "area", "local", "term", "deadline", "member"};

        int[] to = {R.id.image, R.id.title,R.id.area,R.id.local,R.id.term,R.id.deadline,R.id.member};

        SimpleAdapter adapter = new SimpleAdapter(RecruitmentListActivity.this, list, R.layout.row, from, to);

        lsRecruitment.setAdapter(adapter);

        lsRecruitment.setOnItemClickListener(new ListItemClickListener());

        //コンテキストメニューをリストビューに登録。
        registerForContextMenu(lsRecruitment);

    }


    /**
     * リストがタップされたときの処理が記述されたメンバクラス。
     */
    private class ListItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //タップされた行のデータを取得。
            Map<String, Object> item = (Map<String, Object>) parent.getItemAtPosition(position);

            String idNum = (String)item.get("id");
            Log.d("id", idNum);

            // インテントへのインスタンス生成
            Intent intent = new Intent(RecruitmentListActivity.this, RecruitmentDetailActivity.class);
            //　インテントに値をセット
            intent.putExtra("id", idNum);
            // サブ画面の呼び出し
            startActivity(intent);
        }
    }

}
