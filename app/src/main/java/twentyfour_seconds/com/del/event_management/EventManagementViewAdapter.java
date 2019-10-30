package twentyfour_seconds.com.del.event_management;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Map;

import twentyfour_seconds.com.del.R;

//ViewAdapterクラス
//概要：リサイクラービューでは、リストビューと異なり、自分でアダプターを作成しなければいけない。
// 　　 ここでは、ViewAdapterクラスを作成し、作成したアダプタークラスに、データを割り当てる。

public class EventManagementViewAdapter extends RecyclerView.Adapter<EventManagementViewHolder> {              //ここでは、作成したビューホルダクラスを指定する

    private List<Map<String, Object>> messageList;

    //コンストラクタに引き継いできた値を設定
    public EventManagementViewAdapter(List<Map<String, Object>> messageList) {
        this.messageList = messageList;
    }

    //ステップ①　最初に呼ばれるメソッド
    //レイアウトXMLを元に、ビューホルダを生成する処理を行い、生成したViewHolderを
    //RecyclerViewに返す。（戻り値：RecyclerListViewHolder）

    @Override
    //-----------------------レイアウト　部品　作成箇所---------------------------------//
    public EventManagementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ////レイアウトxmlから、Viewオブジェクトを作成
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.row_event_management, parent, false);    //activity_flexbox_layout_recycler_view_item（XMLレイアウト）をinflateし、一行分の画面部品とする。

        //タッチイベント
        //row_event_management_backgroung（XMLレイアウト）の内部部品であるcardViewを取得
        final CardView cardView = itemView.findViewById(R.id.cardView);

        //ビューホルダーオブジェクトを生成（一番下で変数をreturnすることで、ViewHolderに処理が飛ぶ）
        final EventManagementViewHolder EventManagementViewHolderRet = new EventManagementViewHolder(itemView);

        return EventManagementViewHolderRet;
    }

    //ステップ②　onCreateViewHolderの次に呼ばれるメソッド
    //ここでデータの紐づけを行う。
    //-----------------------データ　設定　作成箇所---------------------------------//
    @Override
    public void onBindViewHolder(EventManagementViewHolder holder, int position) {

        //渡された引数positionに該当する、リストからリストデータ一行分のMapデータを取得
        Map<String, Object> message = messageList.get(position);
        //一行分の部品(activity_flexbox_layout_recycler_view_item)のテキスト部品(flex_box_recycler_view_text_itemをtextItemとして、上で定義）

//        holder.textItem.setText(message.get("image").toString());
        holder.event_name.setText(message.get("title").toString());
        holder.area.setText(message.get("area").toString());
        holder.place.setText(message.get("local").toString());
        holder.event_day.setText(message.get("term").toString());
        holder.eventstatus.setText("参加中");
//        holder.eventstatus.setText(message.get("eventstatus").toString());
//        holder.textItem.setText(message.get("member").toString());

    }

    @Override
    //③ リストデータ中の件数をリターン。 (layout managerから呼ばれる)
    public int getItemCount() {
        int ret = 0;
        if(this.messageList!=null)
        {
            ret = messageList.size();
        }
        return ret;
    }
}
