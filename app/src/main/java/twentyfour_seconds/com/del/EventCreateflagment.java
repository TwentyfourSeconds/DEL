package twentyfour_seconds.com.del;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class EventCreateflagment extends Fragment implements View.OnClickListener {

    //コンストラクタ
    public EventCreateflagment() {
    }

    @Nullable
    @Override
    // Fragmentで表示するViewを作成するメソッド（戻り値View）
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 先ほどのレイアウトをここでViewとして作成します
         View view = inflater.inflate(R.layout.eventcreate, container, false);
        return view;
    }

    @Override
    // Viewが生成し終わった時に呼ばれるメソッド（戻り値なし）
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //下部メニューボタンを押下したときの処理を記載
        ImageView menu_bar_home = view.findViewById(R.id.mypage_tab).findViewById(R.id.menu_bar_home);
        ImageView menu_bar_event = view.findViewById(R.id.mypage_tab).findViewById(R.id.menu_bar_event);
        ImageView menu_bar_chat = view.findViewById(R.id.mypage_tab).findViewById(R.id.menu_bar_chat);
        ImageView menu_bar_mypage = view.findViewById(R.id.mypage_tab).findViewById(R.id.menu_bar_mypage);

        menu_bar_home.setOnClickListener(this);
        menu_bar_event.setOnClickListener(this);
        menu_bar_chat.setOnClickListener(this);
        menu_bar_mypage.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch(id){
            case R.id.menu_bar_home:
                //home画面へと飛ぶ処理
                Intent intentHome = new Intent(getActivity(), EventCreateflagment.class);
                startActivity(intentHome);
                break;
            case R.id.menu_bar_event:
                //イベント作成画面へと飛ぶ処理
                Intent intentEvent = new Intent(getActivity(), EventTabcontrol_main.class);
                startActivity(intentEvent);
                break;
            case R.id.menu_bar_chat:
                //チャット画面へと飛ぶ処理
                Intent intentchat = new Intent(getActivity(), ChatDB.class);
                startActivity(intentchat);
                break;
            case R.id.menu_bar_mypage:
                //マイページ画面へと飛ぶ処理
                Intent intentMypage = new Intent(getActivity(), MyPageActivity.class);
                startActivity(intentMypage);
                break;
        }
    }




}
