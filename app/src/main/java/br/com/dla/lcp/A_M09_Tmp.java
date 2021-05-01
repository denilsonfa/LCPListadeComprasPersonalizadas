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
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;

public class A_M09_Tmp extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    //Menu (DrawerLayout) == Creditos: Coding With Tea
    DrawerLayout drawerLayout09;
    NavigationView navigationView09;
    Toolbar toolbar09;

    private Button addListID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(1);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.rgb(0,100,255));
        setContentView(R.layout.activity_m09_tmp);

        //Activity = Menu
        drawerLayout09 =findViewById(R.id.drawer_layout09);
        navigationView09 =findViewById(R.id.nav_view09);
        toolbar09 =findViewById(R.id.toolbar09);

        //Activity = ToolBar
        setSupportActionBar(toolbar09);
        getSupportActionBar().setTitle("");

        //Activity = Navigation Menu
        navigationView09.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout09, toolbar09, R.string.ico_dw_open, R.string.ico_dw_close);
        drawerLayout09.addDrawerListener(toggle);
        toggle.syncState();

        navigationView09.setNavigationItemSelectedListener(this);

        //S_Dados = Função ação do botão addListID
        addListID = findViewById(R.id.addList);
        addListID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                S_ConexaoDAO crud = new S_ConexaoDAO(getBaseContext());
                //Criando nova lista
                long numListDia = S_Dados.getDia();
                long numListMes = S_Dados.getMes();

                crud.createList(numListDia, numListMes);
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
        drawerLayout09.closeDrawer(GravityCompat.START); return true;
    }
}