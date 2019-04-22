package twentyfour_seconds.com.del;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ViewAdapterReadOnly extends RecyclerView.Adapter<RecyclerListViewHolder>  {

    private List<ViewItemDTO> itemDtoList;

    //Activityで文言の判別に使用する処理
    public String reserchWord;

    //このコンストラクタの意味は、引数として渡されてきた値(itemDtoList)をこのメソッドのitemDtoListに渡している。
    //itemDtoListの実際の値は、EventCreate3の変数ret
    //参考資料：https://qiita.com/lrf141/items/05c4f3dc7c319d73ca28

    public ViewAdapterReadOnly(List<ViewItemDTO> itemDtoList) {                                        //
        this.itemDtoList = itemDtoList;
    }

    public RecyclerListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ////レイアウトxmlから、Viewオブジェクトを作成
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.activity_flexbox_layout_recycler_view_item, parent, false);    //activity_flexbox_layout_recycler_view_item（XMLレイアウト）をinflateし、一行分の画面部品とする。

        //タッチイベント
        //activity_flexbox_layout_recycler_view_item（XMLレイアウト）の内部部品であるflex_box_recycler_view_text_item(テキストビュー)を取得
        final TextView textItem = (TextView) itemView.findViewById(R.id.inviteTag);

        //ビューホルダーオブジェクトを生成（一番下で変数をreturnすることで、ViewHolderに処理が飛ぶ）
        final RecyclerListViewHolder ret = new RecyclerListViewHolder(itemView);

        return ret;
    }


    public void onBindViewHolder(RecyclerListViewHolder holder, int position) {

        //渡された引数positionに該当する、リストからリストデータ一行分のデータを取得
        ViewItemDTO itemDto = itemDtoList.get(position);
        //一行分の部品(activity_flexbox_layout_recycler_view_item)のテキスト部品(flex_box_recycler_view_text_itemをtextItemとして、上で定義）)
        holder.textItem.setText(itemDto.getText());
    }

    @Override
    //③ リストデータ中の件数をリターン。 (layout managerから呼ばれる)
    public int getItemCount() {
        int ret = 0;
        if(this.itemDtoList!=null)
        {
            ret = itemDtoList.size();
        }
        return ret;
    }

}
