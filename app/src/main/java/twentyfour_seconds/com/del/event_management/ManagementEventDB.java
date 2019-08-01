package twentyfour_seconds.com.del.event_management;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ManagementEventDB extends SQLiteOpenHelper {
	/**
	 * データベースファイル名の定数フィールド。
	 */
	private static final String DATABASE_NAME = "management_event.db";
	/**
	 * バージョン情報の定数フィールド。
	 */
	private static final int DATABASE_VERSION = 1;

	/**
	 * コンストラクタ。
	 */
	public ManagementEventDB(Context context) {
		//親クラスのコンストラクタの呼び出し。
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//テーブル作成用SQL文字列の作成。
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE management_event (");
		sb.append("id INTEGER PRIMARY KEY,");
		sb.append("image TEXT,");
		sb.append("title TEXT,");
		sb.append("name TEXT,");
		sb.append("area TEXT,");
		sb.append("local TEXT,");
		sb.append("date date,");
		sb.append("term TEXT,");
		sb.append("deadline TEXT,");
		sb.append("current_num INTEGER,");
		sb.append("sum INTEGER,");
		sb.append("comment TEXT");
		sb.append(");");
		String sql = sb.toString();

		//SQLの実行。
		db.execSQL(sql);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
}
