package passenger.view.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.easytaxi.ui.BaseFragementActivity;
import passenger.constant.Constant;
import passenger.model.UserLoginInfo;
import passenger.utils.ApiClient;
import passenger.utils.LoginState;
import passenger.utils.VersionAndServerIsCanUse;
import passenger.view.customview.MViewPaper;
import passenger.view.customview.MyCarDialog;
import passenger.view.frgment.MainFragment;
import passenger.view.frgment.MineFragment;
import passenger.view.frgment.OrderFragment;
import shane_library.shane.upgrade.UpgradeUtils;
import shane_library.shane.utils.GsonUtils;
import shane_library.shane.utils.VolleyListener;

import com.aiton.yc.client.R;
import com.android.volley.VolleyError;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends BaseFragementActivity implements OnClickListener
{

    private String[] tabsItem;
    private Class[] fragment = new Class[]{MainFragment.class, OrderFragment.class, MineFragment.class};
    private int[] imgRes = new int[]{R.drawable.shouye_selector, R.drawable.dingdan_selector, R.drawable.ic_home_me_selector};
    private ImageView[] tabs_img = new ImageView[3];
    private TextView[] tabs_text = new TextView[3];
//    private FragmentTabHost mTabHost;
    private int mBackKey;
    private String mId;
    private String mDeviceId;
    private LoginState mLoginState;
    private long currentTime = 0;
    private String isConnection;
    private String mConfirm;
    private AlertDialog mDialog;
    private MViewPaper viewPager;
    RelativeLayout relaTab01;
    RelativeLayout relaTab02;
    RelativeLayout relaTab03;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isConnection = getResources().getString(R.string.no_connection);
        tabsItem = getResources().getStringArray(R.array.tab);
        mConfirm = getResources().getString(R.string.confirm);
        findID();
        initUI();
        setListener();
        initLoginState();
        checkVersionAndServerIsCanUse();
        getIntentForSetCurrentTab();
    }

    private void setListener() {
    	relaTab01.setOnClickListener(this);
    	relaTab02.setOnClickListener(this);
    	relaTab03.setOnClickListener(this);
	}

	private void findID() {
    	relaTab01 = (RelativeLayout) findViewById(R.id.relaTab01);
    	relaTab02 = (RelativeLayout) findViewById(R.id.relaTab02);
    	relaTab03 = (RelativeLayout) findViewById(R.id.relaTab03);
    	tabs_img[0] = (ImageView) findViewById(R.id.tabs_img01);
    	tabs_img[1] = (ImageView) findViewById(R.id.tabs_img02);
    	tabs_img[2] = (ImageView) findViewById(R.id.tabs_img03);
    	tabs_text[0] = (TextView) findViewById(R.id.tabs_text01);
    	tabs_text[1] = (TextView) findViewById(R.id.tabs_text02);
    	tabs_text[2] = (TextView) findViewById(R.id.tabs_text03);
    	viewPager = (MViewPaper) findViewById(R.id.viewPager);
	}

	private void initUI()
    {
		setCurrentTabs(0);
    	viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
    	viewPager.setDisableScroll(true);
//        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
//        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtab);
//        for (int i = 0; i < imgRes.length; i++)
//        {
//            View inflate = getLayoutInflater().inflate(R.layout.tabs_item, null);
//            TextView tabs_text = (TextView) inflate.findViewById(R.id.tabs_text);
//            ImageView tabs_img = (ImageView) inflate.findViewById(R.id.tabs_img);
//            tabs_text.setText(tabsItem[i]);
//            tabs_img.setImageResource(imgRes[i]);
//            mTabHost.addTab(mTabHost.newTabSpec("" + i).setIndicator(inflate), fragment[i], null);
//        }
    }
    
    private void setCurrentTabs(int position) {
    	tabs_img[position%3].setSelected(true);
    	tabs_img[(position+1)%3].setSelected(false);
    	tabs_img[(position+2)%3].setSelected(false);
    	tabs_text[position%3].setSelected(true);
    	tabs_text[(position+1)%3].setSelected(false);
    	tabs_text[(position+2)%3].setSelected(false);
	}

	public class MyAdapter extends FragmentPagerAdapter{

		public MyAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
			if(arg0==0){
				return new MainFragment();
			}else if(arg0==1){
				return new OrderFragment();
			}else if(arg0==2){
				return new MineFragment();
			}
			return null;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 3;
		}
		
    }

    private void initLoginState()
    {
        mLoginState = LoginState.getInstance(MainActivity.this);
        UserLoginInfo loginInfo = mLoginState.getLoginInfo();
        mId = loginInfo.getUserId();
        mDeviceId = loginInfo.getDeviceId();
    }

    private void checkVersionAndServerIsCanUse()
    {
    	final MyCarDialog myCarDialog = new MyCarDialog(MainActivity.this);
		myCarDialog.show();
		myCarDialog.setCancelable(false);
        ApiClient.getServerIsLive(MainActivity.this, new VolleyListener()
        {
            @Override
            public void onErrorResponse(VolleyError volleyError)
            {
            	myCarDialog.dismiss();
                if (isConnection.equals(volleyError.getMessage()))
                {
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.not_connect_net), Toast.LENGTH_SHORT).show();
                } else
                {
                    setDialogCheck(getResources().getString(R.string.server_is_upgrading), mConfirm);
                }
            }

            @Override
            public void onResponse(String s)
            {
            	myCarDialog.dismiss();
                VersionAndServerIsCanUse versionAndHouTaiIsCanUse = GsonUtils.parseJSON(s, VersionAndServerIsCanUse.class);
                int ableVersion = versionAndHouTaiIsCanUse.getAbleVersion();
                if (versionAndHouTaiIsCanUse.isLive())
                {
                    if (Constant.ABLEVERSION < ableVersion)
                    {
                        setDialogCheck(getResources().getString(R.string.able_version_unused), mConfirm);
                    } else
                    {
                        checkUpGrade();
                        //检查是否在其他设备上登录
//                        checkIsLoginOnOtherDevice();
                    }
                } else
                {
                    setDialogCheck(versionAndHouTaiIsCanUse.getMessage(), mConfirm);
                }
            }
        });
    }


    private void getIntentForSetCurrentTab()
    {
        Intent intent = getIntent();
        mBackKey = intent.getIntExtra(Constant.IntentKey.BACK_TO_ORDER_LIST_KEY, -1);
        if (-1 != mBackKey)
        {
            switch (mBackKey)
            {
                case Constant.IntentKey.ORG_BACK_INT_VALUE:
                    //机构用车返回的当前【订单列表默认为机构用车，无需设置二级Tab】
                    break;
            }
            viewPager.setCurrentItem(1);
            setCurrentTabs(1);
        }
    }

//    private void checkIsLoginOnOtherDevice()
//    {
//        if (mLoginState.isLogin())
//        {
//            Map<String, String> map = new HashMap<String, String>();
//            map.put("account_id", mId);
//            ApiClient.checkIsLoginOnOtherDevice(MainActivity.this, map, new VolleyListener()
//            {
//                @Override
//                public void onErrorResponse(VolleyError volleyError)
//                {
//                    if (isConnection.equals(volleyError.getMessage()))
//                    {
//                        Toast.makeText(MainActivity.this, getResources().getString(R.string.not_connect_net), Toast.LENGTH_SHORT).show();
//                    } else
//                    {
//                        setDialogCheck(getResources().getString(R.string.server_is_upgrading), mConfirm);
//                    }
//                }
//
//                @Override
//                public void onResponse(String s)
//                {
//                    if (!s.equals(mDeviceId))
//                    {
//                        setUnLoginDialog(getResources().getString(R.string.account_abnormal), mConfirm);
//                    }
//                }
//            });
//        }
//    }

    private void setUnLoginDialog(String messageTxt, String iSeeTxt)
    {
        View commit_dialog = getLayoutInflater().inflate(R.layout.commit_dialog, null);
        TextView message = (TextView) commit_dialog.findViewById(R.id.message);
        Button ISee = (Button) commit_dialog.findViewById(R.id.ISee);
        message.setText(messageTxt);
        ISee.setText(iSeeTxt);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        mDialog = builder.setView(commit_dialog)
                .create();
        mDialog.setCancelable(false);
        mDialog.show();
        commit_dialog.findViewById(R.id.ISee).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mDialog.dismiss();
                //清除用户登录信息
                mLoginState.logout(MainActivity.this);
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, SmsLoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void checkUpGrade()
    {
        UpgradeUtils.checkUpgrade(MainActivity.this, Constant.URL.UP_GRADE);
    }

    //dialog提示
    private void setDialogCheck(String messageTxt, String iSeeTxt)
    {
        View commit_dialog = getLayoutInflater().inflate(R.layout.commit_dialog, null);
        TextView message = (TextView) commit_dialog.findViewById(R.id.message);
        Button ISee = (Button) commit_dialog.findViewById(R.id.ISee);
        message.setText(messageTxt);
        ISee.setText(iSeeTxt);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        final AlertDialog dialog = builder.setView(commit_dialog).create();
        dialog.setCancelable(false);
        dialog.show();
        commit_dialog.findViewById(R.id.ISee).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
                animFromBigToSmallOUT();
            }
        });
    }


    @Override
    public void onBackPressed()
    {
        if ((System.currentTimeMillis() - currentTime) > 1000)
        {
            Toast toast = Toast.makeText(MainActivity.this, getResources().getString(R.string.double_click_to_exit), Toast.LENGTH_SHORT);
            toast.show();
            currentTime = System.currentTimeMillis();
        } else
        {
            finish();
            animFromBigToSmallOUT();
        }
    }

    private void animFromBigToSmallOUT()
    {
        overridePendingTransition(R.anim.fade_in, R.anim.big_to_small_fade_out);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (mDialog != null && mDialog.isShowing())
        {
            mDialog.dismiss();
        }
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.relaTab01:
			viewPager.setCurrentItem(0);
			setCurrentTabs(0);
			break;
		case R.id.relaTab02:
			viewPager.setCurrentItem(1);
			setCurrentTabs(1);
			break;
		case R.id.relaTab03:
			viewPager.setCurrentItem(2);
			setCurrentTabs(2);
			break;
		default:
			break;
		}
	}
}
