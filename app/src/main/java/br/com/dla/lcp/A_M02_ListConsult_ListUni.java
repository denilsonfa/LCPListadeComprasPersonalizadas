package br.com.dla.lcp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class A_M02_ListConsult_ListUni extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Menu (DrawerLayout) == Creditos: Coding With Tea
    DrawerLayout drawerLayout02;
    NavigationView navigationView02;
    Toolbar toolbar02;

    //RecyclerView == Creditos:  Stevdza-San (YOUTUBE)
    //Lista Produtos
    RecyclerView recyclerView_ListConsult;
    TextView no_data_ListConsult;
    LinearLayout list_concluida;
    S_ConexaoDAO conexaoDAO_ListConsult;
    S_ConexaoDAO conexaoDAO_ListLista_ListConsult;
    ArrayList<String> idListL, nomeList, dataList, checkList, idProduct, idListP, nomeProduct, quantProduct, medidaProduct, tipoProduct, valorProduct, checkProduct;

    S_M02_ListConsult_Adapter s_M02_listConsult_adapter;

    //Criar lista (Adicionar Produto) == Creditos: @Denilson_fa
    S_Dados dados = new S_Dados();

    private TextView nomeListSELECTED02, nomeListSELECTED02b;
    private ImageView reloadListConsult;

    //private String idProductDADOS, idListPDADOS, nomeProductDADOS, quantProductDADOS, medidaProductDADOS, tipoProductDADOS;
    private String idListLDADOS, nomeListIDDADOS, dataListIDDADOS, checkListIDDADOS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(1);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.rgb(14,91,182));
        setContentView(R.layout.activity_m02_listconsult_listuni);

        //Activity = Menu
        drawerLayout02 =findViewById(R.id.drawer_layout02);
        navigationView02 =findViewById(R.id.nav_view02);
        toolbar02 =findViewById(R.id.toolbar02);

        //Activity = ToolBar
        setSupportActionBar(toolbar02);
        getSupportActionBar().setTitle("");

        //Activity = Navigation Menu
        navigationView02.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout02, toolbar02, R.string.ico_dw_open, R.string.ico_dw_close);
        drawerLayout02.addDrawerListener(toggle);
        toggle.syncState();

        navigationView02.setNavigationItemSelectedListener(this);

        //S_Dados = ID dos Itens
        nomeListSELECTED02 = findViewById(R.id.nomeListSELECTED02);
        nomeListSELECTED02b = findViewById(R.id.nomeListSELECTED02b);
        reloadListConsult = findViewById(R.id.reloadListConsult);
        list_concluida = findViewById(R.id.list_concluida);

        //Recebendo Dados do Produto selecionado
        getAndSetIntentDataLista_ListConsult();

        //RecyclerView
        recyclerView_ListConsult = findViewById(R.id.readProductListConsult);
        no_data_ListConsult = findViewById(R.id.no_data_ListConsult);

        resetStoreProducts_ListConsult();

        //Função reloadListCreate
        reloadListConsult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetStoreProducts_ListConsult();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        //Após retornar da editar/apagar produto, recarrega a lista
        resetStoreProducts_ListConsult();
    }

    @Override
    public void onBackPressed() {

        S_ConexaoDAO conexaoDAO_ListProductCount = new S_ConexaoDAO(A_M02_ListConsult_ListUni.this);
        S_ConexaoDAO conexaoDAO_ListProductCountCheck = new S_ConexaoDAO(A_M02_ListConsult_ListUni.this);
        int countItens = conexaoDAO_ListProductCount.numItensListS( dados.getIdListL() );
        int countItensCheck = conexaoDAO_ListProductCountCheck.numItensCheck( dados.getIdListL() );

        if( countItensCheck == countItens ) {
            //se for, ficha a lista
            double totalValor = conexaoDAO_ListProductCount.numReadProductTotal( dados.getIdListL() );

            conexaoDAO_ListProductCount.updateListCheck( String.valueOf(dados.getIdListL()), true, totalValor );
            Toast.makeText(this, R.string.list_concluida, Toast.LENGTH_SHORT).show();
            finish();

        } else {
            //se não pergunta se deseja fechar
            AlertDialog.Builder builder = new AlertDialog.Builder(A_M02_ListConsult_ListUni.this);
            builder.setTitle(R.string.sltProduct02_exitList);
            builder.setMessage(R.string.sltProduct02_exitListObs);
            //builder.setIcon(R.drawable.ic_item_alert);

            //define um botão negativo
            builder.setPositiveButton(R.string.nao, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    S_ConexaoDAO conexaoDAO_bd = new S_ConexaoDAO(A_M02_ListConsult_ListUni.this);
                    conexaoDAO_bd.updateListCheck( String.valueOf(dados.getIdListL()), false, 0 );
                    Toast.makeText(A_M02_ListConsult_ListUni.this, R.string.list_save, Toast.LENGTH_SHORT).show();
                    finish();
                }
            });

            //define um botão positivo
            builder.setNegativeButton(R.string.sim, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    S_ConexaoDAO conexaoDAO_bd = new S_ConexaoDAO(A_M02_ListConsult_ListUni.this);

                    double totalValor = conexaoDAO_bd.numReadProductTotal( dados.getIdListL() );

                    conexaoDAO_bd.updateListCheck( String.valueOf(dados.getIdListL()), true, totalValor );
                    Toast.makeText(A_M02_ListConsult_ListUni.this, R.string.list_concluida, Toast.LENGTH_SHORT).show();
                    finish();

                    Intent intent = new Intent(A_M02_ListConsult_ListUni.this, A_M03_ListExtract.class);
                    intent.putExtra("idListLID", String.valueOf(dados.getIdListL()));
                    intent.putExtra("nomeListID", String.valueOf(dados.getNomeList()));
                    intent.putExtra("dataListID", String.valueOf(dados.getDataList()));
                    intent.putExtra("checkListID", String.valueOf(dados.getCheckList()));
                    A_M02_ListConsult_ListUni.this.startActivity(intent);

                }
            });

            //define um botão neutro
            builder.setNeutralButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        //Toast.makeText(A_M02_ListConsult_ListUni.this, R.string.cancel, Toast.LENGTH_SHORT).show();
                    }
                });

            //cria o AlertDialog
            AlertDialog  alerta = builder.create();
            //Exibe
            alerta.show();
        }

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_menu01:
                startActivity(new Intent(getBaseContext(), A_M01_ListCreate_SetList.class));
                exitAct02();
                finish();
                break;
            case R.id.nav_menu02:
                startActivity(new Intent(getBaseContext(), A_M02_ListConsult_SetList.class));
                exitAct02();
                finish();
                break;
            case R.id.nav_menu03:
                startActivity(new Intent(getBaseContext(), A_M03_ListExtrato_SetList.class));
                exitAct02();
                finish();
                break;
            case R.id.nav_menu04:
                startActivity(new Intent(getBaseContext(), A_M04_ConfigActivity.class));
                exitAct02();
                finish();
                break;
            case R.id.nav_menu05:
                startActivity(new Intent(getBaseContext(), A_M05_ProductGrafic.class));
                exitAct02();
                finish();
                break;
            case R.id.nav_menu06:
                startActivity(new Intent(getBaseContext(), A_M06_InfoActivity.class));
                exitAct02();
                finish();
                break;
            case R.id.nav_menu07:
                startActivity(new Intent(getBaseContext(), A_A_SplashScreenActivity.class));
                exitAct02();
                finish();
                break;
            case R.id.nav_menu08:
                startActivity(new Intent(getBaseContext(), A_A_OnBoardingActivity.class));
                exitAct02();
                finish();
                break;
        }
        drawerLayout02.closeDrawer(GravityCompat.START); return true;
    }

    //RecyclerView
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    //Lista Produtos
    void storeProducts_ListConsult(){
        S_ConexaoDAO conexaoDAO_ListProductCount = new S_ConexaoDAO(A_M02_ListConsult_ListUni.this);
        S_ConexaoDAO conexaoDAO_ListProductCountCheck = new S_ConexaoDAO(A_M02_ListConsult_ListUni.this);
        int countItens = conexaoDAO_ListProductCount.numItensListS( dados.getIdListL() );
        int countItensCheck = conexaoDAO_ListProductCountCheck.numItensCheck( dados.getIdListL() );

        if( countItensCheck == countItens ) {
            list_concluida.setVisibility(View.VISIBLE);
        } else {
            list_concluida.setVisibility(View.GONE);
        }

        //idListL, nomeList, dataList, checkList,
        //idProduct, idListP, nomeProduct, quantProduct, medidaProduct, tipoProduct, valorProduct, checkProduct

        Cursor cursor = conexaoDAO_ListConsult.readProduct(String.valueOf(dados.getIdListL()));
        if(cursor.getCount() == 0) {
            no_data_ListConsult.setVisibility(View.VISIBLE);
            recyclerView_ListConsult.setVisibility(View.GONE);
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
            no_data_ListConsult.setVisibility(View.GONE);
            recyclerView_ListConsult.setVisibility(View.VISIBLE);
        }
    }

    //Reset storeProducts
    void resetStoreProducts_ListConsult(){
        conexaoDAO_ListConsult = new S_ConexaoDAO(A_M02_ListConsult_ListUni.this);
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

        storeProducts_ListConsult();

        s_M02_listConsult_adapter = new S_M02_ListConsult_Adapter(A_M02_ListConsult_ListUni.this,
                idListL, nomeList, dataList, checkList, idProduct, idListP, nomeProduct, quantProduct,
                medidaProduct, tipoProduct, valorProduct, checkProduct);

        recyclerView_ListConsult.setAdapter(s_M02_listConsult_adapter);
        recyclerView_ListConsult.setLayoutManager(new LinearLayoutManager(A_M02_ListConsult_ListUni.this));
    }

    //Recebendo dados do item selecionado para editar
    void getAndSetIntentDataLista_ListConsult() {
        if (getIntent().hasExtra("idListLID") &&
                getIntent().hasExtra("nomeListID") &&
                getIntent().hasExtra("dataListID") &&
                getIntent().hasExtra("checkListID") ) {

            //GETTING
            idListLDADOS = getIntent().getStringExtra("idListLID");
            nomeListIDDADOS = getIntent().getStringExtra("nomeListID");
            dataListIDDADOS = getIntent().getStringExtra("dataListID");
            checkListIDDADOS = getIntent().getStringExtra("checkListID");

            //SETTING
            dados.setIdListL(Integer.parseInt(idListLDADOS));
            dados.setNomeList(nomeListIDDADOS);
            dados.setDataList(dataListIDDADOS);
            dados.setCheckList(Boolean.parseBoolean(checkListIDDADOS));

            //SETTEXT DADOS IN TEXTVIEWS
            //String nomeList = nomeListIDDADOS+" ( "+ dataListIDDADOS +" )";
            nomeListSELECTED02.setText(nomeListIDDADOS);
            nomeListSELECTED02b.setText(dataListIDDADOS);

        } else {
            //Toast.makeText(this, R.string.erro_product, Toast.LENGTH_SHORT).show();
        }
    }

    private void exitAct02() {
        S_ConexaoDAO conexaoDAO_ListProductCount = new S_ConexaoDAO(A_M02_ListConsult_ListUni.this);
        S_ConexaoDAO conexaoDAO_ListProductCountCheck = new S_ConexaoDAO(A_M02_ListConsult_ListUni.this);
        int countItens = conexaoDAO_ListProductCount.numItensListS( dados.getIdListL() );
        int countItensCheck = conexaoDAO_ListProductCountCheck.numItensCheck( dados.getIdListL() );

        if( countItensCheck == countItens ) {

            double totalValor = conexaoDAO_ListProductCount.numReadProductTotal( dados.getIdListL() );

            conexaoDAO_ListProductCount.updateListCheck( String.valueOf(dados.getIdListL()), true, totalValor );
            Toast.makeText(this, R.string.list_concluida, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.list_save, Toast.LENGTH_SHORT).show();
        }

    }

}