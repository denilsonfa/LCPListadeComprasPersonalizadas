package br.com.dla.lcp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class A_M01_ListCreate_Edit extends AppCompatActivity {

    //Criar lista (Adicionar Produto) == Creditos: @Denilson_fa
    S_Dados dados = new S_Dados();
    S_ConexaoDAO crud = new S_ConexaoDAO(getBaseContext());
    private int idListP_tmp;
    private boolean editMode = false;

    private String idProductID, idListP, nomeProductID, quantProductID, medidaProductID, tipoProductID;

    private TextView idProductID_EDIT_TXT, nomeProductID_EDIT_TXT;
    private EditText nomeProductID_EDIT;
    private EditText quantProductID_EDIT;
    private Spinner medidaProductID_EDIT;
    private Spinner tipoProductID_EDIT;
    private Button addProductID_EDIT;

    private ImageView tipoProductID_EDIT_IMG;
    private TextView idProductEDIT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(1);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.rgb(33,135,255));
        setContentView(R.layout.activity_m01_listcreate_edit);

        //S_Dados = ID dos Itens (EditText)
        nomeProductID_EDIT = findViewById(R.id.nomeProductEDIT);
        quantProductID_EDIT = findViewById(R.id.quantProductEDIT);
        medidaProductID_EDIT = findViewById(R.id.medidaProductEDIT);
        tipoProductID_EDIT = findViewById(R.id.tipoProductEDIT);
        addProductID_EDIT = findViewById(R.id.addProductEDIT);

        idProductID_EDIT_TXT = findViewById(R.id.idProductEDIT_TXT);
        nomeProductID_EDIT_TXT = findViewById(R.id.nomeProductEDIT_TXT);
        tipoProductID_EDIT_IMG = findViewById(R.id.tipoProductEDIT_IMG);

        //Activity = Mudando nomeProductID_EDIT e tipoProductID_EDIT em tempo real
        nomeProductID_EDIT.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                nomeProductID_EDIT_TXT.setText( nomeProductID_EDIT.getText() );
            }
        });

        tipoProductID_EDIT.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                if( tipoProductID_EDIT.getSelectedItem().equals("Alimentos") ) {
                    tipoProductID_EDIT_IMG.setImageResource(R.drawable.ic_item_01);
                } else if( tipoProductID_EDIT.getSelectedItem().equals("Carnes e Ovos") ) {
                    tipoProductID_EDIT_IMG.setImageResource(R.drawable.ic_item_02);
                } else if( tipoProductID_EDIT.getSelectedItem().equals("Verduras, Legumes e Frutas") ) {
                    tipoProductID_EDIT_IMG.setImageResource(R.drawable.ic_item_03);
                } else if( tipoProductID_EDIT.getSelectedItem().equals("Bebidas") ) {
                    tipoProductID_EDIT_IMG.setImageResource(R.drawable.ic_item_04);
                } else if( tipoProductID_EDIT.getSelectedItem().equals("Leite e Derivados") ) {
                    tipoProductID_EDIT_IMG.setImageResource(R.drawable.ic_item_05);
                } else if( tipoProductID_EDIT.getSelectedItem().equals("Padaria e Sobremesa") ) {
                    tipoProductID_EDIT_IMG.setImageResource(R.drawable.ic_item_06);
                } else if( tipoProductID_EDIT.getSelectedItem().equals("Saúde e Beleza") ) {
                    tipoProductID_EDIT_IMG.setImageResource(R.drawable.ic_item_07);
                } else if( tipoProductID_EDIT.getSelectedItem().equals("Higiene e Limpeza") ) {
                    tipoProductID_EDIT_IMG.setImageResource(R.drawable.ic_item_08);
                } else {
                    tipoProductID_EDIT_IMG.setImageResource(R.drawable.ic_item_09);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        //Recebendo Dados do Produto selecionado
        getAndSetIntentData();

        //Funções do Botão
        addProductID_EDIT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if( (nomeProductID_EDIT.getText().toString().equals("")) || (quantProductID_EDIT.getText().toString().equals(""))) {
                    Toast.makeText(A_M01_ListCreate_Edit.this, R.string.erro_noText01, Toast.LENGTH_SHORT).show();
                } else {
                    //S_Dados = Guardando S_Dados em variavies
                    dados.setNomeProduct(nomeProductID_EDIT.getText().toString());
                    dados.setQuantProduct(Double.parseDouble(quantProductID_EDIT.getText().toString()));
                    dados.setMedidaProduct(medidaProductID_EDIT.getSelectedItem().toString());
                    dados.setTipoProduct(tipoProductID_EDIT.getSelectedItem().toString());

                    S_ConexaoDAO conexaoDAO = new S_ConexaoDAO(A_M01_ListCreate_Edit.this);
                    String idProduct = String.valueOf(dados.getIdProduct());

                    //Atualizando dados do item no Banco de Dados
                    conexaoDAO.updateProduct(
                            idProduct,
                            dados.getNomeProduct(),
                            dados.getQuantProduct(),
                            dados.getMedidaProduct(),
                            dados.getTipoProduct()
                    );

                    //Finaliza Activity
                    finish();

                    //Mensagem de confirmação
                    Toast.makeText(A_M01_ListCreate_Edit.this, R.string.editedProduct, Toast.LENGTH_SHORT).show();
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
                getIntent().hasExtra("tipoProductID")) {

            //GETTING
            idProductID = getIntent().getStringExtra("idProductID");
            nomeProductID = getIntent().getStringExtra("nomeProductID");
            quantProductID = getIntent().getStringExtra("quantProductID");
            medidaProductID = getIntent().getStringExtra("medidaProductID");
            tipoProductID = getIntent().getStringExtra("tipoProductID");

            //SETTING
            nomeProductID_EDIT.setText(nomeProductID);
            quantProductID_EDIT.setText(quantProductID);

            //Adapter spinner medidaProductID_EDIT
            if (medidaProductID.equals("Unidade(s)")){
                medidaProductID_EDIT.setSelection(0);
            } else if (medidaProductID.equals("kg")){
                medidaProductID_EDIT.setSelection(1);
            } else if (medidaProductID.equals("Litro(s)")){
                medidaProductID_EDIT.setSelection(2);
            } else if (medidaProductID.equals("Caixa(s)")){
                medidaProductID_EDIT.setSelection(3);
            } else if (medidaProductID.equals("Embalagem(s)")){
                medidaProductID_EDIT.setSelection(4);
            } else if (medidaProductID.equals("Galão(es)")){
                medidaProductID_EDIT.setSelection(5);
            } else if (medidaProductID.equals("Garrafa(s)")){
                medidaProductID_EDIT.setSelection(6);
            } else if (medidaProductID.equals("Lata(s)")){
                medidaProductID_EDIT.setSelection(7);
            } else if (medidaProductID.equals("Pacote(s)")){
                medidaProductID_EDIT.setSelection(8);
            }

            //Adapter spinner tipoProductID_EDIT
            if (tipoProductID.equals("Alimentos")){
                tipoProductID_EDIT.setSelection(0);
            } else if (tipoProductID.equals("Carnes e Ovos")){
                tipoProductID_EDIT.setSelection(1);
            } else if (tipoProductID.equals("Verduras, Legumes e Frutas")){
                tipoProductID_EDIT.setSelection(2);
            } else if (tipoProductID.equals("Bebidas")){
                tipoProductID_EDIT.setSelection(3);
            } else if (tipoProductID.equals("Leite e Derivados")){
                tipoProductID_EDIT.setSelection(4);
            } else if (tipoProductID.equals("Padaria e Sobremesa")){
                tipoProductID_EDIT.setSelection(5);
            } else if (tipoProductID.equals("Saúde e Beleza")){
                tipoProductID_EDIT.setSelection(6);
            } else if (tipoProductID.equals("Higiene e Limpeza")){
                tipoProductID_EDIT.setSelection(7);
            } else if (tipoProductID.equals("Outros")){
                tipoProductID_EDIT.setSelection(8);
            }

            //SETTEXT DADOS IN TEXTVIEWS
            String idProduct = "ID: "+idProductID;
            idProductID_EDIT_TXT.setText(idProduct);
            nomeProductID_EDIT_TXT.setText(nomeProductID);

            if( tipoProductID.equals("Alimentos") ) {
                tipoProductID_EDIT_IMG.setImageResource(R.drawable.ic_item_01);
            } else if( tipoProductID.equals("Carnes e Ovos") ) {
                tipoProductID_EDIT_IMG.setImageResource(R.drawable.ic_item_02);
            } else if( tipoProductID.equals("Verduras, Legumes e Frutas") ) {
                tipoProductID_EDIT_IMG.setImageResource(R.drawable.ic_item_03);
            } else if( tipoProductID.equals("Bebidas") ) {
                tipoProductID_EDIT_IMG.setImageResource(R.drawable.ic_item_04);
            } else if( tipoProductID.equals("Leite e Derivados") ) {
                tipoProductID_EDIT_IMG.setImageResource(R.drawable.ic_item_05);
            } else if( tipoProductID.equals("Padaria e Sobremesa") ) {
                tipoProductID_EDIT_IMG.setImageResource(R.drawable.ic_item_06);
            } else if( tipoProductID.equals("Saúde e Beleza") ) {
                tipoProductID_EDIT_IMG.setImageResource(R.drawable.ic_item_07);
            } else if( tipoProductID.equals("Higiene e Limpeza") ) {
                tipoProductID_EDIT_IMG.setImageResource(R.drawable.ic_item_08);
            } else {
                tipoProductID_EDIT_IMG.setImageResource(R.drawable.ic_item_09);
            }

            //SETTING IN S_Dados
            dados.setIdProduct(Integer.parseInt(idProductID));

        } else {
            Toast.makeText(this, R.string.erro_product, Toast.LENGTH_SHORT).show();
        }
    }
}