package passenger.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2016/5/13.
 */
public class GetRawResource
{
    /**
     * 解析本地Json数据文件的String数据
     */
    public static String JsonTransformaString(InputStream inputStream)
    {
        InputStreamReader inputStreamReader = null;
        try
        {
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");
        } catch (UnsupportedEncodingException e1)
        {
            e1.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuffer sb = new StringBuffer("");
        String line;
        try
        {
            while ((line = reader.readLine()) != null)
            {
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
