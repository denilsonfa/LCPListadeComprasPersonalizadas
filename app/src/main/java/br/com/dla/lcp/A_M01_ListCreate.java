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
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class A_M01_ListCreate extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Menu (DrawerLayout) == Creditos: Coding With Tea (YOUTUBE)
    DrawerLayout drawerLayout01;
    NavigationView navigationView01;
    Toolbar toolbar01;

    //RecyclerView == Creditos:  Stevdza-San (YOUTUBE)
    //Lista Produtos
    RecyclerView recyclerView_ListCreate;
    TextView no_data_ListCreate;
    S_ConexaoDAO conexaoDAO_ListCreate;
    S_ConexaoDAO conexaoDAO_ListLista_ListCreate;
    ArrayList<String> idListL, nomeList, dataList, checkList, idProduct, idListP, nomeProduct, quantProduct, medidaProduct, tipoProduct, valorProduct, checkProduct;

    S_M01_ListCreate_Adapter s_M01_listCreate_adapter;

    //Criar lista (Adicionar Produto) == Creditos: @Denilson_fa
    S_Dados dados = new S_Dados();

    private EditText nomeProductID;
    private EditText quantProductID;
    private Spinner medidaProductID;
    private Spinner tipoProductID;
    private Button addProductID;

    private TextView nomeListSELECTED01;
    private ImageView reloadListCreate;
    RecyclerView readProductListCreate;

    //private String idProductDADOS, idListPDADOS, nomeProductDADOS, quantProductDADOS, medidaProductDADOS, tipoProductDADOS;
    private String idListLDADOS, nomeListIDDADOS, dataListIDDADOS, checkListIDDADOS;

    //private ArrayList<String> numItensList;
    //private String numItensListS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(1);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.rgb(33,135,255));
        setContentView(R.layout.activity_m01_listcreate);

        //Activity = Menu
        drawerLayout01 =findViewById(R.id.drawer_layout01);
        navigationView01 =findViewById(R.id.nav_view01);
        toolbar01 =findViewById(R.id.toolbar01);

        //Activity = ToolBar
        setSupportActionBar(toolbar01);
        getSupportActionBar().setTitle("");

        //Activity = Navigation Menu
        navigationView01.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout01, toolbar01, R.string.ico_dw_open, R.string.ico_dw_close);
        drawerLayout01.addDrawerListener(toggle);
        toggle.syncState();

        navigationView01.setNavigationItemSelectedListener(this);

        //S_Dados = ID dos Itens (EditText)
        nomeProductID = findViewById(R.id.nomeProduct);
        quantProductID = findViewById(R.id.quantProduct);
        medidaProductID = findViewById(R.id.medidaProduct);
        tipoProductID = findViewById(R.id.tipoProduct);
        addProductID = findViewById(R.id.addProduct);

        nomeListSELECTED01 = findViewById(R.id.nomeListSELECTED01);
        reloadListCreate = findViewById(R.id.reloadListCreate);

        //Recebendo Dados do Produto selecionado
        getAndSetIntentDataLista_ListCreate();

        //Activity = Enabled EditText
        quantProductID.setEnabled(false);
        medidaProductID.setEnabled(false);
        tipoProductID.setEnabled(false);
        addProductID.setEnabled(false);

        //Activity = Função de setEnable
        nomeProductID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().equals("")){
                    quantProductID.setEnabled(false);
                    medidaProductID.setEnabled(false);
                } else {
                    quantProductID.setEnabled(true);
                    medidaProductID.setEnabled(true);
                }
            }
        });

        quantProductID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().equals("")){
                    tipoProductID.setEnabled(false);
                    addProductID.setEnabled(false);
                } else {
                    tipoProductID.setEnabled(true);
                    addProductID.setEnabled(true);
                }
            }
        });

        //RecyclerView
        recyclerView_ListCreate = findViewById(R.id.readProductListCreate);
        no_data_ListCreate = findViewById(R.id.no_data_ListCreate);

        resetStoreProducts_ListCreate();

        //S_Dados = Função ação do botão addProductID
        addProductID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( (nomeProductID.getText().toString().equals("")) || (quantProductID.getText().toString().equals(""))) {
                    Toast.makeText(A_M01_ListCreate.this, R.string.erro_noText01,Toast.LENGTH_LONG).show();
                } else {
                    S_ConexaoDAO crud = new S_ConexaoDAO(getBaseContext());

                    //S_Dados = Guardando S_Dados em variavies
                    //Pega o idListL (id da lista selecionada) e envia para o idListP (id da lista em relação ao produto)
                    dados.setIdListP(dados.getIdListL());

                    //Demais SET
                    dados.setNomeProduct(   nomeProductID.getText().toString()  );
                    dados.setQuantProduct(  Double.parseDouble( quantProductID.getText().toString() )   );
                    dados.setMedidaProduct( medidaProductID.getSelectedItem().toString()    );
                    dados.setTipoProduct(   tipoProductID.getSelectedItem().toString()  );
                    dados.setValorProduct( 0 );
                    dados.setCheckProduct( false );


                    //Adicionando dados ao Banco de Dados
                    crud.createProduct(
                            dados.getIdListP(),
                            dados.getNomeProduct(),
                            dados.getQuantProduct(),
                            dados.getMedidaProduct(),
                            dados.getTipoProduct(),
                            dados.getValorProduct(),
                            dados.getCheckProduct()
                    );

                    //Enconder teclado ao clicar em botão
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    if(imm.isActive()) imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                    //Limpando caixa de textos e voltar para o primeiro
                    nomeProductID.getText().clear();
                    quantProductID.getText().clear();
                    nomeProductID.requestFocus();

                    //Atualizar RecyclerView
                    resetStoreProducts_ListCreate();

                    //Mensagem de confirmação
                    Toast.makeText(A_M01_ListCreate.this, R.string.addedProduct,Toast.LENGTH_LONG).show();
                }
            }
        });

        //Função reloadListCreate
        reloadListCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetStoreProducts_ListCreate();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        //Após retornar da editar/apagar produto, recarrega a lista
        resetStoreProducts_ListCreate();
    }

    //Função Menu
    //Ao sair, apaga lista se não houver nenhum item
    @Override
    public void onBackPressed(){
        S_ConexaoDAO conexaoDAO_ListProductCount = new S_ConexaoDAO(A_M01_ListCreate.this);
        int countItens = conexaoDAO_ListProductCount.numItensListS( dados.getIdListL() );

        if (drawerLayout01.isDrawerOpen(GravityCompat.START)){
            drawerLayout01.closeDrawer(GravityCompat.START);
        } else {
            if( countItens == 0 ) {
                conexaoDAO_ListProductCount.deleteList( String.valueOf(dados.getIdListL()) );
                Toast.makeText(this, R.string.list_clear_delet, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, R.string.list_save, Toast.LENGTH_SHORT).show();
            }
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
        drawerLayout01.closeDrawer(GravityCompat.START); return true;
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
    void storeProducts_ListCreate(){
        //idListL, nomeList, dataList, checkList,
        //idProduct, idListP, nomeProduct, quantProduct, medidaProduct, tipoProduct, valorProduct, checkProduct

        Cursor cursor = conexaoDAO_ListCreate.readProduct(String.valueOf(dados.getIdListL()));
        if(cursor.getCount() == 0) {
            no_data_ListCreate.setVisibility(View.VISIBLE);
            recyclerView_ListCreate.setVisibility(View.GONE);
        } else {
            while (cursor.moveToNext()){
                idListL.add(        cursor.getString(0));
                nomeList.add(       cursor.getString(1));
                dataList.add(       cursor.getString(2));
                checkList.add(      cursor.getString(3));

                idProduct.add(      cursor.getString(4));
                idListP.add(        cursor.getString(5));
                nomeProduct.add(    cursor.getString(6));
                quantProduct.add(   cursor.getString(7));
                medidaProduct.add(  cursor.getString(8));
                tipoProduct.add(    cursor.getString(9));
                valorProduct.add(   cursor.getString(10));
                checkProduct.add(   cursor.getString(11));
            }
            no_data_ListCreate.setVisibility(View.GONE);
            recyclerView_ListCreate.setVisibility(View.VISIBLE);
        }
    }

    //Reset storeProducts
    void resetStoreProducts_ListCreate(){
        conexaoDAO_ListCreate = new S_ConexaoDAO(A_M01_ListCreate.this);
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

        storeProducts_ListCreate();

        s_M01_listCreate_adapter = new S_M01_ListCreate_Adapter(A_M01_ListCreate.this,
                idListL, nomeList, dataList, checkList, idProduct, idListP, nomeProduct, quantProduct,
                medidaProduct, tipoProduct, valorProduct, checkProduct);

        recyclerView_ListCreate.setAdapter(s_M01_listCreate_adapter);
        recyclerView_ListCreate.setLayoutManager(new LinearLayoutManager(A_M01_ListCreate.this));
    }

    //Recebendo dados do item selecionado para editar
    void getAndSetIntentDataLista_ListCreate() {
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
            String nomeList = nomeListIDDADOS+" ( "+ dataListIDDADOS +" )";
            nomeListSELECTED01.setText(nomeList);

        } else {
            //Toast.makeText(this, R.string.erro_product, Toast.LENGTH_SHORT).show();
        }
    }

}
