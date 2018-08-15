package ztx.com.br.ormlite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ztx.com.br.ormlite.database.model.Usuario;
import ztx.com.br.ormlite.database.dao.UsuarioDAO;

public class PessoaCadastro extends AppCompatActivity {

    @BindView(R.id.eTNome)
    EditText eTNome;
    @BindView(R.id.eTEmail)
    EditText eTEmail;
    @BindView(R.id.eTTelefone)
    EditText eTTelefone;
    @BindView(R.id.btSalvar)
    Button btSalvar;

    private Usuario u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoa_cadastro);
        ButterKnife.bind(this);

        u = (Usuario) getIntent().getSerializableExtra("usuario");
        if(u != null) convertUsuarioToForm(u);

    }

    @OnClick(R.id.btSalvar)
    public void salvarUsuario(){
        UsuarioDAO dao = new UsuarioDAO(this);
        dao.salvarOuAtualizar(convertFormToUsuario());
        dao.close();
        irParaHome();
    }

    private Usuario convertFormToUsuario(){
        String nome = eTNome.getText().toString();
        String email = eTEmail.getText().toString();
        String telefone = eTTelefone.getText().toString();
        if (u != null)
            return new Usuario(u.getId(), nome, email, telefone);
        else
            return new Usuario(nome, email, telefone);
    }

    private void convertUsuarioToForm(Usuario u){
        eTNome.setText(u.getNome());
        eTEmail.setText(u.getEmail());
        eTTelefone.setText(u.getTelefone());
    }

    private void irParaHome(){
        Intent i = new Intent(this, Home.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }
}
