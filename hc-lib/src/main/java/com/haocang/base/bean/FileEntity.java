package com.haocang.base.bean;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区云锦路 500 号绿地汇中心 A 座 20 楼 2001
 * 网址：http://www.haocang.com/
 * 标 题：文件上传后返回.
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：he
 * 创建时间：2018/4/2810:58
 * 修 改 者：
 * 修改时间：
 */
public class FileEntity {

    private String name;

    private int size;

    private String path;

    private String fullPath;

    private String thumbFullPath;
    private String thumbPath;

    @Override
    public String toString() {
        return "FileEntity{" +
                "name='" + name + '\'' +
                ", size=" + size +
                ", path='" + path + '\'' +
                ", fullPath='" + fullPath + '\'' +
                ", thumbFullPath='" + thumbFullPath + '\'' +
                ", thumbPath='" + thumbPath + '\'' +
                '}';
    }

    public String getThumbFullPath() {
        return thumbFullPath;
    }

    public void setThumbFullPath(String thumbFullPath) {
        this.thumbFullPath = thumbFullPath;
    }

    public String getThumbPath() {
        return thumbPath;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }


}
