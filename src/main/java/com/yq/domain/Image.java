package com.yq.domain;


/**
 * 管理数据库中发布的图片
 * @author 青衫烟雨客 程钦义
 * @date 2021/05/01 20:08
 **/

public class Image {
     /** 图片名称 */
    private String img_name;

     /** 图片地址 */
    private String img_src;

     /** 图片发布时间 */
    private String img_date;

     /** 唯一标识 */
    private String identify;

    public String getIdentify () {
        return identify;
    }

    public void setIdentify (String identify) {
        this.identify = identify;
    }

    public Image () {
    }

    public Image (String img_name, String img_src, String img_date) {
        this.img_name = img_name;
        this.img_src = img_src;
        this.img_date = img_date;
    }

    public Image (String img_name, String img_src, String img_date, String identify) {
        this.img_name = img_name;
        this.img_src = img_src;
        this.img_date = img_date;
        this.identify = identify;
    }

    public String getImg_name () {
        return img_name;
    }

    public void setImg_name (String img_name) {
        this.img_name = img_name;
    }

    public String getImg_src () {
        return img_src;
    }

    public void setImg_src (String img_src) {
        this.img_src = img_src;
    }

    public String getImg_date () {
        return img_date;
    }

    public void setImg_date (String img_date) {
        this.img_date = img_date;
    }

    @Override
    public String toString () {
        return "Image{" + "img_name='" + img_name + '\'' + ", img_src='" + img_src + '\'' + ", img_date='" + img_date + '\'' + '}';
    }
}
