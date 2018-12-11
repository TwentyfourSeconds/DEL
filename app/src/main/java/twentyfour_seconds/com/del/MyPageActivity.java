package twentyfour_seconds.com.del;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class MyPageActivity extends AppCompatActivity {

    private ImageView icon;
    private TextView topName;
    private TextView selfIntroduction;
    private TextView name;
    private TextView gender;
    private TextView location;
    private TextView age;
    private TextView entryTrust;
    private TextView planTrust;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        icon = findViewById(R.id.icon);
        topName = findViewById(R.id.topName);
        selfIntroduction = findViewById(R.id.selfIntroduction);
        name = findViewById(R.id.name);
        gender = findViewById(R.id.gender);
        location = findViewById(R.id.location);
        age = findViewById(R.id.age);
        entryTrust = findViewById(R.id.entryTrust);
        planTrust = findViewById(R.id.planTrust);


//        icon.setImageResource(R.drawable.);
        topName.setText("takuma");
        name.setText("takuma");
        selfIntroduction.setText("自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介自己紹介");
        gender.setText("男");
        location.setText("東京");
        age.setText("29才");
        entryTrust.setText("50");
        planTrust.setText("50");

//        Intent intent = getIntent();
//        int iconId = getResources().getIdentifier(intent.getStringExtra("icon"), "drawable", getPackageName());
//        icon.setImageResource(iconId);
//        topName.setText(intent.getStringExtra("name"));
//        name.setText(intent.getStringExtra("name"));
//        selfIntroduction.setText(intent.getStringExtra("selfIntroduction"));
//        gender.setText(intent.getStringExtra("gender"));
//        location.setText(intent.getStringExtra("location"));
//        age.setText(intent.getStringExtra("age") + "才");
//        entryTrust.setText(intent.getStringExtra("entryTrust"));
//        planTrust.setText(intent.getStringExtra("planTrust"));
    }
}
