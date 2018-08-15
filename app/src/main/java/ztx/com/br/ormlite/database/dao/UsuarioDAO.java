package ztx.com.br.ormlite.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ztx.com.br.ormlite.database.Database;
import ztx.com.br.ormlite.database.model.Usuario;

public class UsuarioDAO {

    protected SQLiteDatabase db;

    public UsuarioDAO(Context ctx){
        db = new Database(ctx).open();
    }

    public long salvarOuAtualizar(Usuario u){
        long id = u.getId();
        if(id != 0){
            atualizar(u);
        } else {
            id = inserir(u);
        }
        return id;
    }

    public long inserir(Usuario u){
        ContentValues values = new ContentValues();
        values.put(Usuario.NOME, u.getNome());
        values.put(Usuario.EMAIL, u.getEmail());
        values.put(Usuario.TELEFONE, u.getTelefone());
        long id = db.insert(Usuario.TABLE, "", values);
        return id;
    }

    public int atualizar(Usuario u){
        ContentValues values = new ContentValues();
        values.put(Usuario.NOME, u.getNome());
        values.put(Usuario.EMAIL, u.getEmail());
        values.put(Usuario.TELEFONE, u.getTelefone());
        String _id = String.valueOf(u.getId());
        String where = Usuario._ID+"=?";
        String[] whereArgs = new String[]{_id};
        int count = db.update(Usuario.TABLE, values, where, whereArgs);
        return count;
    }

    public int deletar(long id){
        String where = Usuario._ID+"=?";
        String _id = String.valueOf(id);
        String[] whereArgs = new String[]{ _id };
        int count = db.delete(Usuario.TABLE, where, whereArgs);
        return count;
    }

    public Usuario buscarUsuario(long id){
        Cursor c = db.query(true, Usuario.TABLE, Usuario.colunas, Usuario._ID+"="+id, null, null, null,  null, null);
        if(c.getCount() > 0){
            c.moveToFirst();
            Usuario u = new Usuario();
            u.setId(c.getLong(0));
            u.setNome(c.getString(1));
            u.setEmail(c.getString(2));
            u.setTelefone(c.getString(3));
            return u;
        }
        return null;
    }

    //Retorna um cursor com todos os usuarios
    public Cursor getCursor(){
        try {
            return db.query(Usuario.TABLE, Usuario.colunas, null, null, null, null, null, null);
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public List<Usuario> listarUsuarios(){
        Cursor c = getCursor();
        List<Usuario> usuarios = new ArrayList<>();
        if (c.moveToFirst()) {
            //Recupera o indice das colunas
            int idxId = c.getColumnIndex(Usuario._ID);
            int idxNome = c.getColumnIndex(Usuario.NOME);
            int idxEmail = c.getColumnIndex(Usuario.EMAIL);
            int idxTelefone = c.getColumnIndex(Usuario.TELEFONE);
            do {
                Usuario u = new Usuario();
                u.setId(c.getLong(idxId));
                u.setNome(c.getString(idxNome));
                u.setEmail(c.getString(idxEmail));
                u.setTelefone(c.getString(idxTelefone));
                usuarios.add(u);
            } while(c.moveToNext());
        }
        return usuarios;
    }

    public Usuario buscarUsuarioPorNome(String nome){
        Usuario u = null;
        try {
            Cursor c = db.query(Usuario.TABLE, Usuario.colunas, Usuario.NOME+"='"+nome+"'", null, null, null, null);
            if(c.moveToNext()){
                u = new Usuario();
                u.setId(c.getLong(0));
                u.setNome(c.getString(1));
                u.setEmail(c.getString(2));
                u.setTelefone(c.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return u;
    }

    public void close(){
        db.close();
    }

}
