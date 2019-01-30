package twentyfour_seconds.com.del;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Eventkanriflagment extends Fragment {

    //コンストラクタ
    public Eventkanriflagment() {
    }

    @Nullable
    @Override
    // Fragmentで表示するViewを作成するメソッド
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 先ほどのレイアウトをここでViewとして作成します
        return inflater.inflate(R.layout.eventkanri, container, false);
    }

    @Override
    // Viewが生成し終わった時に呼ばれるメソッド
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}