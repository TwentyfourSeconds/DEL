package twentyfour_seconds.com.del.login;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import twentyfour_seconds.com.del.R;
import twentyfour_seconds.com.del.top_page.TopActivity;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        //Loginボタンを押下時
        Button login_button = findViewById(R.id.login_button);
        login_button_buttonClickListener login_button_buttonClickListener = new login_button_buttonClickListener();
        login_button.setOnClickListener(login_button_buttonClickListener);

        //Back to registrationをクリック
        TextView back_to_registration = findViewById(R.id.back_to_register_text);
        back_to_registration_textClickListener back_to_registration_textClickListener = new back_to_registration_textClickListener();
        back_to_registration.setOnClickListener(back_to_registration_textClickListener);

    }

    //REGISTERボタンをクリックしたときの処理
    private class login_button_buttonClickListener implements View.OnClickListener {
        public void onClick(View view) {

            TextView email_edittext_login = findViewById(R.id.Email_Edittext_login);
            String email = email_edittext_login.getText().toString();
            TextView password_edittext_login = findViewById(R.id.Password_Edittext_login);
            String password = password_edittext_login.getText().toString();

            Log.d("Login", "Attempt login with email/pw:" + email);

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("Firebase Sign in success", "LoginUserWithEmail:success");

                        //登録に成功した場合は、LatestMessagesActivityに遷移する
                        Intent intent = new Intent(getApplicationContext(), TopActivity.class);
                        //この一文を記載することで、元のログイン画面に戻れないようにする
                        intent.addFlags(FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.d("Firebase Sign in failure", "LoginWithEmail:failure", task.getException());
                    }
                }
            });
        }
    }



    //REGISTERボタンをクリックしたときの処理
    private class back_to_registration_textClickListener implements View.OnClickListener {
        public void onClick(View view) {

            //この画面を終了し、前のログイン画面に戻る
//            LoginActivity.finish();
        }
    }
}