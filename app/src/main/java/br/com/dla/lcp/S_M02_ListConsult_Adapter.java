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

public class S_M02_ListConsult_Adapter extends RecyclerView.Adapter<S_M02_ListConsult_Adapter.HolderListCreate> {

    private Context context;
    S_Dados dados = new S_Dados();
    private ArrayList idListL, nomeList, dataList, checkList, idProduct, idListP, nomeProduct, quantProduct, medidaProduct, tipoProduct, valorProduct, checkProduct;
    int position;

    S_M02_ListConsult_Adapter(
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
        View view = inflater.inflate(R.layout.body_b02_listconsult, parent, false);
        return new HolderListCreate(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderListCreate holder, final int position) {
        this.position = position;

        //checkList
        if( String.valueOf(checkProduct.get(position)).equals("0") ) {
            //define a imagem referente a opção
            holder.checkList.setImageResource(R.drawable.ic_item_nocart);
            //define a cor da imagem
            ColorStateList csl = AppCompatResources.getColorStateList(context, R.color.textVermelho01);
            ImageViewCompat.setImageTintList(holder.checkList, csl);
        } else {
            holder.checkList.setImageResource(R.drawable.ic_item_incart);
            ColorStateList csl = AppCompatResources.getColorStateList(context, R.color.textAzul01);
            ImageViewCompat.setImageTintList(holder.checkList, csl);
        }

        //textValorOption
        if( String.valueOf(valorProduct.get(position)).equals("0") ){
            //visibilidade dos itens
            holder.textValorOptionA02.setVisibility(View.VISIBLE);
            holder.textValorOptionB02.setVisibility(View.GONE);

            //definindo valor
            holder.valorProductITEM.setText(String.valueOf(R.string.num2));
            holder.totalProductITEM.setText(String.valueOf(R.string.num2));

        } else {
            //visibilidade dos itens
            holder.textValorOptionA02.setVisibility(View.GONE);
            holder.textValorOptionB02.setVisibility(View.VISIBLE);

            //definindo valor
            double valor = Double.parseDouble( String.valueOf( valorProduct.get(position) ) );
            double quantidade = Double.parseDouble( String.valueOf( quantProduct.get(position) ) );
            double valorTotal;
            String valorStr;

            if((int) quantidade == quantidade){
                //Se quantidade não tiver numeros decimais
                int quantI = (int)quantidade;
                valorStr = quantI+" × R$ "+valor;
                //fazer calculo com valor inteiro
                valorTotal = valor*quantI;

            } else {
                //Se quantidade tiver numeros decimais
                valorStr = quantidade+" × R$ "+valor;
                //fazer calculo com valor decimal
                valorTotal = valor*quantidade;

            }

            //definir resultado
            valorTotal = (Math.rint (valorTotal * 100.0) / 100.0);
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

        //editProductITEM
        holder.editValorProductITEM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, A_M02_ListConsult_EditUni.class);
                intent.putExtra("idProductID", String.valueOf(idProduct.get(position)));
                intent.putExtra("idListPID", String.valueOf(idListP.get(position)));
                intent.putExtra("nomeProductID", String.valueOf(nomeProduct.get(position)));
                intent.putExtra("quantProductID", String.valueOf(quantProduct.get(position)));
                intent.putExtra("medidaProductID", String.valueOf(medidaProduct.get(position)));
                intent.putExtra("tipoProductID", String.valueOf(tipoProduct.get(position)));
                intent.putExtra("valorProductID", String.valueOf(valorProduct.get(position)));
                intent.putExtra("checkProductID", String.valueOf(checkProduct.get(position)));
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
        TextView idProduct, nomeProduct, quantProduct, medidaProduct, tipoProduct, valorProductITEM, totalProductITEM;
        ImageView checkList, editValorProductITEM;
        LinearLayout textValorOptionA02, textValorOptionB02;

        public HolderListCreate(@NonNull View itemView) {
            super(itemView);
            checkList = itemView.findViewById(R.id.productInCart02);

            idProduct = itemView.findViewById(R.id.idProductITEM02);
            nomeProduct = itemView.findViewById(R.id.nomeProductITEM02);
            quantProduct = itemView.findViewById(R.id.quantProductITEM02);
            medidaProduct = itemView.findViewById(R.id.medidaProductITEM02);
            tipoProduct = itemView.findViewById(R.id.tipoProductITEM02);

            valorProductITEM = itemView.findViewById(R.id.valorProductITEM02);
            totalProductITEM = itemView.findViewById(R.id.totalProductITEM02);

            editValorProductITEM = itemView.findViewById(R.id.editValorProductITEM02);

            textValorOptionA02 = itemView.findViewById(R.id.textValorOptionA02);
            textValorOptionB02 = itemView.findViewById(R.id.textValorOptionB02);
        }
    }

}