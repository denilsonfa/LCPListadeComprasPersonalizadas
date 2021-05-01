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

public class A_M03_ListExtrato_SetList extends AppCompatActivity {

    //RecyclerView == Creditos:  Stevdza-San (YOUTUBE)
    //Lista Listas
    RecyclerView recyclerView_ListConsult_SetList;
    TextView no_data_ListCreate;
    S_ConexaoDAO conexaoDAO_ListLista03;
    ArrayList<String> selectionList03, idListL03, nomeList03, dataList03, checkList03;
    S_M03_ListLista_Adapter s_m03_list_lista_adapter;

    S_ConexaoDAO s_conexaoDAO;

    private Button addNewList03;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(1);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.rgb(149,16,149));
        setContentView(R.layout.activity_m03_listextrato_setlist);

        //RecyclerView
        recyclerView_ListConsult_SetList = (RecyclerView) findViewById(R.id.readListIsNull_setList03);
        no_data_ListCreate = (TextView) findViewById(R.id.no_data_ListConsult_setList03);
        addNewList03 = (Button) findViewById(R.id.addNewList_setList03);

        //Atualizando recyclerView
        resetStoreLists();

        addNewList03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Criando nova lista
                long numListDia = S_Dados.getDia();
                long numListMes = S_Dados.getMes();

                conexaoDAO_ListLista03.createList(numListDia, numListMes);

                //Atualizando recyclerView
                resetStoreLists();

                long idUltimaLista = conexaoDAO_ListLista03.idUltimaList();
                String readListName = conexaoDAO_ListLista03.readListName(idUltimaLista);
                String readListData = conexaoDAO_ListLista03.readListData(idUltimaLista);
                String readListChk = conexaoDAO_ListLista03.readListChk(idUltimaLista);

                Intent intent = new Intent(A_M03_ListExtrato_SetList.this, A_M01_ListCreate.class);
                intent.putExtra("idListLID", String.valueOf(idUltimaLista));
                intent.putExtra("nomeListID", String.valueOf(readListName));
                intent.putExtra("dataListID", String.valueOf(readListData));
                intent.putExtra("checkListID", String.valueOf(readListChk));
                A_M03_ListExtrato_SetList.this.startActivity(intent);

                Toast.makeText(A_M03_ListExtrato_SetList.this, R.string.list_save, Toast.LENGTH_SHORT).show();
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
        Cursor cursor = conexaoDAO_ListLista03.readListIsCheck();
        if(cursor.getCount() == 0) {
            no_data_ListCreate.setVisibility(View.VISIBLE);
            recyclerView_ListConsult_SetList.setVisibility(View.GONE);
        } else {
            while (cursor.moveToNext()){
                idListL03.add(cursor.getString(0));
                nomeList03.add(cursor.getString(1));
                dataList03.add(cursor.getString(2));
                checkList03.add(cursor.getString(3));
            }
            no_data_ListCreate.setVisibility(View.GONE);
            recyclerView_ListConsult_SetList.setVisibility(View.VISIBLE);
        }
    }

    //Metodo
    void resetStoreLists() {
        conexaoDAO_ListLista03 = new S_ConexaoDAO(A_M03_ListExtrato_SetList.this);
        idListL03 = new ArrayList<>();
        nomeList03 = new ArrayList<>();
        dataList03 = new ArrayList<>();
        checkList03 = new ArrayList<>();

        storeLists();

        s_m03_list_lista_adapter = new S_M03_ListLista_Adapter(A_M03_ListExtrato_SetList.this, selectionList03, idListL03, nomeList03, dataList03, checkList03);
        recyclerView_ListConsult_SetList.setAdapter(s_m03_list_lista_adapter);
        recyclerView_ListConsult_SetList.setLayoutManager(new LinearLayoutManager(A_M03_ListExtrato_SetList.this));
    }
}