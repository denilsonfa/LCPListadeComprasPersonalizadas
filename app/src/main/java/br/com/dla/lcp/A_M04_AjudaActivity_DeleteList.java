package br.com.dla.lcp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class A_M04_AjudaActivity_DeleteList extends AppCompatActivity {

    //RecyclerView == Creditos:  Stevdza-San (YOUTUBE)
    //Lista Listas
    RecyclerView recyclerView_ListDelete;
    TextView no_data_ListDelete;
    S_ConexaoDAO conexaoDAO_ListLista04;
    ArrayList<String> selectionList04, idListL04, nomeList04, dataList04, checkList04, totalList04;
    S_M04_ListLista_Adapter s_m04_listLista_adapter;

    S_ConexaoDAO s_conexaoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(1);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.rgb(255,33,33));
        setContentView(R.layout.activity_m04_ajuda_deletelist);

        //RecyclerView
        recyclerView_ListDelete = findViewById(R.id.readListIsNull_setList04);
        no_data_ListDelete = findViewById(R.id.no_data_ListConsult_setList04);

        //Atualizando recyclerView
        resetStoreLists();
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
        Cursor cursor = conexaoDAO_ListLista04.readList();
        if(cursor.getCount() == 0) {
            no_data_ListDelete.setVisibility(View.VISIBLE);
            recyclerView_ListDelete.setVisibility(View.GONE);
        } else {
            while (cursor.moveToNext()){
                idListL04.add(cursor.getString(0));
                nomeList04.add(cursor.getString(1));
                dataList04.add(cursor.getString(2));
                checkList04.add(cursor.getString(3));
                totalList04.add(cursor.getString(4));
            }
            no_data_ListDelete.setVisibility(View.GONE);
            recyclerView_ListDelete.setVisibility(View.VISIBLE);
        }
    }

    //Metodo
    void resetStoreLists() {
        conexaoDAO_ListLista04 = new S_ConexaoDAO(A_M04_AjudaActivity_DeleteList.this);
        idListL04 = new ArrayList<>();
        nomeList04 = new ArrayList<>();
        dataList04 = new ArrayList<>();
        checkList04 = new ArrayList<>();
        totalList04 = new ArrayList<>();

        storeLists();

        s_m04_listLista_adapter = new S_M04_ListLista_Adapter(A_M04_AjudaActivity_DeleteList.this, selectionList04, idListL04, nomeList04, dataList04, checkList04, totalList04);
        recyclerView_ListDelete.setAdapter(s_m04_listLista_adapter);
        recyclerView_ListDelete.setLayoutManager(new LinearLayoutManager(A_M04_AjudaActivity_DeleteList.this));
    }

}