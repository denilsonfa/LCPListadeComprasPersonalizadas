package br.com.dla.lcp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class A_M04_AjudaActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    //Menu (DrawerLayout) == Creditos: Coding With Tea
    DrawerLayout drawerLayout04;
    NavigationView navigationView04;
    Toolbar toolbar04;

    //Tela de Ajuda e Ajustes
    LinearLayout help01, help02, help03, help04, ajust01;
    LinearLayout help01_txt, help02_txt, help03_txt, help04_txt, ajust01_txt;
    ImageView img_help01, img_help02, img_help03, img_help04, img_ajust01;
    TextView countTotalList, countTotalProduct;
    Button ajust01_op01, ajust01_op02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(1);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.rgb(0,100,255));
        setContentView(R.layout.activity_m04_ajuda);

        //Activity = Menu
        drawerLayout04 =findViewById(R.id.drawer_layout04);
        navigationView04 =findViewById(R.id.nav_view04);
        toolbar04 =findViewById(R.id.toolbar04);

        //Activity = ToolBar
        setSupportActionBar(toolbar04);
        getSupportActionBar().setTitle("");

        //Activity = Navigation Menu
        navigationView04.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout04, toolbar04, R.string.ico_dw_open, R.string.ico_dw_close);
        drawerLayout04.addDrawerListener(toggle);
        toggle.syncState();

        navigationView04.setNavigationItemSelectedListener(this);

        //Tela de Ajuda e Ajustes
        //LinearLayout help01, help02, help03, help04, ajust01;
        help01 = findViewById(R.id.help01);
        help02 = findViewById(R.id.help02);
        help03 = findViewById(R.id.help03);
        help04 = findViewById(R.id.help04);
        ajust01 = findViewById(R.id.ajust01);

        //LinearLayout help01_txt, help02_txt, help03_txt, help04_txt, ajust01_txt;
        help01_txt = findViewById(R.id.help01_txt);
        help02_txt = findViewById(R.id.help02_txt);
        help03_txt = findViewById(R.id.help03_txt);
        help04_txt = findViewById(R.id.help04_txt);
        ajust01_txt = findViewById(R.id.ajust01_txt);

        //definir visibilidade
        help01_txt.setVisibility(View.GONE);
        help02_txt.setVisibility(View.GONE);
        help03_txt.setVisibility(View.GONE);
        help04_txt.setVisibility(View.GONE);
        ajust01_txt.setVisibility(View.GONE);

        //ImageView img_help01, img_help02, img_help03, img_help04, img_ajust01;
        img_help01 = findViewById(R.id.img_help01);
        img_help02 = findViewById(R.id.img_help02);
        img_help03 = findViewById(R.id.img_help03);
        img_help04 = findViewById(R.id.img_help04);
        img_ajust01 = findViewById(R.id.img_ajust01);

        //definir rotação
        img_help01.setRotation(0);
        img_help02.setRotation(0);
        img_help03.setRotation(0);
        img_help04.setRotation(0);
        img_ajust01.setRotation(0);

        //TextView countTotalList, countTotalProduct;
        countTotalList = findViewById(R.id.countTotalList);
        countTotalProduct = findViewById(R.id.countTotalProduct);


        //TextView ajust01_op01, ajust01_op02;
        ajust01_op01 = findViewById(R.id.ajust01_op01);
        ajust01_op02 = findViewById(R.id.ajust01_op02);

        //Itens setOnClickListener
        help01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(help01_txt.getVisibility() == View.GONE){
                    help01_txt.setVisibility(View.VISIBLE);
                } else {
                    help01_txt.setVisibility(View.GONE);
                }

                if(img_help01.getRotation() == 0){
                    img_help01.setRotation(180);
                } else {
                    img_help01.setRotation(0);
                }
            }
        });

        help02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(help02_txt.getVisibility() == View.GONE){
                    help02_txt.setVisibility(View.VISIBLE);
                } else {
                    help02_txt.setVisibility(View.GONE);
                }

                if(img_help02.getRotation() == 0){
                    img_help02.setRotation(180);
                } else {
                    img_help02.setRotation(0);
                }
            }
        });

        help03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(help03_txt.getVisibility() == View.GONE){
                    help03_txt.setVisibility(View.VISIBLE);
                } else {
                    help03_txt.setVisibility(View.GONE);
                }

                if(img_help03.getRotation() == 0){
                    img_help03.setRotation(180);
                } else {
                    img_help03.setRotation(0);
                }
            }
        });

        help04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(help04_txt.getVisibility() == View.GONE){
                    help04_txt.setVisibility(View.VISIBLE);
                } else {
                    help04_txt.setVisibility(View.GONE);
                }

                if(img_help04.getRotation() == 0){
                    img_help04.setRotation(180);
                } else {
                    img_help04.setRotation(0);
                }
            }
        });

        ajust01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ajust01_txt.getVisibility() == View.GONE){
                    ajust01_txt.setVisibility(View.VISIBLE);
                } else {
                    ajust01_txt.setVisibility(View.GONE);
                }

                if(img_ajust01.getRotation() == 0){
                    img_ajust01.setRotation(180);
                } else {
                    img_ajust01.setRotation(0);
                }
            }
        });

        //Button ajust01_op01, ajust01_op02;
        ajust01_op01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(A_M04_AjudaActivity.this, A_M04_AjudaActivity_DeleteList.class);
                startActivity(intent);

            }
        });

        ajust01_op02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(A_M04_AjudaActivity.this);
                builder.setTitle(R.string.limparDados);
                builder.setMessage(R.string.limparDadosObs);
                //builder.setIcon(R.drawable.ic_item_alert);

                //define um botão negativo
                builder.setNegativeButton(R.string.nao, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        Toast.makeText(A_M04_AjudaActivity.this, R.string.cancel, Toast.LENGTH_SHORT).show();

                    }
                });

                //define um botão positivo
                builder.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        S_ConexaoDAO.deleteDATABASE(A_M04_AjudaActivity.this);
                        Toast.makeText(A_M04_AjudaActivity.this, R.string.limparDados_confirm, Toast.LENGTH_SHORT).show();

                        ((Activity)A_M04_AjudaActivity.this).recreate();

                    }
                });

                //cria o AlertDialog
                AlertDialog  alerta = builder.create();

                //Exibe
                alerta.show();

            }
        });


        //Mostrar/Esconder opção Graficos
        S_ConexaoDAO s_conexaoDAO1 = new S_ConexaoDAO(A_M04_AjudaActivity.this);
        int countListCheck = s_conexaoDAO1.numListaCheck();
        Menu menu = navigationView04.getMenu();

        if (countListCheck >= 1) {
            menu.findItem(R.id.nav_menu05).setVisible(true);
        } else {
            menu.findItem(R.id.nav_menu05).setVisible(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        //TextView countTotalList, countTotalProduct;
        S_ConexaoDAO sConexaoDAO = new S_ConexaoDAO(A_M04_AjudaActivity.this);

        String countTotalListTxt = String.valueOf(sConexaoDAO.countTotalList());
        String countTotalProductTxt = String.valueOf(sConexaoDAO.countTotalProduct());

        countTotalList.setText(countTotalListTxt);
        countTotalProduct.setText(countTotalProductTxt);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_menu01:
                startActivity(new Intent(getBaseContext(), A_M01_ListCreate_SetList.class));
                finish();
                break;
            case R.id.nav_menu02:
                startActivity(new Intent(getBaseContext(), A_M02_ListConsult_SetList.class));
                finish();
                break;
            case R.id.nav_menu03:
                startActivity(new Intent(getBaseContext(), A_M03_ListExtrato_SetList.class));
                finish();
                break;
            case R.id.nav_menu04:
                startActivity(new Intent(getBaseContext(), A_M04_AjudaActivity.class));
                finish();
                break;
            case R.id.nav_menu05:
                startActivity(new Intent(getBaseContext(), A_M05_ProductGrafic.class));
                finish();
                break;
            case R.id.nav_menu06:
                startActivity(new Intent(getBaseContext(), A_M06_InfoActivity.class));
                finish();
                break;
            case R.id.nav_menu07:
                startActivity(new Intent(getBaseContext(), A_A_SplashScreenActivity.class));
                finish();
                break;
            case R.id.nav_menu08:
                startActivity(new Intent(getBaseContext(), A_A_OnBoardingActivity.class));
                finish();
                break;
        }
        drawerLayout04.closeDrawer(GravityCompat.START); return true;
    }
}