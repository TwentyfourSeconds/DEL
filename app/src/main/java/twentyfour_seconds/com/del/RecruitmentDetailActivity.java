package twentyfour_seconds.com.del;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class RecruitmentDetailActivity extends AppCompatActivity {

    private ImageView icon;
    private TextView leader;
    private TextView title;
    private TextView date1;
    private TextView date2;
    private TextView valuation;
    private TextView comment;
    private TextView location;
    private TextView age;
    private TextView gender;
    private TextView ticket;
    private TextView deadline;
    private Button entry;
    private Button temporary;
    private TextView chat;
    private TextView newComment;
    private Button chatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruitment_detail);

        icon = findViewById(R.id.icon);
        leader = findViewById(R.id.leader);
        title = findViewById(R.id.title);
        date1 = findViewById(R.id.date1);
        date2 = findViewById(R.id.date2);
        valuation = findViewById(R.id.valuation);
        comment = findViewById(R.id.comment);
        location = findViewById(R.id.location);
        age = findViewById(R.id.age);
        gender = findViewById(R.id.gender);
        ticket = findViewById(R.id.ticket);
        deadline = findViewById(R.id.deadline);
        entry = findViewById(R.id.entry);
        temporary = findViewById(R.id.temporary);
        chat = findViewById(R.id.chat);
        newComment = findViewById(R.id.newComment);
        chatButton = findViewById(R.id.chatButton);


//        icon.setImlocationResource(R.drawable.);
        leader.setText("ジータ");
        date1.setText("12/24");
        date2.setText("12/24");
        title.setText("○○のリアル脱出ゲーム");
        valuation.setText("50");
        comment.setText("自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介");
        comment.setFocusable(false);
        location.setText("東京");
        age.setText("29歳");
        gender.setText("男");
        ticket.setText("なし");
        deadline.setText("12/20");
        chat.setFocusable(false);

        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(RecruitmentDetailActivity.this);
                alertDialog.setTitle("下記のコメントでよろしいですか？");
                alertDialog.setMessage("テストメッセージ");
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.create().show();
            }
        });

//        Intent intent = getIntent();
//        int iconId = getResources().getIdentifier(intent.getStringExtra("icon"), "drawable", getPacklocationdate());
//        icon.setImlocationResource(iconId);
//        leader.setText(intent.getStringExtra("date"));
//        date.setText(intent.getStringExtra("date"));
//        title.setText(intent.getStringExtra("title"));
//        valuation.setText(intent.getStringExtra("valuation"));
//        comment.setText(intent.getStringExtra("comment"));
//        location.setText(intent.getStringExtra("location") + "才");
//        age.setText(intent.getStringExtra("age"));
//        gender.setText(intent.getStringExtra("gender"));
    }
}
