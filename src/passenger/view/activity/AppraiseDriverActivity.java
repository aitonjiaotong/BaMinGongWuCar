package passenger.view.activity;

import com.aiton.yc.client.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import passenger.model.OrderDetailsInfo;
import passenger.model.OrderDetailsInfo.ObjectEntity.DriverBean;
import shane_library.shane.utils.UILUtils;


public class AppraiseDriverActivity extends Activity implements View.OnClickListener
{

    private ImageView mIv_back;
    private ImageView mIv_driver_img;
    private ImageView mIv_pj_driver_sex_img;
    private TextView mTv_driver_name;
    private TextView mTv_driver_driving_years;
    private DriverBean mDriverInfo;
    private Button mBtn_pingjia;
    private RatingBar mRatingbar_pingjia;
    private float mRatingCount;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appraise_driver);
        getIntentData();
        findViewID();
        setListener();
        initUI();
    }

    private void getIntentData()
    {
        Intent intent = getIntent();
        mDriverInfo = (DriverBean) intent.getSerializableExtra("driver");
    }

    private void findViewID()
    {
        mIv_back = (ImageView) findViewById(R.id.iv_org_detailback);
        mIv_driver_img = (ImageView) findViewById(R.id.iv_driver_img);
        mIv_pj_driver_sex_img = (ImageView) findViewById(R.id.iv_pj_driver_sex_img);
        mTv_driver_name = (TextView) findViewById(R.id.tv_driver_name);
        mTv_driver_driving_years = (TextView) findViewById(R.id.tv_driver_driving_years);
        mBtn_pingjia = (Button) findViewById(R.id.btn_pingjia);
        mRatingbar_pingjia = (RatingBar) findViewById(R.id.ratingbar_pingjia);
    }

    private void initUI()
    {
        UILUtils.displayImageNoAnim(mDriverInfo.getImage(), mIv_driver_img);
        mTv_driver_name.setText(mDriverInfo.getName());
        if (!"男".equals(mDriverInfo.getSex()))
        {
            mIv_pj_driver_sex_img.setImageResource(R.drawable.xingbienv_2x);
        }
        mTv_driver_driving_years.setText(mDriverInfo.getDrivingYear() + "");

        mRatingbar_pingjia.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener()
        {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser)
            {
                mRatingCount = rating;
            }
        });
    }

    private void setListener()
    {
        mIv_back.setOnClickListener(this);
        mBtn_pingjia.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.iv_org_detailback:
                finish();
                break;
            case R.id.btn_pingjia:
                if (0f == mRatingCount)
                {
                    Toast.makeText(AppraiseDriverActivity.this, "请选择评星数,谢谢!", Toast.LENGTH_SHORT).show();
                } else
                {
                    Toast.makeText(AppraiseDriverActivity.this, "提交成功,感谢您的评价!", Toast.LENGTH_SHORT).show();
                    startToMainActivity();
                }
                break;

        }
    }

    private void startToMainActivity()
    {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
    }
}
