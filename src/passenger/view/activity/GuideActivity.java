package passenger.view.activity;

import com.aiton.yc.client.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.view.WindowManager;
import passenger.view.customview.GuideViewPagerIndicator;
import passenger.view.frgment.GuideFragment;


    public class GuideActivity extends FragmentActivity
{
    private int[] guideImg = new int[]{R.drawable.gui01, R.drawable.gui02, R.drawable.gui03,};
    private GuideViewPagerIndicator mGuideViewPagerIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
        setContentView(R.layout.activity_guide);

        ViewPager myPager = (ViewPager) findViewById(R.id.myPager);
        myPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        mGuideViewPagerIndicator = (GuideViewPagerIndicator) findViewById(R.id.GuideViewPagerIndicator);
        myPager.setOnPageChangeListener(new MyPageChangerListener());

    }

    class MyPageChangerListener implements ViewPager.OnPageChangeListener
    {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
        {
//            mGuideViewPagerIndicator.move(positionOffset, position);

            if (0.0 == positionOffset)
            {
                mGuideViewPagerIndicator.move(position);
            }
        }

        @Override
        public void onPageSelected(int position)
        {

        }

        @Override
        public void onPageScrollStateChanged(int state)
        {

        }
    }

    class MyAdapter extends FragmentPagerAdapter
    {

        public MyAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            GuideFragment guideFragment = new GuideFragment();
            guideFragment.setImg(guideImg[position], guideImg.length - 1, position);
            return guideFragment;
        }

        @Override
        public int getCount()
        {
            return guideImg.length;
        }
    }
}
