package com.haocang.waterlink.home.bean;

import java.util.List;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区云锦路 500 号绿地汇中心 A 座 20 楼 2001
 * 网址：http://www.haocang.com/
 * 标 题：
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：he
 * 创建时间：2018/1/1710:06
 * 修 改 者：
 * 修改时间：
 */
public class MenuEntity {
    private String name;
    private String type;
    private int id;
    private String iconUrl;
    private int sort;
    private int reson;
    private String fragmentName;
    private String params;
    private boolean showHomepage;//标记是否保存
    private int appType;
    private String key;
    private int order;
    private String title;
    private int parentId;
    private String url;
    private String icon;
    private String target;
    private int version;
    private Object state;

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public boolean isShowHomepage() {
        return showHomepage;
    }

    public void setShowHomepage(boolean showHomepage) {
        this.showHomepage = showHomepage;
    }


    private List<String> tabList;

    public List<String> getTabList() {
        return tabList;
    }

    public void setTabList(List<String> tabList) {
        this.tabList = tabList;
    }


    public List<MenuEntity> getSecondMenu() {
        return secondMenu;
    }

    public void setSecondMenu(List<MenuEntity> secondMenu) {
        this.secondMenu = secondMenu;
    }

    private List<MenuEntity> secondMenu;

    public int getAppType() {
        return appType;
    }

    public void setAppType(int appType) {
        this.appType = appType;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getReson() {
        return reson;
    }

    public void setReson(int reson) {
        this.reson = reson;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShowName() {
        String showName = "";
        if (name != null) {
            if (name.length() > 9) {
                showName = name.substring(0, 9);
            } else {
                showName = name;
            }
        }
        return showName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if (type != null && type.startsWith("/")) {
            type = type.substring(type.indexOf("/") + 1, type.length());
        }
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getFragmentName() {
        return fragmentName;
    }

    public void setFragmentName(String fragmentName) {
        this.fragmentName = fragmentName;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Object getState() {
        return state;
    }

    public void setState(Object state) {
        this.state = state;
    }
}
