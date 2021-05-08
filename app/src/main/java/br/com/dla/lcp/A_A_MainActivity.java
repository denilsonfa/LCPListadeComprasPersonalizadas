package br.com.dla.lcp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class A_A_MainActivity extends AppCompatActivity {

    //RecyclerView == Creditos:  Stevdza-San (YOUTUBE)
    //Graficos das Listas
    RecyclerView recyclerView_ProductGrafic_SetList;
    TextView no_data_ProductGrafic_SetList;
    S_ConexaoDAO conexaoDAO_ListLista00;
    ArrayList<String> selectionList00, idListL00, nomeList00, dataList00, checkList00, totalList00;

    //TMP
    S_H00_ListLista_Adapter s_m00_list_lista_adapter;

    //Valor Maximo para geração do calculo do grafico
    float valorMax = 0;

    //id da ultima lista / ou lista consultada referente ao grafico
    long idUltimaLista;
    private TextView porcent0, porcent20, porcent50, porcent80, porcent100;
    private TextView nomeListITEM00_header01, valorListITEM00_header01;
    private LinearLayout option01, option02, option03;

    //itens do menu
    LinearLayout main_menu01, main_menu02, main_menu03, main_menu04, main_menu05, main_menu06, main_menu07, main_menu08;
    Animation fadein, fadeout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(1);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.rgb(149,16,149));
        setContentView(R.layout.activity_a_main);

        //Animação
        fadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
        fadeout = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadeout);

        //RecyclerView
        recyclerView_ProductGrafic_SetList = findViewById(R.id.readProductListGrafico_l);
        no_data_ProductGrafic_SetList = findViewById(R.id.no_data_ListGrafico_l);

        nomeListITEM00_header01 = findViewById(R.id.nomeListITEM05_header01);
        valorListITEM00_header01 = findViewById(R.id.valorListITEM05_header01);

        //option01, option02, option03
        //Conforme a quantidade de listas finalizadas, ele irá mostrar um header diferente
        option01 = findViewById(R.id.option01);
        option02 = findViewById(R.id.option02);
        option03 = findViewById(R.id.option03);

        //LinearLayout
        //main_menu01, main_menu02, main_menu03, main_menu04, main_menu05, main_menu06, main_menu07, main_menu08;
        main_menu01 = findViewById(R.id.main_menu01);
        main_menu02 = findViewById(R.id.main_menu02);
        main_menu03 = findViewById(R.id.main_menu03);
        main_menu04 = findViewById(R.id.main_menu04);
        main_menu05 = findViewById(R.id.main_menu05);
        main_menu06 = findViewById(R.id.main_menu06);
        main_menu07 = findViewById(R.id.main_menu07);
        main_menu08 = findViewById(R.id.main_menu08);

        main_menu07.setVisibility(View.GONE);
        main_menu08.setVisibility(View.GONE);

        main_menu06.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                //Acesso a Activity Ocultas
                Toast.makeText(A_A_MainActivity.this, "Acesso a Activity Ocultas", Toast.LENGTH_SHORT).show();
                main_menu07.setVisibility(View.VISIBLE);
                main_menu08.setVisibility(View.VISIBLE);

                main_menu07.startAnimation(fadein);
                main_menu08.startAnimation(fadein);
                return false;
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();

        S_ConexaoDAO s_conexaoDAO1 = new S_ConexaoDAO(A_A_MainActivity.this);
        int countListCheck = s_conexaoDAO1.numListaCheck();

        if(countListCheck >= 2) {
            //Se numero de listas for maior que 2, aparece grafico

            option01.setVisibility(View.GONE);
            option02.setVisibility(View.GONE);
            option03.setVisibility(View.VISIBLE);

            //Definir valor maximo para gerar grafico
            S_ConexaoDAO conexaoDAO_ListProductCountCheck = new S_ConexaoDAO(A_A_MainActivity.this);
            valorMax = conexaoDAO_ListProductCountCheck.maxTotalList();

            //definir qual lista irá abrir automativamente assim que iniciar activity
            long idListaSelected = S_H05_ListLista_Adapter.idUltimaListaSelected;

            if( idListaSelected == 0) {
                setIdUltimaLista( conexaoDAO_ListProductCountCheck.idUltimaListCheck() );
            } else {
                setIdUltimaLista(idListaSelected);
            }

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

            resetStoreGrafic(valorMax);

            main_menu05.setVisibility(View.VISIBLE);

        } else if (countListCheck == 1) {
            //se for somente 1

            option01.setVisibility(View.GONE);
            option02.setVisibility(View.VISIBLE);
            option03.setVisibility(View.GONE);

            nomeListITEM00_header01.setText(R.string.nomeList);

            //Consultar nome da lista ultima lista finalizada
            S_ConexaoDAO s_conexaoDAO = new S_ConexaoDAO(A_A_MainActivity.this);
            long ultimaLista = s_conexaoDAO.idUltimaListCheck();
            String readListName = "ID: "+ultimaLista+" - "+s_conexaoDAO.readListName( ultimaLista );

            //Consultar valor total da lista atual
            double totalValor = Double.parseDouble( s_conexaoDAO.readListTL( ultimaLista ) );
            totalValor = (Math.rint (totalValor * 100.0) / 100.0);
            String totVal = "R$ "+totalValor;

            //imprimir dados
            nomeListITEM00_header01.setText(readListName);
            valorListITEM00_header01.setText(totVal);

            main_menu05.setVisibility(View.VISIBLE);

        } else {
            //não tiver nenhuma lista ou, algum erro

            option01.setVisibility(View.VISIBLE);
            option02.setVisibility(View.GONE);
            option03.setVisibility(View.GONE);

            main_menu05.setVisibility(View.GONE);

        }

    }

    //Lista Listas
    void storeGrafic(){
        Cursor cursor = conexaoDAO_ListLista00.readListIsCheck();
        if(cursor.getCount() == 0) {
            no_data_ProductGrafic_SetList.setVisibility(View.VISIBLE);
            recyclerView_ProductGrafic_SetList.setVisibility(View.GONE);
        } else {
            while (cursor.moveToNext()){
                idListL00.add(cursor.getString(0));
                nomeList00.add(cursor.getString(1));
                dataList00.add(cursor.getString(2));
                checkList00.add(cursor.getString(3));
                totalList00.add(cursor.getString(4));
            }
            no_data_ProductGrafic_SetList.setVisibility(View.GONE);
            recyclerView_ProductGrafic_SetList.setVisibility(View.VISIBLE);
        }
    }


    //Metodo
    void resetStoreGrafic(float valorMax05) {
        conexaoDAO_ListLista00 = new S_ConexaoDAO(A_A_MainActivity.this);
        idListL00 = new ArrayList<>();
        nomeList00 = new ArrayList<>();
        dataList00 = new ArrayList<>();
        checkList00 = new ArrayList<>();
        totalList00 = new ArrayList<>();

        storeGrafic();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView myList = (RecyclerView) findViewById(R.id.readProductListGrafico_l);
        myList.setLayoutManager(layoutManager);

        s_m00_list_lista_adapter = new S_H00_ListLista_Adapter(A_A_MainActivity.this,
                selectionList00, idListL00, nomeList00, dataList00, checkList00, totalList00, valorMax05);
        recyclerView_ProductGrafic_SetList.setAdapter(s_m00_list_lista_adapter);
    }


    //RecyclerView
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    //idUltimaLista Getter e Setter
    public long getIdUltimaLista() {
        return idUltimaLista;
    }
    public void setIdUltimaLista(long idUltimaLista) {
        this.idUltimaLista = idUltimaLista;
    }


    boolean singleBack = false;
    @Override
    public void onBackPressed() {
        if (singleBack) {
            super.onBackPressed();
            return;
        }
        this.singleBack = true;

        Toast.makeText(this, R.string.doubleExit, Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                singleBack=false;
            }
        }, 2000);

    }

    //Funções do menu principal
    public void main_menu01(View view){
        //Criar Lista
        //Toast.makeText(A_A_MainActivity.this, R.string.main_menu01,Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, A_M01_ListCreate_SetList.class);
        startActivity(intent);
    }

    public void main_menu02(View view){
        //Consultar Lista
        //Toast.makeText(A_A_MainActivity.this, R.string.main_menu02,Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, A_M02_ListConsult_SetList.class);
        startActivity(intent);
    }

    public void main_menu03(View view){
        //Conferir Extrato
        //Toast.makeText(A_A_MainActivity.this, R.string.main_menu03,Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, A_M03_ListExtrato_SetList.class);
        startActivity(intent);
    }

    public void main_menu04(View view){
        //Ajuda e Ajustes
        //Toast.makeText(A_A_MainActivity.this, R.string.main_menu04,Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, A_M04_AjudaActivity.class);
        startActivity(intent);
    }

    public void main_menu05(View view){
        //Grafico de Consumo
        //Toast.makeText(A_A_MainActivity.this, R.string.main_menu05,Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, A_M05_ProductGrafic.class);
        startActivity(intent);
    }

    public void main_menu06(View view){
        //Sobre
        //Toast.makeText(A_A_MainActivity.this, R.string.main_menu06,Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, A_M06_InfoActivity.class);
        startActivity(intent);
    }

    public void main_menu07(View view){
        //SplashScreen
        //Toast.makeText(A_A_MainActivity.this, R.string.main_menu07,Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, A_A_SplashScreenActivity.class);
        startActivity(intent);
    }

    public void main_menu08(View view){
        //Onboarding
        //Toast.makeText(A_A_MainActivity.this, R.string.main_menu08,Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, A_A_OnBoardingActivity.class);
        startActivity(intent);
    }

}