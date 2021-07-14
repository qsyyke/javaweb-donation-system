package com.yq.domain;

public class User {
/**
 *@decript:  此类用于用户信息
 */
    //用户名
    private String username;
    //邮箱
    private String email;
    //用户地址
    private String address;

    private String password;

     /** 用户城市 */
    private String city;

     /** 用户地区 */
    private String area;
    //用户省份
    private String province;

    private int isAdmin;


    //id
    private int id;

    public User (String uname, String email, String province, int id) {
        this.username = uname;
        this.email = email;
        this.province = province;
        this.id = id;
    }

    public User (String username, String email, String address, String password, int isAdmin) {
        this.username = username;
        this.email = email;
        this.address = address;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public User (String username, String email, String address, String city, String area, String province, int isAdmin) {
        this.username = username;
        this.email = email;
        this.address = address;
        this.city = city;
        this.area = area;
        this.province = province;
        this.isAdmin = isAdmin;
    }

    public User (String uname, String email) {
        this.username = uname;
        this.email = email;
    }

    public int getIsAdmin () {
        return isAdmin;
    }

    public void setIsAdmin (int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getPassword () {
        return password;
    }

    public void setPassword (String password) {
        this.password = password;
    }

    public String getAddress () {
        return address;
    }

    public void setAddress (String address) {
        this.address = address;
    }

    public String getCity () {
        return city;
    }

    public void setCity (String city) {
        this.city = city;
    }

    public String getArea () {
        return area;
    }

    public void setArea (String area) {
        this.area = area;
    }

    public String getUsername () {
        return username;
    }

    public void setUsername (String username) {
        this.username = username;
    }


    public String getEmail () {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public String getProvince () {
        return province;
    }

    public void setProvince (String province) {
        this.province = province;
    }

    public int getId () {
        return id;
    }

    public void setId (int id) {
        this.id = id;
    }

    public User () {
    }

    @Override
    public String toString () {
        return "User{" + "username='" + username + '\'' + ", email='" + email + '\'' + ", address='" + address + '\'' + ", password='" + password + '\'' + ", city='" + city + '\'' + ", area='" + area + '\'' + ", province='" + province + '\'' + ", isAdmin=" + isAdmin + ", id=" + id + '}';
    }
}
