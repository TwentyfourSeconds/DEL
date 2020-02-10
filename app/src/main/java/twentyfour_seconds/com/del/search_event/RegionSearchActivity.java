package twentyfour_seconds.com.del.search_event;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import twentyfour_seconds.com.del.R;
import twentyfour_seconds.com.del.chat.UserDTO;
import twentyfour_seconds.com.del.top_page.TopActivity;
import twentyfour_seconds.com.del.util.Common;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;

public class RegionSearchActivity extends AppCompatActivity {

    UserDTO currentUser;
    private CheckBox[] checkBoxs = new CheckBox[47];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.region_setting);

//        final String[] REGION = { "北海道", "青森県", "岩手県", "宮城県", "秋田県", "山形県", "福島県", "茨城県", "栃木県", "群馬県", "埼玉県", "千葉県", "東京都", "神奈川県", "新潟県", "富山県", "石川県", "福井県", "山梨県", "長野県", "岐阜県", "静岡県", "愛知県", "三重県", "滋賀県", "京都府", "大阪府", "兵庫県", "奈良県", "和歌山県", "鳥取県", "島根県", "岡山県", "広島県", "山口県", "徳島県", "香川県", "愛媛県", "高知県", "福岡県", "佐賀県", "長崎県", "熊本県", "大分県", "宮崎県", "鹿児島県", "沖縄県"};
//        final int[] ids = { R.id.hokkaido, R.id.aomori, R.id.iwate, R.id.miyagi, R.id.akita, R.id.yamagata, R.id.fukushima, R.id.ibaraki, R.id.tochigi, R.id.gunma, R.id.saitama, R.id.chiba, R.id.tokyo, R.id.kanagawa, R.id.niigata, R.id.toyama, R.id.ishikawa, R.id.fukui, R.id.yamanashi, R.id.nagano, R.id.gifu, R.id.shizuoka, R.id.aichi, R.id.mie, R.id.shiga, R.id.kyoto, R.id.osaka, R.id.hyogo, R.id.nara, R.id.wakayama, R.id.tottori, R.id.shimane, R.id.okayama, R.id.hiroshima, R.id.yamaguchi, R.id.tokushima, R.id.kagawa, R.id.ehime, R.id.kochi, R.id.fukuoka, R.id.saga, R.id.nagasaki, R.id.kumamoto, R.id.oita, R.id.miyazaki, R.id.kagoshima, R.id.okinawa };
        LinearLayout layout = findViewById(R.id.linearLayout);
        LinearLayout.LayoutParams checkBoxLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        String regionSetting = Common.regionSetting;
        if(regionSetting.length() != Common.REGION_ARY.length) {
            regionSetting = Common.REGION_NOTHING;
        }
        String[] regionSettingAry = regionSetting.split("");
        Log.d("test", regionSettingAry.length + "");
        for(int i = 0; i < Common.REGION_ARY.length; i++) {
//            Log.d("i", i+ "");
            checkBoxs[i] = new CheckBox(this);
            checkBoxs[i].setId(View.generateViewId());
//            checkBoxs[i].setId(ids[i]);
            checkBoxs[i].setText(Common.REGION_ARY[i]);
            if(regionSettingAry[i + 1].equals(Common.REGION_FLG_ON)) {
                checkBoxs[i].setChecked(true);
            }
            checkBoxs[i].setLayoutParams(checkBoxLayoutParams);
            layout.addView(checkBoxs[i]);
        }

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
//        GetUserRegionSetting();

    }

//    private void GetUserRegionSetting(){
//        String regionSetting = Common.regionSetting;
//        //設定されている値を各都道府県ごとに分解する(regionsettingには、47都道府県の設定情報が0or1で登録されている）
//        //splitに関する説明：https://www.javadrive.jp/start/string_class/index5.html
//        //splitに関する説明：https://zero-config.com/java/string_split.html
//        //splitに関する説明：https://qiita.com/komiya_atsushi/items/7fdca9710578723fa8c7
//
//        regionSetting = "101";
//
//        String[] regionsettingchar = regionSetting.split("");
//
//        Log.d("0", regionsettingchar[0]);
//        Log.d("1", regionsettingchar[1]);
//        Log.d("2", regionsettingchar[2]);
//        Log.d("3", regionsettingchar[3]);
//
//        //東京のチェック
//        if(regionsettingchar[1].equals("1")){
//            CheckBox checkbox = (CheckBox)findViewById(R.id.tokyo);
//            checkbox.setChecked(true);
//        }else{
//            //何もしない
//        }
//
//        //神奈川のチェック
//        if(regionsettingchar[2].equals("1")){
//            CheckBox checkbox = (CheckBox)findViewById(R.id.kanagawa);
//            checkbox.setChecked(true);
//        }else{
//            //何もしない
//        }
//
//        //埼玉のチェック
//        if(regionsettingchar[3].equals("1")){
//            CheckBox checkbox = (CheckBox)findViewById(R.id.saitama);
//            checkbox.setChecked(true);
//        }else{
//            //何もしない
//        }
//
//
//    }


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
                Common.regionSetting = regionSettingValue;

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

//        //東京のチェック状態を確認
////        CheckBox checkboxTokyo = (CheckBox)findViewById(R.id.tokyo);
////        String regionsetting1 = null;
////
////        //現在の選択状態を保存
////        if(checkboxTokyo.isChecked() == true) {
////            // チェックされた状態の時の処理を記述
////            regionsetting1 = "1";
////        }
////        else {
////            // チェックされていない状態の時の処理を記述
////            regionsetting1 = "0";
////        }
////
////
////        //神奈川のチェック状態を確認
////        CheckBox checkboxKanagawa = (CheckBox)findViewById(R.id.kanagawa);
////        String regionsetting2 = null;
////
////        //現在の選択状態を保存
////        if(checkboxKanagawa.isChecked() == true) {
////            // チェックされた状態の時の処理を記述
////            regionsetting2 = "1";
////        }
////        else {
////            // チェックされていない状態の時の処理を記述
////            regionsetting2 = "0";
////        }
////
////
////        //埼玉のチェック状態を確認
////        CheckBox checkboxSaittama = (CheckBox)findViewById(R.id.saitama);
////        String regionsetting3 = null;
////
////        //現在の選択状態を保存
////        if(checkboxSaittama.isChecked() == true) {
////            // チェックされた状態の時の処理を記述
////            regionsetting3 = "1";
////        }
////        else {
////            // チェックされていない状態の時の処理を記述
////            regionsetting3 = "0";
////        }


        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < Common.REGION_ARY.length; i++) {
            if(checkBoxs[i].isChecked()) {
                sb.append(Common.REGION_FLG_ON);
            } else {
                sb.append(Common.REGION_FLG_OFF);
            }

        }

        String regionSetting = sb.toString();

        return regionSetting;
    }


}