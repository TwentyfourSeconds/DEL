package twentyfour_seconds.com.del.mypage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import twentyfour_seconds.com.del.R;
import twentyfour_seconds.com.del.chat.UserDTO;
import twentyfour_seconds.com.del.notification.AddTopicTest;
import twentyfour_seconds.com.del.profile_registration.ProfileRegistrationMainA;
import twentyfour_seconds.com.del.util.Common;
import twentyfour_seconds.com.del.util.CustomActivity;

public class MyPageActivity extends CustomActivity {

    private ImageView iconProfile;
    private TextView topNameProfile;
    private TextView selfIntroductionProfile;
    private TextView nameProfile;
    private TextView genderProfile;
    private TextView areaProfile;
    private TextView ageProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        //toolbarを実装する
        // ツールバーをアクションバーとしてセット
        Toolbar toolbar_activityTop = (Toolbar) findViewById(R.id.toolbar_activityMypage);
        toolbar_activityTop.setTitle("");
        //toolbar内のtextviewを取得し、文字列を設定（色を白に変えたいが、style.xmlで変えようとすると、すべて変わる）
        TextView toolbar_text = findViewById(R.id.toolbartext_activity_mypage);
        toolbar_text.setText("プロフィールの設定");
        setSupportActionBar(toolbar_activityTop);


        //Firebaseより、写真を取得し、画面に表示
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/users/" + Common.uid);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserDTO user = dataSnapshot.getValue(UserDTO.class);

                //imageを張り付ける
                ImageView profileImageView = findViewById(R.id.profileImageView);
                Picasso.get().load(user.getProfileImageUrl()).into(profileImageView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //Commonクラスより、ユーザー情報を取得する
        TextView userName = findViewById(R.id.userName);
        TextView selfIntroduction = findViewById(R.id.selfIntroduction);
        TextView location = findViewById(R.id.location);
        TextView age_profile = findViewById(R.id.age_profile);
        TextView gender = findViewById(R.id.gender);

        //Commonクラスより値を設定
        userName.setText(Common.username);
        selfIntroduction.setText(Common.profile);
        location.setText(Common.username);
        //年齢を設定
        String ageStr = Common.age + "";
        age_profile.setText(ageStr);

        //性別を設定
        String genderStr;
        if(Common.gender == 0){
            //コード値0は男
            genderStr = "男性";
        }else{
            //コード値1は女
            genderStr = "女性";
        }
        gender.setText(genderStr);

        //現在地を設定
        location.setText(Common.location);


        //プロフィール編集画面に遷移する

        Button profileEdit = findViewById(R.id.profileEditButton);
        View.OnClickListener buttonClick = new MyPageActivity.profileClickListener();
        profileEdit.setOnClickListener(buttonClick);


        //下部メニューボタンを押下したときの処理を記載
        ImageView menu_bar_home = findViewById(R.id.mypage_tab).findViewById(R.id.menu_bar_home);
        ImageView menu_bar_event = findViewById(R.id.mypage_tab).findViewById(R.id.menu_bar_event);
        ImageView menu_bar_chat = findViewById(R.id.mypage_tab).findViewById(R.id.menu_bar_chat);
        ImageView menu_bar_mypage = findViewById(R.id.mypage_tab).findViewById(R.id.menu_bar_mypage);

        MyPageActivity.menuClickListener menuClickListener = new MyPageActivity.menuClickListener();

        menu_bar_home.setOnClickListener(menuClickListener);
        menu_bar_event.setOnClickListener(menuClickListener);
        menu_bar_chat.setOnClickListener(menuClickListener);
        menu_bar_mypage.setOnClickListener(menuClickListener);

        //終わったら削除すること
        Button addbutton = findViewById(R.id.addTopicButton);
        MyPageActivity.addTopicButtonClickListener addTopicButtonClickListener = new MyPageActivity.addTopicButtonClickListener();
        addbutton.setOnClickListener(addTopicButtonClickListener);

    }

    //プロフィールを編集するボタンを押下時の動き
    public class profileClickListener implements View.OnClickListener{
        public void onClick(View view){
            Intent intent_profileRegistrationMain = new Intent(getApplicationContext(), ProfileRegistrationMainA.class);
            startActivity(intent_profileRegistrationMain);
        }
    }


    //終わったら削除すること
    public class addTopicButtonClickListener implements View.OnClickListener{
        public void onClick(View view){
            Intent AddTopicTest = new Intent(getApplicationContext(), AddTopicTest.class);
            startActivity(AddTopicTest);
        }
    }


}
