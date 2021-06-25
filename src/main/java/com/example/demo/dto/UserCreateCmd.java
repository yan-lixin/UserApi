package com.example.demo.dto;

/**
 * @author: lixin
 * Date: 2021/6/9
 * Description:
 */
public class UserCreateCmd {

    private String mobile;

    private String fullname;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
