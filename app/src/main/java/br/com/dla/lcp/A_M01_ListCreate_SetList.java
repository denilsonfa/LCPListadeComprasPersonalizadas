package br.com.dla.lcp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;

public class A_M01_ListCreate_SetList extends AppCompatActivity {

    //RecyclerView == Creditos:  Stevdza-San (YOUTUBE)
    //Lista Listas
    RecyclerView recyclerView_ListCreate;
    TextView no_data_ListCreate, tmp;
    S_ConexaoDAO conexaoDAO_ListLista01;
    ArrayList<String> selectionList01, idListL01, nomeList01, dataList01, checkList01;
    S_M01_ListLista_Adapter s_m01_list_lista_adapter;

    S_ConexaoDAO s_conexaoDAO;

    private Button addNewList01;

    //adView
    AdView adViewBanner01SetList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(1);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.rgb(33,135,255));
        setContentView(R.layout.activity_m01_listcreate_setlist);

        //Google AdMod
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });

        //adView
        //CODIGO adMob (BANNER): ca-app-pub-7912320570829252/9616887336
        //CODIGO para testes: ca-app-pub-3940256099942544/6300978111

        adViewBanner01SetList = findViewById(R.id.adViewBanner01SetList);
        AdRequest adRequest = new AdRequest.Builder().build();
        adViewBanner01SetList.loadAd(adRequest);

        //RecyclerView
        recyclerView_ListCreate = (RecyclerView) findViewById(R.id.readListIsNull_setList01);
        no_data_ListCreate = (TextView) findViewById(R.id.no_data_ListConsult_setList01);
        addNewList01 = (Button) findViewById(R.id.addNewList_setList01);

        tmp = (TextView) findViewById(R.id.tmp);

        //Atualizando recyclerView
        resetStoreLists();

        addNewList01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Criando nova lista
                long numListDia = S_Dados.getDia();
                long numListMes = S_Dados.getMes();

                conexaoDAO_ListLista01.createList(numListDia, numListMes);

                //Atualizando recyclerView
                resetStoreLists();

                long idUltimaLista = conexaoDAO_ListLista01.idUltimaList();
                String readListName = conexaoDAO_ListLista01.readListName(idUltimaLista);
                String readListData = conexaoDAO_ListLista01.readListData(idUltimaLista);
                String readListChk = conexaoDAO_ListLista01.readListChk(idUltimaLista);

                Intent intent = new Intent(A_M01_ListCreate_SetList.this, A_M01_ListCreate.class);
                intent.putExtra("idListLID", String.valueOf(idUltimaLista));
                intent.putExtra("nomeListID", String.valueOf(readListName));
                intent.putExtra("dataListID", String.valueOf(readListData));
                intent.putExtra("checkListID", String.valueOf(readListChk));
                A_M01_ListCreate_SetList.this.startActivity(intent);

                Toast.makeText(A_M01_ListCreate_SetList.this, R.string.list_save, Toast.LENGTH_SHORT).show();
                finish();

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Após retornar da editar/apagar produto, recarrega a lista
        resetStoreLists();
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
        Cursor cursor = conexaoDAO_ListLista01.readListCheck();
        if(cursor.getCount() == 0) {
            no_data_ListCreate.setVisibility(View.VISIBLE);
            recyclerView_ListCreate.setVisibility(View.GONE);
        } else {
            while (cursor.moveToNext()){
                idListL01.add(cursor.getString(0));
                nomeList01.add(cursor.getString(1));
                dataList01.add(cursor.getString(2));
                checkList01.add(cursor.getString(3));
            }
            no_data_ListCreate.setVisibility(View.GONE);
            recyclerView_ListCreate.setVisibility(View.VISIBLE);
        }
    }

    //Metodo
    void resetStoreLists() {
        conexaoDAO_ListLista01 = new S_ConexaoDAO(A_M01_ListCreate_SetList.this);
        idListL01 = new ArrayList<>();
        nomeList01 = new ArrayList<>();
        dataList01 = new ArrayList<>();
        checkList01 = new ArrayList<>();

        storeLists();

        s_m01_list_lista_adapter = new S_M01_ListLista_Adapter(A_M01_ListCreate_SetList.this, selectionList01, idListL01, nomeList01, dataList01, checkList01);
        recyclerView_ListCreate.setAdapter(s_m01_list_lista_adapter);
        recyclerView_ListCreate.setLayoutManager(new LinearLayoutManager(A_M01_ListCreate_SetList.this));
    }

}