package br.com.dla.lcp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class A_M06_InfoActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    //Menu (DrawerLayout) == Creditos: Coding With Tea
    DrawerLayout drawerLayout06;
    NavigationView navigationView06;
    Toolbar toolbar06;

    ImageView imgLogo, img_sobre, img_dev, img_doc, img_site;
    LinearLayout option_sobre, option_dev, option_doc, option_site;
    LinearLayout option_sobre_txt, option_dev_txt, option_doc_txt, option_site_txt;
    TextView EasterEggDevDL, txtLogo01, txtLogo02;

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
//        fadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down_anim);
//        fadeout = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up_anim);
//        OBJETO.startAnimation(fadein);

        //SOBRE
        //ImageView: imgLogo, img_sobre, img_dev, img_doc, img_site;
        imgLogo = findViewById(R.id.imgLogo);
        img_sobre = findViewById(R.id.img_sobre);
        img_dev = findViewById(R.id.img_dev);
        img_doc = findViewById(R.id.img_doc);
        img_site = findViewById(R.id.img_site);

        //LinearLayout: option_sobre, option_dev, option_doc, option_site;
        option_sobre = findViewById(R.id.option_sobre);
        option_dev = findViewById(R.id.option_dev);
        option_doc = findViewById(R.id.option_doc);
        option_site = findViewById(R.id.option_site);

        //LinearLayout: option_sobre_txt, option_dev_txt, option_doc_txt, option_site_txt, EasterEggDevDL;
        option_sobre_txt = findViewById(R.id.option_sobre_txt);
        option_dev_txt = findViewById(R.id.option_dev_txt);
        option_doc_txt = findViewById(R.id.option_doc_txt);
        option_site_txt = findViewById(R.id.option_site_txt);

        //TextView: EasterEggDevDL, txtLogo01, txtLogo02;
        EasterEggDevDL = findViewById(R.id.EasterEggDevDL);
        txtLogo01 = findViewById(R.id.txtLogo01);
        txtLogo02 = findViewById(R.id.txtLogo02);

        //definir visibilidade
        option_sobre_txt.setVisibility(View.GONE);
        option_dev_txt.setVisibility(View.GONE);
        option_doc_txt.setVisibility(View.GONE);
        option_site_txt.setVisibility(View.GONE);

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

        //Textos clicaveis
        option_doc_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.lcp.eteccruzeiro.dev.br/doc";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        option_site_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.lcp.eteccruzeiro.dev.br/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        EasterEggDevDL.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                getWindow().setStatusBarColor(Color.rgb(0,0,0));
                //ImageView: imgLogo, img_sobre, img_dev, img_doc, img_site;
                imgLogo.setImageResource(R.drawable.lg_lcp_preto);
                img_sobre.setImageResource(R.drawable.ic_item_easteegg);
                img_dev.setImageResource(R.drawable.ic_item_easteegg);
                img_doc.setImageResource(R.drawable.ic_item_easteegg);
                img_site.setImageResource(R.drawable.ic_item_easteegg);

                txtLogo01.setTextColor(0x66000000);
                txtLogo02.setTextColor(0x88000000);

                txtLogo01.setText("Meus parabéns, você acaba de descobrir um Easter Egg!");
                txtLogo02.setText("Me sigue no Instagram. ✌✌✌");

                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String url = "https://www.instagram.com/denilson_fa/";
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
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
                startActivity(new Intent(getBaseContext(), A_M04_ConfigActivity.class));
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
}