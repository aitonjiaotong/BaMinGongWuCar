package passenger.model;


import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/25.
 */
public class UserChooseScreeningInfo implements Serializable
{
    String organizationCode;
    String startMillise;
    String endMillise;
    String flag;

    public String getFlag()
    {
        return flag;
    }

    public void setFlag(String flag)
    {
        this.flag = flag;
    }

    public String getOrganizationCode()
    {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode)
    {
        this.organizationCode = organizationCode;
    }

    public String getStartMillise()
    {
        return startMillise;
    }

    public void setStartMillise(String startMillise)
    {
        this.startMillise = startMillise;
    }

    public String getEndMillise()
    {
        return endMillise;
    }

    public void setEndMillise(String endMillise)
    {
        this.endMillise = endMillise;
    }
}
