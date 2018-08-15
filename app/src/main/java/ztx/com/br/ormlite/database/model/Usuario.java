package ztx.com.br.ormlite.database.model;

import java.io.Serializable;

public class Usuario implements Serializable {

    public static String[] colunas = new String[]{Usuario._ID, Usuario.NOME, Usuario.EMAIL, Usuario.TELEFONE};

    public static final String TABLE = "usuario";
    public static final String _ID = "_id";
    public static final String NOME = "nome";
    public static final String EMAIL = "email";
    public static final String TELEFONE = "telefone";

    private long id;
    private String nome;
    private String email;
    private String telefone;

    public Usuario() {
    }

    public Usuario(long id, String nome, String email, String telefone) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

    public Usuario(String nome, String email, String telefone) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
