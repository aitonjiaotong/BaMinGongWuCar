package shane_library.shane.utils;


import android.content.Context;
import shane_library.github.volley.UTFStringRequest;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;

public class HTTPUtils
{
    private static RequestQueue mRequestQueue;

    private HTTPUtils()
    {
    }

    private static void init(Context context)
    {
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public static void post(Context context, String url, final Map<String, String> params, final VolleyListener listener)
    {
    	 // 判断当前如果没有网络，则返回null
        if (!ConnectionUtils.isConnected(context))
        {
            VolleyError error = new VolleyError("noConnection");
            listener.onErrorResponse(error);
            return;
        }
        StringRequest myReq = new UTFStringRequest(
                Method.POST,
                url,
                new Listener<String>()
                {
                    public void onResponse(String response)
                    {
                        listener.onResponse(response);
                    }
                },
                new Response.ErrorListener()
                {
                    public void onErrorResponse(VolleyError error)
                    {
                        listener.onErrorResponse(error);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                return params;
            }
        };
        if (mRequestQueue == null)
        {
            init(context);
        }
        // 请用缓存
        myReq.setShouldCache(true);
        // 设置缓存时间10分钟
//		 myReq.setCacheTime(10*60);
        //设置超时时间、请求次数
        myReq.setRetryPolicy(new DefaultRetryPolicy(5000, 1, 1.0f));
        mRequestQueue.add(myReq);
    }

    public static void get(Context context, String url,final VolleyListener listener)
    {
        // 判断当前如果没有网络，则返回null
        if (!ConnectionUtils.isConnected(context))
        {
            VolleyError error = new VolleyError("noConnection");
            listener.onErrorResponse(error);
            return;
        }
        StringRequest myReq = new UTFStringRequest(
                Method.GET,
                url,
                new Listener<String>()
                {
                    public void onResponse(String response)
                    {
                        listener.onResponse(response);
                    }
                },
                new Response.ErrorListener()
                {
                    public void onErrorResponse(VolleyError error)
                    {
                        listener.onErrorResponse(error);
                    }
                });
        if (mRequestQueue == null)
        {
            init(context);
        }
        // 请用缓存
        myReq.setShouldCache(true);
        // 设置缓存时间10分钟
//		 myReq.setCacheTime(10*60);
        myReq.setRetryPolicy(new DefaultRetryPolicy(5000, 1, 1.0f));
        mRequestQueue.add(myReq);
    }

    private static RequestQueue getRequestQueue(Context context)
    {
        if (mRequestQueue != null)
        {
            return mRequestQueue;
        } else
        {
            throw new IllegalStateException("RequestQueue not initialized");
        }
    }

    public static void cancelAll(Context context)
    {
        if (mRequestQueue != null)
        {
            mRequestQueue.cancelAll(context);
        }
    }


}
