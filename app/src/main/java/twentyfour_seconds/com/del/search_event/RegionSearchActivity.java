package twentyfour_seconds.com.del.search_event;

import android.app.AppComponentFactory;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import twentyfour_seconds.com.del.R;
import twentyfour_seconds.com.del.chat.UserDTO;
import twentyfour_seconds.com.del.create_user.RegisterActivity;
import twentyfour_seconds.com.del.top_page.TopActivity;
import twentyfour_seconds.com.del.util.Common;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;

public class RegionSearchActivity extends AppCompatActivity {

    UserDTO currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.region_search_setting);

        //toolbarを実装する
        // ツールバーをアクションバーとしてセット
        Toolbar toolbar_activityTop = (Toolbar) findViewById(R.id.toolbar_chatActivity);
        toolbar_activityTop.setTitle("");
        //toolbar内のtextviewを取得し、文字列を設定（色を白に変えたいが、style.xmlで変えようとすると、すべて変わる）
        TextView toolbar_text = findViewById(R.id.toolbar_text);
        toolbar_text.setText("表示する地域を限定する");
        setSupportActionBar(toolbar_activityTop);

        //firebaseより、ユーザーごとに前回まで設定していた地域情報を呼び出し、画面に反映する
        //チェックボックスの操作に関する説明：https://seesaawiki.jp/w/moonlight_aska/d/%A5%C1%A5%A7%A5%C3%A5%AF%A5%DC%A5%C3%A5%AF%A5%B9%A4%CE%A5%C1%A5%A7%A5%C3%A5%AF%BE%F5%C2%D6%A4%F2%C0%DF%C4%EA%2C%20%BC%E8%C6%C0%A4%B9%A4%EB
        GetUserRegionSetting();

    }

    private void GetUserRegionSetting(){
        String regionsetting = Common.regionsetting;
        //設定されている値を各都道府県ごとに分解する(regionsettingには、47都道府県の設定情報が0or1で登録されている）
        //splitに関する説明：https://www.javadrive.jp/start/string_class/index5.html
        //splitに関する説明：https://zero-config.com/java/string_split.html
        //splitに関する説明：https://qiita.com/komiya_atsushi/items/7fdca9710578723fa8c7

        regionsetting = "101";

        String[] regionsettingchar = regionsetting.split("");

        Log.d("0", regionsettingchar[0]);
        Log.d("1", regionsettingchar[1]);
        Log.d("2", regionsettingchar[2]);
        Log.d("3", regionsettingchar[3]);

        //東京のチェック
        if(regionsettingchar[1].equals("1")){
            CheckBox checkbox = (CheckBox)findViewById(R.id.tokyo);
            checkbox.setChecked(true);
        }else{
            //何もしない
        }

        //神奈川のチェック
        if(regionsettingchar[2].equals("1")){
            CheckBox checkbox = (CheckBox)findViewById(R.id.kanagawa);
            checkbox.setChecked(true);
        }else{
            //何もしない
        }

        //埼玉のチェック
        if(regionsettingchar[3].equals("1")){
            CheckBox checkbox = (CheckBox)findViewById(R.id.saitama);
            checkbox.setChecked(true);
        }else{
            //何もしない
        }


    }


    //toolbarに使用するmenuをここでinflateする
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_region_search, menu);
        return true;
    }

    //menuがクリックされた時の挙動を記載
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //保存ボタンを押下時
            case R.id.RegionSave:

                //現在のチェック状況を取得
                String regionSettingValue = GetRegionValue();

                //ユーザーのuid、ユーザー情報のデータベースリファレンス、引き継いできたユーザーimageを登録する
                String uid = FirebaseAuth.getInstance().getUid();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/users");
                //firebaseで一部のデータを更新したいときは、Updateを使用する
                Map<String, Object> updateregiondata = new HashMap<>();
                updateregiondata.put("regionSetting", regionSettingValue);
                //データをUpdate
                ref.child(uid).updateChildren(updateregiondata).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("RegionSearchActivity", "Finally we save the user to Firebase Databese");

                        //登録に成功した場合は、LatestMessagesActivityに遷移する
                        Intent intent = new Intent(getApplicationContext(), TopActivity.class);
                        //この一文を記載することで、元のログイン画面に戻れないようにする
                        intent.addFlags(FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
                break;
        }
        return false;
    }


    private String GetRegionValue(){

        //東京のチェック状態を確認
        CheckBox checkboxTokyo = (CheckBox)findViewById(R.id.tokyo);
        String regionsetting1 = null;

        //現在の選択状態を保存
        if(checkboxTokyo.isChecked() == true) {
            // チェックされた状態の時の処理を記述
            regionsetting1 = "1";
        }
        else {
            // チェックされていない状態の時の処理を記述
            regionsetting1 = "0";
        }


        //神奈川のチェック状態を確認
        CheckBox checkboxKanagawa = (CheckBox)findViewById(R.id.kanagawa);
        String regionsetting2 = null;

        //現在の選択状態を保存
        if(checkboxKanagawa.isChecked() == true) {
            // チェックされた状態の時の処理を記述
            regionsetting2 = "1";
        }
        else {
            // チェックされていない状態の時の処理を記述
            regionsetting2 = "0";
        }


        //埼玉のチェック状態を確認
        CheckBox checkboxSaittama = (CheckBox)findViewById(R.id.saitama);
        String regionsetting3 = null;

        //現在の選択状態を保存
        if(checkboxSaittama.isChecked() == true) {
            // チェックされた状態の時の処理を記述
            regionsetting3 = "1";
        }
        else {
            // チェックされていない状態の時の処理を記述
            regionsetting3 = "0";
        }

        String regionSettingValue = regionsetting1 + regionsetting2 + regionsetting3;

        return regionSettingValue;
    }


}