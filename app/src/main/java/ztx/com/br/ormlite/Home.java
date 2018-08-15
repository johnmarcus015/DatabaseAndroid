package ztx.com.br.ormlite;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;
import ztx.com.br.ormlite.database.model.Usuario;
import ztx.com.br.ormlite.database.dao.UsuarioDAO;

public class Home extends AppCompatActivity {

    @BindView(R.id.btCadastro)
    Button btCadastro;
    @BindView(R.id.lVUsuarios)
    ListView lVUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        List<Usuario> usuarios = obterTodosUsuarios();
        List<String> nomes = criarListaNomes(usuarios);
        preencherListView(nomes);

    }

    private List<Usuario> obterTodosUsuarios() {
        UsuarioDAO dao = new UsuarioDAO(this);
        List<Usuario> usuarios = dao.listarUsuarios();
        dao.close();
        return usuarios;
    }

    @NonNull
    private List<String> criarListaNomes(List<Usuario> usuarios) {
        List<String> nomes = new ArrayList<>();
        for(Usuario u : usuarios){
            nomes.add(u.getNome());
        }
        return nomes;
    }

    private void preencherListView(List<String> nomes) {
        ArrayAdapter<String> adap = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nomes);
        lVUsuarios.setAdapter(adap);
    }

    @OnClick(R.id.btCadastro)
    void irParaCadastro(){
        startActivity(new Intent(this, PessoaCadastro.class));
    }

    @OnItemClick(R.id.lVUsuarios)
    void irParaEdicao(int position){
        String nome = (String) lVUsuarios.getItemAtPosition(position);
        UsuarioDAO dao = new UsuarioDAO(this);
        Usuario u = dao.buscarUsuarioPorNome(nome);
        dao.close();
        Intent i = new Intent(this, PessoaCadastro.class);
        i.putExtra("usuario", u);
        startActivity(i);
    }

    @OnItemLongClick(R.id.lVUsuarios)
    boolean confirmaRemocaoUsuario(final int position){
        new AlertDialog.Builder(this)
            .setTitle("Você tem certeza que deseja remover este usuario?")
            .setPositiveButton("Sim",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        deletarUsuario(position);
                    }
                }
            )
            .setNegativeButton("Não",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                }
            )
            .create()
            .show();
        return false;
    }

    private void deletarUsuario(int position) {
        String nome = (String) lVUsuarios.getItemAtPosition(position);
        UsuarioDAO dao = new UsuarioDAO(this);
        Usuario u = dao.buscarUsuarioPorNome(nome);
        dao.deletar(u.getId());
        dao.close();
        List<Usuario> usuarios = obterTodosUsuarios();
        List<String> nomes = criarListaNomes(usuarios);
        preencherListView(nomes);
        lVUsuarios.invalidateViews();
    }
}
