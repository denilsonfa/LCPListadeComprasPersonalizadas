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
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class A_M04_AjudaActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    //Menu (DrawerLayout) == Creditos: Coding With Tea
    DrawerLayout drawerLayout04;
    NavigationView navigationView04;
    Toolbar toolbar04;

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