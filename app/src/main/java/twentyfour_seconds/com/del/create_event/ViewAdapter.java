package twentyfour_seconds.com.del.create_event;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import twentyfour_seconds.com.del.DTO.ViewItemDTO;
import twentyfour_seconds.com.del.R;
import twentyfour_seconds.com.del.util.RecyclerListViewHolder;

//ViewAdapterクラス
//概要：リサイクラービューでは、リストビューと異なり、自分でアダプターを作成しなければいけない。
// 　　 ここでは、ViewAdapterクラスを作成し、作成したアダプタークラスに、データを割り当てる。

public class ViewAdapter extends RecyclerView.Adapter<RecyclerListViewHolder> {              //ここでは、作成したビューホルダクラスを指定する

    private List<ViewItemDTO> itemDtoList;

    //Activityで文言の判別に使用する処理
    public String reserchWord;

    //このコンストラクタの意味は、引数として渡されてきた値(itemDtoList)をこのメソッドのitemDtoListに渡している。
    //itemDtoListの実際の値は、EventCreate3の変数ret
    //参考資料：https://qiita.com/lrf141/items/05c4f3dc7c319d73ca28

    public ViewAdapter(List<ViewItemDTO> itemDtoList) {                                        //
        this.itemDtoList = itemDtoList;
    }

    //ステップ①　最初に呼ばれるメソッド
    //レイアウトXMLを元に、ビューホルダを生成する処理を行い、生成したViewHolderを
    //RecyclerViewに返す。（戻り値：RecyclerListViewHolder）

    @Override
    //-----------------------レイアウト　部品　作成箇所---------------------------------//
    public RecyclerListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ////レイアウトxmlから、Viewオブジェクトを作成
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.activity_flexbox_layout_recycler_view_item, parent, false);    //activity_flexbox_layout_recycler_view_item（XMLレイアウト）をinflateし、一行分の画面部品とする。

        //タッチイベント
        //activity_flexbox_layout_recycler_view_item（XMLレイアウト）の内部部品であるflex_box_recycler_view_text_item(テキストビュー)を取得
        final TextView textItem = (TextView)itemView.findViewById(R.id.inviteTag);

        //ビューホルダーオブジェクトを生成（一番下で変数をreturnすることで、ViewHolderに処理が飛ぶ）
        final RecyclerListViewHolder ret = new RecyclerListViewHolder(textItem);
//        final RecyclerListViewHolder ret = new RecyclerListViewHolder(itemView);

        //削除のタッチ処理を記載
        //Activityの呼び出しかた：https://calculus-app.com/blog/develop_android/android_ui/306
        Context context = itemView.getContext();

        //※ここでinflateをしてはいけない。同じレイアウトを２つ定義していることになってしまう。
//        View itemViewparent = inflater.inflate(R.layout.eventcreate3, parent, false);    //activity_flexbox_layout_recycler_view_item（XMLレイアウト）をinflateし、一行分の画面部品とする。
        //↓このようにcontextからViewを呼ばなければいけない。(参考)https://stackoverflow.com/questions/13114966/android-how-to-get-view-from-context
        final TextView tukue = (TextView) ((Activity) context).findViewById(R.id.tukueType);

        final TextView  missitu = (TextView)((Activity) context).findViewById(R.id.missituType);
        final TextView  city = (TextView)((Activity) context).findViewById(R.id.cityType);
        final TextView  outField = (TextView)((Activity) context).findViewById(R.id.outFieldType);
        final TextView  shortTime = (TextView)((Activity) context).findViewById(R.id.shortTimeType);
        final TextView  anime = (TextView)((Activity) context).findViewById(R.id.animeType);

        //インフレートされた1行分の画面部品にリスナを設定。
        textItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = ret.getAdapterPosition(); // positionを取得
                Log.i("position", position + "");
                //位置情報を取得
                final ViewItemDTO data;
                data = itemDtoList.get(position);
                //削除処理を実施
                removeFromDataset(data);
                //取得したデータの文字列を取得
                reserchWord = data.getText();

                //取得したデータの文字列に合わせて、部品を再活性化
                switch (reserchWord) {
                    case "－机に座ってガッツリと":
                        tukue.setEnabled(true);
                        tukue.setTextColor(Color.BLACK);
                        break;
                    case "－密室からの脱出":
                        missitu.setEnabled(true);
                        missitu.setTextColor(Color.BLACK);
                        break;
                    case "－街を歩き回って":
                        city.setEnabled(true);
                        city.setTextColor(Color.BLACK);
                        break;
                    case "－遊園地や野球場で":
                        outField.setEnabled(true);
                        outField.setTextColor(Color.BLACK);
                        break;
                    case "－短い時間で気軽に":
                        shortTime.setEnabled(true);
                        shortTime.setTextColor(Color.BLACK);
                        break;
                    case "－アニメタイアップ":
                        anime.setEnabled(true);
                        anime.setTextColor(Color.BLACK);
                        break;
                }
            }
        });
        return ret;
    }

    //ステップ②　onCreateViewHolderの次に呼ばれるメソッド
    //ここでデータの紐づけを行う。
    //-----------------------データ　設定　作成箇所---------------------------------//
    @Override
    public void onBindViewHolder(RecyclerListViewHolder holder, int position) {

        //渡された引数positionに該当する、リストからリストデータ一行分のデータを取得
        ViewItemDTO itemDto = itemDtoList.get(position);
        //一行分の部品(activity_flexbox_layout_recycler_view_item)のテキスト部品(flex_box_recycler_view_text_itemをtextItemとして、上で定義）)
        holder.textItem.setText(itemDto.getText());
//        holder.getTextItem().setText(itemDto.getText());　　　　　　　　//ここでViewHolderのgetTextItem()メソッドを使用して、データを設定している（上でそのまま設定するようにした）
        //削除対象のデータを取得するための変数(data)
//        final ViewItemDTO data;
//        //タッチした位置のポジションを取得
//        data = itemDtoList.get(position);
//        holder.textItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //選択したデータを削除する
//                removeFromDataset(data);
//            }
//        });
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

    //データを削除
    protected void removeFromDataset(ViewItemDTO data){
        for(int i=0; i<itemDtoList.size(); i++){
            //渡された削除するデータ(変数data)と同じ文字列を配列内から検索し、ヒットしたらば、それを削除する
            if(itemDtoList.get(i).equals(data)){
                itemDtoList.remove(i);
                notifyItemRemoved(i);
                break;
            }
        }
    }
}