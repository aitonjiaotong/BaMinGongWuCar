package passenger.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/4/13.
 */

public class IsGoingNowState
{
    private static IsGoingNowState instance = new IsGoingNowState();
    private static SharedPreferences sp;

    private IsGoingNowState()
    {

    }

    public static IsGoingNowState getInstance(Context context)
    {
        sp = context.getSharedPreferences("isDaCheGoingNow", Context.MODE_PRIVATE);
        return instance;
    }

    public void setSaveIsGoingState(boolean b)
    {
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean("isDaCheGoingNow", b);
        edit.commit();
    }

    public boolean getSaveIsGoingState()
    {
        return sp.getBoolean("isDaCheGoingNow", false);
    }


}
