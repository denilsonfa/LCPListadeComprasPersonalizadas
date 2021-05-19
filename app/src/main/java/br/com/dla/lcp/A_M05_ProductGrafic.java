package br.com.dla.lcp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class A_M05_ProductGrafic extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    //Menu (DrawerLayout) == Creditos: Coding With Tea
    DrawerLayout drawerLayout05;
    NavigationView navigationView05;
    Toolbar toolbar05;

    //RecyclerView == Creditos:  Stevdza-San (YOUTUBE)
    //Lista Produtos
    RecyclerView recyclerView_ProductGrafic, recyclerView_ProductGrafic_SetList;
    TextView no_data_ProductGrafic, no_data_ProductGrafic_SetList;
    S_ConexaoDAO conexaoDAO_ProductGrafic, conexaoDAO_ListLista05;
    S_ConexaoDAO conexaoDAO_ListLista_ProductGrafic;
    ArrayList<String> idListL, nomeList, dataList, checkList, idProduct, idListP, nomeProduct, quantProduct, medidaProduct, tipoProduct, valorProduct, checkProduct;
    ArrayList<String> selectionList05, idListL05, nomeList05, dataList05, checkList05, totalList05;

    //TMP
    S_H05_ListLista_Adapter s_m05_list_lista_adapter;
    S_M03_ListExtrato_Adapter s_m03_productGrafic_adapter;

    //Criar lista (Adicionar Produto) == Creditos: @Denilson_fa
    S_Dados dados = new S_Dados();

    //Valor Maximo para geração do calculo do grafico
    float valorMax = 0;

    //id da ultima lista / ou lista consultada referente ao grafico
    long idUltimaLista;
    private TextView totalValorListITEM05, nomeListITEM05, porcent0, porcent20, porcent50, porcent80, porcent100;
    private TextView nomeListITEM05_header01, valorListITEM05_header01;
    private LinearLayout option01, option02, option03;

    //adView
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(1);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.rgb(149,16,149));
        setContentView(R.layout.activity_m05_product_grafic);

        //adView
        //CODIGO adMob (BANNER): ca-app-pub-7912320570829252/1020212016
        //CODIGO para testes: ca-app-pub-3940256099942544/1033173712

        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this,"ca-app-pub-7912320570829252/1020212016", adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                // The mInterstitialAd reference will be null until
                // an ad is loaded.
                mInterstitialAd = interstitialAd;
                Log.i("TAG", "onAdLoaded");
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error
                Log.i("TAG", loadAdError.getMessage());
                mInterstitialAd = null;
            }
        });

        //Activity = Menu
        drawerLayout05 =findViewById(R.id.drawer_layout05);
        navigationView05 =findViewById(R.id.nav_view05);
        toolbar05 =findViewById(R.id.toolbar05);

        //Activity = ToolBar
        setSupportActionBar(toolbar05);
        getSupportActionBar().setTitle("");

        //Activity = Navigation Menu
        navigationView05.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout05, toolbar05, R.string.ico_dw_open, R.string.ico_dw_close);
        drawerLayout05.addDrawerListener(toggle);
        toggle.syncState();

        navigationView05.setNavigationItemSelectedListener(this);

        //RecyclerView
        recyclerView_ProductGrafic_SetList = findViewById(R.id.readProductListGrafico_l);
        no_data_ProductGrafic_SetList = findViewById(R.id.no_data_ListGrafico_l);

        recyclerView_ProductGrafic = findViewById(R.id.readProductListGrafico_p);
        no_data_ProductGrafic = findViewById(R.id.no_data_ListGrafico_p);

        totalValorListITEM05 = findViewById(R.id.totalValorListITEM05);
        nomeListITEM05 = findViewById(R.id.nomeListITEM05);

        nomeListITEM05_header01 = findViewById(R.id.nomeListITEM05_header01);
        valorListITEM05_header01 = findViewById(R.id.valorListITEM05_header01);

        //option01, option02, option03
        //Conforme a quantidade de listas finalizadas, ele irá mostrar um header diferente
        option01 = findViewById(R.id.option01);
        option02 = findViewById(R.id.option02);
        option03 = findViewById(R.id.option03);

        //consultar quantas listas foram finalizadas
        //conexaoDAO_ListLista_ProductGrafic.numListaCheck()
        S_ConexaoDAO s_conexaoDAO1 = new S_ConexaoDAO(A_M05_ProductGrafic.this);
        int countListCheck = s_conexaoDAO1.numListaCheck();

        if(countListCheck >= 2) {
            //Se numero de listas for maior que 2, aparece grafico

            option01.setVisibility(View.GONE);
            option02.setVisibility(View.GONE);
            option03.setVisibility(View.VISIBLE);

            //Definir valor maximo para gerar grafico
            S_ConexaoDAO conexaoDAO_ListProductCountCheck = new S_ConexaoDAO(A_M05_ProductGrafic.this);
            valorMax = conexaoDAO_ListProductCountCheck.maxTotalList();

            //definir qual lista irá abrir automativamente assim que iniciar activity
            long idListaSelected = S_H05_ListLista_Adapter.idUltimaListaSelected;

            if( idListaSelected == 0) {
                setIdUltimaLista( conexaoDAO_ListProductCountCheck.idUltimaListCheck() );
            } else {
                setIdUltimaLista(idListaSelected);
            }

            //Consultar nome da lista atual
            String readListName = "ID: "+getIdUltimaLista()+" - "+conexaoDAO_ListProductCountCheck.readListName(getIdUltimaLista());
            //Consultar valor total da lista atual
            double totalValor = Double.parseDouble( conexaoDAO_ListProductCountCheck.readListTL( getIdUltimaLista() ) );
            totalValor = (Math.rint (totalValor * 100.0) / 100.0);
            String totVal = "R$ "+totalValor;

            //imprimir dados
            nomeListITEM05.setText(readListName);
            totalValorListITEM05.setText(totVal);

            //escala do grafico
            //porcent0, porcent25, porcent50, porcent75, porcent100;
            porcent0 = findViewById(R.id.porcent0);
            porcent20 = findViewById(R.id.porcent20);
            porcent50 = findViewById(R.id.porcent50);
            porcent80 = findViewById(R.id.porcent80);
            porcent100 = findViewById(R.id.porcent100);

            //Calculos de porcentagem
            //0%
            double porcNum0 = (valorMax*0.00);
            porcNum0 = (Math.rint (porcNum0 * 100.0) / 100.0);
            String porc0 = "R$ "+porcNum0;

            //25%
            double porcNum20 = (valorMax*0.20);
            porcNum20 = (Math.rint (porcNum20 * 100.0) / 100.0);
            String porc20 = "R$ "+ porcNum20;

            //50%
            double porcNum50 = (valorMax*0.50);
            porcNum50 = (Math.rint (porcNum50 * 100.0) / 100.0);
            String porc50 = "R$ "+porcNum50;

            //75%
            double porcNum80 = (valorMax*0.80);
            porcNum80 = (Math.rint (porcNum80 * 100.0) / 100.0);
            String porc80 = "R$ "+ porcNum80;

            //100%
            double porcNum100 = (valorMax*1.00);
            porcNum100 = (Math.rint (porcNum100 * 100.0) / 100.0);
            String porc100 = "R$ "+porcNum100;

            //SetText porcent
            porcent0.setText(porc0);
            porcent20.setText(porc20);
            porcent50.setText(porc50);
            porcent80.setText(porc80);
            porcent100.setText(porc100);

            //Carregar lista de produtos
            resetStoreProducts_List(getIdUltimaLista());

        } else if (countListCheck == 1) {
            //se for somente 1

            option01.setVisibility(View.GONE);
            option02.setVisibility(View.VISIBLE);
            option03.setVisibility(View.GONE);

            nomeListITEM05_header01.setText(R.string.nomeList);

            //Consultar nome da lista ultima lista finalizada
            S_ConexaoDAO s_conexaoDAO = new S_ConexaoDAO(A_M05_ProductGrafic.this);
            long ultimaLista = s_conexaoDAO.idUltimaListCheck();
            String readListName = "ID: "+ultimaLista+" - "+s_conexaoDAO.readListName( ultimaLista );

            //Consultar valor total da lista atual
            double totalValor = Double.parseDouble( s_conexaoDAO.readListTL( ultimaLista ) );
            totalValor = (Math.rint (totalValor * 100.0) / 100.0);
            String totVal = "R$ "+totalValor;

            //imprimir dados
            nomeListITEM05_header01.setText(readListName);
            valorListITEM05_header01.setText(totVal);

            nomeListITEM05.setText(readListName);
            totalValorListITEM05.setText(totVal);

            //Carregar lista de produtos
            resetStoreProducts_List( ultimaLista );
        } else {
            //não tiver nenhuma lista ou, algum erro

            option01.setVisibility(View.VISIBLE);
            option02.setVisibility(View.GONE);
            option03.setVisibility(View.GONE);

        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        S_ConexaoDAO s_conexaoDAO1 = new S_ConexaoDAO(A_M05_ProductGrafic.this);
        int countListCheck = s_conexaoDAO1.numListaCheck();

        //recarrega a lista
        if(countListCheck >= 2) {
            //recarrega a lista
            resetStoreProducts_List(getIdUltimaLista());

            //carrega a grafico
            resetStoreGrafic(valorMax);

        } else if (countListCheck == 1) {
            S_ConexaoDAO s_conexaoDAO = new S_ConexaoDAO(A_M05_ProductGrafic.this);
            long ultimaLista = s_conexaoDAO.idUltimaListCheck();
            resetStoreProducts_List(ultimaLista);
        } else {
            resetStoreProducts_List(0);
            nomeListITEM05.setText(R.string.erro_lista);
            totalValorListITEM05.setText(R.string.erro_lista);
        }

    }

    //Função Menu
    @Override
    public void onBackPressed(){

        if (drawerLayout05.isDrawerOpen(GravityCompat.START)){
            drawerLayout05.closeDrawer(GravityCompat.START);
        } else {
            //Toast.makeText(this, R.string.list_concluida, Toast.LENGTH_SHORT).show();

            //adView
            if (mInterstitialAd != null) {
                mInterstitialAd.show(A_M05_ProductGrafic.this);
            } else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.");
            }

            super.onBackPressed();
        }
        isDestroyed();
        //finish();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_menu01:
                startActivity(new Intent(getBaseContext(), A_M01_ListCreate_SetList.class));
                finish();
                break;
            case R.id.nav_menu02:
                startActivity(new Intent(getBaseContext(), A_M02_ListConsult_SetList.class));
                finish();
                break;
            case R.id.nav_menu03:
                startActivity(new Intent(getBaseContext(), A_M03_ListExtrato_SetList.class));
                finish();
                break;
            case R.id.nav_menu04:
                startActivity(new Intent(getBaseContext(), A_M04_AjudaActivity.class));
                finish();
                break;
            case R.id.nav_menu05:
                startActivity(new Intent(getBaseContext(), A_M05_ProductGrafic.class));
                finish();
                break;
            case R.id.nav_menu06:
                startActivity(new Intent(getBaseContext(), A_M06_InfoActivity.class));
                finish();
                break;
            case R.id.nav_menu07:
                startActivity(new Intent(getBaseContext(), A_A_SplashScreenActivity.class));
                finish();
                break;
            case R.id.nav_menu08:
                startActivity(new Intent(getBaseContext(), A_A_OnBoardingActivity.class));
                finish();
                break;
        }
        drawerLayout05.closeDrawer(GravityCompat.START); return true;
    }

    //RecyclerView
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    //Lista Listas
    void storeGrafic(){
        Cursor cursor = conexaoDAO_ListLista05.readListIsCheck();
        if(cursor.getCount() == 0) {
            no_data_ProductGrafic_SetList.setVisibility(View.VISIBLE);
            recyclerView_ProductGrafic_SetList.setVisibility(View.GONE);
        } else {
            while (cursor.moveToNext()){
                idListL05.add(cursor.getString(0));
                nomeList05.add(cursor.getString(1));
                dataList05.add(cursor.getString(2));
                checkList05.add(cursor.getString(3));
                totalList05.add(cursor.getString(4));
            }
            no_data_ProductGrafic_SetList.setVisibility(View.GONE);
            recyclerView_ProductGrafic_SetList.setVisibility(View.VISIBLE);
        }
    }

    //Metodo
    void resetStoreGrafic(float valorMax05) {
        conexaoDAO_ListLista05 = new S_ConexaoDAO(A_M05_ProductGrafic.this);
        idListL05 = new ArrayList<>();
        nomeList05 = new ArrayList<>();
        dataList05 = new ArrayList<>();
        checkList05 = new ArrayList<>();
        totalList05 = new ArrayList<>();

        storeGrafic();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView myList = (RecyclerView) findViewById(R.id.readProductListGrafico_l);
        myList.setLayoutManager(layoutManager);

        s_m05_list_lista_adapter = new S_H05_ListLista_Adapter(A_M05_ProductGrafic.this,
                selectionList05, idListL05, nomeList05, dataList05, checkList05, totalList05, valorMax05);
        recyclerView_ProductGrafic_SetList.setAdapter(s_m05_list_lista_adapter);
    }

    //Lista Produtos
    void storeProducts_List(long idListaP2){
        //idListL, nomeList, dataList, checkList,
        //idProduct, idListP, nomeProduct, quantProduct, medidaProduct, tipoProduct, valorProduct, checkProduct

        //Cursor cursor = conexaoDAO_ProductGrafic.readProduct(String.valueOf(dados.getIdListL()));
        Cursor cursor = conexaoDAO_ProductGrafic.readProduct( String.valueOf(idListaP2) );
        if(cursor.getCount() == 0) {
            no_data_ProductGrafic.setVisibility(View.VISIBLE);
            recyclerView_ProductGrafic.setVisibility(View.GONE);
        } else {
            while (cursor.moveToNext()){
                idListL.add(        cursor.getString(0));
                nomeList.add(       cursor.getString(1));
                dataList.add(       cursor.getString(2));
                checkList.add(      cursor.getString(3));

                idProduct.add(      cursor.getString(5));
                idListP.add(        cursor.getString(6));
                nomeProduct.add(    cursor.getString(7));
                quantProduct.add(   cursor.getString(8));
                medidaProduct.add(  cursor.getString(9));
                tipoProduct.add(    cursor.getString(10));
                valorProduct.add(   cursor.getString(11));
                checkProduct.add(   cursor.getString(12));
            }
            no_data_ProductGrafic.setVisibility(View.GONE);
            recyclerView_ProductGrafic.setVisibility(View.VISIBLE);
        }
    }

    //Reset storeProducts
    void resetStoreProducts_List(long idListaP){
        conexaoDAO_ProductGrafic = new S_ConexaoDAO(A_M05_ProductGrafic.this);
        idListL = new ArrayList<>();
        nomeList = new ArrayList<>();
        dataList = new ArrayList<>();
        checkList = new ArrayList<>();

        idProduct = new ArrayList<>();
        idListP = new ArrayList<>();
        nomeProduct = new ArrayList<>();
        quantProduct = new ArrayList<>();
        medidaProduct = new ArrayList<>();
        tipoProduct = new ArrayList<>();
        valorProduct = new ArrayList<>();
        checkProduct = new ArrayList<>();

        storeProducts_List(idListaP);

        s_m03_productGrafic_adapter = new S_M03_ListExtrato_Adapter(A_M05_ProductGrafic.this,
                idListL, nomeList, dataList, checkList, idProduct, idListP, nomeProduct, quantProduct,
                medidaProduct, tipoProduct, valorProduct, checkProduct);

        recyclerView_ProductGrafic.setAdapter(s_m03_productGrafic_adapter);
        recyclerView_ProductGrafic.setLayoutManager(new LinearLayoutManager(A_M05_ProductGrafic.this));
    }

    //idUltimaLista Getter e Setter
    public long getIdUltimaLista() {
        return idUltimaLista;
    }
    public void setIdUltimaLista(long idUltimaLista) {
        this.idUltimaLista = idUltimaLista;
    }

}