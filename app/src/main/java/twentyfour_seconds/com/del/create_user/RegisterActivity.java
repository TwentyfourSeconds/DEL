package twentyfour_seconds.com.del.create_user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
//
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

import twentyfour_seconds.com.del.R;
import twentyfour_seconds.com.del.chat.UserDTO;
import twentyfour_seconds.com.del.top_page.TopActivity;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;

public class RegisterActivity extends AppCompatActivity {

    //firebase チュートリアル　1.FirebaseAuth インスタンスを宣言します。
    private FirebaseAuth mAuth;
    private Button select_photo_button_register_Button;
    //端末内の写真の場所を示すuri
    private Uri uri = null;
    //登録したユーザーネーム
    private String username_edittext_register_text;
//
//
//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);



        //REGISTERボタンのリスナー
        Button register_button = findViewById(R.id.register_button);

        View.OnClickListener register_buttonClickListener = new register_buttonClickListener();
        register_button.setOnClickListener(register_buttonClickListener);

        //テキストボタン押下時のリスナー
        TextView Already_have_an_account = findViewById(R.id.Already_have_an_acount_text_view);

        View.OnClickListener Already_have_an_account_ClickListener = new Already_have_an_acount_text_ClickListener();
        Already_have_an_account.setOnClickListener(Already_have_an_account_ClickListener);

        //select photoボタン押下時の処理を追加
        select_photo_button_register_Button = findViewById(R.id.select_photo_button_register);

        View.OnClickListener select_photo_button_register_Button_ClickListener = new select_photo_button_register_Button_ClickListener();
        select_photo_button_register_Button.setOnClickListener(select_photo_button_register_Button_ClickListener);

        //登録アクティビティの onCreate メソッドで、FirebaseAuth オブジェクトの共有インスタンスを取得します。
        mAuth = FirebaseAuth.getInstance();

    }

    //select photo register buttonを押下時、android端末内のデータを取得する
    public class select_photo_button_register_Button_ClickListener implements View.OnClickListener{
        public void onClick(View view) {
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
                    ImageView selectphoto_imageview_register = findViewById(R.id.selectphoto_imageview_register);
                    selectphoto_imageview_register.setImageBitmap(bitmap);

                    //ただし、そのままでは既存のselect_photo_button_register_Buttonが邪魔なので、
                    //透明度を設定するsetAlpha(0)メソッドで、透明度を0（透明にする）
                    select_photo_button_register_Button.setAlpha(0);

                    //select_photo_button_register_Buttonのdrawableのbackgroundに設定（旧）
//                    BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
//                    select_photo_button_register_Button.setBackgroundDrawable(bitmapDrawable);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //REGISTERボタンをクリックしたときの処理
    public class register_buttonClickListener implements View.OnClickListener {
        public void onClick(View view) {
            //usernameを取得
            TextView username_edittext_register = findViewById(R.id.username_edittext_register);
            username_edittext_register_text = username_edittext_register.getText().toString();
            //emailを取得
            TextView email_edittext_register = findViewById(R.id.email_edittext_register);
            String email = email_edittext_register.getText().toString();
            //passwordを取得
            TextView password_edittext_register = findViewById(R.id.password_edittext_register);
            String password = password_edittext_register.getText().toString();

            Log.d("RegisterActivity", "Email is = " + email);
            Log.d("RegisterActivity", "Password is = " + password);

            //firebase Authentication to create a user with email and password
            createAccount(email, password);

            //選択した画像をFirebase内のストレージに登録する
            uploadImageToFirebase();

        }
    }

    //実際にfirebaseでアカウントを作成するcreateAccountメソッド
    private void createAccount(String email, String password) {
        Log.d("createAccount", "createAccount:" + email);

        //emailかpasswordがnullだと、nullpointerで処理が止まるので、nullチェックを行うif文をセット
        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(RegisterActivity.this, "email or password = null",
                    Toast.LENGTH_SHORT).show();
        }else{
            //ユーザー作成処理を行う。
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("Firebase Sign in success", "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Log.d("Main", "Successfully create user with uid = " + user.getUid());
//                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("Firebase Sign in failure", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
//                                updateUI(null);
                            }

                            // ...
                        }
                    });
        }
    }

    //firebaseStorageにimageデータを登録するメソッド
    public void uploadImageToFirebase(){

        //端末内の写真の場所を示すuri
        if(uri != null){
            //filenameはランダムな文字列を設定する
            final String filename = UUID.randomUUID().toString();
            //firebaseStorageの格納先を指定
            final StorageReference ref = FirebaseStorage.getInstance().getReference("/image/" + filename);
            Log.d("RegisterActivity", "ref = " + ref.toString());
            //***写真のアップロード***//
            //写真や動画など、端末上のローカル ファイルは、putFile() メソッドを使用してアップロードできます。
            // putFile() は File を受け取って UploadTask を返します。これを使用してアップロード ステータスの管理とモニタリングを行うことができます。
            ref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Write was successful!
                    Log.d("RegisterActivity", "Successfly upload image = " + uri);

                    //ファイルをアップロードした後、StorageReference で getDownloadUrl() メソッドを呼び出して、ファイルをダウンロードするための URL を取得することができます。
                    // Continue with the task to get the download URL
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Log.d("RegisterActivity", "File Location : " + uri.toString());

                            //firebaseにデータを登録する
                            saveUserToFirebase(uri.toString(),filename);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("RegisterActivity", "File Location Get Fail");
                        }
                    });
                }
            });
        }else{
            //画像uriがnullの場合は、処理しない
        }
    }

//    //ユーザー情報をFirebaseのデータベースに登録するためのメソッド
    public void saveUserToFirebase(String profileImageUrl, String filename){

        //ユーザーのuid、ユーザー情報のデータベースリファレンス、引き継いできた画像へのurl、ファイル名を登録する
        String uid = FirebaseAuth.getInstance().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/users/" + uid);
        //Firebaseのデータベースへの初期登録処理（uid, username_edittext_register_text, profileImageUrl以外は初期値を登録を実施する）
        int age = 0;
        int gender = 0;
        String profile = "";
        String location = "";
        String regionsetting = "";

        //ここでRegisterActivityの内部で別のclassを定義すると、FirebaseでDatabaseException:Found conflicting getters for nameのエラーになる。
        //解決策は、別のpackageに移すこと：https://stackoverflow.com/questions/47767636/found-conflicting-getters-for-namedatabase-exception
        UserDTO user = new UserDTO(uid, username_edittext_register_text,age,gender,location,profile, profileImageUrl, filename, regionsetting);

        //addOnSuccessListenerは、帰ってくる引数がないときはVoid、それ以外は決められた変数を指定する？？
        ref.setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("RegisterActivity", "Finally we save the user to Firebase Databese");

                //登録に成功した場合は、LatestMessagesActivityに遷移する
                Intent intent = new Intent(getApplicationContext(), TopActivity.class);
                //この一文を記載することで、元のログイン画面に戻れないようにする
                intent.addFlags(FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    //ログイン画面に遷移するときの動き
    public class Already_have_an_acount_text_ClickListener implements View.OnClickListener {
        public void onClick(View view) {

            Intent LoginActivity = new Intent(getApplicationContext(), twentyfour_seconds.com.del.login.LoginActivity.class);
            startActivity(LoginActivity);
        }
    }
}


