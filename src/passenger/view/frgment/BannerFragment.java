package passenger.view.frgment;


import com.aiton.yc.client.R;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import shane_library.shane.utils.UILUtils;



public class BannerFragment extends Fragment
{

    private View mLayout;
    private String mImageUrl;
    private ImageView mBanner_image;

    public BannerFragment()
    {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public BannerFragment(String imageUrl)
    {
        this.mImageUrl = imageUrl;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mLayout = inflater.inflate(R.layout.fragment_banner, null);
        initUI();
        return mLayout;
    }

    private void initUI()
    {
        mBanner_image = (ImageView) mLayout.findViewById(R.id.iv_banner_image);
        if (mImageUrl != null && !"".equals(mImageUrl))
        {
            UILUtils.displayImageNoAnim(mImageUrl, mBanner_image);
        } else
        {
            mBanner_image.setImageResource(R.drawable.banner);
        }

    }
}
