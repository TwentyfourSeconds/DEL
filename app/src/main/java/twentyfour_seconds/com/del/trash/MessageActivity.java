package twentyfour_seconds.com.del.trash;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import twentyfour_seconds.com.del.R;

public class MessageActivity extends AppCompatActivity {

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_activity);

        // [START initialize_database_ref]
//        mDatabase = FirebaseDatabase.getInstance().getReference();

        User user = new User( "山田太郎",30 );
// インスタンスの取得
        FirebaseDatabase database = FirebaseDatabase.getInstance();

// ファイルパスを指定してリファレンスを取得
        DatabaseReference refName = database.getReference("info/user");
// データを登録
        refName.setValue(user);
    }


    public static class User {
        public String name;
        public Integer age;

        // 空のコンストラクタの宣言が必須
        public User() {
        }

        public User(String _name, Integer _age) {
            name = _name;
            age = _age;
        }
        public String getName(){
            return name;
        }
        public Integer getAge(){
            return age;
        }
    }



}