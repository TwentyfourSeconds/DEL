package twentyfour_seconds.com.del;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class chat extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);

        // Activityのライフサイクルの理解
        Log.i("LifeCycle", "onCreate");
        //データベース参照を取得する＜公式より：https://firebase.google.com/support/guides/firebase-android?hl=ja＞
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();





    }
}