package com.example.tamires.localizacao2.Data.webservice;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tamires.localizacao2.Data.webservice.dialogs.PopupInformacao;
import com.example.tamires.localizacao2.Data.webservice.webservices.WebServiceControle;
import com.example.tamires.localizacao2.Data.webservice.webservices.content.Item;
import com.example.tamires.localizacao2.Data.webservice.webservices.content.LocalizacoesSquidexInfo;
import com.example.tamires.localizacao2.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Context context = MapsActivity.this;

    private AppCompatTextView tvDistancia;
    private AppCompatTextView tvArea;
    //
    private ArrayList<Marker> listMarker = new ArrayList<>();
    private ArrayList<LatLng> listPontos = new ArrayList<>();

    private Marker myLocation;
    private Polygon mPolygon;
    private Polyline mPolyline;

    public static final String LATITUDE = "com.example.tamires.localizacao2.Data.webservice.MapsActivity.LATITUDE";
    public static final String LONGITUDE = "ccom.example.tamires.localizacao2.Data.webservice.MapsActivity.LONGITUDE";


    private FusedLocationProviderClient mFusedLocationClient;
    //
    private static final int REQUEST_PERMISSOES = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //
        /**
         * Só configura a tela se possuir as permissões
         */
        if (verificaPermissoesNecessarias())
        {
            iniciaAplicacao();

            new WebServiceControle().carregaListaLocalizacoes(this, new WebServiceControle.CarregaListaLocalizacoesListener() {

                @Override
                public void onResultOk(LocalizacoesSquidexInfo localizacoes) {
                    for (int i = 0; i < localizacoes.getItems().length; i++) {
                        Item item = localizacoes.getItems()[i];
                        LatLng position = new LatLng(Double.parseDouble(item.getData().getLatitute().getIv()), Double.parseDouble(item.getData().getLongitude().getIv()));

                        adicionaMarcador(position, item.getData().getNomLocal().getIv(), item.getData().getTelLocal().getIv());
                    }
                }
                @Override
                public void onErro() {
                    PopupInformacao.mostraMensagem(MapsActivity.this, "Falha ao buscar os locais");
                }
            });
        }

    }

    private boolean verificaPermissoesNecessarias()
    {
        /**
         * Verifica se o app tem as permissões necessárias para utilizar o sistema
         */
        ArrayList<String> permissoesNecessarias = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            permissoesNecessarias.add(Manifest.permission.ACCESS_FINE_LOCATION);
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            permissoesNecessarias.add(Manifest.permission.ACCESS_COARSE_LOCATION);

        /**
         * Se já possui todas, retorna true
         */
        if (permissoesNecessarias.size() <= 0)
            return true;
        else
        {
            /**
             * Se não possui todas, chama método responsável por solicitar as permissões
             */
            String[] permissoes = new String[permissoesNecessarias.size()];
            ActivityCompat.requestPermissions(MapsActivity.this, permissoesNecessarias.toArray(permissoes), REQUEST_PERMISSOES);
            return false;
        }
    }

    /**
     * Método disparado após solicitar permissões. Funcionamento pareceido com o OnActivityResult
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        switch (requestCode)
        {
            case REQUEST_PERMISSOES:
                /**
                 * Verifica se todas as permissões foram autorizadas
                 */
                for (int result : grantResults)
                {
                    if (result != PackageManager.PERMISSION_GRANTED)
                    {
                        /**
                         * Se alguma permissão não foi autorizada, avisa o usuário e fecha a tela
                         */
                        Toast.makeText(context, R.string.FalhaPermissoes, Toast.LENGTH_LONG).show();
                        finish();
                        return;
                    }
                }
                //
                /**
                 * Se todas as permissões foram dadas, continuar normalmente
                 */
                iniciaAplicacao();
                break;
        }
    }

    private void iniciaAplicacao()
    {
        setContentView(R.layout.activity_maps);
        //
        /**
         * Inicia referência para o mapa
         */
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mMap);
        if (mapFragment != null)
        {
            mapFragment.getMapAsync(this);
        }
        //
        tvArea = findViewById(R.id.tvArea);
        tvDistancia = findViewById(R.id.tvDistancia);
        //
        iniciaLeituraGPS();

    }

    /**
     * Método disparado quando o mapa está pronto para ser utilizado
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;
        // Configura o tipo do mapa
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-21.204185, -47.600372), 10.0f));

        // Adiciona evento para o click nos marcadores
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
        {
            @Override
            public boolean onMarkerClick(Marker marker)
            {
                //Verifica se é o marcador de posição e se for não faz nada
                if (marker.equals(myLocation))
                    return true;
                // Se não for, remover o marcador e refaz os calculos
//                removeMarker(marker);
//                calculaMedidas();
                return true;
            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
        {
            @Override
            public boolean onMarkerClick(Marker marker)
            {
                marker.showInfoWindow();

                return true;
            }
        });
    }

    /**
     * Cria o marcador para a posição.
     * @param position
     */
    private void adicionaMarcador(LatLng position, String title, String telefone)
    {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        markerOptions.position(position);
        markerOptions.draggable(false);
        markerOptions.title(title);
        markerOptions.snippet(telefone);
        Marker marker = mMap.addMarker(markerOptions);
        listMarker.add(marker);
    }

    /**
     * Objeto responsável por receber as atualizações de localização
     */
    private LocationCallback mLocationCallback = new LocationCallback()
    {
        @Override
        public void onLocationResult(LocationResult locationResult)
        {
            if (locationResult == null)
                return;
            //Utiliza a última posição válida
            atualizaMeuLocal(locationResult.getLastLocation());
        }
    };

    private void iniciaLeituraGPS()
    {
        /**
         * Configura a solicitação
         */
        LocationRequest mLocationRequest = new LocationRequest();
        // Intervalo mínimo de 1000 milisegundo para a solicitação explícita
        mLocationRequest.setInterval(1000);
        // Intervalo mínimo de 1000 milisegundos para a solicitação implícita
        mLocationRequest.setFastestInterval(1000);
        // Configura como máxima prioridade
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        // Pega instância do provider de localização (responsável por fazer o tratamento de localização e uso dos hardwares responsáveis
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        // Confirma se o app possui as permissões necessárias
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return;
        //
        // Registra a solicitação passando as configurações e o responsável por receber as informações atualizadas
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
    }


    /**
     * Cria um marcador para representar a posição atual
     * Só cria se já não existir, caso contrário, apenas atualiza posição do marcador
     * @param lastLocation
     */
    private void atualizaMeuLocal(Location lastLocation)
    {
        if (myLocation == null)
        {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            markerOptions.position(new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()));
            markerOptions.draggable(false);
            myLocation = mMap.addMarker(markerOptions);

        }
        else
            myLocation.setPosition(new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()));

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }
}
