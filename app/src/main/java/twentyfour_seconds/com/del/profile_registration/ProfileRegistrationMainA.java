package twentyfour_seconds.com.del.profile_registration;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import twentyfour_seconds.com.del.R;
import twentyfour_seconds.com.del.chat.UserDTO;
import twentyfour_seconds.com.del.mypage.MyPageActivity;
import twentyfour_seconds.com.del.util.Common;

public class ProfileRegistrationMainA extends AppCompatActivity {

    //端末内の写真の場所を示すuri
    private Uri uri = null;
    //更新後のurl（初期値は現行のurl）
    private String newUrl = Common.profileImageUrl;
    //更新後のfilename（初期値は現行のfilename）
    String newFilename = Common.filename;
    //処理が完了してから、画面遷移を行い、再度データベースを読み込むので、latchを使用し、同期処理を行う。
    CountDownLatch latch = new CountDownLatch(1);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profileregistration);



        //編集可能な画面の部品に現在のデータをセット
        EditText nickname_profile = findViewById(R.id.nickname_profile);
        EditText selfIntroduction_profile = findViewById(R.id.selfintroduction_profile);
        EditText age_profile = findViewById(R.id.age_profile);
        EditText area_profile = findViewById(R.id.area_profile);
        TextView gender_profile = findViewById(R.id.gender_profile);

        nickname_profile.setText(Common.username);
        selfIntroduction_profile.setText(Common.profile);
        String ageStr = Common.age + "";
        age_profile.setText(ageStr);
        area_profile.setText(Common.location);
        //性別を設定
        String genderStr;
        if(Common.gender == 0){
            //コード値0は男
            genderStr = "男性";
        }else{
            //コード値1は女
            genderStr = "女性";
        }
        gender_profile.setText(genderStr);

        //画像データを取得
        //Firebaseより、写真を取得し、画面に表示
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/users/" + Common.uid);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserDTO user = dataSnapshot.getValue(UserDTO.class);

                //imageを張り付ける
                ImageView profileregistrationimageView = findViewById(R.id.profileRegistrationmImageView);
                Picasso.get().load(user.getProfileImageUrl()).into(profileregistrationimageView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        //保存ボタンをクリックしたときのリスナーを設定

        TextView profileSaveButton = findViewById(R.id.profileSaveButton);

        profileSaveButtonClickListener profileSaveButtonClickListener = new profileSaveButtonClickListener();
        profileSaveButton.setOnClickListener(profileSaveButtonClickListener);

        //画像を変更するボタンをクリックしたときのリスナーを設定

        TextView imageViewChange = findViewById(R.id.ImageViewChange);

        ImageViewChangeButtonClickListener imageViewChangeButtonClickListener = new ImageViewChangeButtonClickListener();
        imageViewChange.setOnClickListener(imageViewChangeButtonClickListener);

    }


    //グループを検索ボタンを押下時の動き
    public class profileSaveButtonClickListener implements View.OnClickListener{
        public void onClick(View view){

            //名前を取得
            EditText nickname_profile = findViewById(R.id.nickname_profile);
            Common.username = nickname_profile.getText().toString();
            //自己紹介を取得
            EditText selfIntroduction_profile = findViewById(R.id.selfintroduction_profile);
            Common.profile = selfIntroduction_profile.getText().toString();
            //年齢を取得
            EditText age_profile = findViewById(R.id.age_profile);
            Common.age = Integer.valueOf(age_profile.getText().toString());
            //イベントの開催場所を取得
            EditText area_profile = findViewById(R.id.area_profile);
            Common.location = area_profile.getText().toString();
            //性別を取得
            TextView gender_profile = findViewById(R.id.gender_profile);
            String genderStr = gender_profile.getText().toString();


            //genderは数字に変換して登録
            if(genderStr.equals("男性")){
                //男はコード値0
                Common.gender = 0;
            }else{
                //女はコード値1
                Common.gender = 1;
            }

            //***画像の処理について***

            //①画像データをFireStoreに新規登録する

            //端末内の写真の場所を示すuri
            if(uri != null){
                uploadImageToFirebaseStorage();
            }else{
                //画像を選択していない場合は、処理しない
                Log.d("profileImageUrl No Change", "File Location : " + Common.profileImageUrl);
                firebaseDatanewEntry();
//                latch.countDown();
            }
//            try {
//                latch.await();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

            //②FireStoreから、旧の画像データを削除する
//            deleteOldImageFromFirebase();

        }
    }

    //画像を変更するボタンを押下時の動き(imageViewに設定するまで）
    public class ImageViewChangeButtonClickListener implements View.OnClickListener {
        public void onClick(View view) {

            //①画像をピッカーから取得
            Log.d("RegisterActivity", "Try to photo Selector");
            //アプリが ACTION_OPEN_DOCUMENTインテントを開始すると、ピッカーが起動し、条件に一致するすべてのドキュメントプロバイダが表示されます。
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            //CATEGORY_OPENABLEをインテントに追加すると、結果がフィルタリングされ、画像ファイルなどの開くことができるドキュメントのみが表示されます。
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            //intent.setType("image/*") で結果をさらにフィルタリングし、画像 MIME データタイプのドキュメントのみを表示します。
            intent.setType("image/*");
            startActivityForResult(intent, 0);

        }
    }

    @Override
    //startActivityForResultでandroid内の写真フォルダにアクセス。アクセス後、戻った際に呼ばれるメソッドがこのonActivityResult
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        if (requestCode == 0 && resultCode == RESULT_OK && resultData != null) {
            if (resultData != null) {
                //写真の場所を示すuriを取得
                uri = resultData.getData();

                try {
                    //取得した画像をビットマップ型に変換
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);

                    //circleimageviewという、imageViewを使用し、そちらに画像を出力する（上手く円形になる）
                    ImageView profileRegistrationmImageView = findViewById(R.id.profileRegistrationmImageView);
                    profileRegistrationmImageView.setImageBitmap(bitmap);

                    //ただし、そのままでは既存のselect_photo_button_register_Buttonが邪魔なので、
                    //透明度を設定するsetAlpha(0)メソッドで、透明度を0（透明にする）
                    profileRegistrationmImageView.setAlpha(0);

                    //select_photo_button_register_Buttonのdrawableのbackgroundに設定（旧）
//                    BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
//                    select_photo_button_register_Button.setBackgroundDrawable(bitmapDrawable);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    //新しい画像をFirebaseに登録する。またその画像のファイル名、URLを取得する
    private void uploadImageToFirebaseStorage(){

        //画像を新しく選択していた場合
        //filenameはランダムな文字列を設定する
        newFilename = UUID.randomUUID().toString();
        //firebaseStorageの格納先を指定
        final StorageReference ref = FirebaseStorage.getInstance().getReference("/image/" + newFilename);
        Log.d("来た3", "ref = " + ref.toString());
        //***写真のアップロード***//
        setContentView(R.layout.loading_screen);
        //写真や動画など、端末上のローカル ファイルは、putFile() メソッドを使用してアップロードできます。
        // putFile() は File を受け取って UploadTask を返します。これを使用してアップロード ステータスの管理とモニタリングを行うことができます。
        ref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Write was successful!
                Log.d("profileRegistrationActivity", "Successfly upload image = " + uri);

                //ファイルをアップロードした後、StorageReference で getDownloadUrl() メソッドを呼び出して、ファイルをダウンロードするための URL を取得することができます。
                // Continue with the task to get the download URL
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        Log.d("profileImageUrl Change", "Old File Location : " + Common.profileImageUrl);
                        //新しいuriを保存する
                        Common.profileImageUrl = uri.toString();
                        Log.d("profileImageUrl Change", "New File Location : " + Common.profileImageUrl);
                        // 古い画像のfilenameを使用して削除処理を実施する。同時に、CommonのFilenameを新しい画像名に変更する
                        String oldFilename = Common.filename;
                        deleteOldImageFromFirebase(oldFilename);
                        Common.filename = newFilename;
                        Log.d("filename Change", "New filename is " + Common.filename);
                        firebaseDatanewEntry();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("profileRegistrationActivity", "File Location Get Fail");
                    }
                });
            }
        });
    }

    //古い画像をFirebaseから削除する。
    private void deleteOldImageFromFirebase(final String oldFilename){
        // Create a storage reference from our app
        StorageReference ref = FirebaseStorage.getInstance().getReference("/image/" + oldFilename);

        // Delete the file
        ref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("Delete file", "Old filename is " + oldFilename);
//                latch.countDown();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Uh-oh, an error occurred!
//                latch.countDown();
            }
        });
        //Firebaseの処理が完了したら、latchを減らす
    }

    //FirebaseのUser情報にデータを登録する
    private void firebaseDatanewEntry(){
        //DBに書き込みに行く
        //ユーザーのuid、ユーザー情報のデータベースリファレンス、引き継いできたユーザーimageを登録する
        String uid = FirebaseAuth.getInstance().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/users");
        //firebaseで一部のデータを更新したいときは、Updateを使用する
        Map<String, Object> updateUserData = new HashMap<>();
        updateUserData.put("username", Common.username);
        updateUserData.put("age", Common.age);
        updateUserData.put("gender", Common.gender);
        updateUserData.put("location", Common.location);
        updateUserData.put("profile", Common.profile);
        updateUserData.put("filename", Common.filename);
        updateUserData.put("profileImageUrl", Common.profileImageUrl);

        //データをUpdate
        ref.child(uid).updateChildren(updateUserData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("profileRegistrationActivity", "Finally we save the user to Firebase Databese");

                //保存したらば、マイページ画面に帰る
                Intent intentMypage = new Intent(getApplicationContext(), MyPageActivity.class);
                startActivity(intentMypage);
            }
        });
    }






}