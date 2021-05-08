package br.com.dla.lcp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class S_H05_ListLista_Adapter extends RecyclerView.Adapter<S_H05_ListLista_Adapter.HolderListConsult> {

    private Context context;
    private ArrayList selectionList, idListL, nomeList, dataList, checkList, totalList;
    int position;
    double valorMax, totalListInt, porcentagem = 0;
    protected static long idUltimaListaSelected = 0;
    String idNomeLista;

    //Criar lista (Adicionar Produto) == Creditos: @Denilson_fa
    S_Dados dados = new S_Dados();

    S_H05_ListLista_Adapter(Context context,
                            ArrayList selectionList,
                            ArrayList idListL,
                            ArrayList nomeList,
                            ArrayList dataList,
                            ArrayList checkList,
                            ArrayList totalList,
                            double    valorMax) {
        this.context = context;
        this.selectionList = selectionList;
        this.idListL = idListL;
        this.nomeList = nomeList;
        this.dataList = dataList;
        this.checkList = checkList;
        this.totalList = totalList;
        this.valorMax = valorMax;
    }

    @NonNull
    @Override
    public HolderListConsult onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.body_b05_graficbar, parent, false);
        return new HolderListConsult(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderListConsult holder, final int position) {

        //totalList
        totalListInt = Double.parseDouble( String.valueOf(totalList.get(position)) );
        porcentagem = ((totalListInt*100)/valorMax)*4;
        idNomeLista = "ID: "+String.valueOf(idListL.get(position))+" - "+String.valueOf(nomeList.get(position));

        if((int)porcentagem < 25) {
            holder.totalListITEM_bar.setHeight(25);
        } else {
            holder.totalListITEM_bar.setHeight((int)porcentagem);
        }

        //selectionList_Grafic, nomeListITEM_bar, totalListITEM_bar
        holder.nomeListITEM_bar.setText(idNomeLista);

        //Selecionar Lista
        holder.selectionList_Grafic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                long idListaL = Long.parseLong( String.valueOf( idListL.get(position) ) );
                idUltimaListaSelected = idListaL;
                ((Activity) context).recreate();


            }
        });
    }

    @Override
    public int getItemCount() {
        return idListL.size();
    }

    public class HolderListConsult extends RecyclerView.ViewHolder {

        //selectionList_Grafic, nomeListITEM_bar, totalListITEM_bar
        LinearLayout selectionList_Grafic;
        TextView nomeListITEM_bar, totalListITEM_bar;

        LinearLayout selectionList;
        TextView idListL, nomeList, dataList;
        CheckBox checkList;

        public HolderListConsult(@NonNull View itemView) {
            super(itemView);
            selectionList_Grafic = itemView.findViewById(R.id.selectionList_Grafic);
            nomeListITEM_bar = itemView.findViewById(R.id.nomeListITEM_bar);
            totalListITEM_bar = itemView.findViewById(R.id.totalListITEM_bar);

            selectionList = itemView.findViewById(R.id.selectionList);
            idListL = itemView.findViewById(R.id.idListITEM);
            nomeList = itemView.findViewById(R.id.nomeListITEM);
            dataList = itemView.findViewById(R.id.dataListITEM);
            checkList = itemView.findViewById(R.id.checkListITEM);
        }
    }

}