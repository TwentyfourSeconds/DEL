package twentyfour_seconds.com.del;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class RecruitmentListActivity extends AppCompatActivity implements AbsListView.OnScrollListener {


//    private Aleph0 adapter1 = new Aleph0();
    private int count = 0;
    private ListView lsRecruitment;
    private List<Map<String, String>> list = new ArrayList<>();
    private String[] from = {"image", "title", "area", "local", "term", "deadline", "member"};
    private int[] to = new int[7];
    private SimpleAdapter adapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruitment_list);

        lsRecruitment = findViewById(R.id.lsRecruitment);
        progressBar = findViewById(R.id.progressBar);

        // インテントを取得
        Intent intent = getIntent();
        // インテントに保存されたデータを取得
        String searchWord = intent.getStringExtra("searchWord");

        final CountDownLatch latch = new CountDownLatch(1);
//        DetectionDB ddb = new DetectionDB(searchWord, latch);
        DetectionDB ddb = new DetectionDB(searchWord, latch);
        ddb.execute();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//DB取得時、データをcommonクラスに格納するため、commonクラスより、データを取得
        for(int i = 0; i < Common.titleList.size(); i++) {
//            Log.d("size", ""+Common.titleList.size());
//            Log.d("i", ""+i);
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


        to[0] = R.id.image;
        to[1] = R.id.title;
        to[2] = R.id.area;
        to[3] = R.id.local;
        to[4] = R.id.term;
        to[5] = R.id.deadline;
        to[6] = R.id.member;

        adapter = new SimpleAdapter(RecruitmentListActivity.this, list, R.layout.row, from, to);

        lsRecruitment.setAdapter(adapter);

        lsRecruitment.setOnItemClickListener(new ListItemClickListener());

        //コンテキストメニューをリストビューに登録。
        registerForContextMenu(lsRecruitment);

        lsRecruitment.setOnScrollListener(this);
//        progressBar.setVisibility(View.INVISIBLE);

    }


    public void onScroll(AbsListView view,
                         int firstVisible, int visibleCount, int totalCount) {

        boolean loadMore = firstVisible + visibleCount >= totalCount;
        boolean countOver = totalCount < Common.total;

        if(loadMore && countOver) {
            Log.d("matusbi", "kitane");
//            progressBar.setVisibility(View.VISIBLE);
            count += visibleCount; // or any other amount

            final CountDownLatch latch = new CountDownLatch(1);
//        DetectionDB ddb = new DetectionDB(searchWord, latch);
            DetectionDB ddb = new DetectionDB(count, latch);
            ddb.execute();
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

            for(int i = 0; i < Common.titleList.size(); i++) {
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
            adapter = new SimpleAdapter(RecruitmentListActivity.this, list, R.layout.row, from, to);
            lsRecruitment.setAdapter(adapter);
//            progressBar.setVisibility(View.INVISIBLE);
//            adapter.notifyDataSetChanged();
        }
    }

    public void onScrollStateChanged(AbsListView v, int s) { }

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
