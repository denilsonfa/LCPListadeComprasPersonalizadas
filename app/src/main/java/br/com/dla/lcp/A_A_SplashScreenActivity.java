package br.com.dla.lcp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.WindowManager;

public class A_A_SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(1);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.rgb(182,212,248));
        setContentView(R.layout.activity_a_splash_screen);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {

                // Após o tempo de carregamento (3s), a splash screen irá redirecionar
                // Se existir uma database, irá redirecionar para A_A_MainActivity.class (Tela principal)
                // Se não, significa é o primeiro launch do app, onde redireciona para o A_A_OnBoardingActivity.class (Tela de apresentação)

                boolean doesDatabaseExist = S_ConexaoDAO.doesDatabaseExist(A_A_SplashScreenActivity.this);

                if (!doesDatabaseExist) {
                    startActivity(new Intent(getBaseContext(), A_A_OnBoardingActivity.class));
                } else {
                    startActivity(new Intent(getBaseContext(), A_A_MainActivity.class));
                }
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                finish();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);

            }
        }, 3000);
    }

}