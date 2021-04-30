package br.com.dla.lcp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import androidx.viewpager.widget.ViewPager;

import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

//Creditos: Created by Jaison

public class A_A_OnBoardingActivity extends AppCompatActivity {

    private LinearLayout pager_indicator;
    private int dotsCount;
    private ImageView[] dots;

    private ViewPager onboard_pager;
    private S_OnBoard_Adapter mAdapter;
    private Button btn_get_started;
    int previous_pos=0;

    ArrayList<S_OnBoard_Item> onBoardItems=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(1);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.rgb(149,16,149));
        setContentView(R.layout.activity_a_on_boarding);

        btn_get_started = (Button) findViewById(R.id.btn_get_started);
        onboard_pager = (ViewPager) findViewById(R.id.pager_introduction);
        pager_indicator = (LinearLayout) findViewById(R.id.viewPagerCountDots);

        loadData();

        mAdapter = new S_OnBoard_Adapter(this,onBoardItems);
        onboard_pager.setAdapter(mAdapter);
        onboard_pager.setCurrentItem(0);
        onboard_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotsCount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(A_A_OnBoardingActivity.this, R.drawable.ic_menu_selec_off));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(A_A_OnBoardingActivity.this, R.drawable.ic_menu_selec_on));

                int pos=position+1;
                if(pos==dotsCount&&previous_pos==(dotsCount-1))
                     show_animation();
                else if(pos==(dotsCount-1)&&previous_pos==dotsCount)
                     hide_animation();
                previous_pos=pos;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

//        btn_get_started.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(A_A_OnBoardingActivity.this,R.string.tips01,Toast.LENGTH_LONG).show();
//            }
//        });

        btn_get_started.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent i = new Intent(A_A_OnBoardingActivity.this, A_A_MainActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                finish();
//                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });


        setUiPageViewController();
    }

    public void loadData()
    {

        int[] header = {R.string.tips01, R.string.tips02, R.string.tips03, R.string.tips04, R.string.tips05};
        int[] desc = {R.string.tips_desc01, R.string.tips_desc02, R.string.tips_desc03, R.string.tips_desc04, R.string.tips_desc05};
        int[] imageId = {R.drawable.img_tips_bg01, R.drawable.img_tips_bg02, R.drawable.img_tips_bg03, R.drawable.img_tips_bg04, R.drawable.img_tips_bg05};

        for(int i=0;i<imageId.length;i++)
        {
            S_OnBoard_Item item=new S_OnBoard_Item();
            item.setImageID(imageId[i]);
            item.setTitle(getResources().getString(header[i]));
            item.setDescription(getResources().getString(desc[i]));

            onBoardItems.add(item);
        }
    }

    // Button bottomUp animation

    public void show_animation()
    {
        Animation show = AnimationUtils.loadAnimation(this, R.anim.slide_up_anim);
        btn_get_started.startAnimation(show);

        show.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                btn_get_started.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                btn_get_started.clearAnimation();

            }

        });


    }

    // Button Topdown animation

    public void hide_animation()
    {
        Animation hide = AnimationUtils.loadAnimation(this, R.anim.slide_down_anim);

        btn_get_started.startAnimation(hide);

        hide.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                btn_get_started.clearAnimation();
                btn_get_started.setVisibility(View.GONE);

            }

        });


    }

    // setup the
    private void setUiPageViewController() {

        dotsCount = mAdapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(A_A_OnBoardingActivity.this, R.drawable.ic_menu_selec_off));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(6, 0, 6, 0);

            pager_indicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(A_A_OnBoardingActivity.this, R.drawable.ic_menu_selec_on));
    }


}
