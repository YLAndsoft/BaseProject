package pro.base.com.baseproject.demo5.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by DN on 2018/2/10.
 */

public class DbOpenHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static DbOpenHelper chatDBHelper;

    private DbOpenHelper(Context context) {
        super(context, DBConstant.DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DbOpenHelper getInstance(Context context)
    {
        if(chatDBHelper==null)
        {
            chatDBHelper = new DbOpenHelper(context.getApplicationContext());
        }
        return chatDBHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.beginTransaction();
            db.execSQL(DBConstant.CREATETABLE_CHAT.toString());
            db.setTransactionSuccessful();
        } finally{
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
