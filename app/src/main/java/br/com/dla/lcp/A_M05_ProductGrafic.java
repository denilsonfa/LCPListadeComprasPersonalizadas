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
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

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
    private TextView totalValorListITEM05, nomeListITEM05;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(1);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.rgb(149,16,149));
        setContentView(R.layout.activity_m05_product_grafic);

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

        //Definir valor maximo para gerar grafico
        S_ConexaoDAO conexaoDAO_ListProductCountCheck = new S_ConexaoDAO(A_M05_ProductGrafic.this);
        valorMax = conexaoDAO_ListProductCountCheck.maxTotalList();

        //definir qual lista irá abrir automativamente assim que iniciar activity
        long idListaSelected = S_H05_ListLista_Adapter.idUltimaListaSelected;

        if( idListaSelected == 0) {
            setIdUltimaLista( conexaoDAO_ListProductCountCheck.idUltimaList() );
        } else {
            setIdUltimaLista(idListaSelected);
        }

        //RecyclerView
        recyclerView_ProductGrafic_SetList = findViewById(R.id.readProductListGrafico_l);
        no_data_ProductGrafic_SetList = findViewById(R.id.no_data_ListGrafico_l);

        recyclerView_ProductGrafic = findViewById(R.id.readProductListGrafico_p);
        no_data_ProductGrafic = findViewById(R.id.no_data_ListGrafico_p);

        totalValorListITEM05 = findViewById(R.id.totalValorListITEM05);
        nomeListITEM05 = findViewById(R.id.nomeListITEM05);

        //Consultar nome da lista atual
        String readListName = "ID: "+getIdUltimaLista()+" - "+conexaoDAO_ListProductCountCheck.readListName(getIdUltimaLista());
        //Consultar valor total da lista atual
        double totalValor = Double.parseDouble( conexaoDAO_ListProductCountCheck.readListTL( getIdUltimaLista() ) );
        totalValor = (Math.rint (totalValor * 100.0) / 100.0);
        String totVal = "R$ "+totalValor;

        //imprimir dados
        nomeListITEM05.setText(readListName);
        totalValorListITEM05.setText(totVal);

        resetStoreProducts_ProductGrafic(getIdUltimaLista());

    }

    @Override
    protected void onResume() {
        super.onResume();

        //Após retornar da editar/apagar produto, recarrega a lista
        resetStoreProducts_ProductGrafic(getIdUltimaLista());

        //Após retornar da editar/apagar produto, recarrega a lista
        resetStoreLists(valorMax);

    }

    //Função Menu
    @Override
    public void onBackPressed(){

        if (drawerLayout05.isDrawerOpen(GravityCompat.START)){
            drawerLayout05.closeDrawer(GravityCompat.START);
        } else {
            //Toast.makeText(this, R.string.list_concluida, Toast.LENGTH_SHORT).show();
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
                startActivity(new Intent(getBaseContext(), A_M04_ConfigActivity.class));
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
    void storeLists(){
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
    void resetStoreLists(float valorMax05) {
        conexaoDAO_ListLista05 = new S_ConexaoDAO(A_M05_ProductGrafic.this);
        idListL05 = new ArrayList<>();
        nomeList05 = new ArrayList<>();
        dataList05 = new ArrayList<>();
        checkList05 = new ArrayList<>();
        totalList05 = new ArrayList<>();

        storeLists();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView myList = (RecyclerView) findViewById(R.id.readProductListGrafico_l);
        myList.setLayoutManager(layoutManager);

        s_m05_list_lista_adapter = new S_H05_ListLista_Adapter(A_M05_ProductGrafic.this,
                selectionList05, idListL05, nomeList05, dataList05, checkList05, totalList05, valorMax05);
        recyclerView_ProductGrafic_SetList.setAdapter(s_m05_list_lista_adapter);
    }

    //Lista Produtos
    void storeProducts_ListConsult(long idListaP2){
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
    void resetStoreProducts_ProductGrafic(long idListaP){
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

        storeProducts_ListConsult(idListaP);

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