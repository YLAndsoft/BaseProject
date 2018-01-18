package fyl.base.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by DN on 2018/1/18.
 */

public class SqliteHelper extends SQLiteOpenHelper {
    private String createTable;
    private String tableName;

    public SqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, String createTable, String tableName) {
        super(context, name, factory, version);
        this.createTable=createTable;
        this.tableName=tableName;

    }
    /**
     * 创建表
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        if(createTable!=""){
            db.execSQL(createTable);
        }

    }
    /**
     * 更新表
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( "DROP TABLE IF EXISTS " + tableName );
        onCreate(db);
    }
    /**
     * 更新列
     * @param db
     * @param oldColumn
     * @param newColumn
     * @param typeColumn
     */
    public void updateColumn(SQLiteDatabase db, String oldColumn, String newColumn, String typeColumn){
        try{
            db.execSQL( "ALTER TABLE " +
                    tableName + " CHANGE " +
                    oldColumn + " "+ newColumn +
                    " " + typeColumn
            );
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
