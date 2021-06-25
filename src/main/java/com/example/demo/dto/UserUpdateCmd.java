package com.example.demo.dto;

/**
 * @author: lixin
 * Date: 2021/6/9
 * Description:
 */
public class UserUpdateCmd {

    private Integer id;

    private String mobile;

    private String fullname;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
