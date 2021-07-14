package com.yq.domain;


import java.util.List;

/**
 * 英雄类
 * @author 青衫烟雨客 程钦义
 * @date 2021/04/29 13:17
 **/

public class Hero {

    private String name;
    private String birthday;
     /** 是否展示此抗疫英雄的信息 */
    private boolean isShow;

     /** 人物图片连接地址 */
    private String photoSrc;

     /** 展示页面点赞数 */
    private int like;

     /** 此英雄对应的类型，医生，警察 */
    private int  professionType;

     /** 人物描述简介 */
    private String describe;

     /** 人物荣誉 */
    private List<String> honor;

     /** 唯一标识，用来标识每一个人物 */
    private String uniqueIdentify;

    public Hero () {
    }

    public Hero (String name, String photoSrc) {
        this.name = name;
        this.photoSrc = photoSrc;
    }

    public Hero (String name, String birthday, boolean isShow, String photoSrc, int like, int professionType, String describe, List<String> honor) {
        this.name = name;
        this.birthday = birthday;
        this.isShow = isShow;
        this.photoSrc = photoSrc;
        this.like = like;
        this.professionType = professionType;
        this.describe = describe;
        this.honor = honor;
    }

    public String getUniqueIdentify () {
        return uniqueIdentify;
    }

    public void setUniqueIdentify (String uniqueIdentify) {
        this.uniqueIdentify = uniqueIdentify;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getBirthday () {
        return birthday;
    }

    public void setBirthday (String birthday) {
        this.birthday = birthday;
    }

    public boolean isShow () {
        return isShow;
    }

    public void setShow (boolean show) {
        isShow = show;
    }

    public String getPhotoSrc () {
        return photoSrc;
    }

    public void setPhotoSrc (String photoSrc) {
        this.photoSrc = photoSrc;
    }

    public int getLike () {
        return like;
    }

    public void setLike (int like) {
        this.like = like;
    }

    public int getProfessionType () {
        return professionType;
    }

    public void setProfessionType (int professionType) {
        this.professionType = professionType;
    }

    public String getDescribe () {
        return describe;
    }

    public void setDescribe (String describe) {
        this.describe = describe;
    }

    public List<String> getHonor () {
        return honor;
    }

    public void setHonor (List<String> honor) {
        this.honor = honor;
    }

    @Override
    public String toString () {
        return "Hero{" + "name='" + name + '\'' + ", birthday='" + birthday + '\'' + ", isShow=" + isShow + ", photoSrc='" + photoSrc + '\'' + ", like=" + like + ", professionType=" + professionType + ", describe='" + describe + '\'' + ", honor=" + honor + '}';
    }
}
