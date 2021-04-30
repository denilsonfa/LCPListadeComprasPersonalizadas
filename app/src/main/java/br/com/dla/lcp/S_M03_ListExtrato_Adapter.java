package br.com.dla.lcp;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.RecyclerView;

public class S_M03_ListExtrato_Adapter extends RecyclerView.Adapter<S_M03_ListExtrato_Adapter.HolderListCreate> {

    private Context context;
    S_Dados dados = new S_Dados();
    private ArrayList idListL, nomeList, dataList, checkList, idProduct, idListP, nomeProduct, quantProduct, medidaProduct, tipoProduct, valorProduct, checkProduct;
    int position;

    S_M03_ListExtrato_Adapter(
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
        View view = inflater.inflate(R.layout.body_b03_listextrato, parent, false);
        return new HolderListCreate(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderListCreate holder, final int position) {
        this.position = position;

        //tipoProduct
        if( String.valueOf(tipoProduct.get(position)).equals("Alimentos") ) {
            holder.tipoProduct_img.setImageResource(R.drawable.ic_item_01);
        } else if( String.valueOf(tipoProduct.get(position)).equals("Carnes e Ovos") ) {
            holder.tipoProduct_img.setImageResource(R.drawable.ic_item_02);
        } else if( String.valueOf(tipoProduct.get(position)).equals("Verduras, Legumes e Frutas") ) {
            holder.tipoProduct_img.setImageResource(R.drawable.ic_item_03);
        } else if( String.valueOf(tipoProduct.get(position)).equals("Bebidas") ) {
            holder.tipoProduct_img.setImageResource(R.drawable.ic_item_04);
        } else if( String.valueOf(tipoProduct.get(position)).equals("Leite e Derivados") ) {
            holder.tipoProduct_img.setImageResource(R.drawable.ic_item_05);
        } else if( String.valueOf(tipoProduct.get(position)).equals("Padaria e Sobremesa") ) {
            holder.tipoProduct_img.setImageResource(R.drawable.ic_item_06);
        } else if( String.valueOf(tipoProduct.get(position)).equals("Saúde e Beleza") ) {
            holder.tipoProduct_img.setImageResource(R.drawable.ic_item_07);
        } else if( String.valueOf(tipoProduct.get(position)).equals("Higiene e Limpeza") ) {
            holder.tipoProduct_img.setImageResource(R.drawable.ic_item_08);
        } else {
            holder.tipoProduct_img.setImageResource(R.drawable.ic_item_09);
        }

        //textValorOption
        if( String.valueOf(valorProduct.get(position)).equals("0") ){
            //visibilidade dos itens
            holder.textValorOptionA03.setVisibility(View.VISIBLE);
            holder.textValorOptionB03.setVisibility(View.GONE);

            //definindo valor
            holder.valorProductITEM.setText(String.valueOf(R.string.num2));
            holder.totalProductITEM.setText(String.valueOf(R.string.num2));

        } else {
            //visibilidade dos itens
            holder.textValorOptionA03.setVisibility(View.GONE);
            holder.textValorOptionB03.setVisibility(View.VISIBLE);

            //definindo valor
            double valor = Double.parseDouble( String.valueOf( valorProduct.get(position) ) );
            double quantidade = Double.parseDouble( String.valueOf( quantProduct.get(position) ) );
            double valorTotal;
            String valorStr;

            if((int) quantidade == quantidade){
                //Se quantidade não tiver numeros decimais
                int quantI = (int)quantidade;
                valorStr = "R$ "+valor;
                //fazer calculo com valor inteiro
                valorTotal = valor*quantI;

            } else {
                //Se quantidade tiver numeros decimais
                valorStr = "R$ "+valor;
                //fazer calculo com valor decimal
                valorTotal = valor*quantidade;

            }

            //definir resultado
            String totalStr = "R$ "+valorTotal;

            holder.valorProductITEM.setText(valorStr);
            holder.totalProductITEM.setText(totalStr);

        }

        //nomeProduct, quantProduct, medidaProduct
        holder.idProduct.setText(String.valueOf("ID: "+idProduct.get(position)));
        holder.nomeProduct.setText(String.valueOf(nomeProduct.get(position)));
        holder.quantProduct.setText(String.valueOf(quantProduct.get(position)));
        holder.medidaProduct.setText(String.valueOf(medidaProduct.get(position)));
        holder.tipoProduct.setText(String.valueOf(tipoProduct.get(position)));

    }

    @Override
    public int getItemCount() {
        return idProduct.size();
    }

    public class HolderListCreate extends RecyclerView.ViewHolder {

        //idProduct, nomeProduct, quantProduct, medidaProduct, tipoProduct, editProductITEM, deletProductITEM
        TextView idProduct, nomeProduct, quantProduct, medidaProduct, tipoProduct, valorProductITEM, totalProductITEM;
        ImageView tipoProduct_img;
        LinearLayout textValorOptionA03, textValorOptionB03;

        public HolderListCreate(@NonNull View itemView) {
            super(itemView);

            idProduct = itemView.findViewById(R.id.idProductITEM03);
            nomeProduct = itemView.findViewById(R.id.nomeProductITEM03);
            quantProduct = itemView.findViewById(R.id.quantProductITEM03);
            medidaProduct = itemView.findViewById(R.id.medidaProductITEM03);
            tipoProduct = itemView.findViewById(R.id.tipoProductITEM03);
            tipoProduct_img = itemView.findViewById(R.id.tipoProductITEM03_img);

            valorProductITEM = itemView.findViewById(R.id.valorProductITEM03);
            totalProductITEM = itemView.findViewById(R.id.totalProductITEM03);

            textValorOptionA03 = itemView.findViewById(R.id.textValorOptionA03);
            textValorOptionB03 = itemView.findViewById(R.id.textValorOptionB03);
        }
    }
}