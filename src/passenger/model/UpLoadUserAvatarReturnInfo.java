package passenger.model;


/**
 * Created by Administrator on 2016/5/31.
 */
public class UpLoadUserAvatarReturnInfo
{

    /**
     * id : 5
     * name : 13799283714
     * password : PuQFBPpe6wIFm2b87b/yKw==
     * sex : null
     * login_id : 0121a9e2-9fb1-49e3-98d0-4127713baacc
     * idCard : null
     * realName : null
     * cardStatus : null
     * bankCardStatus : null
     * bankCard : null
     * phone : 13799283714
     * address : null
     * image : http://bmcx.oss-cn-shanghai.aliyuncs.com/account/13799283714.jpeg
     * note : null
     * login_id_gongjiao : null
     * login_id_kepiao : d85789b2-1be8-493e-95a2-c38fee850d9f
     * login_id_dache : cf70ea55-9e3a-4980-a5ae-ffe7efd442c1
     * login_id_chihe : null
     * login_id_paotui : null
     * login_id_qiche : null
     * login_id_lvxing : null
     * login_id_kuaidi : null
     * login_id_baoxian : null
     * login_id_shangcheng : null
     * login_id_chongzhi : null
     * login_id_dcdriver : null
     * login_id_zhuandriver : null
     * login_id_paotuiren : null
     */

    private Integer id;
    private String name;
    private String password;
    private Integer sex;//0：女 1：男
    private String login_id;
    private String idCard; //身份证号
    private String realName;
    private Integer cardStatus;//证件类型:0:居民身份证,1:士兵证,2:军官证,3:警官证,4:护照,5:其它
    private String bankCardStatus;
    private String bankCard;   //银行卡号
    private String phone;
    private String address;
    private String image;
    private String note; //备注
    private  String login_id_gongjiao;//flag=0
    private String login_id_kepiao;//flag=1
    private String login_id_dache;//flag=2
    private String login_id_chihe;//flag=3
    private String login_id_paotui;//flag=4
    private String login_id_qiche;//flag=5
    private String login_id_lvxing;//flag=6
    private String login_id_kuaidi;//flag=7
    private String login_id_baoxian;//flag=8
    private String login_id_shangcheng;//flag=9
    private String login_id_chongzhi;//flag=10
    private String login_id_dcdriver;//flag=11
    private String login_id_zhuandriver;//flag=12
    private String login_id_paotuiren;//flag=13

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Integer getSex()
    {
        return sex;
    }

    public void setSex(Integer sex)
    {
        this.sex = sex;
    }

    public String getLogin_id()
    {
        return login_id;
    }

    public void setLogin_id(String login_id)
    {
        this.login_id = login_id;
    }

    public String getIdCard()
    {
        return idCard;
    }

    public void setIdCard(String idCard)
    {
        this.idCard = idCard;
    }

    public String getRealName()
    {
        return realName;
    }

    public void setRealName(String realName)
    {
        this.realName = realName;
    }

    public Integer getCardStatus()
    {
        return cardStatus;
    }

    public void setCardStatus(Integer cardStatus)
    {
        this.cardStatus = cardStatus;
    }

    public String getBankCardStatus()
    {
        return bankCardStatus;
    }

    public void setBankCardStatus(String bankCardStatus)
    {
        this.bankCardStatus = bankCardStatus;
    }

    public String getBankCard()
    {
        return bankCard;
    }

    public void setBankCard(String bankCard)
    {
        this.bankCard = bankCard;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getImage()
    {
        return image;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

    public String getNote()
    {
        return note;
    }

    public void setNote(String note)
    {
        this.note = note;
    }

    public String getLogin_id_gongjiao()
    {
        return login_id_gongjiao;
    }

    public void setLogin_id_gongjiao(String login_id_gongjiao)
    {
        this.login_id_gongjiao = login_id_gongjiao;
    }

    public String getLogin_id_kepiao()
    {
        return login_id_kepiao;
    }

    public void setLogin_id_kepiao(String login_id_kepiao)
    {
        this.login_id_kepiao = login_id_kepiao;
    }

    public String getLogin_id_dache()
    {
        return login_id_dache;
    }

    public void setLogin_id_dache(String login_id_dache)
    {
        this.login_id_dache = login_id_dache;
    }

    public String getLogin_id_chihe()
    {
        return login_id_chihe;
    }

    public void setLogin_id_chihe(String login_id_chihe)
    {
        this.login_id_chihe = login_id_chihe;
    }

    public String getLogin_id_paotui()
    {
        return login_id_paotui;
    }

    public void setLogin_id_paotui(String login_id_paotui)
    {
        this.login_id_paotui = login_id_paotui;
    }

    public String getLogin_id_qiche()
    {
        return login_id_qiche;
    }

    public void setLogin_id_qiche(String login_id_qiche)
    {
        this.login_id_qiche = login_id_qiche;
    }

    public String getLogin_id_lvxing()
    {
        return login_id_lvxing;
    }

    public void setLogin_id_lvxing(String login_id_lvxing)
    {
        this.login_id_lvxing = login_id_lvxing;
    }

    public String getLogin_id_kuaidi()
    {
        return login_id_kuaidi;
    }

    public void setLogin_id_kuaidi(String login_id_kuaidi)
    {
        this.login_id_kuaidi = login_id_kuaidi;
    }

    public String getLogin_id_baoxian()
    {
        return login_id_baoxian;
    }

    public void setLogin_id_baoxian(String login_id_baoxian)
    {
        this.login_id_baoxian = login_id_baoxian;
    }

    public String getLogin_id_shangcheng()
    {
        return login_id_shangcheng;
    }

    public void setLogin_id_shangcheng(String login_id_shangcheng)
    {
        this.login_id_shangcheng = login_id_shangcheng;
    }

    public String getLogin_id_chongzhi()
    {
        return login_id_chongzhi;
    }

    public void setLogin_id_chongzhi(String login_id_chongzhi)
    {
        this.login_id_chongzhi = login_id_chongzhi;
    }

    public String getLogin_id_dcdriver()
    {
        return login_id_dcdriver;
    }

    public void setLogin_id_dcdriver(String login_id_dcdriver)
    {
        this.login_id_dcdriver = login_id_dcdriver;
    }

    public String getLogin_id_zhuandriver()
    {
        return login_id_zhuandriver;
    }

    public void setLogin_id_zhuandriver(String login_id_zhuandriver)
    {
        this.login_id_zhuandriver = login_id_zhuandriver;
    }

    public String getLogin_id_paotuiren()
    {
        return login_id_paotuiren;
    }

    public void setLogin_id_paotuiren(String login_id_paotuiren)
    {
        this.login_id_paotuiren = login_id_paotuiren;
    }
}
