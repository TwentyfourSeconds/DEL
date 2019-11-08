package twentyfour_seconds.com.del.event_management;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import twentyfour_seconds.com.del.DTO.EventInfoDTO;
import twentyfour_seconds.com.del.DTO.ViewItemDTO;
import twentyfour_seconds.com.del.R;
import twentyfour_seconds.com.del.chat.ChatActivity;
import twentyfour_seconds.com.del.chat.ChatMessageDTO;
import twentyfour_seconds.com.del.event_info.EventInfoDAO;
import twentyfour_seconds.com.del.util.Common;
import twentyfour_seconds.com.del.util.CustomActivity;
import twentyfour_seconds.com.del.util.ViewAdapterReadOnly;

public class EventManagementMaintenance extends CustomActivity {

    String event_id;
    private final String ID_SEND = "id=";
    private final String EVENT_URL = Common.ID_SEARCH_EVENT_URL;
    private EventInfoDTO eventInfoDTO = new EventInfoDTO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_management_maintenance);

        Receivedata();

        //eventテーブルより、イベントの現在の状況を取得
        final CountDownLatch latch = new CountDownLatch(1);
        String write = "";
        StringBuilder sb = new StringBuilder();

        String SEND_UID = "eventer_uid=" + event_id;

        Log.d("SEND_UID", SEND_UID);
        sb.append(ID_SEND + event_id);
        write = sb.toString();

        EventInfoDAO eventInfoDAO = new EventInfoDAO(EVENT_URL, write, eventInfoDTO, latch);
        eventInfoDAO.execute();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //各種情報を取得

        String eventName = eventInfoDTO.getEventName();
        String largeArea = eventInfoDTO.getLargeArea();
        String smallArea = eventInfoDTO.getSmallArea();
        String eventDay = eventInfoDTO.getEventDay();
        String eventTag = eventInfoDTO.getEventTag();

        //eventTagをメッセージに変換する
        String[] strArray = eventTag.split("");
        ViewItemDTO viewItemDTO = new ViewItemDTO();
        List<ViewItemDTO> tagMessageList = new ArrayList<ViewItemDTO>();

        for(int i = 0; i < strArray.length; i++) {
            switch (strArray[i]) {
                case "1":
                    viewItemDTO.setText("机に座ってガッツリと");
                    tagMessageList.add(viewItemDTO);
                    break;
                case "2":
                    viewItemDTO.setText("密室からの脱出");
                    tagMessageList.add(viewItemDTO);
                    break;
                case "3":
                    viewItemDTO.setText("街を歩き回って");
                    tagMessageList.add(viewItemDTO);
                    break;
                case "4":
                    viewItemDTO.setText("遊園地や野球場で");
                    tagMessageList.add(viewItemDTO);
                    break;
                case "5":
                    viewItemDTO.setText("短い時間で気軽に");
                    tagMessageList.add(viewItemDTO);
                    break;
                case "6":
                    viewItemDTO.setText("アニメタイアップ");
                    tagMessageList.add(viewItemDTO);
                    break;
            }
        }

        //*取得してきたデータをタグ形式で出力する*//
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.event_tag);

        //--------------------------------flexBox Layout の調整-----------------------------------------------//
        // FlexboxLayoutManangerを定義する（レイアウトマネージャーは、リストデータの見え方を決める。※これがリストビューとは異なるリサイクラービューのいいところ）
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(getApplicationContext());
        //どっち側に伸ばすか.
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        // 子Viewの並び方向へのViewの張り付き位置
        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);
        // リサイクラービューにレイアウトマネージャーを設定
        recyclerView.setLayoutManager(flexboxLayoutManager);

        //--------------------------------viewAdapter の調整-----------------------------------------------//

        // アダプターオブジェクトを生成して、下のメソッドで設定した文字列を追加
        ViewAdapterReadOnly viewAdapterReadOnly = new ViewAdapterReadOnly(tagMessageList);
        // アダプターオブジェクトをセット
        recyclerView.setAdapter(viewAdapterReadOnly);

        Button eventStatusChangeButton = findViewById(R.id.event_status_change_button);
        View.OnClickListener eventStatusChangeButtonClick = new eventStatusChangeButtonClickListener();
        eventStatusChangeButton.setOnClickListener(eventStatusChangeButtonClick);

        //現在の参加者をFirebaseから取得する
        EventMembersGet();







    }


    //EventManagementListから引き継いできた情報を取得する
    private void Receivedata(){
        Intent intent = getIntent();
        this.event_id = intent.getStringExtra("event_id");

        Log.d("", event_id);
    }


    //firebaseからグループメンバーを取得する
    //現在のログイン者を取得
    private void EventMembersGet(){

//        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
//
//        mDatabase.child("/Group/"  + 41 + "/GroupMembers" ).child("uid").addListenerForSingleValueEvent(
//                new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        GroupMembersDTO groupMembersDTO = dataSnapshot.getValue(GroupMembersDTO.class);
//
//                        Log.d("groupMembersDTO", groupMembersDTO.uid + "");
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//                    }
//                });


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("/Group/" + 41 + "/GroupMembers/" + "-Lsm02VwzXxmllLjyX3u");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GroupMembersDTO groupMembersDTO = dataSnapshot.getValue(GroupMembersDTO.class);

                Log.d("groupMembersDTO", groupMembersDTO.uid + "");

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }



    //登録内容の確認ボタンを押下時の動き(eventCreate4に移動)
    public class eventStatusChangeButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), EventManagementMaintenanceEventStatusChange.class);
            //更新するデータを判別するため、イベントidを添付して送る
            intent.putExtra("event_id", event_id);
            startActivity(intent);
        }
    }



}