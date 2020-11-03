package com.haocang.base.bean;

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
 * 创建时间：2018/4/214:47
 * 修 改 者：
 * 修改时间：
 */
public class EquimentEntity {


    /**
     * id : 5
     * name : 1
     * code : 2
     * typeId : 6
     * tenantId : 1
     * orgId : 4
     * processId : 137
     * abc : 1
     * imageUrl :
     * model : 2
     * specification :
     * brand : ss
     * vendor : null
     * manufacturer :
     * serialNumber :
     * manufactureDate : 2018-03-29 00:00:00
     * installDate : null
     * warrantyPeriod : null
     * parentId : 1
     * maintainState : 1
     * state : 2
     * version : 26
     * typeName : 4
     * orgName : 枫泾污水处理厂
     * processName : 曝气池
     */
    private String personResponsibleNames;
    private int id;
    private String name;//设备名称
    private String code;//设备编号
    private int typeId;
    private int tenantId;
    private int orgId;
    private int processId;
    private int abc;
    private String imageUrl;
    private String typeImageUrl;
    private String model;
    private String specification;
    private String brand;
    private String vendor;
    private String manufacturer;
    private String serialNumber;
    private String manufactureDate;
    private String installDate;
    private String warrantyPeriod;
    private int parentId;
    private int maintainState;
    private int state;
    private int version;
    private String typeName;
    private String orgName;
    private String processName;
    private String parentName;//上级设备

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    private Double longitude;
    private Double latitude;

    public boolean isConcerned() {
        return concerned;
    }

    public void setConcerned(boolean concerned) {
        this.concerned = concerned;
    }

    private boolean concerned;//是否已关注

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeImageUrl() {
        return typeImageUrl;
    }

    public void setTypeImageUrl(String typeImageUrl) {
        this.typeImageUrl = typeImageUrl;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getTenantId() {
        return tenantId;
    }

    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public int getProcessId() {
        return processId;
    }

    public void setProcessId(int processId) {
        this.processId = processId;
    }

    public String getPersonResponsibleNames() {
        return personResponsibleNames;
    }

    public void setPersonResponsibleNames(String personResponsibleNames) {
        this.personResponsibleNames = personResponsibleNames;
    }

    public int getAbc() {
        return abc;
    }

    public void setAbc(int abc) {
        this.abc = abc;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getManufactureDate() {
        return manufactureDate;
    }

    public void setManufactureDate(String manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    public String getInstallDate() {
        return installDate;
    }

    public void setInstallDate(String installDate) {
        this.installDate = installDate;
    }

    public String getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(String warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getMaintainState() {
        return maintainState;
    }

    public void setMaintainState(int maintainState) {
        this.maintainState = maintainState;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getNewAbc() {
        if (abc == 1) {
            return "A";
        } else if (abc == 2) {
            return "B";
        } else {
            return "C";
        }
    }
}
