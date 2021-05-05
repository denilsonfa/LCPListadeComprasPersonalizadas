package br.com.dla.lcp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.RecyclerView;

public class S_M02_ListConsultMult_Adapter extends RecyclerView.Adapter<S_M02_ListConsultMult_Adapter.HolderListCreate> {

    private Context context;
    S_Dados dados = new S_Dados();
    private ArrayList idListL, nomeList, dataList, checkList, idProduct, idListP, nomeProduct, quantProduct, medidaProduct, tipoProduct, valorProduct, checkProduct;
    int position;
    private String valorProductText = "";

    S_M02_ListConsultMult_Adapter(
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
        View view = inflater.inflate(R.layout.body_b02_listconsult_mut, parent, false);
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

        //tipoProduct_imt
        if( String.valueOf(tipoProduct.get(position)).equals("Alimentos") ) {
            holder.tipoProduct_imt.setImageResource(R.drawable.ic_item_01);
        } else if( String.valueOf(tipoProduct.get(position)).equals("Carnes e Ovos") ) {
            holder.tipoProduct_imt.setImageResource(R.drawable.ic_item_02);
        } else if( String.valueOf(tipoProduct.get(position)).equals("Verduras, Legumes e Frutas") ) {
            holder.tipoProduct_imt.setImageResource(R.drawable.ic_item_03);
        } else if( String.valueOf(tipoProduct.get(position)).equals("Bebidas") ) {
            holder.tipoProduct_imt.setImageResource(R.drawable.ic_item_04);
        } else if( String.valueOf(tipoProduct.get(position)).equals("Leite e Derivados") ) {
            holder.tipoProduct_imt.setImageResource(R.drawable.ic_item_05);
        } else if( String.valueOf(tipoProduct.get(position)).equals("Padaria e Sobremesa") ) {
            holder.tipoProduct_imt.setImageResource(R.drawable.ic_item_06);
        } else if( String.valueOf(tipoProduct.get(position)).equals("Saúde e Beleza") ) {
            holder.tipoProduct_imt.setImageResource(R.drawable.ic_item_07);
        } else if( String.valueOf(tipoProduct.get(position)).equals("Higiene e Limpeza") ) {
            holder.tipoProduct_imt.setImageResource(R.drawable.ic_item_08);
        } else {
            holder.tipoProduct_imt.setImageResource(R.drawable.ic_item_09);
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

                String idL = String.valueOf(idProduct.get(position));
                String nomeP = String.valueOf(nomeProduct.get(position));

                A_M02_ListConsult_ListMult.tst(context, idL, nomeP);

//                //Nome do Produto
//                String nomeProductS = String.valueOf(nomeProduct.get(position));
//                final String id = String.valueOf(idProduct.get(position));
//
//                FrameLayout.LayoutParams params = new  FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                final EditText input = new EditText(context);
//                FrameLayout container = new FrameLayout(context);
//
//                builder.setTitle(nomeProductS);
//                builder.setMessage(R.string.sltList_obs02_editMult);
//
//                input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
//                input.setFilters(new InputFilter[] { new InputFilter.LengthFilter(30) });
//                input.setHint(R.string.valorProduct);
//                input.setSingleLine();
//
//                params.leftMargin = container.getResources().getDimensionPixelSize(R.dimen.dp_19);
//                params.rightMargin = container.getResources().getDimensionPixelSize(R.dimen.dp_19);
//
//                input.setLayoutParams(params);
//                container.addView(input);
//                builder.setView(container);
//
//
//                // Set up the buttons
//                builder.setNeutralButton(R.string.sltList_obs02_incart, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                        //Pegando dados do input
//                        valorProductText = input.getText().toString();
//
//                        if(valorProductText.equals("") || valorProductText.equals("0")){
//                            Toast.makeText(context, R.string.erro_noText01,Toast.LENGTH_LONG).show();
//                        } else {
//
//                            Double valorProduct = Double.parseDouble( valorProductText );
//                            boolean checkProduct = true;
//
//                            //Editando dados do produto
//                            S_ConexaoDAO conexaoDAO = new S_ConexaoDAO(context);
//                            conexaoDAO.updateProductValor( id, valorProduct, checkProduct );
//
//                            //Mensagem de confirmação
//                            Toast.makeText(context, R.string.editedProduct_addValor,Toast.LENGTH_LONG).show();
//                        }
//
//                    }
//                });
//                builder.setNegativeButton(R.string.sltList_obs02_nocart, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                        //Por ser a opção de que não está  no carrinho, o produto tem o valorProduct zerado e o checkProduct false
//                        Double valorProduct = 0.0;
//                        boolean checkProduct = false;
//
//                        //Editando dados do produto
//                        S_ConexaoDAO conexaoDAO = new S_ConexaoDAO(context);
//                        conexaoDAO.updateProductValor( id, valorProduct, checkProduct );
//
//                        //Mensagem de confirmação
//                        Toast.makeText(context, R.string.editedProduct_addValor,Toast.LENGTH_LONG).show();
//
//                    }
//                });
//
//                builder.show();

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
        ImageView tipoProduct_imt, checkList, editValorProductITEM;
        LinearLayout textValorOptionA02, textValorOptionB02;

        public HolderListCreate(@NonNull View itemView) {
            super(itemView);
            checkList = itemView.findViewById(R.id.productInCart02_mult);
            tipoProduct_imt = itemView.findViewById(R.id.tipoProductITEM02_img_mult);

            idProduct = itemView.findViewById(R.id.idProductITEM02_mult);
            nomeProduct = itemView.findViewById(R.id.nomeProductITEM02_mult);
            quantProduct = itemView.findViewById(R.id.quantProductITEM02_mult);
            medidaProduct = itemView.findViewById(R.id.medidaProductITEM02_mult);
            tipoProduct = itemView.findViewById(R.id.tipoProductITEM02_mult);

            valorProductITEM = itemView.findViewById(R.id.valorProductITEM02_mult);
            totalProductITEM = itemView.findViewById(R.id.totalProductITEM02_mult);

            editValorProductITEM = itemView.findViewById(R.id.editValorProductITEM02_mult);

            textValorOptionA02 = itemView.findViewById(R.id.textValorOptionA02_mult);
            textValorOptionB02 = itemView.findViewById(R.id.textValorOptionB02_mult);
        }
    }

}