package twentyfour_seconds.com.del.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
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

import java.util.concurrent.CountDownLatch;

import twentyfour_seconds.com.del.R;
import twentyfour_seconds.com.del.create_user.RegisterActivity;
import twentyfour_seconds.com.del.util.Common;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;

public class ChatActivity extends AppCompatActivity {

    private GroupAdapter adapter;
    private UserDTO currentUser;
    String eventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);

        //NewMessageActivityから、ユーザーネームを取得し、タイトルバーに出す
        //動画では        GetterUser user = getIntent().getParcelableExtra<GetterUser>("USER_KEY");
//        user = getIntent().getParcelableExtra("USER_KEY");
//        String username = user.username;

        //EventManegementFragmentのintentより、イベントidを取得する
        Intent intent = getIntent();
        eventId = intent.getStringExtra("event_id");

        //ユーザー情報をFirebaseから取得する
//        fetchCurrentUser();
        currentUser = new UserDTO();

        currentUser.uid = Common.uid;
        currentUser.username = Common.username;
        currentUser.age = Common.age;
        currentUser.gender = Common.gender;
        currentUser.profile = Common.profile;
        currentUser.profileImageUrl = Common.profileImageUrl;
        currentUser.regionSetting = Common.regionSetting;

        //右上のアクションバーの名前(バージョンの問題？こける）
//        getSupportActionBar().setTitle(eventId);

        //toolbarを実装する
        // ツールバーをアクションバーとしてセット
        Toolbar toolbar_activityTop = (Toolbar) findViewById(R.id.toolbar_chatActivity);
        toolbar_activityTop.setTitle("");
        //toolbar内のtextviewを取得し、文字列を設定（色を白に変えたいが、style.xmlで変えようとすると、すべて変わる）
        TextView toolbar_text = findViewById(R.id.toolbar_text);
        toolbar_text.setText(eventId);
        setSupportActionBar(toolbar_activityTop);


        //ChatLogActivityで使用するリサイクラービューの定義
        adapter = new GroupAdapter<ContentViewHolder>();


        //メッセージ自動取得のメソッド
        ListenForMessage();

        //SENDボタン押下時の動き
        Button send_button_chat_log = findViewById(R.id.send_button_chat_log);
        send_button_chat_log_buttonClickListener send_button_chat_log_buttonClickListener = new send_button_chat_log_buttonClickListener();
        send_button_chat_log.setOnClickListener(send_button_chat_log_buttonClickListener);

        //リサイクラービューにセット
        RecyclerView recyclerView_newmessage = findViewById(R.id.recyclerview_chat_log);
        recyclerView_newmessage.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView_newmessage.setAdapter(adapter);
    }


    //REGISTERボタンをクリックしたときの処理
    public class send_button_chat_log_buttonClickListener implements View.OnClickListener {
        public void onClick(View view) {
            Log.d("ChatLog", "Attempt to send message ......");
            //メッセージを送信する
            performSendMessage();
        }
    }


    //firebaseより、イベントidでデータを取得する
//    private void fetchCurrentUser(){
//
//        String uid = FirebaseAuth.getInstance().getUid();
//        Log.d("fetchCurrentUser", " uid = " + uid);
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("/users/" + uid);
//        reference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                currentUser = dataSnapshot.getValue(UserDTO.class);
//                Log.d("currentUser", " currentUser = " + currentUser);
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });
//    }


    //firebaseからメッセージを受信する
    private void ListenForMessage(){
        //firebaseのmessageにアクセス


        //メッセージを受信する
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/GroupMessages/" + eventId);

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ChatMessageDTO chatMessageDTO = dataSnapshot.getValue(ChatMessageDTO.class);

                //取得したメッセージをリサイクラービューにセット
                if (chatMessageDTO != null) {
                    Log.d("ChatLog", chatMessageDTO.text);

                    //Userクラスに、画像とメッセージを編集
                    ChatFromItem chatFromItem = new ChatFromItem(chatMessageDTO.text, currentUser);
                    ChatToItem chatToItem = new ChatToItem(chatMessageDTO.text, currentUser);

                    //自分から送ったメッセージは右
                    if (chatMessageDTO.sendId.equals(FirebaseAuth.getInstance().getUid())) {
                        adapter.add(chatToItem);
                    } else {
                        adapter.add(chatFromItem);
                    }
                }
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

    //firebaseにメッセージを登録する
    private void performSendMessage(){
        final TextView editText_chat_log = findViewById(R.id.editText_chat_log);
        String text = editText_chat_log.getText().toString();
        //送信者のidは、使用者のid
        String sendId = currentUser.uid;

        //firebaseのmessage-userに登録する
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/GroupMessages/" + eventId).push();

        ChatMessageDTO chatMessageDTO = new ChatMessageDTO(ref.getKey(),text,sendId,System.currentTimeMillis()/1000);
        //*****現状、spaceでも送れてしまうので注意*************//
        ref.setValue(chatMessageDTO).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("ChatLog", "Save our chat message:" + ref.getKey());
                //成功していたらば、入力欄のテキスト変える
                editText_chat_log.setText("");
                //メッセ―ジを送信したら、最終行までスクロールポジションを移動する
                RecyclerView recyclerview_chat_log = findViewById(R.id.recyclerview_chat_log);
                recyclerview_chat_log.scrollToPosition(adapter.getItemCount() -1);
            }
        });
    }

    //送られるチャットの内容を定義
    public class ChatFromItem extends Item<ChatActivity.ContentViewHolder> {

        String text;
        UserDTO user;

        //引っ張ってきた引数(User)をコンストラクタで定義する
        public ChatFromItem(String text, UserDTO user){
            this.text = text;
            this.user = user;
        }

        @NonNull
        @Override
        public ChatActivity.ContentViewHolder createViewHolder(@NonNull View itemView) {
            return new ChatActivity.ContentViewHolder(itemView);
        }

        @Override
        public void bind(@NonNull ChatActivity.ContentViewHolder viewHolder, int position) {
            //メッセージを張り付ける
            viewHolder.textView_from_row.setText(text);

            //画像を張り付ける
            Log.d("ChatFromItem", "test" + user.profileImageUrl);
            Picasso.get().load(user.profileImageUrl).into(viewHolder.imageView_chat_from_low);
        }

        @Override
        public int getLayout() {
            return R.layout.chat_from_row;
        }
    }

    //こちらから送るチャットの内容を定義
    public class ChatToItem extends Item<ChatActivity.ContentViewHolder> {

        String text;
        UserDTO user;

        //引っ張ってきた引数(User)をコンストラクタで定義する
        public ChatToItem(String text, UserDTO user){
            this.text = text;
            this.user = user;
        }

        @NonNull
        @Override
        public ChatActivity.ContentViewHolder createViewHolder(@NonNull View itemView) {
            return new ChatActivity.ContentViewHolder(itemView);
        }

        @Override
        public void bind(@NonNull ChatActivity.ContentViewHolder viewHolder, int position) {
            viewHolder.textView_to_row.setText(text);

            //画像を張り付ける
            Log.d("ChatToItem", "test" + user.profileImageUrl);
            Picasso.get().load(user.profileImageUrl).into(viewHolder.imageView_chat_to_low);
        }

        @Override
        public int getLayout() {
            return R.layout.chat_to_row;
        }
    }

    //ChatFromItemとChatToItemで同じクラスを使用する。
    public class ContentViewHolder extends ViewHolder {

        @NonNull
        private TextView textView_from_row;
        @NonNull
        private TextView textView_to_row;
        @NonNull
        private ImageView imageView_chat_to_low;
        @NonNull
        private ImageView imageView_chat_from_low;

        public ContentViewHolder(@NonNull View rootView) {
            super(rootView);
            textView_from_row = rootView.findViewById(R.id.textView_from_row);
            textView_to_row = rootView.findViewById(R.id.textView_to_row);
            imageView_chat_from_low = rootView.findViewById(R.id.imageView_chat_from_row);
            imageView_chat_to_low = rootView.findViewById(R.id.imageView_chat_to_row);
        }
    }

    //toolbarに使用するmenuをここでinflateする
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chat_activity, menu);
        return true;
    }

    //menuがクリックされた時の挙動を記載
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.check_event:
                //イベント詳細画面に飛ぶ
                break;
        }
        return false;
    }



}
