package br.com.dla.lcp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class A_H_Grafic extends AppCompatActivity {

    //RecyclerView == Creditos:  Stevdza-San (YOUTUBE)
    //Lista Listas
    RecyclerView recyclerView_ProductGrafic_SetList;
    TextView no_data_ProductGrafic;
    S_ConexaoDAO conexaoDAO_ListLista05;
    ArrayList<String> selectionList05, idListL05, nomeList05, dataList05, checkList05;
    S_M03_ListLista_Adapter s_m03_list_lista_adapter;
    S_M05_ListLista_Adapter s_m05_list_lista_adapter;

    S_ConexaoDAO s_conexaoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.header_a_grafic);

        //RecyclerView
        //recyclerView_ProductGrafic_SetList = (RecyclerView) findViewById(R.id.readProductListGrafico_l);

        recyclerView_ProductGrafic_SetList = (RecyclerView) findViewById(R.id.readProductListGrafico_l);
        no_data_ProductGrafic = (TextView) findViewById(R.id.no_data_ListGrafico_l);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(A_H_Grafic.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView_ProductGrafic_SetList.setLayoutManager(linearLayoutManager);

        //Atualizando recyclerView
//        resetStoreLists();
    }

//
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        //Após retornar da editar/apagar produto, recarrega a lista
//        resetStoreLists();
//    }
//
//    //RecyclerView
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == 1){
//            recreate();
//        }
//    }
//
//    //Lista Listas
//    void storeLists(){
//        Cursor cursor = conexaoDAO_ListLista05.readListIsCheck();
//        if(cursor.getCount() == 0) {
//            no_data_ProductGrafic.setVisibility(View.VISIBLE);
//            recyclerView_ProductGrafic_SetList.setVisibility(View.GONE);
//        } else {
//            while (cursor.moveToNext()){
//                idListL05.add(cursor.getString(0));
//                nomeList05.add(cursor.getString(1));
//                dataList05.add(cursor.getString(2));
//                checkList05.add(cursor.getString(3));
//            }
//            no_data_ProductGrafic.setVisibility(View.GONE);
//            recyclerView_ProductGrafic_SetList.setVisibility(View.VISIBLE);
//        }
//    }
//
//    //Metodo
//    void resetStoreLists() {
//        conexaoDAO_ListLista05 = new S_ConexaoDAO(A_H_Grafic.this);
//        idListL05 = new ArrayList<>();
//        nomeList05 = new ArrayList<>();
//        dataList05 = new ArrayList<>();
//        checkList05 = new ArrayList<>();
//
//        storeLists();
//
//        //S_M03_ListLista_Adapter s_m03_list_lista_adapter;
//        //S_M05_ListLista_Adapter s_m05_list_lista_adapter;
//
//        recyclerView_ProductGrafic_SetList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
//        s_m05_list_lista_adapter = new S_M05_ListLista_Adapter(A_H_Grafic.this, selectionList05, idListL05, nomeList05, dataList05, checkList05);
//        recyclerView_ProductGrafic_SetList.setAdapter(s_m05_list_lista_adapter);
//        recyclerView_ProductGrafic_SetList.setLayoutManager(new LinearLayoutManager(A_H_Grafic.this));
//
//    }

}