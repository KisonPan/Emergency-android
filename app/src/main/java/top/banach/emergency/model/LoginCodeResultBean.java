package top.banach.emergency.model;

public class LoginCodeResultBean {

    private String code;
    private String msg;
    private String imgbase64;
    public void setCode(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getMsg() {
        return msg;
    }

    public void setImgbase64(String imgbase64) {
        this.imgbase64 = imgbase64;
    }
    public String getImgbase64() {
        return imgbase64;
    }

}
