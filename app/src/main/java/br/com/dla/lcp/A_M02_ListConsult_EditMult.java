package br.com.dla.lcp;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.widget.ImageViewCompat;

public class A_M02_ListConsult_EditMult extends AppCompatActivity {

    //Criar lista (Adicionar Produto) == Creditos: @Denilson_fa
    S_Dados dados = new S_Dados();
    S_ConexaoDAO crud = new S_ConexaoDAO(getBaseContext());
    Context context;

    private String idProductID, idListP, nomeProductID, quantProductID, medidaProductID, tipoProductID, valorProductID, checkProductID, totalProductID;
    private TextView idProductEDIT_M, nomeProductEDIT_M, quantProductEDIT_M, medidaProductEDIT_M, tipoProductEDIT_M, valorProductEDIT_M, totalProductEDIT_M, textValorOptionB02_M;
    private ImageView tipoProduct_img_EDIT_M, checkProduct_img_EDIT_M;
    private EditText valorProductEdTx_EDIT_M;
    private Button cancelEDIT_M, confirmEDIT_M;
    private LinearLayout textValorOptionA02_M;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(1);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.rgb(14,91,182));
        setContentView(R.layout.activity_m02_listconsult_edituni);

        //ImageView
        tipoProduct_img_EDIT_M = findViewById(R.id.tipoProductEDIT_IMG_M);
        checkProduct_img_EDIT_M = findViewById(R.id.checkProductEDIT_IMG_M);

        //TextView
        nomeProductEDIT_M = findViewById(R.id.nomeProductEDIT_TXT_M);
        quantProductEDIT_M = findViewById(R.id.quantProductEDIT_TXT_M);
        medidaProductEDIT_M = findViewById(R.id.medidaProductEDIT_TXT_M);
        tipoProductEDIT_M = findViewById(R.id.tipoProductEDIT_TXT_M);
        valorProductEDIT_M = findViewById(R.id.valorProductEDIT_TXT_M);
        totalProductEDIT_M = findViewById(R.id.totalProductEDIT_TXT_M);


        //EditText
        valorProductEdTx_EDIT_M = findViewById(R.id.valorProductEDIT_EDIT_M);

        //LinearLayout
        textValorOptionA02_M = findViewById(R.id.textValorOptionA02_EDIT_TXT_M);
        //TextView
        textValorOptionB02_M = findViewById(R.id.textValorOptionB02_EDIT_TXT_M);

        //Button
        cancelEDIT_M = findViewById(R.id.cancelEDIT_EDIT_U);
        confirmEDIT_M = findViewById(R.id.confirmEDIT_EDIT_U);

        getAndSetIntentData();

        //valorProductEdTx_EDIT_U
//        valorProductEdTx_EDIT_M.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
//            @Override
//            public void afterTextChanged(Editable editable) {}
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if(charSequence.toString().equals("")){
//                    //visibilidade dos itens
//                    textValorOptionB02_M.setVisibility(View.VISIBLE);
//                    textValorOptionA02_M.setVisibility(View.GONE);
//                } else {
//                    //visibilidade dos itens
//                    textValorOptionB02_M.setVisibility(View.GONE);
//                    textValorOptionA02_M.setVisibility(View.VISIBLE);
//
//                    //definindo valor
//                    definiValor();
//                }
//            }
//        });

        //cancelEDIT_U
        cancelEDIT_M.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( (valorProductEdTx_EDIT_M.getText().toString().equals("")) ) {
                    Toast.makeText(A_M02_ListConsult_EditMult.this, R.string.erro_noText02,Toast.LENGTH_LONG).show();
                } else {
                    //S_Dados = Guardando S_Dados em variavies
                    valorProductEdTx_EDIT_M.setText("0");
                    dados.setValorProduct( Double.parseDouble( valorProductEdTx_EDIT_M.getText().toString() ) );
                    dados.setCheckProduct(false);

                    S_ConexaoDAO conexaoDAO = new S_ConexaoDAO(A_M02_ListConsult_EditMult.this);
                    String idProduct = String.valueOf(dados.getIdProduct());

                    //Atualizando dados do item no Banco de Dados
                    conexaoDAO.updateProductValor(
                            idProduct,
                            dados.getValorProduct(),
                            dados.getCheckProduct()
                    );

                    //Finaliza Activity
                    finish();

                    //Mensagem de confirmação
                    Toast.makeText(A_M02_ListConsult_EditMult.this, R.string.editedProduct_addValor,Toast.LENGTH_LONG).show();
                }
            }
        });

        //confirmEDIT_U
        confirmEDIT_M.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( (valorProductEdTx_EDIT_M.getText().toString().equals("")) ) {
                    Toast.makeText(A_M02_ListConsult_EditMult.this, R.string.erro_noText02,Toast.LENGTH_LONG).show();
                } else {
                    //S_Dados = Guardando S_Dados em variavies
                    dados.setValorProduct( Double.parseDouble( valorProductEdTx_EDIT_M.getText().toString() ) );
                    dados.setCheckProduct(true);

                    S_ConexaoDAO conexaoDAO = new S_ConexaoDAO(A_M02_ListConsult_EditMult.this);
                    String idProduct = String.valueOf(dados.getIdProduct());

                    //Atualizando dados do item no Banco de Dados
                    conexaoDAO.updateProductValor(
                            idProduct,
                            dados.getValorProduct(),
                            dados.getCheckProduct()
                    );

                    //Finaliza Activity
                    finish();

                    //Mensagem de confirmação
                    Toast.makeText(A_M02_ListConsult_EditMult.this, R.string.editedProduct_addValor,Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    //Recebendo dados do item selecionado para editar
    void getAndSetIntentData() {
        if (getIntent().hasExtra("idProductID") &&
                getIntent().hasExtra("nomeProductID") &&
                getIntent().hasExtra("quantProductID") &&
                getIntent().hasExtra("medidaProductID") &&
                getIntent().hasExtra("tipoProductID") &&
                getIntent().hasExtra("valorProductID") &&
                getIntent().hasExtra("checkProductID")) {

            //GETTING
            idProductID = getIntent().getStringExtra("idProductID");
            nomeProductID = getIntent().getStringExtra("nomeProductID");
            quantProductID = getIntent().getStringExtra("quantProductID");
            medidaProductID = getIntent().getStringExtra("medidaProductID");
            tipoProductID = getIntent().getStringExtra("tipoProductID");
            valorProductID = getIntent().getStringExtra("valorProductID");
            checkProductID = getIntent().getStringExtra("checkProductID");

            //SETTING
            //String idProduct = "ID: "+idProductID;
            //idProductEDIT_U.setText(idProduct);
            nomeProductEDIT_M.setText(nomeProductID);
            quantProductEDIT_M.setText(quantProductID);
            medidaProductEDIT_M.setText(medidaProductID);
            tipoProductEDIT_M.setText(tipoProductID);

            //tipoProduct
            if( tipoProductID.equals("Alimentos") ) {
                tipoProduct_img_EDIT_M.setImageResource(R.drawable.ic_item_01);
            } else if( tipoProductID.equals("Carnes e Ovos") ) {
                tipoProduct_img_EDIT_M.setImageResource(R.drawable.ic_item_02);
            } else if( tipoProductID.equals("Verduras, Legumes e Frutas") ) {
                tipoProduct_img_EDIT_M.setImageResource(R.drawable.ic_item_03);
            } else if( tipoProductID.equals("Bebidas") ) {
                tipoProduct_img_EDIT_M.setImageResource(R.drawable.ic_item_04);
            } else if( tipoProductID.equals("Leite e Derivados") ) {
                tipoProduct_img_EDIT_M.setImageResource(R.drawable.ic_item_05);
            } else if( tipoProductID.equals("Padaria e Sobremesa") ) {
                tipoProduct_img_EDIT_M.setImageResource(R.drawable.ic_item_06);
            } else if( tipoProductID.equals("Saúde e Beleza") ) {
                tipoProduct_img_EDIT_M.setImageResource(R.drawable.ic_item_07);
            } else if( tipoProductID.equals("Higiene e Limpeza") ) {
                tipoProduct_img_EDIT_M.setImageResource(R.drawable.ic_item_08);
            } else {
                tipoProduct_img_EDIT_M.setImageResource(R.drawable.ic_item_09);
            }

            //checkList
            if( checkProductID.equals("0") ) {
                //define a imagem referente a opção
                checkProduct_img_EDIT_M.setImageResource(R.drawable.ic_item_nocart);
                //define a cor da imagem
                ColorStateList csl = AppCompatResources.getColorStateList(A_M02_ListConsult_EditMult.this, R.color.textVermelho01);
                ImageViewCompat.setImageTintList(checkProduct_img_EDIT_M, csl);
            } else {
                checkProduct_img_EDIT_M.setImageResource(R.drawable.ic_item_incart);
                ColorStateList csl = AppCompatResources.getColorStateList(A_M02_ListConsult_EditMult.this, R.color.textAzul01);
                ImageViewCompat.setImageTintList(checkProduct_img_EDIT_M, csl);
            }

            //textValorOption
            if( valorProductID.equals("0") ){
                //visibilidade dos itens
                textValorOptionB02_M.setVisibility(View.VISIBLE);
                textValorOptionA02_M.setVisibility(View.GONE);

                //definindo valor
                valorProductEDIT_M.setText(String.valueOf(R.string.num2));
                totalProductEDIT_M.setText(String.valueOf(R.string.num2));

                valorProductEdTx_EDIT_M.setText("");

            } else {
                //visibilidade dos itens
                textValorOptionB02_M.setVisibility(View.GONE);
                textValorOptionA02_M.setVisibility(View.VISIBLE);

                valorProductEdTx_EDIT_M.setText(valorProductID);

                //definindo valor
                definiValor();

            }

            //SETTING IN S_Dados
            dados.setIdProduct(Integer.parseInt(idProductID));

        } else {
            Toast.makeText(this, R.string.erro_product, Toast.LENGTH_SHORT).show();
        }
    }

    public void definiValor() {

        //definindo valor
        double valor = Double.parseDouble( String.valueOf(valorProductEdTx_EDIT_M.getText()) );
        valor = (Math.rint (valor * 100.0) / 100.0);

        double quantidade = Double.parseDouble( quantProductID );
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

        valorProductEDIT_M.setText(valorStr);
        totalProductEDIT_M.setText(totalStr);

    }

}
