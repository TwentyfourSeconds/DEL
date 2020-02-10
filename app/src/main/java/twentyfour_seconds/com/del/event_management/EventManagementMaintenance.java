package twentyfour_seconds.com.del.event_management;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

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
     * 非同期処理に対応させるため、Groupieを使用する。
     */
    private GroupAdapter adapter;

    /*
     * 参加希望者がいるかを判断するFLGを作成
     */
    TextView existJoinMemberText;
    Button checkJoinMemberButton;
    private int existEventAttendFlg = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_management_maintenance);

        //EventManagementListから引き継いできた情報を取得する
        Receivedata();

        //eventテーブルより、イベントの詳細を取得
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

        //取得結果より、画面に情報を表示

        String eventNameStr = eventInfoDTO.getEventName();
        String largeAreaStr = eventInfoDTO.getLargeArea();
        String smallAreaStr = eventInfoDTO.getSmallArea();
        String eventDayStr = eventInfoDTO.getEventDay();
        String maxPersonsStr = eventInfoDTO.getMember();
        String eventTagStr = eventInfoDTO.getEventTag();

        //イベント名を編集（トップ）



        //県を編集
        TextView largeArea = findViewById(R.id.large_area);
        largeArea.setText(largeAreaStr);

        //地域を編集
        TextView smallArea = findViewById(R.id.small_area);
        smallArea.setText(smallAreaStr);

        //イベント日時を編集
        TextView eventDayOn = findViewById(R.id.event_day_on);
        eventDayOn.setText(eventDayStr);

        //最大人数を編集
//        TextView maxPersons = findViewById(R.id.max_persons);
//        maxPersons.setText(maxPersons);


        /*
         * eventTagをメッセージに変換する
         */

        //*①eventTagをメッセージに変換する*//
        String[] strArray = eventTagStr.split("");
//        ViewItemDTO viewItemDTO = new ViewItemDTO();
        List<String> list = new ArrayList<>();

        List<ViewItemDTO> tagMessageList = new ArrayList<ViewItemDTO>();

        //外で一回のみ宣言するだけだと、同じレイアウトをリストに入れることになり、すべて同じ値になってしまう。
        //例）インスタンスＡを定義
        // 1:A
        // A = アニメタイアップ
        // 2:A
        // A = 短い時間で気軽に
        // 3:A
        // A = アニメタイアップ
        // となるので、最後のアニメタイアップですべて埋まる。
        //そのため、毎回、新しくインスタンスを作らないとダメ。
        //例）インスタンスＡを定義
        // 1:A
        // A = アニメタイアップ
        // 2:B
        // B = 短い時間で気軽に
        // 3:C
        // C = アニメタイアップ

        for(int i = 0; i < strArray.length; i++) {


            ViewItemDTO viewItemDTO = new ViewItemDTO();

            switch (strArray[i]) {
                case "1":
                    viewItemDTO.setText("机に座ってガッツリと");
                    tagMessageList.add(viewItemDTO);
                    Log.d("tag", "1");
                    break;
                case "2":
                    viewItemDTO.setText("密室からの脱出");
                    tagMessageList.add(viewItemDTO);
                    Log.d("tag", "2");
                    break;
                case "3":
                    viewItemDTO.setText("街を歩き回って");
                    tagMessageList.add(viewItemDTO);
                    Log.d("tag", "3");
                    break;
                case "4":
                    viewItemDTO.setText("遊園地や野球場で");
                    tagMessageList.add(viewItemDTO);
                    Log.d("tag", "4");
                    break;
                case "5":
                    viewItemDTO.setText("短い時間で気軽に");
                    tagMessageList.add(viewItemDTO);
                    Log.d("tag", "5");
                    break;
                case "6":
                    viewItemDTO.setText("アニメタイアップ");
                    tagMessageList.add(viewItemDTO);
                    Log.d("tag", "6");
                    break;
            }
        }

        Log.d("tagMessageList.size()", tagMessageList.size() + "");
        for(int i = 0 ; i < tagMessageList.size(); i++){
            Log.d("i", i + "");
            Log.d("tagMessageList = ", tagMessageList.get(i).getText() + "");
        }


        //*②取得してきたデータをタグ形式で出力する*//
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

        /*
         * 参加者一覧を作成する
         */

        //*現在の参加者uidをFirebaseから取得する

        //参加者一覧で使用するアダプターの定義
        adapter = new GroupAdapter<ChatActivity.ContentViewHolder>();

        //*参加者一覧に必要な情報を取得する
        firebaseDataGet();

        //--------------------------------LinearLayout の調整-----------------------------------------------//
        RecyclerView recyclerView2 = findViewById(R.id.group_member);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL); // ここで横方向に設定
        // リサイクラービューにレイアウトマネージャーを設定
        recyclerView2.setLayoutManager(linearLayoutManager);
//        recyclerView2.setNestedScrollingEnabled(false);
        // アダプターオブジェクトをセット
        recyclerView2.setAdapter(adapter);

        /*
        ※非同期処理について
        firebaseの処理は全て非同期なため、実際には、viewへ、adapterのセットが終わった後に、adapterの取得処理が動く。
        そのため、通常のリサイクラービューを使用すると、adapterへのセットが先に行われてしまうため、正しく表示がされない。
        しかし、groupieならば、そこらへんがうまく処理される。
         */


        //参加者がいる場合、申請申請があります
        //参加者がいる場合、現在、参加申請はありませんのメッセージを出しわける

        //*参加者一覧に必要な情報を取得する
        existJoinMemberText = findViewById(R.id.exist_join_member_text);
        checkJoinMemberButton = findViewById(R.id.check_join_member_button);

        firebaseEventAttendeesGet();


        //参加希望者確認画面へ遷移
        View.OnClickListener CheckJoinMemberButtonClick = new CheckJoinMemberButtonClickListener();
        checkJoinMemberButton.setOnClickListener(CheckJoinMemberButtonClick);



        //イベント条件を変更
        Button eventConditionChangeButton = findViewById(R.id.event_condition_change_button);
        View.OnClickListener eventConditionChangeButtonClick = new eventConditionChangeButtonClickListener();
        eventConditionChangeButton.setOnClickListener(eventConditionChangeButtonClick);







//      callback関数の書き方：https://www.youtube.com/watch?v=OvDZVV5CbQg
//        readData(new FirebaseCallback() {
//            @Override
//            public void onCallback(List<String> list) {
//                Log.d("readData onCallback start", "readData = " + list);
//
//                for(int i = 0; i < list.size(); i++) {
//                    String uid = list.get(i);
//                    Log.d("uid", "uid start uid = "  + uid);
//
//                    EventMembersProfileGet(uid, new SecondCallback(){
//                        @Override
//                        public void onCallback(){
//                            Log.d("Adapter set start", "---start---");
//                            RecyclerView recyclerView2 = findViewById(R.id.group_member);
//
//                            //--------------------------------flexBox Layout の調整-----------------------------------------------//
//                            // FlexboxLayoutManangerを定義する（レイアウトマネージャーは、リストデータの見え方を決める。※これがリストビューとは異なるリサイクラービューのいいところ）
//                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
//                            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL); // ここで横方向に設定
//                            // リサイクラービューにレイアウトマネージャーを設定
//                            recyclerView2.setLayoutManager(linearLayoutManager);
//
//                            //データをmessageListに格納
//                            //        messageList = this.initViewItemDtoList();
//
//                            // アダプターオブジェクトを生成して、下のメソッドで設定した文字列を追加
//                            EventManagementMaintenanceViewAdapter eventManagementMaintenanceViewAdapter = new EventManagementMaintenanceViewAdapter(groupMembersDTOArrayList);
//                            // アダプターオブジェクトをセット
//                            recyclerView2.setAdapter(eventManagementMaintenanceViewAdapter);
//                            Log.d("Adapter set finish", "---finish---");
//                        }
//                    });
//                }
//            }
//        });


        //参加者の名前と画像を取得する
//        Log.d("adapter set", "adapter set");
//        ListView listView = (ListView)findViewById(R.id.listView);
//
//        EventManagementGroupMemberAdapter adapter = new EventManagementGroupMemberAdapter(EventManagementMaintenance.this);
//        Log.d("adapter mae" ,"adapter mae");
//        adapter.setMemberList(groupMembersDTOArrayList);
//        listView.setAdapter(adapter);

    }



    //「こちらより確認ください」ボタンを押下時の動き(参加希望者確認画面に移動)
    public class CheckJoinMemberButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), EventManagementMaintenanceMemberAprroval.class);
            //更新するデータを判別するため、イベントidを添付して送る
            intent.putExtra("event_id", event_id);
            startActivity(intent);

        }
    }



    //「募集条件の変更」ボタンを押下時の動き(募集条件の変更画面に移動)
    public class eventConditionChangeButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view){
            Intent intent = new Intent(getApplicationContext(), EventManagementMaintenanceMemberAprroval.class);

            intent.putExtra("event_id", event_id);
            startActivity(intent);
        }
    }




    //参加者を取得し、そのあとにユーザーテーブルより各種情報を取得する
    private void firebaseDataGet(){

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/Group/" + 42 + "/GroupMembers");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                GroupMembersDTO groupMembersDTO = dataSnapshot.getValue(GroupMembersDTO.class);
                //取得完了
                Log.d("finish　readData2", "---finish---" + groupMembersDTO.uid);
//                uidList.add(groupMembersDTO.uid);

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("/users/" + groupMembersDTO.uid);
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        UserDTO currentUser = dataSnapshot.getValue(UserDTO.class);
                        Log.d("EventMembersProfileGet finish", "groupMembersDTOArrayList" + currentUser.getProfileImageUrl());
//                        currentUserList.add(currentUser);

                        Log.d("Adapter set start", "---start---");
                        GroupMembersDTO groupMembersDTO2 = new GroupMembersDTO();
                        groupMembersDTO2.setUid(currentUser.getUid());
                        groupMembersDTO2.setUsername(currentUser.getUsername());
                        groupMembersDTO2.setProfileImageUrl(currentUser.getProfileImageUrl());
                        EventManagementAdapter eventManagementAdapter = new EventManagementAdapter(groupMembersDTO2);
                        adapter.add(eventManagementAdapter);

                        Log.d("Username", currentUser.getUsername());
                        Log.d("Adapter set finish", "---finish---");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

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


    //参加者がいるときは、ボタンを活性化し、部品を残す。いない場合には、部品を消す。
    private void firebaseEventAttendeesGet() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("/Group/" + 42 + "/eventAttendees");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GroupMembersDTO existEventAttendees = dataSnapshot.getValue(GroupMembersDTO.class);
                if(existEventAttendees == null){
                    existJoinMemberText.setText("現在、参加申請はありません");
                    checkJoinMemberButton.setVisibility(View.GONE);
                }else{
                    existJoinMemberText.setText("申請申請があります");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }






    //EventManagementListから引き継いできた情報を取得する
    private void Receivedata(){
        Intent intent = getIntent();
        this.event_id = intent.getStringExtra("event_id");

        Log.d("", event_id);
    }


    //firebaseからグループメンバーを取得する
    //現在のログイン者を取得
//    private void EventMembersUidGet(){

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
//    }

    //firebaseの同期処理を行うため、callback関数で処理を記載
//    private interface FirebaseCallback{
//        void onCallback(List<String> list);
//    }
//
//    private void readData(final FirebaseCallback firebaseCallback){
//        //readData start
//        Log.d("readData start", "---Start---");
//
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/Group/" + 42 + "/GroupMembers");
//        ref.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                GroupMembersDTO groupMembersDTO = dataSnapshot.getValue(GroupMembersDTO.class);
//
//                for(int i = 0; i < dataSnapshot.getChildrenCount(); i++){
//                    //取得したメッセージをリサイクラービューにセット
//                    Log.d("groupMembersDTO", groupMembersDTO.uid);
//                    memberUidList.add(groupMembersDTO.uid);
//                }
//                //取得完了
//                Log.d("finish　readData", "---finish---");
//                firebaseCallback.onCallback(memberUidList);
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//            }
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//            }
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });
//    }


    //firebaseからグループメンバーを取得する
    //ユーザー名を取得
//    private void EventMembersProfileGet(String uid, final SecondCallback secondCallback){
//        Log.d("EventMembersProfileGet start", "---Start---");
//
//        final GroupMembersDTO groupMembersDTO = new GroupMembersDTO();
//        //Firebaseより、名前、画像を取得
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("/users/" + uid);
//        reference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                UserDTO currentUser = dataSnapshot.getValue(UserDTO.class);
//                //imageを張り付ける
//                for(int i = 0; i < dataSnapshot.getChildrenCount(); i++) {
//                    groupMembersDTO.setUsername(currentUser.getUsername());
//                    groupMembersDTO.setProfileImageUrl(currentUser.getProfileImageUrl());
//                    groupMembersDTOArrayList.add(groupMembersDTO);
//                }
//                Log.d("EventMembersProfileGet ginish", "groupMembersDTOArrayList" + groupMembersDTOArrayList);

                //取得したデータをアダプターにセットする。
//                EventManagementGroupMemberAdapter adapter = new EventManagementGroupMemberAdapter(EventManagementMaintenance.this);
//                Log.d("adapter mae" ,"adapter mae");
//                adapter.setMemberList(groupMembersDTOArrayList);
//                ListView listView = (ListView)findViewById(R.id.listView);
//                listView.setAdapter(adapter);
//                secondCallback.onCallback();
                // Get the RecyclerView object.
//                RecyclerView recyclerView2 = findViewById(R.id.group_member);
//
//                //--------------------------------flexBox Layout の調整-----------------------------------------------//
//                // FlexboxLayoutManangerを定義する（レイアウトマネージャーは、リストデータの見え方を決める。※これがリストビューとは異なるリサイクラービューのいいところ）
//                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
//                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL); // ここで横方向に設定
//                // リサイクラービューにレイアウトマネージャーを設定
//                recyclerView2.setLayoutManager(linearLayoutManager);
//
//                //データをmessageListに格納
//                //        messageList = this.initViewItemDtoList();
//
//                // アダプターオブジェクトを生成して、下のメソッドで設定した文字列を追加
//                EventManagementMaintenanceViewAdapter eventManagementMaintenanceViewAdapter = new EventManagementMaintenanceViewAdapter(groupMembersDTOArrayList);
//                // アダプターオブジェクトをセット
//                recyclerView2.setAdapter(eventManagementMaintenanceViewAdapter);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });
//    }

    //登録内容の確認ボタンを押下時の動き(EventStatusChangeに移動)
    public class eventStatusChangeButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), EventManagementMaintenanceEventStatusChange.class);
            //取得したid、イベントステータス表示を引き継ぐ
            intent.putExtra("event_uid", eventInfoDTO.getEventId());
            intent.putExtra("event_status", eventInfoDTO.getEventStatus());
            startActivity(intent);
        }
    }


    //
    public class EventManagementAdapter extends Item<EventManagementMaintenance.ContentViewHolder> {

        GroupMembersDTO groupMembersDTO;

        //引っ張ってきた引数(groupMembersDTO)をコンストラクタで定義する
        public EventManagementAdapter(GroupMembersDTO groupMembersDTO){
            this.groupMembersDTO = groupMembersDTO;
        }

        @NonNull
        @Override
        public EventManagementMaintenance.ContentViewHolder createViewHolder(@NonNull View itemView) {
            return new EventManagementMaintenance.ContentViewHolder(itemView);
        }

        @Override
        public void bind(@NonNull EventManagementMaintenance.ContentViewHolder viewHolder, int position) {
            //メッセージを張り付ける
            viewHolder.group_member_name.setText(groupMembersDTO.username);

            //画像を張り付ける
//            Log.d("ChatFromItem", "test" + user.profileImageUrl);
            Picasso.get().load(groupMembersDTO.profileImageUrl).into(viewHolder.group_member_image);
        }

        @Override
        public int getLayout() {
            return R.layout.event_management_maintenance_group_member_list_row;
        }

    }

    //
    public class ContentViewHolder extends ViewHolder {

        @NonNull
        private TextView group_member_name;
        @NonNull
        private ImageView group_member_image;

        public ContentViewHolder(@NonNull View rootView) {
            super(rootView);
            group_member_image = rootView.findViewById(R.id.group_member_image);
            group_member_name = rootView.findViewById(R.id.group_member_name);

        }
    }
}