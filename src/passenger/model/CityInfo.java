package passenger.model;

import java.util.List;

/**
 * Created by Administrator on 2016/3/21.
 */
public class CityInfo
{

    /**
     * contains : ["三明"]
     * num : 1
     */

    private int num;
    private List<String> contains;

    public int getNum()
    {
        return num;
    }

    public void setNum(int num)
    {
        this.num = num;
    }

    public List<String> getContains()
    {
        return contains;
    }

    public void setContains(List<String> contains)
    {
        this.contains = contains;
    }
}
