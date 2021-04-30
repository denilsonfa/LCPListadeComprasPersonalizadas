package br.com.dla.lcp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class S_M01_ListCreate_Adapter extends RecyclerView.Adapter<S_M01_ListCreate_Adapter.HolderListCreate> {

    private Context context;
    S_Dados dados = new S_Dados();
    private ArrayList idListL, nomeList, dataList, checkList, idProduct, idListP, nomeProduct, quantProduct, medidaProduct, tipoProduct, valorProduct, checkProduct;
    int position;

    S_M01_ListCreate_Adapter(
            Context context,
            ArrayList idListL,
            ArrayList nomeList,
            ArrayList dataList,
            ArrayList checkList,

            ArrayList idProduct,
            ArrayList idListP,
            ArrayList nomeProduct,
            ArrayList quantProduct,
            ArrayList medidaProduct,
            ArrayList tipoProduct,
            ArrayList valorProduct,
            ArrayList checkProduct

    ) {

        this.context = context;

        this.idListL = idListL;
        this.nomeList = nomeList;
        this.dataList = dataList;
        this.checkList = checkList;

        this.idProduct = idProduct;
        this.idListP = idListP;
        this.nomeProduct = nomeProduct;
        this.quantProduct = quantProduct;
        this.medidaProduct = medidaProduct;
        this.tipoProduct = tipoProduct;
        this.valorProduct = valorProduct;
        this.checkProduct = checkProduct;

    }

    @NonNull
    @Override
    public HolderListCreate onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.body_b01_listcreate, parent, false);
        return new HolderListCreate(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderListCreate holder, final int position) {
        this.position = position;

        //tipoProduct
        if( String.valueOf(tipoProduct.get(position)).equals("Alimentos") ) {
            holder.tipoProduct.setImageResource(R.drawable.ic_item_01);
        } else if( String.valueOf(tipoProduct.get(position)).equals("Carnes e Ovos") ) {
            holder.tipoProduct.setImageResource(R.drawable.ic_item_02);
        } else if( String.valueOf(tipoProduct.get(position)).equals("Verduras, Legumes e Frutas") ) {
            holder.tipoProduct.setImageResource(R.drawable.ic_item_03);
        } else if( String.valueOf(tipoProduct.get(position)).equals("Bebidas") ) {
            holder.tipoProduct.setImageResource(R.drawable.ic_item_04);
        } else if( String.valueOf(tipoProduct.get(position)).equals("Leite e Derivados") ) {
            holder.tipoProduct.setImageResource(R.drawable.ic_item_05);
        } else if( String.valueOf(tipoProduct.get(position)).equals("Padaria e Sobremesa") ) {
            holder.tipoProduct.setImageResource(R.drawable.ic_item_06);
        } else if( String.valueOf(tipoProduct.get(position)).equals("Saúde e Beleza") ) {
            holder.tipoProduct.setImageResource(R.drawable.ic_item_07);
        } else if( String.valueOf(tipoProduct.get(position)).equals("Higiene e Limpeza") ) {
            holder.tipoProduct.setImageResource(R.drawable.ic_item_08);
        } else {
            holder.tipoProduct.setImageResource(R.drawable.ic_item_09);
        }

        //nomeProduct, quantProduct, medidaProduct
        holder.idProduct.setText(String.valueOf("ID: "+idProduct.get(position)));
        holder.nomeProduct.setText(String.valueOf(nomeProduct.get(position)));
        holder.quantProduct.setText(String.valueOf(quantProduct.get(position)));
        holder.medidaProduct.setText(String.valueOf(medidaProduct.get(position)));

        //editProductITEM
        holder.editProductITEM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, A_M01_ListCreate_Edit.class);
                intent.putExtra("idProductID", String.valueOf(idProduct.get(position)));
                intent.putExtra("idListPID", String.valueOf(idListP.get(position)));
                intent.putExtra("nomeProductID", String.valueOf(nomeProduct.get(position)));
                intent.putExtra("quantProductID", String.valueOf(quantProduct.get(position)));
                intent.putExtra("medidaProductID", String.valueOf(medidaProduct.get(position)));
                intent.putExtra("tipoProductID", String.valueOf(tipoProduct.get(position)));
                context.startActivity(intent);

            }
        });

        //deletProductITEM
        holder.deletProductITEM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, A_M01_ListCreate_Delet.class);
                intent.putExtra("idProductID", String.valueOf(idProduct.get(position)));
                intent.putExtra("idListPID", String.valueOf(idListP.get(position)));
                intent.putExtra("nomeProductID", String.valueOf(nomeProduct.get(position)));
                intent.putExtra("quantProductID", String.valueOf(quantProduct.get(position)));
                intent.putExtra("medidaProductID", String.valueOf(medidaProduct.get(position)));
                intent.putExtra("tipoProductID", String.valueOf(tipoProduct.get(position)));
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return idProduct.size();
    }

    public class HolderListCreate extends RecyclerView.ViewHolder {

        //idProduct, nomeProduct, quantProduct, medidaProduct, tipoProduct, editProductITEM, deletProductITEM
        TextView idProduct, nomeProduct, quantProduct, medidaProduct;
        ImageView tipoProduct, editProductITEM, deletProductITEM;

        public HolderListCreate(@NonNull View itemView) {
            super(itemView);
            idProduct = itemView.findViewById(R.id.idProductITEM01);
            nomeProduct = itemView.findViewById(R.id.nomeProductITEM01);
            quantProduct = itemView.findViewById(R.id.quantProductITEM01);
            medidaProduct = itemView.findViewById(R.id.medidaProductITEM01);

            tipoProduct = itemView.findViewById(R.id.tipoProductITEM01);

            editProductITEM = itemView.findViewById(R.id.editProductITEM01);
            deletProductITEM = itemView.findViewById(R.id.deletProductITEM01);
        }
    }
}