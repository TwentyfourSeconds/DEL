//package twentyfour_seconds.com.del.trash;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.CountDownLatch;
//
//import twentyfour_seconds.com.del.DTO.EventInfoDTO;
//import twentyfour_seconds.com.del.DTO.EventInfoDTOList;
//import twentyfour_seconds.com.del.R;
//import twentyfour_seconds.com.del.chat.ChatDB;
////import twentyfour_seconds.com.del.entry_event.ParticipantEventDAO;
//import twentyfour_seconds.com.del.mypage.MyPageActivity;
//import twentyfour_seconds.com.del.top_page.TopActivity;
//import twentyfour_seconds.com.del.util.Common;
//
//public class EventEntryFragment2 extends Fragment implements View.OnClickListener {
//
//    private final String SEND_UID = "member_uid=" + Common.uid;
//    private final String PARTICIPANT_EVENT = Common.PARTICIPANT_EVENT_URL;
//    private EventInfoDTOList eventInfoDTOList = new EventInfoDTOList();
//
//    //アダプター
//    private twentyfour_seconds.com.del.event_management.EventManagementViewAdapter EventEntryViewAdapter;
//    //アダプターにセットするリスト（Map型でいろいろ格納できるようにしておく）
//    //(参考)Map型とは：https://qiita.com/hainet/items/daab47dc991285b1f552
//    //(参考)Map型に値を追加する方法：https://stackoverrun.com/ja/q/10712774
//    private List<Map<String, Object>> messageList = new ArrayList<Map<String, Object>>();
//
//    //コンストラクタ
//    public EventEntryFragment2() {
//    }
//
//    @Nullable
//    @Override
//    // Fragmentで表示するViewを作成するメソッド
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        // 先ほどのレイアウトをここでViewとして作成します
//        return inflater.inflate(R.layout.event_join, container, false);
//    }
//
//    @Override
//    // Viewが生成し終わった時に呼ばれるメソッド
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        //新DB用
//        final CountDownLatch latch = new CountDownLatch(1);
//        String write = "";
//        StringBuilder sb = new StringBuilder();
//        sb.append(SEND_UID);
//        write = sb.toString();
//        //DetailDBを読み込む
////        ParticipantEventDAO participantEventDAO = new ParticipantEventDAO(PARTICIPANT_EVENT, write, eventInfoDTOList, latch);
////        participantEventDAO.execute();
//        try {
//            latch.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        //DB取得時、データをcommonクラスに格納するため、commonクラスより、データを取得
//        for(int i = 0; i < eventInfoDTOList.getDtoArrayList().size(); i++) {
//
//            Map<String, Object> Map = new HashMap<>();
//
//            EventInfoDTO eventInfoDTO = eventInfoDTOList.getDtoArrayList().get(i);
//            Map.put("image", "test");
//            Map.put("title", eventInfoDTO.getEventName());
//            Map.put("area", eventInfoDTO.getLargeArea());
//            Map.put("local", eventInfoDTO.getSmallArea());
//            Map.put("term", "nothing");
//            Map.put("deadline", eventInfoDTO.getClosedDay());
//            Map.put("member", eventInfoDTO.getMember());
//            messageList.add(Map);
//        }
//
//
//
////        //自分のIDから、現在、作成しているイベントを抽出する。
////        int id = 1;
////        final CountDownLatch latch = new CountDownLatch(1);
////
////        //DetailDBを読み込む
////        event_info_id_search_bk ddb = new event_info_id_search_bk(id, latch);
////        ddb.execute();
////        try {
////            latch.await();
////        } catch (InterruptedException e) {
////            e.printStackTrace();
////        }
////
////        Log.d("来た2","来た2");
////        //DB取得時、データをcommonクラスに格納するため、commonクラスより、データを取得
////        for(int i = 0; i < Common.titleList.size(); i++) {
////
////            Map<String, Object> Map = new HashMap<>();
////
////            Map.put("image", Common.imageList.get(i));
////            Map.put("title", Common.titleList.get(i));
////            Map.put("area", Common.areaList.get(i));
////            Map.put("local", Common.localList.get(i));
////            Map.put("term", Common.termList.get(i));
////            Map.put("deadline", Common.deadlineList.get(i));
////            Map.put("member", Common.memberList.get(i));
////            messageList.add(Map);
////        }
//
//
//        //joinメンバーDBを読み込む。
//        //更新があれば、赤丸を出す。
//
//        //処理（firebaseを使う？）
//
//        //出た情報をリサイクラービューにてリスト出力
//
//        // Get the RecyclerView object.
//        RecyclerView recyclerView = view.findViewById(R.id.entry_event);
//
//        //--------------------------------flexBox Layout の調整-----------------------------------------------//
//        // FlexboxLayoutManangerを定義する（レイアウトマネージャーは、リストデータの見え方を決める。※これがリストビューとは異なるリサイクラービューのいいところ）
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        // リサイクラービューにレイアウトマネージャーを設定
//        recyclerView.setLayoutManager(linearLayoutManager);
//
//        //データをmessageListに格納
////        messageList = this.initViewItemDtoList();
//
//        // アダプターオブジェクトを生成して、下のメソッドで設定した文字列を追加
//        EventEntryViewAdapter = new EventEntryViewAdapter(messageList);
//        // アダプターオブジェクトをセット
//        recyclerView.setAdapter(EventEntryViewAdapter);
//
//
//        //        //下部メニューボタンを押下したときの処理を記載
//        ImageView menu_bar_home = view.findViewById(R.id.tab3).findViewById(R.id.menu_bar_home);
//        ImageView menu_bar_event = view.findViewById(R.id.tab3).findViewById(R.id.menu_bar_event);
//        ImageView menu_bar_chat = view.findViewById(R.id.tab3).findViewById(R.id.menu_bar_chat);
//        ImageView menu_bar_mypage = view.findViewById(R.id.tab3).findViewById(R.id.menu_bar_mypage);
//
//        menu_bar_home.setOnClickListener(this);
//        menu_bar_event.setOnClickListener(this);
//        menu_bar_chat.setOnClickListener(this);
//        menu_bar_mypage.setOnClickListener(this);
//
//
//    }
//
//
//    @Override
//    public void onClick(View view) {
//        int id = view.getId();
//        switch(id){
//            case R.id.menu_bar_home:
//                //home画面へと飛ぶ処理
//                Intent intentHome = new Intent(getActivity(), TopActivity.class);
//                startActivity(intentHome);
//                break;
//            case R.id.menu_bar_event:
//                //イベント作成画面へと飛ぶ処理
//                Intent intentEvent = new Intent(getActivity(), EventTabcontrol_main.class);
//                startActivity(intentEvent);
//                break;
//            case R.id.menu_bar_chat:
//                //チャット画面へと飛ぶ処理
//                Intent intentchat = new Intent(getActivity(), ChatDB.class);
//                startActivity(intentchat);
//                break;
//            case R.id.menu_bar_mypage:
//                //マイページ画面へと飛ぶ処理
//                Intent intentMypage = new Intent(getActivity(), MyPageActivity.class);
//                startActivity(intentMypage);
//                break;
//        }
//    }
//}