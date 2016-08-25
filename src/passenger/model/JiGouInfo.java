package passenger.model;

import java.io.Serializable;

public class JiGouInfo implements Serializable{
	/**
     * id : 1
     * name : 艾通信息
     * head : 鲁仁华
     * phone : 15959184067
     * code : 8000
     * loginName : 艾通
     */

    private InstitutionsEntity institutions;
    /**
     * institutions : {"id":1,"name":"艾通信息","head":"鲁仁华","phone":"15959184067","code":"8000","loginName":"艾通"}
     * success : true
     */

    private boolean success;

    public InstitutionsEntity getInstitutions() {
        return institutions;
    }

    public void setInstitutions(InstitutionsEntity institutions) {
        this.institutions = institutions;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class InstitutionsEntity {
        private int id;
        private String name;
        private String head;
        private String phone;
        private String code;
        private String loginName;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }
    }
}
