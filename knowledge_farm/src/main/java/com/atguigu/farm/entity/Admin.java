package com.atguigu.farm.entity;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;

/**
 * @ClassName Admin
 * @Description
 * @Author 张帅华
 * @Date 2020-04-06 14:03
 */
@Entity
@Table(name = "admin")
public class Admin {
    private Integer id;
    private String account;
    private String password;
    private Integer exist;

    @Id
    @GeneratedValue(generator="identity")
    @GenericGenerator(name="identity", strategy = "identity")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(insertable = false)
    public Integer getExist() {
        return exist;
    }

    public void setExist(Integer exist) {
        this.exist = exist;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", exist=" + exist +
                '}';
    }

}
