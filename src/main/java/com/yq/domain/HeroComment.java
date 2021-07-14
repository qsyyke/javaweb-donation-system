package com.yq.domain;


/**
 * @author 青衫烟雨客 程钦义
 * @date 2021/05/03 15:37
 **/

public class HeroComment {
     /** 英雄人物唯一标识 */
    private String identify;

     /** 评论者名字 */
    private String cName;

     /** 评论日期 */
    private String cDate;

     /** 评论内容 */
    private String cContent;

     /** 用户唯一标识 */
     private int uid;

    /** 是否点赞 */
    private int isLike;

    public HeroComment () {
    }

    public HeroComment (String identify, String cName, String cDate, String cContent) {
        this.identify = identify;
        this.cName = cName;
        this.cDate = cDate;
        this.cContent = cContent;
    }

    public HeroComment (String identify, String cName, String cDate, String cContent, int uid) {
        this.identify = identify;
        this.cName = cName;
        this.cDate = cDate;
        this.cContent = cContent;
        this.uid = uid;
    }

    public int getIsLike () {
        return isLike;
    }

    public void setIsLike (int isLike) {
        this.isLike = isLike;
    }

    public String getIdentify () {
        return identify;
    }

    public int getUid () {
        return uid;
    }

    public void setUid (int uid) {
        this.uid = uid;
    }

    public void setIdentify (String identify) {
        this.identify = identify;
    }

    public String getcName () {
        return cName;
    }

    public void setcName (String cName) {
        this.cName = cName;
    }

    public String getcDate () {
        return cDate;
    }

    public void setcDate (String cDate) {
        this.cDate = cDate;
    }

    public String getcContent () {
        return cContent;
    }

    public void setcContent (String cContent) {
        this.cContent = cContent;
    }
}
