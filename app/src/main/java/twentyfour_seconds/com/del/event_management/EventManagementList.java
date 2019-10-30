package twentyfour_seconds.com.del.event_management;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import twentyfour_seconds.com.del.DTO.EventInfoDTO;
import twentyfour_seconds.com.del.DTO.EventInfoDTOList;
import twentyfour_seconds.com.del.R;
import twentyfour_seconds.com.del.util.Common;
import twentyfour_seconds.com.del.util.CustomActivity;

public class EventManagementList extends CustomActivity {

    private final String SEND_UID = "member_uid=" + Common.uid;
    private final String PARTICIPANT_EVENT = Common.PARTICIPANT_EVENT_URL;
    private EventInfoDTOList eventInfoDTOList = new EventInfoDTOList();

    //アダプター
    private twentyfour_seconds.com.del.event_management.EventManagementListViewAdapter EventManagementListViewAdapter;
    //アダプターにセットするリスト（Map型でいろいろ格納できるようにしておく）
    //(参考)Map型とは：https://qiita.com/hainet/items/daab47dc991285b1f552
    //(参考)Map型に値を追加する方法：https://stackoverrun.com/ja/q/10712774
    private List<Map<String, Object>> messageList = new ArrayList<Map<String, Object>>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_join);


        //新DB用
        final CountDownLatch latch = new CountDownLatch(1);
        String write = "";
        StringBuilder sb = new StringBuilder();
        sb.append(SEND_UID);
        write = sb.toString();
        //DetailDBを読み込む
        ParticipantEventDAO participantEventDAO = new ParticipantEventDAO(PARTICIPANT_EVENT, write, eventInfoDTOList, latch);
        participantEventDAO.execute();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //DB取得時、データをcommonクラスに格納するため、commonクラスより、データを取得
        for(int i = 0; i < eventInfoDTOList.getDtoArrayList().size(); i++) {

            Map<String, Object> Map = new HashMap<>();

            EventInfoDTO eventInfoDTO = eventInfoDTOList.getDtoArrayList().get(i);
            Map.put("image", "test");
            Map.put("title", eventInfoDTO.getEventName());
            Map.put("area", eventInfoDTO.getLargeArea());
            Map.put("local", eventInfoDTO.getSmallArea());
            Map.put("term", "nothing");

//            Map.put("deadline", eventInfoDTO.getClosedDay());
            Map.put("member", eventInfoDTO.getMember());
            messageList.add(Map);
        }



//        //自分のIDから、現在、作成しているイベントを抽出する。
//        int id = 1;
//        final CountDownLatch latch = new CountDownLatch(1);
//
//        //DetailDBを読み込む
//        event_info_id_search_bk ddb = new event_info_id_search_bk(id, latch);
//        ddb.execute();
//        try {
//            latch.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        Log.d("来た2","来た2");
//        //DB取得時、データをcommonクラスに格納するため、commonクラスより、データを取得
//        for(int i = 0; i < Common.titleList.size(); i++) {
//
//            Map<String, Object> Map = new HashMap<>();
//
//            Map.put("image", Common.imageList.get(i));
//            Map.put("title", Common.titleList.get(i));
//            Map.put("area", Common.areaList.get(i));
//            Map.put("local", Common.localList.get(i));
//            Map.put("term", Common.termList.get(i));
//            Map.put("deadline", Common.deadlineList.get(i));
//            Map.put("member", Common.memberList.get(i));
//            messageList.add(Map);
//        }


        //joinメンバーDBを読み込む。
        //更新があれば、赤丸を出す。

        //処理（firebaseを使う？）

        //出た情報をリサイクラービューにてリスト出力

        // Get the RecyclerView object.
        RecyclerView recyclerView = findViewById(R.id.entry_event);

        //--------------------------------flexBox Layout の調整-----------------------------------------------//
        // FlexboxLayoutManangerを定義する（レイアウトマネージャーは、リストデータの見え方を決める。※これがリストビューとは異なるリサイクラービューのいいところ）
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        // リサイクラービューにレイアウトマネージャーを設定
        recyclerView.setLayoutManager(linearLayoutManager);

        //データをmessageListに格納
//        messageList = this.initViewItemDtoList();

        // アダプターオブジェクトを生成して、下のメソッドで設定した文字列を追加
        EventManagementListViewAdapter = new EventManagementListViewAdapter(messageList);
        // アダプターオブジェクトをセット
        recyclerView.setAdapter(EventManagementListViewAdapter);


        //        //下部メニューボタンを押下したときの処理を記載
        ImageView menu_bar_home = findViewById(R.id.tab3).findViewById(R.id.menu_bar_home);
        ImageView menu_bar_event = findViewById(R.id.tab3).findViewById(R.id.menu_bar_event);
        ImageView menu_bar_chat = findViewById(R.id.tab3).findViewById(R.id.menu_bar_chat);
        ImageView menu_bar_mypage = findViewById(R.id.tab3).findViewById(R.id.menu_bar_mypage);

        menuClickListener menuClickListener = new menuClickListener();

        menu_bar_home.setOnClickListener(menuClickListener);
        menu_bar_event.setOnClickListener(menuClickListener);
        menu_bar_chat.setOnClickListener(menuClickListener);
        menu_bar_mypage.setOnClickListener(menuClickListener);


    }


}


