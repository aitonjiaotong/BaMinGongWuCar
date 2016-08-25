package passenger.view.activity;

import android.Manifest;
import android.app.Activity;
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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;
import passenger.constant.Constant;
import passenger.model.UserLoginInfo;
import passenger.utils.LoginState;
import shane_library.shane.utils.UILUtils;

import com.aiton.yc.client.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;


public class UploadUserDataActivity extends Activity implements View.OnClickListener
{

    private ImageView mImageView_back;
    private Button mBtn_upload_done;
    private PopupWindow mPopupWindow;
    private int mUpLoadWhatImg;
    private final int PIC_FROM_CAMERA = 1;
    private final int PIC_FROM＿LOCALPHOTO = 0;
    private Uri mPhotoUri;
    private ImageView mIv_sfzzm;
    private ImageView mIv_sfzfm;
    private ImageView mIv_jszzm;
    private ImageView mIv_jszfm;
    private LoginState mLoginState;
    private String mUserId;
    private String mIdCardImage;
    private String mIdCardImage_back;
    private String mDrivingLicenseImage;
    private String mDrivingLicenseImage_back;
    private ImageView mIv_btn_sjzzm_updata;
    private ImageView mIv_btn_sjzfm_updata;
    private ImageView mIv_btn_jszzm_updata;
    private ImageView mIv_btn_jszfm_updata;
    private ProgressDialog mProgressDialog;
    private boolean mIsUploadSfzZm;
    private boolean mIsUploadSfzFm;
    private boolean mIsUploadJszZm;
    private boolean mIsUploadJszFm;
    private boolean isFrist = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_user_data);

        getLoginStage();
        findViewID();
        setListener();
        initUI();
    }

    private void getLoginStage()
    {
        mLoginState = LoginState.getInstance(UploadUserDataActivity.this);
        UserLoginInfo loginInfo = mLoginState.getLoginInfo();
        mUserId = loginInfo.getUserId();
        mIdCardImage = loginInfo.getIdCardImage();
        mIdCardImage_back = loginInfo.getIdCardImage_back();
        mDrivingLicenseImage = loginInfo.getDrivingLicenseImage();
        mDrivingLicenseImage_back = loginInfo.getDrivingLicenseImage_back();
    }

    private void findViewID()
    {
        mImageView_back = (ImageView) findViewById(R.id.imageView_back);
        mIv_sfzzm = (ImageView) findViewById(R.id.iv_sfzzm);
        mIv_sfzfm = (ImageView) findViewById(R.id.iv_sfzfm);
        mIv_jszzm = (ImageView) findViewById(R.id.iv_jszzm);
        mIv_jszfm = (ImageView) findViewById(R.id.iv_jszfm);
        mBtn_upload_done = (Button) findViewById(R.id.btn_upload_done);
        mIv_btn_sjzzm_updata = (ImageView) findViewById(R.id.iv_btn_sjzzm_updata);
        mIv_btn_sjzfm_updata = (ImageView) findViewById(R.id.iv_btn_sjzfm_updata);
        mIv_btn_jszzm_updata = (ImageView) findViewById(R.id.iv_btn_jszzm_updata);
        mIv_btn_jszfm_updata = (ImageView) findViewById(R.id.iv_btn_jszfm_updata);
    }

    private void setListener()
    {
        mImageView_back.setOnClickListener(this);
        mIv_sfzzm.setOnClickListener(this);
        mIv_sfzfm.setOnClickListener(this);
        mIv_jszzm.setOnClickListener(this);
        mIv_jszfm.setOnClickListener(this);
        mBtn_upload_done.setOnClickListener(this);
    }

    private void initUI()
    {
        //判断是否有上传过图片，对图片进行相关默认加载
        if (mIdCardImage != null && !"".equals(mIdCardImage))
        {
            UILUtils.displayImageNoAnim(mIdCardImage, mIv_sfzzm, false);
            mIv_btn_sjzzm_updata.setImageResource(R.drawable.xiugai_2x);
            mIsUploadSfzZm = false;
        }
        if (mIdCardImage_back != null && !"".equals(mIdCardImage_back))
        {
            UILUtils.displayImageNoAnim(mIdCardImage_back, mIv_sfzfm, false);
            mIv_btn_sjzfm_updata.setImageResource(R.drawable.xiugai_2x);
            mIsUploadSfzFm = true;
        }
        if (mDrivingLicenseImage != null && !"".equals(mDrivingLicenseImage))
        {
            UILUtils.displayImageNoAnim(mDrivingLicenseImage, mIv_jszzm, false);
            mIv_btn_jszzm_updata.setImageResource(R.drawable.xiugai_2x);
            mIsUploadJszZm = true;
        }
        if (mDrivingLicenseImage_back != null && !"".equals(mDrivingLicenseImage_back))
        {
            UILUtils.displayImageNoAnim(mDrivingLicenseImage_back, mIv_jszfm, false);
            mIv_btn_jszfm_updata.setImageResource(R.drawable.xiugai_2x);
            mIsUploadJszFm = true;
        }
        if (isFrist)
        {
            mBtn_upload_done.setText("返回");
        }

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.imageView_back:
                finish();
                break;
            case R.id.btn_upload_done:
                if (mIsUploadSfzZm && mIsUploadSfzFm && mIsUploadJszZm && mIsUploadJszFm)
                {
                    finish();
                } else
                {
                    if (isFrist)
                    {
                        finish();
                    } else
                    {
                        new AlertDialog.Builder(UploadUserDataActivity.this).setMessage("您还有相关资料未完善，确认先保存当前编辑并退出吗？")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        finish();
                                    }
                                })
                                .setNegativeButton("取消", null).create().show();
                    }
                }
                break;
            case R.id.iv_sfzzm:
                //上传身份证正面
                isFrist = false;
                mBtn_upload_done.setText("确认");
                showPopupwindowForChooseImageStage();
                mUpLoadWhatImg = 0;
                break;
            case R.id.iv_sfzfm:
                //上传身份证反面
                isFrist = false;
                mBtn_upload_done.setText("确认");
                showPopupwindowForChooseImageStage();
                mUpLoadWhatImg = 1;
                break;
            case R.id.iv_jszzm:
                //上传驾驶证正面
                isFrist = false;
                mBtn_upload_done.setText("确认");
                showPopupwindowForChooseImageStage();
                mUpLoadWhatImg = 2;
                break;
            case R.id.iv_jszfm:
                //上传驾驶证反面
                isFrist = false;
                mBtn_upload_done.setText("确认");
                mUpLoadWhatImg = 3;
                showPopupwindowForChooseImageStage();
                break;

            case R.id.click_local:
//                if (ContextCompat.checkSelfPermission(UploadUserDataActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
//                {
//                    //申请WRITE_EXTERNAL_STORAGE权限
//                    ActivityCompat.requestPermissions(UploadUserDataActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
//                } else
//                {
                    switch (mUpLoadWhatImg)
                    {
                        case 0:
                            doHandlerPhoto(PIC_FROM＿LOCALPHOTO, "SJZZM");// 从相册中去获取
                            break;
                        case 1:
                            doHandlerPhoto(PIC_FROM＿LOCALPHOTO, "SJZFM");// 从相册中去获取
                            break;
                        case 2:
                            doHandlerPhoto(PIC_FROM＿LOCALPHOTO, "JSZZM");// 从相册中去获取
                            break;
                        case 3:
                            doHandlerPhoto(PIC_FROM＿LOCALPHOTO, "JSZFM");// 从相册中去获取
                            break;
                    }
                    mPopupWindow.dismiss();
//                }
                break;
            case R.id.click_camera:
//                if (ContextCompat.checkSelfPermission(UploadUserDataActivity.this, Manifest.permission.CAMERA)
//                        != PackageManager.PERMISSION_GRANTED)
//                {
//                    //申请WRITE_EXTERNAL_STORAGE权限
//                    ActivityCompat.requestPermissions(UploadUserDataActivity.this, new String[]{Manifest.permission.CAMERA}, 0);
//                } else
//                {
                    switch (mUpLoadWhatImg)
                    {
                        case 0:
                            doHandlerPhoto(PIC_FROM_CAMERA, "SJZZM");// 用户点击了从照相机获取
                            break;
                        case 1:
                            doHandlerPhoto(PIC_FROM_CAMERA, "SJZFM");// 用户点击了从照相机获取
                            break;
                        case 2:
                            doHandlerPhoto(PIC_FROM_CAMERA, "JSZZM");// 用户点击了从照相机获取
                            break;
                        case 3:
                            doHandlerPhoto(PIC_FROM_CAMERA, "JSZFM");// 用户点击了从照相机获取
                            break;
                    }
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
    private void doHandlerPhoto(int type, String jpgName)
    {
        try
        {
            //保存裁剪后的图片文件
            File pictureFileDir = new File(Environment.getExternalStorageDirectory(), "/upload");
            if (!pictureFileDir.exists())
            {
                pictureFileDir.mkdirs();
            }
            File picFile = new File(pictureFileDir, jpgName + "upload.jpeg");
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
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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
        intent.putExtra("aspectY", 2);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 200);
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
    public void postFile(Uri uploadFile, String forUpLoadUrl)
    {
        //上传图片过程中的加载提示框
        mProgressDialog = android.app.ProgressDialog.show(UploadUserDataActivity.this, null, "拼命上传中…", true, true);
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
//            AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
//            asyncHttpClient.post(forUpLoadUrl, params, new AsyncHttpResponseHandler()
//            {
//                @Override
//                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody)
//                {
//                    mProgressDialog.dismiss();
//                    //将上传过的图片地址缓存到本地SP中保存
//                    switch (mUpLoadWhatImg)
//                    {
//                        case 0:
//                            LoginState.getInstance(UploadUserDataActivity.this).setLoginInfoSfzZm(new String(responseBody));
//                            mIv_btn_sjzzm_updata.setImageResource(R.drawable.xiugai_2x);
//                            mIsUploadSfzZm = true;
//                            break;
//                        case 1:
//                            LoginState.getInstance(UploadUserDataActivity.this).setLoginInfoSfzFm(new String(responseBody));
//                            mIv_btn_sjzfm_updata.setImageResource(R.drawable.xiugai_2x);
//                            mIsUploadSfzFm = true;
//                            break;
//                        case 2:
//                            LoginState.getInstance(UploadUserDataActivity.this).setLoginInfoJszZm(new String(responseBody));
//                            mIv_btn_jszzm_updata.setImageResource(R.drawable.xiugai_2x);
//                            mIsUploadJszZm = true;
//                            break;
//                        case 3:
//                            LoginState.getInstance(UploadUserDataActivity.this).setLoginInfoJszFm(new String(responseBody));
//                            mIv_btn_jszfm_updata.setImageResource(R.drawable.xiugai_2x);
//                            mIsUploadJszFm = true;
//                            break;
//                    }
//                }

//                @Override
//                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error)
//                {
//
//                }
//            });
        } else
        {
            Toast.makeText(UploadUserDataActivity.this, "未选择文件或文件不存在", Toast.LENGTH_LONG).show();
            switch (mUpLoadWhatImg)
            {
                case 0:
                    mIv_sfzzm.setImageResource(R.drawable.shenfzzhengmian_2x);
                    break;
                case 1:
                    mIv_sfzfm.setImageResource(R.drawable.shenfzfanmian_2x);
                    break;
                case 2:
                    mIv_jszzm.setImageResource(R.drawable.jiashizzhengmian_2x);
                    break;
                case 3:
                    mIv_jszfm.setImageResource(R.drawable.jiashizfanmian_2x);
                    break;
            }
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
                        switch (mUpLoadWhatImg)
                        {
                            case 0:
                                mIv_sfzzm.setImageBitmap(bitmap);
                                postFile(mPhotoUri, Constant.URL.UPLOAD_SFZZM);
                                break;
                            case 1:
                                mIv_sfzfm.setImageBitmap(bitmap);
                                postFile(mPhotoUri, Constant.URL.UPLOAD_SFZFM);
                                break;
                            case 2:
                                mIv_jszzm.setImageBitmap(bitmap);
                                postFile(mPhotoUri, Constant.URL.UPLOAD_JSZZM);
                                break;
                            case 3:
                                mIv_jszfm.setImageBitmap(bitmap);
                                postFile(mPhotoUri, Constant.URL.UPLOAD_JSZFM);
                                break;
                        }

                    }
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;
        }
    }


}
