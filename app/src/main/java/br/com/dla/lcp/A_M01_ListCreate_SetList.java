package br.com.dla.lcp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class A_M01_ListCreate_SetList extends AppCompatActivity {

    //RecyclerView == Creditos:  Stevdza-San (YOUTUBE)
    //Lista Listas
    RecyclerView recyclerView_ListCreate;
    TextView no_data_ListCreate;
    S_ConexaoDAO conexaoDAO_ListLista01;
    ArrayList<String> selectionList01, idListL01, nomeList01, dataList01, checkList01;
    S_M01_ListLista_Adapter s_m01_list_lista_adapter;

    S_ConexaoDAO s_conexaoDAO;

    private Button addNewList01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(1);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.rgb(33,135,255));
        setContentView(R.layout.activity_m01_listcreate_setlist);

        //RecyclerView
        recyclerView_ListCreate = (RecyclerView) findViewById(R.id.readListIsNull_setList01);
        no_data_ListCreate = (TextView) findViewById(R.id.no_data_ListConsult_setList01);
        addNewList01 = (Button) findViewById(R.id.addNewList_setList01);

        //Atualizando recyclerView
        resetStoreLists();

        addNewList01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Criando nova lista
                conexaoDAO_ListLista01.createList(conexaoDAO_ListLista01.numList());

                //Atualizando recyclerView
                resetStoreLists();

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