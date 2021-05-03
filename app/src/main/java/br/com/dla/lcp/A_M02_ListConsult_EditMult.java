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

    private String idListLDADOS, nomeListIDDADOS, dataListIDDADOS, checkListIDDADOS;
    private String idProductID, idListP, nomeProductID, quantProductID, medidaProductID, tipoProductID,
            valorProductID, checkProductID, totalProductID;
    private TextView idProductEDIT_M, nomeProductEDIT_M, quantProductEDIT_M, medidaProductEDIT_M,
            tipoProductEDIT_M, valorProductEDIT_M, totalProductEDIT_M, textValorOptionB02_M, confirmEDIT_TXT_L_M;
    private ImageView tipoProduct_img_EDIT_M, checkProduct_img_EDIT_M;
    private EditText valorProductEdTx_EDIT_M;
    private Button cancelEDIT_M, confirmEDIT_M;
    private LinearLayout textValorOptionA02_M, confirmEDIT_EDIT_L_M;

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
        confirmEDIT_TXT_L_M = findViewById(R.id.confirmEDIT_TXT_L_M);

        //LinearLayout
        textValorOptionA02_M = findViewById(R.id.textValorOptionA02_EDIT_TXT_M);
        confirmEDIT_EDIT_L_M = findViewById(R.id.confirmEDIT_EDIT_L_M);
        //TextView
        textValorOptionB02_M = findViewById(R.id.textValorOptionB02_EDIT_TXT_M);

        //Button
        cancelEDIT_M = findViewById(R.id.cancelEDIT_EDIT_U);
        confirmEDIT_M = findViewById(R.id.confirmEDIT_EDIT_U);

    }

    //Recebendo dados do item selecionado para editar
    void getAndSetIntentData() {
        if (getIntent().hasExtra("idListLID") &&
                getIntent().hasExtra("nomeListID") &&
                getIntent().hasExtra("dataListID") &&
                getIntent().hasExtra("checkListID") ) {

            //GETTING
            idListLDADOS = getIntent().getStringExtra("idListLID");
            nomeListIDDADOS = getIntent().getStringExtra("nomeListID");
            dataListIDDADOS = getIntent().getStringExtra("dataListID");
            checkListIDDADOS = getIntent().getStringExtra("checkListID");

            //SETTING
            dados.setIdListL(Integer.parseInt(idListLDADOS));
            dados.setNomeList(nomeListIDDADOS);
            dados.setDataList(dataListIDDADOS);
            dados.setCheckList(Boolean.parseBoolean(checkListIDDADOS));

        } else {
            Toast.makeText(this, R.string.erro_product, Toast.LENGTH_SHORT).show();
        }
    }
}
