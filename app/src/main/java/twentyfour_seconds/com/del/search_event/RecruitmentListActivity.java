package twentyfour_seconds.com.del.search_event;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import twentyfour_seconds.com.del.DTO.EventInfoDTO;
import twentyfour_seconds.com.del.util.Common;
import twentyfour_seconds.com.del.R;
import twentyfour_seconds.com.del.event_info.RecruitmentDetailActivity;

public class RecruitmentListActivity extends AppCompatActivity implements AbsListView.OnScrollListener {


//    private Aleph0 adapter1 = new Aleph0();
    private int count = 0;
    private ListView lsRecruitment;
    private List<Map<String, String>> list = new ArrayList<>();
    private String[] from = {"image", "title", "area", "local", "term", "deadline", "member"};
    private int[] to = new int[7];
    private SimpleAdapter adapter;
    private ProgressBar progressBar;
    //処理の分岐を決める変数
    private int value;
    //文字列検索時の検索用語
    private String searchWord;
    //top画面より渡されたタグ情報
    private int tag_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruitment_list);

        //toolbarを実装する
        // ツールバーをアクションバーとしてセット
        Toolbar toolbar_activityTop = (Toolbar) findViewById(R.id.toolbar_activityTop);
        toolbar_activityTop.setTitle("");
        setSupportActionBar(toolbar_activityTop);


        lsRecruitment = findViewById(R.id.lsRecruitment);
        progressBar = findViewById(R.id.progressBar);

        //latchは1
        final CountDownLatch latch = new CountDownLatch(1);
        // インテントを取得
        Intent intent = getIntent();
        // インテントに保存されたデータから、どの処理を動かすかを判断
        value = intent.getIntExtra("VALUE", 0);
        switch (value) {
            case 1:
                //サーチワードから検索する
                searchWord = intent.getStringExtra("searchWord");
                event_info_event_name_search ddb = new event_info_event_name_search(searchWord, latch);
                ddb.execute();
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                //タグから検索する
                tag_type = intent.getIntExtra("tag_type",0);
                Log.i("tag_type","" + tag_type);
                TagMapDB TagMapDB = new TagMapDB(tag_type, latch);
                TagMapDB.execute();
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
        }
//データベースの件数が7件以上ある場合、下の配列のIndex数を超えるため、10以上あったらば、6に置き換える
        int Indexlength;
        if(Common.titleList.size() > 7){
            Indexlength = 7;
        }else{
            Indexlength = Common.titleList.size();
        }

//DB取得時、データをcommonクラスに格納するため、commonクラスより、データを取得
        for(int i = 0; i < Indexlength; i++) {
//            Log.d("size", ""+Common.titleList.size());
//            Log.d("i", ""+i);
            Map<String, String> menu = new HashMap<>();
            Log.d("i", i + "");
            Log.d("word", Common.idList.get(i) + "");
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

        //firstVisibleItem : 画面上での一番上のリストの番号
        //visibleItemCount : 画面内に表示されているリストの個数（画面に隠れているリストはカウントしない）
        //totalItemCount : ListViewが持つリストの総数


        Log.i("firstVisible", firstVisible + "");
        Log.i("visibleCount", visibleCount + "");
        Log.i("totalCount"  , totalCount + "");

        boolean loadMore = firstVisible + visibleCount >= totalCount;
//      totalカウントを最初にDBから読み込むのではなく、前回取得したデータベースの個数が
//      7件以下（最終レコードまで到達）であれば、スクロール時のデータベース読み込みを行わない。
//        boolean countOver = totalCount < Common.total;
        boolean countOver = 7 <= Common.currentRecordsetLength;

        if(loadMore && countOver) {
            Log.d("matusbi", "kitane");
//            progressBar.setVisibility(View.VISIBLE);
            count += visibleCount; // or any other amount
            final CountDownLatch latch = new CountDownLatch(1);
            switch (value) {
                case 1:
                    //サーチワードから検索する
                    event_info_event_name_search ddb = new event_info_event_name_search(count,searchWord, latch);
                    ddb.execute();
                    try {
                        latch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    //タグから検索する
                    TagMapDB TagMapDB = new TagMapDB(count,tag_type, latch);
                    TagMapDB.execute();
                    try {
                        latch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
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


    //toolbarに使用するmenuをここでinflateする
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_recruitment_list, menu);
        return true;
    }

    //menuがクリックされた時の挙動を記載
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:

                break;
        }
        return false;
    }
}
