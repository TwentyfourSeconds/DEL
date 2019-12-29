package twentyfour_seconds.com.del.event_management;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.Group;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.GroupDataObserver;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;
import com.xwray.groupie.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import twentyfour_seconds.com.del.R;
import twentyfour_seconds.com.del.chat.ChatActivity;
import twentyfour_seconds.com.del.chat.UserDTO;
import twentyfour_seconds.com.del.util.Common;
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

    /*
     * 押下時、データを取得するためのList形式の変数を用意
     */
    private List<GroupMembersDTO> groupMembersDTOList;

    private Group group;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_management_maintenance_member_approval);



        //firebaseより、参加希望者の情報を取得
        //参加者一覧で使用するアダプターの定義
            adapter = new GroupAdapter<ChatActivity.ContentViewHolder>();

        //*参加者一覧に必要な情報を取得する

        //ここに、取得したデータを格納
        groupMembersDTOList = new ArrayList<>();

        firebaseDataGet();

        //--------------------------------LinearLayout の調整-----------------------------------------------//
        RecyclerView recyclerView = findViewById(R.id.member_approval_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // ここで縦方向に設定
        // リサイクラービューにレイアウトマネージャーを設定
        recyclerView.setLayoutManager(linearLayoutManager);
        // アダプターオブジェクトをセット
        recyclerView.setAdapter(adapter);


//        adapter.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(@NonNull Item item, @NonNull View view) {
//
//                int index = adapter.getAdapterPosition(item);
//                GroupMembersDTO groupMembersDTO = new GroupMembersDTO();
//                groupMembersDTO = groupMembersDTOList.get(index);
//                Log.d("a", groupMembersDTO.getUsername() + "");
//
//                Log.d("index", index + "");
//
//            }
//        });





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

                        Log.d("Username", currentUser.getUsername());
                        //後で、情報を取得できるように、リスト型の変数に値をストックしておく
                        groupMembersDTOList.add(groupMembersDTO);

                        EventManagementMaintenanceMemberAprroval.EventManagementMemberApprovalAdapter eventManagementMemberApprovalAdapter = new EventManagementMaintenanceMemberAprroval.EventManagementMemberApprovalAdapter(groupMembersDTO);
                        adapter.add(eventManagementMemberApprovalAdapter);


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
        public EventManagementMaintenanceMemberAprroval.ContentViewHolder createViewHolder(@NonNull final View itemView) {


            //ViewHolderを作成（GroupAdapterではあるが、ViewHolderの中身をクリックする処理を書きたいので、しっかり定義する。）
            final ContentViewHolder contentViewHolder = new EventManagementMaintenanceMemberAprroval.ContentViewHolder(itemView);

            //承認ボタン
             final Button approvalButton = itemView.findViewById(R.id.user_approval);
            //不承認ボタン
            final Button notApprovalButton = itemView.findViewById(R.id.user_not_approval);
            //ユーザー画像
            final ImageView userImageCheck = itemView.findViewById(R.id.approval_row_user_image);

            //承認ボタンを押下時
             approvalButton.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {

                     int position = contentViewHolder.getAdapterPosition(); // positionを取得
                     Log.i("position", position + "");

                     GroupMembersDTO groupMembersDTO = groupMembersDTOList.get(position);
                     Log.i("position", groupMembersDTO.getUsername() + "");
                     //承認を押下時、参加予定者から、参加者へFirebaseを変更する
                     FirebaseAprrovalToJoin(groupMembersDTO);
                     //データを削除する
                     groupMembersDTOList.remove(position);
//                     notifyItemRemoved(position);

//                     adapter.notifyDataSetChanged();
                     //画面のアニメーションを起動させる
                     adapter.removeGroup(position);
                     adapter.notifyItemChanged(position);
//                     adapter.removeGroup(adapter.getItemCount()-1);
//                     adapter.notifyItemRangeRemoved(position, ret);
                 }
             });

            //不承認ボタンを押下時
            notApprovalButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = contentViewHolder.getAdapterPosition(); // positionを取得
                    Log.i("position", position + "");

                    GroupMembersDTO groupMembersDTO = groupMembersDTOList.get(position);
                    Log.i("position", groupMembersDTO.getUsername() + "");
                    //不承認を押下時、参加予定者から、名前を消す
                    FirebaseAprrovalToDeny(groupMembersDTO);
                }
            });

            //ユーザー画像を押下時
            userImageCheck.setOnClickListener(
                    new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = contentViewHolder.getAdapterPosition(); // positionを取得
                    Log.i("position", position + "");

                    GroupMembersDTO groupMembersDTO = groupMembersDTOList.get(position);
                    Log.i("position", groupMembersDTO.getUsername() + "");
                    //画像を押下時、ユーザープロフィールへと飛ぶ
                    FirebaseAprrovalUserProfile(groupMembersDTO);
                }
            });

              return contentViewHolder;

//            return new EventManagementMaintenanceMemberAprroval.ContentViewHolder(itemView);
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



    //画像を押下時、ユーザープロフィールへと飛ぶ
    private void FirebaseAprrovalUserProfile(GroupMembersDTO groupMembersDTO){

        // インテントへのインスタンス生成
        Intent intent = new Intent(EventManagementMaintenanceMemberAprroval.this, EventManagementMaintenanceUserProfileCheck.class);
        //　インテントに値をセット
        intent.putExtra("id", groupMembersDTO.getUid());
        // サブ画面の呼び出し
        startActivity(intent);
    }

    //承認を押下時、参加予定者から、参加者へFirebaseを変更する
    private void FirebaseAprrovalToJoin(final GroupMembersDTO groupMembersDTO){

        //参加者のほうに登録する
        final DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("/Group/" + 42 + "/GroupMembers").push();

        GroupMemberJoinDTO groupMemberJoinDTO = new GroupMemberJoinDTO(groupMembersDTO.getUid());

//        Map<String, Object> insertUserId = new HashMap<>();
//        insertUserId.put("uid", groupMembersDTO.getUid());

        //addOnSuccessListenerは、帰ってくる引数がないときはVoid、それ以外は決められた変数を指定する？？
        ref1.setValue(groupMemberJoinDTO).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("EventManagementMemberApproval", "参加者登録処理完了" + groupMembersDTO.getUid());

                //参加希望者から削除する
                DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("/Group/" + 42 + "/eventAttendees");
                //削除する対象のuidを選択
                Query getApprovalUserQuery = ref2.orderByChild("uid").equalTo(groupMembersDTO.getUid());

                getApprovalUserQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot approvalDataSnapshot : dataSnapshot.getChildren()){
                            approvalDataSnapshot.getRef().removeValue();
                        }
                        Log.d("EventManagementMemberApproval", "参加者削除処理完了");
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    //拒否を押下時、参加予定者から、名前を消す
    private void FirebaseAprrovalToDeny(GroupMembersDTO groupMembersDTO){
    }




}
