package twentyfour_seconds.com.del.event_management;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import twentyfour_seconds.com.del.DTO.EventInfoDTO;
import twentyfour_seconds.com.del.DTO.ViewItemDTO;
import twentyfour_seconds.com.del.R;
import twentyfour_seconds.com.del.chat.ChatActivity;
import twentyfour_seconds.com.del.chat.ChatMessageDTO;
import twentyfour_seconds.com.del.chat.UserDTO;
import twentyfour_seconds.com.del.event_info.EventInfoDAO;
import twentyfour_seconds.com.del.util.Common;
import twentyfour_seconds.com.del.util.CustomActivity;
import twentyfour_seconds.com.del.util.ViewAdapterReadOnly;

public class EventManagementMaintenance extends CustomActivity {

    String event_id;
    private final String ID_SEND = "id=";
    private final String EVENT_URL = Common.ID_SEARCH_EVENT_URL;
    private EventInfoDTO eventInfoDTO = new EventInfoDTO();
    //参加者一人分のuidを保持
    private List<String> memberUidList = new ArrayList<String>();
    //参加者一人分の名前と画像urlを保持
    private GroupMembersDTO groupMembersDTO = new GroupMembersDTO();
    //参加者全員分の名前と画像urlを保持
    private List<GroupMembersDTO> groupMembersDTOArrayList = new ArrayList<GroupMembersDTO>();
    /*
     * firebaseの同期処理を管理するクラス
     */
    private Lock lock = new ReentrantLock();
    private Condition finishedCondition = lock.newCondition();
    /*
     * 読み込みが完了したかどうかを表す
     */
    private boolean finished;



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

        //参加者一覧を作る

        //現在の参加者uidをFirebaseから取得する

//        EventMembersUidGet();

//        lock.lock();
//        try {
//            while (!finished) {
//                finishedCondition.await();
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            lock.unlock();
//        }

//      callback関数の書き方：https://www.youtube.com/watch?v=OvDZVV5CbQg
        readData(new FirebaseCallback() {
            @Override
            public void onCallback(List<String> list) {
                Log.d("readData", "readData" + list);

                for(int i = 0; i < list.size(); i++) {
                    Log.d("uid", "uid start");
                    String uid = list.get(i);
                    Log.d("uid", "uid = " + uid);
                    EventMembersProfileGet(uid);
                }
            }
        });


        //参加者の名前と画像を取得する
//        Log.d("adapter set", "adapter set");
//        ListView listView = (ListView)findViewById(R.id.listView);
//
//        EventManagementGroupMemberAdapter adapter = new EventManagementGroupMemberAdapter(EventManagementMaintenance.this);
//        Log.d("adapter mae" ,"adapter mae");
//        adapter.setMemberList(groupMembersDTOArrayList);
//        listView.setAdapter(adapter);

    }


    //EventManagementListから引き継いできた情報を取得する
    private void Receivedata(){
        Intent intent = getIntent();
        this.event_id = intent.getStringExtra("event_id");

        Log.d("", event_id);
    }


    //firebaseからグループメンバーを取得する
    //現在のログイン者を取得
    private void EventMembersUidGet(){

//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/Group/" + 41 + "/GroupMembers");
//        ref.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                GroupMembersDTO groupMembersDTO = dataSnapshot.getValue(GroupMembersDTO.class);
//
//                //取得したメッセージをリサイクラービューにセット
//                Log.d("groupMembersDTO", groupMembersDTO.uid);
//
//                memberUidList.add(groupMembersDTO.uid);
//
//
//                for(int i = 0; i < memberUidList.size(); i++) {
//
//                    String uid = memberUidList.get(i);
//                    Log.d("uid", uid + "");
//                    EventMembersProfileGet(uid);
//                }
//
//            }
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        //addListenerForSingleValueEventは、細かい場所まで指定しないと、値をとってこれなかった。
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("/Group/" + 41 + "/GroupMembers");
//        reference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                GroupMembersDTO groupMembersDTO = dataSnapshot.getValue(GroupMembersDTO.class);
//
//                Log.d("groupMembersDTO", groupMembersDTO.uid);
//
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });
    }

    //firebaseの同期処理を行うため、callback関数で処理を記載
    private interface FirebaseCallback{
        void onCallback(List<String> list);
    }

    private void readData(final FirebaseCallback firebaseCallback){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/Group/" + 41 + "/GroupMembers");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                GroupMembersDTO groupMembersDTO = dataSnapshot.getValue(GroupMembersDTO.class);

                for(int i = 0; i < dataSnapshot.getChildrenCount(); i++){
                    //取得したメッセージをリサイクラービューにセット
                    Log.d("groupMembersDTO", groupMembersDTO.uid);
                    memberUidList.add(groupMembersDTO.uid);
                }
                //取得完了
                Log.d("finish　groupMembersDTO", "finish　groupMembersDTO");
                firebaseCallback.onCallback(memberUidList);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }




    //firebaseからグループメンバーを取得する

    //ユーザー名を取得
    private void EventMembersProfileGet(String uid){

        final GroupMembersDTO groupMembersDTO = new GroupMembersDTO();
        //Firebaseより、名前、画像を取得
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("/users/" + uid);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserDTO currentUser = dataSnapshot.getValue(UserDTO.class);
                //imageを張り付ける
                for(int i = 0; i < dataSnapshot.getChildrenCount(); i++) {
                    groupMembersDTO.setUsername(currentUser.getUsername());
                    groupMembersDTO.setProfileImageUrl(currentUser.getProfileImageUrl());
                    groupMembersDTOArrayList.add(groupMembersDTO);
                }
                Log.d("完了", "groupMembersDTOArrayList" + groupMembersDTOArrayList);

                //取得したデータをアダプターにセットする。
                EventManagementGroupMemberAdapter adapter = new EventManagementGroupMemberAdapter(EventManagementMaintenance.this);
                Log.d("adapter mae" ,"adapter mae");
                adapter.setMemberList(groupMembersDTOArrayList);
                ListView listView = (ListView)findViewById(R.id.listView);
                listView.setAdapter(adapter);
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