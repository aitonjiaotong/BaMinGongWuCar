package passenger.view.customview;

import com.aiton.yc.client.R;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/8/11.
 */
public class MyCarDialog extends ProgressDialog {
    private Context context;
    public MyCarDialog(Context context) {
        super(context, R.style.MineDialog);
        this.context = context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }
    public void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_car_dialog, null);
        setContentView(view);

        ImageView imageView_rotate = (ImageView) view.findViewById(R.id.imageView_rotate);
        imageView_rotate.setImageResource(R.drawable.car_gif);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView_rotate.getDrawable();
        animationDrawable.setOneShot(false);
        animationDrawable.start();
//        Window dialogWindow = getWindow();
//        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
//        lp.width = (int) (d.widthPixels * 0.8); // 高度设置为屏幕的0.6
//        dialogWindow.setAttributes(lp);
    }
}
