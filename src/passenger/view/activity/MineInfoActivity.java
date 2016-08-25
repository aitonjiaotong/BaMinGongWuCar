package passenger.view.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import shane_library.shane.utils.GsonUtils;
import shane_library.shane.utils.UILUtils;

import com.aiton.yc.client.R;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;

import passenger.model.UpLoadUserAvatarReturnInfo;
import passenger.model.UserLoginInfo;
import passenger.utils.ApiClient;
import passenger.utils.LoginState;

public class MineInfoActivity extends Activity implements View.OnClickListener
{
    private String mPhoneNum;
    private TextView mTextView_phone;
    private ImageView mIv_user_avatar;
    private String mImageUrl;
    private PopupWindow mPopupWindow;
    private final int PIC_FROM_CAMERA = 1;
    private final int PIC_FROM＿LOCALPHOTO = 0;
    private Uri mPhotoUri;
    private String mUserId;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mime_info);

        initLoginState();
        findID();
        setListener();
        initUI();
    }

    private void initLoginState()
    {
        UserLoginInfo loginInfo = LoginState.getInstance(MineInfoActivity.this).getLoginInfo();
        mPhoneNum = loginInfo.getPhoneNum();
        mImageUrl = loginInfo.getImage();
        mUserId = loginInfo.getUserId();
    }

    private void findID()
    {
        mTextView_phone = (TextView) findViewById(R.id.textView_phone);
        mIv_user_avatar = (ImageView) findViewById(R.id.iv_user_avatar);
    }

    private void setListener()
    {
        findViewById(R.id.findBackPassword).setOnClickListener(this);
        findViewById(R.id.button_zuxiao).setOnClickListener(this);
        findViewById(R.id.imageView_back).setOnClickListener(this);
        findViewById(R.id.rl_upload_user_data).setOnClickListener(this);
        findViewById(R.id.rl_upload_user_avatar_img).setOnClickListener(this);
    }

    private void initUI()
    {
        String phoneNum = mPhoneNum.substring(0, mPhoneNum.length() - (mPhoneNum.substring(3)).length()) + "****" + mPhoneNum.substring(7);
        mTextView_phone.setText(phoneNum);

        if (LoginState.getInstance(MineInfoActivity.this).isLogin() && mImageUrl != null && !"".equals(mImageUrl))
        {
            UILUtils.displayImageNoAnim(mImageUrl, mIv_user_avatar, false);
        }

    }


    @Override
    public void onClick(View v)
    {
        Intent intent = new Intent();
        switch (v.getId())
        {
            case R.id.findBackPassword:
                intent.setClass(MineInfoActivity.this, UpdatePasswordActivity.class);
                intent.putExtra("findBackPassword", "findBackPassword");
                startActivity(intent);
                break;
            case R.id.button_zuxiao:
                AlertDialog.Builder builder = new AlertDialog.Builder(MineInfoActivity.this);
                builder.setTitle("提醒");
                builder.setMessage("确定要退出吗？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        LoginState.getInstance(MineInfoActivity.this).logout(MineInfoActivity.this);
                        finish();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
            case R.id.imageView_back:
                finish();
                break;

            case R.id.rl_upload_user_data:
                intent.setClass(MineInfoActivity.this, UploadUserDataActivity.class);
                startActivity(intent);
                break;

            case R.id.rl_upload_user_avatar_img:
                // 弹出选择相机或者是相册选项框
                showPopupwindowForChooseImageStage();
                break;

            case R.id.click_local:
//                if (ContextCompat.checkSelfPermission(MineInfoActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
//                        != PackageManager.PERMISSION_GRANTED)
//                {
//                    //申请WRITE_EXTERNAL_STORAGE权限
//                    ActivityCompat.requestPermissions(MineInfoActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                            0);
//                } else
//                {
                    doHandlerPhoto(PIC_FROM＿LOCALPHOTO);// 从相册中去获取
                    mPopupWindow.dismiss();
//                }
                break;
            case R.id.click_camera:
//                if (ContextCompat.checkSelfPermission(MineInfoActivity.this, Manifest.permission.CAMERA)
//                        != PackageManager.PERMISSION_GRANTED)
//                {
//                    //申请WRITE_EXTERNAL_STORAGE权限
//                    ActivityCompat.requestPermissions(MineInfoActivity.this, new String[]{Manifest.permission.CAMERA}, 0);
//                } else
//                {
                    doHandlerPhoto(PIC_FROM_CAMERA);// 用户点击了从照相机获取
                    mPopupWindow.dismiss();
//                }
                break;
            case R.id.cancle:
                mPopupWindow.dismiss();
                break;

        }
    }

    /**
     * 弹起POP选择相片方式
     */
    private void showPopupwindowForChooseImageStage()
    {
        View inflate = getLayoutInflater().inflate(R.layout.layout_pop_choose_img_stage, null);
        inflate.findViewById(R.id.click_local).setOnClickListener(this);
        inflate.findViewById(R.id.click_camera).setOnClickListener(this);
        inflate.findViewById(R.id.cancle).setOnClickListener(this);
        //最后一个参数为true，点击PopupWindow消失,宽必须为match，不然肯呢个会导致布局显示不完全
        mPopupWindow = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        //设置外部点击无效
        mPopupWindow.setOutsideTouchable(false);
        //设置背景变暗
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;
        getWindow().setAttributes(lp);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener()
        {

            @Override
            public void onDismiss()
            {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        BitmapDrawable bitmapDrawable = new BitmapDrawable();
        mPopupWindow.setBackgroundDrawable(bitmapDrawable);
        mPopupWindow.showAtLocation(inflate, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 根据不同方式选择图片设置ImageView
     *
     * @param type 0-本地相册选择，非0为拍照
     */
    private void doHandlerPhoto(int type)
    {
        try
        {
            //保存裁剪后的图片文件
            File pictureFileDir = new File(Environment.getExternalStorageDirectory(), "/upload");
            if (!pictureFileDir.exists())
            {
                pictureFileDir.mkdirs();
            }
            File picFile = new File(pictureFileDir, mPhoneNum + "upload.jpeg");
            if (!picFile.exists())
            {
                picFile.createNewFile();
            }
            mPhotoUri = Uri.fromFile(picFile);
            if (type == PIC_FROM＿LOCALPHOTO)
            {
                Intent intent = getCropImageIntent();
                startActivityForResult(intent, PIC_FROM＿LOCALPHOTO);
            } else
            {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoUri);
                startActivityForResult(cameraIntent, PIC_FROM_CAMERA);
            }

        } catch (Exception e)
        {
            Log.i("HandlerPicError", "处理图片出现错误");
        }
    }

    /**
     * 调用图片剪辑程序
     */
    public Intent getCropImageIntent()
    {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        setIntentParams(intent);
        return intent;
    }

    /**
     * 设置公用参数
     */
    private void setIntentParams(Intent intent)
    {
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 3);
        intent.putExtra("aspectY", 3);
        intent.putExtra("outputX", 100);
        intent.putExtra("outputY", 100);
        intent.putExtra("noFaceDetection", true); // no face detection
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
    }

    /**
     * 启动裁剪
     */
    private void cropImageUriByTakePhoto()
    {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(mPhotoUri, "image/*");
        setIntentParams(intent);
        startActivityForResult(intent, PIC_FROM＿LOCALPHOTO);
    }

    private Bitmap decodeUriAsBitmap(Uri uri)
    {
        Bitmap bitmap = null;
        try
        {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    /**
     * 上传图片
     *
     * @param uploadFile
     */
    public void postFile(Uri uploadFile)
    {
        mProgressDialog = android.app.ProgressDialog.show(MineInfoActivity.this, null, "拼命上传中…", true, true);
        mProgressDialog.show();
        String path = uploadFile.getPath();
        File file = new File(path);
        if (file.exists() && file.length() > 0)
        {
            RequestParams params = new RequestParams();
            params.put("account_id", mUserId);
            try
            {
                params.put("data", file);
            } catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }

//            ApiClient.uploadUserAvatar(params, new AsyncHttpResponseHandler()
//            {
//                @Override
//                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody)
//                {
//                    mProgressDialog.dismiss();
//                    UpLoadUserAvatarReturnInfo userReturnInfo = GsonUtils.parseJSON(new String(responseBody), UpLoadUserAvatarReturnInfo.class);
//                    LoginState.getInstance(MineInfoActivity.this).setLoginInfoAvatar(userReturnInfo.getImage());
//                }
//
//                @Override
//                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error)
//                {
//
//                }
//            });

        } else
        {
            Toast.makeText(MineInfoActivity.this, "文件不存在", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        Intent intent = new Intent();
        switch (requestCode)
        {
            case PIC_FROM_CAMERA: // 拍照
                try
                {
                    cropImageUriByTakePhoto();
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;
            case PIC_FROM＿LOCALPHOTO:
                try
                {
                    if (mPhotoUri != null)
                    {
                        Bitmap bitmap = decodeUriAsBitmap(mPhotoUri);
                        mIv_user_avatar.setImageBitmap(bitmap);
                        postFile(mPhotoUri);
                    }
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;
        }
    }


    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        if (mProgressDialog != null && mProgressDialog.isShowing())
        {
            Toast.makeText(MineInfoActivity.this, "您已取消头像上传!", Toast.LENGTH_SHORT).show();
        }
    }
}
