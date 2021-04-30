package br.com.dla.lcp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class S_M02_ListLista_Adapter extends RecyclerView.Adapter<S_M02_ListLista_Adapter.HolderListConsult> {

    private Context context;
    private ArrayList selectionList, idListL, nomeList, dataList, checkList;
    int position;

    S_M02_ListLista_Adapter(Context context,
                            ArrayList selectionList,
                            ArrayList idListL,
                            ArrayList nomeList,
                            ArrayList dataList,
                            ArrayList checkList) {
        this.context = context;
        this.selectionList = selectionList;
        this.idListL = idListL;
        this.nomeList = nomeList;
        this.dataList = dataList;
        this.checkList = checkList;
    }

    @NonNull
    @Override
    public HolderListConsult onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.body_b00_list, parent, false);
        return new HolderListConsult(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderListConsult holder, final int position) {
        //idListL, nomeList, dataList
        holder.idListL.setText( String.valueOf("ID: " + idListL.get(position)));
        holder.nomeList.setText(String.valueOf(nomeList.get(position)));
        holder.dataList.setText(String.valueOf(dataList.get(position)));

        //checkList
        if( (String.valueOf(checkList.get(position)).equals("0")) ){

            holder.checkList.setChecked(false);

        } else {

            holder.checkList.setChecked(true);

        }

        //Selecionar Lista
        holder.selectionList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, A_M02_ListConsult.class);
                intent.putExtra("idListLID", String.valueOf(idListL.get(position)));
                intent.putExtra("nomeListID", String.valueOf(nomeList.get(position)));
                intent.putExtra("dataListID", String.valueOf(dataList.get(position)));
                intent.putExtra("checkListID", String.valueOf(checkList.get(position)));
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return idListL.size();
    }

    public class HolderListConsult extends RecyclerView.ViewHolder {

        //idListL, nomeList, dataList, checkList
        LinearLayout selectionList;
        TextView idListL, nomeList, dataList;
        CheckBox checkList;

        public HolderListConsult(@NonNull View itemView) {
            super(itemView);
            selectionList = itemView.findViewById(R.id.selectionList);
            idListL = itemView.findViewById(R.id.idListITEM);
            nomeList = itemView.findViewById(R.id.nomeListITEM);
            dataList = itemView.findViewById(R.id.dataListITEM);
            checkList = itemView.findViewById(R.id.checkListITEM);
        }
    }
}