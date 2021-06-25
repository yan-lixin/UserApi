package com.example.demo.domain;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.Optional;

/**
 * @author: lixin
 * Date: 2021/6/9
 * Description:
 */
@Entity
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@Table(name = "t_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    public static User create(String mobile, String fullname) {
        return new User(mobile, fullname);
    }

    public User() {
    }

    public User(String mobile, String fullname) {
        this.mobile = mobile;
        this.fullname = fullname;
    }

    public Integer getId() {
        return id;
    }

    public String getMobile() {
        return mobile;
    }

    public String getFullname() {
        return fullname;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void update(String mobile, String fullname) {
        Optional.ofNullable(mobile).ifPresent(e -> this.mobile = e);
        Optional.ofNullable(fullname).ifPresent(e -> this.fullname = e);
    }
}
