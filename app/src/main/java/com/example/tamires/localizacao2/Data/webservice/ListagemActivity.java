package com.example.tamires.localizacao2.Data.webservice;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tamires.localizacao2.Data.webservice.webservices.content.Item;
import com.example.tamires.localizacao2.R;
import com.example.tamires.localizacao2.Data.webservice.webservices.WebServiceControle;
import com.example.tamires.localizacao2.Data.webservice.webservices.content.LocalizacoesSquidexInfo;

public class ListagemActivity extends AppCompatActivity
{

    public static final String EXTRA_REGISTRO = "com.example.tamires.localizacao2.Data.webservice.ListagemActivity.EXTRA_REGISTRO";
    private ListView lvLocalizacao;
    private SwipeRefreshLayout srLocalizacao;
    private FloatingActionButton fabConfirmar;
    private FloatingActionButton fabMapa;
    private LocalizacoesSquidexInfo listLocalizacoes;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem);
        //
        inicializaComponenetes();
        criaAdapterLista();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        carregaListaLocalizacao();
    }

    private void inicializaComponenetes()
    {
        lvLocalizacao = findViewById(R.id.lvLocalizacao);
        srLocalizacao = findViewById(R.id.srLocalizacao);
        fabConfirmar = findViewById(R.id.fabConfirmar);
        fabMapa = findViewById(R.id.fabMapa);
        srLocalizacao.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {

                carregaListaLocalizacao();
            }
        });
        //
        fabConfirmar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(ListagemActivity.this, CadastroActivity.class));
            }
        });

        //abrindo activity maps
        fabMapa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(ListagemActivity.this, MapsActivity.class);
                startActivity(it);
            }
        });

        //
        lvLocalizacao.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Item item = listLocalizacoes.getItems()[position];
                Intent intent = new Intent(ListagemActivity.this, CadastroActivity.class);
                intent.putExtra(EXTRA_REGISTRO, item);
                startActivity(intent);
            }
        });
    }

    private void criaAdapterLista()
    {
        lvLocalizacao.setAdapter(new ArrayAdapter<Object>(this, 0)
                                 {
                                     class ViewHolder
                                     {
                                         TextView tvNome;
                                         TextView tvEndereco;
                                         TextView tvTelefone;
                                         TextView Locais;
                                         TextView tvDescricao;
                                     }

                                     @Override
                                     public int getCount()
                                     {
                                         if (listLocalizacoes != null)
                                             return listLocalizacoes.getTotal().intValue();
                                         else
                                             return 0;
                                     }

                                     @NonNull
                                     @Override
                                     public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
                                     {
                                         ViewHolder viewHolder;
                                         Item item = listLocalizacoes.getItems()[position];
                                         //
                                         if (convertView == null)
                                         {
                                             convertView = getLayoutInflater().inflate(R.layout.item_listagem, null);
                                             viewHolder = new ViewHolder();
                                             convertView.setTag(viewHolder);
                                             //
                                             viewHolder.tvNome = convertView.findViewById(R.id.tvNome);
                                             viewHolder.tvEndereco = convertView.findViewById(R.id.tvEndereco);
                                             viewHolder.tvTelefone = convertView.findViewById(R.id.tvTelefone);
                                             viewHolder.Locais = convertView.findViewById(R.id.Locais);
                                             viewHolder.tvDescricao = convertView.findViewById(R.id.tvDescricao);
                                         }
                                         else
                                             viewHolder = (ViewHolder) convertView.getTag();
                                         //
                                         viewHolder.tvNome.setText(item.getData().getNomLocal() != null ? item.getData().getNomLocal().getIv() : "");
                                         viewHolder.tvEndereco.setText(item.getData().getEnderecoLocal() != null ? item.getData().getEnderecoLocal().getIv() : "");
                                         viewHolder.tvTelefone.setText(item.getData().getTelLocal() != null ? item.getData().getTelLocal().getIv() : "");
                                         viewHolder.Locais.setText(item.getData().getTipoLocal() != null ? item.getData().getTipoLocal().getIv() : "");
                                         viewHolder.tvDescricao.setText(item.getData().getDescLocal() != null ? item.getData().getDescLocal().getIv() : "");
                                         //
                                         return convertView;
                                     }
                                 }
        );
    }

    private void carregaListaLocalizacao()
    {
        srLocalizacao.setRefreshing(true);
        new WebServiceControle().carregaListaLocalizacoes(this, new WebServiceControle.CarregaListaLocalizacoesListener()
        {
            @Override
            public void onResultOk(LocalizacoesSquidexInfo localizacoes)
            {
                listLocalizacoes = localizacoes;
                ((ArrayAdapter) lvLocalizacao.getAdapter()).notifyDataSetChanged();
                srLocalizacao.setRefreshing(false);
            }

            @Override
            public void onErro()
            {
                listLocalizacoes = null;
                ((ArrayAdapter) lvLocalizacao.getAdapter()).notifyDataSetChanged();
                srLocalizacao.setRefreshing(false);
            }
        });

    }
}