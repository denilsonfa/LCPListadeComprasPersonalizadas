package br.com.dla.lcp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

public class S_M04_ListLista_Adapter extends RecyclerView.Adapter<S_M04_ListLista_Adapter.HolderListConsult> {

    private Context context;
    private ArrayList selectionList, idListL, nomeList, dataList, checkList, totalList;
    int position;

    S_M04_ListLista_Adapter(Context context,
                            ArrayList selectionList,
                            ArrayList idListL,
                            ArrayList nomeList,
                            ArrayList dataList,
                            ArrayList checkList,
                            ArrayList totalList) {
        this.context = context;
        this.selectionList = selectionList;
        this.idListL = idListL;
        this.nomeList = nomeList;
        this.dataList = dataList;
        this.checkList = checkList;
        this.totalList = totalList;
    }

    @NonNull
    @Override
    public HolderListConsult onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.body_b04_list, parent, false);
        return new HolderListConsult(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderListConsult holder, final int position) {

        //checkList
        if( String.valueOf(checkList.get(position)).equals("0") ) {
            //definindo cor da barra
            holder.checkListITEM_bar.setBackgroundColor(0x88FF0000);
            holder.checkList.setText(R.string.checkList_isNot);
            holder.totalList.setText(R.string.lista_nFinalizada);
            holder.totalList.setBackgroundColor(0x88FF0000);

        } else {

            holder.checkList.setText(R.string.checkList_isOk);

            if( String.valueOf(totalList.get(position)).equals("0") ) {

                holder.checkListITEM_bar.setBackgroundColor(0x88951095);
                holder.totalList.setText(R.string.lista_nSemValor);
                holder.totalList.setBackgroundColor(0x88951095);

            } else {

                double valorTotal = Double.parseDouble( String.valueOf( totalList.get(position) ) );
                double total = (Math.rint (valorTotal * 100.0) / 100.0);
                String totalStr = "Total: R$ "+total;

                holder.checkListITEM_bar.setBackgroundColor(0x882187FF);
                holder.totalList.setText(totalStr);
                holder.totalList.setBackgroundColor(0x882187FF);

            }
        }

        //idListL, nomeList, dataList
        holder.idListL.setText( String.valueOf("ID: " + idListL.get(position)));
        holder.nomeList.setText(String.valueOf(nomeList.get(position)));
        holder.dataList.setText(String.valueOf(dataList.get(position)));

        //Selecionar Lista
        holder.selectionList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.limparLista);
                builder.setMessage(R.string.limparListaObs);
                //builder.setIcon(R.drawable.ic_item_alert);

                //define um botão negativo
                builder.setNegativeButton(R.string.nao, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        Toast.makeText(context, R.string.cancel, Toast.LENGTH_SHORT).show();

                    }
                });

                //define um botão positivo
                builder.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        S_ConexaoDAO conexaoDAO = new S_ConexaoDAO(context);
                        String idProduct = String.valueOf(idListL.get(position));

                        //apagar todos os itens referente a esta lista
                        conexaoDAO.deleteProductinList(idProduct);

                        //apagar lista
                        conexaoDAO.deleteList(idProduct);

                        //Mensagem de conclusão
                        Toast.makeText(context, R.string.limparLista_confirm, Toast.LENGTH_SHORT).show();

                        //Recarregar lista
                        ((Activity)context).recreate();

                    }
                });

                //cria o AlertDialog
                AlertDialog  alerta = builder.create();

                //Exibe
                alerta.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return idListL.size();
    }

    public class HolderListConsult extends RecyclerView.ViewHolder {

        //idListL, nomeList, dataList, checkList
        LinearLayout selectionList, checkListITEM_bar;
        TextView idListL, nomeList, dataList, checkList, totalList;

        public HolderListConsult(@NonNull View itemView) {
            super(itemView);
            selectionList = itemView.findViewById(R.id.selectionList04);
            checkListITEM_bar = itemView.findViewById(R.id.checkListITEM04_bar);
            idListL = itemView.findViewById(R.id.idListITEM04);
            nomeList = itemView.findViewById(R.id.nomeListITEM04);
            dataList = itemView.findViewById(R.id.dataListITEM04);
            checkList = itemView.findViewById(R.id.checkListITEM04);
            totalList = itemView.findViewById(R.id.totalListITEM04);
        }
    }
}