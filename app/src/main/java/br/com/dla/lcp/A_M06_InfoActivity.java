package br.com.dla.lcp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class A_M06_InfoActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    //Menu (DrawerLayout) == Creditos: Coding With Tea
    DrawerLayout drawerLayout06;
    NavigationView navigationView06;
    Toolbar toolbar06;

    ImageView imgLogo, img_sobre, img_dev, img_doc, img_site, img_github;
    LinearLayout option_sobre, option_dev, option_doc, option_site, option_github;
    LinearLayout option_sobre_txt, option_dev_txt, option_doc_txt, option_site_txt, option_github_txt;
    TextView hello, txtLogo01, txtLogo02, txt_sobre;

    Animation fadein, fadeout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(1);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.rgb(0,100,255));
        setContentView(R.layout.activity_m06_info);

        //Activity = Menu
        drawerLayout06 =findViewById(R.id.drawer_layout06);
        navigationView06 =findViewById(R.id.nav_view06);
        toolbar06 =findViewById(R.id.toolbar06);

        //Activity = ToolBar
        setSupportActionBar(toolbar06);
        getSupportActionBar().setTitle("");

        //Activity = Navigation Menu
        navigationView06.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout06, toolbar06, R.string.ico_dw_open, R.string.ico_dw_close);
        drawerLayout06.addDrawerListener(toggle);
        toggle.syncState();

        navigationView06.setNavigationItemSelectedListener(this);

        //Animação
        fadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
        fadeout = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadeout);
        //OBJETO.startAnimation(fadein);

        //SOBRE
        //ImageView: imgLogo, img_sobre, img_dev, img_doc, img_site, img_github;
        imgLogo = findViewById(R.id.imgLogo);
        img_sobre = findViewById(R.id.img_sobre);
        img_dev = findViewById(R.id.img_dev);
        img_doc = findViewById(R.id.img_doc);
        img_site = findViewById(R.id.img_site);
        img_github = findViewById(R.id.img_github);

        //LinearLayout: option_sobre, option_dev, option_doc, option_site, option_github;
        option_sobre = findViewById(R.id.option_sobre);
        option_dev = findViewById(R.id.option_dev);
        option_doc = findViewById(R.id.option_doc);
        option_site = findViewById(R.id.option_site);
        option_github = findViewById(R.id.option_github);

        //LinearLayout: option_sobre_txt, option_dev_txt, option_doc_txt, option_site_txt, EasterEggDevDL, option_site_github;
        option_sobre_txt = findViewById(R.id.option_sobre_txt);
        option_dev_txt = findViewById(R.id.option_dev_txt);
        option_doc_txt = findViewById(R.id.option_doc_txt);
        option_site_txt = findViewById(R.id.option_site_txt);
        option_github_txt = findViewById(R.id.option_site_github);

        //TextView: EasterEggDevDL, txtLogo01, txtLogo02, txt_sobre;
        hello = findViewById(R.id.hello);
        txtLogo01 = findViewById(R.id.txtLogo01);
        txtLogo02 = findViewById(R.id.txtLogo02);
        txt_sobre = findViewById(R.id.txt_sobre);

        //definir visibilidade
        option_sobre_txt.setVisibility(View.GONE);
        option_dev_txt.setVisibility(View.GONE);
        option_doc_txt.setVisibility(View.GONE);
        option_site_txt.setVisibility(View.GONE);
        option_github_txt.setVisibility(View.GONE);

        //definindo texto sobre a aplicação
        txt_sobre.setText(R.string.tips_desc06_01);

        //Definir botões
        //menu
        option_sobre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(option_sobre_txt.getVisibility() == View.GONE){
                    option_sobre_txt.setVisibility(View.VISIBLE);
                } else {
                    option_sobre_txt.setVisibility(View.GONE);
                }
            }
        });

        option_dev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(option_dev_txt.getVisibility() == View.GONE){
                    option_dev_txt.setVisibility(View.VISIBLE);
                } else {
                    option_dev_txt.setVisibility(View.GONE);
                }
            }
        });

        option_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(option_doc_txt.getVisibility() == View.GONE){
                    option_doc_txt.setVisibility(View.VISIBLE);
                } else {
                    option_doc_txt.setVisibility(View.GONE);
                }
            }
        });

        option_site.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(option_site_txt.getVisibility() == View.GONE){
                    option_site_txt.setVisibility(View.VISIBLE);
                } else {
                    option_site_txt.setVisibility(View.GONE);
                }
            }
        });

        option_github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(option_github_txt.getVisibility() == View.GONE){
                    option_github_txt.setVisibility(View.VISIBLE);
                } else {
                    option_github_txt.setVisibility(View.GONE);
                }
            }
        });

        //Textos clicaveis
        option_doc_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chamar("https://www.lcp.eteccruzeiro.dev.br/doc");
            }
        });

        option_site_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chamar("https://www.lcp.eteccruzeiro.dev.br/");
            }
        });

        option_github_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chamar("https://github.com/denilsonfa/LCPListadeComprasPersonalizadas");
            }
        });


        //Mostrar/Esconder opção Graficos
        S_ConexaoDAO s_conexaoDAO1 = new S_ConexaoDAO(A_M06_InfoActivity.this);
        int countListCheck = s_conexaoDAO1.numListaCheck();
        final Menu menu = navigationView06.getMenu();
        menu.findItem(R.id.nav_menu07).setVisible(false);
        menu.findItem(R.id.nav_menu08).setVisible(false);

        if (countListCheck >= 1) {
            menu.findItem(R.id.nav_menu05).setVisible(true);
        } else {
            menu.findItem(R.id.nav_menu05).setVisible(false);
        }

        //HELLO
        hello.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                //ImageView: imgLogo, img_sobre, img_dev, img_doc, img_site;
                imgLogo.setImageResource(R.drawable.ic_item_hello);
                ColorStateList csl = AppCompatResources.getColorStateList(A_M06_InfoActivity.this, R.color.textAzul01_t);
                ImageViewCompat.setImageTintList(imgLogo, csl);

                imgLogo.startAnimation(fadein);
                txtLogo01.setText("Meus parabéns, você acaba de descobrir um Easter Egg!");
                txtLogo02.setText("Me sigue no Instagram. ✌✌✌");
                txtLogo01.startAnimation(fadein);
                txtLogo02.startAnimation(fadein);

                menu.findItem(R.id.nav_menu07).setVisible(true);
                menu.findItem(R.id.nav_menu08).setVisible(true);

                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        chamar("https://www.instagram.com/denilson_fa/");
                    }
                }, 5000);
                return false;
            }
        });

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
        drawerLayout06.closeDrawer(GravityCompat.START); return true;
    }

    public void chamar(String link){
        String url = link;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}