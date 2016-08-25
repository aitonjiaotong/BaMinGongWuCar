package passenger.view.activity;

import com.aiton.yc.client.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;


public class ChooseAddrActivity extends Activity implements View.OnClickListener
{
    private ImageView mIv_back;
    private EditText mEt_search_zone;
    private ImageView mIv_clear;
    private ListView mLv_search_ruslt;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_addr);

        findViewId();
        setListener();
        initUI();
    }

    private void findViewId()
    {
        mIv_back = (ImageView) findViewById(R.id.iv_back);
        mIv_clear = (ImageView) findViewById(R.id.iv_clear);
        mEt_search_zone = (EditText) findViewById(R.id.et_search_zone);
        mLv_search_ruslt = (ListView) findViewById(R.id.lv_search_ruslt);
    }

    private void setListener()
    {
        mIv_back.setOnClickListener(this);
        mIv_clear.setOnClickListener(this);
    }

    private void initUI()
    {
        initSearchRusltListView();
        initSearchEditText();

    }

    private void initSearchRusltListView()
    {
//        mLv_search_ruslt.setAdapter();
    }

    private void initSearchEditText()
    {
        mEt_search_zone.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if ("".equalsIgnoreCase(s.toString()))
                {
                    mIv_clear.setVisibility(View.GONE);
                } else
                {
                    mIv_clear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_clear:
                //TODO 清除EditText内容

                break;
        }
    }
}
