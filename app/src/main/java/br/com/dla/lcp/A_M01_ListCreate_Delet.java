package br.com.dla.lcp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class A_M01_ListCreate_Delet extends AppCompatActivity {

    //Criar lista (Adicionar Produto) == Creditos: @Denilson_fa
    S_Dados dados = new S_Dados();
    S_ConexaoDAO crud = new S_ConexaoDAO(getBaseContext());
    private int idListP_tmp;
    private boolean editMode = false;

    private String idProductID, idListP, nomeProductID, quantProductID, medidaProductID, tipoProductID;
    private TextView idProductDELET_TXT, nomeProductDELET_TXT, quantProductDELET_TXT, medidaProductDELET_TXT, tipoProductDELET_TXT;
    private ImageView tipoProductDELET_IMG;
    private Button cancelDEL, confirmDEL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(1);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.rgb(255,33,33));
        setContentView(R.layout.activity_m01_listcreate_delet);

        //S_Dados = ID dos Itens (EditText)
        idProductDELET_TXT = findViewById(R.id.idProductDELET_TXT);
        tipoProductDELET_IMG = findViewById(R.id.tipoProductDELET_IMG);
        nomeProductDELET_TXT = findViewById(R.id.nomeProductDELET_TXT);
        quantProductDELET_TXT = findViewById(R.id.quantProductDELET_TXT);
        medidaProductDELET_TXT = findViewById(R.id.medidaProductDELET_TXT);
        tipoProductDELET_TXT = findViewById(R.id.tipoProductDELET_TXT);

        cancelDEL = findViewById(R.id.cancelDEL);
        confirmDEL = findViewById(R.id.confirmDEL);

        //Recebendo Dados do Produto selecionado
        getAndSetIntentData();

        //Função dos botões
        cancelDEL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Finaliza Activity
                finish();

                //Mensagem de confirmação
                Toast.makeText(A_M01_ListCreate_Delet.this, R.string.cancel, Toast.LENGTH_SHORT).show();
            }
        });

        confirmDEL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Apagando item do Banco de Dados
                S_ConexaoDAO conexaoDAO = new S_ConexaoDAO(A_M01_ListCreate_Delet.this);
                String idProduct = String.valueOf(dados.getIdProduct());
                conexaoDAO.deleteProduct(idProduct);

                //Finaliza Activity
                finish();

                //Mensagem de confirmação
                Toast.makeText(A_M01_ListCreate_Delet.this, R.string.deletedProduct, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Recebendo dados do item selecionado para editar
    void getAndSetIntentData() {
        if (getIntent().hasExtra("idProductID") &&
                getIntent().hasExtra("nomeProductID") &&
                getIntent().hasExtra("quantProductID") &&
                getIntent().hasExtra("medidaProductID") &&
                getIntent().hasExtra("tipoProductID")) {

            //GETTING
            idProductID = getIntent().getStringExtra("idProductID");
            nomeProductID = getIntent().getStringExtra("nomeProductID");
            quantProductID = getIntent().getStringExtra("quantProductID");
            medidaProductID = getIntent().getStringExtra("medidaProductID");
            tipoProductID = getIntent().getStringExtra("tipoProductID");

            //SETTING
            String idProduct = "ID: "+idProductID;
            idProductDELET_TXT.setText(idProduct);
            nomeProductDELET_TXT.setText(nomeProductID);
            quantProductDELET_TXT.setText(quantProductID);
            medidaProductDELET_TXT.setText(medidaProductID);
            tipoProductDELET_TXT.setText(tipoProductID);

            if( tipoProductID.equals("Alimentos") ) {
                tipoProductDELET_IMG.setImageResource(R.drawable.ic_item_01);
            } else if( tipoProductID.equals("Carnes e Ovos") ) {
                tipoProductDELET_IMG.setImageResource(R.drawable.ic_item_02);
            } else if( tipoProductID.equals("Verduras, Legumes e Frutas") ) {
                tipoProductDELET_IMG.setImageResource(R.drawable.ic_item_03);
            } else if( tipoProductID.equals("Bebidas") ) {
                tipoProductDELET_IMG.setImageResource(R.drawable.ic_item_04);
            } else if( tipoProductID.equals("Leite e Derivados") ) {
                tipoProductDELET_IMG.setImageResource(R.drawable.ic_item_05);
            } else if( tipoProductID.equals("Padaria e Sobremesa") ) {
                tipoProductDELET_IMG.setImageResource(R.drawable.ic_item_06);
            } else if( tipoProductID.equals("Saúde e Beleza") ) {
                tipoProductDELET_IMG.setImageResource(R.drawable.ic_item_07);
            } else if( tipoProductID.equals("Higiene e Limpeza") ) {
                tipoProductDELET_IMG.setImageResource(R.drawable.ic_item_08);
            } else {
                tipoProductDELET_IMG.setImageResource(R.drawable.ic_item_09);
            }

            //SETTING IN S_Dados
            dados.setIdProduct(Integer.parseInt(idProductID));

        } else {
            Toast.makeText(this, R.string.erro_product, Toast.LENGTH_SHORT).show();
        }
    }
}