package passenger.model;

/**
 * Created by Administrator on 2016/2/29.
 */
public class BannerInfo
{


    /**
     * id : 1
     * name : banner01
     * url : http://120.24.46.15:8080/bmpw/img/banner01.png
     * url2 : http://120.24.46.15:8080/bmpw/front/getRedEnvelope?activity=4
     * flag : null
     */

    private int id;
    private String name;
    private String url;

    public void setId(int id)
    {
        this.id = id;
    }

    public int getId()
    {
        return id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getUrl()
    {
        return url;
    }

}
