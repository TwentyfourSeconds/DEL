package twentyfour_seconds.com.del.event_management;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

import twentyfour_seconds.com.del.R;
import twentyfour_seconds.com.del.chat.ChatActivity;
import twentyfour_seconds.com.del.chat.UserDTO;
import twentyfour_seconds.com.del.util.CustomActivity;

public class EventManagementMaintenanceMemberAprroval extends CustomActivity {
    /*
     * 非同期処理に対応させるため、Groupieを使用する。
     */
    private GroupAdapter adapter;

    /*
     * イベントidを使用。
     */
    private String event_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_management_maintenance_member_approval);



        //firebaseより、参加希望者の情報を取得
        //参加者一覧で使用するアダプターの定義
        adapter = new GroupAdapter<ChatActivity.ContentViewHolder>();

        //*参加者一覧に必要な情報を取得する
        firebaseDataGet();

        //--------------------------------LinearLayout の調整-----------------------------------------------//
        RecyclerView recyclerView = findViewById(R.id.member_approval_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // ここで縦方向に設定
        // リサイクラービューにレイアウトマネージャーを設定
        recyclerView.setLayoutManager(linearLayoutManager);
        // アダプターオブジェクトをセット
        recyclerView.setAdapter(adapter);








    }

    //EventManagementListから引き継いできた情報を取得する
    private void Receivedata(){
        Intent intent = getIntent();
        this.event_id = intent.getStringExtra("event_id");

        Log.d("", event_id);
    }







    //参加者を取得し、そのあとにユーザーテーブルより各種情報を取得する
    private void firebaseDataGet(){

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/Group/" + 42 + "/eventAttendees");
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
                        GroupMembersDTO groupMembersDTO = new GroupMembersDTO();
                        groupMembersDTO.setUid(currentUser.getUid());
                        groupMembersDTO.setUsername(currentUser.getUsername());
                        groupMembersDTO.setProfileImageUrl(currentUser.getProfileImageUrl());
                        EventManagementMaintenanceMemberAprroval.EventManagementMemberApprovalAdapter eventManagementMemberApprovalAdapter = new EventManagementMaintenanceMemberAprroval.EventManagementMemberApprovalAdapter(groupMembersDTO);
                        adapter.add(eventManagementMemberApprovalAdapter);

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



    public class EventManagementMemberApprovalAdapter extends Item<EventManagementMaintenanceMemberAprroval.ContentViewHolder> {

        GroupMembersDTO groupMembersDTO;

        //引っ張ってきた引数(groupMembersDTO)をコンストラクタで定義する
        public EventManagementMemberApprovalAdapter(GroupMembersDTO groupMembersDTO){
            this.groupMembersDTO = groupMembersDTO;
        }

        @NonNull
        @Override
        public EventManagementMaintenanceMemberAprroval.ContentViewHolder createViewHolder(@NonNull View itemView) {
            return new EventManagementMaintenanceMemberAprroval.ContentViewHolder(itemView);
        }

        @Override
        public void bind(@NonNull EventManagementMaintenanceMemberAprroval.ContentViewHolder viewHolder, int position) {
            //メッセージを張り付ける
            viewHolder.approval_username.setText(groupMembersDTO.username);

            //画像を張り付ける
//            Log.d("ChatFromItem", "test" + user.profileImageUrl);
            Picasso.get().load(groupMembersDTO.profileImageUrl).into(viewHolder.approval_userimage);
        }

        @Override
        public int getLayout() {
            return R.layout.event_management_maintenance_group_member_approval_row;
        }

    }

    //
    public class ContentViewHolder extends ViewHolder {

        @NonNull
        private TextView approval_username;
        @NonNull
        private ImageView approval_userimage;

        public ContentViewHolder(@NonNull View rootView) {
            super(rootView);
            approval_userimage = rootView.findViewById(R.id.approval_row_user_image);
            approval_username = rootView.findViewById(R.id.approval_row_username);

        }
    }



}
