package ztx.com.br.ormlite.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class SQLiteHelper extends SQLiteOpenHelper {

    private String[] scriptSQLCreate;
    private String scriptSQLDelete;

    public SQLiteHelper(Context ctx, String databaseName, int databaseVersion, String[] scriptDatabaseCreate, String scriptDatabaseDelete) {
        super(ctx, databaseName, null, databaseVersion);
        this.scriptSQLCreate = scriptDatabaseCreate;
        this.scriptSQLDelete = scriptDatabaseDelete;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for(String sql : scriptSQLCreate){
            db.execSQL(sql);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(scriptSQLDelete);
        onCreate(db);
    }
}
