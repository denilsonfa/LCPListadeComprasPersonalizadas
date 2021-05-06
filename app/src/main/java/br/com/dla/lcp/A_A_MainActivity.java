package br.com.dla.lcp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;


public class A_A_MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(1);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.rgb(149,16,149));
        setContentView(R.layout.activity_a_main);

    }

    boolean singleBack = false;
    @Override
    public void onBackPressed() {
        if (singleBack) {
            super.onBackPressed();
            return;
        }
        this.singleBack = true;
        Toast.makeText(this, R.string.doubleExit, Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() { singleBack=false;}
        }, 2000);
    }


    public void main_menu01(View view){
        //Criar Lista
        //Toast.makeText(A_A_MainActivity.this, R.string.main_menu01,Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, A_M01_ListCreate_SetList.class);
        startActivity(intent);
    }

    public void main_menu02(View view){
        //Consultar Lista
        //Toast.makeText(A_A_MainActivity.this, R.string.main_menu02,Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, A_M02_ListConsult_SetList.class);
        startActivity(intent);
    }

    public void main_menu03(View view){
        //Conferir Extrato
        //Toast.makeText(A_A_MainActivity.this, R.string.main_menu03,Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, A_M03_ListExtrato_SetList.class);
        startActivity(intent);
    }

    public void main_menu04(View view){
        //Ajuda e Ajustes
        //Toast.makeText(A_A_MainActivity.this, R.string.main_menu04,Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, A_M04_ConfigActivity.class);
        startActivity(intent);
    }

    public void main_menu05(View view){
        //Grafico de Consumo
        //Toast.makeText(A_A_MainActivity.this, R.string.main_menu05,Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, A_M05_ProductGrafic.class);
        startActivity(intent);
    }

    public void main_menu06(View view){
        //Sobre
        //Toast.makeText(A_A_MainActivity.this, R.string.main_menu06,Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, A_M06_InfoActivity.class);
        startActivity(intent);
    }

    public void main_menu07(View view){
        //SplashScreen
        //Toast.makeText(A_A_MainActivity.this, R.string.main_menu07,Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, A_A_SplashScreenActivity.class);
        startActivity(intent);
    }

    public void main_menu08(View view){
        //Onboarding
        //Toast.makeText(A_A_MainActivity.this, R.string.main_menu08,Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, A_A_OnBoardingActivity.class);
        startActivity(intent);
    }

    public void main_menu09(View view){
        //Temporario
        //Toast.makeText(A_A_MainActivity.this, R.string.main_menu09,Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, A_M09_Tmp.class);
        startActivity(intent);
    }

}