package com.example.tamires.localizacao2.Data.webservice;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Spinner;

import com.example.tamires.localizacao2.R;
import com.example.tamires.localizacao2.Data.webservice.dialogs.PopupInformacao;
import com.example.tamires.localizacao2.Data.webservice.webservices.WebServiceControle;
import com.example.tamires.localizacao2.Data.webservice.webservices.content.Localizacao;
import com.example.tamires.localizacao2.Data.webservice.webservices.content.Item;
import com.example.tamires.localizacao2.Data.webservice.webservices.content.StringValue;

import org.json.JSONException;

public class CadastroActivity extends AppCompatActivity
{
    private TextInputLayout xxNome;
    private TextInputEditText edNome;

    private TextInputLayout xxEndereco;
    private TextInputEditText EdEndereco;

    private TextInputLayout xxTelefone;
    private TextInputEditText EdTelefone;

//    private TextInputLayout xxPosicao;
//    private TextInputEditText EdPosicao;

    private TextInputLayout xxDescricao;
    private TextInputEditText EdDescricao;

    private Spinner Locais;
    //
    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrolocalizacao);
        inicializaComponentes();
        //
        item = (Item) getIntent().getSerializableExtra(ListagemActivity.EXTRA_REGISTRO);
        //
        carregaValores();
    }

    private void inicializaComponentes()
    {
        xxNome = findViewById(R.id.xxNome);
        edNome = findViewById(R.id.edNome);
        xxEndereco = findViewById(R.id.xxEndereco);
        EdEndereco = findViewById(R.id.EdEndereco);
        xxTelefone = findViewById(R.id.xxTelefone);
        EdTelefone = findViewById(R.id.EdTelefone);
//        xxPosicao = findViewById(R.id.xxPosicao);
//        EdPosicao = findViewById(R.id.EdPosicao);
        xxDescricao = findViewById(R.id.xxDescricao);
        EdDescricao = findViewById(R.id.EdDescricao);

        Locais = findViewById(R.id.Locais);
        FloatingActionButton fabConfirmar = findViewById(R.id.fabConfirmar);
        FloatingActionButton fabDeletar = findViewById(R.id.fabDeletar);
        //
        edNome.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                xxNome.setError(null);
            }
        });
        EdEndereco.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                xxEndereco.setError(null);
            }
        });

        EdTelefone.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                xxTelefone.setError(null);
            }
        });

//        EdPosicao.addTextChangedListener(new TextWatcher()
//        {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after)
//            {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count)
//            {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s)
//            {
//                xxPosicao.setError(null);
//            }
//        });

        EdDescricao.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                xxDescricao.setError(null);
            }
        });
        //
        fabConfirmar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                confirmaTela();
            }
        });
        //
        fabDeletar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                deletaRegistro();
            }
        });
    }

    private boolean validaTela()
    {
        boolean retorno = true;
        //
        if (edNome.getText().toString().trim().length() == 0)
        {
            xxNome.setError("Informe o nome do Local");
            retorno = false;
        }
        //
        if (EdEndereco.getText().toString().trim().length() == 0)
        {
            xxEndereco.setError("Informe o Endereço do Local");
            retorno = false;
        }
        //
        if (EdTelefone.getText().toString().trim().length() == 0)
        {
            xxTelefone.setError("Informe o Telefone do Local");
            retorno = false;
        }
        //
//        if (EdPosicao.getText().toString().trim().length() == 0)
//        {
//            xxPosicao.setError("Informe a posição do Local");
//            retorno = false;
//        }
        //
        if (EdDescricao.getText().toString().trim().length() == 0)
        {
            xxDescricao.setError("Informe a descrição do Local");
            retorno = false;
        }
        //
        if (Locais.getSelectedItemPosition() <= 0)
        {
            PopupInformacao.mostraMensagem(this, "Selecione o tipo de local");
            retorno = false;
        }

        return retorno;
    }

    private void confirmaTela()
    {
        if (!validaTela())
            return;

        salvaRegistro();
    }

    private void salvaRegistro()
    {
        Localizacao localizacao = new Localizacao();
        localizacao.setNomLocal(new StringValue(edNome.getText().toString()));
        localizacao.setEnderecoLocal(new StringValue(EdEndereco.getText().toString()));
        localizacao.setEnderecoLocal(new StringValue(EdTelefone.getText().toString()));
//        localizacao.setEnderecoLocal(new StringValue(EdPosicao.getText().toString()));
        localizacao.setEnderecoLocal(new StringValue(EdDescricao.getText().toString()));
        localizacao.setTipoLocal(new StringValue(Locais.getSelectedItem().toString()));
        //
        try
        {
            if(item == null)
            {
                new WebServiceControle().criaLocalizacao(this, localizacao, new WebServiceControle.UpdateLocalizacaoListener()
                {
                    @Override
                    public void onResultOk()
                    {
                        CadastroActivity.this.finish();
                    }

                    @Override
                    public void onErro()
                    {
                        PopupInformacao.mostraMensagem(CadastroActivity.this, "Falha ao cadastrar");
                    }
                });
            }
            else
            {
                new WebServiceControle().atualizaLocalizacao(this, localizacao, item.getId(), new WebServiceControle.UpdateLocalizacaoListener()
                {
                    @Override
                    public void onResultOk()
                    {
                        CadastroActivity.this.finish();
                    }

                    @Override
                    public void onErro()
                    {
                        PopupInformacao.mostraMensagem(CadastroActivity.this, "Falha ao atualizar cadastro");
                    }
                });
            }
        }
        catch (JSONException e)
        {
            PopupInformacao.mostraMensagem(CadastroActivity.this, "Falha ao salvar registro");
        }
        //

    }

    private void carregaValores()
    {
        if(item != null)
        {
            edNome.setText(item.getLocalizacao().getNomLocal().getIv());
            EdEndereco.setText(item.getLocalizacao().getEnderecoLocal().getIv());
            EdTelefone.setText(item.getLocalizacao().getTelLocal().getIv());
//            EdPosicao.setText(item.getLocalizacao().getPosicao().getIv());
            EdDescricao.setText(item.getLocalizacao().getDescLocal().getIv());
            for (int i = 1; i < Locais.getCount(); i++)
            {
                if (((String) Locais.getItemAtPosition(i)).equals(item.getLocalizacao().getTipoLocal().getIv()))
                {
                    Locais.setSelection(i, true);
                    break;
                }
            }
        }
    }

    private void deletaRegistro()
    {
        if(item != null)
        {
            new WebServiceControle().deletaLocalizacao(this, item.getId(), new WebServiceControle.UpdateLocalizacaoListener()
            {
                @Override
                public void onResultOk()
                {
                    CadastroActivity.this.finish();
                }

                @Override
                public void onErro()
                {
                    PopupInformacao.mostraMensagem(CadastroActivity.this, "Falha ao deletar registro");
                }
            });
        }
    }

}
