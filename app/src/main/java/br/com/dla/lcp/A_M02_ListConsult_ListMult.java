package br.com.dla.lcp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

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

public class A_M02_ListConsult_ListMult extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    //Menu (DrawerLayout) == Creditos: Coding With Tea
    DrawerLayout drawerLayout02_mult;
    NavigationView navigationView02_mult;
    Toolbar toolbar02_mult;

    //RecyclerView == Creditos:  Stevdza-San (YOUTUBE)
    //Lista Produtos
    RecyclerView recyclerView_ListConsult_mult;
    TextView no_data_ListConsult_mult;
    LinearLayout list_concluida;
    S_ConexaoDAO conexaoDAO_ListConsult_mult;
    S_ConexaoDAO conexaoDAO_ListLista_ListConsult;
    ArrayList<String> idListL, nomeList, dataList, checkList, idProduct, idListP, nomeProduct, quantProduct, medidaProduct, tipoProduct, valorProduct, checkProduct;

    S_M02_ListConsultMult_Adapter s_M02_listConsultMult_adapter;

    //Criar lista (Adicionar Produto) == Creditos: @Denilson_fa
    S_Dados dados = new S_Dados();

    private TextView nomeListSELECTED02, nomeListSELECTED02b;
    private ImageView reloadListConsult;

    //private String idProductDADOS, idListPDADOS, nomeProductDADOS, quantProductDADOS, medidaProductDADOS, tipoProductDADOS;
    private String idListLDADOS, nomeListIDDADOS, dataListIDDADOS, checkListIDDADOS;

    //PopUp EditProduct

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(1);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.rgb(14,91,182));
        setContentView(R.layout.activity_m02_listconsult_listmult);

        //Activity = Menu
        drawerLayout02_mult =findViewById(R.id.drawer_layout02_mult);
        navigationView02_mult =findViewById(R.id.nav_view02_mult);
        toolbar02_mult =findViewById(R.id.toolbar02_mult);

        //Activity = ToolBar
        setSupportActionBar(toolbar02_mult);
        getSupportActionBar().setTitle("");

        //Activity = Navigation Menu
        navigationView02_mult.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout02_mult, toolbar02_mult, R.string.ico_dw_open, R.string.ico_dw_close);
        drawerLayout02_mult.addDrawerListener(toggle);
        toggle.syncState();

        navigationView02_mult.setNavigationItemSelectedListener(this);

        //S_Dados = ID dos Itens
        nomeListSELECTED02 = findViewById(R.id.nomeListSELECTED02_mult);
        nomeListSELECTED02b = findViewById(R.id.nomeListSELECTED02b_mult);
        reloadListConsult = findViewById(R.id.reloadListConsult_mult);
        list_concluida = findViewById(R.id.list_concluida_mult);

        //Recebendo Dados do Produto selecionado
        getAndSetIntentDataLista_ListConsult();

        //RecyclerView
        recyclerView_ListConsult_mult = findViewById(R.id.readProductListConsult_mult);
        no_data_ListConsult_mult = findViewById(R.id.no_data_ListConsult_mult);

        resetStoreProducts_ListConsult();

        //Função reloadListCreate
        reloadListConsult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetStoreProducts_ListConsult();
            }
        });

    }

    public static void tst(final Context context, final String idListL, String nomeProductS) {


        final A_M02_ListConsult_ListMult a = new A_M02_ListConsult_ListMult();

        FrameLayout.LayoutParams params = new  FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final EditText input = new EditText(context);
        FrameLayout container = new FrameLayout(context);

        builder.setTitle(nomeProductS);
        builder.setMessage(R.string.sltList_obs02_editMult);

        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        input.setFilters(new InputFilter[] { new InputFilter.LengthFilter(30) });
        input.setHint(R.string.valorProduct);
        input.setSingleLine();

        params.leftMargin = container.getResources().getDimensionPixelSize(R.dimen.dp_19);
        params.rightMargin = container.getResources().getDimensionPixelSize(R.dimen.dp_19);

        input.setLayoutParams(params);
        container.addView(input);
        builder.setView(container);

        // Set up the buttons
        builder.setNeutralButton(R.string.sltList_obs02_incart, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //Pegando dados do input
                String valorProductText = input.getText().toString();

                if(valorProductText.equals("") || valorProductText.equals("0")){
                    Toast.makeText(context, R.string.erro_noText01,Toast.LENGTH_LONG).show();
                } else {

                    Double valorProduct = Double.parseDouble( valorProductText );
                    boolean checkProduct = true;

                    //Editando dados do produto
                    S_ConexaoDAO conexaoDAO = new S_ConexaoDAO(context);
                    conexaoDAO.updateProductValor( idListL, valorProduct, checkProduct );

                    //Mensagem de confirmação
                    Toast.makeText(context, R.string.editedProduct_addValor,Toast.LENGTH_LONG).show();

                    //Reload Activity
                    ((Activity) context).recreate();
                }

            }
        });

        builder.setNegativeButton(R.string.sltList_obs02_nocart, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //Por ser a opção de que não está  no carrinho, o produto tem o valorProduct zerado e o checkProduct false
                Double valorProduct = 0.0;
                boolean checkProduct = false;

                //Editando dados do produto
                S_ConexaoDAO conexaoDAO = new S_ConexaoDAO(context);
                conexaoDAO.updateProductValor( idListL, valorProduct, checkProduct );

                //Mensagem de confirmação
                Toast.makeText(context, R.string.editedProduct_addValor,Toast.LENGTH_LONG).show();

                //Reload Activity
                ((Activity) context).recreate();
            }
        });

        builder.show();

    }

    @Override
    protected void onResume() {
        super.onResume();

        //Após retornar da editar/apagar produto, recarrega a lista
        resetStoreProducts_ListConsult();
    }

    @Override
    public void onBackPressed() {

        S_ConexaoDAO conexaoDAO_ListProductCount = new S_ConexaoDAO(A_M02_ListConsult_ListMult.this);
        S_ConexaoDAO conexaoDAO_ListProductCountCheck = new S_ConexaoDAO(A_M02_ListConsult_ListMult.this);
        int countItens = conexaoDAO_ListProductCount.numItensListS( dados.getIdListL() );
        int countItensCheck = conexaoDAO_ListProductCountCheck.numItensCheck( dados.getIdListL() );

        if( countItensCheck == countItens ) {
            //se for, ficha a lista
            conexaoDAO_ListProductCount.updateListCheck( String.valueOf(dados.getIdListL()), true );
            Toast.makeText(this, R.string.list_concluida, Toast.LENGTH_SHORT).show();
            finish();

        } else {
            //se não pergunta se deseja fechar
            AlertDialog.Builder builder = new AlertDialog.Builder(A_M02_ListConsult_ListMult.this);
            builder.setTitle(R.string.sltProduct02_exitList);
            builder.setMessage(R.string.sltProduct02_exitListObs);
            //builder.setIcon(R.drawable.ic_item_alert);

            //define um botão negativo
            builder.setPositiveButton(R.string.nao, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    S_ConexaoDAO conexaoDAO_bd = new S_ConexaoDAO(A_M02_ListConsult_ListMult.this);
                    conexaoDAO_bd.updateListCheck( String.valueOf(dados.getIdListL()), false );
                    Toast.makeText(A_M02_ListConsult_ListMult.this, R.string.list_save, Toast.LENGTH_SHORT).show();
                    finish();
                }
            });

            //define um botão positivo
            builder.setNegativeButton(R.string.sim, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    S_ConexaoDAO conexaoDAO_bd = new S_ConexaoDAO(A_M02_ListConsult_ListMult.this);
                    conexaoDAO_bd.updateListCheck( String.valueOf(dados.getIdListL()), true );
                    Toast.makeText(A_M02_ListConsult_ListMult.this, R.string.list_concluida, Toast.LENGTH_SHORT).show();
                    finish();

                    Intent intent = new Intent(A_M02_ListConsult_ListMult.this, A_M03_ListExtract.class);
                    intent.putExtra("idListLID", String.valueOf(dados.getIdListL()));
                    intent.putExtra("nomeListID", String.valueOf(dados.getNomeList()));
                    intent.putExtra("dataListID", String.valueOf(dados.getDataList()));
                    intent.putExtra("checkListID", String.valueOf(dados.getCheckList()));
                    A_M02_ListConsult_ListMult.this.startActivity(intent);

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
        drawerLayout02_mult.closeDrawer(GravityCompat.START); return true;
    }


    //RecyclerView
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

//    public static void editPopup(final int idProduct, String nomeProduct, double valorProduct){
//
//        final TextView nomeProductEDIT_TXT_M;
//        final EditText valorProductEDIT_EDIT_M;
//        final Button cancelEDIT_EDIT_M, confirmEDIT_EDIT_M;
//
//        nomeProductEDIT_TXT_M = (TextView) dialogView.findViewById(R.id.nomeProductEDIT_TXT_M);
//        valorProductEDIT_EDIT_M = (EditText) dialogView.findViewById(R.id.valorProductEDIT_EDIT_M);
//        cancelEDIT_EDIT_M = (Button) dialogView.findViewById(R.id.cancelEDIT_EDIT_M);
//        confirmEDIT_EDIT_M = (Button) dialogView.findViewById(R.id.confirmEDIT_EDIT_M);
//
//        //nomeProductEDIT_TXT_M
//        nomeProductEDIT_TXT_M.setText(nomeProduct);
//
//        //valorProductEDIT_EDIT_M
//        if( valorProduct == 0 ){
//            valorProductEDIT_EDIT_M.setText("");
//        } else {
//            valorProductEDIT_EDIT_M.setText( String.valueOf( valorProduct ) );
//        }
//
//        //cancelEDIT_EDIT_M
//        cancelEDIT_EDIT_M.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //S_Dados = Guardando S_Dados em variavies
//                String zero = "0";
//                valorProductEDIT_EDIT_M.setText(zero);
//                String idProductS = String.valueOf( idProduct );
//                Double valorProduct = Double.parseDouble( valorProductEDIT_EDIT_M.getText().toString() );
//                boolean checkProduct = false;
//
//                //Editando dados do produto
//                S_ConexaoDAO conexaoDAO = new S_ConexaoDAO(context);
//                conexaoDAO.updateProductValor( idProductS, valorProduct, checkProduct );
//
//                //Mensagem de confirmação
//                Toast.makeText(context, R.string.editedProduct_addValor,Toast.LENGTH_LONG).show();
//            }
//        });
//
//        //cancelEDIT_EDIT_M
//        confirmEDIT_EDIT_M.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //S_Dados = Guardando S_Dados em variavies
//                String idProductS = String.valueOf( idProduct );
//                Double valorProduct = Double.parseDouble( valorProductEDIT_EDIT_M.getText().toString() );
//                boolean checkProduct = true;
//
//                //Editando dados do produto
//                S_ConexaoDAO conexaoDAO = new S_ConexaoDAO(context);
//                conexaoDAO.updateProductValor( idProductS, valorProduct, checkProduct );
//
//                //Mensagem de confirmação
//                Toast.makeText(context, R.string.editedProduct_addValor,Toast.LENGTH_LONG).show();
//
//            }
//        });
//
//        builder.setView(dialogView);
//        builder.setCancelable(true);
//        builder.show();
//
//    }

    //Lista Produtos
    void storeProducts_ListConsult(){
        S_ConexaoDAO conexaoDAO_ListProductCount = new S_ConexaoDAO(A_M02_ListConsult_ListMult.this);
        S_ConexaoDAO conexaoDAO_ListProductCountCheck = new S_ConexaoDAO(A_M02_ListConsult_ListMult.this);
        int countItens = conexaoDAO_ListProductCount.numItensListS( dados.getIdListL() );
        int countItensCheck = conexaoDAO_ListProductCountCheck.numItensCheck( dados.getIdListL() );

        if( countItensCheck == countItens ) {
            list_concluida.setVisibility(View.VISIBLE);
        } else {
            list_concluida.setVisibility(View.GONE);
        }

        //idListL, nomeList, dataList, checkList,
        //idProduct, idListP, nomeProduct, quantProduct, medidaProduct, tipoProduct, valorProduct, checkProduct

        Cursor cursor = conexaoDAO_ListConsult_mult.readProduct(String.valueOf(dados.getIdListL()));
        if(cursor.getCount() == 0) {
            no_data_ListConsult_mult.setVisibility(View.VISIBLE);
            recyclerView_ListConsult_mult.setVisibility(View.GONE);
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
            no_data_ListConsult_mult.setVisibility(View.GONE);
            recyclerView_ListConsult_mult.setVisibility(View.VISIBLE);
        }
    }

    //Reset storeProducts
    void resetStoreProducts_ListConsult(){
        //Toast.makeText(A_M02_ListConsult_ListMult.this, "TESTE", Toast.LENGTH_SHORT).show();
        conexaoDAO_ListConsult_mult = new S_ConexaoDAO(A_M02_ListConsult_ListMult.this);
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

        s_M02_listConsultMult_adapter = new S_M02_ListConsultMult_Adapter(A_M02_ListConsult_ListMult.this,
                idListL, nomeList, dataList, checkList, idProduct, idListP, nomeProduct, quantProduct,
                medidaProduct, tipoProduct, valorProduct, checkProduct);

        recyclerView_ListConsult_mult.setAdapter(s_M02_listConsultMult_adapter);
        recyclerView_ListConsult_mult.setLayoutManager(new LinearLayoutManager(A_M02_ListConsult_ListMult.this));
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
        S_ConexaoDAO conexaoDAO_ListProductCount = new S_ConexaoDAO(A_M02_ListConsult_ListMult.this);
        S_ConexaoDAO conexaoDAO_ListProductCountCheck = new S_ConexaoDAO(A_M02_ListConsult_ListMult.this);
        int countItens = conexaoDAO_ListProductCount.numItensListS( dados.getIdListL() );
        int countItensCheck = conexaoDAO_ListProductCountCheck.numItensCheck( dados.getIdListL() );

        if( countItensCheck == countItens ) {
            conexaoDAO_ListProductCount.updateListCheck( String.valueOf(dados.getIdListL()), true );
            Toast.makeText(this, R.string.list_concluida, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.list_save, Toast.LENGTH_SHORT).show();
        }

    }

}