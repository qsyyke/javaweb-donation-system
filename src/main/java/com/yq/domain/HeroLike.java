package com.yq.domain;


/**
 * 英雄人物点赞
 *
 * @author 青衫烟雨客 程钦义
 * @date 2021/05/06 17:30
 **/

public class HeroLike {

     /** 英雄人物唯一标识 */
    private String identify;

     /** 用户UID */
    private int uid;

     /** 是否点赞 1表示点赞 0表示没有 */
    private String isLike;


    public HeroLike () {
    }

    public HeroLike (String identify, int uid, String isLike) {
        this.identify = identify;
        this.uid = uid;
        this.isLike = isLike;
    }

    public String getIdentify () {
        return identify;
    }

    public void setIdentify (String identify) {
        this.identify = identify;
    }

    public int getUid () {
        return uid;
    }

    public void setUid (int uid) {
        this.uid = uid;
    }

    public String getIsLike () {
        return isLike;
    }

    public void setIsLike (String isLike) {
        this.isLike = isLike;
    }
}
