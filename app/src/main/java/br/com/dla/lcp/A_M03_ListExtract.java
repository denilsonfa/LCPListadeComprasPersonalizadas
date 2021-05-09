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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class A_M03_ListExtract extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    //Menu (DrawerLayout) == Creditos: Coding With Tea
    DrawerLayout drawerLayout03;
    NavigationView navigationView03;
    Toolbar toolbar03;

    //RecyclerView == Creditos:  Stevdza-San (YOUTUBE)
    //Lista Produtos
    RecyclerView recyclerView_ListExtrato;
    TextView no_data_ListExtrato;
    S_ConexaoDAO conexaoDAO_ListExtrato;
    S_ConexaoDAO conexaoDAO_ListLista_ListExtrato;
    ArrayList<String> idListL, nomeList, dataList, checkList, idProduct, idListP, nomeProduct, quantProduct, medidaProduct, tipoProduct, valorProduct, checkProduct;

    S_M03_ListExtrato_Adapter s_m03_listExtrato_adapter;

    //Criar lista (Adicionar Produto) == Creditos: @Denilson_fa
    S_Dados dados = new S_Dados();

    private TextView nomeListSELECTED03, nomeListSELECTED03b, totalValorListITEM03;
    private ImageView reloadListExtrato, editListExtrato;

    //private String idProductDADOS, idListPDADOS, nomeProductDADOS, quantProductDADOS, medidaProductDADOS, tipoProductDADOS;
    private String idListLDADOS, nomeListIDDADOS, dataListIDDADOS, checkListIDDADOS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(1);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.rgb(149,16,149));
        setContentView(R.layout.activity_m03_listextract);

        //Activity = Menu
        drawerLayout03 =findViewById(R.id.drawer_layout03);
        navigationView03 =findViewById(R.id.nav_view03);
        toolbar03 =findViewById(R.id.toolbar03);

        //Activity = ToolBar
        setSupportActionBar(toolbar03);
        getSupportActionBar().setTitle("");

        //Activity = Navigation Menu
        navigationView03.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout03, toolbar03, R.string.ico_dw_open, R.string.ico_dw_close);
        drawerLayout03.addDrawerListener(toggle);
        toggle.syncState();

        navigationView03.setNavigationItemSelectedListener(this);

        //S_Dados = ID dos Itens
        nomeListSELECTED03 = findViewById(R.id.nomeListSELECTED03);
        nomeListSELECTED03b = findViewById(R.id.nomeListSELECTED03b);
        totalValorListITEM03 = findViewById(R.id.totalValorListITEM03);
        reloadListExtrato = findViewById(R.id.reloadListExtrato);
        editListExtrato = findViewById(R.id.editListExtrato);

        //Recebendo Dados do Produto selecionado
        getAndSetIntentDataLista_ListExtract();

        //RecyclerView
        recyclerView_ListExtrato = findViewById(R.id.readProductListExtrato);
        no_data_ListExtrato = findViewById(R.id.no_data_ListExtrato);

        resetStoreProducts_ListExtrato();

        //Função reloadListCreate
        reloadListExtrato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetStoreProducts_ListExtrato();
            }
        });

        //Função reloadListCreate
        editListExtrato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(A_M03_ListExtract.this);
                builder.setTitle(R.string.sltProduct03_reEdit);
                builder.setMessage(R.string.sltProduct03_reEditObs);
                //builder.setIcon(R.drawable.ic_item_edit);

                //define um botão negativo
                builder.setPositiveButton(R.string.main_menu01, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        //Toast.makeText(A_M03_ListExtract.this, R.string.main_menu01, Toast.LENGTH_SHORT).show();

                        S_ConexaoDAO conexaoDAO_ListProductCount = new S_ConexaoDAO(A_M03_ListExtract.this);

                        //Metodo para updateListCheck
                        conexaoDAO_ListProductCount.updateListCheck( String.valueOf(dados.getIdListL()), false, 0 );

                        //Enviar dados para outra Activity
                        Intent intent = new Intent(A_M03_ListExtract.this, A_M01_ListCreate.class);
                        intent.putExtra("idListLID", String.valueOf(dados.getIdListL()));
                        intent.putExtra("nomeListID", String.valueOf(dados.getNomeList()));
                        intent.putExtra("dataListID", String.valueOf(dados.getDataList()));
                        intent.putExtra("checkListID", String.valueOf(dados.getCheckList()));
                        A_M03_ListExtract.this.startActivity(intent);

                        //finalizando activity
                        finish();

                    }
                });

                //define um botão positivo
                builder.setNegativeButton(R.string.main_menu02, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(A_M03_ListExtract.this);
                        builder.setTitle(R.string.sltProduct02_editTipo);
                        builder.setMessage(R.string.sltProduct02_editTipoObs);

                        //define um botão negativo
                        builder.setNeutralButton(R.string.sltProduct02_editTipoUni, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {

                                S_ConexaoDAO conexaoDAO_ListProductCount = new S_ConexaoDAO(A_M03_ListExtract.this);

                                //Metodo para updateListCheck
                                conexaoDAO_ListProductCount.updateListCheck( String.valueOf(dados.getIdListL()), false, 0 );

                                //Enviar dados para outra Activity
                                Intent intent = new Intent(A_M03_ListExtract.this, A_M02_ListConsult_ListUni.class);
                                intent.putExtra("idListLID", String.valueOf(dados.getIdListL()));
                                intent.putExtra("nomeListID", String.valueOf(dados.getNomeList()));
                                intent.putExtra("dataListID", String.valueOf(dados.getDataList()));
                                intent.putExtra("checkListID", String.valueOf(dados.getCheckList()));
                                A_M03_ListExtract.this.startActivity(intent);

                                //finalizando activity
                                finish();

                            }
                        });

                        //define um botão positivo
                        builder.setNegativeButton(R.string.sltProduct02_editTipoMult, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {

                                S_ConexaoDAO conexaoDAO_ListProductCount = new S_ConexaoDAO(A_M03_ListExtract.this);

                                //Metodo para updateListCheck
                                conexaoDAO_ListProductCount.updateListCheck( String.valueOf(dados.getIdListL()), false, 0 );

                                //Enviar dados para outra Activity
                                Intent intent = new Intent(A_M03_ListExtract.this, A_M02_ListConsult_ListMult.class);
                                intent.putExtra("idListLID", String.valueOf(dados.getIdListL()));
                                intent.putExtra("nomeListID", String.valueOf(dados.getNomeList()));
                                intent.putExtra("dataListID", String.valueOf(dados.getDataList()));
                                intent.putExtra("checkListID", String.valueOf(dados.getCheckList()));
                                A_M03_ListExtract.this.startActivity(intent);

                                //finalizando activity
                                finish();

                            }
                        });
                        //cria o AlertDialog
                        AlertDialog  alerta = builder.create();
                        //Exibe
                        alerta.show();

                    }
                });

                //define um botão neutro
                builder.setNeutralButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        //Toast.makeText(A_M03_ListExtract.this, R.string.cancel, Toast.LENGTH_SHORT).show();
                    }
                });

                //cria o AlertDialog
                AlertDialog  alerta = builder.create();
                //Exibe
                alerta.show();

                //Mostrar/Esconder opção Graficos
                S_ConexaoDAO s_conexaoDAO1 = new S_ConexaoDAO(A_M03_ListExtract.this);
                int countListCheck = s_conexaoDAO1.numListaCheck();
                Menu menu = navigationView03.getMenu();

                if (countListCheck >= 1) {
                    menu.findItem(R.id.nav_menu05).setVisible(true);
                } else {
                    menu.findItem(R.id.nav_menu05).setVisible(false);
                }

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

        //Após retornar da editar/apagar produto, recarrega a lista
        resetStoreProducts_ListExtrato();
    }

    //Função Menu
    @Override
    public void onBackPressed(){

        if (drawerLayout03.isDrawerOpen(GravityCompat.START)){
            drawerLayout03.closeDrawer(GravityCompat.START);
        } else {
            Toast.makeText(this, R.string.list_concluida, Toast.LENGTH_SHORT).show();
            super.onBackPressed();
        }
        finish();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_menu01:
                startActivity(new Intent(getBaseContext(), A_M01_ListCreate_SetList.class));
                Toast.makeText(this, R.string.list_concluida, Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.nav_menu02:
                startActivity(new Intent(getBaseContext(), A_M02_ListConsult_SetList.class));
                Toast.makeText(this, R.string.list_concluida, Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.nav_menu03:
                startActivity(new Intent(getBaseContext(), A_M03_ListExtrato_SetList.class));
                Toast.makeText(this, R.string.list_concluida, Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.nav_menu04:
                startActivity(new Intent(getBaseContext(), A_M04_AjudaActivity.class));
                Toast.makeText(this, R.string.list_concluida, Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.nav_menu05:
                startActivity(new Intent(getBaseContext(), A_M05_ProductGrafic.class));
                Toast.makeText(this, R.string.list_concluida, Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.nav_menu06:
                startActivity(new Intent(getBaseContext(), A_M06_InfoActivity.class));
                Toast.makeText(this, R.string.list_concluida, Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.nav_menu07:
                startActivity(new Intent(getBaseContext(), A_A_SplashScreenActivity.class));
                Toast.makeText(this, R.string.list_concluida, Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.nav_menu08:
                startActivity(new Intent(getBaseContext(), A_A_OnBoardingActivity.class));
                Toast.makeText(this, R.string.list_concluida, Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        drawerLayout03.closeDrawer(GravityCompat.START); return true;
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
        //idListL, nomeList, dataList, checkList,
        //idProduct, idListP, nomeProduct, quantProduct, medidaProduct, tipoProduct, valorProduct, checkProduct

        Cursor cursor = conexaoDAO_ListExtrato.readProduct(String.valueOf(dados.getIdListL()));
        if(cursor.getCount() == 0) {
            no_data_ListExtrato.setVisibility(View.VISIBLE);
            recyclerView_ListExtrato.setVisibility(View.GONE);
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
            no_data_ListExtrato.setVisibility(View.GONE);
            recyclerView_ListExtrato.setVisibility(View.VISIBLE);
        }
    }

    //Reset storeProducts
    void resetStoreProducts_ListExtrato(){
        conexaoDAO_ListExtrato = new S_ConexaoDAO(A_M03_ListExtract.this);
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

        s_m03_listExtrato_adapter = new S_M03_ListExtrato_Adapter(A_M03_ListExtract.this,
                idListL, nomeList, dataList, checkList, idProduct, idListP, nomeProduct, quantProduct,
                medidaProduct, tipoProduct, valorProduct, checkProduct);

        recyclerView_ListExtrato.setAdapter(s_m03_listExtrato_adapter);
        recyclerView_ListExtrato.setLayoutManager(new LinearLayoutManager(A_M03_ListExtract.this));
    }

    //Recebendo dados do item selecionado para editar
    void getAndSetIntentDataLista_ListExtract() {
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
            nomeListSELECTED03.setText(nomeListIDDADOS);
            nomeListSELECTED03b.setText(dataListIDDADOS);

            S_ConexaoDAO conexaoDAO_ListProductCountCheck = new S_ConexaoDAO(A_M03_ListExtract.this);
            double totalValor = conexaoDAO_ListProductCountCheck.numReadProductTotal( dados.getIdListL() );
            totalValor = (Math.rint (totalValor * 100.0) / 100.0);
            String totVal = "R$ "+totalValor;

            totalValorListITEM03.setText(totVal);

        } else {
            //Toast.makeText(this, R.string.erro_product, Toast.LENGTH_SHORT).show();
        }
    }

}