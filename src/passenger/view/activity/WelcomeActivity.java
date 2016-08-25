package passenger.view.activity;


import com.aiton.yc.client.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;


public class WelcomeActivity extends Activity
{
    Handler hand = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
        setContentView(R.layout.activity_welcome);
        SharedPreferences sp = getSharedPreferences("isfrist", 0);
        final boolean isfrist = sp.getBoolean("isfrist", true);
        hand.postDelayed(new Runnable()
        {

            @Override
            public void run()
            {
                Intent intent = new Intent();
                if (isfrist)
                {
                    intent.setClass(WelcomeActivity.this, GuideActivity.class);
                } else
                {
                    intent.setClass(WelcomeActivity.this, MainActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}
