package passenger.utils;

import android.content.Context;
import android.content.SharedPreferences;
import passenger.model.UserLoginInfo;


/**
 * Created by Administrator on 2016/5/20.
 */
public class LoginState
{
    private static LoginState userLoginState = new LoginState();
    private static SharedPreferences sp;
    private static UserLoginInfo userLoginInfo;

    private LoginState()
    {

    }

    public static LoginState getInstance(Context context)
    {
        sp = context.getSharedPreferences("isLogin", Context.MODE_PRIVATE);
        return userLoginState;
    }

    /**
     * 登录
     */
    public void login(Context context)
    {
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean("isLogin", true);
        edit.commit();
    }

    /**
     * 设置用户登录的相关信息
     */
    public void setLoginInfo(UserLoginInfo userLoginInfo)
    {
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("phoneNum", userLoginInfo.getPhoneNum());
        edit.putString("id", userLoginInfo.getUserId());
        edit.putString("DeviceId", userLoginInfo.getDeviceId());
        edit.putString("imageUrl", userLoginInfo.getImage());
        edit.putString("idCardImage", userLoginInfo.getIdCardImage());
        edit.putString("idCardImage_back", userLoginInfo.getIdCardImage_back());
        edit.putString("drivingLicenseImage", userLoginInfo.getDrivingLicenseImage());
        edit.putString("drivingLicenseImage_back", userLoginInfo.getDrivingLicenseImage_back());
        edit.commit();
    }

    /**
     * 设置用户头像地址
     */
    public void setLoginInfoAvatar(String avatarImageUrl)
    {
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("imageUrl", avatarImageUrl);
        edit.commit();
    }

    /**
     * 设置用户身份证正面地址
     */
    public void setLoginInfoSfzZm(String avatarImageUrl)
    {
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("idCardImage", avatarImageUrl);
        edit.commit();
    }

    /**
     * 设置用户身份证反面地址
     */
    public void setLoginInfoSfzFm(String avatarImageUrl)
    {
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("idCardImage_back", avatarImageUrl);
        edit.commit();
    }

    /**
     * 设置用户驾驶证正面地址
     */
    public void setLoginInfoJszZm(String avatarImageUrl)
    {
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("drivingLicenseImage", avatarImageUrl);
        edit.commit();
    }

    /**
     * 设置用户驾驶证反面地址
     */
    public void setLoginInfoJszFm(String avatarImageUrl)
    {
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("drivingLicenseImage_back", avatarImageUrl);
        edit.commit();
    }

    /**
     * 获取用户登录时保存的信息
     */
    public UserLoginInfo getLoginInfo()
    {
        userLoginInfo = new UserLoginInfo();
        userLoginInfo.setPhoneNum(sp.getString("phoneNum", null));
        userLoginInfo.setUserId(sp.getString("id", null));
        userLoginInfo.setDeviceId(sp.getString("DeviceId", null));
        userLoginInfo.setImage(sp.getString("imageUrl", null));
        userLoginInfo.setIdCardImage(sp.getString("idCardImage", null));
        userLoginInfo.setIdCardImage_back(sp.getString("idCardImage_back", null));
        userLoginInfo.setDrivingLicenseImage(sp.getString("drivingLicenseImage", null));
        userLoginInfo.setDrivingLicenseImage_back(sp.getString("drivingLicenseImage_back", null));
        return userLoginInfo;
    }

    /**
     * 登出
     */
    public void logout(Context context)
    {
        sp = context.getSharedPreferences("isLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.clear();
        edit.putBoolean("isLogin", false);
        edit.commit();
    }

    /**
     * 获取用户登录的状态
     */
    public boolean isLogin()
    {
        return sp.getBoolean("isLogin", false);
    }


}
