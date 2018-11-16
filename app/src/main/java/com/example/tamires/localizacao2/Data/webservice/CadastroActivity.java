package com.example.tamires.localizacao2.Data.webservice;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.tamires.localizacao2.Data.webservice.webservices.content.LocalizacoesSquidexInfo;
import com.example.tamires.localizacao2.Data.webservice.webservices.content.LocationIV;
import com.example.tamires.localizacao2.Data.webservice.webservices.content.LocationValue;
import com.example.tamires.localizacao2.R;
import com.example.tamires.localizacao2.Data.webservice.dialogs.PopupInformacao;
import com.example.tamires.localizacao2.Data.webservice.webservices.WebServiceControle;
import com.example.tamires.localizacao2.Data.webservice.webservices.content.Localizacao;
import com.example.tamires.localizacao2.Data.webservice.webservices.content.Item;
import com.example.tamires.localizacao2.Data.webservice.webservices.content.StringValue;

import org.json.JSONException;

import java.util.ArrayList;

public class CadastroActivity extends AppCompatActivity
{
    private TextInputLayout xxNome;
    private TextInputEditText edNome;

    private TextInputLayout xxEndereco;
    private TextInputEditText EdEndereco;

    private TextInputLayout xxTelefone;
    private TextInputEditText EdTelefone;

    private TextInputLayout xxLatitude;
    private TextInputEditText EdLatitude;

    private TextInputLayout xxLongitude;
    private TextInputEditText EdLongitude;

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

        xxLatitude = findViewById(R.id.xxLatitude);
        EdLatitude = findViewById(R.id.EdLatitude);

        xxLongitude = findViewById(R.id.xxLongitude);
        EdLongitude = findViewById(R.id.EdLongitude);

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

        EdLatitude.addTextChangedListener(new TextWatcher()
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
                xxLatitude.setError(null);
            }
        });

        EdLongitude.addTextChangedListener(new TextWatcher()
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
                xxLongitude.setError(null);
            }
        });

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

        //lista

        new WebServiceControle().carregaListaTipo(this, new WebServiceControle.CarregaTipoListener() {

            @Override
            public void onResultOk(LocalizacoesSquidexInfo tipos) {
                Locais = findViewById(R.id.Locais);

                ArrayList<String> arrayList = new ArrayList<String>();


                for (int i = 0; i < tipos.getItems().length; i++) {
                    Item item = tipos.getItems()[i];

                    arrayList.add(item.getData().getNomLocal().getIv());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(CadastroActivity.this, android.R.layout.simple_spinner_item, arrayList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Locais.setAdapter(adapter);
            }

            @Override
            public void onErro() {
                PopupInformacao.mostraMensagem(CadastroActivity.this, "Falha ao buscar os tipos de locais");
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

        if (EdLatitude.getText().toString().trim().length() == 0)
        {
            xxLatitude.setError("Informe a posição do Local");
            retorno = false;
        }

        if (EdLongitude.getText().toString().trim().length() == 0)
        {
            xxLongitude.setError("Informe a posição do Local");
            retorno = false;
        }

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

        localizacao.setTelLocal(new StringValue(EdTelefone.getText().toString()));

        localizacao.setDescLocal(new StringValue(EdDescricao.getText().toString()));

        localizacao.setTipoLocal(new StringValue(Locais.getSelectedItem().toString()));

//        localizacao.setPosicao(new LocationValue(new LocationIV(Double.parseDouble(EdLatitude.getText().toString()), Double.parseDouble(EdLongitude.getText().toString()))));

        localizacao.setLatitute(new StringValue(EdLatitude.getText().toString()));
        localizacao.setLongitude(new StringValue(EdLongitude.getText().toString()));

//        LocationValue posicao = new LocationValue();
//        LocationIV locationIV
//            =new LocationIV
//            (
//                    Double.parseDouble(EdLatitude.getText().toString()),
//                    Double.parseDouble(EdLongitude.getText().toString())
//            );
//        posicao.setIv(locationIV);
//        localizacao.setPosicao(posicao);
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
            edNome.setText(item.getData().getNomLocal() != null ? item.getData().getNomLocal().getIv() : "");

            EdEndereco.setText(item.getData().getEnderecoLocal() != null ?item.getData().getEnderecoLocal().getIv() : "");

            EdTelefone.setText(item.getData().getTelLocal() != null ?item.getData().getTelLocal().getIv() : "");

//            EdLatitude.setText(item.getData().getPosicao() != null ?String.valueOf(item.getData().getPosicao().getIv().getLatitude()) : "");
//
//            EdLongitude.setText(item.getData().getPosicao() != null ?String.valueOf(item.getData().getPosicao().getIv().getLongitude()) : "");

            EdLatitude.setText(item.getData().getLatitute()!= null ?String.valueOf(item.getData().getLatitute().getIv()) : "");

            EdLongitude.setText(item.getData().getLongitude()!= null ?String.valueOf(item.getData().getLongitude().getIv()) : "");

            EdDescricao.setText(item.getData().getDescLocal() != null ?item.getData().getDescLocal().getIv() : "");

            if(item.getData().getTipoLocal() != null)
            {
                for (int i = 1; i < Locais.getCount(); i++) {
                    if (((String) Locais.getItemAtPosition(i)).equals(item.getData().getTipoLocal().getIv())) {
                        Locais.setSelection(i, true);
                        break;
                    }
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