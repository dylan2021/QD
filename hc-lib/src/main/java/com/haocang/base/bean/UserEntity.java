package com.haocang.base.bean;

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
 * 创建时间：2018/1/3014:31
 * 修 改 者：
 * 修改时间：
 */
public class UserEntity {
    /**
     * id : 5
     * login : kf
     * name : 开发管理员
     * tel : 13684857529
     * email : kf@qq.com
     * imageUrl : null
     * langKey : zh-cn
     * activated : true
     * resetDate : 2018-01-16T09:11:05Z
     * tenantId : 1
     * orgId : null
     * org : null
     * state : 1
     * version : 0
     */

    private long id;
    private String login;
    private String name;
    private String tel;
    private String email;
    private String imageUrl;
    private String langKey;
    private boolean activated;
    private String resetDate;
    private long tenantId;
    private int orgId;
    private String org;
    private long state;
    private long version;
    private String label;
    private Integer value;
    private String orgName;
    private String password;

    //    LOO_MPOINT_RC：远程控制。
//    VOI_VOICE_ALL：语音识别。
    private String[] appOpers;

    public String[] getAppOpers() {
        return appOpers;
    }

    public void setAppOpers(String[] appOpers) {
        this.appOpers = appOpers;
    }


    public String getWebsocketAddress() {
        return websocketAddress;
    }

    public void setWebsocketAddress(String websocketAddress) {
        this.websocketAddress = websocketAddress;
    }

    private String websocketAddress;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public List<OrgEntity> getOrgEntity() {
        return orgEntity;
    }

    public void setOrgEntity(List<OrgEntity> orgEntity) {
        this.orgEntity = orgEntity;
    }

    private List<OrgEntity> orgEntity;


    private boolean firstLogin;

    private boolean isEqu;
    private boolean isPatrol;
    private boolean isFault;
    private boolean isRepair;
    private boolean isCurve;
    private boolean isMaintain;

    public boolean isMaintain() {
        return isMaintain;
    }

    public void setMaintain(boolean maintain) {
        isMaintain = maintain;
    }


    public boolean isEqu() {
        return isEqu;
    }

    public void setEqu(boolean equ) {
        isEqu = equ;
    }

    public boolean isPatrol() {
        return isPatrol;
    }

    public void setPatrol(boolean patrol) {
        isPatrol = patrol;
    }

    public boolean isFault() {
        return isFault;
    }

    public void setFault(boolean fault) {
        isFault = fault;
    }

    public boolean isRepair() {
        return isRepair;
    }

    public void setRepair(boolean repair) {
        isRepair = repair;
    }

    public boolean isCurve() {
        return isCurve;
    }

    public void setCurve(boolean curve) {
        isCurve = curve;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }


    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }


    private OrganizationalEntity organizationalEntity;

    private boolean select = false;

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public OrganizationalEntity getOrganizationalEntity() {
        return organizationalEntity;
    }

    public void setOrganizationalEntity(OrganizationalEntity organizationalEntity) {
        this.organizationalEntity = organizationalEntity;
    }

    public boolean isFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(boolean firstLogin) {
        this.firstLogin = firstLogin;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getResetDate() {
        return resetDate;
    }

    public void setResetDate(String resetDate) {
        this.resetDate = resetDate;
    }

    public long getTenantId() {
        return tenantId;
    }

    public void setTenantId(long tenantId) {
        this.tenantId = tenantId;
    }

    public int getOrgId() {
        return getParentIds();
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public long getState() {
        return state;
    }

    public void setState(long state) {
        this.state = state;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }


    public int getParentIds() {
        if (orgEntity != null) {
            for (OrgEntity entity : orgEntity) {
                if (entity.getId() == orgId && (entity.getType() == 3 || entity.getType() == 4)) {
                    return entity.getParentId();
                }
            }
        }
        return orgId;
    }
}
