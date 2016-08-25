package shane_library.shane.utils;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;


public final class TextViewUtils
{

    public static final int DRAWABLE_TOP = 0;
    public static final int DRAWABLE_LEFT = 1;
    public static final int DRAWABLE_BOTTOM = 2;
    public static final int DRAWABLE_RIGHT = 3;

    public static void setTextDrawable(Context context, int drawableRes, TextView tvName, int where)
    {
        Drawable drawable = context.getResources().getDrawable(drawableRes);
        // 必须设置图片大小，否则不显示
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        switch (where)
        {
            case DRAWABLE_TOP:
                tvName.setCompoundDrawables(null, drawable, null, null);
                break;
            case DRAWABLE_BOTTOM:
                tvName.setCompoundDrawables(null, null, null, drawable);
                break;
            case DRAWABLE_LEFT:
                tvName.setCompoundDrawables(drawable, null, null, null);
                break;
            case DRAWABLE_RIGHT:
                tvName.setCompoundDrawables(null, null, drawable, null);
                break;
        }

    }

    public static void setTextDrawable(Context context, Bitmap bitmap, TextView tvName, int where)
    {
        Drawable drawable = BitmapUtils.bitmap2Drawable(bitmap);
        // 必须设置图片大小，否则不显示
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        switch (where)
        {
            case DRAWABLE_TOP:
                tvName.setCompoundDrawables(null, drawable, null, null);
                break;
            case DRAWABLE_BOTTOM:
                tvName.setCompoundDrawables(null, null, null, drawable);
                break;
            case DRAWABLE_LEFT:
                tvName.setCompoundDrawables(drawable, null, null, null);
                break;
            case DRAWABLE_RIGHT:
                tvName.setCompoundDrawables(null, null, drawable, null);
                break;
        }
    }

    public static void setTextDrawable(Context context, String url, final TextView tvName, final int where)
    {
        ImageLoader.getInstance().loadImage(url, new ImageLoadingListener()
        {
            public void onLoadingStarted(String arg0, View arg1)
            {
            }

            public void onLoadingFailed(String arg0, View arg1, FailReason arg2)
            {
            }

            public void onLoadingComplete(String arg0, View arg1, Bitmap bitmap)
            {
                Drawable drawable = BitmapUtils.bitmap2Drawable(bitmap);
                // 必须设置图片大小，否则不显示
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                switch (where)
                {
                    case DRAWABLE_TOP:
                        tvName.setCompoundDrawables(null, drawable, null, null);
                        break;
                    case DRAWABLE_BOTTOM:
                        tvName.setCompoundDrawables(null, null, null, drawable);
                        break;
                    case DRAWABLE_LEFT:
                        tvName.setCompoundDrawables(drawable, null, null, null);
                        break;
                    case DRAWABLE_RIGHT:
                        tvName.setCompoundDrawables(null, null, drawable, null);
                        break;
                }
            }

            public void onLoadingCancelled(String arg0, View arg1)
            {
            }
        });
    }

}
