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
import twentyfour_seconds.com.del.DTO.EventInfoDTOList;
import twentyfour_seconds.com.del.util.Common;
import twentyfour_seconds.com.del.R;
import twentyfour_seconds.com.del.event_info.RecruitmentDetailActivity;
import twentyfour_seconds.com.del.util.EventListAdapter;

public class RecruitmentListActivity extends AppCompatActivity implements AbsListView.OnScrollListener {


//    private Aleph0 adapter1 = new Aleph0();
    private int count = 0;
    private ListView lsRecruitment;
    private List<Map<String, String>> list = new ArrayList<>();
    private String[] from = {"image", "title", "area", "local", "term", "deadline", "member"};
    private int[] to = new int[7];
    private EventListAdapter adapter;
    private SimpleAdapter bk_adapter;
    private ProgressBar progressBar;
    //処理の分岐を決める変数
    private int value;
    //文字列検索時の検索用語
    private String searchWord;
    private String searchArea;
    private String tagType;
    private final String SEARCH_WORD_SEND = "searchWord=";
    private final String EVENT_TAG_SEND = "&eventTag=";
    private final String LARGE_AREA = "&largeArea=";
    private final String SEND_NUM = "&number=";
    private final String INITIAL_NUMBER = "0";
    private final String COUNT_URL = Common.COUNT_EVENT_URL;
    private final String SEARCH_URL = Common.SEARCH_EVENT_URL;
    private String write;
    //top画面より渡されたタグ情報
    private int tag_type;
    private EventInfoDTOList eventInfoDTOList = new EventInfoDTOList();

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

        //新DB用
        // インテントを取得
        Intent intent = getIntent();

        //エリア設定を取得する
        StringBuilder sbSearchArea = new StringBuilder();
        String regionSetting = Common.regionSetting;
        if(regionSetting == null || regionSetting.length() != Common.REGION_ARY.length) {
            regionSetting = "";
        }

        //エリア検索のsbSearchWordコマンド発行
        String[] regionSettingAry = regionSetting.split("");
        sbSearchArea.append("large_area IN (");
        boolean firstFlg = true;
        for(int i = 1; i < regionSettingAry.length; i++) {
            if(regionSettingAry[i].equals(Common.REGION_FLG_ON) && firstFlg) {
                sbSearchArea.append(" \"");
                sbSearchArea.append(Common.REGION_ARY[i - 1]);
                sbSearchArea.append("\"");
                firstFlg = false;
            } else if(regionSettingAry[i].equals(Common.REGION_FLG_ON)) {
                sbSearchArea.append(", \"");
                sbSearchArea.append(Common.REGION_ARY[i - 1]);
                sbSearchArea.append("\"");
            }
        }
        sbSearchArea.append(")");
//        if(regionSetting != "") {
        if(true) {
//            sbSearchArea.setLength(sbSearchArea.length() - 3);
            sbSearchArea.insert(0, " AND ");
            sbSearchArea.insert(0, LARGE_AREA);
            searchArea = sbSearchArea.toString();
        } else {
            searchArea = LARGE_AREA;
        }


        //サーチワードから検索する
        if(intent.getStringExtra("searchWord") == null || intent.getStringExtra("searchWord") == "") {
//        if(true) {
            searchWord = SEARCH_WORD_SEND + "event_name like \"%\"";
        } else {
            StringBuilder sbSearchWord = new StringBuilder();
            firstFlg = true;
            for (String keyword : intent.getStringExtra("searchWord").replaceAll("　", " ").split(" ")) {
                if(firstFlg) {
                    sbSearchWord.append(" event_name like \"%");
                    sbSearchWord.append(keyword);
                    sbSearchWord.append("%\"");
                    firstFlg = false;
                } else {
                    sbSearchWord.append(" AND event_name like \"%");
                    sbSearchWord.append(keyword);
                    sbSearchWord.append("%\"");
                }
            }
//            sbSearchWord.insert(0, "\"");
            sbSearchWord.insert(0, SEARCH_WORD_SEND);
//            sbSearchWord.append("\"");
            searchWord = sbSearchWord.toString();
        }

        //タグ検索のコマンドを発行
//        tagType = EVENT_TAG_SEND + intent.getIntExtra("tag_type", 1);
        if(intent.getStringExtra("tag_type") == null) {
            tagType = EVENT_TAG_SEND;
        } else {
            String[] tagTypeAry = intent.getStringExtra("tag_type").split("");
            StringBuilder sbStagType = new StringBuilder();
            for(int i = 1; i < tagTypeAry.length; i++) {
                sbStagType.append(" AND event_tag = ");
                sbStagType.append(tagTypeAry[i]);
            }
            sbStagType.insert(0, EVENT_TAG_SEND);
            tagType = sbStagType.toString();
        }


        StringBuilder sb = new StringBuilder();
        sb.append(searchWord);
        sb.append(searchArea);
        sb.append(tagType);
        sb.append(SEND_NUM);
        sb.append(INITIAL_NUMBER);
        write = sb.toString();
        Log.d("write", write);
        final CountDownLatch latch = new CountDownLatch(2);
        EventSearchDAO eventSearchDAO = new EventSearchDAO(SEARCH_URL, write, eventInfoDTOList, latch);
        eventSearchDAO.execute();
        CountEventDAO countEventDAO = new CountEventDAO(COUNT_URL, write, latch);
        countEventDAO.execute();

//        //latchは1
//        final CountDownLatch latch = new CountDownLatch(1);
//        // インテントを取得
//        Intent intent = getIntent();
//        // インテントに保存されたデータから、どの処理を動かすかを判断
//        value = intent.getIntExtra("VALUE", 0);
//        String urlStr = "";
//        StringBuilder sb = new StringBuilder();
//        switch (value) {
//            case 1:
//                //サーチワードから検索する
//                searchWord = intent.getStringExtra("searchWord");
//                urlStr = Common.EVENT_SEARCH_NAME_URL;
//                sb.append("number=0");
//                sb.append("&searchWord=" + searchWord);
//                sb.append("&tag_id=" + tag_type);
//                write = sb.toString();
//
//                break;
//            case 2:
//                //タグから検索する
//                tag_type = intent.getIntExtra("tag_type",1);
//                Log.i("tag_type","" + tag_type);
//                urlStr = Common.EVENT_SEARCH_TAG_URL;
//                sb.append("number=0");
//                sb.append("&searchWord=" + searchWord);
//                sb.append("&tag_id=" + tag_type);
//                write = sb.toString();
//                break;
//        }
//        EventSearchDAO eventSearchDAO = new EventSearchDAO(urlStr, write, eventInfoDTOList, latch);
//        eventSearchDAO.execute();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//データベースの件数が7件以上ある場合、下の配列のIndex数を超えるため、10以上あったらば、6に置き換える
//        int Indexlength;
//        if(Common.titleList.size() > 7){
//            Indexlength = 7;
//        }else{
//            Indexlength = Common.titleList.size();
//        }

//DB取得時、データをcommonクラスに格納するため、commonクラスより、データを取得
//        for(int i = 0; i < Indexlength; i++) {
////            Log.d("size", ""+Common.titleList.size());
////            Log.d("i", ""+i);
//            Map<String, String> menu = new HashMap<>();
////            Log.d("i", i + "");
////            Log.d("word", Common.idList.get(i) + "");
//            menu.put("id", eventInfoDTOList.getDtoArrayList().get(i).getId());
//            menu.put("image", eventInfoDTOList.getDtoArrayList().get(i).getImage());
//            menu.put("title", eventInfoDTOList.getDtoArrayList().get(i).getTitle());
//            menu.put("area", Common.areaList.get(i));
//            menu.put("local", Common.localList.get(i));
//            menu.put("term", Common.termList.get(i));
//            menu.put("deadline", Common.deadlineList.get(i));
//            menu.put("member", Common.memberList.get(i));
//            list.add(menu);
//        }

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


//
//        adapter = new SimpleAdapter(RecruitmentListActivity.this, list, R.layout.row, from, to);
//
//        lsRecruitment.setAdapter(adapter);

        adapter = new EventListAdapter(RecruitmentListActivity.this);
        adapter.setEventList(eventInfoDTOList);
        lsRecruitment.setAdapter(adapter);

        lsRecruitment.setOnItemClickListener(new ListItemClickListener());

        //コンテキストメニューをリストビューに登録。
        registerForContextMenu(lsRecruitment);
        lsRecruitment.setOnScrollListener(this);

//        if(eventInfoDTOList.getDtoArrayList().size() == 0) {
//            Map<String, String> menu = new HashMap<>();
//            menu.put("image", "なし");
//            menu.put("title", "なし");
//            menu.put("area", "なし");
//            menu.put("local", "なし");
//            menu.put("term", "なし");
//            menu.put("deadline", "なし");
//            menu.put("member", "なし");
//            list.add(menu);
//
//            to[0] = R.id.image;
//            to[1] = R.id.title;
//            to[2] = R.id.area;
//            to[3] = R.id.local;
//            to[4] = R.id.term;
//            to[5] = R.id.deadline;
//            to[6] = R.id.member;
//            bk_adapter = new SimpleAdapter(RecruitmentListActivity.this, list, R.layout.row, from, to);
//
//            lsRecruitment.setAdapter(bk_adapter);
//
//        } else {
//
//            adapter = new EventListAdapter(RecruitmentListActivity.this);
//            adapter.setEventList(eventInfoDTOList);
//            lsRecruitment.setAdapter(adapter);
//
//            lsRecruitment.setOnItemClickListener(new ListItemClickListener());
//
//            //コンテキストメニューをリストビューに登録。
//            registerForContextMenu(lsRecruitment);
//            lsRecruitment.setOnScrollListener(this);
//
//        }
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


            //新DB用
            StringBuilder sb = new StringBuilder();
            sb.append(searchWord);
            sb.append(LARGE_AREA);
            sb.append(tagType);
            sb.append(SEND_NUM + count);
            write = sb.toString();
            final CountDownLatch latch = new CountDownLatch(1);
            EventSearchDAO eventSearchDAO = new EventSearchDAO(COUNT_URL, write, eventInfoDTOList, latch);
            eventSearchDAO.execute();



//            final CountDownLatch latch = new CountDownLatch(1);
//            String write = "";
//            String urlStr = "";
//            StringBuilder sb = new StringBuilder();
//            switch (value) {
//                case 1:
//                    //サーチワードから検索する
//                    urlStr = Common.EVENT_SEARCH_NAME_URL;
//                    sb.append("number=" + count);
//                    sb.append("&searchWord=" + searchWord);
//                    write = sb.toString();
//
//                    break;
//                case 2:
//                    //タグから検索する
//                    Log.i("tag_type","" + tag_type);
//                    urlStr = Common.EVENT_SEARCH_TAG_URL;
//                    sb.append("tag_id=" + tag_type);
//                    sb.append("&number=" + count);
//                    write = sb.toString();
//                    break;
//            }
//            EventSearchDAO eventSearchDAO = new EventSearchDAO(urlStr, write, eventInfoDTOList, latch);
//            eventSearchDAO.execute();
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

//            for(int i = 0; i < Common.titleList.size(); i++) {
//                Map<String, String> menu = new HashMap<>();
//                menu.put("id", Common.idList.get(i));
//                menu.put("image", Common.imageList.get(i));
//                menu.put("title", Common.titleList.get(i));
//                menu.put("area", Common.areaList.get(i));
//                menu.put("local", Common.localList.get(i));
//                menu.put("term", Common.termList.get(i));
//                menu.put("deadline", Common.deadlineList.get(i));
//                menu.put("member", Common.memberList.get(i));
//                list.add(menu);
//            }
//            bk_adapter = new SimpleAdapter(RecruitmentListActivity.this, list, R.layout.row, from, to);
//            lsRecruitment.setAdapter(bk_adapter);
//            progressBar.setVisibility(View.INVISIBLE);
//            adapter.notifyDataSetChanged();
            adapter = new EventListAdapter(RecruitmentListActivity.this);
            adapter.setEventList(eventInfoDTOList);
            lsRecruitment.setAdapter(adapter);

            lsRecruitment.setOnItemClickListener(new ListItemClickListener());

            //コンテキストメニューをリストビューに登録。
            registerForContextMenu(lsRecruitment);

            lsRecruitment.setOnScrollListener(this);
        }
    }

    public void onScrollStateChanged(AbsListView v, int s) { }

    /**
     * リストがタップされたときの処理が記述されたメンバクラス。
     */
    private class ListItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //タップされた行のデータを取得。
            String idNum = ((EventInfoDTO)parent.getItemAtPosition(position)).getEventId();

//            String idNum = (String)item.get("id");
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
            case R.id.region_search_setting:
                Intent intent = new Intent(getApplicationContext(), RegionSearchActivity.class);
                startActivity(intent);
                break;
        }
        return false;
    }
}
