package ztx.com.br.ormlite.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class Database {

    private static final String SCRIPT_DATABASE_DELETE = "DROP TABLE IF EXISTS usuario";
    private static final String[] SCRIPT_DATABASE_CREATE = new String[]{
        "create table usuario (_id integer primary key autoincrement, nome text not null, email text not null, telefone email text not null);",
        "insert into usuario (nome, email, telefone) values ('Employer', 'employer@gmail.com', '37954369870');",
        "insert into usuario (nome, email, telefone) values ('Administrador', 'admin@gmail.com', '37988889999');",
        "insert into usuario (nome, email, telefone) values ('Manager', 'manager@gmail.com', '37987875412');",
        "insert into usuario (nome, email, telefone) values ('Customer', 'customer@gmail.com', '37965342312');"
    };
    private static final String DATABASE_NAME = "ormlite";
    private static final int DATABASE_VERSION = 1;
    private SQLiteHelper dbHelper;
    private SQLiteDatabase db;

    public Database(Context ctx){
        dbHelper = new SQLiteHelper(ctx, DATABASE_NAME, DATABASE_VERSION, SCRIPT_DATABASE_CREATE, SCRIPT_DATABASE_DELETE);
        db = dbHelper.getWritableDatabase();
    }

    public SQLiteDatabase open() {
        return db;
    }

    public void close(){
        if(db != null){
            db.close();
        }
    }
}
