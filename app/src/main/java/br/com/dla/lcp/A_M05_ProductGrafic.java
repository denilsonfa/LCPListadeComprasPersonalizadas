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
import android.view.WindowManager;

import com.google.android.material.navigation.NavigationView;

public class A_M05_ProductGrafic extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    //Menu (DrawerLayout) == Creditos: Coding With Tea
    DrawerLayout drawerLayout05;
    NavigationView navigationView05;
    Toolbar toolbar05;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(1);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.rgb(33,135,255));
        setContentView(R.layout.activity_m05_product_grafic);

        //Activity = Menu
        drawerLayout05 =findViewById(R.id.drawer_layout05);
        navigationView05 =findViewById(R.id.nav_view05);
        toolbar05 =findViewById(R.id.toolbar05);

        //Activity = ToolBar
        setSupportActionBar(toolbar05);
        getSupportActionBar().setTitle("");

        //Activity = Navigation Menu
        navigationView05.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout05, toolbar05, R.string.ico_dw_open, R.string.ico_dw_close);
        drawerLayout05.addDrawerListener(toggle);
        toggle.syncState();

        navigationView05.setNavigationItemSelectedListener(this);
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
        drawerLayout05.closeDrawer(GravityCompat.START); return true;
    }
}