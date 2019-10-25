package twentyfour_seconds.com.del.management_event;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import twentyfour_seconds.com.del.DTO.EventInfoDTO;
import twentyfour_seconds.com.del.DTO.EventInfoDTOList;
import twentyfour_seconds.com.del.R;
import twentyfour_seconds.com.del.chat.ChatActivity;
import twentyfour_seconds.com.del.chat.UserDTO;
import twentyfour_seconds.com.del.create_user.RegisterActivity;
import twentyfour_seconds.com.del.search_event.RecruitmentListActivity;
import twentyfour_seconds.com.del.util.Common;
import twentyfour_seconds.com.del.util.CustomActivity;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;

public class EventManagement extends CustomActivity {

    private final String SEND_UID = "eventer_uid=" + Common.uid;
    private final String UID_SEARCH_URL = Common.UID_SEARCH_EVENT_URL;
    private EventInfoDTOList eventInfoDTOList = new EventInfoDTOList();

    //アダプターにセットするリスト（Map型でいろいろ格納できるようにしておく）
    //(参考)Map型とは：https://qiita.com/hainet/items/daab47dc991285b1f552
    //(参考)Map型に値を追加する方法：https://stackoverrun.com/ja/q/10712774
    private List<Map<String, Object>> EventManagementList = new ArrayList<Map<String, Object>>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventkanri);

        //新DB用
        final CountDownLatch latch = new CountDownLatch(1);
        String write = "";
        StringBuilder sb = new StringBuilder();
        sb.append(SEND_UID);
        write = sb.toString();
        //DetailDBを読み込む
        UidSearchEventDAO ddb = new UidSearchEventDAO(UID_SEARCH_URL, write, eventInfoDTOList, latch);
        ddb.execute();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for(int i = 0; i < eventInfoDTOList.getDtoArrayList().size(); i++) {

            Map<String, Object> Map = new HashMap<>();

            EventInfoDTO eventInfoDTO = eventInfoDTOList.getDtoArrayList().get(i);
            //チャット画面に遷移する際、イベントを識別するため
            Map.put("event_id",eventInfoDTO.getEventId());
            //一覧に出す情報を取得
            Map.put("image", "test");
            Map.put("title", eventInfoDTO.getEventName());
            Map.put("area", eventInfoDTO.getLargeArea());
            Map.put("local", eventInfoDTO.getSmallArea());
            Map.put("term", "nothing");
            Map.put("deadline", eventInfoDTO.getClosedDay());
            Map.put("member", eventInfoDTO.getMember());
            EventManagementList.add(Map);
        }


//        //暫定的にidを設定。本来はFirebaseより取得
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
//            EventManagementList.add(Map);
//        }

        // Get the RecyclerView object.
        RecyclerView recyclerView = findViewById(R.id.ManagementEventList);

        //--------------------------------flexBox Layout の調整-----------------------------------------------//
        // FlexboxLayoutManangerを定義する（レイアウトマネージャーは、リストデータの見え方を決める。※これがリストビューとは異なるリサイクラービューのいいところ）
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        // リサイクラービューにレイアウトマネージャーを設定
        recyclerView.setLayoutManager(linearLayoutManager);

        //データをmessageListに格納
//        messageList = this.initViewItemDtoList();

        // アダプターオブジェクトを生成して、下のメソッドで設定した文字列を追加
        EventManagementViewAdapter eventManagementAdapter = new EventManagementViewAdapter(EventManagementList);
        // アダプターオブジェクトをセット
        recyclerView.setAdapter(eventManagementAdapter);


        //下部メニューボタンを押下したときの処理を記載
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

//    //toolbarに使用するmenuをここでinflateする
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_activitytop, menu);
//        return true;
//    }
//
//    //menuがクリックされた時の挙動を記載
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.logoff:
//                FirebaseAuth.getInstance().signOut();
//                //ログオフした場合は、登録画面に戻る
//                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
//                //この一文を記載することで、元のログイン画面に戻れないようにする
//                intent.addFlags(FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//                break;
//        }
//        return false;
//    }

    //イベント管理画面のAdapterを作成する。
    class EventManagementViewAdapter extends RecyclerView.Adapter<EventManagementViewHolder> {
        //ここでは、作成したビューホルダクラスを指定する

        List<Map<String, Object>> EventManagementList;

        //コンストラクタに引き継いできた値を設定
        public EventManagementViewAdapter(List<Map<String, Object>> EventManagementList) {
            this.EventManagementList = EventManagementList;
        }

        //ステップ①　最初に呼ばれるメソッド
        //レイアウトXMLを元に、ビューホルダを生成する処理を行い、生成したViewHolderを
        //RecyclerViewに返す。（戻り値：RecyclerListViewHolder）
        @Override
        //-----------------------レイアウト　部品　作成箇所---------------------------------//
        public EventManagementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            ////レイアウトxmlから、Viewオブジェクトを作成
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.row_event_management, parent, false);    //activity_flexbox_layout_recycler_view_item（XMLレイアウト）をinflateし、一行分の画面部品とする。

            //row_event_management_backgroung（XMLレイアウト）の内部部品であるcardViewを取得
            final CardView cardView = itemView.findViewById(R.id.cardView);
            //ビューホルダーオブジェクトを生成（一番下で変数をreturnすることで、ViewHolderに処理が飛ぶ）
            final EventManagementViewHolder EventManagementViewHolderRet = new EventManagementViewHolder(itemView);
            //タッチイベント
            //リストの内容をクリックしたら、チャット画面に遷移する
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //タッチしたイベントのpositionを取得
                    int position = EventManagementViewHolderRet.getAdapterPosition(); // positionを取得
                    Log.i("position", position + "");

                    //**イベントのイベントidを取得する*
                    //タッチしたポジションに対応するEventManagementList（DBの情報をすべて取得したLIST型の変数）より取得する
                    //*

                    //渡された引数positionに該当する、リストからリストデータ一行分のMapデータを取得
                    Map<String, Object> singleRow = EventManagementList.get(position);
                    String eventId = singleRow.get("event_id").toString();

                    //チャット画面に遷移する（intentでイベントidを遷移）      ログインしていない場合は、登録画面に戻る?
                    //Flagmentの画面遷移はgetActivity()
                    Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                    intent.putExtra("event_id", eventId);

                    startActivity(intent);
                }
            });

            return EventManagementViewHolderRet;
        }

        //ステップ②　onCreateViewHolderの次に呼ばれるメソッド
        //ここでデータの紐づけを行う。
        //-----------------------データ　設定　作成箇所---------------------------------//
        @Override
        public void onBindViewHolder(EventManagementViewHolder holder, int position) {

            //渡された引数positionに該当する、リストからリストデータ一行分のMapデータを取得
            Map<String, Object> message = EventManagementList.get(position);
            //一行分の部品(activity_flexbox_layout_recycler_view_item)のテキスト部品(flex_box_recycler_view_text_itemをtextItemとして、上で定義）

//        holder.textItem.setText(message.get("image").toString());
            holder.event_name.setText(message.get("title").toString());
            holder.area.setText(message.get("area").toString());
            holder.place.setText(message.get("local").toString());
            holder.event_day.setText(message.get("term").toString());
            holder.deadline.setText(message.get("deadline").toString());
//        holder.textItem.setText(message.get("member").toString());

        }

        @Override
        //③ リストデータ中の件数をリターン。 (layout managerから呼ばれる)
        public int getItemCount() {
            int ret = 0;
            if(this.EventManagementList!=null)
            {
                ret = EventManagementList.size();
            }
            return ret;
        }
    }

    //イベント管理画面のViewHolderを作成する。
    class EventManagementViewHolder extends RecyclerView.ViewHolder {

        //row_event_managementの一行分の画面定義
        public TextView event_name;
        public TextView area;
        public TextView place;
        public TextView event_day;
        public TextView inviteTag;
        public TextView deadline;

        //コンストラクタ
        public EventManagementViewHolder(View EventManagementItemView) {
            //親クラスのコンストラクタの呼び出し
            super(EventManagementItemView);
            //引数で渡されたitemViewがない場合の処理（保険）
            if(EventManagementItemView!=null) {
                //引数で渡された一行分の画面部品から、表示に使われる部品を取得。
                this.event_name = EventManagementItemView.findViewById(R.id.event_name);
                this.area = EventManagementItemView.findViewById(R.id.area);
                this.place = EventManagementItemView.findViewById(R.id.place);
                this.event_day = EventManagementItemView.findViewById(R.id.event_day);
                this.inviteTag = EventManagementItemView.findViewById(R.id.inviteTag);
                this.deadline = EventManagementItemView.findViewById(R.id.deadline);
            }
        }

        public TextView getTextItem() {
            return event_name;
        }
    }


}


